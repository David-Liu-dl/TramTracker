package au.com.realestate.hometime.timetable

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TimetableViewModel(
    initialFragmentTag: String
): ViewModel(){

    val currentFragmentTag = MutableLiveData<String>().apply { value = initialFragmentTag }

}