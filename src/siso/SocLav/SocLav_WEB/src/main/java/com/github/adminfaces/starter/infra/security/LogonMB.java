package com.github.adminfaces.starter.infra.security;

import static com.github.adminfaces.starter.util.Utils.addDetailMessage;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Specializes;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;

import org.omnifaces.util.Faces;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.github.adminfaces.template.config.AdminConfig;
import com.github.adminfaces.template.session.AdminSession;

import it.umbriadigitale.soclav.managedbeans.AutenticazioneBean;
import it.umbriadigitale.soclav.model.RdCUser;
import it.webred.cet.permission.CeTUser;

/**
 * Created by rmpestano on 12/20/14.
 *
 * This is just a login example.
 *
 * AdminSession uses isLoggedIn to determine if user must be redirect to login page or not.
 * By default AdminSession isLoggedIn always resolves to true so it will not try to redirect user.
 *
 * If you already have your authorization mechanism which controls when user must be redirect to initial page or logon
 * you can skip this class.
 */
//@FacesConfig
@Named
@SessionScoped
@Specializes
public class LogonMB extends AdminSession implements Serializable {

    private String currentUser;
    private String login;
    private String password;
    private boolean remember;
    private CeTUser utente;
    private RdCUser rdcUtente;
    private String token = null;
    
    
    @Inject
    private AdminConfig adminConfig;

    
    
    AutenticazioneBean authService = null;
    ApplicationContext ctx = null;
    
	public LogonMB() {
		  ctx = ContextLoader.getCurrentWebApplicationContext();  
		
	}
   
 
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public CeTUser getUtente() {
		return utente;
	}
	public void setUtente(CeTUser utente) {
		this.utente = utente;
	}
	private void setDatiUtente() {
		
		this.utente = authService.getCetUser();
		this.currentUser = (authService.getCetUser() != null ? authService.getCetUser().getName() : this.login);
		this.rdcUtente = authService.getRdcUser();
	}
	
	public void login() throws IOException {
		
		 String result = Faces.getRequestBaseURL();
	 		 authService = (AutenticazioneBean)ctx.getBean("autenticazioneBean");                 
			 if(authService.getCetUser() == null && authService.getEsitoAut().getCetToken() == null ) {
				 
				 authService.accessoDirettoScoLav(this.login, this.password);
				  result = authService.getEsitoAut().getUrl();
				 
				  if(authService.getEsitoAut().getEsito() < 0) {
					  addDetailMessage("Errore: <b>" + authService.getEsitoAut().getMessaggio() + "</b>!", FacesMessage.SEVERITY_ERROR);
					  Faces.redirect(result);
				  }
			 }//in attesa della scelta dell'ente
			 else if(authService.getCetUser() == null && authService.getEsitoAut().getCetToken() != null){
				 result += "jsp/protected/sceltaEnte.xhtml";
				 //setDatiUtente();
				 
			 }
			 else if(authService.getCetUser() != null){
				 result += "jsp/protected/index.xhtml";
				 //setDatiUtente();
				 
			 }
			 setDatiUtente();
	    addDetailMessage("Benvenuto <b>" + this.getRdcUtente().getCognome() +" "+this.getRdcUtente().getNome() + "</b>!");
        Faces.getExternalContext().getFlash().setKeepMessages(true);
        Faces.redirect(result);
        
    }
	
	
	 public void autoLogin() throws IOException {
		 
		 String token = Faces.getSessionAttribute("token");
 
		 
		 if(token != null) {
			 authService = (AutenticazioneBean)ctx.getBean("autenticazioneBean");                 
			 authService.accesso(token, Faces.getContext().getExternalContext().getRequestContextPath() );
			 if (authService.getEsitoAut().getEsito() == 0) {  //if (authService.getCetUser() != null) {
		     	login();
		     }
		 	 else {
		 		 addDetailMessage("Si è verificato il seguente errore <b>" + authService.getEsitoAut().getMessaggio() + "</b>!");
		 		 Faces.removeSessionAttribute("token");
		 		 Faces.redirect(authService.getEsitoAut().getUrl());
		 	 }
		 }
 		   
	 }
	 
	 public void isLoginCompleted() throws IOException {
		 
		
		 
		 	 try {	
				 authService = (AutenticazioneBean)ctx.getBean("autenticazioneBean");                 
				 if(authService.getCetUser() == null ||
						 (authService.getCetUser().getCurrentEnte() == null  ||
						 		authService.getCetUser().getCurrentEnte().isEmpty() == true)) {
					 login();
				 }
				 
		 	 }
		 	 catch( BeansException be) {
		 		 // bena non presente, login non effettuato
		 		 Faces.redirect("Login.xhtml");
		 	 }
		
 		   
	 }
		
    @Override
    public boolean isLoggedIn() {

    	  return (authService !=null  && (authService.getCetUser()!= null || authService.getEsitoAut().getCetToken() != null) ? true : false);
      
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
        this.remember = remember;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

	public RdCUser getRdcUtente() {
		return rdcUtente;
	}

	public void setRdcUtente(RdCUser rdcUtente) {
		this.rdcUtente = rdcUtente;
	}
}
