<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro - BookSwap</title>
    <link rel="stylesheet" href="/styles/styles.css">
</head>
<body>
    <div>
        <h1>Criar Conta</h1>

        <form method="POST" action="/register">
            <label for="nome">Nome Completo</label>
            <input type="text" id="nome" name="nome" required>

            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" required>

            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" required>
            
            <button type="submit">Registrar</button>
        </form>

        <div>
            Já tem uma conta? <a href="/login">Faça Login</a>
        </div>
    </div>
</body>
</html>