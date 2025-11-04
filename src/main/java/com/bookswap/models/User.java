package com.bookswap.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String nome;
    private String email;
    private String senha; 
    private LocalDateTime dataRegistro;
    private UserRole role; 
    private UserStatus status;
    private String fotoPerfil;
    private String localizacao;

    public User(){
        // ... codigo? nop construtor vazio padrão ヾ(⌐■_■)ノ♪
    }

    //inteiro
    public User(int id, String nome, String email, String senha, LocalDateTime dataRegistro, UserRole role, UserStatus status, String fotoPerfil, String localizacao) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha  = senha ;
        this.dataRegistro = dataRegistro;
        this.role  = role ;
        this.status = status;
        this.fotoPerfil = fotoPerfil;
        this.localizacao = localizacao;
    }

    //pra autenticação
    public User( String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha  = senha ;
        this.dataRegistro = LocalDateTime.now();
        this.role  = UserRole.USUARIO ;
        this.status = UserStatus.ATIVO;
        this.fotoPerfil = null;
        this.localizacao = null;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDateTime getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(LocalDateTime dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }
}

