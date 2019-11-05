package echomskfan.gmail.com.presentation.casts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import echomskfan.gmail.com.R

internal class CastsAdapter(private val viewModel: CastsViewModel) : RecyclerView.Adapter<CastsAdapter.Holder>() {

    private val items = mutableListOf<ICastsListItemDelegate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false))
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

            }
        }
    }
}