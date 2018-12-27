package com.mycompany.gaviao.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;

@ManagedBean
@ViewScoped
public class menuBean implements Serializable {
    public String paginaCadastroCliente(){
        return "cliente/cadastroPessoa?faces-redirect=true";
    }
    public String paginaCadastroCidade(){
        return "cidade/cadastroCidade?faces-redirect=true";
    }
    public String paginaCadastroEstado(){
        return "estado/cadastroEstado?faces-redirect=true";
    }
    public String paginaListaCliente(){
        return "cliente/listaClientes?faces-redirect=true";
    }
    public String paginaListaCidade(){
        return "cidade/listaCidade?faces-redirect=true";
    }
    public String paginaListaEstado(){
        return "estado/listaEstado?faces-redirect=true";
    }

    public String paginaListaEnderecos(){
        return "endereco/listaClienteEndereco?faces-redirect=true";
    }
}
