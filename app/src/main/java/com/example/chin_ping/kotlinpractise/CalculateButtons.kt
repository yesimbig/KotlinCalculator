package com.example.chin_ping.kotlinpractise

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.chin_ping.kotlinpractise.PanelNumberHandler.Operator as Operator

class CalculateButtons : Fragment() {

    interface OnButtonClicked {
        fun onButtonClicked(buttonId: Int)
    }

    private lateinit var mViewModel: MainViewModel
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
        val view = inflater.inflate(R.layout.calculator_buttons, container, false)

        this.myViewParent = view.findViewById(R.id.guild1)
        mViewModel = ViewModelProviders.of(this.activity!!).get(MainViewModel::class.java)
        setTextSize(mViewModel.getTextSize())
        setButtonState( mViewModel.getOperator())
        setButtonCText(mViewModel.getIsButtonC())

        bindViewsButtonsListener()

        return view
    }

    private fun setTextSize(size:Float){
        for(i in 0..myViewParent.childCount){
            val v : View? = myViewParent.getChildAt(i)
            if(v is Button){
                v.setTextSize(TypedValue.COMPLEX_UNIT_SP, size  )
            }
            else if(v is TextView){
                v.setTextSize(TypedValue.COMPLEX_UNIT_SP, size  )
            }
        }
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
        val buttonC: Button = myViewParent.findViewById(R.id.buttonC)
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
            Operator.Add -> myViewParent.findViewById(R.id.buttonAdd)
            Operator.Minus -> myViewParent.findViewById(R.id.buttonMinus)
            Operator.Multiple -> myViewParent.findViewById(R.id.buttonMultiple)
            Operator.Divide -> myViewParent.findViewById(R.id.buttonDivide)
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