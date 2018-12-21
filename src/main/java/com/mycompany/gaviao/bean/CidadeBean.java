package com.mycompany.gaviao.bean;

import com.mycompany.gaviao.model.Cidade;
import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.CidadeDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.CidadeValidate;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import java.util.*;

@ManagedBean
@ViewScoped
public class CidadeBean implements IBean{

    protected Cidade cidade = new Cidade();
    protected Message message = new Message();
    protected CidadeDAO cidadeDAO = new CidadeDAO();
    protected List<Cidade> listaCidade = new ArrayList<>();

    protected CidadeValidate validate = new CidadeValidate();

    String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

    @Override
    public void salvar() {
        try{
            if(validate.validatePost(cidade)){
                cidadeDAO.create(cidade);
                message.renderMessage("Success", "Cidade cadastrada com sucesso!");
                limparCampos();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao cadastrar cidade!");
        }
    }

    @Override
    public List<Cidade> pesquisarTodos() {
        try{
           listaCidade = cidadeDAO.retreaveAll();
           return listaCidade;
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar todas cidades");
            return null;
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
        }
        return null;
    }

    @Override
    public void alterar(Object objCidade) {
        try{
            if(validate.validatePost(objCidade)){
                cidadeDAO.update(objCidade);
                message.renderMessage("Success", "Cidade alterada com sucesso!");
                limparCampos();
            }
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao alterar cidade!");
        }
    }

    @Override
    public void deletar(int id) {
        try{
            cidadeDAO.delete(id);
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
            }
        }
    }
    public String paginaEditarCidade(int id){
        return "cadastroCidade?faces-redirect=true";
    }
    public String paginaListarCidade(){
        return "cadastroCidade?faces-redirect=true";
    }
    public void atualizaTela(){
        try{
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroCidade.xhtml?faces-redirect=true");
        }catch (Exception e){
            System.out.println("Erro ao atualizar tela. " +e);
        }
    }
    public void limparCampos(){
        cidade.setNome("");
        cidade.getEstado().setNome("");
    }
}
