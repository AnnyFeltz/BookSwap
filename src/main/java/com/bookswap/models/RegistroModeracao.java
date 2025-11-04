package com.bookswap.models;

import java.time.LocalDate;


import com.bookswap.models.subModels.ModeracaoAcao;

public class RegistroModeracao {
    private int id;
    private int idUsuarioModerador;
    private int idUsuario;
    private ModeracaoAcao tipoAcao;
    private String motivo;
    private LocalDate dataAcao;

    public RegistroModeracao(){
        // X﹏X
    }

    public RegistroModeracao(int id, int idUsuarioModerador, int idUsuario, ModeracaoAcao tipoAcao, String motivo, LocalDate dataAcao) {
        this.id = id;
        this.idUsuarioModerador = idUsuarioModerador;
        this.idUsuario = idUsuario;
        this.tipoAcao = tipoAcao;
        this.motivo = motivo;
        this.dataAcao = dataAcao;
    }

    public RegistroModeracao(int idUsuarioModerador, int idUsuario, ModeracaoAcao tipoAcao, String motivo) {
        this.idUsuarioModerador = idUsuarioModerador;
        this.idUsuario = idUsuario;
        this.tipoAcao = tipoAcao;
        this.motivo = motivo;
        this.dataAcao = LocalDate.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuarioModerador() {
        return idUsuarioModerador;
    }

    public void setIdUsuarioModerador(int idUsuarioModerador) {
        this.idUsuarioModerador = idUsuarioModerador;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public ModeracaoAcao getTipoAcao() {
        return tipoAcao;
    }

    public void setTipoAcao(ModeracaoAcao tipoAcao) {
        this.tipoAcao = tipoAcao;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDate getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(LocalDate dataAcao) {
        this.dataAcao = dataAcao;
    }
}
