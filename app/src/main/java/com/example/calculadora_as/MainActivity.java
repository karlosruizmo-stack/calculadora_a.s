
package com.example.calculadora_as;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etPeso, etAltura;
    private RadioButton rbCentimetros;
    private Button btnCalcular;
    private TextView tvResultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPeso = findViewById(R.id.etPeso);
        etAltura = findViewById(R.id.etAltura);
        rbCentimetros = findViewById(R.id.rbCentimetros);
        btnCalcular = findViewById(R.id.btnCalcular);
        tvResultado = findViewById(R.id.tvResultado);

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alPulsarCalcular();
            }
        });
    }

    private void alPulsarCalcular() {
        String pesoTexto = etPeso.getText().toString().trim();
        String alturaTexto = etAltura.getText().toString().trim();


        if (pesoTexto.isEmpty()) {
            etPeso.setError(getString(R.string.error_vacio));
            return;
        }
        if (alturaTexto.isEmpty()) {
            etAltura.setError(getString(R.string.error_vacio));
            return;
        }


        double peso;
        double altura;
        try {
            peso = Double.parseDouble(pesoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            etPeso.setError(getString(R.string.error_numero));
            return;
        }
        try {
            altura = Double.parseDouble(alturaTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            etAltura.setError(getString(R.string.error_numero));
            return;
        }

        // 3. Valores sin sentido
        if (peso <= 0) {
            etPeso.setError(getString(R.string.error_peso));
            return;
        }
        if (altura <= 0) {
            etAltura.setError(getString(R.string.error_altura));
            return;
        }

        if (rbCentimetros.isChecked()) {
            altura = altura / 100;
        }

        double imc = calcularImc(peso, altura);
        String categoria = obtenerCategoria(imc);

        String imcFormateado = String.format("%.2f", imc);
        tvResultado.setText(getString(R.string.resultado_formato, imcFormateado, categoria));
    }

    private double calcularImc(double peso, double altura) {
        return peso / (altura * altura);
    }

    private String obtenerCategoria(double imc) {
        if (imc < 18.5) {
            return getString(R.string.cat_bajo);
        } else if (imc < 25) {
            return getString(R.string.cat_normal);
        } else if (imc < 30) {
            return getString(R.string.cat_sobrepeso);
        } else {
            return getString(R.string.cat_obesidad);
        }
    }
}