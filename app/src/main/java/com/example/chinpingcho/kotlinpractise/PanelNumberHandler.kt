package com.example.chinpingcho.kotlinpractise

import com.example.chinpingcho.kotlinpractise.Constants.Companion.Operator

class PanelNumberHandler{

    interface DisplayCallback {
        fun setButtonState(op: Operator)
        fun setButtonCText(isButtonC:Boolean)
        fun setPanelText(txt:String)
    }

    private lateinit var mDisplayCallbackListener: DisplayCallback

    private var inputField: DoubleArray = DoubleArray(2)
    private var displayPointer = 0

    private var isOperatorTyped = false
    private var isCurrentPointTyped = false
    private var isTypingNumber = false
    private var isButtonC = false
    private var isEqualJustClicked = false
    private var currentIntDigit = 0
    private var currentFloatDigit = 0
    private var currentFloatDigitDegree = 1.0
    private var maxDisplayDigits = 9

    private var currentTypedOperator:Operator = Operator.UnTyped
    private var currentDisplayOperator:Operator = Operator.UnTyped

    fun setDisplayCallback(callback:DisplayCallback){
        mDisplayCallbackListener = callback
    }

    fun getCurrentDisplayOperator():Operator{
        return currentDisplayOperator
    }

    fun getCurrentButtonC():Boolean{
        return isButtonC
    }

    fun setUpPanelDisplay(){
        showNum(inputField[displayPointer])
    }

    private fun setUpButtonState(){
        currentDisplayOperator = when(isOperatorTyped){
            true -> currentTypedOperator
            false -> Operator.UnTyped
        }
        mDisplayCallbackListener.setButtonState(currentDisplayOperator)
    }

    private fun setUpButtonCText(){
        mDisplayCallbackListener.setButtonCText(isButtonC)
    }

    private fun setUpAllStates(){
        setUpPanelDisplay()
        setUpButtonState()
        setUpButtonCText()
    }

    fun clear(){

        if(!isTypingNumber) {
            displayPointer = 0
            inputField[0] = 0.0
            inputField[1] = 0.0
            isOperatorTyped = false
            currentTypedOperator = Operator.UnTyped
            currentDisplayOperator = Operator.UnTyped
            maxDisplayDigits = 9
        }

        clearCurrentDisplayState()
        isTypingNumber = false
        isButtonC = false
        isEqualJustClicked = false

        setUpAllStates()
    }

    private fun clearCurrentDisplayState(){
        isCurrentPointTyped = false
        inputField[displayPointer] = 0.0
        currentIntDigit = 0
        currentFloatDigit = 0
        currentFloatDigitDegree = 1.0
    }

    fun insertNumber(num: Int){
        if(isDigitFull() && !isOperatorTyped){
            return
        }

        initWhenDisplayNumberChanged()

        isTypingNumber = true
        isButtonC = true
        isEqualJustClicked = false

        if(!isCurrentPointTyped) {
            inputField[displayPointer] = inputField[displayPointer]*10 + num
            if(inputField[displayPointer] != 0.0) {
                currentIntDigit++
            }
        }
        else if(isCurrentPointTyped) {
            inputField[displayPointer] += num * currentFloatDigitDegree
            currentFloatDigit++
            currentFloatDigitDegree /= 10f
        }

        setUpAllStates()
    }

    fun insertPoint(){
        initWhenDisplayNumberChanged()
        if(!isCurrentPointTyped && !isDigitFull()){
            isCurrentPointTyped = true
            isTypingNumber = true
            isButtonC = true
            isEqualJustClicked = false
            currentFloatDigitDegree = 0.1
            currentIntDigit = Math.max(currentIntDigit, 1)
        }

        setUpAllStates()
    }

    private fun initWhenDisplayNumberChanged(){
        if(isOperatorTyped){
            isOperatorTyped = false
            displayPointer = 1
            clearCurrentDisplayState()
        }else if(isEqualJustClicked){
            clear()
        }
    }

    fun inverse(){
        inputField[displayPointer] *= -1.0
        setUpPanelDisplay()
    }

    fun percent(){
        inputField[displayPointer] /= 100.0
        setUpPanelDisplay()
    }

    fun insertOperator(op:Operator){
        if(!isOperatorTyped && isTypingNumber) {
            inputField[0] = calculate()
        }

        displayPointer = 0
        currentTypedOperator = op
        isOperatorTyped = true

        setUpAllStates()
    }

    fun insertEqual(){

        inputField[0] = calculate()
        displayPointer = 0
        isOperatorTyped = false
        isTypingNumber = false
        isEqualJustClicked = true

        setUpAllStates()
    }

    private fun calculate():Double{
        return when (currentTypedOperator) {
            Operator.Add -> inputField[0] + inputField[1]
            Operator.Minus -> inputField[0] - inputField[1]
            Operator.Multiple -> inputField[0] * inputField[1]
            Operator.Divide -> inputField[0] / inputField[1]
            else -> inputField[displayPointer]
        }
    }

    private fun isDigitFull(): Boolean{
        return currentIntDigit + currentFloatDigit >= maxDisplayDigits
    }

    private fun showNum(num: Double){

        //determine the number is shown equal to max digits
        val integerDigit = getIntegerDigit(num)
        var numString: String = when(integerDigit <= maxDisplayDigits){
            true -> shrinkDownZeroUntilFloatDigit(num.digitFormat(maxDisplayDigits - integerDigit))
            false -> java.lang.String.format("%.5e",num)
        }

        if( isCurrentPointTyped && currentFloatDigit == 0){
            numString += '.'
        }

        mDisplayCallbackListener.setPanelText(addCommaEveryThreeDigit(numString));
        //this.currentPanel.text = addCommaEveryThreeDigit(numString)
    }

    private fun getIntegerDigit(num: Double):Int{
        val numString = num.digitFormat(maxDisplayDigits - 1)
        val pointIndex = numString.indexOf('.')
        return when(pointIndex){
            -1 -> numString.length
            else -> pointIndex
        }
    }

    private fun shrinkDownZeroUntilFloatDigit(numString: String):String{
        println(numString)
        val pointIndex = numString.indexOf('.')
        if(pointIndex == -1){
            return numString
        }

        var zeroIterator = numString.length-1
        while(zeroIterator > pointIndex + currentFloatDigit   &&
                numString[zeroIterator] == '0' ||
                numString[zeroIterator] == '.'){
            zeroIterator--
        }
        return numString.substring(0, zeroIterator+1)
    }

    private fun addCommaEveryThreeDigit(numString: String):String{
        val pointIndex = numString.indexOf('.')
        return when(pointIndex != -1){
            true-> addCommaFromDigit(numString, numString.indexOf('.')-4)
            false-> addCommaFromDigit(numString, numString.length-4)
        }
    }

    private fun addCommaFromDigit(numString: String, digit: Int): String{
        var returnString = numString
        for(i in digit downTo 0 step 3) {
            if(returnString[i] != '-') {
                returnString = returnString.substring(0, i + 1) + ',' + returnString.substring(i + 1)
            }
        }
        return returnString
    }

    private fun Double.digitFormat(digits: Int) = java.lang.String.format("%.${digits}f", this)

}