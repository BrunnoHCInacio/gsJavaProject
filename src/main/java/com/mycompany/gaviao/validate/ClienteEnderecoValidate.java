package com.mycompany.gaviao.validate;

import com.mycompany.gaviao.model.ClienteEndereco;
import com.mycompany.gaviao.utils.Message;

public class ClienteEnderecoValidate implements IValidate {
    Message message = new Message();
    @Override
    public boolean validatePost(Object objEndereco) {
        ClienteEndereco endereco = (ClienteEndereco) objEndereco;
        if(endereco.getLogradouro().equals(null) || endereco.getLogradouro().equals("")){
            return false;
        }else if(endereco.getNumero().equals(null) || endereco.getNumero().equals("")){
            return false;
        }else if(endereco.getComplemento().equals(null) || endereco.getComplemento().equals("")){
            return false;
        }else if(endereco.getBairro().equals(null) || endereco.getBairro().equals("")){
            return false;
        }else if(endereco.getCidade().equals(null) || endereco.getCidade().equals("")){
            return false;
        }else if(endereco.getPais().equals(null) || endereco.getPais().equals("")){
            return false;
        }else if(endereco.getCep().equals(null) || !endereco.getCep().matches("[0-9]{2}\\.?[0-9]{3}\\.?[0-9]{3}")){
            message.renderMessage("Error", "O cep deve conter 8 dígitos, sendo eles números");
            return false;
        }
        return true ;
    }
    @Override
    public boolean validateGet() {
        return false;
    }

    @Override
    public boolean validatePut(Object object) {
        return false;
    }

    @Override
    public boolean validateDelete() {
        return false;
    }
}
