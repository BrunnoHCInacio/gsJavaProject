package com.mycompany.gaviao.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public void renderMessage(String tipo, String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(tipo, message) );
    }
}
