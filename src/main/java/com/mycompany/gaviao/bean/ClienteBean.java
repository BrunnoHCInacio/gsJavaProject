package com.mycompany.gaviao.bean;
import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.ClienteDAO;
import com.mycompany.gaviao.utils.Message;
import com.mycompany.gaviao.validate.CidadeValidate;
import com.mycompany.gaviao.validate.ClienteEnderecoValidate;
import com.mycompany.gaviao.validate.ClienteValidate;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ManagedBean
@ViewScoped
@SessionScoped
public class ClienteBean implements Serializable {

    public Message message = new Message();
    public Cliente cliente = new Cliente();
    public ClienteDAO clienteDAO = new ClienteDAO();
    public List<Cliente> listaClientes = new ArrayList<Cliente>();
    protected ClienteValidate validateCliente = new ClienteValidate();
    protected ClienteEnderecoValidate validateEndereco = new ClienteEnderecoValidate();

    protected ClienteEndereco endereco = new ClienteEndereco();
    protected ClienteEnderecoBean enderecoBean = new ClienteEnderecoBean();

    String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

    public void inicializar() {
        if(params != null){
            int paramId = Integer.parseInt(params);
            if(paramId != 0){
                 Cliente cl = getClienteId((long) paramId);
                 setCliente(cl);
            }
        }
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }

    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    public List<Cliente> listar() {
        try {
            return listaClientes = clienteDAO.retreaveAll();
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao listar todos os clientes! Solicite suporte técnico.");
        }
        return null;
    }

    public void salvar() {
        try {
            if(params != null){
                    alterar(cliente);
            }else {
                if(validateEndereco.validatePost(endereco) && validateCliente.validatePost(cliente)){
                    enderecoBean.salvar(endereco);
//                    Setar o id do endereco na classe de cliente
                    cliente.setEndereco(endereco);
                    clienteDAO.create(cliente);
                    message.renderMessage("Successful", "Cliente cadastrado com sucesso!");
                    limparCampos();
                }else {
                    message.renderMessage("Error", "Dados inválidos! Preencha os campos corretamente.");
                }
            }
        } catch (Exception ex) {
            message.renderMessage("Error", "Erro ao cadastrar cliente! Solicite suporte técnico.");
        }

    }

    public Cliente getClienteId(Long id) {
        try {
            if (id != null) {
                int intId = id.intValue();
                cliente = clienteDAO.retreaveById(intId);
                return cliente;
            }
            return null;
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao pesquisar cliente por ID! Solicite suporte técnico.");
            return null;
        }
    }

    public void alterar(Cliente cliente) {
        try {
            if(validateCliente.validatePost(cliente) && validateEndereco.validatePost(endereco)){
                enderecoBean.alterar(endereco);
                clienteDAO.update(cliente);
                message.renderMessage("Successful", "Cliente alterado com sucesso!");
                limparCampos();
                atualizaTela();
            }else {
                message.renderMessage("Error", "Dados inválidos! Preencha os campos corretamente.");
            }
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao alterar dados de cliente! Solicite suporte técnico.");
        }
    }

    public void deletar(int id) {
        try {
            clienteDAO.delete(id);
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao deletar cliente! Solicite suporte técnico.");
        }finally {
            message.renderMessage("Successful", "Cliente deletado com sucesso!");
            listar();
        }
    }
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ClienteEndereco getEndereco() {
        return endereco;
    }
    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

    public String paginaListaClientes() {
       return "listaClientes?faces-redirect=true";
    }
    public String paginaEditarCliente(Long id) {

        if (id != null) {
            return "cadastroCliente?faces-redirect=true&id=" + id;
        }
        return "cadastroCliente?faces-redirect=true";
    }

    public void atualizaTela(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroCliente.xhtml?faces-redirect=true");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar tela! Mensagem: " + e);
        }
    }

    public void limparCampos(){
        cliente.setNome("");
        cliente.setCpf("");
        cliente.setRg("");
        endereco.setLogradouro("");
        endereco.setNumero("");
        endereco.setComplemento("");
        endereco.setBairro("");
        endereco.getCidade().setNome("");
        endereco.setPais("");
        endereco.setCep("");
    }
}
