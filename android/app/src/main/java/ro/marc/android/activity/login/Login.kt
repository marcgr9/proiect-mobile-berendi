package ro.marc.android.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import org.koin.androidx.viewmodel.ext.android.viewModel
import ro.marc.android.CoreActivity
import ro.marc.android.R
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.data.api.CallStatus
import ro.marc.android.data.api.CallStatus.Companion.LayoutAffectedByApiCall
import ro.marc.android.util.Utils

class Login: CoreActivity(R.layout.activity_login) {

    private val vm: LoginVM by viewModel()
    private var isLoading = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashscreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashscreen.setKeepOnScreenCondition { isLoading }

        val layout = LayoutAffectedByApiCall(
            this,
        )
        vm.me().observe(this) {
            if (CallStatus.manageCallStatus(it, layout) && it is CallStatus.Success) {
                isLoading = false
                startActivity(Intent(this, MainActivity::class.java))
            } else if (it is CallStatus.Error){
                isLoading = false
                initialize()
            }
        }

    }

    private fun initialize() {
        val layout = LayoutAffectedByApiCall(
            this,
        )
        findViewById<Button>(R.id.login).setOnClickListener {
            vm.login(findViewById<EditText>(R.id.username).text.toString(), findViewById<EditText>(R.id.password).text.toString()).observe(this) {
                if (CallStatus.manageCallStatus(it, layout) && it is CallStatus.Success) {
                    val payload = it.businessPayload!!.payload
                    if (payload != null) {
                        Utils.toast(this, payload.jwt)
                        getSharedPreferences("app", Context.MODE_PRIVATE).edit()
                            .putString("JWT", payload.jwt)
                            .commit()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Utils.toast(this, "Bad credentials")
                    }
                }
            }
        }
    }

}