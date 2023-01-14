package ro.marc.android.activity.main.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.marc.android.data.model.Entity
import ro.marc.android.databinding.CompItemBinding
import ro.marc.android.util.Utils

class EntityAdapter(
    private val onClick: (Entity) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var _entities: MutableList<Entity> = mutableListOf()

    val entities: List<Entity>
        get() = _entities

    @SuppressLint("NotifyDataSetChanged")
    fun addEntities(entities: List<Entity>) {
        this._entities.addAll(entities)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearEntities() {
        this._entities.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val compTranItemBinding = CompItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(compTranItemBinding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as RequestViewHolder).bind(_entities[position])
        holder.itemView.setOnClickListener {
            onClick(_entities[position])
        }
    }

    override fun getItemCount(): Int = _entities.size

    fun setIdOf(localId: Long, id: Long) {
        _entities.find {
            it.localId == localId
        }?.id = id
    }

    class RequestViewHolder(private val entityItem: CompItemBinding) : RecyclerView.ViewHolder(entityItem.root) {

        fun bind(entity: Entity) {
            Utils.fillEntityCard(entityItem, entity)
        }
    }
}
