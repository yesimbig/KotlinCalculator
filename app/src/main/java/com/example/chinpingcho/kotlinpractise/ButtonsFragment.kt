package com.example.chinpingcho.kotlinpractise

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.chinpingcho.kotlinpractise.Constants.Companion.Operator
import kotlinx.android.synthetic.main.calculator_buttons.*

class ButtonsFragment : Fragment(){

    interface OnButtonClicked {
        fun onButtonClicked(buttonId: Int)
    }

    private lateinit var myViewParent: ConstraintLayout
    private lateinit var buttonClickedListener: OnButtonClicked


    override fun onAttach(context:Context){
        super.onAttach(context)
        if(context is OnButtonClicked){
            buttonClickedListener = context
        }else {
            throw ClassCastException(context.toString() + " must implement OnButtonClicked.")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calculator_buttons, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.myViewParent = guild1

        bindViewsButtonsListener()
    }

    private fun bindViewsButtonsListener(){
        for(i in 0..myViewParent.childCount) {
            val v: View? = myViewParent.getChildAt(i)
            if (v is Button) {
                v.setOnClickListener{
                    buttonClickedListener.onButtonClicked(v.id)
                }
            }
        }
    }

    fun setButtonCText(ac: Boolean){
        val buttonC: Button = button_c
        buttonC.setText (when (ac) {
                false -> R.string.buttonTextAC
                true -> R.string.buttonTextC
            })
    }

    fun setButtonState(op: Operator){

        for( i in Operator.values()){
            val btn: Button? = buttonSelector(i) ?: continue
            if(i == op ){
                setButtonOn(btn!!)
            }
            else{
                setButtonOff(btn!!)
            }
        }
    }

    private fun buttonSelector(op: Operator):Button?{
        return when(op){
            Operator.Add -> button_add
            Operator.Minus -> button_minus
            Operator.Multiple -> button_multiple
            Operator.Divide -> button_divide
            else -> return null
        }
    }

    private fun setButtonOn(btn: Button){
        btn.setTextColor(Color.parseColor("#ff8800"))
        btn.setBackgroundResource(R.drawable.btn_white)
    }

    private fun setButtonOff(btn: Button){
        btn.setTextColor(Color.parseColor("#ffffff"))
        btn.setBackgroundResource(R.drawable.btn_orange)
    }
}