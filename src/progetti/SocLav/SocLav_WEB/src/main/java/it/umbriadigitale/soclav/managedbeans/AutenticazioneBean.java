package it.umbriadigitale.soclav.managedbeans;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.omnifaces.util.Faces;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.auth0.jwt.exceptions.JWTVerificationException;

import it.umbriadigitale.soclav.DataModelCostanti;
import it.umbriadigitale.soclav.model.RdCUser;
import it.umbriadigitale.soclav.service.dto.LoginDTO;
import it.umbriadigitale.soclav.service.interfaccia.IUserService;
import it.umbriadigitale.soclav.util.*;
import it.webred.cet.permission.CeTUser;
 

@Named
@org.springframework.context.annotation.Scope("session")
@SessionScoped
public class AutenticazioneBean extends BaseBean{

  public final Logger logger = Logger.getLogger(this.getClass());

	private CeTUser cetUser = null;
	private RdCUser rdcUser = null;
	//private CeTToken cetToken = null;
	private String currentEnte;
	private EsitoAut esitoAut = null;
	
	@PostConstruct
	public void init() {
		esitoAut = new EsitoAut();
		esitoAut.setEsito(0);
		
	}
	
	private void getPermessiUtente(String path, String login, String ente) {
		
		if(login != null && ente != null && path != null) {
			//Estrazione dei permessi e put in sessione
			try {
				HttpRestClient restClient = new HttpRestClient();
			    HashMap<String, String> listPermessi = restClient.getPermissionUtente(path, login, ente);
				cetUser = new CeTUser();
				cetUser.setPermList(listPermessi);
				cetUser.setUsername(login);
				cetUser.setCurrentEnte(ente);
			
			}
			catch(LoginException lEx) {
				esitoAut.setEsito(-101);
				esitoAut.setEccezione(lEx);
				 
			}
			catch(Exception ex) {
				esitoAut.setEsito(-102);
				esitoAut.setEccezione(ex);
				esitoAut.setMessaggio("Eccezione durante il reperimento dei permessi associato all'utente");
			}
			
			
			
		}
	}
	private void verificaPiKUtente(String path, String priK) {
		
		if(priK != null && path != null) {
			//Estrazione dei permessi e put in sessione
			try {
				HttpRestClient restClient = new HttpRestClient();
			    this.esitoAut = restClient.verificaPriKUtente(path,priK);
				 
			
			}
			catch(LoginException lEx) {
				esitoAut.setEsito(-101);
				esitoAut.setEccezione(lEx);
				 
			}
			catch(Exception ex) {
				esitoAut.setEsito(-102);
				esitoAut.setEccezione(ex);
				esitoAut.setMessaggio("Eccezione durante il reperimento dei permessi associato all'utente");
			}
			
			
			
		}
	}
	/**
	 * Metodo per proseguire la navigazione dopo la selezione dell'ente
	 * 
	 * */
	public void accesso() {
		//Carico i permessi dell'ente selezionato
		if(esitoAut.getCetToken() != null && esitoAut.getCetToken() != null) {
			String pathPermessi = rdcUser.getPathRoot()+DataModelCostanti.Percorsi.PATH_PERMESSI;
			this.getPermessiUtente(pathPermessi, rdcUser.getUsername(), this.currentEnte);
			try {
				if(esitoAut.getEsito() ==0) {
				 		Faces.redirect(Faces.getRequestBaseURL()+ "jsp/protected/index.xhtml");
					
				}
				else {
					Faces.redirect(Faces.getRequestBaseURL()+ "/500.xhtml"); // da verificare e sositutire
				}
			} catch (IOException e) {
				logger.error(e);
			}
		}
	}
	
	public void accesso(String tokenP,String baseURL) {
		
		String url = baseURL;
		Token t = new Token();
		TokenClaim tc;
		try {
			tc = t.verifyNotExpired(tokenP);
			double val = Math.random();
			
			if(tc.getEsito() == 0){
				//Estrazione utente da db
				 ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();  
				 IUserService userService = (IUserService)ctx.getBean("userService");                 

				rdcUser = userService.findUserByLoginEnte(tc.getLogin(),tc.getEnte());
				if(rdcUser != null && rdcUser.getUsername()!= null) {
					//VERIFICO TOKEN SU PRIK AM
					String pathPrik =rdcUser.getPathRoot()+DataModelCostanti.Percorsi.PATH_PRIK;
					this.verificaPiKUtente(pathPrik, tokenP);
					if(esitoAut.getEsito() == 0) {
						//Estrazione dei permessi e put in sessione
						String pathPermessi = rdcUser.getPathRoot()+DataModelCostanti.Percorsi.PATH_PERMESSI;
						this.getPermessiUtente(pathPermessi, rdcUser.getUsername(), rdcUser.getEnteDefault());
					}else
						esitoAut.setUrl(url + "/jsp/public/AccessoNegato.xhtml");
					if(esitoAut.getEsito() == 0) {
						url += "/jsp/protected/index.xhtml?" + val;
						esitoAut.setUrl(url);
					}
				}
				
				
			}
			else{
				
				url += "/jsp/public/AccessoNegato.xhtml";
				esitoAut.setEsito(-10);
				esitoAut.setMessaggio("Stringa di Autenticazione non valida");
				esitoAut.setUrl(url);
				
				
			}
		} catch (JWTVerificationException e) {
			logger.error(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e);
		}
		 
	}

public void accessoDirettoScoLav(String login,String password) {
		
		String url = "";
		try {
			//Estrazione utente da db
			 ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();  
			 IUserService userService = (IUserService)ctx.getBean("userService");                 
			 	
			 	//occorre estrarre dalla login il percorso da interrogare
				rdcUser = userService.findUserByLogin(login);
				if(rdcUser != null && rdcUser.getUsername() != null) {
					//Validazione dell'utente
					HttpRestClient restClient = new HttpRestClient();
					LoginDTO loginDTO = new  LoginDTO();
					loginDTO.setUserName(login);
					loginDTO.setPwd(password);
					loginDTO.setEnte(rdcUser.getEnteDefault()); // si carica quello default o cmq il capofile..
					String pathLogin = rdcUser.getPathRoot()+DataModelCostanti.Percorsi.PATH_AUTENTICAZIONE;
					this.esitoAut = restClient.startLogin(loginDTO, pathLogin); 
					if(esitoAut.getEsito() == 0) { 
						url += "jsp/protected/sceltaEnte.xhtml";
						esitoAut.setUrl(url);
					}
//					esitoAut.setEsito(0);
//					esitoAut.setMessaggio("Autenticazione ok");
//					
				}
				else{
					
					url += "jsp/public/AccessoNegato.xhtml";
					
					esitoAut.setEsito(-10);
					esitoAut.setMessaggio("Login o password non corretti!");
					esitoAut.setUrl(url);
				}
				
			 
			
		} 
		
		 
		catch (Exception e) {
			logger.error(e);
			esitoAut.setEsito(-100);
			esitoAut.setMessaggio("Eccezione durante la procedura di autenticazione");
			esitoAut.setUrl(url);
		}  
		 
	}

	public CeTUser getCetUser() {
		return cetUser;
	}

	public void setCetUser(CeTUser cetUser) {
		this.cetUser = cetUser;
	}

	public RdCUser getRdcUser() {
		return rdcUser;
	}

	public void setRdcUser(RdCUser rdcUser) {
		this.rdcUser = rdcUser;
	}
//	public CeTToken getCetToken() {
//		return cetToken;
//	}
//	public void setCetToken(CeTToken cetToken) {
//		this.cetToken = cetToken;
//	}
	public String getCurrentEnte() {
		return currentEnte;
	}
	public void setCurrentEnte(String currentEnte) {
		this.currentEnte = currentEnte;
	}
	public EsitoAut getEsitoAut() {
		return esitoAut;
	}
	public void setEsitoAut(EsitoAut esitoAut) {
		this.esitoAut = esitoAut;
	}

 
	
}
