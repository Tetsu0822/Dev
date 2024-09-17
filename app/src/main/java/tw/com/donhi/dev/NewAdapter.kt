package tw.com.donhi.dev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import tw.com.donhi.dev.databinding.RowNewBinding
import tw.com.donhi.dev.network.New

class NewAdapter(var news:List<New>) : Adapter<NewAdapter.NewViewHolder>() {
    class NewViewHolder(val view: RowNewBinding) : ViewHolder(view.root) {
        init {
            val titleText = view.new1
            val fileText = view.filename
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val binding = RowNewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return NewViewHolder(binding)
    }

    override fun getItemCount() = news.size


    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val new = news.get(position)
        holder.view.new1.text = new.news_title
        holder.view.filename.text = new.file_name
    }
}