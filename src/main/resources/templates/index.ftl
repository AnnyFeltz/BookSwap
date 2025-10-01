<!DOCTYPE html>
<html lang="pt">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - BookSwap</title>
  <link rel="stylesheet" href="/styles/styles.css">
</head>

<body>

  <header class="site-header">
    <h1>BookSwap</h1>
    <nav>
      <a href="/perfil">Meu Perfil</a>
      <form method="POST" action="/logout">
        <button type="submit" class="logout-button-top">
          Sair
        </button>
      </form>
    </nav>
  </header>

  <main class="site-content">


    <div class="dashboard-toolbar">
      <h2>Explore os Livros</h2>
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
          <img src="/img/o-programador-pragmatico.jpg" alt="Capa do livro O Programador Pragmático">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">O Programador Pragmático</h3>
          <p class="book-author">Andrew Hunt, David Thomas</p>
        </div>
      </div>

      <!-- Card 3 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/sapiens.jpg" alt="Capa do livro Sapiens: Uma Breve História da Humanidade">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">Sapiens: Uma Breve História da Humanidade</h3>
          <p class="book-author">Yuval Noah Harari</p>
        </div>
      </div>

      <!-- Card 4 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/o-senhor-dos-aneis.jpg" alt="Capa do livro O Senhor dos Anéis: A Sociedade do Anel">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">O Senhor dos Anéis: A Sociedade do Anel</h3>
          <p class="book-author">J.R.R. Tolkien</p>
        </div>
      </div>

      <!-- Card 5 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/arquitetura-limpa.jpg" alt="Capa do livro Arquitetura Limpa">
          <div class="bookmark saved"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">Arquitetura Limpa</h3>
          <p class="book-author">Robert C. Martin</p>
        </div>
      </div>

      <!-- Card 6 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/1984.jpg" alt="Capa do livro 1984">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">1984</h3>
          <p class="book-author">George Orwell</p>
        </div>
      </div>

      <!-- Card 7 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/o-poder-do-habito.jpg" alt="Capa do livro O Poder do Hábito">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">O Poder do Hábito</h3>
          <p class="book-author">Charles Duhigg</p>
        </div>
      </div>

      <!-- Card 8 -->
      <div class="book-card">
        <div class="book-cover">
          <img src="/img/mitologia-nordica.jpg" alt="Capa do livro Mitologia Nórdica">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">Mitologia Nórdica</h3>
          <p class="book-author">Neil Gaiman</p>
        </div>
      </div>

    </div>

  </main>

  <footer class="site-footer">
    <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
  </footer>

</body>

</html>