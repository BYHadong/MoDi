package byh.adong.modi

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import byh.adong.modi.Adapter.MyAdapter
import byh.adong.modi.data.Diarielist
import byh.adong.modi.data.DiariesGet
import byh.adong.modi.service.APIService
import byh.adong.modi.service.RetrofitService
import byh.adong.modi.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "MainActivity"
    var adapter : MyAdapter? = null
    var diarylist : RecyclerView? = null
    val listarraydata: ArrayList<Diarielist> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diarylist = findViewById(R.id.diaryList)
        val lim = LinearLayoutManager(this)
        diarylist!!.layoutManager = lim
        inputDiary()

        diaryWirteButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this, WriteActivity::class.java))
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_logout -> {
                Toast.makeText(applicationContext, "로그아웃", Toast.LENGTH_SHORT).show()
                SharedPreferenceUtil.removePreference(applicationContext)
                startActivity(Intent(applicationContext, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun inputDiary() {
        val token = SharedPreferenceUtil.getPreference(this)
        val apiService = RetrofitService().creatService(APIService::class.java, token)
        val call = apiService.getdiary()
        call.enqueue(object : Callback<DiariesGet> {
            override fun onFailure(call: Call<DiariesGet>, t: Throwable) {
                val alertDialog = AlertDialog.Builder(this@MainActivity)
                alertDialog.setTitle("서버 점검중입니다.")
                alertDialog.setMessage("서버가 점검중입니다. 나중에 다시 들어와 주세요.")
                alertDialog.setIcon(R.drawable.warning)
                alertDialog.setPositiveButton("확인", { dialog, which ->
                    finish()
                })
                alertDialog.show()
                Log.d(TAG, t!!.message)
            }

            override fun onResponse(call: Call<DiariesGet>, response: Response<DiariesGet>) {
                val state = response!!.body()!!.status
                val diaries = response.body()!!.diaries
                if (state.success) {
                    for (i in diaries.size - 1 downTo 0) {
                        val days = diaries.get(i).createdAt
                        val feel = diaries.get(i).feel
                        val weather = diaries.get(i).weather
                        val content = diaries.get(i).contents
                        listarraydata.add(Diarielist(days, feel, weather, content))
                    }
                    adapter = MyAdapter(listarraydata)
                    diarylist!!.adapter = adapter
                } else {
                    Snackbar.make(window.decorView.rootView, state.message, Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }
}
