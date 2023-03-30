package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Tempo_Vivo extends AppCompatActivity {
    Button btnRetornar;
    TextView tvTempo_Vivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_vivo);
        btnRetornar = findViewById(R.id.btnRetornar);
        tvTempo_Vivo = findViewById(R.id.tvTempo_Vivo);

        //Bundle extras = getIntent().getExtras();
        //String data_nascimento = extras.getString("data");
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        //LocalDate localDate = LocalDate.parse(date, formatter);
        //LocalDate todayBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo")); *\

    }
}