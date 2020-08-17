package echomskfan.gmail.com.presentation.casts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import echomskfan.gmail.com.R
import echomskfan.gmail.com.utils.fromSecToAudioDuration
import echomskfan.gmail.com.utils.gone
import echomskfan.gmail.com.utils.visible
import kotlinx.android.synthetic.main.item_cast.view.*

// TODO Unable to load pages in favs on mode! Message about it/
internal class CastsAdapter(private val viewModel: CastsViewModel) : RecyclerView.Adapter<CastsAdapter.Holder>() {

    private val items = mutableListOf<ICastsListItemDelegate>()
    private var favsOn: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutId = if (viewType == TYPE_ITEM) R.layout.item_cast else R.layout.item_content_progress_bar
        return Holder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
        if (position == items.lastIndex) {
            viewModel.scrolledToBottom()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (!favsOn && position == items.size - 1) TYPE_PROGRESS_BAR else TYPE_ITEM
    }

    fun setItems(newItems: List<CastListItem>, favsOn: Boolean = false) {
        this.favsOn = favsOn
        items.clear()
        items.addAll(newItems)
        if (!favsOn && newItems.isNotEmpty()) {
            items.add(CastsProgressBarItem())
        }

        notifyDataSetChanged()
    }

    /*
     * Class-marker for progress-bar item
     */
    private class CastsProgressBarItem : ICastsListItemDelegate

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ICastsListItemDelegate) {
            if (item is CastListItem) {
                itemView.item_content_type_text_view.text = item.typeSubtype
                itemView.item_content_date_text_view.text = item.formattedDate
                itemView.item_content_short_text_view.text = item.shortText

                if (item.mp3Url.isNotEmpty()) {
                    itemView.item_content_audio_duration_text_view.text = item.mp3Duration.fromSecToAudioDuration()
                    itemView.item_content_audio_play_image_button.setOnClickListener {
                        viewModel.playButtonClicked(item)
                    }

                    itemView.item_content_audio_play_image_button.visible()
                    itemView.item_content_audio_duration_text_view.visible()
                    itemView.itemCastAudioTitleTextView.visible()
                } else {
                    itemView.item_content_audio_play_image_button.gone()
                    itemView.item_content_audio_duration_text_view.gone()
                    itemView.itemCastAudioTitleTextView.gone()
                }

                itemView.item_content_favorite_image_view.setImageResource(
                    if (item.fav) {
                        R.drawable.ic_baseline_favorite_black_32dp
                    } else {
                        R.drawable.ic_baseline_favorite_none_black_32dp
                    }
                )

                itemView.item_content_favorite_image_view.setOnClickListener {
                    viewModel.itemIdFavClicked(item.id)
                }
            }
        }
    }

    companion object {
        const val TYPE_ITEM: Int = 0
        const val TYPE_PROGRESS_BAR: Int = 1
    }
}