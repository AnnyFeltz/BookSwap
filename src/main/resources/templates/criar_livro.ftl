<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Novo Livro - BookSwap</title>
    <link rel="stylesheet" href="/static/styles/styles.css">
    <style>
        .form-container {
            max-width: 600px;
            margin: 40px auto;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .form-container h2 {
            text-align: center;
            color: #333;
            margin-bottom: 20px;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        .form-group input[type="text"], 
        .form-group input[type="number"], 
        .form-group select, 
        .form-group input[type="file"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-group input[type="file"] {
            padding: 3px;
        }
        .preview-image {
            display: block;
            max-width: 150px;
            height: auto;
            margin-top: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .submit-button {
            width: 100%;
            padding: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        .submit-button:hover {
            background-color: #0056b3;
        }
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
        <div class="form-container">
            <h2>Cadastrar Novo Livro</h2>

            <#if errorMessage?has_content>
                <p style="color: red; text-align: center;">${errorMessage}</p>
            </#if>

            <form action="/livro/cadastrar" method="POST" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="titulo">Título:</label>
                    <input type="text" id="titulo" name="titulo" required>
                </div>

                <div class="form-group">
                    <label for="autor">Autor:</label>
                    <input type="text" id="autor" name="autor" required>
                </div>

                <div class="form-group">
                    <label for="estadoCondicao">Condição/Estado de Conservação:</label>
                    <select id="estadoCondicao" name="estadoCondicao" required>
                        <option value="">Selecione a condição</option>
                        <option value="NOVO">Novo</option>
                        <option value="SEMI_NOVO">Seminovo</option>
                        <option value="BOM">Bom</option>
                        <option value="REGULAR">Regular</option>
                        <option value="DANIFICADO">Danificado</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="precoCreditos">Preço em Créditos:</label>
                    <input type="number" id="precoCreditos" name="precoCreditos" min="1" step="0.01" required>
                </div>

                <div class="form-group">
                    <label for="fotoCapa">Foto da Capa:</label>
                    <input type="file" id="fotoCapa" name="fotoCapa" accept="image/*" onchange="previewImagem(event)" required>
                    <img id="preview-foto" class="preview-image" src="#" alt="Pré-visualização da Capa" style="display: none;">
                </div>

                <button type="submit" class="submit-button">Cadastrar Livro</button>
            </form>
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
                preview.style.display = 'block'; 
                reader.onload = function(e) {
                    preview.src = e.target.result;
                }

                reader.readAsDataURL(input.files[0]);
            } else {
                preview.style.display = 'none';
                preview.src = '';
            }
        }
    </script>

</body>
</html>