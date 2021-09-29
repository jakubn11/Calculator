package com.example.kalkulacka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.nio.channels.InterruptedByTimeoutException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import classes.Capture;

public class MainActivity extends AppCompatActivity {

    private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnPlus, btnMinus, btnDel, btnEquals, btnPlusMinus, btnMultiply, btnDivide, btnRecent, btnPi, btnComma;
    private EditText result;
    private HorizontalScrollView hs;
    private ArrayList<Integer> numbers;
    private ArrayList<Integer> numbersTemp;
    private ArrayList<Integer> numbersTemp2;
    private ArrayList<String> history;
    private ArrayList<String> operatory;
    private ArrayList<String> operatoryTemp;
    private ArrayList<Character> resultList;
    private ArrayList<Character> resultList2;
    private int res, resultMultiply, first, second, indexOfSecond, indexOfFirst;
    private boolean stav = false;
    private boolean endResult = false;
    private boolean vysledekBool = false;
    private boolean deleteHistory;
    private boolean cancel = true;
    private TextView meziResult;
    private String vysledek, historyText, listString, listString2, textFromQR;
    private MenuItem historyItem, qrItem, shareItem;
    private char lastChar8;

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
        hs = findViewById(R.id.scrollView);

        numbers = new ArrayList<>();
        history = new ArrayList<>();
        operatory = new ArrayList<>();
        numbersTemp = new ArrayList<>();
        operatoryTemp = new ArrayList<>();
        numbersTemp2 = new ArrayList<>();
        resultList = new ArrayList<>();
        resultList2 = new ArrayList<>();

        result.setHint("0");

        createNotificationChannel();

        getSupportActionBar().setTitle(getString(R.string.app_name));

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
                                    hs.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                        }
                                    }, 100L);
                                }
                            }
                        }
                    } else {

                    }
                }


                if (endResult) {
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
                                    hs.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                        }
                                    }, 100L);
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult) {
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
                                    hs.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                        }
                                    }, 100L);
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult) {
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
                                    hs.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                        }
                                    }, 100L);
                                }
                            }
                        }
                    } else {
                    }
                }

                if (endResult) {
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

                if (!meziResult.getText().toString().isEmpty()) {
                    String word = meziResult.getText().toString();
                    char[] chrs = word.toCharArray();
                    lastChar8 = chrs[word.length() - 1];
                }

                if (result.getText().toString() != null && !meziResult.getText().toString().isEmpty() && lastChar8 != '=') {
                    stav = true;
                    endResult = true;

                    if (checkMultiply(operatory) && meziResult.getText().toString().length() > 1 && !vysledekBool && !result.getText().toString().isEmpty()) {

                        int a = Integer.parseInt(result.getText().toString());
                        numbers.add(a);

                        String q = meziResult.getText().toString();
                        meziResult.setText(q + result.getText() + "=");

                        System.out.println("BEFORE CALL METHOD multiplyDevice() " + numbers);
                        System.out.println("BEFORE CALL METHOD multiplyDevice() " + operatory);
                        System.out.println("BEFORE CALL METHOD multiplyDevice() " + numbersTemp);
                        System.out.println("BEFORE CALL METHOD multiplyDevice() " + operatoryTemp);

                        multiplyDevide();

                        hs.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                            }
                        }, 100L);

                        numbers.clear();
                        operatory.clear();
                        numbersTemp.clear();
                        operatory.clear();
                        vysledekBool = true;
                        cancel = true;
                        endResult = true;
                    } else {
                        if (meziResult.getText().toString().length() > 1 && !vysledekBool && !result.getText().toString().isEmpty()) {
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

                            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "My notification");
                            builder.setSmallIcon(R.drawable.notification_icon);
                            builder.setContentTitle("Výsledek příkladu");
                            builder.setContentText(vysledek);
                            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                            builder.setAutoCancel(true);

                            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                            notificationManager.notify(1, builder.build());

                            hs.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    hs.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
                                }
                            }, 100L);

                            numbers.clear();
                            operatory.clear();
                            numbersTemp.clear();
                            operatory.clear();
                            vysledekBool = true;
                            cancel = true;
                            endResult = true;
                        }
                    }

                    String word = meziResult.getText().toString();
                    char[] chrs = word.toCharArray();
                    char lastChar9 = chrs[word.length() - 1];

                    if (lastChar9 != '=') {
                        endResult = false;
                        vysledekBool = false;
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

        btnRecent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vysledekBool) {
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

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("My notification", "My notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);

        historyItem = menu.findItem(R.id.history);
        qrItem = menu.findItem(R.id.qr_code);
        shareItem = menu.findItem(R.id.share);

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
            case R.id.qr_code:
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity.this);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
                setResult(3);
                return true;
            case R.id.share:
                Toast.makeText(getApplicationContext(), "Share QR Code", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (resultCode) {
                case -1:
                    System.out.println("request: " + requestCode + " result: " + resultCode);
                    IntentResult intentResult = new IntentIntegrator(MainActivity.this).parseActivityResult(requestCode, resultCode, data);
                    if (intentResult.getContents() != null) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
                        builder.setTitle("Úspěšně naskenováno");
                        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stav = true;

                                resultList.clear();
                                resultList2.clear();

                                textFromQR = intentResult.getContents();

                                history.add(textFromQR);

                                System.out.println(history);
                                System.out.println(operatory);
                                System.out.println(numbers);

                                btnDel.performClick();

                                int equalsPosition = textFromQR.indexOf("=");
                                StringBuffer stringBuffer = new StringBuffer(textFromQR);
                                for (int i = equalsPosition + 1; i < textFromQR.length(); i++) {
                                    resultList.add(stringBuffer.charAt(i));
                                    listString = TextUtils.join("", resultList);
                                }
                                result.setText(listString);

                                int equalsPosition2 = textFromQR.indexOf("=");
                                StringBuffer stringBuffer2 = new StringBuffer(textFromQR);
                                for (int i = 0; i < equalsPosition2 + 1; i++) {
                                    resultList2.add(stringBuffer2.charAt(i));
                                    listString2 = TextUtils.join("", resultList2);
                                }
                                meziResult.setText(listString2);

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "My notification");
                                builder.setSmallIcon(R.drawable.notification_icon);
                                builder.setContentTitle("Výsledek příkladu");
                                builder.setContentText(listString);
                                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                                builder.setAutoCancel(true);

                                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                                notificationManager.notify(1, builder.build());

                                vysledekBool = true;
                                cancel = true;
                                endResult = true;

                                dialog.dismiss();
                            }
                        });
                        builder.show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing scanned", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 0:

                case 1:
                    historyText = data.getStringExtra("DataFromHistory");
                    StringTokenizer tokens = new StringTokenizer(historyText, "=");
                    String first = tokens.nextToken();
                    String second = tokens.nextToken();
                    meziResult.setText(first + "=");
                    result.setText(second);
                    vysledek = second;
                    numbers.clear();
                    operatory.clear();
                    endResult = true;
                    break;

                case 2:
                    deleteHistory = data.getBooleanExtra("DeleteHistory", false);
                    if (deleteHistory) {
                        history.clear();
                        btnDel.performClick();
                    }
                    break;
                case 3:
                    history = data.getStringArrayListExtra("HistoryArrayList");
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

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "My notification");
                builder.setSmallIcon(R.drawable.notification_icon);
                builder.setContentTitle("Výsledek příkladu");
                builder.setContentText(numbers.get(0).toString());
                builder.setPriority(NotificationCompat.PRIORITY_HIGH);
                builder.setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                notificationManager.notify(1, builder.build());
            }
        }
    }

    private void plusMinus() {
        res = numbers.get(0);

        for (int i = 1; i < numbers.size(); i++) {
            cons(numbers.get(i), operatory.get(i - 1));
        }

        vysledek = String.valueOf(res);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "My notification");
        builder.setSmallIcon(R.drawable.notification_icon);
        builder.setContentTitle("Výsledek příkladu");
        builder.setContentText(vysledek);
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(1, builder.build());

        result.setText(vysledek);
        history.add(meziResult.getText().toString() + vysledek);
        System.out.println("Historie " + history);
    }

    private void removeLastChar(String str) {
        operatory.remove(operatory.size() - 1);
        StringBuilder sb = new StringBuilder(str);
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