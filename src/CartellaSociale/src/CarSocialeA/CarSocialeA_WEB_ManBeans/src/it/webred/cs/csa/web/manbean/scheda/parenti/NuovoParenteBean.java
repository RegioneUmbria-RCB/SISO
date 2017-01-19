package it.webred.cs.csa.web.manbean.scheda.parenti;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAComponente;
import it.webred.cs.data.model.CsAComponenteGit;
import it.webred.cs.data.model.CsTbContatto;
import it.webred.cs.data.model.CsTbDisponibilita;
import it.webred.cs.data.model.CsTbPotesta;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.jsf.interfaces.INuovoParente;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;

public class NuovoParenteBean extends CsUiCompBaseBean implements INuovoParente {

	private AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");

	private String dialogHeader = "Anagrafica parente";
	
	private String cognome;
	private String nome;
	private SessoBean datiSesso=new SessoBean();
	private Date dataNascita;
	private String codFiscale;
	private String indirizzo;
	private String civico;
	private String telefono;
	private String cellulare;
	private String email;
	private boolean convivente;
	private boolean decesso;
	private Date dataDecesso;
	private String note;
	private Long idParentela;
	private CsTbTipoRapportoCon parentelaModel;
	private Long idPotesta;
	private Long idContatto;
	private Long idDisponibilita;
	private String cittadinanza;
	
	private List<CsTbTipoRapportoCon> lstParentelaModel;
	private List<SelectItem> lstParentela;
	private List<SelectItem> lstPotesta;
	private List<SelectItem> lstContatto;
	private List<SelectItem> lstDisponibilita;
	private List<SelectItem> lstCittadinanze;
	
	private ComuneNazioneNascitaBean comuneNazioneNascitaBean = new ComuneNazioneNascitaBean();
	private ComuneNazioneResidenzaBean comuneNazioneResidenzaBean = new ComuneNazioneResidenzaBean();
	
	@Override
	public void reset() {
		cognome = null;
		nome = null;
		datiSesso = new SessoBean();
		dataNascita = null;
		codFiscale = null;
		indirizzo = null;
		civico = null;
		telefono = null;
		cellulare = null;
		email = null;
		convivente = false;
		decesso = false;
		dataDecesso = null;
		note = null;
		idParentela = null;
		idContatto = null;
		idDisponibilita = null;
		idPotesta = null;
		cittadinanza = null;
		comuneNazioneNascitaBean.getComuneMan().setComune(null);
		comuneNazioneResidenzaBean.getComuneMan().setComune(null);
	}
	
	
	public void precaricaParenteDaComponente(CsAAnagrafica anagraficaComponenteDaAltraScheda) {
		
		cognome =anagraficaComponenteDaAltraScheda.getCognome();
		nome = anagraficaComponenteDaAltraScheda.getNome();
		datiSesso = new SessoBean(anagraficaComponenteDaAltraScheda.getSesso());
		dataNascita = anagraficaComponenteDaAltraScheda.getDataNascita();
		codFiscale = anagraficaComponenteDaAltraScheda.getCf();
		telefono = anagraficaComponenteDaAltraScheda.getTel();
		cellulare = anagraficaComponenteDaAltraScheda.getCell();
		email = anagraficaComponenteDaAltraScheda.getEmail();
		decesso = (anagraficaComponenteDaAltraScheda.getDataDecesso()!=null);
		dataDecesso = anagraficaComponenteDaAltraScheda.getDataDecesso();
		note = anagraficaComponenteDaAltraScheda.getNote();
		cittadinanza = anagraficaComponenteDaAltraScheda.getCittadinanza();
		comuneNazioneNascitaBean.getComuneMan().setComune(new ComuneBean(anagraficaComponenteDaAltraScheda.getComuneNascitaCod(), anagraficaComponenteDaAltraScheda.getComuneNascitaDes(), anagraficaComponenteDaAltraScheda.getProvNascitaCod()) );
		
		
	}

	@Override
	public Long getIdParentela() {
		return idParentela;
	}

	public void setIdParentela(Long idParentela) {
		if(lstParentela != null){
			for(CsTbTipoRapportoCon item: lstParentelaModel){
				if(idParentela.equals(item.getId()))
					parentelaModel = item;
			}
		}
		this.idParentela = idParentela;
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
	public List<SelectItem> getLstParentela() {
		if(lstParentela == null){
			lstParentela = new ArrayList<SelectItem>();
			lstParentela.add(new SelectItem("", "- seleziona -"));
			CeTBaseObject bo = new CeTBaseObject();
			fillEnte(bo);
			lstParentelaModel = confService.getTipoRapportoConParenti(bo);
			if (lstParentelaModel != null) {
				for (CsTbTipoRapportoCon obj : lstParentelaModel) {
					lstParentela.add(new SelectItem(obj.getId(), obj.getDescrizione()));
				}
			}		
		}
		
		return lstParentela;
	}

	public void setLstParentela(List<SelectItem> lstParentela) {
		this.lstParentela = lstParentela;
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
	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getCodFiscale() {
		return codFiscale;
	}

	public void setCodFiscale(String codFiscale) {
		this.codFiscale = codFiscale;
	}

	@Override
	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	@Override
	public String getCivico() {
		return civico;
	}

	public void setCivico(String civico) {
		this.civico = civico;
	}

	@Override
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	@Override
	public String getCellulare() {
		return cellulare;
	}

	public void setCellulare(String cellulare) {
		this.cellulare = cellulare;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Date getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	@Override
	public boolean getConvivente() {
		return convivente;
	}

	@Override
	public SessoBean getDatiSesso() {
		return datiSesso;
	}

	public void setDatiSesso(SessoBean datiSesso) {
		this.datiSesso = datiSesso;
	}

	public void setConvivente(boolean convivente) {
		this.convivente = convivente;
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
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	@Override
	public ComuneNazioneNascitaBean getComuneNazioneNascitaBean() {
		return comuneNazioneNascitaBean;
	}

	public void setComuneNazioneNascitaBean(
			ComuneNazioneNascitaBean comuneNazioneNascitaBean) {
		this.comuneNazioneNascitaBean = comuneNazioneNascitaBean;
	}

	@Override
	public ComuneNazioneResidenzaBean getComuneNazioneResidenzaBean() {
		return comuneNazioneResidenzaBean;
	}

	public void setComuneNazioneResidenzaBean(
			ComuneNazioneResidenzaBean comuneNazioneResidenzaBean) {
		this.comuneNazioneResidenzaBean = comuneNazioneResidenzaBean;
	}

	public String getDialogHeader() {
		return dialogHeader;
	}

	public void setDialogHeader(String dialogHeader) {
		this.dialogHeader = dialogHeader;
	}

	@Override
	public CsTbTipoRapportoCon getParentelaModel() {
		return this.parentelaModel;
	}

	public void setParentelaModel(CsTbTipoRapportoCon rapportoModel) {
		this.parentelaModel = rapportoModel;
	}

	
}
