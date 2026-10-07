package com.example.act05_calculadora;

import static android.text.TextUtils.concat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity {

    String operacio = null;
    String ultimaOperacio = null;
    BigDecimal valor1 = null;
    BigDecimal memoria = null;
    BigDecimal ultimValor2 = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        TextView txtResultat = findViewById(R.id.txtResultat);
        View.OnClickListener btnView = v -> {
            Button btnPulsado = (Button) v;
            if(btnPulsado.getText().length() == 1 && Character.isDigit(btnPulsado.getText().charAt(0))){
                ultimaOperacio = null;
                ultimValor2 = null;
                if(txtResultat.getText().equals("0")){
                    txtResultat.setText(btnPulsado.getText());
                }else{
                    if(operacio != null && valor1 == null){
                        valor1 = new BigDecimal(txtResultat.getText().toString());
                        txtResultat.setText(btnPulsado.getText());
                    }else{
                        txtResultat.setText(concat(txtResultat.getText(), btnPulsado.getText()));
                    }
                }
            } else if (btnPulsado.getText().equals("C")) {
                txtResultat.setText("0");
                operacio = null;
                valor1 = null;
                ultimaOperacio = null;
                ultimValor2 = null;
            } else if (btnPulsado.getText().equals("=")){
                if(operacio != null){
                    BigDecimal valor2 = new BigDecimal(txtResultat.getText().toString());
                    ultimaOperacio = operacio;
                    ultimValor2 = valor2;
                    BigDecimal resultat = null;
                    if(operacio.equals("×")){
                        resultat = valor1.multiply(valor2);
                    }else if(operacio.equals("-")){
                        resultat = valor1.subtract(valor2);
                    }else if(operacio.equals("+")){
                        resultat = valor1.add(valor2);
                    }else if(operacio.equals("/")){
                        resultat = valor1.divide(valor2, MathContext.DECIMAL128);
                    }
                    if(resultat != null){
                        String s = String.valueOf(resultat);
                        txtResultat.setText(s);
                        valor1 = null;
                        operacio = null;
                    }
                } else if(ultimaOperacio != null && ultimValor2 != null){
                    BigDecimal valorActual = new BigDecimal(txtResultat.getText().toString());
                    BigDecimal resultat = null;
                    if(ultimaOperacio.equals("×")){
                        resultat = valorActual.multiply(ultimValor2);
                    }else if(ultimaOperacio.equals("-")){
                        resultat = valorActual.subtract(ultimValor2);
                    }else if(ultimaOperacio.equals("+")){
                        resultat = valorActual.add(ultimValor2);
                    }else if(ultimaOperacio.equals("/")){
                        resultat = valorActual.divide(ultimValor2, MathContext.DECIMAL128);
                    }
                    if(resultat != null){
                        txtResultat.setText(String.valueOf(resultat));
                    }
                }
            } else if (btnPulsado.getText().toString().startsWith("M")){
                if(btnPulsado.getText().toString().equals("MC")){
                    memoria = null;
                }else if(btnPulsado.getText().toString().equals("MR")){
                    if(memoria != null){
                        txtResultat.setText(String.valueOf(memoria));
                    }
                }else if(btnPulsado.getText().toString().equals("M-")){
                    if(memoria != null){
                        BigDecimal calculat = new BigDecimal(txtResultat.getText().toString()).subtract(memoria);
                        txtResultat.setText(String.valueOf(calculat));
                    }
                }else if(btnPulsado.getText().toString().equals("M+")){
                    if(memoria != null){
                        BigDecimal calculat = new BigDecimal(txtResultat.getText().toString()).add(memoria);
                        txtResultat.setText(String.valueOf(calculat));
                    }else{
                        memoria = new BigDecimal(txtResultat.getText().toString());
                    }
                }
            } else if (btnPulsado.getText().toString().equals("⌫")){
                if(txtResultat.getText().length() > 1){
                    txtResultat.setText(txtResultat.getText().toString().substring(0, txtResultat.getText().toString().length() - 1));
                    if(txtResultat.getText().toString().endsWith(".") || txtResultat.getText().toString().endsWith(",")){
                        txtResultat.setText(txtResultat.getText().toString().substring(0, txtResultat.getText().toString().length() - 1));
                    }
                }else if(!txtResultat.getText().toString().equals("0")){
                    txtResultat.setText("0");
                }
            }else if (btnPulsado.getText().toString().startsWith(".")){
                ultimaOperacio = null;
                ultimValor2 = null;
                if(!txtResultat.getText().toString().contains(".") && !txtResultat.getText().toString().contains(",")){
                    if(operacio != null && valor1 == null){
                        valor1 = new BigDecimal(txtResultat.getText().toString());
                        txtResultat.setText("0.");
                    }else{
                        txtResultat.setText(concat(txtResultat.getText(), "."));
                    }
                }
            }else{
                ultimaOperacio = null;
                ultimValor2 = null;
                operacio = btnPulsado.getText().toString();
            }
        };

        int[] botones = {
                R.id.button0, R.id.button1, R.id.button2, R.id.button3, R.id.button4,
                R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9,
                R.id.buttonX, R.id.buttonResta, R.id.buttonMes, R.id.buttonDividir,
                R.id.buttonIgual, R.id.buttonC, R.id.buttonM1, R.id.buttonM2,
                R.id.buttonM3, R.id.buttonM4, R.id.buttonComma, R.id.buttonDelete
        };
        for (int botonId : botones) {
            findViewById(botonId).setOnClickListener(btnView);
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

}
