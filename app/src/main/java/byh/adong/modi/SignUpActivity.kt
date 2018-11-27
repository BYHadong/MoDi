package byh.adong.modi

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import byh.adong.modi.data.Status
import byh.adong.modi.data.User
import byh.adong.modi.service.APIService
import byh.adong.modi.service.RetrofitUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "SignUpActivity"
    lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        view = window.decorView.rootView
        signUpButton.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val sId = signUpId.text.toString()
        val sPasswd = signUpPasswd.text.toString()
        val sRePasswd = reSignUpPasswd.text.toString()
        if (sPasswd.equals(sRePasswd)){
            val user = User(sId, sPasswd)
            val apiservice = RetrofitUtil.creatService(APIService::class.java)
            val call = apiservice.signup(user)
            call.enqueue(object : Callback<Status>{
                override fun onFailure(call: Call<Status>, t: Throwable) {
                    Snackbar.make(view, "알 수 없는 오류가 발생 했습니다.", Snackbar.LENGTH_SHORT).show()
                    Log.d(TAG, t.message)
                }

                override fun onResponse(call: Call<Status>, response: Response<Status>) {
                    val status = response.body()!!.status
                    if (status.success) {
                        Toast.makeText(this@SignUpActivity, "회원가입 완료", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    } else {
                        Snackbar.make(view, status.message, Snackbar.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            Snackbar.make(view, "비밀번호가 다릅니다.", Snackbar.LENGTH_SHORT).show()
        }
    }
}
