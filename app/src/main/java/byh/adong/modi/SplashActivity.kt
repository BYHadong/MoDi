package byh.adong.modi

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import byh.adong.modi.util.SharedPreferenceUtil

class SplashActivity : AppCompatActivity() {
    val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val nwi = getnetworkcheck()
        Log.d(TAG, (nwi != null && nwi.isConnected).toString())
        if (nwi != null && nwi.isConnected){
            val tokenget = SharedPreferenceUtil.getPreference(applicationContext)
            Handler().postDelayed({
                if (tokenget.equals("")) startActivity(Intent(applicationContext, LoginActivity :: class.java))
                else startActivity(Intent(applicationContext, MainActivity :: class.java))
                finish()
            }, 1200)
        } else {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("네트워크 연결 상태 확인")
            alertDialog.setMessage("네트워크를 연결하고 다시 시행시켜 주세요.")
            alertDialog.setIcon(R.drawable.warning)
            alertDialog.setPositiveButton("확인", { dialog, which ->
                finish()
            })
            alertDialog.show()
        }
    }

    fun getnetworkcheck() : NetworkInfo? {
        val ccm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nwi = ccm.activeNetworkInfo
        return nwi
    }
}
