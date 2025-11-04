package com.bookswap.models;

import java.time.LocalDate;

import com.bookswap.models.subModels.TrocaStatus;

public class Troca {
    private int id;
    private int idUsuarioOfertando;
    private int idUsuarioRecebendo;
    private LocalDate dataInicio;
    private LocalDate dataLimite;
    private TrocaStatus statusTroca;

    public Troca(){
        // []~(￣▽￣)~*
    }

    public Troca(int id, int idUsuarioOfertando, int idUsuarioRecebendo, LocalDate dataInicio, LocalDate dataLimite, TrocaStatus statusTroca) {
        this.id = id;
        this.idUsuarioOfertando = idUsuarioOfertando;
        this.idUsuarioRecebendo = idUsuarioRecebendo;
        this.dataInicio = dataInicio;
        this.dataLimite = dataLimite;
        this.statusTroca = statusTroca;
    }

    public Troca(int idUsuarioOfertando, int idUsuarioRecebendo) {
        this.idUsuarioOfertando = idUsuarioOfertando;
        this.idUsuarioRecebendo = idUsuarioRecebendo;
        this.dataInicio = LocalDate.now();
        this.dataLimite = null;
        this.statusTroca = TrocaStatus.PENDENTE;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuarioOfertando() {
        return idUsuarioOfertando;
    }

    public void setIdUsuarioOfertando(int idUsuarioOfertando) {
        this.idUsuarioOfertando = idUsuarioOfertando;
    }

    public int getIdUsuarioRecebendo() {
        return idUsuarioRecebendo;
    }

    public void setIdUsuarioRecebendo(int idUsuarioRecebendo) {
        this.idUsuarioRecebendo = idUsuarioRecebendo;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(LocalDate dataLimite) {
        this.dataLimite = dataLimite;
    }

    public TrocaStatus getStatusTroca() {
        return statusTroca;
    }

    public void setStatusTroca(TrocaStatus statusTroca) {
        this.statusTroca = statusTroca;
    }

    
}
