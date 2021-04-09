
package dao;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ClienteBean;
import model.ItemVendaBean;
import model.ProdutoBean;
import model.VendaBean;


public class VendaDAO {
        private Connection con;
    
    public VendaDAO(){
        try {
            this.con = Conexao.getConnetion();
        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }
            //metodo para adicionar uma venda
    public void addVenda(List<ProdutoBean> v, int codCli){
        try {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO venda(valorTotal, codigoCli) VALUES( ?, ?)");
            for (ProdutoBean produtoBean : v) {                            
            pstm.setDouble(1, produtoBean.getPreco());
            pstm.setInt(2, codCli);
            }
            
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        } 
    }
            //Retorna o codigo da ultima venda cadastrada
        public int selecionaCodigoVenda() {
        System.out.println("chegou no dao da venda");
        int v = 0;
        try {
            PreparedStatement pstm = con.prepareStatement("SELECT MAX(codigo) as id FROM venda");
            ResultSet rs = pstm.executeQuery();
            
            while(rs.next()){
               v = rs.getInt("id");
            }
            rs.close();
            pstm.close();
            
            return v;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return 0;
        }    
    }
    
    public  List ListaVendas(){
        try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM venda");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            List<VendaBean> listaVendas = new ArrayList<VendaBean>();
            
            while (rs.next()) {
                //Criando um objeto do tipo ClienteBean
                VendaBean venda = new VendaBean();
                
                venda.setCodigo(rs.getInt("codigo"));
                venda.setDataVenda(rs.getDate("dataVenda"));
                venda.setCodigoCli(rs.getInt("codigoCli"));
                venda.setValorTotal(rs.getDouble("valorTotal"));
                
                //adiciona o objeto cliente bean a lista de clientes
                listaVendas.add(venda);
            }
            pstm.close();
            rs.close();
            return listaVendas;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }
    }
    // codigo, quantProd, preco, codvenda, codproduto
    public List listaProdutosVendidos(){
              try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM item_venda");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            List<ItemVendaBean> listaProdutosVendidos = new ArrayList<ItemVendaBean>();
            
            while (rs.next()) {
                //Criando um objeto do tipo ClienteBean
                ItemVendaBean venda = new ItemVendaBean();
                
                venda.setCodigo(rs.getInt("codigo"));
                venda.setQuatidade(rs.getInt("quantprod"));
                venda.setPreco(rs.getDouble("preco"));
                venda.setCodVenda(rs.getInt("codvenda"));
                venda.setCodProduto(rs.getInt("codproduto"));
                
                //adiciona o objeto cliente bean a lista de clientes
                listaProdutosVendidos.add(venda);
            }
            pstm.close();
            rs.close();
            return listaProdutosVendidos;
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }  
    }

    public void excluiVenda(int cod) {
        try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM venda WHERE codigo = ?");
            
            pstmt.setInt(1, cod);
            
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }

    public void excluiItemVenda(int cod) {
         try {
            PreparedStatement pstmt = con.prepareStatement("DELETE FROM item_venda WHERE codigo = ?");
            
            pstmt.setInt(1, cod);
            
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        }
    }
    
         //metodo para adicionar um produto a tabela itemVenda
    public void addItem(List<ProdutoBean> v, int codv, int qv){
        try {
            PreparedStatement pstm = con.prepareStatement("INSERT INTO item_venda(quantProd, preco, codVenda, codProduto) VALUES( ?, ?, ?, ?)");
            for (ProdutoBean produtoBean : v) {                            
            pstm.setInt(1, qv);
            pstm.setDouble(2, produtoBean.getPreco());
            pstm.setInt(3, codv);
            pstm.setInt(4, produtoBean.getCodigo());
            }
            
            pstm.execute();
            pstm.close();
        } catch (SQLException e) {
            System.out.println("Erro:" + e.getMessage());
        } 
    }

    public void geraRelatorioVendas() {
        try {
            //criando o preparedStatemante para a consulta
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM venda");
            
            //cria a variavel para armazenar os dados retornados da consulta
            ResultSet rs = pstm.executeQuery();
            
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("C:/Users/mathe/Documents/NetBeansProjects/PaquitoAutomoveisVersao9tio/Relatorios/RelatorioVendas.pdf"));
                doc.open();
                doc.add(new Paragraph("RELATÓRIO CONTROLE DE VENDAS"));   
                doc.add(new Paragraph("_____________________________________________________________________________"));
                VendaBean vb = new VendaBean();  
                while(rs.next()){

                vb.setCodigo(rs.getInt("codigo"));
                vb.setDataVenda(rs.getDate("dataVenda"));
                vb.setValorTotal(rs.getInt("valorTotal"));
                vb.setCodigoCli(rs.getInt("codigoCli"));               
                    
                    
                    
                    doc.add(new Paragraph("Codigo venda: " + vb.getCodigo()+ ". Data Venda: " + vb.getDataVenda() + ". Valor Total: " + 
                            vb.getValorTotal() + ". Código cliente: " + vb.getCodigoCli() +"."));
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
