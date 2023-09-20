package com.example.permisos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_VIBRATE = 1;
    public static final int REQUEST_CODE_MICROFONO = 2;
    public static final int REQUEST_CODE_STATUS_NETWORK = 3;
    private TextView tvVibracion;
     private TextView tvMicrofono;
     private TextView tvAccInternet;
     private Button validarPermiso;
     private Button solicitudMicrofono;
     private Button solicitudVibracion;
     private Button solicitudEstadoInternet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Inicio();
        validarPermiso.setOnClickListener(this::VerificarPermisos);
    }

     private void VerificarPermisos(View view) {
        //0 otorgado
        //-1 no otorgado
        int statusPermisoVib= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.VIBRATE);
        int statusPermisoRecord= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
        int statusPermisoAccInternet= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE);
        tvVibracion.setText("Estatus del permiso de Vibracion: "+statusPermisoVib);
        tvMicrofono.setText("Estatus del permiso del Microfono: "+statusPermisoRecord);
        tvAccInternet.setText("Estatus del permiso del Estado de Red: "+statusPermisoAccInternet);
        solicitudVibracion.setEnabled(true);

    }

    public void solicitarPermisosVibracion(View view){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.VIBRATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.VIBRATE},REQUEST_CODE_VIBRATE);
        } else {
            solicitudMicrofono.setEnabled(true);
        }

    }
    public void SolicitarPermisosMicrofono(View view){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.RECORD_AUDIO},REQUEST_CODE_MICROFONO);
        }else {
            solicitudEstadoInternet.setEnabled(true);
        }
    }

    public void solicitarPermisosStatusRed(View view){
        if(ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, REQUEST_CODE_STATUS_NETWORK);
        }

    }

   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    switch (requestCode) {
        case REQUEST_CODE_VIBRATE:
            // Verifica si el código de solicitud corresponde a la solicitud de permiso de Vibracion.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // El usuario otorgó el permiso de vibración, ahora habilitamos el permiso siguiente.
                int statusPermisoVib= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.VIBRATE);
                tvVibracion.setText("Estatus del permiso de Vibracion: "+statusPermisoVib);
                 solicitudMicrofono.setEnabled(true);
            } else {
                Toast.makeText(this,"Si no acepta el permiso de Vibración no puede continuar con el uso de la aplicación",
                        Toast.LENGTH_LONG).show();
            }
            break;

        case REQUEST_CODE_MICROFONO:
            // Verifica si el código de solicitud corresponde a la solicitud de permiso de microfono.
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                 int statusPermisoMicro= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
                tvMicrofono.setText("Estatus del permiso de Microfono: "+statusPermisoMicro);
                // El usuario otorgó el permiso de Micorofono.
                solicitudEstadoInternet.setEnabled(true);
            } else {
                 Toast.makeText(this,"Si no acepta el permiso de Microfono no puede continuar con el uso de la aplicación",
                        Toast.LENGTH_LONG).show();
            }
            break;
        case REQUEST_CODE_STATUS_NETWORK:
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                int statusPermisoAccInternet= ContextCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.RECORD_AUDIO);
                tvMicrofono.setText("Estatus del permiso del estado de Red: "+statusPermisoAccInternet);
               Toast.makeText(this,"Se han concedido todos los permisos, !Bienvenido!",
                        Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(this,"Si no acepta el permiso de estatus de Internet no puede continuar con el uso de la aplicación",
                        Toast.LENGTH_LONG).show();
            }
            break;


    }
}


        private void Inicio(){
        tvVibracion =findViewById(R.id.tvVibracion);
        tvMicrofono=findViewById(R.id.tvMicrofono);
        tvAccInternet=findViewById(R.id.tvAccesoInternet);
        validarPermiso=findViewById(R.id.btnVerificar);
        solicitudMicrofono=findViewById(R.id.btnSoliPerMicro);
        solicitudVibracion=findViewById(R.id.btnSoliPerVibracion);
        solicitudEstadoInternet= findViewById(R.id.btnSoliPerRed);
        //Desabilitar el boton request

        solicitudVibracion.setEnabled(false);
        solicitudEstadoInternet.setEnabled(false);
        solicitudMicrofono.setEnabled(false);
    }
}