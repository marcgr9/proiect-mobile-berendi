package ro.marc.android.activity.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import ro.marc.android.CoreActivity
import ro.marc.android.R
import ro.marc.android.util.Utils

class MainActivity: CoreActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navController) as NavHostFragment
        navController = navHostFragment.navController

        findViewById<Button>(R.id.logout).setOnClickListener {
            Utils.logout(this)
        }
    }

    override fun onBackPressed() {
        startActivity(
            Intent(Intent.ACTION_MAIN).apply {
                addCategory(Intent.CATEGORY_HOME)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }

    fun navigateToEdit() {
        navController!!.navigate(R.id.action_mainHome_to_mainEdit)
    }

    fun navigateToHome() {
        navController!!.navigate(R.id.action_mainEdit_to_mainHome)
    }

}