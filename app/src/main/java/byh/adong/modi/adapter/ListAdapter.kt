package byh.adong.modi.Adapter

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import byh.adong.modi.R
import byh.adong.modi.data.Diarielist

class MyAdapter(val adapterlist : ArrayList<Diarielist> = ArrayList())
    : RecyclerView.Adapter<MyAdapter.ViewHolder>(){

    val TAG = "MyAdpter"
    var viewHolder : ViewHolder? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutview = LayoutInflater.from(parent.context).inflate(R.layout.image_activity, parent, false)
        viewHolder = ViewHolder(layoutview)
        return viewHolder!!
    }

    override fun getItemCount(): Int = adapterlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.days.setText(adapterlist.get(position).days)
        holder.feel.setText(adapterlist.get(position).feel)
        holder.weather.setText(adapterlist.get(position).weather)
        holder.contents.setText(adapterlist.get(position).contents)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {
        val cv: CardView = itemview.findViewById(R.id.cv)
        val days: TextView = itemview.findViewById(R.id.days)
        val weather : TextView = itemview.findViewById(R.id.weather)
        val feel : TextView = itemview.findViewById(R.id.feel)
        val contents: TextView = itemview.findViewById(R.id.contents)
    }
}
