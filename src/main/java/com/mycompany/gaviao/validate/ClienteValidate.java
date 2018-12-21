package com.mycompany.gaviao.validate;

import antlr.StringUtils;
import com.mycompany.gaviao.model.Cliente;
import com.mycompany.gaviao.utils.Message;

public class ClienteValidate implements IValidate {
    public Message message = new Message();
    public boolean validatePost(Object objCliente) {
        Cliente cliente = (Cliente) objCliente ;
        if(cliente.getNome().equals(null) || cliente.getNome().equals("")){
            return false;
        } else if (cliente.getCpf().equals(null) || cliente.getCpf().equals("")){
            return false;
        } else if (cliente.getRg().equals(null) || cliente.getRg().equals("")){
            return false;
        } else if (!cliente.getCpf().matches("[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}")){
            message.renderMessage("Error", "O CPF deve conter 11 dígitos, sendo somente números");
            return false;
        } else if(!cliente.getRg().matches("[0-9]{6}")){
            message.renderMessage("Error", "O RG deve conter 6 digitos, sendo somente números");
            return false;
        }
        return true;
    }

    @Override
    public boolean validateGet() {
        return false;
    }

    @Override
    public boolean validatePut(Object objCliente) {
        return true;
    }

    @Override
    public boolean validateDelete() {
        return false;
    }
}
