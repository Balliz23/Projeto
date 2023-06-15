package com.example.projeto;

public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String dataNascimento;
    private String dataAcesso;

    public Usuario(String nomeUsuario, String dataNascimento, String dataAcesso) {
        this.nomeUsuario = nomeUsuario;
        this.dataNascimento = dataNascimento;
        this.dataAcesso = dataAcesso;
    }
    public Usuario(int idUsuario, String nomeUsuario, String dataNascimento, String dataAcesso) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.dataNascimento = dataNascimento;
        this.dataAcesso = dataAcesso;
    }
    public String toString() {
        return "Usuario{" + "idUsuario=" + idUsuario + ", nomeUsuario='" + nomeUsuario +
                '\'' + ", dataNascimento=" + dataNascimento + ", dataAcesso=" + dataAcesso +'}';
    }
    protected int getIdUsuario(){
        return idUsuario;
    }
    protected String getNomeUsuario(){
        return nomeUsuario;
    }
    protected void setNomeUsuario(String nomeUsuario){
        this.nomeUsuario = nomeUsuario;
    }
    protected String getDataNascimento(){
        return dataNascimento;
    }
    protected void setDataNascimento(String dataNascimento){
        this.dataNascimento = dataNascimento;
    }
    protected String getDataAtualBrasil(){
        return dataAcesso;
    }
    protected void setDataAtualBrasil(String dataAcesso){
        this.dataAcesso = dataAcesso;
    }
}


