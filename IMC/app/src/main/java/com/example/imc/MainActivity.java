package com.example.imc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso;
    private EditText etAltura;
    private Button btCalcular;
    private Button btLimpar;
    private TextView tvResultado;
    private TextView tvInformativo;
    private Double imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPeso = (EditText) findViewById(R.id.inputPeso);
        etAltura = (EditText) findViewById(R.id.inputAltura);
        tvResultado = (TextView) findViewById(R.id.resultado);
        tvInformativo = (TextView) findViewById(R.id.informativo);
        btCalcular = (Button) findViewById(R.id.calcular);
        btLimpar = (Button) findViewById(R.id.limpar);

        btCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btCalcularOnClick();
            }
        });

        tvInformativo.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                informativo(v);
                return true;
            }
        });
/*
        btLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btLimparOnClick();
            }
        });
*/
    }

    private void btCalcularOnClick(){

        if(etAltura.getText().toString().equals("")){
            toastNoInput("Campo [Altura] deve ser preenchido.", etAltura, "error");
            return;
        }

        if(etPeso.getText().toString().equals("")){
            toastNoInput("Campo [Peso] deve ser preenchido.", etPeso, "error");
            return;
        }

        double peso = Double.parseDouble(etPeso.getText().toString());
        double altura = Double.parseDouble(etAltura.getText().toString());
        double imc = peso / Math.pow(altura,2);
        this.imc = imc;

        tvResultado.setText(new DecimalFormat("0.00").format(imc));

    }

    //public void btLimparOnClick(){
    public void btLimparOnClick(View v){
        etPeso.setText("");
        etAltura.setText("");
        tvResultado.setText("0.0");
        etAltura.requestFocus();
        this.imc = null;
    }

    public void informativo(View v){
        if(imc != null){

            if (imc<=18.5) {
                toastMsg("Abaixo do peso normal", "error");
            }else if (18.5 < imc && imc <= 25) {
                toastMsg("Peso normal", "success");
            }else if (25 < imc && imc<=30) {
                toastMsg("Acima do peso normal", "alert");
            }else if( imc > 30) {
                toastMsg("Obesidade", "error");
            }
        }else{
            toastMsg("Faça o cálculo antes de segurar o informativo.", "info");
        }
    }

    private void toastNoInput(String msg, EditText input, String tipo){

        toastMsg(msg, tipo);

        input.requestFocus();
    }

    private void toastMsg(String msg, String tipo) {
        Toast t = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG);
        t.setGravity(Gravity.CENTER, 0, 0);
        TextView v = (TextView) t.getView().findViewById(android.R.id.message);
        if(tipo == "success"){
            v.setTextColor(Color.GREEN);
        }else if(tipo == "error"){
            v.setTextColor(Color.RED);
        }else if(tipo == "info"){
            v.setTextColor(Color.BLUE);
        }else if(tipo == "alert"){
            v.setTextColor(Color.YELLOW);
        }

        t.show();
    }
}
