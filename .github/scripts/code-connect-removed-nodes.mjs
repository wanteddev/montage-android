// 이전 parse 결과(before)에는 있는데 지금 parse 결과(after)에는 없는 Figma 노드를 한 줄에 하나씩 출력한다.
// code-connect-publish.yml 이 이 노드들을 unpublish 한다.
// 같은 노드에 variant 별 문서가 여러 개일 수 있어 노드 단위로 비교한다. 노드가 남아 있으면 내리지 않는다.
//
// 사용: node code-connect-removed-nodes.mjs <before.json> <after.json> <label> [--figma-dir <지금 figma 폴더>]
//
// 안전장치(하나라도 걸리면 아무것도 출력하지 않고 exit 3 — 워크플로가 실패하고 unpublish 는 돌지 않는다):
// - after 에 이 label 문서가 하나도 없는데 before 에는 있다 → parse 가 템플릿을 못 읽은 것이다(include 오타, label 변경 등).
//   이대로 두면 전부 "사라진" 것으로 보여 모든 스니펫을 내리게 된다.
// - --figma-dir 를 주면, 그 폴더의 템플릿 파일(*.figma.ts, *.figma.batch.json) 중 after 에 문서가 하나도 없는 파일이 있다
//   → include 패턴이 파일을 놓친 것이다. 그 파일의 노드를 지워진 것으로 잘못 볼 수 있다.
import { readFileSync, readdirSync } from 'node:fs'
import { join, resolve } from 'node:path'

const args = process.argv.slice(2)
let figmaDir = null
const pos = []
for (let i = 0; i < args.length; i++) {
  if (args[i] === '--figma-dir') figmaDir = args[++i]
  else pos.push(args[i])
}
const [beforePath, afterPath, label] = pos
if (!beforePath || !afterPath || !label || (args.includes('--figma-dir') && !figmaDir)) {
  console.error('usage: code-connect-removed-nodes.mjs <before.json> <after.json> <label> [--figma-dir <dir>]')
  process.exit(2)
}

const fail = (msg) => {
  console.error(`::error::${msg}`)
  process.exit(3)
}

const docsOf = (path) => {
  const docs = JSON.parse(readFileSync(path, 'utf8'))
  if (!Array.isArray(docs)) throw new Error(`${path}: parse 결과가 배열이 아닙니다`)
  return docs
}

const beforeDocs = docsOf(beforePath).filter((d) => d.label === label)
const afterAll = docsOf(afterPath)
const afterDocs = afterAll.filter((d) => d.label === label)

if (beforeDocs.length > 0 && afterDocs.length === 0) {
  fail(
    `지금 parse 결과에 label "${label}" 문서가 없습니다(전체 ${afterAll.length}개). ` +
      `템플릿을 못 읽은 것으로 보고 unpublish 를 멈춥니다. figma.config.json 의 include·label 을 확인하세요.`,
  )
}

if (figmaDir) {
  const isTemplate = (name) => name.endsWith('.figma.ts') || name.endsWith('.figma.batch.json')
  const walk = (dir) =>
    readdirSync(dir, { withFileTypes: true }).flatMap((e) => {
      if (e.name === 'node_modules' || e.name.startsWith('.')) return []
      const p = join(dir, e.name)
      return e.isDirectory() ? walk(p) : isTemplate(e.name) ? [resolve(p)] : []
    })
  const parsed = new Set(afterAll.map((d) => d._codeConnectFilePath && resolve(d._codeConnectFilePath)).filter(Boolean))
  const missed = walk(figmaDir).filter((p) => !parsed.has(p))
  if (missed.length > 0) {
    fail(
      `parse 가 템플릿 ${missed.length}개를 읽지 않았습니다(include 패턴 확인). ` +
        `이 파일의 노드를 지워진 것으로 잘못 볼 수 있어 unpublish 를 멈춥니다: ${missed.join(', ')}`,
    )
  }
}

// 같은 노드라도 URL 표기가 다를 수 있다(파일 이름 slug, node-id 의 '-'·':'·'%3A', &m=dev 같은 추가 파라미터).
// CLI 처럼 fileKey + nodeId 로 비교해야, 표기만 바뀐 노드를 "사라진" 것으로 보고 내리지 않는다.
// 해석할 수 없는 URL 은 문자열 그대로 비교한다.
const nodeKey = (url) => {
  try {
    const u = new URL(url)
    const seg = u.pathname.split('/').filter(Boolean)
    const i = seg.findIndex((s) => s === 'design' || s === 'file')
    if (i < 0 || !seg[i + 1]) return url
    const fileKey = seg[i + 2] === 'branch' && seg[i + 3] ? seg[i + 3] : seg[i + 1]
    const nodeId = u.searchParams.get('node-id')
    if (!nodeId) return url
    return `${fileKey}:${nodeId.replace(/-/g, ':')}`
  } catch {
    return url
  }
}

const after = new Set(afterDocs.map((d) => nodeKey(d.figmaNode)))
const removed = new Map() // key → before 의 원래 URL(unpublish 에 그대로 넘긴다)
for (const d of beforeDocs) {
  const k = nodeKey(d.figmaNode)
  if (!after.has(k) && !removed.has(k)) removed.set(k, d.figmaNode)
}
for (const url of [...removed.values()].sort()) {
  console.log(url)
}
