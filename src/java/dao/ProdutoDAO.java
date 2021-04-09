/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import dao.Conexao;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ClienteBean;
import model.ProdutoBean;

/**
 *
 * @author Apa
 */
public class ProdutoDAO {
        private Connection con;
    
    public ProdutoDAO(){
        try {
            this.con = Conexao.getConnetion();
        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }
    
    //metodo para adicionar um produto
    public void addproduto(ProdutoBean p){
        try {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO produto(nome, tipo, descricao, preco, quantEstoque, imagem) VALUES (?,?,?,?,?,?)");
            
            pstm.setString(1, p.getNome());
            pstm.setString(2, p.getTipo());
            pstm.setString(3, p.getDescricao());
            pstm.setDouble(4, p.getPreco());
            pstm.setInt(5, p.getQuantEstoque());
            pstm.setString(6, p.getImagem());
            
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        } 
    }
    
    //metodo para retornar todos os clientes
    public  List ListaProduto(){
        try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produto");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            List<ProdutoBean> listaProduto = new ArrayList<ProdutoBean>();
            
            while (rs.next()) {
                //Criando um objeto do tipo ClienteBean
                ProdutoBean p = new ProdutoBean();     
                p.setCodigo(rs.getInt("codigo"));
                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantEstoque(rs.getInt("quantEstoque"));
                p.setImagem(rs.getString("imagem"));
                
                //adiciona o objeto cliente bean a lista de clientes
                listaProduto.add(p);
            }
            pstm.close();
            rs.close();
            return listaProduto;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }
    }
    
        //metodo para excluir um produto
    public void excluiProduto(int cod) {
        System.out.println("chegou no excluir");
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM produto WHERE codigo = ?");
            
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
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produto WHERE codigo='" + cod + "'");
            ResultSet rs = pstm.executeQuery();
            
            List <ProdutoBean> linha = new ArrayList<ProdutoBean>();
            
            while(rs.next()){
               ProdutoBean prod = new ProdutoBean();
               prod.setCodigo(rs.getInt("codigo"));
               prod.setNome(rs.getString("nome"));
               prod.setTipo(rs.getString("tipo"));
               prod.setDescricao(rs.getString("descricao"));
               prod.setPreco(rs.getDouble("preco"));
               prod.setQuantEstoque(rs.getInt("quantEstoque"));
               prod.setImagem(rs.getString("imagem"));
               linha.add(prod);
            }
            rs.close();
            pstm.close();
            return linha;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }    
    }
    
        //grava as alteracoes na tabela produto // ALTERADO TIO TIOU
    public void alteraProduto(ProdutoBean p){
        System.out.println("HAUHUAUAHUAUA ISSO: "+p.getImagem());
            
            if (p.getImagem().equals("")) {
                try {
              PreparedStatement pstm = con.prepareStatement("UPDATE produto SET nome = ? , tipo = ? , descricao = ? , "
                     + "preco = ? , quantEstoque = ? WHERE codigo=" + p.getCodigo());  
             pstm.setString(1, p.getNome());
             pstm.setString(2, p.getTipo());
             pstm.setString(3, p.getDescricao());
             pstm.setDouble(4, p.getPreco());
             pstm.setInt(5, p.getQuantEstoque());  
             pstm.executeUpdate(); 
             pstm.close(); 
                } catch (Exception e) {
                    System.out.println("Erro conexão sem imagem: "+e);
                }
            } else {
            try {   
             PreparedStatement pstm = con.prepareStatement("UPDATE produto SET nome = ? , tipo = ? , descricao = ? , "
                     + "preco = ? , quantEstoque = ? , imagem = ? WHERE codigo=" + p.getCodigo());  
             pstm.setString(1, p.getNome());
             pstm.setString(2, p.getTipo());
             pstm.setString(3, p.getDescricao());
             pstm.setDouble(4, p.getPreco());
             pstm.setInt(5, p.getQuantEstoque());
             pstm.setString(6, p.getImagem());    
             pstm.executeUpdate(); 
             pstm.close();
            } catch (Exception e){
                System.out.println("Erro conexão com imagem: "+e);
            }
             
    } 
    }
    
    //metodo que seleciona o produto que será enviado ao carrinho.
    public List selecionaProdutoPorCodigo(int cod) {
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produto WHERE codigo='" + cod + "'");
            ResultSet rs = pstm.executeQuery();
            
            List <ProdutoBean> linha = new ArrayList<ProdutoBean>();
            
            while(rs.next()){
               ProdutoBean prod = new ProdutoBean();
               prod.setCodigo(rs.getInt("codigo"));
               prod.setNome(rs.getString("nome"));
               prod.setTipo(rs.getString("tipo"));               
               prod.setDescricao(rs.getString("descricao"));
               prod.setPreco(rs.getDouble("preco"));
               prod.setQuantEstoque(rs.getInt("quantEstoque"));
               prod.setImagem(rs.getString("imagem"));
               linha.add(prod);
            }
            rs.close();
            pstm.close();
            return linha;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }    
    }
    
   
       public void geraRelatorioEstoque() throws IOException{
        try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM produto");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/mathe/Documents/NetBeansProjects/PaquitoAutomoveisVersao9tio/Relatorios/RelatorioEstoque.pdf"));
                doc.open();
                doc.add(new Paragraph("RELATÓRIO CONTROLE DE ESTOQUE"));   
                doc.add(new Paragraph("_____________________________________________________________________________"));
                ProdutoBean p = new ProdutoBean();  
                while(rs.next()){

                p.setNome(rs.getString("nome"));
                p.setTipo(rs.getString("tipo"));
                p.setDescricao(rs.getString("descricao"));
                p.setPreco(rs.getDouble("preco"));
                p.setQuantEstoque(rs.getInt("quantEstoque"));                   
                    
                    
                    
                    doc.add(new Paragraph("Nome: " + p.getNome() + ". Tipo: " + p.getTipo() + ". Descrição: " + 
                            p.getDescricao() + ". Preço: " + p.getPreco() + ". Em estoque: " + p.getQuantEstoque()+"."));
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
    
    
}
