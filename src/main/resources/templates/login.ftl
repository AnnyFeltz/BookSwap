<!DOCTYPE html>
<html lang="pt">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - BookSwap</title>
    <link rel="stylesheet" href="/styles/styles.css">
</head>
<body>
    <div>
        <h1>Fazer Login</h1>

        <form method="POST" action="/login">
            <label for="email">E-mail</label>
            <input type="email" id="email" name="email" required>

            <label for="senha">Senha</label>
            <input type="password" id="senha" name="senha" required>

            <button type="submit">Entrar</button>
        </form>

        <div>
            Não tem uma conta? <a href="/register">Crie uma aqui</a>
        </div>
    </div>
</body>
</html>