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

internal class CastsAdapter(private val viewModel: CastsViewModel) : RecyclerView.Adapter<CastsAdapter.Holder>() {

    private val items = mutableListOf<ICastsListItemDelegate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.item_cast, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(list: List<CastListItem>?) {
        items.clear()
        list?.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: ICastsListItemDelegate) {
            if (item is CastListItem) {
                itemView.item_content_type_text_view.text = item.typeSubtype
                itemView.item_content_date_text_view.text = item.formattedDate
                itemView.item_content_short_text_view.text = item.shortText

                if (item.mp3Url.isNotEmpty()) {
                    itemView.item_content_audio_duration_text_view.text = item.mp3Duration.fromSecToAudioDuration()
                    itemView.item_content_audio_play_image_button.setOnClickListener {
                        // presenter.play(item)
                    }

                    itemView.item_content_audio_layout.visible()
                } else {
                    itemView.item_content_audio_layout.gone()
                }

                itemView.item_content_favorite_image_view.setImageResource(
                    if (item.fav) R.drawable.ic_baseline_favorite_24px
                    else R.drawable.ic_baseline_favorite_border_24px
                )

//                itemView.item_content_favorite_image_view.setOnClickListener {
//                    presenter.itemFavIconClicked(item)
//                }
            }
        }
    }
}