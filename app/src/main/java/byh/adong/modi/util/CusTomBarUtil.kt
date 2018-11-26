package byh.adong.modi.util

import android.content.Context
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View

class CusTomBarUtil : AppCompatActivity() {

    var actionbarview : View? = null
    fun creatcustombar(context : Context, layout : Int, actionBar: ActionBar?){
        actionBar!!.setDisplayShowCustomEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(false)
        actionBar.setDisplayShowHomeEnabled(false)
        actionBar.setDisplayShowTitleEnabled(false)

        actionbarview = LayoutInflater.from(context).inflate(layout, null)
        actionBar.setCustomView(actionbarview)

        val parent : Toolbar = actionbarview!!.parent as Toolbar
        parent.setContentInsetsAbsolute(0, 0)
    }

    fun getcustomlayoutview() : View = actionbarview!!

}