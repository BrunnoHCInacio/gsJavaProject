package com.mycompany.gaviao.utils.conversores;

import com.mycompany.gaviao.model.Cidade;
import com.mycompany.gaviao.model.Estado;
import com.mycompany.gaviao.service.CidadeDAO;
import com.mycompany.gaviao.service.EstadoDAO;
import org.apache.commons.lang3.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@FacesConverter(value = "estadoConverter")
public class EstadoConverter implements Converter {

    private EstadoDAO estadoDAO = new EstadoDAO();

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Estado retorno = null;

        if (value != null && StringUtils.isNotEmpty(value)) {
            try {
                retorno = (Estado) this.estadoDAO.retreaveById(Integer.parseInt(value));
            } catch (Exception e) {
                System.out.println("Erro em getAsObject: "+e);
            }
        }

        return retorno;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Estado estado = (Estado) value;
            return Integer.toString(estado.getId());
        }
        return null;
    }
    public ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }
}

