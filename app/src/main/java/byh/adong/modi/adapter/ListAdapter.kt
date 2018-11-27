package byh.adong.modi.adapter

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import byh.adong.modi.R
import byh.adong.modi.data.Diarielist
import byh.adong.modi.data.DiariesGet
import byh.adong.modi.service.APIService
import byh.adong.modi.service.RetrofitUtil
import byh.adong.modi.util.SharedPreferenceUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAdapter(val adapterlist: ArrayList<Diarielist> = ArrayList()) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    val TAG = "MyAdpter"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutview = LayoutInflater.from(parent.context).inflate(R.layout.image_activity, parent, false)
        return ViewHolder(layoutview)
    }

    override fun getItemCount(): Int = adapterlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.days.setText(adapterlist.get(position).days)
        holder.feel.setText(adapterlist.get(position).feel)
        holder.weather.setText(adapterlist.get(position).weather)
        holder.contents.setText(adapterlist.get(position).contents)
        holder.deleteDiarie.setOnClickListener {
            val token = SharedPreferenceUtil.getPreference(holder.itemView.context)
            val apiService = RetrofitUtil.creatService(APIService::class.java, token)
            val call = apiService.deleteDiary(adapterlist.get(position).diary_id)
            call.enqueue(object : Callback<DiariesGet>{
                override fun onFailure(call: Call<DiariesGet>, t: Throwable) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onResponse(call: Call<DiariesGet>, response: Response<DiariesGet>) {
                    Log.d(TAG, "${adapterlist.get(position).diary_id} + $position")
                    adapterlist.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
    }

    class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview){
        val days: TextView = itemview.findViewById(R.id.days)
        val weather: TextView = itemview.findViewById(R.id.weather)
        val feel: TextView = itemview.findViewById(R.id.feel)
        val contents: TextView = itemview.findViewById(R.id.contents)
        val deleteDiarie: ImageView = itemview.findViewById(R.id.deleteDiarie)
    }
}
