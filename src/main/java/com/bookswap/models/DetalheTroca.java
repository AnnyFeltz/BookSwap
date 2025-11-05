package com.bookswap.models;

import com.bookswap.models.subModels.LivroStatus;

public class DetalheTroca {
    private int id;
    private int idTroca;
    private int idLivroOfertado;
    private double creditosOfertados;
    private LivroStatus statusLivro;
    
    public DetalheTroca() {
        // (U.U )...zzz
    }

    public DetalheTroca(int id, int idTroca, int idLivroOfertado, double creditosOfertados, LivroStatus statusLivro) {
        this.id = id;
        this.idTroca = idTroca;
        this.idLivroOfertado = idLivroOfertado;
        this.creditosOfertados = creditosOfertados;
        this.statusLivro = statusLivro;
    }

    public DetalheTroca(int idTroca, int idLivroOferta, double creditosOfertados, LivroStatus statusLivro) {
        this.idTroca = idTroca;
        this.idLivroOfertado = idLivroOfertado;
        this.creditosOfertados = creditosOfertados;
        this.statusLivro = statusLivro;
    }

    public DetalheTroca(int idTroca, int idLivroOfertado) {
        this.idTroca = idTroca;
        this.idLivroOfertado = idLivroOfertado;
        this.creditosOfertados = 0.0;
        this.statusLivro = LivroStatus.DISPONIVEL;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getIdTroca() {
        return idTroca;
    }
    public void setIdTroca(int idTroca) {
        this.idTroca = idTroca;
    }
    public int getIdLivroOfertado() {
        return idLivroOfertado;
    }
    public void setIdLivroOfertado(int idLivroOfertado) {
        this.idLivroOfertado = idLivroOfertado;
    }
    public double getCreditosOfertados() {
        return creditosOfertados;
    }
    public void setCreditosOfertados(double creditosOfertados) {
        this.creditosOfertados = creditosOfertados;
    }
    public LivroStatus getStatusLivro() {
        return statusLivro;
    }
    public void setStatusLivro(LivroStatus statusLivro) {
        this.statusLivro = statusLivro;
    }
}
