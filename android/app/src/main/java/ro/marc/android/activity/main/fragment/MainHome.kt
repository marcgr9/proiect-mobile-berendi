package ro.marc.android.activity.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.android.R
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.activity.main.MainVM
import ro.marc.android.activity.main.adapter.EntityAdapter
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.FragMainHomeBinding
import ro.marc.android.util.NetworkUtils

class MainHome: Fragment() {

    private lateinit var activity: MainActivity
    private lateinit var vm: MainVM
    private var _binding: FragMainHomeBinding? = null
    private val binding get() = _binding!!

    private var onItemClick: (Entity) -> Unit = {
        vm.entity = it
        activity.navigateToEdit()
    }

    private val entitiesAdapter = EntityAdapter(onItemClick)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        activity = requireActivity() as MainActivity
        val injectedViewModel by sharedViewModel<MainVM>()
        vm = injectedViewModel
        _binding = FragMainHomeBinding.inflate(inflater, container, false)

        binding.post.setOnClickListener {
            activity.navigateToEdit()
        }

        binding.rv.apply {
            adapter = entitiesAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        vm.entity = null
        populate()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun populate() {
        when (NetworkUtils.hasNetwork) {
            true -> {
                // todo
            }
            false -> {
                entitiesAdapter.clearEntities()
                entitiesAdapter.addEntities(vm.getLocalEntities())
            }
        }
    }

}
