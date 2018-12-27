package com.mycompany.gaviao.bean;
import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.service.ClienteDAO;
import com.mycompany.gaviao.service.ClienteEnderecoDAO;
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
public class ClienteBean implements Serializable {

//    Componente de alerta
    private Message message = new Message();

//    Cliente
    private Cliente cliente = new Cliente();
    private ClienteDAO clienteDAO = new ClienteDAO();

//    Enderecos
    private ClienteEndereco endereco = new ClienteEndereco();
    private ClienteEnderecoDAO enderecoDAO = new ClienteEnderecoDAO();

//    private ClienteEnderecoBean enderecoBean = new ClienteEnderecoBean();

//    Lista de clientes
    private List<Cliente> listaClientes = new ArrayList<Cliente>();
    private List listaEndereco = new ArrayList<>();

//    Validação de Cliente
    private ClienteValidate validateCliente = new ClienteValidate();

//    Validação de enderecos
    private ClienteEnderecoValidate validateEndereco = new ClienteEnderecoValidate();

    private String params = (String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id");

    public void inicializar() {
        if(params != null){
            int paramId = Integer.parseInt(params);
            if(paramId != 0){
                 Cliente cl = pesquisarPorIdCliente((long) paramId);
                 setCliente(cl);
                 setListaEndereco(cl.getEnderecos());
            }
        }
    }

    public void adicionarEndereco(){
        try{
            if(validateEndereco.validatePost(endereco)) {
                listaEndereco.add(endereco);
                cliente.setEnderecos(listaEndereco);
            }
        } catch (Exception e){
            message.renderMessage("Error", "Erro ao adicionar endereço!");
        }finally {
            limparCamposEndereco();
        }
    }

    public void pesquisarTodosClientes() {
        try {
             listaClientes = clienteDAO.retreaveAll();
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao listar todos os clientes! Solicite suporte técnico.");
        }
    }
    public void pesquisarTodosEnderecos(){
        try{
             listaEndereco = enderecoDAO.retreaveAll();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao pesquisar todos os endereços!");
        }

    }

    public Cliente pesquisarPorIdCliente(Long id) {
        try {
            if (id != null) {
                cliente = (Cliente) clienteDAO.retreaveById(id);
                return cliente;
            }
            return null;
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao pesquisar cliente por ID! Solicite suporte técnico.");
            return null;
        }
    }
    public ClienteEndereco pesquisarPorIdEndereco(Long id){
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

    public void salvar() {
        try {
            if(params != null){
                    alterar(cliente);
            }else {
                if(validateCliente.validatePost(cliente)){
                    clienteDAO.create(cliente);
                    for (ClienteEndereco clienteEndereco : cliente.getEnderecos()) {
                        clienteEndereco.setCliente(cliente);
                        enderecoDAO.create(clienteEndereco);
                    }
                    message.renderMessage("Success", "Cliente cadastrado com sucesso!");
                    limparCamposEndereco();
                    limparCamposCliente();
                }else {
                    message.renderMessage("Error", "Dados inválidos! Preencha os campos corretamente.");
                }
            }
        } catch (Exception ex) {
            message.renderMessage("Error", "Erro ao cadastrar cliente! Solicite suporte técnico.");
        }
    }

    public void alterar(Cliente cliente) {
        try {
            if(validateCliente.validatePost(cliente)){
                clienteDAO.update(cliente);
                for (ClienteEndereco enderecoFor : cliente.getEnderecos()) {
                    enderecoFor.setCliente(cliente);
                    enderecoDAO.update(enderecoFor);
                }
                message.renderMessage("Success", "Cliente alterado com sucesso!");

                limparCamposCliente();
                limparCamposEndereco();
                atualizaTela();
            }else {
                message.renderMessage("Error", "Dados inválidos! Preencha os campos corretamente.");
            }
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao alterar dados de cliente! Solicite suporte técnico.");
        }
    }

    public void deletarCliente(Cliente cliente) {
        try {
            clienteDAO.delete(cliente);
            pesquisarTodosClientes();
        } catch (Exception e) {
            message.renderMessage("Error", "Erro ao deletar cliente! Solicite suporte técnico.");
        }finally {
            message.renderMessage("Successful", "Cliente deletado com sucesso!");
            pesquisarTodosClientes();
        }
    }
    public void deletarEndereco(ClienteEndereco endereco){
        try{
            enderecoDAO.delete(endereco);
            pesquisarTodosEnderecos();
        }catch (Exception e){
            message.renderMessage("Error", "Erro ao deletar endereço");
        }
    }

    public void editarEndereco(ClienteEndereco endereco){
        setEndereco(endereco);
        listaEndereco.remove(endereco);
    }
    public void deletarEndTable(ClienteEndereco endereco){
        listaEndereco.remove(endereco);
    }

//    Get's and Set's
//    Cliente
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

//    Lista Cliente
    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
    public void setListaClientes(List<Cliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

//    Endereco
    public ClienteEndereco getEndereco() {
        return endereco;
    }
    public void setEndereco(ClienteEndereco endereco) {
        this.endereco = endereco;
    }

//    Lista Endereco
    public List<ClienteEndereco> getListaEndereco() {
        return listaEndereco;
    }
    public void setListaEndereco(List<ClienteEndereco> listaEndereco) {
        this.listaEndereco = listaEndereco;
    }

    public String paginaEditarCliente(Long id) {
        if (id != null) {
            return "cadastroPessoa?faces-redirect=true&id=" + id;
        }
        return "cadastroPessoa?faces-redirect=true";
    }

    public void atualizaTela(){
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("cadastroPessoa.xhtml?faces-redirect=true");
        } catch (Exception e) {
            System.out.println("Erro ao atualizar tela! Mensagem: " + e);
        }
    }

    public void limparCamposEndereco(){
        endereco = new ClienteEndereco();
    }
    public void limparCamposCliente() {
        cliente = new Cliente();
        listaEndereco = new ArrayList<>();
    }
    public String paginaEditarEndereco(int id){
        return "cadastroClienteEndereco?faces-redirect=true&id="+id;
    }
}
