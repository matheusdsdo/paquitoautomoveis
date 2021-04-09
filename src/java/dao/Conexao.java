
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Conexao {
    public static Connection getConnetion(){
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection("jdbc:postgresql://localhost:5432/lojacarrot", "postgres", "senha");
        } catch (Exception e) {
            System.out.println("Erro:" + e.getMessage());
            return null;
        }
    }
    
    public void conecta() throws ClassNotFoundException, SQLException{
        try {       
        Class.forName("org.postgresql.Driver");
        DriverManager.getConnection("jdbc:postgresql://localhost:5432/lojacarrot", "postgres", "senha");            
        } catch (Exception e) {
        System.out.println("Erro na conexão: "+e);
        }

    }
}
