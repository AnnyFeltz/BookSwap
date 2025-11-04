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

    
}
