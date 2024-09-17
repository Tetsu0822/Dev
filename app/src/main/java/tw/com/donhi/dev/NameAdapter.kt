package tw.com.donhi.dev

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tw.com.donhi.dev.databinding.RowTextViewBinding

//繼承RecyclerView身上的一個類別Adapter<ViewHolder>
class NameAdapter(val names:List<String>) : RecyclerView.Adapter<NameAdapter.NameViewHolder>() {
    class NameViewHolder(var view: RowTextViewBinding) :RecyclerView.ViewHolder(view.root) {

    }

    //最後回傳的ViewHolder，僅僅送一個面板
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = RowTextViewBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return NameViewHolder(binding)
    }

    //回傳資料的個數
    override fun getItemCount(): Int {
        return names.size
    }

    //有資料時，把面板拿過來並將資料塞進去
    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val name = names.get(position)
        holder.view.textView.text = name
    }

}