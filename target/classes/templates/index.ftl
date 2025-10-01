<!DOCTYPE html>
<html lang="pt">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - BookSwap</title>
  <link rel="stylesheet" href="/styles/styles.css">
  <style>


    header.site-header {
      background-color: #948AC9; /* roxo do topo do register */
      color: white;
    }


    .book-card {
      background-color: #ffffffff; /* mesmo azul do card do register */
    }

    .book-title {
      color: #000000ff;
    }
  </style>
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

      <div class="book-card">
        <div class="book-cover">
          <img src="/img/HarryPotter1.jpg" alt="Capa do livro Harry Potter e a Pedra Filosofal">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">Harry Potter e a Pedra Filosofal</h3>
          <p class="book-author">J.K ROWLING</p>
        </div>
      </div>

      <div class="book-card">
        <div class="book-cover">
          <img src="/img/A_Menina.jpg" alt="Capa do livro A Menina Do Outro Lado">
          <div class="bookmark"></div>
        </div>
        <div class="book-info">
          <h3 class="book-title">A Menina do Outro Lado</h3>
          <p class="book-author">Nagabe</p>
        </div>
      </div>

        <div class="book-card">
    <div class="book-cover">
      <img src="/img/download (2).jpg" alt="Capa do livro Percy Jackson O Ladrão de Raios">
      <div class="bookmark"></div>
    </div>
    <div class="book-info">
      <h3 class="book-title">Percy Jackson O Ladrão de Raios</h3>
      <p class="book-author">Rick Riordan</p>
    </div>
  </div>

      <div class="book-card">
      <div class="book-cover">
        <img src="/img/Trono_Vidro.jpg" alt="Capa do livro Trono de Vidro: Império da Tempestade">
        <div class="bookmark"></div>
      </div>
      <div class="book-info">
        <h3 class="book-title">Trono de Vidro Império da Tempestade</h3>
        <p class="book-author">Sarah J. Maas</p>
      </div>
    </div>

        <!-- Heartstopper -->
    <div class="book-card">
      <div class="book-cover">
        <img src="/img/Heartstopper.jpg" alt="Capa do livro Heartstopper">
        <div class="bookmark"></div>
      </div>
      <div class="book-info">
        <h3 class="book-title">Heartstopper</h3>
        <p class="book-author">Alice Oseman</p>
      </div>
    </div>

    <!-- Verity -->
    <div class="book-card">
      <div class="book-cover">
        <img src="/img/Verity.jpg" alt="Capa do livro Verity">
        <div class="bookmark"></div>
      </div>
      <div class="book-info">
        <h3 class="book-title">Verity</h3>
        <p class="book-author">Colleen Hoover</p>
      </div>
    </div>

    <!-- Um Encanto Delicado -->
    <div class="book-card">
      <div class="book-cover">
        <img src="/img/Um Encanto Delicado.jpg" alt="Capa do livro Um Encanto Delicado">
        <div class="bookmark"></div>
      </div>
      <div class="book-info">
        <h3 class="book-title">Um Encanto Delicado</h3>
        <p class="book-author">Kristin Hannah</p>
      </div>
    </div>

    <!-- A Depressão é uma Borboleta Azul -->
    <div class="book-card">
      <div class="book-cover">
        <img src="/img/Borboleta.jpg" alt="Capa do livro A Depressão é uma Borboleta Azul">
        <div class="bookmark"></div>
      </div>
      <div class="book-info">
        <h3 class="book-title">A Depressão é uma Borboleta Azul</h3>
        <p class="book-author">Sabrina Cantele</p>
      </div>
    </div>



    </div>

  </main>

  <footer class="site-footer">
    <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
  </footer>

</body>

</html>