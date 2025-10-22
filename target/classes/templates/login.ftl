<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/styles/styles.css">
    <title>BookSwap - Login</title>
</head>

<body class="login-or-register-page">

    <main class="card">
        <div class="register-link">
            <a href="/register">Register ></a>
        </div>

        <div class="logo-section">
            <img src="/static/img/dia-mundial-do-livro.png">
            <h2>BookSwap</h2>
            <p>Ler, Trocar, Repetir</p>
        </div>

        <form method="POST" action="/login" class="form">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" placeholder="Digite aqui..." required>

            <label for="senha">Password</label>
            <input type="password" id="senha" name="senha" placeholder="Digite aqui..." required>

            <button type="submit" class="btn">Login</button>
        </form>

        <a href="#" class="forgot-link">Forgot password?</a>
        
    </main>

</body>

</html>