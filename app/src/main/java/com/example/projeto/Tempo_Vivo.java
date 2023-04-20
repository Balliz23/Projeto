package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Tempo_Vivo extends AppCompatActivity {
    Button btnRetornar;
    TextView tvTempo_Vivo;
    TextView tvData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tempo_vivo);
        //Conexao dos elementos xml com o código Java
        btnRetornar = findViewById(R.id.btnRetornar);
        tvTempo_Vivo = findViewById(R.id.tvTempo_Vivo);
        tvData = findViewById(R.id.tvData);

        //Resgata os valores da tela anterior e os armazenam em novas variaveis
        Bundle extras = getIntent().getExtras();
        String data_nascimento = extras.getString("data");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");//Função usada para formatar a string em um formato de data desejado
        LocalDate localDate = LocalDate.parse(data_nascimento, formatter); //Formatar a String em formato de date data
        LocalDate todayBrasil = LocalDate.now(ZoneId.of("America/Sao_Paulo"));// Resgata a data real em São Paulo/ América
        tvData.setText("Data de nascimento: "+data_nascimento); // Set o texto no tv para a data de nascimento previamente solicitada

        int anos_gap = Integer.parseInt(String.valueOf(ChronoUnit.YEARS.between(localDate, todayBrasil))); //Função para achar anos entre datas
        int meses_gap = Integer.parseInt(String.valueOf(ChronoUnit.MONTHS.between(localDate, todayBrasil)))%12; //Função para achar meses entre datas, com % eliminando os meses já listados nos anos
        int dias_gap = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(localDate, todayBrasil)))%30; //Função para achar dias entre datas, com % eliminando os dias já listados
        if (anos_gap < 0 || meses_gap < 0 || dias_gap < 0){
            tvTempo_Vivo.setText("Parece que temos um viajante do tempo entre nós!!!"); //se o usuário inserir uma data futura uma mensagem diferente aparecera
        }else{
            tvTempo_Vivo.setText(anos_gap+" anos, "+meses_gap+" meses e "+dias_gap+" dias."); // Mostrar o tempo vivido
        }
        btnRetornar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Tempo_Vivo.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}