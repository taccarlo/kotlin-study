package com.taccarlo.kotlinrequestapi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.taccarlo.kotlinrequestapi.data.LinkedinRepository

class MainFragmentViewModel : ViewModel() {
    private val linkedinList: MutableLiveData<List<LinkedinRepository>> by lazy {
        MutableLiveData<List<LinkedinRepository>>().also{
            loadLinkedinList()
        }
    }

    fun getLinkedinList(): LiveData<List<LinkedinRepository>> {
        return linkedinList
    }

    private fun loadLinkedinList(){
        //do an asynchronous operation to fetch linkedin repositories
      /*  val a = LinkedinRepository("carlo","ciao","ciao@ciao.it","www.ciao.it")
        val b = LinkedinRepository("filippo","salve","salve@ciao.it","www.salve.it")

        linkedinList.value = listOf(a, b, a, b);*/
    }
}