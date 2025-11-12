<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detalhes do Livro: ${livro.titulo}</title>
    
    <!-- Bootstrap 4 funcional -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css">
    <link rel="stylesheet" href="/static/styles/styles.css">
</head>

<body>

    <!-- Header -->
    <header class="site-header">
      <h1>BookSwap</h1>
      <nav>
        <a href="/perfil">Meu Perfil</a>
        <a href="/livro/cadastrar" class="action-button">Cadastrar Livro</a> 
        <form method="POST" action="/logout">
          <button type="submit" class="logout-button-top">
            Sair
          </button>
        </form>
      </nav>
    </header>

    <main class="site-content">
        <div class="container mt-4">
            <div class="row">
                <div class="col-md-12">
                    <a href="/" class="btn btn-outline-secondary mb-3">
                        <i class="fas fa-arrow-left"></i> Voltar para a lista
                    </a>
                </div>
            </div>

            <#if ctx.queryParam("msg")??>
                <#if ctx.queryParam("msg") == "livro_atualizado">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="fas fa-check-circle"></i> Livro atualizado com sucesso!
                        <button type="button" class="close" data-dismiss="alert" aria-label="Fechar">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </#if>
            </#if>

            <div class="card shadow-sm mb-5">
                <div class="card-body">
                    <div class="row g-4">
                        <div class="col-md-4 text-center">
                            <img src="${livro.fotoCapa}" class="book-cover" alt="Capa do Livro: ${livro.titulo}">
                            <h4 class="mt-3 text-primary">${livro.precoCreditos?string["0.00"]} Créditos</h4>

                            <#if isDono>
                                <div class="mt-3 d-grid gap-2">
                                    <a href="/livro/${livro.id}/editar" class="btn btn-info btn-block mb-2">
                                        <i class="fas fa-edit"></i> Editar Livro
                                    </a>
                                    <a href="#" class="btn btn-danger btn-block" data-toggle="modal" data-target="#modalDeletar">
                                        <i class="fas fa-trash-alt"></i> Deletar Livro
                                    </a>
                                    <small class="text-muted d-block mt-2">Você é o dono deste livro.</small>
                                </div>
                            <#else>
                                <#if livro.statusLivro.name() == "DISPONIVEL">
                                    <button type="button" class="btn btn-success btn-lg btn-block mt-3" data-toggle="modal" data-target="#modalTroca">
                                        <i class="fas fa-exchange-alt"></i> TROCAR AGORA
                                    </button>
                                    <small class="text-muted d-block mt-2">Seu saldo atual: <strong>${creditosUsuario?string["0.00"]} Créditos</strong></small>
                                <#else>
                                    <button class="btn btn-warning btn-lg btn-block mt-3" disabled>
                                        <i class="fas fa-clock"></i> ${livro.statusLivro.name()}
                                    </button>
                                    <small class="text-danger d-block mt-2">Este livro não está disponível para troca no momento.</small>
                                </#if>
                            </#if>

                        </div>

                        <div class="col-md-8">
                            <h1 class="display-4 text-primary">${livro.titulo}</h1>
                            <h3 class="text-secondary">Por ${livro.autor}</h3>
                            <hr>

                            <div class="mb-3">
                                <i class="fas fa-user-circle text-primary"></i>
                                Dono: <a href="/perfil/${dono.id}" class="fw-bold text-dark">${dono.nome}</a>
                                <#if dono.fotoPerfil??>
                                    <img src="${dono.fotoPerfil}" class="rounded-circle ml-2" style="width: 25px; height: 25px; object-fit: cover;" alt="Foto do Dono">
                                </#if>
                            </div>

                            <div class="mb-3">
                                <i class="fas fa-book-open text-primary"></i> Condição: ${livro.condicaoEstado}
                            </div>

                            <div class="mb-4">
                                <i class="fas fa-sync-alt text-primary"></i> Status: <span class="badge badge-info">${livro.statusLivro.name()}</span>
                            </div>

                            <h3>Descrição</h3>
                            <p class="lead">${livro.descricao! "*Descrição não fornecida*"}</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal Troca -->
        <#if !isDono && livro.statusLivro.name() == "DISPONIVEL">
        <div class="modal fade" id="modalTroca" tabindex="-1" role="dialog" aria-labelledby="modalTrocaLabel" aria-hidden="true" data-backdrop="false">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-success text-white">
                        <h5 class="modal-title" id="modalTrocaLabel"><i class="fas fa-exchange-alt me-2"></i> Confirmar Troca</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/troca/iniciar/${livro.id}" method="POST">
                        <div class="modal-body">
                            <p>Você está prestes a adquirir "<strong>${livro.titulo}</strong>" por <strong>${livro.precoCreditos?string["0.00"]} Créditos</strong>.</p>
                            <p>Seu saldo atual: <strong>${creditosUsuario?string["0.00"]} Créditos</strong></p>

                            <#if creditosUsuario < livro.precoCreditos>
                                <div class="alert alert-danger" role="alert">
                                    <i class="fas fa-exclamation-triangle"></i> Saldo insuficiente!
                                </div>
                                <button type="submit" class="btn btn-success btn-block" disabled>TROCAR</button>
                            <#else>
                                <div class="alert alert-info" role="alert">
                                    Ao confirmar, ${livro.precoCreditos?string["0.00"]} Créditos serão debitados.
                                </div>

                                <div class="form-group">
                                    <label for="idLivroOfertado">Selecione seu livro para oferta (Opcional):</label>
                                    <select class="form-control" id="idLivroOfertado" name="idLivroOfertado">
                                        <option value="">-- Não ofertar livro (Apenas créditos) --</option>
                                        <#if meusLivrosDisponiveis?? && (meusLivrosDisponiveis?size > 0)>
                                            <#list meusLivrosDisponiveis as meuLivro>
                                                <#if meuLivro.id != livro.id>
                                                    <option value="${meuLivro.id}">${meuLivro.titulo} (${meuLivro.condicaoEstado})</option>
                                                </#if>
                                            </#list>
                                        <#else>
                                            <option value="" disabled>Você não tem livros disponíveis para oferta.</option>
                                        </#if>
                                    </select>
                                </div>

                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                                    <button type="submit" class="btn btn-success">
                                        <i class="fas fa-share-square"></i> Enviar Proposta de Troca
                                    </button>
                                </div>
                            </#if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        </#if>

        <!-- Modal Deletar -->
        <#if isDono>
        <div class="modal fade" id="modalDeletar" tabindex="-1" role="dialog" aria-labelledby="modalDeletarLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header bg-danger text-white">
                        <h5 class="modal-title" id="modalDeletarLabel"><i class="fas fa-trash-alt me-2"></i> Confirmar Deleção</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Fechar">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p>Tem certeza que deseja deletar o livro "<strong>${livro.titulo}</strong>"?</p>
                        <div class="alert alert-warning">Esta ação é irreversível.</div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancelar</button>
                        <a href="/livro/${livro.id}/deletar" class="btn btn-danger">
                            <i class="fas fa-trash-alt"></i> Deletar Permanentemente
                        </a>
                    </div>
                </div>
            </div>
        </div>
        </#if>

    </main>

    <footer class="site-footer bg-light text-center py-3 mt-5">
        <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

</body>
</html>
