package ro.marc.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import ro.marc.android.util.NetworkUtils

open class CoreActivity(
    private val layoutResId: Int,
): AppCompatActivity() {

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(layoutResId)

        NetworkUtils.init(this)
    }

    open fun onNetworkReconnected() {
        println("onNetworkReconnected not implemented")
    }

    open fun onNetworkConnected() {
        println("onNetworkConnected not implemented")
    }
    open fun onNetworkDisconnected() {
        println("onNetworkDisconnected not implemented")
    }

}
