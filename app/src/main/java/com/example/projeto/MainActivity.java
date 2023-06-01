package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    EditText etData;
    EditText etNome;
    Button btnProsseguir;
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        //Conexao dos elementos xml com o código Java
        etData = findViewById(R.id.etData);
        etNome = findViewById(R.id.etNome);
        btnProsseguir = findViewById(R.id.btnProsseguir);

        etData.setFocusable(false); // Não atrapalhar no processo de seleção da data
        etData.setKeyListener(null); //Não abrir o teclado quando o campo for selecionado

        Calendar calendar = Calendar.getInstance(); // Seleciona o calendário a ser usado de acordo com o fusohorário por meio dojava runtime environment
        // Recebe os valores atuais para abrir o seletor da data no dia atual
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etData.setOnClickListener(new View.OnClickListener() { // O campo de texto é interativo
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( //Este componente permite o uso de uma interface gráfica para a seleção da data
                        MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override //modifica o componente original
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month+1;
                        String data = day+"/"+month+"/"+year; // Organização da data de modo Brasileiro
                        etData.setText(data); // Definir o texto do et como a data selecionada
                    }
                },year,month,day);
                datePickerDialog.show(); // Abre o componente modificado
            }
        });
        btnProsseguir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if(!etData.getText().toString().isEmpty() && !etNome.getText().toString().isEmpty()) { //não prosseguir caso o campo de data estiver vazio
                Intent i1 = new Intent(MainActivity.this, Tempo_Vivo.class);
                i1.putExtra("data", etData.getText().toString());
                i1.putExtra("nome", etNome.getText().toString());
                startActivity(i1); //mudar de tela e levar a informação da data para outra tela
            }
            }
        });
    }
}