package com.example.projeto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AcessoBD extends SQLiteOpenHelper {
    protected static final String TABELA_USUARIO = "TABELA_USUARIO";
    protected static final String USUARIO_ID = "ID";
    protected static final String USUARIO_NOME = "USUARIO_NOME";
    protected static final String DATA_NASCIMENTO = "DATA_NASCIMENTO";
    protected static final String DATA_ACESSO = "DATA_ACESSO";


    public AcessoBD(@Nullable Context context) {
        super(context, "ClientBD", null, 1);
    }
    //É chamado na primeira vez que o banco de Dados(BD) é acessado.
    //Usado também para a criação do banco de dados

    @Override
    public void onCreate(SQLiteDatabase db) {
        String statement = "CREATE TABLE " + TABELA_USUARIO +
                " (" + USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USUARIO_NOME + " TEXT, " + DATA_NASCIMENTO + "TEXT" + DATA_ACESSO +"TEXT)";

        //"CREATE TABLE TABELA_USUARIO(USUARIO_ID INTEGER PRIMARY KEY AUTOINCREMENT, USUARIO_NOME TEXT, USUARIO_IDADE INT)";
        db.execSQL(statement);
    }

    //Atualiza a versão do BD.
    //Permite que usuários antigos e novos usem a aplicação mesmo com o BD sofrendo manutenção
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public boolean adicionarUsuario(Usuario usuario){
        //getWritableDatabase() permite a gravação em um banco de dados
        SQLiteDatabase db = this.getWritableDatabase();

        //ContentValues é uma classe que permite armazenar dados agrupados aos pares. Exemplo contentValues.put("nome", value); contentValues.getString("name"). É uma associative array em PHP ou hashmap em outras linguagens.
        ContentValues contentValues = new ContentValues();
        contentValues.put(USUARIO_NOME, usuario.getNomeUsuario()); //coluna nome e valor
        contentValues.put(DATA_NASCIMENTO, usuario.getDataNascimento()); //coluna data_nascimento e valor
        contentValues.put(DATA_ACESSO, usuario.getDataAtualBrasil()); //coluna data_acesso e valor

        //nullColumnHack é null quando deseja-se adicionar uma linha não nula. Quando intencionalmente deseja inserir uma linha vazia, é necessário informar o valor de uma coluna da tabela usada. No caso da tabela usuário, pode ser nome ou idade.
        long inserirSucedido = db.insert(TABELA_USUARIO, null, contentValues);
        db.close();//Sempre fechar o banco de dados após uso.

        //-1 indica que nenhuma linha foi inserida na referida tabela
        return inserirSucedido != -1;
    }

    /**
     * Método para retornar a lista de usuarios completa.
     * Cria uma lista (listaUsuarios) vazia para preenchimento com as tuplas resultantes da consulta no banco de dados.
     *
     * @return List de objetos Usuario
     */
    public List<Usuario> getListaUsuarios() {//Para importar List, pode ser usado o atalho com Alt+Enter (para escolher a ação) ou Shift+Alt+Enter (para pegar o pacote sugerido pela ferramenta Android Studio). Tem que ser do pacote "java.util.List"

        List<Usuario> listaUsuarios = new ArrayList<>();

        //String queryStatement = "SELECT * FROM TABELA_USUARIO";
        String queryStatement = "SELECT * FROM " + TABELA_USUARIO;//Considere a linha acima para visualizar a sentença como ela é na prática.

        SQLiteDatabase db = this.getReadableDatabase();//Readable para operações de Select. Fechar após uso.

        //O retorno Cursor serve para receber o resultado do banco e navegar entre os resultados. Fechar após uso.
        //selectionArgs é usado para preencher os parâmetros da SQL para os campus da cláusula WHERE
        try (Cursor cursor = db.rawQuery(queryStatement, null)) {
            //Exemplo com selectionArgs ->
            // rawQuery("SELECT ID, USUARIO_NOME, USUARIO_IDADE FROM TABELA_USUARIO WHERE id = ? AND USUARIO_NOME = ? AND USUARIO_IDADE = ?", new String[] {"1", "David", "2"});
            //rawQuery("SELECT ID, USUARIO_NOME, USUARIO_IDADE FROM TABELA_USUARIO WHERE id = 1 AND USUARIO_NOME = David AND USUARIO_IDADE = 2",null);

            if (cursor.moveToFirst()) {//moveToFirst retorna true no caso de haver registro(s( proveniente(s) da consulta. Posiciona cursor no primeiro resultado
                //do/while Percorrer o cursor para instanciar objetos da classe Usuario.
                do {
                    int usuarioCod = cursor.getInt(0);//A primeira coluna da tabela usuário é código
                    String usuarioNome = cursor.getString(1);
                    String dataNascimento = cursor.getString(2);
                    String dataAcesso = cursor.getString(3);
                    //int usuarioIdade = cursor.getInt(cursor.getColumnIndex("idade"));
                    //String dataNascimentoUsuario = cursor.getString(3);
                    //Não há cursor.getBoolean. Precisa converter um int para boolean. Pode ser feito com cast, SE/ENTÃO ou operador ternário

                    Usuario usuario = new Usuario(usuarioNome, dataNascimento, dataAcesso );//Sempre confira a ordem do construtor
                    listaUsuarios.add(usuario);//Adiciona o objeto usuário a lista.
                } while (cursor.moveToNext());//Enquanto houver um próximo registro (moveToNext)
            } else {
                //A lista está vazia por falta de registro no banco de dados.
            }

            //fechar cursor e base de dados. Com isso, permite que outras pessoas/processos possam utilizar.

            cursor.close();
        }
        db.close();//Fechar o banco de dados para liberar memória e permitir que outros processos assumam a prioridade de consulta de maneira mais rápida

        return listaUsuarios; //retorna a lista com os usuários encontrados no banco de dados
    }
}
