package echomskfan.gmail.com.presentation.persons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import echomskfan.gmail.com.R
import kotlinx.android.synthetic.main.person_item.view.*

internal class PersonsAdapter : RecyclerView.Adapter<PersonsAdapter.Holder>() {

    private val items = mutableListOf<IPersonsListItemDelegate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false))
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun addItems(list: List<PersonListItem>?) {
        items.clear()
        list?.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: IPersonsListItemDelegate) {
            if (item is PersonListItem) {
                itemView.personItemNameTextView.text = item.fullName
                itemView.personItemProfessionTextView.text = item.profession
                itemView.personItemInfoTextView.text = item.info


                itemView.personItemNotificationImageView.setImageResource(
                    if (item.notification) R.drawable.ic_baseline_notifications_24px
                    else R.drawable.ic_baseline_notifications_none_24px
                )
            }

//            itemView.vipItemNotificationImageView.setImageResource(
//                if (vipVM.notification) R.drawable.ic_baseline_notifications_24px
//                else R.drawable.ic_baseline_notifications_none_24px
//            )
//
//            itemView.vipItemNotificationImageView.setOnClickListener {
//                presenter.itemNotificationIconClicked(vipVM)
//            }
//
//            itemView.vipItemFavImageView.setImageResource(
//                if (vipVM.fav) R.drawable.ic_baseline_favorite_24px
//                else R.drawable.ic_baseline_favorite_border_24px
//            )
//
//            itemView.vipItemFavImageView.setOnClickListener {
//                presenter.itemFavIconClicked(vipVM)
//            }
//
//            itemView.setOnClickListener {
//                presenter.itemClicked(vipVM)
//            }
        }


    }
}