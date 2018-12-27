package com.mycompany.gaviao.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Message {
    public void renderMessage(String tipo, String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, tipo, message));
    }
}
