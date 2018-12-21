package com.mycompany.gaviao.utils.conversores;


import com.mycompany.gaviao.model.Cidade;
import com.mycompany.gaviao.service.CidadeDAO;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "cidadeConverter")
public class CidadeConverter implements Converter {

    private CidadeDAO cidadeDAO = new CidadeDAO();

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Cidade retorno = null;

        if (value != null && StringUtils.isNotEmpty(value)) {
            try {
                retorno = (Cidade) this.cidadeDAO.retreaveById(Integer.parseInt(value));
            } catch (Exception e) {
                System.out.println("Erro em getAsObject: "+e);
            }
        }

        return retorno;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Cidade estado = (Cidade) value;
            return Integer.toString(estado.getId());
        }
        return null;
    }
}
