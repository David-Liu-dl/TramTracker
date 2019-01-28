package au.com.realestate.hometime.common.viewholders

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import au.com.realestate.domain.route.model.Route
import au.com.realestate.hometime.R
import au.com.realestate.infra.extensions.diffWithCurrentTimeInMinutes
import butterknife.BindView
import butterknife.ButterKnife

class RouteItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    @BindView(R.id.tram_id_text_view)
    lateinit var tramIdTextView: TextView

    @BindView(R.id.special_event_text_view)
    lateinit var specialEventTextView: TextView

    @BindView(R.id.direction_text_view)
    lateinit var directionTextView: TextView

    @BindView(R.id.time_remain_text_view)
    lateinit var timeRemainTextView: TextView

    init {
        ButterKnife.bind(this, itemView)
        itemView.tag = this
    }

    fun bind(item: Route, routeClickCallback: (selectedVehicleId: Int) -> Unit) {
        itemView.apply {
            setOnClickListener {
                routeClickCallback(item.vehicleId)
            }
        }
        tramIdTextView.text = itemView.context.getString(R.string.label_tram_id, item.routeNo)
        directionTextView.text = item.destination
        timeRemainTextView.text = itemView.context.getString(R.string.label_time_remain, item.getFormattedArrivalDateTime().diffWithCurrentTimeInMinutes())

        if (item.hasSpecialEvent){
            specialEventTextView.visibility = View.VISIBLE
            specialEventTextView.text = item.specialEventMessage
        }else{
            specialEventTextView.visibility = View.GONE
        }
    }
}