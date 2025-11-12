package com.bookswap.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void mostrarSelecaoLivro(Context ctx) {
        User userLogado = ctx.sessionAttribute("user");
        if (userLogado == null) {
            ctx.redirect("/login");
            return;
        }
        String idLivroDesejadoStr = ctx.pathParam("id");
        if (idLivroDesejadoStr == null) {
            ctx.status(400).result("ID do livro desejado ausente.");
            return;
        }
        try {
            int idLivroDesejado = Integer.parseInt(idLivroDesejadoStr);
            Livro livroDesejado = livroRepository.findById(idLivroDesejado);
            if (livroDesejado == null) {
                ctx.status(404).result("Livro de destino não encontrado.");
                return;
            }
            if (livroDesejado.getIdUsuario() == userLogado.getId()) {
                ctx.status(400).result("Este é um dos seus livros. Use a tela de perfil para gerenciar.");
                return;
            }
            List<Livro> meusLivrosDisponiveis = livroRepository.findAvailableByUserId(userLogado.getId());
            Map<String, Object> model = new HashMap<>();
            model.put("livroDesejado", livroDesejado);
            model.put("meusLivros", meusLivrosDisponiveis);
            model.put("userLogado", userLogado);
            ctx.render("selecao_troca.ftl", model);
        } catch (NumberFormatException e) {
            ctx.status(400).result("ID do livro inválido.");
        } catch (RuntimeException e) {
            e.printStackTrace();
            ctx.status(500).result("Erro interno ao carregar a seleção de troca: " + e.getMessage());
        }
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
        // Verificar se os livros estão disponíveis
        if (livroDestino.getStatusLivro() != LivroStatus.DISPONIVEL || livroOfertado.getStatusLivro() != LivroStatus.DISPONIVEL) {
            ctx.status(400).result("Um ou ambos os livros não estão disponíveis para troca.");
            return;
        }
        Troca novaTroca = new Troca(userOfertante.getId(), livroDestino.getIdUsuario());
        trocaRepository.save(novaTroca);
        DetalheTroca detalheOfertado = new DetalheTroca(novaTroca.getId(), idLivroOfertado, idLivroDestino);
        detalheTrocaRepository.save(detalheOfertado);
        
        // Mudar status dos livros para EM_TROCA
        livroDestino.setStatusLivro(LivroStatus.EM_TROCA);
        livroOfertado.setStatusLivro(LivroStatus.EM_TROCA);
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

        // 🔹 TROCA REAL DE DONOS
        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);
        for (DetalheTroca detalhe : detalhes) {
            Livro livroOfertado = livroRepository.findById(detalhe.getIdLivroOfertado());
            Livro livroDesejado = livroRepository.findById(detalhe.getIdLivroDesejado());

            if (livroOfertado != null && livroDesejado != null) {
                // O Livro Desejado (que era do Recebedor) agora pertence ao Ofertante
                livroDesejado.setIdUsuario(troca.getIdUsuarioOfertando()); 
                
                // O Livro Ofertado (que era do Ofertante) agora pertence ao Recebedor
                livroOfertado.setIdUsuario(troca.getIdUsuarioRecebendo());

                // Mudar status do livro para DISPONIVEL (e não TROCADO, pois ele está disponível para nova troca)
                livroOfertado.setStatusLivro(LivroStatus.DISPONIVEL);
                livroDesejado.setStatusLivro(LivroStatus.DISPONIVEL);
                
                livroRepository.update(livroOfertado);
                livroRepository.update(livroDesejado);
            }
        }

        // Mudar status da Troca para ACEITA (ou CONCLUIDA, dependendo do fluxo)
        // Usaremos ACEITA para indicar que o envio deve ser processado.
        // O status CONCLUIDA será usado após o recebimento.
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
        
        // Apenas o destinatário (quem recebeu a oferta) pode recusar
        if (troca.getIdUsuarioRecebendo() != userLogado.getId()) {
             ctx.status(403).result("Você não tem permissão para recusar esta troca.");
             return;
        }

        troca.setStatusTroca(TrocaStatus.CANCELADA);
        trocaRepository.update(troca);
        
        // Ambos os livros voltam a DISPONIVEL e mantêm os donos originais
        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);
        for (DetalheTroca detalhe : detalhes) {
            Livro livroOfertado = livroRepository.findById(detalhe.getIdLivroOfertado());
            Livro livroDesejado = livroRepository.findById(detalhe.getIdLivroDesejado()); 
            
            if (livroOfertado != null) {
                livroOfertado.setStatusLivro(LivroStatus.DISPONIVEL);
                livroRepository.update(livroOfertado);
            }
            if (livroDesejado != null) { 
                livroDesejado.setStatusLivro(LivroStatus.DISPONIVEL);
                livroRepository.update(livroDesejado);
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
        // O status aqui deve ser o status anterior à finalização, que pode ser ENVIADA ou ENTREGUE
        if (troca == null || troca.getStatusTroca() != TrocaStatus.ENTREGUE) { 
            ctx.status(400).result("Troca inválida ou ainda não entregue.");
            return;
        }
        
        // Apenas o Ofertante (quem receberá o livro e dará os créditos) confirma,
        // mas como o livro já mudou de dono, o novo dono é quem confirma o recebimento do livro.
        // No seu fluxo, o IdUsuarioOfertando é o novo dono do livro que recebeu.
        if (troca.getIdUsuarioOfertando() != userLogado.getId()) { 
             ctx.status(403).result("Você não é o usuário que deve confirmar o recebimento desta troca.");
             return;
        }
        
        // Adicionar crédito ao Recebedor (quem enviou o livro)
        creditoController.adicionarCredito(troca.getIdUsuarioRecebendo(), VALOR_CREDITO_TROCA);
        
        // Mudar status para CONCLUIDA, marcando o fim da transação
        troca.setStatusTroca(TrocaStatus.CONCLUIDA);
        trocaRepository.update(troca);

        ctx.redirect("/perfil?tab=trocas&msg=recebimento_confirmado");
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
        
        // Apenas o Ofertante pode cancelar antes de ser aceita/recusada
        if (troca.getIdUsuarioOfertando() != userLogado.getId()) {
             ctx.status(403).result("Você não tem permissão para cancelar esta troca.");
             return;
        }

        troca.setStatusTroca(TrocaStatus.CANCELADA);
        trocaRepository.update(troca);
        
        // Ambos os livros voltam a DISPONIVEL e mantêm os donos originais
        List<DetalheTroca> detalhes = detalheTrocaRepository.findByTrocaId(idTroca);
        for (DetalheTroca detalhe : detalhes) {
            Livro livroOfertado = livroRepository.findById(detalhe.getIdLivroOfertado());
            Livro livroDesejado = livroRepository.findById(detalhe.getIdLivroDesejado());
            
            if (livroOfertado != null) {
                livroOfertado.setStatusLivro(LivroStatus.DISPONIVEL);
                livroRepository.update(livroOfertado);
            }
             if (livroDesejado != null) {
                livroDesejado.setStatusLivro(LivroStatus.DISPONIVEL);
                livroRepository.update(livroDesejado);
            }
        }
        
        ctx.redirect("/perfil?tab=trocas&msg=troca_cancelada");
    }
}