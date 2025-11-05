<!DOCTYPE html>
<html lang="pt">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - BookSwap</title>
  <link rel="stylesheet" href="/static/styles/styles.css">
</head>

<body>

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
    <div class="dashboard-toolbar">
      <h2 style="background-color: white; border-radius: 100px; padding: 1vh 2vw;">
        Explore os Livros Disponíveis
      </h2>

      <div class="search-container">
        <input type="text" class="search-input" placeholder="Buscar por título, autor ou ISBN...">
        <button class="filter-btn">
          <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2"
            stroke-linecap="round" stroke-linejoin="round">
            <polygon points="22 3 2 3 10 12.46 10 19 14 21 14 12.46 22 3"></polygon>
          </svg>
          Filtros
        </button>
      </div>
    </div>

    <div class="book-grid">
      <#if livrosDisponiveis?? && livrosDisponiveis?has_content>
        <#list livrosDisponiveis as livro>
          <a href="/livroPraTroca/${livro.id}" class="book-card-link">
            <div class="book-cover">
              <#assign fotoUrl = livro.fotoCapa!>
              <img src="${fotoUrl?has_content?then(fotoUrl, '/static/img/default-profile.png')}" 
                   alt="Capa do livro ${livro.titulo?html}">
              <div class="bookmark <#if livro.statusLivro == 'EM_TROCA'>saved</#if>"></div> 
            </div>
            <div class="book-info">
              <h3 class="book-title">${livro.titulo?html}</h3>
              <p class="book-author">${livro.autor?html}</p>
              <p class="book-credits">${livro.precoCreditos} créditos</p>
            </div>
          </a>
        </#list>
      <#else>
        <p>Nenhum livro disponível para troca no momento. Cadastre o seu!</p>
      </#if>
    </div>

  </main>

  <footer class="site-footer">
    <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
  </footer>

</body>

</html>
