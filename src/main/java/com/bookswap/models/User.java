package com.bookswap.models;

public class User {
    //variaveis
    private int id;
    private String nome;
    private String email;
    private String senha;
    private Role role;

    //construtores
    public User(int id, String nome, String email, String senha, Role role) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    public User(String nome, String email, String senha, Role role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    //toString
    @Override
    public String toString() {
        return "User id=" + id 
                + "\nnome=" + nome 
                + "\nemail=" + email 
                + "\nsenha=" + senha 
                + "\nrole=" + role;
    }
}
