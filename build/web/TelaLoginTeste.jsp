<%-- 
    Document   : TelaAdministrativa
    Created on : 29/05/2019, 18:13:03
    Author     : mathe
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Paquito Automóveis</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/pricing/">

    <!-- Bootstrap core CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        -ms-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }
    </style>
        <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/sign-in/">
            <link href="https://getbootstrap.com/docs/4.3/examples/sign-in/signin.css" rel="stylesheet">
  </head>
  <body>
    <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
        <h5 class="my-0 mr-md-auto font-weight-normal">Paquito Automóveis</h5>
        <nav class="my-2 my-md-0 mr-md-3">
           
            <a class="p-2 text-dark" href="ProdutoServlet?acao=listarTelaPrincipal">Inicio</a>
            <a class="p-2 text-dark" href="TelaMostraCarros.jsp">Carros</a>

            <%-- Mostrar somente para o ususario administrador --%>
            <a class="p-2 text-dark" href="TelaAdministrativa.jsp">Área administrativa</a>
        </nav>
    <a class="btn btn-outline-primary" href="TelaLogin.jsp">Fazer Login</a>
    </div>

<div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
  <h1 class="display-4">Paquito automóveis</h1>
  <p class="lead">Melhor opção para compra e venda de automóveis</p>
</div>

  <div class="card-deck mb-3 text-center">
      
    <form class="form-signin">
  <img class="mb-4" src="/docs/4.3/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
  <h1 class="h3 mb-3 font-weight-normal">Please sign in</h1>
  <label for="inputEmail" class="sr-only">Email address</label>
  <input type="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
  <label for="inputPassword" class="sr-only">Password</label>
  <input type="password" id="inputPassword" class="form-control" placeholder="Password" required>
  <div class="checkbox mb-3">
    <label>
      <input type="checkbox" value="remember-me"> Remember me
    </label>
  </div>
  <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
  <p class="mt-5 mb-3 text-muted">&copy; 2017-2019</p>
</form>
  </div>

    <footer class="pt-4 my-md-5 pt-md-5 border-top">
    <div class="row">
      <div class="col-6 col-md">
        <img class="mb-2" src="/docs/4.3/assets/brand/bootstrap-solid.svg" alt="" width="24" height="24">
        <small class="d-block mb-3 text-muted">&copy; 2019</small>
      </div>
      <div class="col-6 col-md">
        <h5>Sobre nós</h5>
        <ul class="list-unstyled text-small">
          <li><a class="text-muted" href="#">Equipe</a></li>
          <li><a class="text-muted" href="#">Localização</a></li>
          <li><a class="text-muted" href="#">Termos de uso</a></li>
        </ul>
      </div>
    </div>
    </footer>
</div>
</body>
</html>
