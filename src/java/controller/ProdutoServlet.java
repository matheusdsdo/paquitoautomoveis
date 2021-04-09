/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import dao.ProdutoDAO;
import dao.RelatorioDAO;
import dao.VendaDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ClienteBean;
import model.ProdutoBean;

/**
 *
 * @author Apa
 */
public class ProdutoServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String acao = request.getParameter("acao");
        RequestDispatcher rd = null;

        //instancia um objeto do tipo produto dao
        ProdutoDAO prod = new ProdutoDAO();

        if (acao.equalsIgnoreCase("addProduto")) {
            try {
                ProdutoBean produto = new ProdutoBean();
                produto.setNome(request.getParameter("nome"));
                produto.setTipo(request.getParameter("tipo"));
                produto.setDescricao(request.getParameter("descricao"));
                produto.setPreco(Double.parseDouble(request.getParameter("preco")));
                produto.setQuantEstoque(Integer.parseInt(request.getParameter("quantidade")));
                produto.setImagem(request.getParameter("img"));
                prod.addproduto(produto);

                List listaProduto = prod.ListaProduto();
                request.setAttribute("listaProduto", listaProduto);
                rd = request.getRequestDispatcher("Administrativa.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                System.out.println("Erro:" + e.getMessage());
            }

        } else if (acao.equalsIgnoreCase("listarTelaPrincipal")) {
            List listaProduto = prod.ListaProduto();
            request.setAttribute("listarTelaPrincipal", listaProduto);
            rd = request.getRequestDispatcher("TelaPrincipal.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("listarProduto")) {

            List listaProduto = prod.ListaProduto();
            request.setAttribute("listaProduto", listaProduto);
            rd = request.getRequestDispatcher("TelaListaCarros.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("excluirProduto")) {
            int cod = Integer.parseInt(request.getParameter("codigo"));
            prod.excluiProduto(cod);

            List listaProduto = prod.ListaProduto();
            request.setAttribute("listaProduto", listaProduto);
            rd = request.getRequestDispatcher("TelaListaCarros.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("atualizarProduto")) {
            int cod = Integer.parseInt(request.getParameter("codigo"));
            List linhaSelecionada = prod.selecionaLinha(cod);
            request.setAttribute("linhaSelecionada", linhaSelecionada);
            rd = request.getRequestDispatcher("TelaAtualizarProduto.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("atualizarProd")) {

            ProdutoBean produto = new ProdutoBean();
            //ecuperando parametros da tela atualiza produto.
            produto.setCodigo(Integer.parseInt(request.getParameter("codigo")));
            produto.setNome(request.getParameter("nome"));
            produto.setTipo(request.getParameter("tipo"));
            produto.setDescricao(request.getParameter("descricao"));
            produto.setPreco(Double.parseDouble(request.getParameter("preco")));
            produto.setQuantEstoque(Integer.parseInt(request.getParameter("quantEstoque")));
            produto.setImagem(request.getParameter("img"));

            prod.alteraProduto(produto);
            List listaProduto = prod.ListaProduto();
            request.setAttribute("listaProduto", listaProduto);
            rd = request.getRequestDispatcher("TelaListaCarros.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("addCarrinho")) {
            HttpSession sessao = request.getSession();
            if (sessao.getAttribute("usuario") == null) {
                rd = request.getRequestDispatcher("TelaLogin.jsp");
                rd.forward(request, response);
            } else {
                ProdutoBean produto = new ProdutoBean();
                produto.setCodigo(Integer.parseInt(request.getParameter("id")));
                produto.setNome(request.getParameter("nome"));
                produto.setImagem(request.getParameter("img"));
                produto.setTipo(request.getParameter("tipo"));
                produto.setPreco(Double.parseDouble(request.getParameter("preco")));
                List<ProdutoBean> carrinho = prod.selecionaProdutoPorCodigo(produto.getCodigo());
                if (sessao.getAttribute("carrinho") != null) {
                    List<ProdutoBean> produtos = (List<ProdutoBean>) sessao.getAttribute("carrinho");
                    for (ProdutoBean p : produtos) {
                        carrinho.add(p);
                    }
                }
                sessao.setAttribute("carrinho", carrinho);
                rd = request.getRequestDispatcher("Carrinho.jsp");
                rd.forward(request, response);

            }
        } else if (acao.equalsIgnoreCase("excluirProdutoCar")) {
            System.out.println("chamou a acao do excluir");
            HttpSession sessao = request.getSession();
            int cod = Integer.parseInt(request.getParameter("codigo"));
            List<ProdutoBean> carrinho = new ArrayList<ProdutoBean>();
            List<ProdutoBean> produtos = (List<ProdutoBean>) sessao.getAttribute("carrinho");
            for (ProdutoBean p : produtos) {
                if (p.getCodigo() != cod) {
                    carrinho.add(p);
                }
            }
            sessao.setAttribute("carrinho", carrinho);
            rd = request.getRequestDispatcher("Carrinho.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("geraRelatorioEstoque")) {
            rd = request.getRequestDispatcher("TelaAdministrativa.jsp");
            rd.forward(request, response);   
            prod.geraRelatorioEstoque();

        } else if (acao.equalsIgnoreCase("comprar")) {
            //Adção na tabela venda
            ClienteBean cli = new ClienteBean();
            HttpSession sessao = request.getSession();
            int cod = Integer.parseInt(request.getParameter("codigo"));
            ClienteDAO cdao = new ClienteDAO();
            int codCli = cdao.selecionaCodigoCliente(sessao.getAttribute("usuario").toString());
            List<ProdutoBean> produto = (List<ProdutoBean>) prod.selecionaLinha(cod);
            VendaDAO venda = new VendaDAO();
            venda.addVenda(produto, codCli);

            //adciona na tabéla item_venda
            ProdutoBean p = new ProdutoBean();
            p.setQuantvenda(Integer.parseInt(request.getParameter("quantidadeVenda")));
            int qv = p.getQuantvenda();
            int codVenda = venda.selecionaCodigoVenda();
            venda.addItem(produto, codVenda, qv);
            
            //Exclui os itens do carrinho e redireciona pra pagina inicial
            int cod1 = Integer.parseInt(request.getParameter("codigo"));
            System.out.println("codigo de exclusao:" + cod1);
            List<ProdutoBean> carrinho = new ArrayList<ProdutoBean>();
            List<ProdutoBean> produtos = (List<ProdutoBean>) sessao.getAttribute("carrinho");
            for (ProdutoBean p1 : produtos) {
                if (p.getCodigo() == cod) {
                    carrinho.remove(p);
                }
            }
            sessao.setAttribute("carrinho", carrinho);
           
            rd = request.getRequestDispatcher("ProdutoServlet?acao=listarTelaPrincipal");
            rd.forward(request, response);

        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("get");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("post");
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /*  public void listarPrincipal(HttpServletRequest request, HttpServletResponse response)
     throws ServletException, IOException {
     ProdutoDAO prod = new ProdutoDAO();
     RequestDispatcher rd = null;
     List listaProduto = prod.ListaProduto();
     request.setAttribute("listarProduto", listaProduto);
     rd = request.getRequestDispatcher("TelaPrincipal.jsp");
     rd.forward(request, response);
     }
     */
}
