package com.example.a3_volodymyr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void salir(View v) {
        finish();
        System.exit(0);
    }
    public void calculo(View v) {
        Intent intent = new Intent(MainActivity.this,calculadora.class);
        startActivity(intent);
    }
    public void listado(View v) {
        Intent intent = new Intent(MainActivity.this,readFireBase.class);
        startActivity(intent);
    }
    public void libre(View v) {
        Intent intent = new Intent(MainActivity.this,libreActividad.class);
        startActivity(intent);
    }
}
