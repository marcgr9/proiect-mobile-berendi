package ro.marc.android.activity.login

import androidx.lifecycle.ViewModel
import ro.marc.android.data.repo.UserRepo

class LoginVM(
    private val userRepo: UserRepo
): ViewModel() {

    fun me() = userRepo.me()

    fun login(username: String, password: String) = userRepo.login(username, password)

}