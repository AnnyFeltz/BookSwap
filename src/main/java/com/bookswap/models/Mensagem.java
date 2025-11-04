package com.bookswap.models;

import java.time.LocalDateTime;

public class Mensagem {
    private int id;
    private int idUsuario;
    private int idChat;
    private String texto;
    private LocalDateTime data_hora;

    
    public Mensagem() {
        // ╰（‵□′）╯
    }

    
    public Mensagem(int id, int idUsuario, int idChat, String texto, LocalDateTime data_hora) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.idChat = idChat;
        this.texto = texto;
        this.data_hora = data_hora;
    }

    public Mensagem(int idUsuario, int idChat, String texto) {
        this.idUsuario = idUsuario;
        this.idChat = idChat;
        this.texto = texto;
        this.data_hora = LocalDateTime.now();
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

    public int getIdChat() {
        return idChat;
    }

    public void setIdChat(int idChat) {
        this.idChat = idChat;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public LocalDateTime getData_hora() {
        return data_hora;
    }

    public void setData_hora(LocalDateTime data_hora) {
        this.data_hora = data_hora;
    }
}