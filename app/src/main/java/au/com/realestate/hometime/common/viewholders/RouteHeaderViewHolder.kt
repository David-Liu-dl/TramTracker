package au.com.realestate.hometime.common.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.realestate.domain.route.model.Direction
import au.com.realestate.hometime.R
import au.com.realestate.hometime.common.uimodels.RouteHeader
import butterknife.BindView
import butterknife.ButterKnife

class RouteHeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.direction_text_view)
    lateinit var directionTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
        itemView.tag = this
    }

    fun bind(routeHeader: RouteHeader) {
        when (routeHeader.direction) {
            Direction.North -> {
                directionTextView.text = itemView.context.getString(R.string.label_direction_north)
            }
            Direction.South -> {
                directionTextView.text = itemView.context.getString(R.string.label_direction_south)
            }
        }
    }

}