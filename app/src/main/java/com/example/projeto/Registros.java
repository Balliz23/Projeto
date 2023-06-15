package com.example.projeto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Registros extends AppCompatActivity {
    ListView lvUsuarios;
    Button btnHome;

    ArrayAdapter usuarioArrayAdapter;
    AcessoBD acessoBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);
        btnHome = findViewById(R.id.btnHome);
        lvUsuarios = findViewById(R.id.lvUsuarios);

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
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i5 = new Intent(Registros.this, MainActivity.class);
                    startActivity(i5); //mudar de tela e levar a informação da data para outra tela
                }
            });
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
    }

    private void mostrarUsuariosNaListView(AcessoBD acessoBD) {
        usuarioArrayAdapter = new ArrayAdapter<Usuario>(Registros.this,
                android.R.layout.simple_list_item_1, acessoBD.getListaUsuarios());//Dentro de <> está o tipo de objeto que será adicionado na lista
        lvUsuarios.setAdapter(usuarioArrayAdapter);
    }
}