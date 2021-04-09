
<%@page import="model.ClienteBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="model.ProdutoBean"%>
<%@page import="java.util.List"%>
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
        <link href="css/pricing.css" rel="stylesheet">
    </head>
    <body>
        <div class="d-flex flex-column flex-md-row align-items-center p-3 px-md-4 mb-3 bg-white border-bottom shadow-sm">
            <h5 class="my-0 mr-md-auto font-weight-normal">Paquito Automóveis</h5>
            <nav class="my-2 my-md-0 mr-md-3">
                <a class="p-2 text-dark" href="ProdutoServlet?acao=listarTelaPrincipal">Inicio</a>

                <%-- Mostrar somente para o ususario administrador --%>
                <%
                    if (session.getAttribute("administrador") != null) {
                %>   
                <a class="p-2 text-dark" href="TelaAdministrativa.jsp">Área administrativa</a>
                <%
                    }
                %>
            </nav>
            <%
                if (session.getAttribute("usuario") == null && (session.getAttribute("administrador") == null)) {
            %>
            <a class="btn btn-outline-primary" href="TelaLogin.jsp">Fazer Login</a>
            <%
            } else {
            %>

            <div class="btn-group">
                <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                    <%                   if (session.getAttribute("administrador") != null) {
                            out.println("Administrador!");
                        } else {
                            out.println("Bem vindo, " + (session.getAttribute("usuario")));
                        }
                    %>
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="ClienteServlet?acao=atlzCliente&nome=<%= session.getAttribute("usuario")%>">Editar Perfil</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="EncerraSessao.jsp">Sair</a>
                </div>
            </div>

            <%
                }
            %>
        </div>

        <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
            <h1 class="display-4">Paquito automóveis</h1>
            <p class="lead">Carrinho de compras</p>
        </div>

        <div class="card-deck mb-3 text-center">

            <div class="card mb-4 shadow-sm">
                <center>
                    <table border="1">
                        <form action="ProdutoServlet?acao=comprar" method="POST">
                        <tr>
                            <th>Codigo</th>
                            <th>Nome</th>
                            <th>Tipo</th>
                            <th>Preço</th>
                            <th>Quantidade</th>
                            <th>Imagem</th>
                            <th>Remover</th>
                        </tr>
                        <%
                            List<ProdutoBean> produto = (List<ProdutoBean>) session.getAttribute("carrinho");
                            for (ProdutoBean pr : produto) {
                        %>
                        <tr>
                            <td><%=pr.getCodigo()%></td>
                            <td><%=pr.getNome()%></td>
                            <td><%=pr.getTipo()%></td>
                            <td><%=pr.getPreco()%></td>
                            <td><input type="number" name="quantidadeVenda" value="1" min="1" max="100"></td>
                            <td><img src="img/<%=pr.getImagem()%>" width="100" height="70"></td>
                            <td><a href="ProdutoServlet?acao=excluirProdutoCar&codigo=<%=pr.getCodigo()%>">Remover</a></td>
                            <td><input type="hidden" name="codigo" value="<%=pr.getCodigo()%>"></td>

                        </tr>
                        <%
                            }
                        %>
                    </table> <br><br>              
                    <input type="submit" class="btn btn-outline-primary" value="Comprar">
                    <br><br>
                    </form>
                    <br>
                </center>
            </div>

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
    
             <!-- IMPORTAÇÃO DO JS -->
         <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
         <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
         <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    
    
</body>
</html>
