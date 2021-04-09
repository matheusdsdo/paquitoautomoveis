/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ClienteBean;
import model.ProdutoBean;

/**
 *
 * @author 2015005043
 */
public class ClienteDAO {
    
    private Connection con;
    
    public ClienteDAO(){
        try {
            this.con = Conexao.getConnetion();
        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }
    
        //metodo para adicionar um cliente
    public void addCliente(ClienteBean c){
        try {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO cliente(nome, email, senha, telefone) VALUES (?,?,?,?)");
            
            pstm.setString(1, c.getNome());
            pstm.setString(2, c.getEmail().toLowerCase());
            pstm.setString(3, c.getSenha());
            pstm.setInt(4, c.getTelefone());
            
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        } 
    }
    
    //metodo para retornar todos os clientes
    public  List ListaCliente(){
        try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            List<ClienteBean> listaCliente = new ArrayList<ClienteBean>();
            
            while (rs.next()) {
                //Criando um objeto do tipo ClienteBean
                ClienteBean cli = new ClienteBean();
                
                cli.setCodigo(rs.getInt("codigo"));
                cli.setNome(rs.getString("nome"));
                cli.setEmail(rs.getString("email"));
                cli.setSenha(rs.getString("senha"));
                cli.setTelefone(rs.getInt("telefone"));
                
                //adiciona o objeto cliente bean a lista de clientes
                listaCliente.add(cli);
            }
            pstm.close();
            rs.close();
            return listaCliente;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }
    }
    
    //metodo para excluir um cliente
    public void excluiCliente(int cod) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM cliente WHERE codigo = ?");
            
            pstmt.setInt(1, cod);
            
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }
    
    //metodo que seleciona a linha da tabela a ser modificada
    public List selecionaLinha(int cod) {
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente WHERE codigo='" + cod + "'");
            ResultSet rs = pstm.executeQuery();
            
            List <ClienteBean> linha = new ArrayList<ClienteBean>();
            
            while(rs.next()){
               ClienteBean cliente = new ClienteBean();
               cliente.setCodigo(rs.getInt("codigo"));
               cliente.setNome(rs.getString("nome"));
               cliente.setEmail(rs.getString("email"));
               cliente.setSenha(rs.getString("senha"));
               cliente.setTelefone(rs.getInt("telefone"));
               linha.add(cliente);
            }
            rs.close();
            pstm.close();
            return linha;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }    
    }
        //metodo que seleciona a linha da tabela a ser modificada pelo nome do usuario que esta logado no sistema
    public List selecionaLinhaPorNome(String nome) {
        System.out.println("chegou no dao");
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente WHERE email='" + nome + "'");
            ResultSet rs = pstm.executeQuery();
            
            List <ClienteBean> linha = new ArrayList<ClienteBean>();
            
            while(rs.next()){
               ClienteBean cliente = new ClienteBean();
               cliente.setCodigo(rs.getInt("codigo"));
               cliente.setNome(rs.getString("nome"));
               cliente.setEmail(rs.getString("email"));
               cliente.setSenha(rs.getString("senha"));
               cliente.setTelefone(rs.getInt("telefone"));
               linha.add(cliente);
            }
            rs.close();
            pstm.close();
            return linha;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }    
    }
    
    //grava as alteracoes na tabela
    public void alteraCliente(ClienteBean cli){
        try {
             PreparedStatement pstm = con.prepareStatement("UPDATE cliente SET nome = ?, email = ?, "
                     + "senha = ?, telefone = ? WHERE codigo=" + cli.getCodigo());  
             pstm.setString(1, cli.getNome());
             pstm.setString(2, cli.getEmail());
             pstm.setString(3, cli.getSenha());
             pstm.setInt(4, cli.getTelefone()); 
             pstm.executeUpdate(); 
             pstm.close();
             
        } catch (SQLException e) {
            System.out.println("Erro de conexão: "+e.getMessage());
        }
    }
    
    //metodo que verifica se exite usuario no banco para login
    public boolean autentica(ClienteBean cli) {
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente WHERE email = ? AND senha = ?");           
            pstm.setString(1, cli.getEmail().toLowerCase());
            pstm.setString(2, cli.getSenha());
            ResultSet rs = pstm.executeQuery();            
            if(rs.next()){
             return true;   
            }else return false;
            
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());            
        }    
        return false;
    }
    
    public void geraRelatorioClientes(){
                try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/mathe/Documents/NetBeansProjects/PaquitoAutomoveisVersao9tio/Relatorios/RelatorioCliente.pdf"));
                doc.open();
                doc.add(new Paragraph("RELATÓRIO CONTROLE DE CLIENTE"));   
                doc.add(new Paragraph("_____________________________________________________________________________"));
                ClienteBean cb = new ClienteBean();
                while(rs.next()){

                cb.setNome(rs.getString("nome"));
                cb.setEmail(rs.getString("email"));
                cb.setTelefone(rs.getInt("telefone"));               
                    
                    
                    
                    doc.add(new Paragraph("Nome: " + cb.getNome() + ". Email: " + cb.getEmail()+ ". Telefone: " + 
                            cb.getTelefone()+ "."));
                    doc.add(new Paragraph(""));
                }
                doc.close();
                pstm.close();
                rs.close();
                
            } catch (Exception e) {
                System.out.println("Deu ruim no relatorio "+e);
            }
            
            
        } catch (SQLException e) {
            System.out.println("Erro no relatorio:" + e.getMessage());
        }
        
        
    }
    //metodo que retorna o codigo do cliente para ser adicionado na tabela venda
    public int selecionaCodigoCliente(String email) {
        System.out.println("chegou no dao");
        int d = 0;
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM cliente WHERE email='" + email + "'");
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
               d = rs.getInt("codigo");
            }
            rs.close();
            pstm.close();
            
            return d;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return 0;
        }    
    }
    
}
