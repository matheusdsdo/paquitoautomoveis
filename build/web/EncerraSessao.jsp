<%
    session.invalidate();
    response.sendRedirect("ProdutoServlet?acao=listarTelaPrincipal");
%>