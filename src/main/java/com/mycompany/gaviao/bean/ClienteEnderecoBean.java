package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.ClienteEnderecoDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.ClienteEnderecoValidate;
import com.mycompany.gaviao.validate.ClienteValidate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClienteEnderecoBean implements Serializable {

//    Mensageiro
    Message message = new Message();

//    Objeto endereco
    ClienteEndereco endereco = new ClienteEndereco();
    Cliente cliente = new Cliente();

//    JPA endereco
    ClienteEnderecoDAO enderecoDAO = new ClienteEnderecoDAO();

//    Lista de enderecos
    List<ClienteEndereco> listaEndereco = new ArrayList<>();

//    Validação de endereco e cliente
    ClienteEnderecoValidate validateEndereco = new ClienteEnderecoValidate();
    ClienteValidate validateCliente = new ClienteValidate();

    String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");



//    public void inicializar(){
//        if(params != null){
//            int id = Integer.parseInt(params);
//            if(id != 0 ){
//                ClienteEndereco endereco = pesquisarPorId((long) id);
//                setEndereco(endereco);
//            }
//        }
//    }





}
