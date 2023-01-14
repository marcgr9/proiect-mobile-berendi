package ro.marc.android.activity

import androidx.lifecycle.ViewModel
import ro.marc.android.data.UserRepo

class LoginVM(
    private val userRepo: UserRepo
): ViewModel() {

    fun me() = userRepo.me()

    fun login(username: String, password: String) = userRepo.login(username, password)

}