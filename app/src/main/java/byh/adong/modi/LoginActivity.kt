package byh.adong.modi

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import byh.adong.modi.data.User
import byh.adong.modi.data.UserGet
import byh.adong.modi.service.APIService
import byh.adong.modi.service.RetrofitService
import byh.adong.modi.util.SharedPreferenceUtil
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "LoginActivity"

    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        view = window.decorView.rootView
        loginButton.setOnClickListener(this)
        loginSignUpButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val lID = loginId.text.toString()
        val lPasswd = loginPasswd.text.toString()
        val user = User(lID, lPasswd)
        when (v!!.id) {
            R.id.loginButton -> {
                val apiservice = RetrofitService().creatService(APIService::class.java)
                val call = apiservice.login(user)
                call.enqueue(object : Callback<UserGet> {
                    override fun onFailure(call: Call<UserGet>, t: Throwable) {
                        Snackbar.make(view, "알수 없는 오류가 발생했습니다.", Snackbar.LENGTH_LONG).show()
                        Log.d(TAG, t!!.message)
                    }

                    override fun onResponse(call: Call<UserGet>, response: Response<UserGet>) {
                        val status = response!!.body()!!.status
                        if (status.success) {
                            Toast.makeText(this@LoginActivity, "로그인 성공", Toast.LENGTH_SHORT).show()
                            val token = response.body()!!.token.data
                            SharedPreferenceUtil.savePreference(this@LoginActivity, token)
                            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                            finish()
                        } else {
                            Snackbar.make(view, status.message, Snackbar.LENGTH_LONG).show()
                        }
                    }

                })
            }
            R.id.loginSignUpButton -> {
                startActivity(Intent(this@LoginActivity, SignUpActivity::class.java))
                finish()
            }
        }
    }
}
