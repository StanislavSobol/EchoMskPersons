package echomskfan.gmail.com.presentation.casts

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import echomskfan.gmail.com.R
import echomskfan.gmail.com.utils.fromSecToAudioDuration
import echomskfan.gmail.com.utils.gone
import echomskfan.gmail.com.utils.visible
import kotlinx.android.synthetic.main.item_cast.view.*

internal class CastsAdapter(private val viewModel: CastsViewModel) : RecyclerView.Adapter<CastsAdapter.Holder>() {

    //    private val currentPage:Int = 1
    private val items = mutableListOf<ICastsListItemDelegate>()

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
        Log.d("SSS", "position = $position / ${items.size}")
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == items.size - 1) TYPE_PROGRESS_BAR else TYPE_ITEM
    }

//    private fun getPageNumsFromItems(items: List<ICastsListItemDelegate>): Set<Int> {
//        val result = mutableSetOf<Int>()
//        items.filter { it is CastListItem }.forEach { result.add((it as CastListItem).pageNum) }
//        return result
//    }

//    private fun <T> getPageNumsFromItemsitems: List<out CastListItem>):Set<Int> {
//    }


    fun addItems(newItems: List<CastListItem>) {
        items.remove(items.find { it is CastsProgressBarItem })
//        val castListItems = mutableListOf<CastListItem>()
//        items.forEach {
//            if (it is CastListItem) {
//                castListItems.add(it)
//            }
//        }
//
//        if (castListItems.isEmpty()) {
//            notifyDataSetChanged()
//            return
//        }


        val newPages = mutableSetOf<Int>()
        newItems.forEach { newPages.add(it.pageNum) }
        val forDeleteItems = mutableListOf<ICastsListItemDelegate>()
        newPages.forEach { newPage ->
            run {
                items.forEach {
                    if (it is CastListItem && it.pageNum == newPage) {
                        forDeleteItems.add(it)
                    }
                }
            }
        }

        forDeleteItems.forEach { items.remove(it) }
        forDeleteItems.clear()

        // items.clear()
        items.addAll(newItems)
        if (newItems.isNotEmpty()) {
            items.add(CastsProgressBarItem())
        }

        notifyDataSetChanged()
    }

    private class DiffUtilsCallback(
        private val oldItems: List<ICastsListItemDelegate>,
        private val newItems: List<CastListItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldItems[oldItemPosition]
            val new = newItems[newItemPosition]
            return if (old is CastListItem) {
                old.id == new.id
            } else {
                false
            }
        }

        override fun getOldListSize() = oldItems.size

        override fun getNewListSize() = newItems.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val old = oldItems[oldItemPosition]
            val new = newItems[newItemPosition]
            return if (old is CastListItem) {
                old.same(new)
            } else {
                false
            }
        }
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

                    itemView.item_content_audio_layout.visible()
                } else {
                    itemView.item_content_audio_layout.gone()
                }

                itemView.item_content_favorite_image_view.setImageResource(
                    if (item.fav) R.drawable.ic_baseline_favorite_black_24dp
                    else R.drawable.ic_baseline_favorite_none_24dp
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