package com.bookswap.models;

public class Credito {
    private int idUsuario;
    private double saldoAtual;

    public Credito() {
        //   /\_/\
        // =(0 ω 0)=
    }

    public Credito(int idUsuario, double saldoAtual) {
        this.idUsuario = idUsuario;
        this.saldoAtual = saldoAtual;
    }

    public int getIdUsuario() {
        return idUsuario;
    }
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    public double getSaldoAtual() {
        return saldoAtual;
    }
    public void setSaldoAtual(double saldoAtual) {
        this.saldoAtual = saldoAtual;
    }
}
