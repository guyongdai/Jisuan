package com.yongdai.jishu;

import java.math.BigDecimal;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends ActionBarActivity {
 private TextView textView;
 private BigDecimal lastNum = new BigDecimal("0");
 private BigDecimal currentNum = new BigDecimal("0");
 private String currentText;
 private boolean typeClean = false;
 private boolean hadCalced = false;
 private MODE currentMode = MODE.PLUS;
 private enum MODE {
  PLUS, MINUS, MULTIPLY, DIVIDE
 }
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  textView = (TextView) findViewById(R.id.resultText);
 }
 @Override
 public boolean onCreateOptionsMenu(Menu menu) {
  getMenuInflater().inflate(R.menu.main, menu);
  return true;
 }
 public void onClickListener(View v) {
  currentText = textView.getText().toString();
  switch (v.getId()) {
  case R.id.button0:
   addNumber("0");
   break;
  case R.id.button1:
   addNumber("1");
   break;
  case R.id.button2:
   addNumber("2");
   break;
  case R.id.button3:
   addNumber("3");
   break;
  case R.id.button4:
   addNumber("4");
   break;
  case R.id.button5:
   addNumber("5");
   break;
  case R.id.button6:
   addNumber("6");
   break;
  case R.id.button7:
   addNumber("7");
   break;
  case R.id.button8:
   addNumber("8");
   break;
  case R.id.button9:
   addNumber("9");
   break;
  case R.id.buttonNegSign:
   addSign();
   break;
  case R.id.buttonClear:
   clear();
   break;
  case R.id.buttonBackSpace:
   backSpace();
   break;
  case R.id.buttonPlus:
   setMode(MODE.PLUS);
   break;
  case R.id.buttonMinus:
   setMode(MODE.MINUS);
   break;
  case R.id.buttonMultiply:
   setMode(MODE.MULTIPLY);
   break;
  case R.id.buttonDivide:
   setMode(MODE.DIVIDE);
   break;
  case R.id.buttonEval:
   calc();
   break;
  case R.id.buttonDot:
   addNumber(".");
   break;
  }
 }
 private void calc() {
  if (hadCalced && typeClean) return;
  currentNum = new BigDecimal(currentText);
  try {
   switch (currentMode) {
   case PLUS:
    lastNum = lastNum.add(currentNum);
    break;
   case MINUS:
    lastNum = lastNum.subtract(currentNum);
    break;
   case MULTIPLY:
    lastNum = lastNum.multiply(currentNum);
    break;
   case DIVIDE:
    lastNum = lastNum.divide(currentNum, 16,
      BigDecimal.ROUND_HALF_DOWN);
    break;
   }
  } catch (Exception e) {
   Toast.makeText(getApplicationContext(), "发生错误:"+e.getMessage(), Toast.LENGTH_SHORT).show();
   return;
  }
  String resultText = lastNum.toString();
  if (resultText.indexOf(".") > 0) { // 消除小数多余位数
   resultText = resultText.replaceAll("0+?$", "");
   resultText = resultText.replaceAll("[.]$", "");
  }
  setResultText(resultText);
  typeClean = true;
  hadCalced = false;
 }
 private void setMode(MODE mode) {
  if (!hadCalced && !typeClean) {
   calc();
   currentMode = mode;
   return;
  }
  lastNum = new BigDecimal(currentText);
  currentMode = mode;
  typeClean = true;
  hadCalced = true;
 }
 
 private void addSign() {
  if (currentText.equals("0") || currentText.equals("")) return;
  if (currentText.startsWith("-")) setResultText(currentText.substring(1));
  else setResultText("-" + currentText);
 }
 
 private void clear() {
  currentNum = new BigDecimal("0");
  lastNum = new BigDecimal("0");
  typeClean = false;
  currentMode = MODE.PLUS;
  setResultText("0");
 }
 
 private void setResultText(String string) {
  textView.setText(string);
 }
 public void addNumber(String num) {
  String resultText = currentText;
  if (typeClean == true) {
   resultText = "0";
   typeClean = false;
  }
  if (num.equals(".") && currentText.lastIndexOf(".") != -1) return;
  if ((resultText.equals("0") || resultText.equals(""))
    && !num.equals("."))
   resultText = "";
  resultText = resultText + num;
  setResultText(resultText);
 }
 
 private void backSpace() {
  if (currentText.equals("0") || currentText.equals("")) return;
  if (currentText.length() == 1) {
   setResultText("0");
   return;
  }
  setResultText(currentText.substring(0, currentText.length() - 1));
 }
 
 

}