<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil - BookSwap</title>
    <link rel="stylesheet" href="/static/styles/styles.css">

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

        <div class="details-card">
            <div class="detail-header">
                <h1>${livro.Titulo?html}</h1>
            </div>
            <div class="book-info-section">
                <div class="book-details">
                    <h3>${livro.Autor?html}!</h3>
                    <p>${livro.Condicao?html}</p>
                    <p>${livro.PrecoCreditos?html}</p>
                    <p>${livro.StatusLivro?html}</p>
                
                    <#assign imagemUrl = livro.getFotoCapa()!>
                    <#if fotoUrl?has_content>
                        <img src="${imagemUrl}" alt="imagem do livro">
                    <#else>
                        <img src="/target/classes/public/img/A_Menina.jpg" alt="Foto Padrão">
                    </#if>
            
                <form action="/trade">
                <button type="submit" class="trade-button">TRADE</button>
                </form>
                </div>
        </div>
    </main>

    <footer class="site-footer">
        <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
    </footer>

    <script>
        function previewImagem(event) {
            const input = event.target;
            const preview = document.getElementById('preview-foto');

            if (input.files && input.files[0]) {
                const reader = new FileReader();

                reader.onload = function(e) {
                    preview.src = e.target.result;
                }

                reader.readAsDataURL(input.files[0]);
            }
        }
    </script>

</body>

</html>