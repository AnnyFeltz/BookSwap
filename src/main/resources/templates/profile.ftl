<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil - BookSwap</title>
    <link rel="stylesheet" href="/styles/styles.css">
</head>
<body>

    <div class="app-header">
        <form method="POST" action="/logout">
            <button type="submit" class="logout-button-top">
                Logout
            </button>
        </form>

        <span class="header-title">Meu Perfil</span>

        <div class="credits-system">
            <span class="icon">★</span>
            <span>150 pts</span> 
            <#-- <span>**${user.credits}** pts</span>  -->
        </div>
    </div>
    
    <div class="profile-card">

        <div class="user-info-section">
            <div class="profile-pic">
                <img src="/img/f8e830e2-7a3f-4824-832f-6ac92445f6dd.jpg" alt="Ícone de Perfil">                
            </div>
            <div class="user-details">
                <h1>${user.nome}!</h1>
                <p>${user.email}</p>
            </div>
        </div>
        
        <div class="action-block">
            <button class="btn-update" onclick="document.getElementById('update-form-container').style.display='block'; this.parentNode.style.display='none';">
                Atualizar Perfil
            </button>
        </div>
        
        <div class="action-block" id="update-form-container" style="display: none;">
            <form method="POST" action="/perfil/update" class="update-form" id="update-form">
                <h4>Área de Atualização</h4>
                <p style="font-size: 0.8em; color: #999;">Deixe o campo em branco para manter o valor atual.</p>

                <label for="nome">Novo Nome:</label>
                <input type="text" id="nome" name="nome" placeholder="Seu novo nome">

                <label for="email">Novo E-mail:</label>
                <input type="email" id="email" name="email" placeholder="Seu novo e-mail">
                
                <button type="submit" class="btn-update">Salvar Alterações</button>
            </form>
        </div>

        <div class="action-block" >
            <form method="POST" action="/perfil/delete" onsubmit="return confirm('Tem certeza que deseja EXCLUIR sua conta? Esta ação é irreversível.');">
                <button type="submit" class="btn-delete">
                    Excluir Minha Conta
                </button>
            </form>
        </div>
    </div>
</body>
</html>