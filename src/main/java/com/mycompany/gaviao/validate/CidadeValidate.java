package com.mycompany.gaviao.validate;

import com.mycompany.gaviao.model.Cidade;

public class CidadeValidate implements IValidate {
    @Override
    public boolean validatePost(Object objCidade) {
        Cidade cidade = (Cidade) objCidade;
        if(cidade.getNome().equals(null) || cidade.getNome().equals("")){
            return false;
        } else if(cidade.getEstado().equals(null) || cidade.getEstado().equals("")){
            return false;
        }
        return true;
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
