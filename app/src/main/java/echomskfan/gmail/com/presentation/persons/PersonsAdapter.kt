package echomskfan.gmail.com.presentation.persons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import echomskfan.gmail.com.R
import kotlinx.android.synthetic.main.item_person.view.*

internal class PersonsAdapter(private val viewModel: PersonsViewModel) : RecyclerView.Adapter<PersonsAdapter.Holder>() {

    private val items = mutableListOf<IPersonsListItemDelegate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_person,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(list: List<PersonListItem>?) {
        items.clear()
        list?.let { items.addAll(it) }
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: IPersonsListItemDelegate) {
            if (item is PersonListItem) {
                Picasso.with(itemView.context)
                    .load(item.photoUrl)
                    .into(itemView.personItemImageView)

                itemView.personItemNameTextView.text = item.fullName
                itemView.personItemProfessionTextView.text = item.profession
                itemView.personItemInfoTextView.text = item.info

                itemView.personItemNotificationImageView.setImageResource(
                    if (item.notification) R.drawable.ic_baseline_notifications_black_24dp
                    else R.drawable.ic_baseline_notifications_none_24dp
                )

                itemView.personItemNotificationImageView.setOnClickListener {
                    viewModel.itemIdNotificationClicked(item.id)
                }

                itemView.personItemFavImageView.setImageResource(
                    if (item.fav) R.drawable.ic_baseline_favorite_black_24dp
                    else R.drawable.ic_baseline_favorite_none_24dp
                )

                itemView.personItemFavImageView.setOnClickListener {
                    viewModel.itemIdFavClicked(item.id)
                }

                itemView.setOnClickListener {
                    viewModel.itemIdClicked(
                        item.id,
                        itemView.personItemImageView
                    )
                }
            }
        }
    }
}