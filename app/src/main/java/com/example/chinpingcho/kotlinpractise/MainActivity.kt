package com.example.chinpingcho.kotlinpractise

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.chinpingcho.kotlinpractise.Constants.Companion.Operator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ButtonsFragment.OnButtonClicked, PanelNumberHandler.DisplayCallback {

    private lateinit var mPanelNumber:PanelNumberHandler
    private lateinit var mViewModel:MainViewModel
    private lateinit var mButtonsFragment: ButtonsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        initView()
    }

    private fun setupViewModel(){
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mPanelNumber = mViewModel.getPanelNumberData(this)
    }

    private fun initView() {
        mButtonsFragment = buttons_panel as ButtonsFragment
        mPanelNumber.setUpPanelDisplay()
        setButtonState(mPanelNumber.getCurrentDisplayOperator())
        setButtonCText(mPanelNumber.getCurrentButtonC())
    }

    override fun onButtonClicked(buttonId: Int) {

        when(buttonId){
            R.id.button0 -> mPanelNumber.insertNumber(0)
            R.id.button1 -> mPanelNumber.insertNumber(1)
            R.id.button2 -> mPanelNumber.insertNumber(2)
            R.id.button3 -> mPanelNumber.insertNumber(3)
            R.id.button4 -> mPanelNumber.insertNumber(4)
            R.id.button5 -> mPanelNumber.insertNumber(5)
            R.id.button6 -> mPanelNumber.insertNumber(6)
            R.id.button7 -> mPanelNumber.insertNumber(7)
            R.id.button8 -> mPanelNumber.insertNumber(8)
            R.id.button9 -> mPanelNumber.insertNumber(9)
            R.id.button_point -> mPanelNumber.insertPoint()
            R.id.button_inverse -> mPanelNumber.inverse()
            R.id.button_percent -> mPanelNumber.percent()
            R.id.button_add -> mPanelNumber.insertOperator(Operator.Add)
            R.id.button_minus -> mPanelNumber.insertOperator(Operator.Minus)
            R.id.button_multiple -> mPanelNumber.insertOperator(Operator.Multiple)
            R.id.button_divide -> mPanelNumber.insertOperator(Operator.Divide)
            R.id.button_equal -> mPanelNumber.insertEqual()
            R.id.button_c -> mPanelNumber.clear()
            else -> return
        }
    }

    override fun setButtonCText(isButtonC: Boolean) {
        mButtonsFragment.setButtonCText(isButtonC)
    }

    override fun setButtonState(op: Operator) {
        mButtonsFragment.setButtonState(op)
    }

    override fun setPanelText(txt: String) {
        calculate_panel.text = txt
    }

}
