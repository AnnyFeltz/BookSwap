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

        <div class="profile-card">
            <div class="profile-header">
                <h2>Meu Perfil</h2>
                <div class="credits-system"><span>★</span><span>${user.creditosDisponiveis?c} pts</span></div>
            </div>
            <div class="user-info-section">
                <div class="profile-pic">
                    <#assign fotoUrl = user.getFotoPerfilUrl()!>
                    <#if fotoUrl?has_content>
                        <img src="${fotoUrl}" alt="Foto de Perfil">
                    <#else>
                        <img src="/static/img/default-profile.png" alt="Foto Padrão">
                    </#if>
                </div>
                <div class="user-details">
                    <h1>${user.nome?html}!</h1>
                    <p>${user.email?html}</p>
                </div>
            </div>

            <div class="profile-actions">
                <label for="toggle-update" class="btn-secondary">Atualizar Dados</label>
                <label for="toggle-photo" class="btn-secondary">Trocar Foto</label>
                <form method="POST" action="/perfil/delete"
                    onsubmit="return confirm('Tem certeza que deseja EXCLUIR sua conta? Esta ação é irreversível.');">
                    <button type="submit" class="btn-danger">Excluir Conta</button>
                </form>
            </div>
        </div>

        <input type="checkbox" id="toggle-update" class="hidden-toggle">
        <div class="update-form-wrapper">
            <div class="update-form-container">
                <form method="POST" action="/perfil/update" class="update-form">
                    <h4>Área de Atualização</h4>
                    <label for="nome">Novo Nome:</label>
                    <input type="text" id="nome" name="nome" placeholder="Seu novo nome" value="${user.nome?html}">
                    <label for="email">Novo E-mail:</label>
                    <input type="email" id="email" name="email" placeholder="Seu novo e-mail" value="${user.email?html}">
                    <button type="submit" class="btn-primary">Salvar Alterações</button>
                </form>
            </div>
        </div>

        <input type="checkbox" id="toggle-photo" class="hidden-toggle">
        <div class="upload-form-wrapper">
            <div class="update-form-container upload-photo-container">
                <form method="POST" action="/perfil/upload-foto" class="upload-form" enctype="multipart/form-data">
                    <h4>Trocar Foto de Perfil</h4>
                    
                    <div class="photo-upload-preview">
                        <img id="preview-foto" 
                             src="${fotoUrl?has_content?then(fotoUrl, '/static/img/default-profile.png')}" 
                             alt="Pré-visualização da Foto">
                    </div>

                    <label for="foto">Escolha a Imagem:</label>
                    <input type="file" id="foto" name="foto" accept="image/*" required onchange="previewImagem(event)">
                    
                    <button type="submit" class="btn-primary">Fazer Upload</button>
                </form>
            </div>
        </div>

        <div class="profile-tabs-container">
            <input type="radio" name="profile-tabs" id="tab1" class="hidden-toggle" checked>
            <input type="radio" name="profile-tabs" id="tab2" class="hidden-toggle">

            <div class="tab-navigation">
                <label for="tab1" class="tab-link">Meus Livros</label>
                <label for="tab2" class="tab-link">Minhas Trocas</label>
            </div>

            <div class="tab-content-wrapper">
                <div id="meus-livros" class="tab-content">
                    <h3>Livros que você está oferecendo para troca:</h3>
                    <div class="book-grid profile-book-grid">
                        </div>
                </div>

                <div id="minhas-trocas" class="tab-content">
                    <h3>Histórico de Trocas</h3>
                    <ul class="trade-list">
                        </ul>
                </div>
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