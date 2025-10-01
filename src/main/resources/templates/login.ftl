<!DOCTYPE html>
<html lang="pt">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/styles/styles.css">
    <title>BookSwap - Login</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #dcf0fdff;
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: flex-start;
            min-height: 100vh;
        }

        .top-bar {
            width: 100%;
            height: 60px;
            background-color: #948AC9;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 0 0 20px 20px;
        }

        .logo-icon {
            height: 35px;
        }


        .card {
            background-color: #a4cfffff;
            margin-top: 40px;
            padding: 40px 35px;
            border-radius: 15px;
            width: 90%;
            max-width: 440px;
            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
            text-align: center;
            position: relative;
        }

        .register-link {
            position: absolute;
            top: 10px;
            right: 15px;
        }

        .register-link a {
            font-size: 0.9em;
            color: #0d47a1;
            text-decoration: none;
            font-weight: bold;
        }

        .logo-section img {
            width: 60px;
            margin-bottom: 10px;
        }

        .logo-section h2 {
            margin: 5px 0;
            font-size: 1.4em;
            color: #002f6c;
        }

        .logo-section p {
            font-size: 0.9em;
            color: #333;
            margin-bottom: 20px;
        }


        .form {
            display: flex;
            flex-direction: column;
            gap: 10px;
            text-align: left;
        }

        .form label {
            font-weight: bold;
            color: #002f6c;
            font-size: 0.9em;
        }

        .form input {
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #bbb;
            font-size: 0.9em;
        }

        .btn {
            margin-top: 15px;
            padding: 12px;
            background: #004c8c;
            color: white;
            border: none;
            border-radius: 25px;
            font-size: 1em;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.2s;
        }

        .btn:hover {
            background: #003366;
        }


        .forgot-link {
            display: block;
            margin-top: 15px;
            font-size: 0.8em;
            color: #333;
            text-decoration: none;
        }
    </style>
</head>

<body>

    <header class="top-bar">
        
    </header>

    <main class="card">
        <div class="register-link">
            <a href="/register">Register ></a>
        </div>

        <div class="logo-section">
            <img src="/img/dia-mundial-do-livro.png">
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