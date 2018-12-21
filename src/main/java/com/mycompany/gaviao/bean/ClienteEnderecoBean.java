package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.ClienteEnderecoDAO;
import com.mycompany.gaviao.utils.Message;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class ClienteEnderecoBean {

    Message message = new Message();
    List<ClienteEndereco> listaEndereco = new ArrayList<>();
    ClienteEndereco endereco = new ClienteEndereco();
    ClienteEnderecoDAO enderecoDAO = new ClienteEnderecoDAO();

    String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

    public void inicializar(){
        if(params != null){
            int id = Integer.parseInt(params);
            if(id != 0 ){
                ClienteEndereco endereco = pesquisarPorId((long) id);
                setEndereco(endereco);
            }
        }
    }

    public List<ClienteEndereco> pesquisarTodos(){
        try{
           return listaEndereco = enderecoDAO.retreaveAll();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar todos os endereços!");
        }
        return null;
    }
    public ClienteEndereco pesquisarPorId(Long id){
        try{
            if(id != null) {
                int intId = id.intValue();
                endereco = (ClienteEndereco) enderecoDAO.retreaveById(intId);
                return endereco;
            }
            return null;
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar endereço por id!");
            return null;
        }
    }
    public List<ClienteEndereco> pesquisarPorLogradouro(String logradouro){
        try{
            listaEndereco = enderecoDAO.retreaveByLogradouro(logradouro);
            if(listaEndereco.size() == 0){
                message.renderMessage("Error", "Logradouro não encontrado!");
            }
            return listaEndereco;
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar endereço por nome!");
            return null;
        }
    }
    public void salvar(ClienteEndereco endereco){
        try{
            enderecoDAO.create(endereco);
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao cadastrar endereço!");
        }
    }
    public void alterar(ClienteEndereco endereco){
        try{
            enderecoDAO.update(endereco);
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao alterar endereço!");
        }
    }
    public void deletar(int id){
        try{
            enderecoDAO.delete(id);
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao deletar endereço");
        }
    }

    public List<ClienteEndereco> getListaEndereco() {
        return listaEndereco;
    }

    public void setListaEndereco(List<ClienteEndereco> listaEndereco) {
        this.listaEndereco = listaEndereco;
    }

    public ClienteEndereco getEndereco() {
        return endereco;
    }

    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }
    public String paginaEditarEndereco(int id){
        return "cadastroClienteEndereco?faces-redirect=true&id="+id;
    }

}
