package com.bookswap.controllers;

import com.bookswap.models.DetalheTroca;
import com.bookswap.models.Livro;
import com.bookswap.models.Troca;
import com.bookswap.models.User;
import com.bookswap.models.subModels.LivroStatus;
import com.bookswap.models.subModels.TrocaStatus;
import com.bookswap.repository.DetalheTrocaRepository;
import com.bookswap.repository.LivroRepository;
import com.bookswap.repository.TrocaRepository;

import io.javalin.http.Context;

import java.util.List;


public class TrocaController {
    
    private final TrocaRepository trocaRepository;
    private final CreditoController creditoController; 
    private final DetalheTrocaRepository detalheTrocaRepository; 
    private final LivroRepository livroRepository;

    private static final double VALOR_CREDITO_TROCA = 5.0; 

    public TrocaController() {
        this.trocaRepository = new TrocaRepository();
        this.creditoController = new CreditoController(); 
        this.detalheTrocaRepository = new DetalheTrocaRepository();
        this.livroRepository = new LivroRepository();
    }

    public void iniciarTroca(Context ctx) {
        User userOfertante = ctx.sessionAttribute("user");
        
        if (userOfertante == null) {
            ctx.status(401).result("Acesso não autorizado. Faça login novamente");
            return;
        }

        String idLivroStr = ctx.pathParam("idLivro");
        String idLivroOfertadoStr = ctx.formParam("idLivroOfertado");

        if (idLivroStr == null || idLivroOfertadoStr == null || idLivroStr.isEmpty() || idLivroOfertadoStr.isEmpty()) {
            ctx.status(400).result("Livro de destino ou livro de oferta não especificado.");
            return;
        }
        
        int idLivroDestino, idLivroOfertado;
        try {
            idLivroDestino = Integer.parseInt(idLivroStr);
            idLivroOfertado = Integer.parseInt(idLivroOfertadoStr);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido (não é um número).");
            return;
        }
        
        Livro livroDestino = livroRepository.findById(idLivroDestino);
        Livro livroOfertado = livroRepository.findById(idLivroOfertado);
        
        if (livroDestino == null || livroOfertado == null) {
            ctx.status(404).result("Um dos livros não foi encontrado.");
            return;
        }

        if (livroDestino.getIdUsuario() == userOfertante.getId()) {
            ctx.status(400).result("Você não pode iniciar uma troca com um livro que você possui (Livro Destino).");
            return;
        }
        
        if (livroOfertado.getIdUsuario() != userOfertante.getId()) {
            ctx.status(403).result("Você só pode ofertar um livro que você possui.");
            return;
        }

        if (livroDestino.getStatusLivro() != LivroStatus.DISPONIVEL || livroOfertado.getStatusLivro() != LivroStatus.DISPONIVEL) {
             ctx.status(400).result("Um ou ambos os livros não estão disponíveis para troca.");
            return;
        }

        Troca novaTroca = new Troca(userOfertante.getId(), livroDestino.getIdUsuario());
        trocaRepository.save(novaTroca);

        DetalheTroca detalheOfertado = new DetalheTroca(novaTroca.getId(), idLivroOfertado); 
        detalheTrocaRepository.save(detalheOfertado);

        LivroStatus statusTroca = LivroStatus.EM_TROCA;
        
        livroDestino.setStatusLivro(statusTroca);
        livroOfertado.setStatusLivro(statusTroca);
        
        livroRepository.update(livroDestino);
        livroRepository.update(livroOfertado);

        ctx.redirect("/perfil?tab=trocas&msg=troca_iniciada");
    }

    public void aceitarTroca(Context ctx) {
        User userLogado = ctx.sessionAttribute("user");

        if (userLogado == null) {
            ctx.status(401).result("Acesso não autorizado.");
            return;
        }
        
        String idTrocaStr = ctx.pathParam("idTroca");
        int idTroca;
        
        if (idTrocaStr == null || idTrocaStr.isEmpty()) {
            ctx.status(400).result("ID da troca ausente na URL.");
            return;
        }

        try {
            idTroca = Integer.parseInt(idTrocaStr);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID da troca inválido (não é um número).");
            return;
        }
        
        Troca troca = trocaRepository.findById(idTroca);

        if (troca == null || troca.getStatusTroca() != TrocaStatus.PENDENTE) {
            ctx.status(400).result("Troca inválida ou já aceita/recusada.");
            return;
        }
        
        if (troca.getIdUsuarioRecebendo() != userLogado.getId()) {
            ctx.status(403).result("Você não é o destinatário desta troca.");
            return;
        }

        troca.setStatusTroca(TrocaStatus.ACEITA);
        trocaRepository.update(troca);

        ctx.redirect("/perfil?tab=trocas&msg=troca_aceita");
    }

    public void recusarTroca(Context ctx) {
        User userLogado = ctx.sessionAttribute("user");

        if (userLogado == null) {
            ctx.status(401).result("Acesso não autorizado.");
            return;
        }
        
        String idTrocaStr = ctx.pathParam("idTroca");
        int idTroca;
        
        if (idTrocaStr == null || idTrocaStr.isEmpty()) {
            ctx.status(400).result("ID da troca ausente na URL.");
            return;
        }

        try {
            idTroca = Integer.parseInt(idTrocaStr);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID da troca inválido (não é um número).");
            return;
        }
        
        Troca troca = trocaRepository.findById(idTroca);

        if (troca == null || troca.getStatusTroca() != TrocaStatus.PENDENTE) {
            ctx.status(400).result("Troca inválida ou já aceita/recusada.");
            return;
        }
        
        if (troca.getIdUsuarioRecebendo() != userLogado.getId()) {
            ctx.status(403).result("Você não é o destinatário desta troca.");
            return;
        }

        troca.setStatusTroca(TrocaStatus.CANCELADA);
        trocaRepository.update(troca);

        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);
        for (DetalheTroca detalhe : detalhes) {
            Livro livroOfertado = livroRepository.findById(detalhe.getIdLivroOfertado());
            if (livroOfertado != null) {
                livroOfertado.setStatusLivro(LivroStatus.DISPONIVEL); 
                livroRepository.update(livroOfertado);
            }
        }


        ctx.redirect("/perfil?tab=trocas&msg=troca_recusada");
    }
    
    public void confirmarRecebimento(Context ctx) {
        User userLogado = ctx.sessionAttribute("user");

        if (userLogado == null) {
            ctx.status(401).result("Acesso não autorizado.");
            return;
        }
        
        String idTrocaStr = ctx.pathParam("idTroca");
        int idTroca;
        
        if (idTrocaStr == null || idTrocaStr.isEmpty()) {
            ctx.status(400).result("ID da troca ausente na URL.");
            return;
        }

        try {
            idTroca = Integer.parseInt(idTrocaStr);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID da troca inválido (não é um número).");
            return;
        }
        
        Troca troca = trocaRepository.findById(idTroca);

        if (troca == null || troca.getStatusTroca() != TrocaStatus.ACEITA) {
            ctx.status(400).result("Troca inválida ou ainda não aceita.");
            return;
        }
        
        if (troca.getIdUsuarioRecebendo() != userLogado.getId()) {
            ctx.status(403).result("A confirmação deve ser feita por quem está recebendo o livro.");
            return;
        }
        
        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);

        if (detalhes.isEmpty()) {
            ctx.status(500).result("Erro interno: Nenhum livro envolvido na troca.");
            return;
        }
        
        DetalheTroca detalheLivroOfertado = detalhes.get(0); 

        detalheLivroOfertado.setStatusLivro(LivroStatus.TROCADO); 
        detalheTrocaRepository.update(detalheLivroOfertado);
        
        Livro livroOfertado = livroRepository.findById(detalheLivroOfertado.getIdLivroOfertado());
        if (livroOfertado != null) {
            livroOfertado.setIdUsuario(troca.getIdUsuarioRecebendo());
            livroOfertado.setStatusLivro(LivroStatus.DISPONIVEL); 
            livroRepository.update(livroOfertado);
        }
        
        creditoController.adicionarCredito(troca.getIdUsuarioOfertando(), VALOR_CREDITO_TROCA);
        
        troca.setStatusTroca(TrocaStatus.CONCLUIDA);
        trocaRepository.update(troca);

        ctx.redirect("/perfil?tab=trocas&msg=troca_finalizada");
    }
    
    public void cancelarTroca(Context ctx) {
        User userLogado = ctx.sessionAttribute("user");

        if (userLogado == null) {
            ctx.status(401).result("Acesso não autorizado.");
            return;
        }
        
        String idTrocaStr = ctx.pathParam("idTroca");
        int idTroca;
        
        if (idTrocaStr == null || idTrocaStr.isEmpty()) {
            ctx.status(400).result("ID da troca ausente na URL.");
            return;
        }

        try {
            idTroca = Integer.parseInt(idTrocaStr);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID da troca inválido (não é um número).");
            return;
        }
        
        Troca troca = trocaRepository.findById(idTroca);

        if (troca == null || troca.getStatusTroca() != TrocaStatus.PENDENTE) {
            ctx.status(400).result("Troca inválida ou já finalizada.");
            return;
        }

        if (troca.getIdUsuarioOfertando() != userLogado.getId() && troca.getIdUsuarioRecebendo() != userLogado.getId()) {
            ctx.status(403).result("Você não faz parte desta troca.");
            return;
        }

        troca.setStatusTroca(TrocaStatus.CANCELADA);
        trocaRepository.update(troca);
        
        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);
        for (DetalheTroca detalhe : detalhes) {
            detalhe.setStatusLivro(LivroStatus.DISPONIVEL); 
            detalheTrocaRepository.update(detalhe);
            
            Livro livro = livroRepository.findById(detalhe.getIdLivroOfertado());
            if (livro != null) {
                livro.setStatusLivro(LivroStatus.DISPONIVEL);
                livroRepository.update(livro);
            }
        }

        ctx.redirect("/perfil?tab=trocas&msg=troca_cancelada");
    }
}