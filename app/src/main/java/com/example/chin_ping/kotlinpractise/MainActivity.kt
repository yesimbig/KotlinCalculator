package com.example.chin_ping.kotlinpractise

import android.arch.lifecycle.ViewModelProviders
import android.content.res.Configuration
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.chin_ping.kotlinpractise.PanelNumberHandler.Operator as Operator

class MainActivity : AppCompatActivity(), CalculateButtons.OnButtonClicked {

    private lateinit var mPanelNumber:PanelNumberHandler
    private lateinit var mViewModel:MainViewModel
    private lateinit var mCalculateButtons: CalculateButtons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        initView()
    }

    private fun setupViewModel(){
        mViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        mPanelNumber = mViewModel.getPanelNumberData()
        mPanelNumber.setCurrentPanel(findViewById(R.id.calculatePanel))
        mPanelNumber.setUpPanelDisplay()

        mViewModel.setTextSize(determineTextSize(this.resources.configuration.orientation))
        mViewModel.setOperator(mPanelNumber.getCurrentDisplayOperator())
        mViewModel.setIsButtonC(mPanelNumber.getCurrentButtonC())
    }

    private fun initView() {
        val transaction = supportFragmentManager.beginTransaction()
        //mCalculateButtons = CalculateButtons.newInstance(mViewModel)
        mCalculateButtons = CalculateButtons()

        transaction.replace(R.id.calculateButtonsFragment, mCalculateButtons)
                .commit()

        mPanelNumber.setCalculateButtonController(mCalculateButtons)
    }

    private fun determineTextSize(orientation: Int): Float{
        return when( orientation){
            Configuration.ORIENTATION_LANDSCAPE -> Constants.TEXT_SIZE_LANDSCAPE
            Configuration.ORIENTATION_PORTRAIT -> Constants.TEXT_SIZE_PORTRAIT
            else -> -1f
        }
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
            R.id.buttonPoint -> mPanelNumber.insertPoint()
            R.id.buttonInverse -> mPanelNumber.inverse()
            R.id.buttonP -> mPanelNumber.percent()
            R.id.buttonAdd -> mPanelNumber.insertOperator(Operator.Add)
            R.id.buttonMinus -> mPanelNumber.insertOperator(Operator.Minus)
            R.id.buttonMultiple -> mPanelNumber.insertOperator(Operator.Multiple)
            R.id.buttonDivide -> mPanelNumber.insertOperator(Operator.Divide)
            R.id.buttonEqual -> mPanelNumber.insertEqual()
            R.id.buttonC -> mPanelNumber.clear()
            else -> return
        }
    }

}
