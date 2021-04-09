<%-- 
    Document   : TelaAdministrativa
    Created on : 29/05/2019, 18:13:03
    Author     : mathe
--%>

<%@page import="controller.ProdutoServlet"%>
<%@page import="dao.Conexao"%>
<%@page import="model.ProdutoBean"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="model.ClienteBean"%>
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
            <h5 href="TelaBoasVindas.jsp" class="my-0 mr-md-auto font-weight-normal">Paquito Automóveis</h5>
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
                <%
                if (session.getAttribute("usuario") != null || (session.getAttribute("administrador")) != null) {                                   
                %>
                    <a class="p-2 text-dark" href="Carrinho.jsp">Carrinho</a>
                <% }
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
            <p class="lead">Melhor opção para compra e venda de automóveis</p>
        </div>
    <center>
                         <%
                    
                        if ((session.getAttribute("usuario") != null) || (session.getAttribute("administrador")) != null) {
                            if (session.getAttribute("administrador")!= null) {
                                    out.println("Bem vindo senhor administrador!");
                                } else {
                                out.println("Bem vindo, "+ (session.getAttribute("usuario")));
                            }
                   
                        } else {
                    %>
                   
                        Você não está logado, crie sua conta ou faça login para comprar carros burgueses.
                        <br><br>
                    </center>
                    <%
                    }
                    %>
    <br><br>
    <center>
         <div class="container">   
        <div class="card-deck mb-3 text-center">
            <%
               /* ProdutoServlet ps = new ProdutoServlet();
                ps.listarPrincipal(request, response);*/
                List<ProdutoBean> listaProduto = (List<ProdutoBean>)request.getAttribute("listarTelaPrincipal");
                int c = 0;
                Iterator i = listaProduto.iterator();
                while(i.hasNext()){
                    //Cria uma instancia do bean cliente
                    ProdutoBean produto = (ProdutoBean) i.next();
            %>

                

            <div class="card mb-4 shadow-sm" style="max-width: 20rem" >

                <div class="card-header" >
                    <form action="ProdutoServlet?acao=addCarrinho&id=<%=produto.getCodigo()%>" method="POST">
                        <h4 class="my-0 font-weight-normal"><%=produto.getNome()%></h4>
                        <input type="hidden" name="nome" value="<%=produto.getTipo()%>">
                        </div>
                        <center>
                            <div class="card-body">
                                <h1 class="card-title pricing-card-title"><img src="img/<%=produto.getImagem()%>" width="250" height="150"></h1>
                                <input type="hidden" name="img" value="<%=produto.getImagem()%>">
                                <ul class="list-unstyled mt-3 mb-4">
                                    <li>Tipo: <%=produto.getTipo()%></li>
                                    <input type="hidden" name="tipo" value="<%=produto.getTipo()%>">
                                    <li>Valor: R$<%= produto.getPreco()%></li>
                                    <input type="hidden" name="preco" value="<%=produto.getPreco()%>">
                                    <li>Em estoque: <%=produto.getQuantEstoque()%></li>
                                    <li>Descrição: <%=produto.getDescricao()%></li>
                                </ul>
                                <input type="submit" class="btn btn-lg btn-block btn-outline-primary" value="Adicionar ao Carrinho"></button>
                                </form>
                                <br>
                            </div>
                        </center>
                </div>

                <% //contador de carros por linha, chegou a 3 quebra a linha e o c volta a ser 0
                    }
                    c++;
                    if (c == 3) {
                %>
                <br>      
                <%
                        c = 0;
                    }
                %>

            </div>
            </center>
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
