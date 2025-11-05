<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${livro.titulo?html} - BookSwap</title>
    <link rel="stylesheet" href="/static/styles/styles.css">
    <style>
        .book-detail-container {
            display: flex;
            gap: 40px;
            max-width: 1000px;
            margin: 40px auto;
            background-color: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.1);
        }
        .book-image-area {
            flex-shrink: 0;
            width: 300px;
        }
        .book-image-area img {
            width: 100%;
            height: auto;
            border-radius: 6px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .book-info-area {
            flex-grow: 1;
        }
        .book-info-area h1 {
            color: #333;
            margin-top: 0;
            font-size: 2.5em;
        }
        .book-info-area h2 {
            color: #555;
            font-size: 1.5em;
            font-weight: normal;
            margin-bottom: 20px;
        }
        .info-group p {
            margin: 10px 0;
            font-size: 1.1em;
            color: #666;
        }
        .info-group strong {
            color: #333;
            font-weight: bold;
        }
        .owner-info {
            border-top: 1px solid #eee;
            margin-top: 20px;
            padding-top: 15px;
        }
        .owner-info a {
            color: #007bff;
            text-decoration: none;
            font-weight: bold;
        }
        .price-tag {
            background-color: #28a745;
            color: white;
            padding: 10px 20px;
            border-radius: 5px;
            font-size: 1.8em;
            font-weight: bold;
            display: inline-block;
            margin-top: 15px;
            margin-bottom: 30px;
        }
        .trade-button {
            padding: 15px 30px;
            background-color: #ffc107;
            color: #333;
            border: none;
            border-radius: 6px;
            font-size: 1.2em;
            font-weight: bold;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .trade-button:hover {
            background-color: #e0a800;
        }
        .status-badge {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 4px;
            font-size: 0.9em;
            font-weight: bold;
            margin-left: 10px;
        }
        .status-DISPONIVEL { background-color: #28a745; color: white; }
        .status-EM_TROCA { background-color: #ffc107; color: #333; }
        .status-INDISPONIVEL { background-color: #dc3545; color: white; }
    </style>
</head>

<body>
    <header class="site-header">
        <h1>BookSwap</h1>
        <nav>
            <a href="/">Início</a>
            <a href="/perfil">Meu Perfil</a>
            <form method="POST" action="/logout">
                <button type="submit" class="logout-button-top">Sair</button>
            </form>
        </nav>
    </header>

    <main class="site-content">
        <div class="book-detail-container">
            
            <div class="book-image-area">
                <#assign imagemUrl = livro.getFotoCapa()!>
                <img src="${imagemUrl?has_content?then(imagemUrl, '/target/classes/public/img/A_Menina.jpg')}" 
                     alt="Capa do livro ${livro.titulo?html}">
            </div>

            <div class="book-info-area">
                <h1>${livro.titulo?html}</h1>
                <h2>${livro.autor?html}</h2>
                
                <div class="price-tag">
                    ${livro.precoCreditos?string["0.00"]} Créditos
                </div>

                <div class="info-group">
                    <p><strong>Condição:</strong> ${livro.condicaoEstado?html}</p>
                    <p>
                        <strong>Status:</strong> 
                        <span class="status-badge status-${livro.statusLivro?html}">${livro.statusLivro?html}</span>
                    </p>
                </div>
                
                <div class="owner-info">
                    <p><strong>Dono:</strong> 
                        <a href="/usuario/${dono.id}">${dono.nome?html}</a>
                    </p>
                </div>

                <#if userLogado.id != livro.idUsuario && livro.statusLivro == "DISPONIVEL">
                    <form action="/troca/iniciar/${livro.id}" method="POST" style="margin-top: 20px;">
                        <button type="submit" class="trade-button">
                            TROCAR por ${livro.PrecoCreditos?string["0.00"]} Créditos
                        </button>
                    </form>
                <#elseif userLogado.id == livro.idUsuario>
                    <p style="margin-top: 20px; color: #007bff; font-weight: bold;">Este é um dos seus livros.</p>
                <#elseif livro.statusLivro != "DISPONIVEL">
                    <p style="margin-top: 20px; color: orange; font-weight: bold;">Este livro não está disponível para troca no momento.</p>
                </#if>
            </div>
        </div>
    </main>

    <footer class="site-footer">
        <p>&copy; 2025 BookSwap. Todos os direitos reservados.</p>
    </footer>

</body>
</html>