package com.bookswap.models;

import java.time.LocalDateTime;

public class User {
    private int id;
    private String nome;
    private String email;
    private String senha; 
    private LocalDateTime dataRegistro;
    private UserRole perfil; 
    private int creditosDisponiveis;
    private PenaltyStatus statusPenalidade;
    private String motivoPenalidade;
    private LocalDateTime dataInicioPenalidade;
    private LocalDateTime dataFimPenalidade;
    private String fotoPerfilUrl;

    public User(int id, String nome, String email, String senha, LocalDateTime dataRegistro, UserRole perfil, int creditosDisponiveis, PenaltyStatus statusPenalidade, String motivoPenalidade, LocalDateTime dataInicioPenalidade, LocalDateTime dataFimPenalidade, String fotoPerfilUrl) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataRegistro = dataRegistro;
        this.perfil = perfil;
        this.creditosDisponiveis = creditosDisponiveis;
        this.statusPenalidade = statusPenalidade;
        this.motivoPenalidade = motivoPenalidade;
        this.dataInicioPenalidade = dataInicioPenalidade;
        this.dataFimPenalidade = dataFimPenalidade;
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    public User(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataRegistro = LocalDateTime.now();
        this.perfil = UserRole.USUARIO;
        this.creditosDisponiveis = 0; 
        this.statusPenalidade = PenaltyStatus.ATIVO;
        this.motivoPenalidade = null;
        this.dataInicioPenalidade = null;
        this.dataFimPenalidade = null;
        this.fotoPerfilUrl = null;
    }

    public User() {
    }

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

    public UserRole getPerfil() {
        return perfil;
    }

    public void setPerfil(UserRole perfil) {
        this.perfil = perfil;
    }

    public int getCreditosDisponiveis() {
        return creditosDisponiveis;
    }

    public void setCreditosDisponiveis(int creditosDisponiveis) {
        this.creditosDisponiveis = creditosDisponiveis;
    }

    public PenaltyStatus getStatusPenalidade() {
        return statusPenalidade;
    }

    public void setStatusPenalidade(PenaltyStatus statusPenalidade) {
        this.statusPenalidade = statusPenalidade;
    }

    public String getMotivoPenalidade() {
        return motivoPenalidade;
    }

    public void setMotivoPenalidade(String motivoPenalidade) {
        this.motivoPenalidade = motivoPenalidade;
    }

    public LocalDateTime getDataInicioPenalidade() {
        return dataInicioPenalidade;
    }

    public void setDataInicioPenalidade(LocalDateTime dataInicioPenalidade) {
        this.dataInicioPenalidade = dataInicioPenalidade;
    }

    public LocalDateTime getDataFimPenalidade() {
        return dataFimPenalidade;
    }

    public void setDataFimPenalidade(LocalDateTime dataFimPenalidade) {
        this.dataFimPenalidade = dataFimPenalidade;
    }
    
    public String getFotoPerfilUrl() {
        return fotoPerfilUrl;
    }

    public void setFotoPerfilUrl(String fotoPerfilUrl) {
        this.fotoPerfilUrl = fotoPerfilUrl;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", dataRegistro=" + dataRegistro +
                ", perfil=" + perfil +
                ", creditosDisponiveis=" + creditosDisponiveis +
                ", statusPenalidade=" + statusPenalidade +
                (statusPenalidade != PenaltyStatus.ATIVO ? ", motivoPenalidade='" + motivoPenalidade + '\'' +
                                                         ", dataInicioPenalidade=" + dataInicioPenalidade +
                                                         ", dataFimPenalidade=" + dataFimPenalidade : "") +
                ", fotoPerfilUrl='" + fotoPerfilUrl + '\'' +
                '}';
    }
}