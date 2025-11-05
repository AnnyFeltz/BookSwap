package com.bookswap.models;

import java.time.LocalDate;

import com.bookswap.models.subModels.PacoteCompra;
import com.bookswap.models.subModels.TransacaoTipo;

public class Transacao {
    private int id;
    private int idUsuario;
    private TransacaoTipo tipo;
    private double valorCreditos;
    private LocalDate dataTransacao;
    private String origemDescricao;
    private PacoteCompra nomePacoteCompra;

    public Transacao() {
        // (ToT)/~~~$$
    }

    public Transacao(int id, int idUsuario, TransacaoTipo tipo, double valorCreditos, LocalDate dataTransacao, String origemDescricao, PacoteCompra nomePacoteCompra) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.valorCreditos = valorCreditos;
        this.dataTransacao = dataTransacao;
        this.origemDescricao = origemDescricao;
        this.nomePacoteCompra = nomePacoteCompra;
    }

    public Transacao(int idUsuario, TransacaoTipo tipo, double valorCreditos, String origemDescricao, PacoteCompra nomePacoteCompra) {
        this.idUsuario = idUsuario;
        this.tipo = tipo;
        this.valorCreditos = valorCreditos;
        this.dataTransacao = LocalDate.now();
        this.origemDescricao = origemDescricao;
        this.nomePacoteCompra = nomePacoteCompra;
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
    public TransacaoTipo getTipo() {
        return tipo;
    }
    public void setTipo(TransacaoTipo tipo) {
        this.tipo = tipo;
    }
    public double getValorCreditos() {
        return valorCreditos;
    }
    public void setValorCreditos(double valorCreditos) {
        this.valorCreditos = valorCreditos;
    }
    public LocalDate getDataTransacao() {
        return dataTransacao;
    }
    public void setDataTransacao(LocalDate dataTransacao) {
        this.dataTransacao = dataTransacao;
    }
    public String getOrigemDescricao() {
        return origemDescricao;
    }
    public void setOrigemDescricao(String origemDescricao) {
        this.origemDescricao = origemDescricao;
    }
    public PacoteCompra getNomePacoteCompra() {
        return nomePacoteCompra;
    }
    public void setNomePacoteCompra(PacoteCompra nomePacoteCompra) {
        this.nomePacoteCompra = nomePacoteCompra;
    }
}
