// 이전 parse 결과(before)에는 있는데 지금 parse 결과(after)에는 없는 Figma 노드를 한 줄에 하나씩 출력한다.
// code-connect-publish.yml 이 이 노드들을 unpublish 한다.
// 같은 노드에 variant 별 문서가 여러 개일 수 있어 노드 단위로 비교한다. 노드가 남아 있으면 내리지 않는다.
//
// 사용: node code-connect-removed-nodes.mjs <before.json> <after.json> <label>
import { readFileSync } from 'node:fs'

const [beforePath, afterPath, label] = process.argv.slice(2)
if (!beforePath || !afterPath || !label) {
  console.error('usage: code-connect-removed-nodes.mjs <before.json> <after.json> <label>')
  process.exit(2)
}

const nodesOf = (path) => {
  const docs = JSON.parse(readFileSync(path, 'utf8'))
  if (!Array.isArray(docs)) throw new Error(`${path}: parse 결과가 배열이 아닙니다`)
  return new Set(docs.filter((d) => d.label === label).map((d) => d.figmaNode))
}

const before = nodesOf(beforePath)
const after = nodesOf(afterPath)
for (const node of [...before].filter((n) => !after.has(n)).sort()) {
  console.log(node)
}
