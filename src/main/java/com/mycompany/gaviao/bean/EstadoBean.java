package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.Estado;
import com.mycompany.gaviao.service.EstadoDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.EstadoValidate;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class EstadoBean implements IBean {
    Message message = new Message();
    Estado estado = new Estado();
    EstadoDAO estadoDAO = new EstadoDAO();
    EstadoValidate validate = new EstadoValidate();
    List<Estado> listaEstado = new ArrayList<>();

    @Override
    public void salvar() {
        try{
            if(validate.validatePost(estado)){
                estadoDAO.create(estado);
                limparCampos();
                message.renderMessage("Successful", "Estado cadastrado com sucesso!");
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao cadastrar novo estado!");
        }
    }

    @Override
    public List<Estado> pesquisarTodos() {
        try {
            return listaEstado = estadoDAO.retreaveAll();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao listar todos os estados");
        }
        return null;
    }
    public List<Estado> pesquisarPorNome(String nome){
        try {
            listaEstado = estadoDAO.retreaveByNome(nome);
            if(listaEstado.size() == 0){
                message.renderMessage("Error", "Estado não encontrado!");
            }
            return listaEstado;
        } catch (Exception e) {
            message.renderMessage("Error", "Estado não encontrado!");
            return null;
        }
    }

    @Override
    public void alterar(Object objEstado) {
        try{
            if(validate.validatePost(objEstado)){
                estadoDAO.update(objEstado);
                message.renderMessage("Successful", "Estado alterado com sucesso!");
                limparCampos();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao alterar estado!");
        }
    }

    @Override
    public void deletar(int id) {
        try {
            estadoDAO.delete(id);
            message.renderMessage("Successful", "Estado deletado com sucesso!");

        }catch (Exception e){
            message.renderMessage("Error", "Erro ao deletar estado!");
        }
    }


//    Get e Set Estado e listaEstado
    public Estado getEstado() {
        return estado;
    }
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public List<Estado> getListaEstado() {
        return listaEstado;
    }

    public void setListaEstado(List<Estado> listaEstado) {
        this.listaEstado = listaEstado;
    }

    //    Métodos funcionais da tela
    public void limparCampos(){
        estado.setNome("");
    }
    public void atualizaTela(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroEstado?faces-redirect=true");
        }catch (Exception e){
            System.out.println("Erro ao atualizar tela! Mensagem: " + e);
        }
    }
    public String paginaEditarEstado(int id){
        return "cadastroEstado?faces-redirect=true&id="+id;
    }
    public String paginaListarEstado(){
        return "cadastroEstado?faces-redirect=true";
    }
}
