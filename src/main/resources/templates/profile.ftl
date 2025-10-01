<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil - BookSwap</title>
    <link rel="stylesheet" href="/styles/styles.css">
</head>

<body>

    <header class="site-header">
        <h1>BookSwap</h1>
        <nav>
            <a href="/">Início</a>
            <form method="POST" action="/logout">
                <button type="submit" class="logout-button-top">Sair</button>
            </form>
        </nav>
    </header>

    <main class="site-content">

        <!-- CARD DE PERFIL -->
        <div class="profile-card">
            <div class="profile-header">
                <h2>Meu Perfil</h2>
                <div class="credits-system"><span>★</span><span>150 pts</span></div>
            </div>
            <div class="user-info-section">
                <div class="profile-pic">
                    <img src="/img/duck.jpg" alt="Foto de Perfil">
                </div>
                <div class="user-details">
                    <h1>${user.nome?html}!</h1>
                    <p>${user.email?html}</p>
                </div>
            </div>

            <!-- CONTROLE DO FORMULÁRIO DE ATUALIZAÇÃO -->
            <div class="profile-actions">
                <!-- 1. O botão agora é um LABEL que ativa o checkbox -->
                <label for="toggle-update" class="btn-secondary">Atualizar Perfil</label>
                <form method="POST" action="/perfil/delete"
                    onsubmit="return confirm('Tem certeza que deseja EXCLUIR sua conta? Esta ação é irreversível.');">
                    <button type="submit" class="btn-danger">Excluir Conta</button>
                </form>
            </div>
        </div>

        <!-- O formulário e seu controle ficam FORA do card principal, mas próximos no HTML -->
        <input type="checkbox" id="toggle-update" class="hidden-toggle">
        <div class="update-form-wrapper">
            <div class="update-form-container">
                <form method="POST" action="/perfil/update" class="update-form">
                    <h4>Área de Atualização</h4>
                    <label for="nome">Novo Nome:</label>
                    <input type="text" id="nome" name="nome" placeholder="Seu novo nome">
                    <label for="email">Novo E-mail:</label>
                    <input type="email" id="email" name="email" placeholder="Seu novo e-mail">
                    <button type="submit" class="btn-primary">Salvar Alterações</button>
                </form>
            </div>
        </div>

        <!-- CONTAINER DAS ABAS -->
        <div class="profile-tabs-container">
            <!-- 2. Inputs de rádio invisíveis para controlar as abas -->
            <input type="radio" name="profile-tabs" id="tab1" class="hidden-toggle" checked>
            <input type="radio" name="profile-tabs" id="tab2" class="hidden-toggle">

            <div class="tab-navigation">
                <!-- 3. As abas são LABELS que ativam os radios -->
                <label for="tab1" class="tab-link">Meus Livros</label>
                <label for="tab2" class="tab-link">Minhas Trocas</label>
            </div>

            <div class="tab-content-wrapper">
                <div id="meus-livros" class="tab-content">
                    <h3>Livros que você está oferecendo para troca:</h3>
                    <div class="book-grid profile-book-grid">
                        <!-- Card 1 -->
                        <div class="book-card">
                            <div class="book-cover">
                                <img src="/img/duna.jpg" alt="Capa do livro Duna">
                                <div class="bookmark saved"></div>
                            </div>
                            <div class="book-info">
                                <h3 class="book-title">Duna</h3>
                                <p class="book-author">Frank Herbert</p>
                            </div>
                        </div>

                        <!-- Card 2 -->
                        <div class="book-card">
                            <div class="book-cover">
                                <img src="/img/o-programador-pragmatico.jpg"
                                    alt="Capa do livro O Programador Pragmático">
                                <div class="bookmark"></div>
                            </div>
                            <div class="book-info">
                                <h3 class="book-title">O Programador Pragmático</h3>
                                <p class="book-author">Andrew Hunt, David Thomas</p>
                            </div>
                        </div>

                    </div>
                </div>

                <!-- Substitua a sua seção "minhas-trocas" por esta -->

                <div id="minhas-trocas" class="tab-content">
                    <h3>Histórico de Trocas</h3>
                    <ul class="trade-list">

                        <!-- Exemplo 1: Uma solicitação que VOCÊ FEZ e está pendente -->
                        <li class="trade-item pending">
                            <div class="trade-details">
                                <span class="trade-direction outgoing">▲ Você solicitou</span>
                                <p class="trade-info">
                                    O livro <span class="trade-book-title">"Duna"</span> para o usuário
                                    <strong>@carlos_s</strong>.
                                </p>
                            </div>
                            <div class="trade-status">Aguardando Resposta</div>
                        </li>

                        <!-- Exemplo 2: Uma solicitação que VOCÊ RECEBEU e está pendente -->
                        <li class="trade-item pending">
                            <div class="trade-details">
                                <span class="trade-direction incoming">▼ Você recebeu</span>
                                <p class="trade-info">
                                    Uma solicitação de <strong>@ana_leitora</strong> pelo seu livro <span
                                        class="trade-book-title">"Arquitetura Limpa"</span>.
                                </p>
                            </div>
                            <!-- Para solicitações recebidas, você pode adicionar botões de ação -->
                            <div class="trade-actions">
                                <button class="btn-accept">Aceitar</button>
                                <button class="btn-reject">Recusar</button>
                            </div>
                        </li>

                        <!-- Exemplo 3: Uma troca CONCLUÍDA -->
                        <li class="trade-item completed">
                            <div class="trade-details">
                                <span class="trade-direction outgoing">✔ Troca realizada</span>
                                <p class="trade-info">
                                    Você trocou <span class="trade-book-title">"1984"</span> com o usuário
                                    <strong>@leitor_voraz</strong>.
                                </p>
                            </div>
                            <div class="trade-status">Concluída</div>
                        </li>

                        <!-- Exemplo 4: Uma troca que foi RECUSADA -->
                        <li class="trade-item rejected">
                            <div class="trade-details">
                                <span class="trade-direction incoming">✖ Solicitação recusada</span>
                                <p class="trade-info">
                                    Você recusou a solicitação de <strong>@pedro_h</strong> pelo seu livro <span
                                        class="trade-book-title">"O Poder do Hábito"</span>.
                                </p>
                            </div>
                            <div class="trade-status">Recusada</div>
                        </li>

                    </ul>
                </div>

            </div>
        </div>
    </main>

    <footer class="site-footer">
        <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
    </footer>

</body>

</html>