package com.bookswap.models;

import com.bookswap.models.subModels.LivroStatus;

public class Livro {
    private int id;
    private int idUsuario;
    private String titulo;
    private String autor;
    private String condicaoEstado;
    private double precoCreditos;
    private String fotoCapa;
    private LivroStatus statusLivro;

    public Livro(){
        // (o゜▽゜)o☆
    }

    public Livro(int id, int idUsuario, String titulo, String autor, String condicaoEstado, double precoCreditos, String fotoCapa, LivroStatus statusLivro) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.autor = autor;
        this.condicaoEstado = condicaoEstado;
        this.precoCreditos = precoCreditos;
        this.fotoCapa = fotoCapa;
        this.statusLivro = statusLivro;
    }

    public Livro(int idUsuario, String titulo, String autor, String condicaoEstado, double precoCreditos, String fotoCapa) {
        this.idUsuario = idUsuario;
        this.titulo = titulo;
        this.autor = autor;
        this.condicaoEstado = condicaoEstado;
        this.precoCreditos = precoCreditos;
        this.fotoCapa = fotoCapa;
        this.statusLivro = LivroStatus.DISPONIVEL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCondicaoEstado() {
        return condicaoEstado;
    }

    public void setCondicaoEstado(String condicaoEstado) {
        this.condicaoEstado = condicaoEstado;
    }

    public double getPrecoCreditos() {
        return precoCreditos;
    }

    public void setPrecoCreditos(double precoCreditos) {
        this.precoCreditos = precoCreditos;
    }

    public String getFotoCapa() {
        return fotoCapa;
    }

    public void setFotoCapa(String fotoCapa) {
        this.fotoCapa = fotoCapa;
    }

    public LivroStatus getStatusLivro() {
        return statusLivro;
    }

    public void setStatusLivro(LivroStatus statusLivro) {
        this.statusLivro = statusLivro;
    }
}
