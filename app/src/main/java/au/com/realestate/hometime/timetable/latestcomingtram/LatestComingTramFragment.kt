package au.com.realestate.hometime.timetable.latestcomingtram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import au.com.realestate.hometime.R

class LatestComingTramFragment: Fragment(){
    companion object {
        val TAG: String = LatestComingTramFragment:: class.java.simpleName

        fun newInstance(): LatestComingTramFragment {
            return LatestComingTramFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_latest_coming_tram, container, false)
    }
}