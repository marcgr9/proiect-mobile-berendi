package ro.marc.android.activity.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.activity.main.MainVM
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.FragMainEditBinding
import ro.marc.android.util.NetworkUtils
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


        configureLayout()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun configureLayout() {
        when (vm.entity) {
            null -> {
                binding.save.setOnClickListener {
                    when (NetworkUtils.hasNetwork) {
                        true -> {
                            // todo
                        }
                        false -> {
                            vm.saveLocalEntity(Entity(-1, -1, binding.name.text.toString(), binding.quantity.text.toString().toInt(), Utils.getDateFromString(binding.date.text.toString())!!, binding.favourite.isChecked))
                            activity.navigateToHome()
                        }
                    }
                }
            }
            else -> {
                binding.name.setText(vm.entity!!.name)
                binding.quantity.setText(vm.entity!!.quantity.toString())
                binding.date.setText(Utils.formatDate(vm.entity!!.date))
                binding.favourite.isChecked = vm.entity!!.isFavourite
                binding.save.text = "Update"

                binding.save.setOnClickListener {
                    when (NetworkUtils.hasNetwork) {
                        true -> {
                            // todo
                        }
                        false -> {
                            vm.update(vm.entity!!.localId!!, Entity(vm.entity!!.localId, -1, binding.name.text.toString(), binding.quantity.text.toString().toInt(), Utils.getDateFromString(binding.date.text.toString())!!, binding.favourite.isChecked))
                            activity.navigateToHome()
                        }
                    }
                }
            }
        }
    }

}
