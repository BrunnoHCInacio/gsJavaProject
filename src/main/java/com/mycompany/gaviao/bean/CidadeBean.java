package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.Cidade;
import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.CidadeDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.CidadeValidate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.io.Serializable;
import java.util.*;

@ManagedBean
@ViewScoped
public class CidadeBean implements Serializable {

    protected Cidade cidade = new Cidade();
    protected Message message = new Message();
    protected CidadeDAO cidadeDAO = new CidadeDAO();
    protected List<Cidade> listaCidade = new ArrayList<>();

    protected CidadeValidate validate = new CidadeValidate();

    String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");


    public void salvar() {
        try{
            if(params != null){
                alterar(cidade);
            }
            else if(validate.validatePost(cidade)){
                cidadeDAO.create(cidade);
                message.renderMessage("Success", "Cidade cadastrada com sucesso!");
                limparCampos();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao cadastrar cidade!");
        }
    }

    public void pesquisarTodos() {
        try{
           listaCidade = cidadeDAO.retreaveAll();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar todas cidades");
        }
    }
    public Cidade pesquisarPorId(Long id){
        try{
            if(id != null) {
                int intId = id.intValue();
                cidade = (Cidade) cidadeDAO.retreaveById(intId);
                return cidade;
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar cidade pelo id!");
        }
        return null;
    }
    public List<Cidade> pesquisarPorNome(String nome){
        try{
            listaCidade = cidadeDAO.retreaveByNome(nome);
            if(listaCidade.size() ==0){
                message.renderMessage("Error", "Cidade não encontrada");
            }
            return listaCidade;
        }catch (Exception e){
            System.out.println("Erro ao pesquisar endereco por nome! " + e);
            return null;
        }
    }


    public void alterar(Object objCidade) {
        try{
            if(validate.validatePost(objCidade)){
                cidadeDAO.update(objCidade);
                message.renderMessage("Success", "Cidade alterada com sucesso!");
                limparCampos();
                atualizaTela();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao alterar cidade!");
        }
    }


    public void deletar(Cidade cidade) {
        try{
            cidadeDAO.delete(cidade);
            pesquisarTodos();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao deletar cidade!");
        }
    }

//    Get e Set Cidade
    public Cidade getCidade() {
        return cidade;
    }
    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public List<Cidade> getListaCidade() {
        return listaCidade;
    }
    public void setListaCidade(List<Cidade> listaCidade) {
        this.listaCidade = listaCidade;
    }

    //    Métodos auxiliares de tela
    public void inicializar(){
        if(params != null){
            int paramId = Integer.parseInt(params);
            if(paramId != 0){
                Cidade cidade = pesquisarPorId((long) paramId);
                setCidade(cidade);
            }
        }
    }
    public String paginaEditarCidade(int id){
        return "cadastroCidade?faces-redirect=true&id="+id;
    }
    public String paginaListarCidade(){
        return "listaCidade?faces-redirect=true";
    }
    public void atualizaTela(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("listaCidade.xhtml?faces-redirect=true");
        }catch (Exception e){
            System.out.println("Erro ao atualizar tela. " +e);
        }
    }
    public void limparCampos(){
        cidade = new Cidade();
    }
}
