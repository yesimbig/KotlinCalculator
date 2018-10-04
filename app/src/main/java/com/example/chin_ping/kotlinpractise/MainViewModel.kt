package com.example.chin_ping.kotlinpractise

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.example.chin_ping.kotlinpractise.PanelNumberHandler.Operator as Operator

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mPanelNumber:PanelNumberHandler? = null
    private var mTextSize: Float = 0f
    private var mOperator: Operator = Operator.UnTyped
    private var mIsButtonC: Boolean = false


    fun getPanelNumberData(): PanelNumberHandler{
        if(mPanelNumber == null){
            mPanelNumber = PanelNumberHandler()
        }
        return mPanelNumber as PanelNumberHandler
    }

    fun getTextSize(): Float{
        return mTextSize
    }

    fun getOperator(): Operator{
        return mOperator
    }

    fun getIsButtonC(): Boolean{
        return mIsButtonC
    }

    fun setTextSize(textSize:Float){
        mTextSize = textSize
    }

    fun setOperator(operator: Operator){
        mOperator = operator
    }

    fun setIsButtonC(isButtonC: Boolean){
        mIsButtonC = isButtonC
    }

}
