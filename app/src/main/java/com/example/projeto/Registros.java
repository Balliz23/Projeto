package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Registros extends AppCompatActivity {
    ListView lvUsuarios;
    Button btnHome;
    Button btnAlterar;
    Button btnDeletar;
    EditText etData;
    EditText etNome1;
    EditText etId;
    ArrayAdapter usuarioArrayAdapter;
    AcessoBD acessoBD;
    String idUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_registros);
        btnHome = findViewById(R.id.btnHome);
        btnAlterar = findViewById(R.id.btnAlterar);
        btnDeletar = findViewById(R.id.btnDeletar);
        lvUsuarios = findViewById(R.id.lvUsuarios);
        etData = findViewById(R.id.etData);
        etNome1 = findViewById(R.id.etNome1);
        etId = findViewById(R.id.etId);
        etId.setKeyListener(null);
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
                        Registros.this, new DatePickerDialog.OnDateSetListener() {
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
        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etId.getText().toString().isEmpty()) {
                    acessoBD.deletarUsuario(idUser);
                    mostrarUsuariosNaListView(acessoBD);
                    etData.setText(null);
                    etId.setText(null);
                    etNome1.setText(null);
                }
            }
        });

        btnAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etId.getText().toString().isEmpty()) {
                    Usuario usuario = null;
                    usuario = new Usuario(Integer.parseInt(idUser), etNome1.getText().toString(),
                            etData.getText().toString(), "teste");
                    acessoBD.atualizarUsuario(usuario);
                    mostrarUsuariosNaListView(acessoBD);
                    etData.setText(null);
                    etId.setText(null);
                    etNome1.setText(null);
                }
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(Registros.this, MainActivity.class);
                startActivity(i5); //mudar de tela e levar a informação da data para outra tela
            }
        });
        try {
            acessoBD = new AcessoBD(Registros.this);
            mostrarUsuariosNaListView(acessoBD);

            //Resgata os valores da tela anterior e os armazenam em novas variaveis
            Bundle extras = getIntent().getExtras();
            String nome = extras.getString("nome");
            String data_nascimento = extras.getString("data_nascimento");
            String data_acesso = extras.getString("data_acesso");
            Usuario usuario = new Usuario(nome, data_nascimento, data_acesso);
            boolean sucesso = acessoBD.adicionarUsuario(usuario);
            Toast.makeText(Registros.this, "Sucesso:" + sucesso, Toast.LENGTH_SHORT).show();
            //Usuario usuario = new Usuario(nome, data_nascimento, data_acesso);

            //Bloco try para "tentar" executar as ações esperadas. O catch é para "remediar" ou "avisar" algo que não foi realizado no bloco try como esperado.
            //boolean sucesso = acessoBD.adicionarUsuario(usuario);
            mostrarUsuariosNaListView(acessoBD);
            Toast.makeText(Registros.this, "Sucesso:" + sucesso, Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(Registros.this, "Erro na conversão de uma String para int: Idade não corresponde a número!", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(Registros.this, "Erro na criação do usuário!", Toast.LENGTH_LONG).show();
        }

        lvUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("Captou o click na lista!");
                Usuario usuarioClicado = (Usuario) parent.getItemAtPosition(position);
                etData.setText(usuarioClicado.getDataNascimento());
                etNome1.setText(usuarioClicado.getNomeUsuario());
                idUser = String.valueOf(usuarioClicado.getIdUsuario());
                etId.setText(idUser);
            }
        });
    }

    private void mostrarUsuariosNaListView(AcessoBD acessoBD) {
        usuarioArrayAdapter = new ArrayAdapter<Usuario>(Registros.this,
                android.R.layout.simple_list_item_1, acessoBD.getListaUsuarios());//Dentro de <> está o tipo de objeto que será adicionado na lista
        lvUsuarios.setAdapter(usuarioArrayAdapter);
    }
}