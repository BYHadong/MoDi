package byh.adong.modi

import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import byh.adong.modi.data.Diaries
import byh.adong.modi.data.DiariesGet
import byh.adong.modi.service.APIService
import byh.adong.modi.service.RetrofitService
import byh.adong.modi.util.CusTomBarUtil
import byh.adong.modi.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_write.*
import kotlinx.android.synthetic.main.custom_actionbar.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class WriteActivity : AppCompatActivity() {

    val MIN_CLICK_TIME = 600L

    lateinit var customlayoutview : View

    var mLastClickTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write)

        val custombar = CusTomBarUtil()
        custombar.creatcustombar(applicationContext, R.layout.custom_actionbar, supportActionBar)
        customlayoutview = custombar.getcustomlayoutview()

        customlayoutview.btnBack.setOnClickListener{
            Toast.makeText(applicationContext,"일기작성이 종료 되었습니다.", Toast.LENGTH_SHORT).show()
            finish()
        }
        customlayoutview.btnSelect.setOnClickListener {
            val currentClickTime = SystemClock.uptimeMillis()
            val elapsedTime = currentClickTime-mLastClickTime
            if (elapsedTime<=MIN_CLICK_TIME){
                return@setOnClickListener
            }
            mLastClickTime = currentClickTime
            finishdiary()
        }
        
        setContent.addTextChangedListener(object : TextWatcher {
            var str: String = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                str = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length > 2000) {
                    setContent.setText(str)
                    setContent.setSelection(start)
                } else
                    textSize.setText(s.length.toString())
            }
        })

    }

    fun finishdiary(){
        val time = SimpleDateFormat("yyyy/MM/dd/aa hh/mm").format(Date(System.currentTimeMillis()))
        val diary = Diaries(setWeather.text.toString(),setFeel.text.toString(), setContent.text.toString(), time)
        val token = SharedPreferenceUtil.getPreference(applicationContext)
        val apiService = RetrofitService().creatService(APIService :: class.java, token)
        val call = apiService.diarywrite(diary)
        call.enqueue(object : Callback<DiariesGet>{
            override fun onFailure(call: Call<DiariesGet>, t: Throwable) {
                Snackbar.make(window.decorView.rootView, "알 수 없는 오류가 발생했습니다.", Snackbar.LENGTH_SHORT).show()
                Log.d("WriteAcitivity", t!!.message)
            }

            override fun onResponse(call: Call<DiariesGet>, response: Response<DiariesGet>) {
                val status = response!!.body()!!.status
                if(status.success){
                    Toast.makeText(applicationContext, "일기 작성 완료", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, MainActivity::class.java))
                    finish()
                } else {
                    Snackbar.make(window.decorView.rootView, status.message, Snackbar.LENGTH_SHORT).show()
                }

            }
        })
    }
}
