package echomskfan.gmail.com.presentation.persons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.corelib.startIconTapAnimation
import echomskfan.gmail.com.R
import kotlinx.android.synthetic.main.item_person.view.*

internal class PersonsAdapter(private val viewModel: PersonsViewModel) :
    RecyclerView.Adapter<PersonsAdapter.Holder>() {

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
                with(itemView) {
                    personItemImageView.imageUrl = item.photoUrl

                    personItemNameTextView.text = item.fullName
                    personItemProfessionTextView.text = item.profession
                    personItemInfoTextView.text = item.info

                    personItemInfoImageView.setOnClickListener {
                        viewModel.personItemInfoClicked(item.id)
                        personItemInfoImageView.startIconTapAnimation()
                    }

                    personItemNotificationImageView.setImageResource(
                        if (item.notification) R.drawable.ic_baseline_notifications_black_32dp
                        else R.drawable.ic_baseline_notifications_none_black_32dp
                    )

                    personItemNotificationImageView.setOnClickListener {
                        viewModel.personItemNotificationClicked(item.id)
                        personItemNotificationImageView.startIconTapAnimation()
                    }

                    personItemFavImageView.setImageResource(
                        if (item.fav) R.drawable.ic_baseline_favorite_black_32dp
                        else R.drawable.ic_baseline_favorite_none_black_32dp
                    )

                    personItemFavImageView.setOnClickListener {
                        viewModel.personItemFavClicked(item.id)
                        personItemFavImageView.startIconTapAnimation()
                    }

                    setOnClickListener { viewModel.personItemClicked(item.id) }
                }
            }
        }
    }
}