package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisponibilita;
import it.webred.cs.data.model.CsTbPotesta;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.interfaces.INuovoParente;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

public class NuovoParenteBean extends AnagraficaNucleoBean implements INuovoParente {

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

	private String dialogHeader = "Anagrafica parente";
	
	
	private boolean decesso;
	private Date dataDecesso;
	
	private Long idPotesta;
	private Long idContatto;
	private Long idDisponibilita;
	private String cittadinanza;
	
	private List<CsTbTipoRapportoCon> lstParentelaModel;
	private List<SelectItem> lstPotesta;
	private List<SelectItem> lstContatto;
	private List<SelectItem> lstDisponibilita;
	private List<SelectItem> lstCittadinanze;
	
	private FormazioneLavoroMan formLavoroMan;
	
	public NuovoParenteBean(){
		super();
		formLavoroMan=new FormazioneLavoroMan();
		formLavoroMan.setRenderSettoreImpiego(false);
		formLavoroMan.setRenderTitoloStudio(false);
		formLavoroMan.setRequiredCondLavorativa(false);
	}
	
	@Override
	public void reset() {
		super.reset();

		decesso = false;
		dataDecesso = null;
		idContatto = null;
		idDisponibilita = null;
		idPotesta = null;
		cittadinanza = null;
		
		formLavoroMan=new FormazioneLavoroMan();
		formLavoroMan.setRenderSettoreImpiego(false);
		formLavoroMan.setRenderTitoloStudio(false);
	}
	
	
	public void precaricaParenteDaComponente(CsAAnagrafica anagraficaComponenteDaAltraScheda) {
		
		setCognome(anagraficaComponenteDaAltraScheda.getCognome());
		setNome(anagraficaComponenteDaAltraScheda.getNome());
		setDatiSesso(new SessoBean(anagraficaComponenteDaAltraScheda.getSesso()));
		setDataNascita(anagraficaComponenteDaAltraScheda.getDataNascita());
		setCodFiscale(anagraficaComponenteDaAltraScheda.getCf());
		setTelefono(anagraficaComponenteDaAltraScheda.getTel());
		setCellulare(anagraficaComponenteDaAltraScheda.getCell());
		setEmail(anagraficaComponenteDaAltraScheda.getEmail());
		decesso = (anagraficaComponenteDaAltraScheda.getDataDecesso()!=null);
		dataDecesso = anagraficaComponenteDaAltraScheda.getDataDecesso();
		setNote(anagraficaComponenteDaAltraScheda.getNote());
		cittadinanza = anagraficaComponenteDaAltraScheda.getCittadinanza();
		getComuneNazioneNascitaBean().getComuneMan().setComune(new ComuneBean(anagraficaComponenteDaAltraScheda.getComuneNascitaCod(), anagraficaComponenteDaAltraScheda.getComuneNascitaDes(), anagraficaComponenteDaAltraScheda.getProvNascitaCod()) );
		
		
	}

	@Override
	public Long getIdPotesta() {
		return idPotesta;
	}

	public void setIdPotesta(Long idPotesta) {
		this.idPotesta = idPotesta;
	}

	@Override
	public Long getIdContatto() {
		return idContatto;
	}

	public void setIdContatto(Long idContatto) {
		this.idContatto = idContatto;
	}

	@Override
	public Long getIdDisponibilita() {
		return idDisponibilita;
	}

	public void setIdDisponibilita(Long idDisponibilita) {
		this.idDisponibilita = idDisponibilita;
	}

	@Override
	public List<SelectItem> getLstPotesta() {
		
		if(lstPotesta == null){
			lstPotesta = new ArrayList<SelectItem>();
			lstPotesta.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbPotesta> lst = confService.getPotesta(bo);
			if (lst != null) {
				for (CsTbPotesta obj : lst) {
					lstPotesta.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstPotesta;
	}

	public void setLstPotesta(List<SelectItem> lstPotesta) {
		this.lstPotesta = lstPotesta;
	}

	@Override
	public List<SelectItem> getLstContatto() {
		
		if(lstContatto == null){
			lstContatto = new ArrayList<SelectItem>();
			lstContatto.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbContatto> lst = confService.getContatti(bo);
			if (lst != null) {
				for (CsTbContatto obj : lst) {
					lstContatto.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstContatto;
	}

	public void setLstContatto(List<SelectItem> lstContatto) {
		this.lstContatto = lstContatto;
	}

	@Override
	public List<SelectItem> getLstDisponibilita() {
		
		if(lstDisponibilita == null){
			lstDisponibilita = new ArrayList<SelectItem>();
			lstDisponibilita.add(new SelectItem(null, "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			List<CsTbDisponibilita> lst = confService.getDisponibilita(bo);
			if (lst != null) {
				for (CsTbDisponibilita obj : lst) {
					lstDisponibilita.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstDisponibilita;
	}

	public void setLstDisponibilita(List<SelectItem> lstDisponibilita) {
		this.lstDisponibilita = lstDisponibilita;
	}

	@Override
	public boolean getDecesso() {
		return decesso;
	}

	public void setDecesso(boolean decesso) {
		this.decesso = decesso;
	}

	@Override
	public Date getDataDecesso() {
		return dataDecesso;
	}

	public void setDataDecesso(Date dataDecesso) {
		this.dataDecesso = dataDecesso;
	}
	
	@Override
	public void resetDataDecesso(){
		if(!this.getDecesso())
			this.dataDecesso=null;
	}

	@Override
	public String getCittadinanza() {
		return cittadinanza;
	}

	public void setCittadinanza(String cittadinanza) {
		this.cittadinanza = cittadinanza;
	}

	@Override
	public List<SelectItem> getLstCittadinanze() {
		
		if(lstCittadinanze == null){
			lstCittadinanze = new ArrayList<SelectItem>();
			lstCittadinanze.add(new SelectItem(null, "- seleziona -"));
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				List<String> beanLstCittadinanze = bean.getCittadinanze();
				if (beanLstCittadinanze != null) {
					for (String cittadinanza : beanLstCittadinanze) {
						//in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza 500, in CS_A_SOGGETTO il campo CITTADINANZA ha lunghezza 255
						if (cittadinanza.length() > 255) {
							cittadinanza = cittadinanza.substring(0, 252) + "...";
						}
						lstCittadinanze.add(new SelectItem(cittadinanza, cittadinanza));
					}
				}
			} catch (NamingException e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}
		
		return lstCittadinanze;
	}

	public void setLstCittadinanze(List<SelectItem> lstCittadinanze) {
		this.lstCittadinanze = lstCittadinanze;
	}

	public String getDialogHeader() {
		return dialogHeader;
	}

	public void setDialogHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	public List<CsTbTipoRapportoCon> getLstParentelaModel() {
		CeTBaseObject bo = new CeTBaseObject();
		fillEnte(bo);
		lstParentelaModel = confService.getTipoRapportoConParenti(bo);
		return lstParentelaModel;
	}

	public void setLstParentelaModel(List<CsTbTipoRapportoCon> lstParentelaModel) {
		this.lstParentelaModel = lstParentelaModel;
	}


	public FormazioneLavoroMan getFormLavoroMan() {
		return formLavoroMan;
	}


	public void setFormLavoroMan(FormazioneLavoroMan formLavoroMan) {
		this.formLavoroMan = formLavoroMan;
	}

	@Override
	public void resetAffidatario(){
		this.setAffidatario(false);
	}
	
	@Override
	public void onChangeCondLavoro() {}

	@Override
	public void onChangeTitoloStudio() {}

}
