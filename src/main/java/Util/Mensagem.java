package Util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Mensagem {
	
	 
	public static void addInfo(String mensagem, String detalhes) {
		addMessage(FacesMessage.SEVERITY_INFO, mensagem, detalhes);
	}

	public static void addAviso(String mensagem, String detalhes) {
		addMessage(FacesMessage.SEVERITY_WARN, mensagem, detalhes);
	}

	public static void addErro(String mensagem, String detalhes) {
		addMessage(FacesMessage.SEVERITY_ERROR, mensagem, detalhes);
	}
	
	public static void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
    }
}
