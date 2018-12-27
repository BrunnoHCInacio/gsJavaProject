package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.Estado;
import com.mycompany.gaviao.service.EstadoDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.EstadoValidate;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
public class EstadoBean implements Serializable {
    Message message = new Message();
    Estado estado = new Estado();
    EstadoDAO estadoDAO = new EstadoDAO();
    EstadoValidate validate = new EstadoValidate();
    List<Estado> listaEstado = new ArrayList<>();
    String params  = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

    public void inicializar(){
        if(params != null){
            int intParam = Integer.parseInt(params);
            Estado estado = pesquisarPorId((long) intParam);
            setEstado(estado);
        }
    }

    public void salvar() {
        try{
            if(params != null){
                alterar(estado);
            }
            else if(validate.validatePost(estado)){
                estadoDAO.create(estado);
                limparCampos();
                message.renderMessage("Successful", "Estado cadastrado com sucesso!");

            }

        }catch (Exception e){
            message.renderMessage("Error", "Erro ao cadastrar novo estado!");
        }
    }

    public void pesquisarTodos() {
        try {
             listaEstado = estadoDAO.retreaveAll();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao listar todos os estados");
        }
    }

    public Estado pesquisarPorId(Long id){
        try{
            int intId = id.intValue();
            return estado = (Estado) estadoDAO.retreaveById(intId);
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar estado pelo id!");
            return null;
        }
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

    public void alterar(Object objEstado) {
        try{
            if(validate.validatePost(objEstado)){
                estadoDAO.update(objEstado);
                message.renderMessage("Successful", "Estado alterado com sucesso!");
                limparCampos();
                atualizaTela();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao alterar estado!");
        }
    }

//    Alterando status ativo do Estado
    public void deletar(Estado estado) {
        try {
            estadoDAO.delete(estado);
            message.renderMessage("Successful", "Estado deletado com sucesso!");
            pesquisarTodos();
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
        estado = new Estado();
    }
    public void atualizaTela(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaEstado.xhtml?faces-redirect=true");
        }catch (Exception e){
            System.out.println("Erro ao atualizar tela! Mensagem: " + e);
        }
    }
    public String paginaEditarEstado(int id){
        return "cadastroEstado?faces-redirect=true&id="+id;
    }
    public String paginaListarEstado(){
        return "listaEstado?faces-redirect=true";
    }
}
