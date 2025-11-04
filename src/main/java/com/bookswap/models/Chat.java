package com.bookswap.models;

public class Chat {
    private int id;
    private int idTroca;

    
    public Chat() {
        //~(￣▽￣)~*
    }

    public Chat(int id, int idTroca) {
        this.id = id;
        this.idTroca = idTroca;
    }

    public Chat(int idTroca) {
        this.idTroca = idTroca;
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
}
