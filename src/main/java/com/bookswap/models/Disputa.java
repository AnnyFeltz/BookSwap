package com.bookswap.models;

import java.time.LocalDate;
import com.bookswap.models.subModels.DisputaStatus;


public class Disputa {
    private int id;
    private int idTroca;
    private int idUsuario;
    private String motivoReclamacao;
    private DisputaStatus statusDisputa;
    private LocalDate dataAbertura;
    private String resultadoresolucao;
    
    public Disputa() {
        // 
    }
    
    public Disputa(int id, int idTroca, int idUsuario, String motivoReclamacao, DisputaStatus statusDisputa, LocalDate dataAbertura, String resultadoresolucao) {
        this.id = id;
        this.idTroca = idTroca;
        this.idUsuario = idUsuario;
        this.motivoReclamacao = motivoReclamacao;
        this.statusDisputa = statusDisputa;
        this.dataAbertura = dataAbertura;
        this.resultadoresolucao = resultadoresolucao;
    }

    public Disputa(int idTroca, int idUsuario, String motivoReclamacao, DisputaStatus statusDisputa, LocalDate dataAbertura, String resultadoresolucao) {
        this.idTroca = idTroca;
        this.idUsuario = idUsuario;
        this.motivoReclamacao = motivoReclamacao;
        this.statusDisputa = DisputaStatus.ABERTA;
        this.dataAbertura = LocalDate.now();
        this.resultadoresolucao = resultadoresolucao;
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
    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getMotivoReclamacao() {
        return motivoReclamacao;
    }
    public void setMotivoReclamacao(String motivoReclamacao) {
        this.motivoReclamacao = motivoReclamacao;
    }
    public DisputaStatus getStatusDisputa() {
        return statusDisputa;
    }
    public void setStatusDisputa(DisputaStatus statusDisputa) {
        this.statusDisputa = statusDisputa;
    }
    public LocalDate getDataAbertura() {
        return dataAbertura;
    }
    public void setDataAbertura(LocalDate dataAbertura) {
        this.dataAbertura = dataAbertura;
    }
    public String getResultadoresolucao() {
        return resultadoresolucao;
    }
    public void setResultadoresolucao(String resultadoresolucao) {
        this.resultadoresolucao = resultadoresolucao;
    }
}
