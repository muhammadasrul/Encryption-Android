package com.asrul.myapplication;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText edtPlain;
    private Button btnSubmit;
    private ImageView btnIncrease, btnDecrease;
    private EditText edtKey;
    private int key = 0;
    private Switch sw;
    private AlertDialog alertDialog;
    private AlertDialog.Builder alertBuilder;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        edtPlain = findViewById(R.id.edt_plaintxt);
        btnSubmit = findViewById(R.id.btn_submit);
        btnDecrease = findViewById(R.id.btn_decrease);
        btnIncrease = findViewById(R.id.btn_increase);
        edtKey = findViewById(R.id.edt_key);
        sw = findViewById(R.id.sw);
        tvResult = findViewById(R.id.result);

        edtKey.setText(String.valueOf(key));
        btnIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtKey.getText().toString().isEmpty()) {
                    resetKey();
                    tambahKey();
                } else {
                    tambahKey();
                }
            }
        });
        btnDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtKey.getText().toString().isEmpty()) {
                    resetKey();
                    kurangKey();
                } else {
                    kurangKey();
                }
            }
        });
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sw.isChecked()) {
                    decode(Integer.parseInt(edtKey.getText().toString().trim()));
                } else {
                    encode(Integer.parseInt(edtKey.getText().toString().trim()));
                }
            }
        });
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(sw.isChecked()){
                    edtPlain.setText("");
                    sw.setText("Encoder");
                    btnSubmit.setText("Decrypt");
                }else{
                    edtPlain.setText("");
                    sw.setText("Decoder");
                    btnSubmit.setText("Encrypt");
                }
            }
        });
    }

    private void resetKey() {
        key = 0;
        edtKey.setText(String.valueOf(key));
    }

    private void tambahKey() {
        try {
            key = Integer.parseInt(edtKey.getText().toString());
            if (key == 26){
                edtKey.setError("Maximal 26");
            } else {
                key = key + 1;
                edtKey.setText(String.valueOf(key));
            }

        } catch (NumberFormatException nfe) {
            resetKey();
            tambahKey();
        }
    }

    private void kurangKey() {
        try {
            key = Integer.parseInt(edtKey.getText().toString());
            if (key == 0) {
                Toast.makeText(getApplicationContext(), "Key tidak bisa kurang dari 0", Toast.LENGTH_SHORT).show();
            } else {
                key = key - 1;
                edtKey.setText(String.valueOf(key));
            }
        } catch (NumberFormatException nfe) {
            resetKey();
            kurangKey();
        }
    }

    private void encode(int shift) {
        Editable msg = edtPlain.getText();
        String s = "";
        int len = msg.length();
        for (int i = 0; i < len; i++) {
            char c = (char) (msg.charAt(i) + shift);
            if (c > 'z' && c > 'Z')
                s += (char) (msg.charAt(i) - (26 - shift));
            else
                s += (char) (msg.charAt(i) + shift);
        }
        popUpResult(s);
    }

    private void decode(int shift) {
        int shifMinus = shift * -1;
        Editable msg = edtPlain.getText();
        String s = "";
        int len = msg.length();
        for (int i = 0; i < len; i++) {
            char c = (char) (msg.charAt(i) + shifMinus);
            if (c > 'z' && c > 'Z')
                s += (char) (msg.charAt(i) - (26 - shifMinus));
            else
                s += (char) (msg.charAt(i) + shifMinus);
        }
        popUpResult(s);
    }

    private void popUpResult(String result) {
        alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Result");
        alertBuilder.setMessage(result);
        alertBuilder.setCancelable(true);

        alertDialog = alertBuilder.show();
    }
}