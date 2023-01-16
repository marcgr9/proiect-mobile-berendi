package ro.marc.android.activity.main.fragment

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import ro.marc.android.R
import ro.marc.android.activity.main.MainActivity
import ro.marc.android.activity.main.MainVM
import ro.marc.android.activity.main.adapter.EntityAdapter
import ro.marc.android.data.api.CallStatus
import ro.marc.android.data.api.CallStatus.Companion.LayoutAffectedByApiCall
import ro.marc.android.data.api.dto.EntityDTO
import ro.marc.android.data.db.entity.LocalEntityStatus
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.FragMainHomeBinding
import ro.marc.android.util.NetworkUtils
import ro.marc.android.util.Utils

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

        binding.camera.setOnClickListener {
            activity.dispatchTakePictureIntent()
        }

        binding.rv.apply {
            adapter = entitiesAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        vm.entity = null
        populate()
        attachReconnectedListener()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    private fun populate() {
        when (NetworkUtils.hasNetwork) {
            true -> {
                vm.getEntities().observe(viewLifecycleOwner) {
                    if (CallStatus.manageCallStatus(it, LayoutAffectedByApiCall(activity)) && it is CallStatus.Success) {
                        vm.clearLocalEntities()
                        entitiesAdapter.clearEntities()
                        it.businessPayload!!.payload!!.forEach { dto ->
                            vm.saveLocalEntity(Utils.asEntity(dto), LocalEntityStatus.COMMITTED)
                        }
                        entitiesAdapter.addEntities(vm.getLocalEntities())
                    }
                }
            }
            false -> {
                entitiesAdapter.clearEntities()
                entitiesAdapter.addEntities(vm.getLocalEntities())
            }
        }
    }

    private fun attachReconnectedListener() {

        NetworkUtils.reconnectedLiveData.observe(viewLifecycleOwner) {
            if (it == false) return@observe

            Utils.toast(activity, "Reconnected, pushing updates to server.")
            val uncommitted = vm.getUncommitted()
            val updated = vm.getUpdated()

            uncommitted.forEach { dbEntity ->
                vm.postEntity(EntityDTO(
                    null,
                    dbEntity.name,
                    dbEntity.quantity,
                    dbEntity.date,
                    dbEntity.isFavourite,
                )).observe(viewLifecycleOwner) {
                    if (CallStatus.manageCallStatus(it, LayoutAffectedByApiCall(activity)) && it is CallStatus.Success) {
                        val id = it.businessPayload!!.payload!!.id!!

                        vm.setCommitted(dbEntity.localId)
                        vm.setIdToLocalEntity(id, dbEntity.localId)
                        entitiesAdapter.setIdOf(dbEntity.localId, id)

                        showNotification()
                    }
                }
            }

            updated.forEach { dbEntity ->
                vm.patch(EntityDTO(
                    null,
                    dbEntity.name,
                    dbEntity.quantity,
                    dbEntity.date,
                    dbEntity.isFavourite,
                ), dbEntity.id!!).observe(viewLifecycleOwner) {
                    if (CallStatus.manageCallStatus(it, LayoutAffectedByApiCall(activity)) && it is CallStatus.Success) {
                        vm.setCommitted(dbEntity.localId)
                    }
                }
            }
        }

    }

    private fun showNotification() {
        val channel = NotificationChannel("1", "channel", NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "description"
        }
        val notificationManager = activity.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(activity, "1")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Synchronized with the server")
            .setContentText("One item was pushed to the server")
            .setStyle(
                NotificationCompat.BigTextStyle()
                .bigText("One item was pushed to the server"))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(activity)) {
            notify(1, builder.build())
        }
    }

}
