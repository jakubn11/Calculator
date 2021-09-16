package com.example.kalkulacka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnDel, btnEquals, btnPlusMinus, btnMultiply, btnDivide, btnRecent, btnPi, btnComma;
    private EditText result;
    private ArrayList<Integer> numbers;
    private ArrayList<Integer> numbersTemp;
    private ArrayList<Integer> numbersTemp2;
    private ArrayList<String> history;
    private ArrayList<String> operatory;
    private ArrayList<String> operatoryTemp;
    private int res, resultMultiply, first, second, indexOfSecond, indexOfFirst;
    private boolean stav = false;
    private boolean endResult = false;
    private boolean vysledekBool = false;
    private boolean deleteHistory;
    private boolean cancel = true;
    private TextView meziResult;
    private String vysledek, historyText;
    private MenuItem historyItem;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn0 = findViewById(R.id.button0);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btn6 = findViewById(R.id.button6);
        btn7 = findViewById(R.id.button7);
        btn8 = findViewById(R.id.button8);
        btn9 = findViewById(R.id.button9);
        btnMinus = findViewById(R.id.buttonSubtract);
        btnPlus = findViewById(R.id.buttonPlus);
        btnDel = findViewById(R.id.buttonDelete);
        btnEquals = findViewById(R.id.buttonequals);
        btnPlusMinus = findViewById(R.id.buttonPlusMinus);
        btnMultiply = findViewById(R.id.buttonMultiply);
        btnDivide = findViewById(R.id.buttonDivide);
        btnRecent = findViewById(R.id.buttonDeleteRecent);
        btnPi = findViewById(R.id.buttonPí);
        btnComma = findViewById(R.id.buttonComma);
        result = findViewById(R.id.result);
        meziResult = findViewById(R.id.meziResult);

        numbers = new ArrayList<>();
        history = new ArrayList<>();
        operatory = new ArrayList<>();
        numbersTemp = new ArrayList<>();
        operatoryTemp = new ArrayList<>();
        numbersTemp2 = new ArrayList<>();

        result.setHint("0");

        getSupportActionBar().setTitle("Kalkulačka");

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stav) {
                    result.setText("0");
                    stav = false;
                } else {
                    String word = result.getText().toString();
                    if (word.length() > 0) {
                        if (word.charAt(0) != '0') {
                            result.setText(result.getText() + "0");
                        }
                    } else {
                        result.setText("0");
                    }
                }
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("1");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("2");
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("3");
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("4");
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("5");
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("6");
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("7");
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("8");
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funkce("9");
            }
        });

        btnPi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.setText("3,14159 26535 89793 23846 26433 83279 5");
            }
        });

        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endResult = false;
                result.setText("");
                meziResult.setText("");
                numbers.clear();
                operatory.clear();
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vysledekBool = false;
                String word = result.getText().toString();
                char[] chrs = word.toCharArray();

                if (word.length() > 0) {
                    char lastChar = chrs[word.length() - 1];
                    if (lastChar != '-') {
                        if (lastChar != '+') {
                            if (lastChar != '*') {
                                if (lastChar != '/') {
                                    stav = true;
                                    int a = Integer.parseInt(result.getText().toString());
                                    result.setText(result.getText());
                                    numbers.add(a);
                                    operatory.add("+");
                                    String q = meziResult.getText().toString();
                                    meziResult.setText(q + result.getText() + "+");
                                }
                            }
                        }
                    } else {

                    }
                }


                if (endResult == true) {
                    meziResult.setText(result.getText() + "+");
                    endResult = false;
                }

                String word2 = meziResult.getText().toString();
                char[] chrs2 = word2.toCharArray();

                if (word2.length() > 0) {
                    char lastChar2 = chrs2[word2.length() - 1];
                    if (lastChar2 == '-' || lastChar2 == '*' || lastChar2 == '/') {
                        if (result.getText().toString().isEmpty()) {
                            stav = true;
                            removeLastChar(word2);
                            operatory.add("+");
                            String q = meziResult.getText().toString();
                            meziResult.setText(q + "+");
                        }
                    }
                }
                result.setText(null);
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vysledekBool = false;

                String word = result.getText().toString();
                char[] chrs = word.toCharArray();

                if (word.length() > 0) {
                    char lastChar = chrs[word.length() - 1];
                    if (lastChar != '+') {
                        if (lastChar != '-') {
                            if (lastChar != '*') {
                                if (lastChar != '/') {
                                    stav = true;
                                    int a = Integer.parseInt(result.getText().toString());
                                    result.setText(result.getText());
                                    numbers.add(a);
                                    operatory.add("-");
                                    String q = meziResult.getText().toString();
                                    meziResult.setText(q + result.getText() + "-");
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult == true) {
                    meziResult.setText(result.getText() + "-");
                    endResult = false;
                }

                String word2 = meziResult.getText().toString();
                char[] chrs2 = word2.toCharArray();

                if (word2.length() > 0) {
                    char lastChar2 = chrs2[word2.length() - 1];
                    if (lastChar2 == '+' || lastChar2 == '*' || lastChar2 == '/') {
                        if (result.getText().toString().isEmpty()) {
                            stav = true;
                            removeLastChar(word2);
                            operatory.add("-");
                            String q = meziResult.getText().toString();
                            meziResult.setText(q + "-");
                        }
                    }
                }
                result.setText(null);
            }

        });

        btnMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vysledekBool = false;

                String word = result.getText().toString();
                char[] chrs = word.toCharArray();

                if (word.length() > 0) {
                    char lastChar = chrs[word.length() - 1];
                    if (lastChar != '+') {
                        if (lastChar != '-') {
                            if (lastChar != '/') {
                                if (lastChar != '*') {
                                    stav = true;
                                    int a = Integer.parseInt(result.getText().toString());
                                    result.setText(result.getText());
                                    numbers.add(a);
                                    operatory.add("*");
                                    String q = meziResult.getText().toString();
                                    meziResult.setText(q + result.getText() + "*");
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult == true) {
                    meziResult.setText(result.getText() + "*");
                    endResult = false;
                }

                String word2 = meziResult.getText().toString();
                char[] chrs2 = word2.toCharArray();

                if (word2.length() > 0) {
                    char lastChar2 = chrs2[word2.length() - 1];
                    if (lastChar2 == '+' || lastChar2 == '-' || lastChar2 == '/') {
                        if (result.getText().toString().isEmpty()) {
                            stav = true;
                            removeLastChar(word2);
                            operatory.add("*");
                            String q = meziResult.getText().toString();
                            meziResult.setText(q + "*");
                        }
                    }
                }
                result.setText(null);
            }
        });

        btnDivide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vysledekBool = false;

                String word = result.getText().toString();
                char[] chrs = word.toCharArray();

                if (word.length() > 0) {
                    char lastChar = chrs[word.length() - 1];
                    if (lastChar != '+') {
                        if (lastChar != '-') {
                            if (lastChar != '*') {
                                if (lastChar != '/') {
                                    stav = true;
                                    int a = Integer.parseInt(result.getText().toString());
                                    result.setText(result.getText());
                                    numbers.add(a);
                                    operatory.add("/");
                                    String q = meziResult.getText().toString();
                                    meziResult.setText(q + result.getText() + "/");
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult == true) {
                    meziResult.setText(result.getText() + "/");
                    endResult = false;
                }

                String word2 = meziResult.getText().toString();
                char[] chrs2 = word2.toCharArray();


                if (word2.length() > 0) {
                    char lastChar2 = chrs2[word2.length() - 1];
                    if (lastChar2 == '+' || lastChar2 == '-' || lastChar2 == '*') {
                        if (result.getText().toString().isEmpty()) {
                            stav = true;
                            removeLastChar(word2);
                            operatory.add("/");
                            String q = meziResult.getText().toString();
                            meziResult.setText(q + "/");
                        }
                    }
                }

                result.setText(null);
            }
        });

        btnEquals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stav = true;
                endResult = true;

                if (checkMultiply(operatory) && meziResult.getText().toString().length() > 1 && vysledekBool != true && !result.getText().toString().isEmpty()) {
                    int a = Integer.parseInt(result.getText().toString());
                    numbers.add(a);

                    String q = meziResult.getText().toString();
                    meziResult.setText(q + result.getText() + "=");

                    System.out.println("BEFORE CALL METHOD multiplyDevice() " + numbers);
                    System.out.println("BEFORE CALL METHOD multiplyDevice() " + operatory);
                    System.out.println("BEFORE CALL METHOD multiplyDevice() " + numbersTemp);
                    System.out.println("BEFORE CALL METHOD multiplyDevice() " + operatoryTemp);

                    multiplyDevide();

                    numbers.clear();
                    operatory.clear();
                    numbersTemp.clear();
                    operatory.clear();
                    vysledekBool = true;
                    cancel = true;

                } else {
                    if (meziResult.getText().toString().length() > 1 && vysledekBool != true && !result.getText().toString().isEmpty()) {
                        int a = Integer.parseInt(result.getText().toString());
                        numbers.add(a);

                        String q = meziResult.getText().toString();
                        meziResult.setText(q + result.getText() + "=");

                        res = numbers.get(0);

                        for (int i = 1; i < numbers.size(); i++) {
                            cons(numbers.get(i), operatory.get(i - 1));
                        }

                        vysledek = String.valueOf(res);
                        result.setText(vysledek);
                        res = Integer.parseInt(vysledek);
                        history.add(meziResult.getText().toString() + vysledek);

                        System.out.println("Čísla: " + numbers);
                        System.out.println("Operátory: " + operatory);
                        System.out.println("Historie: " + history);

                        numbers.clear();
                        operatory.clear();
                        numbersTemp.clear();
                        operatory.clear();
                        vysledekBool = true;
                        cancel = true;
                    }
                }
            }
        });

        btnPlusMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String resultText = result.getText().toString();

                if (!resultText.equals("0")) {
                    if (resultText.length() > 0) {
                        if (resultText.charAt(0) != '-') {
                            result.setText(addChar(resultText, '-', 0));
                        } else {
                            result.setText(resultText.replaceFirst("-", ""));
                        }
                    }
                }
            }
        });

//        historyImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent showHistory = new Intent(getApplicationContext(), HistoryActivity.class);
//                showHistory.putExtra("History", history);
//                startActivityForResult(showHistory, 1);
//            }
//        });

        btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vysledekBool == true) {
                    endResult = false;
                    result.setText("");
                    meziResult.setText("");
                    numbers.clear();
                    operatory.clear();
                } else {
                    result.setText("0");
                }
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        historyItem = menu.findItem(R.id.history);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.history:
                Intent showHistory = new Intent(getApplicationContext(), HistoryActivity.class);
                showHistory.putExtra("History", history);
                startActivityForResult(showHistory, 1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                historyText = data.getStringExtra("DataFromHistory");
                if (historyText != null) {
                    if (historyText.length() > 0) {
                        StringTokenizer tokens = new StringTokenizer(historyText, "=");
                        String first = tokens.nextToken();
                        String second = tokens.nextToken();
                        meziResult.setText(first + "=");
                        result.setText(second);
                        vysledek = second;
                        numbers.clear();
                        operatory.clear();
                        endResult = true;
                    }
                }
                deleteHistory = data.getBooleanExtra("DeleteHistory", false);
                if (deleteHistory) {
                    history.clear();
                    btnDel.performClick();
                }
            }
        }
    }

    private void cons(int x, String y) {
        switch (y) {
            case "+":
                res += x;
                break;
            case "-":
                res -= x;
                break;
        }
    }

    private Boolean checkMultiply(ArrayList<String> list) {
        return list.contains("*") || list.contains("/");
    }

    private void multiplyDevide() {
        if (cancel) {
            for (int i = 0; i < operatory.size(); i++) {
                String g = operatory.get(i);
                if (g.equals("*")) {
                    first = numbers.get(i);
                    second = numbers.get(i + 1);
                    indexOfFirst = i;
                    indexOfSecond = i + 1;
                    resultMultiply = first * second;
                    numbersTemp.add(resultMultiply);
                    cancel = false;
                    multiplyDevide();
                    return;
                } else if (g.equals("/")) {
                    first = numbers.get(i);
                    second = numbers.get(i + 1);
                    indexOfFirst = i;
                    indexOfSecond = i + 1;
                    resultMultiply = first / second;
                    numbersTemp.add(resultMultiply);
                    cancel = false;
                    multiplyDevide();
                    return;
                } else {
                    numbersTemp.add(numbers.get(i));
                    operatoryTemp.add(operatory.get(i));
                }
            }
        } else {
            int size = operatory.size() - 1;
            System.out.println("BEFORE FOR " + numbers);
            System.out.println("BEFORE FOR " + operatory);
            System.out.println("BEFORE FOR " + numbersTemp);
            System.out.println("BEFORE FOR " + operatoryTemp);

            for (int i = indexOfSecond; i <= size; i++) {
                numbersTemp.add(numbers.get(i + 1));
                operatoryTemp.add(operatory.get(i));
                System.out.println("ČíslaTemp: " + numbersTemp);
                System.out.println("OperátoryTemp: " + operatoryTemp);
                cancel = true;
            }

            numbers.clear();
            operatory.clear();
            numbers.addAll(numbersTemp);
            operatory.addAll(operatoryTemp);
            operatoryTemp.clear();
            numbersTemp.clear();

            System.out.println("Čísla: " + numbers);
            System.out.println("Operátory: " + operatory);
            System.out.println("ČíslaTemp: " + numbersTemp);
            System.out.println("OperátoryTemp: " + operatoryTemp);

            if (numbers.size() > 1) {
                if (operatory.contains("*")) {
                    multiplyDevide();
                } else if (operatory.contains("/")) {
                    multiplyDevide();
                } else {
                    plusMinus();
                }
            } else {
                vysledek = String.valueOf(numbers.get(0));
                System.out.println("AFTER GET 0 FROM NUMBERS " + numbers);
                result.setText(vysledek);
                history.add(meziResult.getText().toString() + vysledek);
                System.out.println("Historie " + history);
            }
        }
    }

    private void plusMinus() {
        res = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            cons(numbers.get(i), operatory.get(i - 1));
        }

        vysledek = String.valueOf(res);
        result.setText(vysledek);
        history.add(meziResult.getText().toString() + vysledek);
        System.out.println("Historie " + history);
    }

    private void removeLastChar(String str) {
        operatory.remove(operatory.size() - 1);
        StringBuffer sb = new StringBuffer(str);
        sb.deleteCharAt(sb.length() - 1);
        meziResult.setText(sb.toString());
    }

    public String addChar(String str, char ch, int position) {
        return str.substring(0, position) + ch + str.substring(position);
    }

    private void funkce(String i) {
        if (stav) {
            result.setText(i);
            stav = false;
        } else {
            String word = result.getText().toString();

            if (word.equals("0")) {
                result.setText("");
                result.setText(result.getText() + i);
            } else {
                result.setText(result.getText() + i);
            }
        }
    }
}