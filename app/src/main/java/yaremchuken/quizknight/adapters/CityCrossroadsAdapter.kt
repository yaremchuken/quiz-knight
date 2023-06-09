package yaremchuken.quizknight.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import yaremchuken.quizknight.GameStats
import yaremchuken.quizknight.OpponentType
import yaremchuken.quizknight.R
import yaremchuken.quizknight.activities.CityActivity
import yaremchuken.quizknight.databinding.ItemCityCrossroadsLevelBinding
import yaremchuken.quizknight.entity.ModuleLevelEntity

class CityCrossroadsAdapter(
    private val activity: CityActivity,
    val items: List<ModuleLevelEntity>
): RecyclerView.Adapter<CityCrossroadsAdapter.ViewHolder>() {

    class ViewHolder(binding: ItemCityCrossroadsLevelBinding): RecyclerView.ViewHolder(binding.root) {
        val viewHolder = binding.llCrossroadsLevelHolder
        val title = binding.tvCrossroadsLevelTitle
        val completedMark = binding.ivCrossroadsCompleted
        val portrait = binding.ivCrossroadsPortrait
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemCityCrossroadsLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewHolder.setOnClickListener {
            activity.launchLevel(items[position])
        }
        holder.title.text = "${position+1}. ${items[position].title}"
        holder.portrait.setImageResource(
            when (items[position].opponent) {
                OpponentType.GOBLIN -> R.drawable.ic_portrait_goblin
            }
        )
        val progress = GameStats.getInstance().progress[GameStats.getInstance().module] ?: 0
        holder.completedMark.visibility = if (position < progress) View.VISIBLE else View.INVISIBLE
    }
}