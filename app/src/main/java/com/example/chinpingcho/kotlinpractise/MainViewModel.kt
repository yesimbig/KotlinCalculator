package com.example.chinpingcho.kotlinpractise

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.chinpingcho.kotlinpractise.Constants.Companion.Operator

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val mPanelNumber : PanelNumberHandler by lazy { PanelNumberHandler() }

    fun getPanelNumberData(callback: PanelNumberHandler.DisplayCallback): PanelNumberHandler{
        mPanelNumber.setDisplayCallback(callback)
        return mPanelNumber
    }

}
