/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.ClienteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ClienteBean;

/**
 *
 * @author 2015005043
 */
public class ClienteServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String acao = request.getParameter("acao");

        RequestDispatcher rd = null;

        //instancia um objeto do tipo cliente dao
        ClienteDAO cliente = new ClienteDAO();

        if (acao.equalsIgnoreCase("listarCliente")) {

            List listaClientes = cliente.ListaCliente();
            request.setAttribute("listaClientes", listaClientes);
            rd = request.getRequestDispatcher("TelaListarCliente.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("addCliente")) {
            try {
                String nome = request.getParameter("nome");
                String email = request.getParameter("email");
                String senha = request.getParameter("senha");
                System.out.println("Cliente:" + nome);
                int telefone = Integer.parseInt(request.getParameter("telefone"));
                ClienteBean cliAdd = new ClienteBean(nome, email, senha, telefone);
                cliente.addCliente(cliAdd);
                List listaClientes = cliente.ListaCliente();
                request.setAttribute("listaClientes", listaClientes);
                rd = request.getRequestDispatcher("TelaLogin.jsp");
                rd.forward(request, response);

            } catch (Exception e) {
                System.out.println("Erro:" + e.getMessage());
            }

        } else if (acao.equalsIgnoreCase("excluirCliente")) {

            int cod = Integer.parseInt(request.getParameter("codigo"));
            cliente.excluiCliente(cod);
            List listaClientes = cliente.ListaCliente();
            request.setAttribute("listaClientes", listaClientes);
            rd = request.getRequestDispatcher("TelaListarCliente.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("atualizarCliente")) {

            int cod = Integer.parseInt(request.getParameter("codigo"));
            List linhaSelecionada = cliente.selecionaLinha(cod);
            request.setAttribute("linhaSelecionada", linhaSelecionada);
            rd = request.getRequestDispatcher("TelaAtualizarClientes.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("atlzCliente")) {

            String nomeCli = request.getParameter("nome");
            List linhaSelecionada = cliente.selecionaLinhaPorNome(nomeCli);
            request.setAttribute("linhaSelecionada", linhaSelecionada);
            rd = request.getRequestDispatcher("TelaAtualizarUsuario.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("atualizarCli")) {

            ClienteBean cli = new ClienteBean();
            //ecuperando parametros da tela atualiza clientes (Area administrativa).
            cli.setCodigo(Integer.parseInt(request.getParameter("codigo")));
            cli.setNome(request.getParameter("nome"));
            cli.setEmail(request.getParameter("email").toLowerCase());
            cli.setSenha(request.getParameter("senha"));
            cli.setTelefone(Integer.parseInt(request.getParameter("telefone")));


            cliente.alteraCliente(cli);
            List listaClientes = cliente.ListaCliente();
            request.setAttribute("listaClientes", listaClientes);
            rd = request.getRequestDispatcher("TelaListarCliente.jsp");
            rd.forward(request, response);

        } else if (acao.equalsIgnoreCase("verificaLogin")) {

            ClienteBean cli = new ClienteBean();
            String emailLogin = request.getParameter("user").toLowerCase();
            String senhaLogin = request.getParameter("pass");

            cli.setEmail(emailLogin.toLowerCase());
            cli.setSenha(senhaLogin);
            ClienteDAO cliDao = new ClienteDAO();
            if ((cliDao.autentica(cli)) && emailLogin.equals("admin@admin")) {
                HttpSession sessao = request.getSession(true);
                sessao.setAttribute("administrador", emailLogin);
                rd = request.getRequestDispatcher("TelaAdministrativa.jsp");
                rd.forward(request, response);

            } else if (cliDao.autentica(cli) && !emailLogin.equals("admin@admin")) {
                HttpSession sessao = request.getSession(true);
                sessao.setAttribute("usuario", emailLogin);
                rd = request.getRequestDispatcher("ProdutoServlet?acao=listarTelaPrincipal");
                rd.forward(request, response);

            } else {
                rd = request.getRequestDispatcher("TelaErroLogin.jsp");
                rd.forward(request, response);
            }

        }else if (acao.equalsIgnoreCase("atlzUsuario")) {
            ClienteBean cli = new ClienteBean();
            //recuperando parametros da tela atualiza clientes (Area administrativa).
            cli.setCodigo(Integer.parseInt(request.getParameter("codigo")));
            cli.setNome(request.getParameter("nome"));
            cli.setEmail(request.getParameter("email").toLowerCase());
            cli.setSenha(request.getParameter("senha"));
            cli.setTelefone(Integer.parseInt(request.getParameter("telefone")));
            
            cliente.alteraCliente(cli);
            List listaClientes = cliente.ListaCliente();
            request.setAttribute("listaClientes", listaClientes);
            rd = request.getRequestDispatcher("TelaLogin.jsp");
            rd.forward(request, response);
  
        } else if (acao.equalsIgnoreCase("geraRelatorioCliente")){
            rd = request.getRequestDispatcher("TelaAdministrativa.jsp");
            rd.forward(request, response);            
            cliente.geraRelatorioClientes();
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
        /*       
         RequestDispatcher rd = null;
         String acao = request.getParameter("acao");
         /*
         try {
         //Rcuperando parametros completos
         int cod = Integer.parseInt(request.getParameter("codigo"));
         String nome = request.getParameter("nome");
         String email = request.getParameter("email");
         String senha = request.getParameter("senha");
         int telefone = Integer.parseInt(request.getParameter("telefone"));
         System.out.println(" teste:" + nome);
         //Rcuperando parametros para login
         String emailLogin =  request.getParameter("user");
         String senhaLogin =  request.getParameter("pass");

         ClienteBean cli = new ClienteBean(cod, nome, email, senha, telefone);

         ClienteDAO cliente = new ClienteDAO();

         if (acao.equals("atualizar")) {
         try {
         cli.setCodigo(Integer.parseInt(request.getParameter("codigo")));
         cliente.alteraCliente(cli);
         List listaClientes = cliente.ListaCliente();
         request.setAttribute("listaClientes", listaClientes);
         rd = request.getRequestDispatcher("MostraClientes.jsp");
         rd.forward(request, response);

         } catch (Exception e) {
         System.out.println("Erro:" + e.getMessage());
         }

         }else if (acao.equals("verificaLogin")) {
         try {
                  
         cli.setEmail(emailLogin);
         cli.setSenha(senhaLogin);
         ClienteDAO cliDao = new ClienteDAO();     
         if((cliDao.autentica(cli))){
         HttpSession sessao = request.getSession(true);
         sessao.setAttribute("ususario", emailLogin);
         rd = request.getRequestDispatcher("TelaPrincipal.jsp");
         rd.forward(request, response);
         }else{
         response.sendRedirect("TelaErroLogin.jsp");
         }

         } catch (Exception e) {
         System.out.println("Erro:" + e.getMessage());
         }

         }

         } catch (Exception e) {
         System.out.println("Erro:" + e.getMessage());
         }*/
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

}
