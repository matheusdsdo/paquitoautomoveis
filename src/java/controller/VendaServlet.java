/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.VendaDAO;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Apa
 */
public class VendaServlet extends HttpServlet {

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
        
        RequestDispatcher rd = null;
        String acao = request.getParameter("acao");
        VendaDAO venda = new VendaDAO();
        
        if (acao.equalsIgnoreCase("listar")) {
            List listaVendas = venda.ListaVendas();
            request.setAttribute("listaVendas", listaVendas);
            rd = request.getRequestDispatcher("TelaListaVenda.jsp");
            rd.forward(request, response);
            
        } else if (acao.equalsIgnoreCase("excluirVenda")){
            int cod = Integer.parseInt(request.getParameter("codigo"));
            venda.excluiVenda(cod);

            List listaVendas = venda.ListaVendas();
            request.setAttribute("listaVendas", listaVendas);
            rd = request.getRequestDispatcher("TelaListaVenda.jsp");
            rd.forward(request, response);
            
        } else if(acao.equalsIgnoreCase("geraRelatorioVendas")){
            rd = request.getRequestDispatcher("TelaAdministrativa.jsp");
            rd.forward(request, response);              
            venda.geraRelatorioVendas();         
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
