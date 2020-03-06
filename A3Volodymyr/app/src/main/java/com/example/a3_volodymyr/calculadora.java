package com.example.a3_volodymyr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class calculadora extends AppCompatActivity {

    BigInteger e,d,n,pe,q,totient;
    int tamClave;
    TextView introducir,procedimiento;
    int primero,segundo,estado;
    boolean p=true,s=true,o=true;

    DatabaseReference data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculadora);
        introducir= findViewById(R.id.textView2);
        procedimiento= findViewById(R.id.textView4);

        data= FirebaseDatabase.getInstance().getReference();
    }

    public void zero(View v) {
        introducir.append("0");
    }
    public void uno(View v) {
        introducir.append("1");
    }
    public void dos(View v) {
        introducir.append("2");
    }
    public void tres(View v) {
        introducir.append("3");
    }
    public void cuatro(View v) {
        introducir.append("4");
    }
    public void cinco(View v) {
        introducir.append("5");
    }
    public void seis(View v) {
        introducir.append("6");
    }
    public void siete(View v) {
        introducir.append("7");
    }
    public void ocho(View v) {
        introducir.append("8");
    }
    public void nueve(View v) {
        introducir.append("9");
    }

    public void s(View v){
        if(o) {
            estado = 1;
            o = false;
            operaciones();
            procedimiento.append("+");
        }
    }
    public void r(View v){
        if(o) {
            estado = 2;
            o = false;
            operaciones();
            procedimiento.append("-");
        }
    }
    public void m(View v){
        if(o) {
            estado = 3;
            o = false;
            operaciones();
            procedimiento.append("*");
        }
    }
    public void d(View v){
        if(o){
            estado=4;
            o=false;
            operaciones();
            procedimiento.append("/");
        }
    }
    public void i(View v){
        if(s && !p) {
            operaciones();
            ejecutar(estado);
        }
    }
    public void c(View v){
        clear();
    }


    public void operaciones() {
        String num;
        try {
            if (p) {
                num = introducir.getText().toString();
                primero = Integer.parseInt(num);
                procedimiento.setText(num);
                p = false;
            } else {
                if (s) {
                    num = introducir.getText().toString();
                    segundo = Integer.parseInt(num);
                    procedimiento.append(num);
                    s = false;
                }
            }
        }catch (Exception ex){
            clear();
            procedimiento.setText(R.string.error_carga);
        }
        introducir.setText("");

    }

    private void ejecutar(int e){
        switch (e){
            case 1: sumar();break;//suma
            case 2: restar();break;//resta
            case 3: multiplicar();break;//multiplicacion
            case 4: dividir();break;//divicion
            default: break;//clear
        }
        o=true;
        p=true;
        s=true;
    }

    private void sumar(){
        String result;
        result=String.valueOf(primero+segundo);
        introducir.setText(result);
        procedimiento.setText("");
    }
    private void restar(){
        String result;
        result=String.valueOf(primero-segundo);
        introducir.setText(result);
        procedimiento.setText("");
    }
    private void multiplicar(){
        String result;
        result=String.valueOf(primero*segundo);
        introducir.setText(result);
        procedimiento.setText("");
    }
    private void dividir(){
        String result;
        if (segundo==0) {
            result=String.valueOf(primero);
            clear();
            procedimiento.setText(R.string.div_cero);
        }else {
            result = String.valueOf(primero / segundo);
            procedimiento.setText("");
        }
        introducir.setText(result);
    }
    private void clear(){
        p=true;
        s=true;
        o=true;
        introducir.setText("");
        procedimiento.setText("");
        estado=0;
    }

    private void sendToFirebase(String modo,String tiempo){

        String id = data.child("generado").push().getKey();
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat format= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        DatosParaFirebase f = new DatosParaFirebase(id,modo,format.format(currentTime),tiempo);

        data.child("generado").child(id).setValue(f);
    }

////////////////////////////////////////////////

    public void SyncTask(View v){
        Spinner spin = findViewById(R.id.spinner);
        tamClave = Integer.parseInt(spin.getSelectedItem().toString());
        new Clavetask().execute();
        Log.d("ThreadANR","Hebra creada y vuelve hebra principal");
    }

    private class Clavetask extends AsyncTask<Void, Void, Long> {
        @Override
        protected Long doInBackground(Void... params) {
            Log.d("ThreadANR","Asyntask Creada y vuelve hebra principal");
            return generaClavesRSA();
        }

        protected void onPostExecute(Long time) {
            int seconds,miliseconds;
            seconds = (int)(time/1000);
            miliseconds = (int)(time%1000);
            String resultadoTiempo=String.format("%4d,%03d", seconds, miliseconds);
            Toast.makeText(getApplicationContext(),R.string.inic+resultadoTiempo+R.string.fin,Toast.LENGTH_SHORT).show();
            Log.d("ThreadANR","onPostExecute");
            sendToFirebase("SYNC",resultadoTiempo);
        }
    }

    public long generaClavesRSA() {

        long ini = System.currentTimeMillis();
        generaPrimos(); //Genera p y q
        generaClaves(); //Genera e y d
        long fin = System.currentTimeMillis();
        Log.d("ThreadANR","Finalizado en hilo sync");

        return fin-ini;
    }

    public void generaPrimos()	{
        pe = new BigInteger(tamClave/2, 10, new Random());

        do q = new BigInteger(tamClave/2, 10, new Random());
        while(q.compareTo(pe)==0);
    }


    public void generaClaves(){
        // n = p * q
        n = pe.multiply(q);
        // toltient = (p-1)*(q-1)
        totient = pe.subtract(BigInteger.valueOf(1));
        totient = totient.multiply(q.subtract(BigInteger.valueOf(1)));
        // Elegimos un e coprimo de y menor que n
        do e = new BigInteger(tamClave, new Random());

        while((e.compareTo(totient) != -1) ||
                (e.gcd(totient).compareTo(BigInteger.valueOf(1)) != 0));
        // d = e^1 mod totient
        d = e.modInverse(totient);
    }

/////////////////////////////////////////////////////

    public void onClickMainThread(View v) {
        Spinner spin = findViewById(R.id.spinner);
        tamClave = Integer.parseInt(spin.getSelectedItem().toString());
        long time= generaClavesRSA();
        int seconds,miliseconds;
        seconds = (int)(time/1000);
        miliseconds = (int)(time%1000);
        String resultadoTiempo=String.format("%4d,%03d", seconds, miliseconds);
        Toast.makeText(getApplicationContext(),R.string.inic+resultadoTiempo+R.string.fin,Toast.LENGTH_SHORT).show();
        Log.d("ThreadANR","Finalizado en hilo presente");
        sendToFirebase("MAIN",resultadoTiempo);
    }

/////////////////////////////////////////////////////

    public void onClickNewThread(View v) {

        new Thread(new Runnable() {
            public void run() {
                Spinner spin = findViewById(R.id.spinner);
                tamClave = Integer.parseInt(spin.getSelectedItem().toString());
                final long time = generaClavesRSA();
                int seconds,miliseconds;
                seconds = (int)(time/1000);
                miliseconds = (int)(time%1000);
                final String resultadoTiempo=String.format("%4d,%03d", seconds, miliseconds);
                sendToFirebase("NEWT",resultadoTiempo);
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(getApplicationContext(),R.string.inic+resultadoTiempo+R.string.fin,Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        Log.d("ThreadANR","Finalizado hilo nuevo");
    }
}
