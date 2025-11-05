<#-- livro.ftl -->
<#include "base.ftl">

<#macro page_content>
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
                <div class="alert alert-success" role="alert">
                    Livro atualizado com sucesso!
                </div>
            </#if>
        </#if>

        <div class="card shadow-sm">
            <div class="card-body">
                <div class="row">
                    <div class="col-md-4 text-center">
                        <img src="${livro.fotoCapa}" class="img-fluid rounded" alt="Capa do Livro: ${livro.titulo}" style="max-height: 400px; object-fit: cover;">
                        
                        <h4 class="mt-3 text-primary">${livro.precoCreditos?string["0.00"]} Créditos</h4>
                        
                        <#if isDono>
                            <div class="mt-3">
                                <a href="/livro/${livro.id}/editar" class="btn btn-info btn-block mb-2">
                                    <i class="fas fa-edit"></i> Editar Livro
                                </a>
                                <a href="/livro/${livro.id}/deletar" class="btn btn-danger btn-block" onclick="return confirm('Tem certeza que deseja deletar este livro?');">
                                    <i class="fas fa-trash-alt"></i> Deletar Livro
                                </a>
                                <small class="text-muted d-block mt-2">Você é o dono deste livro.</small>
                            </div>
                        <#else>
                            <#if livro.statusLivro.name() == "DISPONIVEL">
                                <button type="button" class="btn btn-success btn-lg btn-block mt-3" data-toggle="modal" data-target="#modalTroca">
                                    <i class="fas fa-exchange-alt"></i> TROCAR AGORA
                                </button>
                                <small class="text-muted d-block mt-2">Seu saldo atual: **${creditosUsuario?string["0.00"]} Créditos**</small>
                            <#else>
                                <button class="btn btn-warning btn-lg btn-block mt-3" disabled>
                                    <i class="fas fa-clock"></i> ${livro.statusLivro.name()}
                                </button>
                                <small class="text-danger d-block mt-2">Este livro não está disponível para troca no momento.</small>
                            </#if>
                        </#if>

                    </div>
                    
                    <div class="col-md-8">
                        <h1 class="display-4">${livro.titulo}</h1>
                        <h3 class="text-muted">Por ${livro.autor}</h3>
                        
                        <hr>
                        
                        <div class="mb-3">
                            <i class="fas fa-user-circle text-secondary"></i>
                            **Dono:** <a href="/perfil/${dono.id}">${dono.nome}</a>
                            <#if dono.fotoPerfil??>
                                <img src="${dono.fotoPerfil}" alt="Foto de Perfil do Dono" class="rounded-circle ml-2" style="width: 25px; height: 25px; object-fit: cover;">
                            </#if>
                        </div>
                        
                        <div class="mb-3">
                            <i class="fas fa-book-open text-secondary"></i>
                            **Condição:** ${livro.condicaoEstado}
                        </div>
                        
                        <div class="mb-4">
                            <i class="fas fa-sync-alt text-secondary"></i>
                            **Status:** <span class="badge badge-primary">${livro.statusLivro.name()}</span>
                        </div>
                        
                        <h3>Descrição</h3>
                        <p class="lead">
                            *A descrição detalhada do livro será inserida aqui. Este é um livro de ${livro.autor}, em estado ${livro.condicaoEstado}. Excelente oportunidade para troca!*
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <#if !isDono && livro.statusLivro.name() == "DISPONIVEL">
        <div class="modal fade" id="modalTroca" tabindex="-1" role="dialog" aria-labelledby="modalTrocaLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="modalTrocaLabel">Confirmar Troca por Créditos</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <form action="/troca/iniciar/${livro.id}" method="POST">
                        <div class="modal-body">
                            <p>Você está prestes a realizar a troca de **"${livro.titulo}"** por **${livro.precoCreditos?string["0.00"]} Créditos**.</p>
                            
                            <p>Seu saldo atual é de **${creditosUsuario?string["0.00"]} Créditos**.</p>
                            
                            <#if creditosUsuario < livro.precoCreditos>
                                <div class="alert alert-danger" role="alert">
                                    <i class="fas fa-exclamation-triangle"></i> **Saldo insuficiente!** Você precisa de mais **${(livro.precoCreditos - creditosUsuario)?string["0.00"]} Créditos** para esta troca.
                                </div>
                                <button type="submit" class="btn btn-success btn-block" disabled>
                                    TROCAR
                                </button>
                            <#else>
                                <div class="alert alert-info" role="alert">
                                    Ao confirmar, **${livro.precoCreditos?string["0.00"]} Créditos** serão debitados do seu saldo. O livro será enviado pelo dono.
                                </div>

                                <div class="form-group">
                                    <label for="idLivroOfertado">Selecione Seu Livro para Oferta:</label>
                                    <select class="form-control" id="idLivroOfertado" name="idLivroOfertado" required>
                                        <option value="">-- Escolha um de seus livros --</option>
                                        <#-- Substitua `meusLivrosDisponiveis` pela lista real que deve vir da Controller -->
                                        <#if meusLivrosDisponiveis?? && (meusLivrosDisponiveis?size > 0)>
                                            <#list meusLivrosDisponiveis as meuLivro>
                                                <#if meuLivro.id != livro.id>
                                                     <option value="${meuLivro.id}">${meuLivro.titulo} (${meuLivro.condicaoEstado})</option>
                                                </#if>
                                            </#list>
                                        <#else>
                                            <option value="" disabled>Nenhum livro seu disponível para troca.</option>
                                        </#if>
                                    </select>
                                </div>
                                
                                <button type="submit" class="btn btn-success btn-block mt-3" 
                                    <#if meusLivrosDisponiveis?? && (meusLivrosDisponiveis?size > 0) == false>disabled</#if>>
                                    <i class="fas fa-exchange-alt"></i> Iniciar Proposta de Troca
                                </button>
                            </#if>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </#if>

</#macro>