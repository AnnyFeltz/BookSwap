<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Meu Perfil - BookSwap</title>
    <link rel="stylesheet" href="/styles/styles.css">
</head>
<body>

    <#-- 
    
    Eu faço o profile pode deixar 
    
    -->

    <div>
        <h1>Bem-vindo, ${user.nome}!</h1>

        <h2>Informações da Conta</h2>
        <div>
            <p><strong>Nome:</strong> ${user.nome}</p>
            <p><strong>E-mail:</strong> ${user.email}</p>
            <p><strong>Papel (Role):</strong> ${user.role}</p>
        </div>

        <#-- Formulário de Atualização de Perfil -->
        <form method="POST" action="/perfil/update">
            <h2>Atualizar Perfil</h2>
            <p>Deixe o campo em branco para manter o valor atual.</p>

            <label for="nome">Novo Nome:</label>
            <input type="text" id="nome" name="nome" placeholder="Seu novo nome">

            <label for="email">Novo E-mail:</label>
            <input type="email" id="email" name="email" placeholder="Seu novo e-mail">
            
            <button type="submit">Atualizar Dados</button>
        </form>

        <#-- Ações de Segurança -->

        <#-- Logout -->
        <form method="POST" action="/logout">
            <button type="submit" style="background-color: #6c757d;">Fazer Logout</button>
        </form>

        <#-- Excluir Conta -->
        <form method="POST" action="/perfil/delete" onsubmit="return confirm('Tem certeza que deseja EXCLUIR sua conta? Esta ação é irreversível.');">
            <button type="submit">Excluir Minha Conta</button>
        </form>
    </div>
</body>
</html>