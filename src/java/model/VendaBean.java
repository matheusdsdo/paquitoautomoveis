/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author 2015005043
 */
public class VendaBean {
    private int codigo;
    private Date dataVenda;
    private int codigoCli;
    private double valorTotal;

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public VendaBean() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(Date dataVenda) {
        this.dataVenda = dataVenda;
    }

    public int getCodigoCli() {
        return codigoCli;
    }

    public void setCodigoCli(int codigoCli) {
        this.codigoCli = codigoCli;
    }
    
    
}
