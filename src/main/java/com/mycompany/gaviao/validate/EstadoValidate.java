package com.mycompany.gaviao.validate;

import com.mycompany.gaviao.model.Estado;

public class EstadoValidate implements IValidate{
    @Override
    public boolean validatePost(Object objEstado) {
        Estado estado = (Estado) objEstado;
        if(estado.getNome().equals(null) || estado.getNome().equals("")){
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
