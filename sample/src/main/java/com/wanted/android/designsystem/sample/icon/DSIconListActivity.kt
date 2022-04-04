package com.wanted.android.designsystem.sample.icon

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView
import com.wanted.android.designsystem.util.tint
import com.wanted.android.designsystem.sample.BindingActivity
import com.wanted.android.designsystem.R
import com.wanted.android.designsystem.sample.databinding.DsActivityIconListBinding

class DSIconListActivity
    : BindingActivity<DsActivityIconListBinding>(DsActivityIconListBinding::inflate) {

    companion object {
        const val PARAM_SIZE = "PARAM_SIZE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.gridView.adapter = IconAdapter(icons)
        binding.gridView.numColumns = 5
    }

    private val icons: List<Int>
        get() = when (intent.getIntExtra(PARAM_SIZE, 24)) {
            12 -> listOf(
                R.drawable.icon_1_line_alarmsetting_12,
                R.drawable.icon_1_line_answer_12,
                R.drawable.icon_1_line_arrow_down_12,
                R.drawable.icon_1_line_arrow_left_12,
                R.drawable.icon_1_line_arrow_right_12,
                R.drawable.icon_1_line_arrow_up_12,
                R.drawable.icon_1_line_bookmark_12,
                R.drawable.icon_1_line_checkbox_12,
                R.drawable.icon_1_line_check_12,
                R.drawable.icon_1_line_download_12,
                R.drawable.icon_1_line_email_12,
                R.drawable.icon_1_line_exit_12,
                R.drawable.icon_1_line_expand_12,
                R.drawable.icon_1_line_filtering_12,
                R.drawable.icon_1_line_fold_12,
                R.drawable.icon_1_line_full_12,
                R.drawable.icon_1_line_handle_12,
                R.drawable.icon_1_line_heart_12,
                R.drawable.icon_1_line_home_12,
                R.drawable.icon_1_line_lang_12,
                R.drawable.icon_1_line_like_12,
                R.drawable.icon_1_line_link_12,
                R.drawable.icon_1_line_lock_12,
                R.drawable.icon_1_line_mappin_12,
                R.drawable.icon_1_line_menu_12,
                R.drawable.icon_1_line_menu_star_12,
                R.drawable.icon_1_line_more_12,
                R.drawable.icon_1_line_morevertical_12,
                R.drawable.icon_1_line_notification_12,
                R.drawable.icon_1_line_pin_12,
                R.drawable.icon_1_line_plus_12,
                R.drawable.icon_1_line_profile_12,
                R.drawable.icon_1_line_question_12,
                R.drawable.icon_1_line_radio_12,
                R.drawable.icon_1_line_refresh_12,
                R.drawable.icon_1_line_send_12,
                R.drawable.icon_1_line_setting_12,
                R.drawable.icon_1_line_share_12,
                R.drawable.icon_1_line_sharelink_12,
                R.drawable.icon_1_line_star_12,
                R.drawable.icon_1_line_stop_12,
                R.drawable.icon_1_line_thunder_12,
                R.drawable.icon_1_line_totop_12,
                R.drawable.icon_1_line_trash_12,
                R.drawable.icon_1_line_upload_12,
                R.drawable.icon_2_fill_bookmark_12,
                R.drawable.icon_2_fill_boxcheck_12,
                R.drawable.icon_2_fill_filtering_12,
                R.drawable.icon_2_fill_heart_12,
                R.drawable.icon_2_fill_home_12,
                R.drawable.icon_2_fill_like_12,
                R.drawable.icon_2_fill_lock_12,
                R.drawable.icon_2_fill_profile_12,
                R.drawable.icon_2_fill_radio_12,
                R.drawable.icon_2_fill_send_12,
                R.drawable.icon_2_fill_star_12,
                R.drawable.icon_2_fill_thunder_12,
            )
            18 -> listOf(
                R.drawable.icon_2_fill_conversation_18,
                R.drawable.icon_1_line_heart_18,
                R.drawable.icon_2_fill_heart_18,
                R.drawable.icon_1_line_messege_18,
                R.drawable.icon_2_fill_messege_18,
                R.drawable.icon_1_line_camera_18,
                R.drawable.icon_2_fill_camera_18,
                R.drawable.icon_1_line_star_18,
                R.drawable.icon_2_fill_star_18,
                R.drawable.icon_1_line_like_18,
                R.drawable.icon_2_fill_like_18,
                R.drawable.icon_1_line_lock_18,
                R.drawable.icon_2_fill_lock_18,
                R.drawable.icon_1_line_send_18,
                R.drawable.icon_2_fill_send_18,
                R.drawable.icon_1_line_bookmark_18,
                R.drawable.icon_2_fill_bookmark_18,
                R.drawable.icon_1_line_filtering_18,
                R.drawable.icon_2_fill_filtering_18,
                R.drawable.icon_1_line_profile_18,
                R.drawable.icon_2_fill_profile_18,
                R.drawable.icon_1_line_starbadge_18,
                R.drawable.icon_2_fill_starbadge_18,
                R.drawable.icon_1_line_checkbadge_18,
                R.drawable.icon_2_fill_checkbadge_18,
                R.drawable.icon_1_line_search_18,
                R.drawable.icon_2_fill_search_18,
                R.drawable.icon_1_line_home_18,
                R.drawable.icon_2_fill_home_18,
                R.drawable.icon_1_line_bag_18,
                R.drawable.icon_2_fill_bag_18,
                R.drawable.icon_1_line_thunder_18,
                R.drawable.icon_2_fill_thunder_18,
                R.drawable.icon_1_line_circlechek_18,
                R.drawable.icon_2_fill_circlechek_18,
                R.drawable.icon_1_line_checkbox_18,
                R.drawable.icon_2_fill_boxcheck_18,
                R.drawable.icon_1_line_radio_18,
                R.drawable.icon_2_fill_radio_18,
                R.drawable.icon_1_line_community_18,
                R.drawable.icon_2_fill_community_18,

                R.drawable.icon_1_line_arrow_left_18,
                R.drawable.icon_1_line_arrow_right_18,
                R.drawable.icon_1_line_arrow_up_18,
                R.drawable.icon_1_line_arrow_down_18,
                R.drawable.icon_1_line_exit_18,
                R.drawable.icon_1_line_fold_18,
                R.drawable.icon_1_line_expand_18,
                R.drawable.icon_1_line_plus_18,
                R.drawable.icon_1_line_menu_18,
                R.drawable.icon_1_line_totop_18,
                R.drawable.icon_1_line_systemcheck_18,
                R.drawable.icon_1_line_circleinfo_18,
                R.drawable.icon_1_line_circlealert_18,
                R.drawable.icon_1_line_circlehelp_18,
                R.drawable.icon_1_line_check_18,
                R.drawable.icon_1_line_answer_18,
                R.drawable.icon_1_line_question_18,
                R.drawable.icon_1_line_more_18,
                R.drawable.icon_1_line_morevertical_18,
                R.drawable.icon_1_line_warning_18,
                R.drawable.icon_1_line_email_18,
                R.drawable.icon_1_line_document_18,
                R.drawable.icon_1_line_filter_18,
                R.drawable.icon_1_line_notification_18,
                R.drawable.icon_1_line_lang_18,
                R.drawable.icon_1_line_write_18,
                R.drawable.icon_1_line_exit_18,
                R.drawable.icon_1_line_calendar_18,
                R.drawable.icon_1_line_setting_18,
                R.drawable.icon_1_line_mappin_18,
                R.drawable.icon_1_line_download_18,
                R.drawable.icon_1_line_upload_18,
                R.drawable.icon_1_line_full_18,
                R.drawable.icon_1_line_image_18,
                R.drawable.icon_1_line_sharelink_18,
                R.drawable.icon_1_line_refresh_18,
                R.drawable.icon_1_line_pin_18,
                R.drawable.icon_1_line_stop_18,
                R.drawable.icon_1_line_trash_18,
                R.drawable.icon_1_line_share_18,
                R.drawable.icon_1_line_lang_kor_18,
                R.drawable.icon_1_line_lang_chn_18,
                R.drawable.icon_1_line_lang_jpn_18,
                R.drawable.icon_1_line_lang_eng_18,
                R.drawable.icon_1_line_conversation_18,
                R.drawable.icon_1_line_menu_star_18,
                R.drawable.icon_1_line_link_18,
                R.drawable.icon_1_line_handle_18,
                R.drawable.icon_1_line_circle_plus_18,
                R.drawable.icon_1_line_lang_more_18,
                R.drawable.icon_1_line_alarmsetting_18,
            )
            else -> /* 24 */ listOf(
                R.drawable.icon_2_fill_conversation_24,
                R.drawable.icon_1_line_heart_24,
                R.drawable.icon_2_fill_heart_24,
                R.drawable.icon_1_line_messege_24,
                R.drawable.icon_2_fill_messege_24,
                R.drawable.icon_1_line_camera_24,
                R.drawable.icon_2_fill_camera_24,
                R.drawable.icon_1_line_star_24,
                R.drawable.icon_2_fill_star_24,
                R.drawable.icon_1_line_like_24,
                R.drawable.icon_2_fill_like_24,
                R.drawable.icon_1_line_lock_24,
                R.drawable.icon_2_fill_lock_24,
                R.drawable.icon_1_line_send_24,
                R.drawable.icon_2_fill_send_24,
                R.drawable.icon_1_line_bookmark_24,
                R.drawable.icon_2_fill_bookmark_24,
                R.drawable.icon_1_line_filtering_24,
                R.drawable.icon_2_fill_filtering_24,
                R.drawable.icon_1_line_profile_24,
                R.drawable.icon_2_fill_profile_24,
                R.drawable.icon_1_line_starbadge_24,
                R.drawable.icon_2_fill_starbadge_24,
                R.drawable.icon_1_line_checkbadge_24,
                R.drawable.icon_2_fill_checkbadge_24,
                R.drawable.icon_1_line_search_24,
                R.drawable.icon_2_fill_search_24,
                R.drawable.icon_1_line_home_24,
                R.drawable.icon_2_fill_home_24,
                R.drawable.icon_1_line_bag_24,
                R.drawable.icon_2_fill_bag_24,
                R.drawable.icon_1_line_thunder_24,
                R.drawable.icon_2_fill_thunder_24,
                R.drawable.icon_1_line_circlechek_24,
                R.drawable.icon_2_fill_circlechek_24,
                R.drawable.icon_1_line_checkbox_24,
                R.drawable.icon_2_fill_boxcheck_24,
                R.drawable.icon_1_line_radio_24,
                R.drawable.icon_2_fill_radio_24,
                R.drawable.icon_1_line_community_24,
                R.drawable.icon_2_fill_community_24,

                R.drawable.icon_1_line_arrow_left_24,
                R.drawable.icon_1_line_arrow_right_24,
                R.drawable.icon_1_line_arrow_up_24,
                R.drawable.icon_1_line_arrow_down_24,
                R.drawable.icon_1_line_exit_24,
                R.drawable.icon_1_line_fold_24,
                R.drawable.icon_1_line_expand_24,
                R.drawable.icon_1_line_plus_24,
                R.drawable.icon_1_line_menu_24,
                R.drawable.icon_1_line_totop_24,
                R.drawable.icon_1_line_systemcheck_24,
                R.drawable.icon_1_line_circleinfo_24,
                R.drawable.icon_1_line_circlealert_24,
                R.drawable.icon_1_line_circlehelp_24,
                R.drawable.icon_1_line_check_24,
                R.drawable.icon_1_line_answer_24,
                R.drawable.icon_1_line_question_24,
                R.drawable.icon_1_line_more_24,
                R.drawable.icon_1_line_morevertical_24,
                R.drawable.icon_1_line_warning_24,
                R.drawable.icon_1_line_email_24,
                R.drawable.icon_1_line_document_24,
                R.drawable.icon_1_line_filter_24,
                R.drawable.icon_1_line_notification_24,
                R.drawable.icon_1_line_lang_24,
                R.drawable.icon_1_line_write_24,
                R.drawable.icon_1_line_exit_24,
                R.drawable.icon_1_line_calendar_24,
                R.drawable.icon_1_line_setting_24,
                R.drawable.icon_1_line_mappin_24,
                R.drawable.icon_1_line_download_24,
                R.drawable.icon_1_line_upload_24,
                R.drawable.icon_1_line_full_24,
                R.drawable.icon_1_line_image_24,
                R.drawable.icon_1_line_sharelink_24,
                R.drawable.icon_1_line_refresh_24,
                R.drawable.icon_1_line_pin_24,
                R.drawable.icon_1_line_stop_24,
                R.drawable.icon_1_line_trash_24,
                R.drawable.icon_1_line_share_24,
                R.drawable.icon_1_line_lang_kor_24,
                R.drawable.icon_1_line_lang_chn_24,
                R.drawable.icon_1_line_lang_jpn_24,
                R.drawable.icon_1_line_lang_eng_24,
                R.drawable.icon_1_line_conversation_24,
                R.drawable.icon_1_line_menu_star_24,
                R.drawable.icon_1_line_link_24,
                R.drawable.icon_1_line_handle_24,
                R.drawable.icon_1_line_circle_plus_24,
                R.drawable.icon_1_line_lang_more_24,
                R.drawable.icon_1_line_alarmsetting_24,
            )
        }

}

class IconAdapter(
    private val icons: List<Int>
) : BaseAdapter() {

    override fun getCount(): Int = icons.size

    override fun getItem(position: Int): Any = icons[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val context = parent?.context!!
        val imageView = (convertView as? ImageView) ?: ImageView(context)
        imageView.layoutParams = AbsListView.LayoutParams(
            AbsListView.LayoutParams.WRAP_CONTENT,
            AbsListView.LayoutParams.WRAP_CONTENT
        )
        imageView.setImageResource(getItem(position) as Int)
        imageView.tint = context.getColor(R.color.neutral_black_100)

        return imageView
    }

}