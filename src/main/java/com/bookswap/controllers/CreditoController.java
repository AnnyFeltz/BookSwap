package com.bookswap.controllers;

import com.bookswap.models.Credito;
import com.bookswap.repository.CreditoRepository;

public class CreditoController {

    private final CreditoRepository creditoRepository;

    public CreditoController() {
        this.creditoRepository = new CreditoRepository();
    }

    public void adicionarCredito(int idUsuario, double valor) {
        if (valor <= 0) {
            return; 
        }
        
        Credito credito = creditoRepository.findByIdUsuario(idUsuario);

        if (credito == null) {
            Credito novoCredito = new Credito(idUsuario, valor);
            creditoRepository.save(novoCredito);
        } else {
            double novoSaldo = credito.getSaldoAtual() + valor;
            credito.setSaldoAtual(novoSaldo);
            creditoRepository.update(credito);
        }
    }

    public boolean subtrairCredito(int idUsuario, double valor) {
        if (valor <= 0) {
            return false;
        }
        
        Credito credito = creditoRepository.findByIdUsuario(idUsuario);

        if (credito != null && credito.getSaldoAtual() >= valor) {
            double novoSaldo = credito.getSaldoAtual() - valor;
            credito.setSaldoAtual(novoSaldo);
            creditoRepository.update(credito);
            return true; 
        }

        return false; 
    }

    public double getSaldo(int idUsuario) {
        Credito credito = creditoRepository.findByIdUsuario(idUsuario);
        return (credito != null) ? credito.getSaldoAtual() : 0.0;
    }
}