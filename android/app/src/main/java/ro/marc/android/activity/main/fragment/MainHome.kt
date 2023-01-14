package ro.marc.android.activity.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.android.R
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.activity.main.MainVM
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.FragMainHomeBinding

class MainHome: Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var vm: MainVM
    private var _binding: FragMainHomeBinding? = null
    private val binding get() = _binding!!

    private var onItemClick: (Entity) -> Unit = {
        vm.entity = it
        activity.navigateToEdit()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        activity = requireActivity() as MainActivity
        val injectedViewModel by sharedViewModel<MainVM>()
        vm = injectedViewModel
        _binding = FragMainHomeBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            activity.navigateToEdit()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
