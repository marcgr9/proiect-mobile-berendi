package ro.marc.android.activity.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.activity.main.MainVM
import ro.marc.android.databinding.FragMainEditBinding
import ro.marc.android.util.Utils

class MainEdit: Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var vm: MainVM
    private var _binding: FragMainEditBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        activity = requireActivity() as MainActivity
        val injectedViewModel by sharedViewModel<MainVM>()
        vm = injectedViewModel
        _binding = FragMainEditBinding.inflate(inflater, container, false)

        if (vm.entity != null) {
            binding.name.setText(vm.entity!!.name)
            binding.quantity.setText(vm.entity!!.quantity)
            binding.date.setText(Utils.formatDate(vm.entity!!.date))
            binding.favourite.isChecked = vm.entity!!.isFavourite
            binding.save.text = "Update"
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}
