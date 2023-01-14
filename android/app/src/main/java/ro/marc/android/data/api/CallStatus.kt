package ro.marc.android.data.api

import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity


sealed class CallStatus<T>(val businessPayload: BusinessPayload<T>?) {
    
    class Success<T>(businessPayload: BusinessPayload<T>) : CallStatus<T>(businessPayload)

    class Loading<T> : CallStatus<T>(null)

    class Error<T>(businessPayload: BusinessPayload<T>) : CallStatus<T>(businessPayload)

    companion object {

        data class LayoutAffectedByApiCall(
            val activity: AppCompatActivity,
            var progressBar: ProgressBar? = null,
            var disappearingViewsWhileLoading: List<View>? = null,
        )

        fun manageCallStatus(
            callStatus: CallStatus<*>,
            layout: LayoutAffectedByApiCall,
        ): Boolean {
            when (callStatus) {
                is Loading -> {
                    layout.disappearingViewsWhileLoading?.forEach {
                        it.visibility = View.INVISIBLE
                    }
                    layout.progressBar?.visibility = View.VISIBLE
                }
                is Success -> {
                    layout.disappearingViewsWhileLoading?.forEach {
                        it.visibility = View.VISIBLE
                    }
                    layout.progressBar?.visibility = View.INVISIBLE
                }
                is Error -> {
                    layout.disappearingViewsWhileLoading?.forEach {
                        it.visibility = View.VISIBLE
                    }
                    layout.progressBar?.visibility = View.INVISIBLE

//                    Toast.makeText(layout.activity, "No internet", Toast.LENGTH_LONG).show()
                }
            }

            return true
        }
    }
}
