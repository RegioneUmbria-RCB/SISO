package it.webred.cs.jsf.bean;

import it.webred.cs.csa.ejb.client.AccessTableComuniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableInterventoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.model.CsCTipoIntervento;
import it.webred.cs.data.model.CsCTipoInterventoCustom;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsFlgIntervento;
import it.webred.cs.data.model.CsIAdmAdh;
import it.webred.cs.data.model.CsIAffidoFam;
import it.webred.cs.data.model.CsIBuonoSoc;
import it.webred.cs.data.model.CsICentrod;
import it.webred.cs.data.model.CsIContrEco;
import it.webred.cs.data.model.CsIIntervento;
import it.webred.cs.data.model.CsIPasti;
import it.webred.cs.data.model.CsIResiAdulti;
import it.webred.cs.data.model.CsIResiMinore;
import it.webred.cs.data.model.CsISchedaLavoro;
import it.webred.cs.data.model.CsISemiResiMin;
import it.webred.cs.data.model.CsIVouchersad;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsRelSettCsocTipoInter;
import it.webred.cs.data.model.CsTbInterventiUOL;
import it.webred.cs.data.model.CsTbTipoCirc4;
import it.webred.cs.data.model.CsTbTipoProgetto;
import it.webred.cs.jsf.manbean.ComponenteAltroMan;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.ListaComunitaMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.NoneScoped;
import javax.naming.NamingException;

@ManagedBean
@NoneScoped
public class DatiInterventoBean extends CsUiCompBaseBean implements Serializable {	

	private static final long serialVersionUID = 1L;
	
	protected AccessTableInterventoSessionBeanRemote interventoService = (AccessTableInterventoSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableInterventoSessionBean");

	private Long idSoggetto;
	
	private Long idIntervento;
	private Boolean flagUnatantum;
	private String flagPrimaErRinnovo;
	
	private Date inizioDa;
	private Date inizioA;
	private String tipoDataInizio;
	
	private Date fineDa;
	private Date fineA;
	private String tipoDataFine;
	
	private Long idTipoIntervento;
	private Long idTipoIntrCustom;
	private Long idCatSociale;
	private Long settore;
	private String descTipoIntervento;
	private String descTipoInterventoCustom;
	private String note;
	
	private CsRelSettCsocTipoInter relSettCsocTipoInter;
	
	private ComponenteAltroMan componente;

	//Attributi PASTI
	private CsIPasti pasti;
	private Boolean pastiDieta;
	
	//Attributi CONTRIBUTO ECONOMICO
	private CsIContrEco contrEco;
	private Boolean contrSeStesso;
	private ComponenteAltroMan contrEcoComponente;
	private Long ceTipoContributo;
	
	//Attributi VOUCHER SAD
	private CsIVouchersad sad;
	
	//Attributi CENTRO DIURNO
	private CsICentrod centrod;
	private Boolean centrodDieta;
	private Boolean centrodTrasporto;
	
	//Attributi BUONO SOCIALE
	private CsIBuonoSoc buonosoc;
	private Boolean buonoSeStesso;
	private ComponenteAltroMan buonoSocComponente;
	
	//Attributi RESIDENZIALITA MINORE
	private CsIResiMinore minore;
	private List<String> rmSpeseExtra;
	private Boolean rmSenzaProvvedimento;
	private ComponenteAltroMan rmComponente;
	private Long rmTipoRientriFam;
	private Long rmTipoRetta;
	private ListaComunitaMan rmComunita;
	
	//Attributi RESI_ADULTI
	private CsIResiAdulti resiAdu;
	private Long raTipoRetta;
	private ListaComunitaMan raComunita;
	
	//Attributi ADM-ADH
	private CsIAdmAdh adm;
	private Boolean admSenzaProvvedimento;
	private ComponenteAltroMan admComponente;
	
	//Attributi SEMI RESI MINORI
	private CsISemiResiMin srm;
	private List<String> srmSpeseExtra;
	private Boolean srmPranzi;
	private Boolean srmTrasportoSC;
	private Boolean srmTrasportoCC;
	private Boolean srmSenzaProvvedimento;
	private Long srmTipoRetta;
	private ListaComunitaMan srmComunita;
	private ComponenteAltroMan srmComponente;
	
	//Attributi AFFIDO FAMILIARE
	private CsIAffidoFam affido;
	private List<String> affSpeseExtra;
	private Boolean affSenzaProvvedimento;
	private Boolean affConsensuale;
	private List<String> affTipo;
	private Boolean affSpeseExtraAltro;
	private Boolean affDiurnoGiornaliero;
	private ComuneResidenzaMan affComuneFamiglia;
	
	//Attributi SCHEDA UOL
	private CsISchedaLavoro sl;
	private String slEducatoreLabel;
	private String slTipoCirc4Label;
	private String slInterventoUOLLabel;
	private String slTipoProgettoLabel;

	private Boolean nuovoIntervento = true;
	private List<CsFlgIntervento> listaFogli;
	
	private Boolean interventoChiuso;
	//frida
	private Boolean interventoAperto;
	private Boolean interventoRespinto;
	
	private CsDPai csDPai;//residuo-evoluzione-pai
	
	
	private void initCsISemiResiMin(){
		srm = new CsISemiResiMin();
		srmSpeseExtra = new ArrayList<String>();
		srmComponente = new ComponenteAltroMan(idSoggetto);
		srmComunita = new ListaComunitaMan(ListaComunitaMan.TIPO_COMUNITA_SEMI_RESI_MINORE);
	}
	
	private void initCsIAffidoFam(){
		affido = new CsIAffidoFam();
		affSpeseExtra = new ArrayList<String>();
		affTipo = new ArrayList<String>();
		affComuneFamiglia = new ComuneResidenzaMan();
		
		this.affido.setTipoRiscossione("Riscossione diretta");
		
	}
	
	private void initCsIBuonoSoc(){
		buonosoc = new CsIBuonoSoc();
		buonoSocComponente = new ComponenteAltroMan(idSoggetto);
	}
	
	private void initCsIContrEco(){
		contrEco = new CsIContrEco();
		contrEcoComponente = new ComponenteAltroMan(idSoggetto);
	}
	
	private void initCsIResiMinore(){
		minore = new CsIResiMinore();
		rmSpeseExtra = new ArrayList<String>();
		rmComunita = new ListaComunitaMan(ListaComunitaMan.TIPO_COMUNITA_RESI_MINORE);
		rmComponente = new ComponenteAltroMan(idSoggetto);
	}
	
	private void initCsIAdmAdh(){
		adm = new CsIAdmAdh();
		admComponente = new ComponenteAltroMan(idSoggetto);
	}
	
	private void initCsIResiAdulti(){
		resiAdu = new CsIResiAdulti();
		raComunita = new ListaComunitaMan(ListaComunitaMan.TIPO_COMUNITA_RESI_ADULTO);
	}
	
	private void initCsISchedaLavoro(){
		sl = new CsISchedaLavoro();
		sl.setCsOOperatore(new CsOOperatore());
	}
	
	private void initDettagliIntervento(){
		pasti = new CsIPasti();
		sad = new CsIVouchersad();
		centrod = new CsICentrod();

		initCsIBuonoSoc();
		initCsIContrEco();
		initCsIResiMinore();
		initCsIAdmAdh();
		initCsIAffidoFam();
		initCsISemiResiMin();
		initCsIResiAdulti();
		initCsISchedaLavoro();
	}
	
	public DatiInterventoBean(Long settoreId, Long idSoggetto){
		this.idSoggetto=idSoggetto;
		this.settore = settoreId;
		
		initDettagliIntervento();
		
		listaFogli = new ArrayList<CsFlgIntervento>();
		
		this.setTipoDataInizio("2");
		this.setTipoDataFine("2");
		this.setFlagPrimaErRinnovo("P");
	}
	
	public void resetOnChangeTipoIntervento(){
		initDettagliIntervento();
		
		rmTipoRientriFam=null;
		rmTipoRetta=null;
		srmTipoRetta=null;
		raTipoRetta=null;
		ceTipoContributo=null;
		
		note=null;
	}
	
	public DatiInterventoBean(CsIIntervento ci, Long idSoggetto){
		this.nuovoIntervento = false;
		this.setNote(ci.getNote());
		this.setRelSettCsocTipoInter(ci.getCsRelSettCsocTipoInter());
		
		this.setIdIntervento(ci.getId());
		this.setInizioDa(ci.getInizioDal());
		this.setInizioA(ci.getInizioAl());
		this.setFineDa(ci.getFineDal());
		this.setFineA(ci.getFineAl());
		
		//Valorizzo dati componente familiare
		if(this.getComponente()==null)
			this.setComponente(new ComponenteAltroMan());
		this.getComponente().setCompIndirizzo(ci.getCompIndirizzo());
		this.getComponente().setCompCitta(ci.getCompCitta());
		this.getComponente().setCompDenominazione(ci.getCompDenominazione());
		this.getComponente().setCompTelefono(ci.getCompTel());
		if(ci.getCompCitta()!=null){
			int index = ci.getCompCitta().lastIndexOf('-');
			String citta = ci.getCompCitta().substring(0,index);
			String prov = ci.getCompCitta().substring(index+1);
			this.getComponente().setComuneResidenzaMan(citta, prov);
		}
		this.getComponente().setIdComponente(ci.getCsAComponente()!=null ? ci.getCsAComponente().getId() : null);
			
		if( ci.getCsIInterventoCustom() != null )
		{
			this.setIdTipoIntrCustom(ci.getCsIInterventoCustom().getId());
			this.descTipoInterventoCustom = ci.getCsIInterventoCustom().getDescrizione();
		}
		
		this.setFlagPrimaErRinnovo(ci.getFlagPrimaerRinnovo());
		this.setFlagUnatantum(ci.getFlagUnatantum());
		
		initDettagliIntervento();
		
		if (ci.getCsIInterventoCustom()!=null) {
			
			int tipoCustom = ci.getCsIInterventoCustom().getId().intValue();

			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(ci.getId());
			
			//Valorizzo tipologie intervento custom
			switch (tipoCustom) {
				case 1 : 
					CsIVouchersad vs = interventoService.findVouherSadById(dto);
					this.setSad(vs);
					break; 
				case 2 : 
					CsIContrEco ce = interventoService.findContributoEconomicoById(dto);
					this.setContrEco(ce);
					break;
				case 3 : 	
					CsICentrod cd = interventoService.findCentroDiurnoById(dto);
					this.setCentrod(cd);
					break;
				case 4 :
					CsIPasti pasti = interventoService.findPastiById(dto);
					this.setPasti(pasti);
					break;
				case 5 :
					CsIBuonoSoc bs = interventoService.findBuonoSocialeById(dto);
					this.setBuonosoc(bs);
					break;
				case 6 :
					CsIResiAdulti ra = interventoService.findResiAdultiById(dto);
					this.setResiAdu(ra);
					break;
				case 7 : 
					CsIResiMinore resiMinore = interventoService.findResiMinoreById(dto);
					this.setMinore(resiMinore);
					break;
				case 8 : 
					CsIAffidoFam affido = interventoService.findAffidoById(dto);
					this.setAffido(affido);
					break;
				case 9 : 
					CsIAdmAdh adm = interventoService.findAdmById(dto);
					this.setAdm(adm);
					break;
				case 10: 
					CsISemiResiMin srm = interventoService.findSemiResMinoreById(dto);
					this.setSrm(srm);
					break;
				case 11: 
					CsISchedaLavoro sl = interventoService.findSchedaLavoroById(dto);
					this.setSl(sl);
					break;
			}
			
			

			
		}
	
		this.listaFogli = new ArrayList<CsFlgIntervento>();
		//inizio modifica evoluzione-pai - la lista FlgInterventos può essere vuota nel caso in cui il DatiInterventoBean venga creato nel metodo refreshPicklistInterventi
		if (ci.getCsFlgInterventos()!=null) { 
			this.listaFogli.addAll(ci.getCsFlgInterventos());
		}
		//fine modifica evoluzione-pai

		//INIZIO residuo-evoluzione-pai
		this.csDPai = ci.getCsDPai();
		//FINE residuo-evoluzione-pai
//		if (ci.getCsDPai()!=null) { 
//			System.out.println(  ci.getCsDPai().getCsTbTipoPai().getDescrizione() );
//			System.out.println(  ci.getCsDPai().getCsDDiario().getDtAttivazioneDa()  );
//			System.out.println(  ci.getCsDPai().getCsDDiario().getDtAttivazioneA()  );
//		}
		
		
		
		this.interventoAperto=false;
		this.interventoRespinto=false;
		this.interventoChiuso=false;
		for(CsFlgIntervento f : this.listaFogli){
			if(DataModelCostanti.FoglioAmministrativo.STATO_CHIUSURA.equalsIgnoreCase(f.getFlagAttSospC()))
				this.interventoChiuso=true;
			//TODO frida esiste foglio amministrativo di attivazione??
			//if("A".equalsIgnoreCase(f.getFlagAttSospC()))
			if(DataModelCostanti.FoglioAmministrativo.STATO_ATTIVAZIONE.equalsIgnoreCase(f.getFlagAttSospC()))
				this.interventoAperto=true;
			//TODO frida esiste un foglio amministrativo con respinto ==true??
			if(f.getFlagRespinto().compareTo(new BigDecimal(0))!=0)
				this.interventoRespinto=true;
		}
		
	}
	
	public Boolean getNuovoIntervento() {
		return nuovoIntervento;
	}
	public void setNuovoIntervento(Boolean nuovoIntervento) {
		this.nuovoIntervento = nuovoIntervento;
	}
	
	public Boolean getFlagUnatantum() {
		return flagUnatantum;
	}
	public void setFlagUnatantum(Boolean flagUnatantum) {
		this.flagUnatantum = flagUnatantum;
	}
	public Date getInizioDa() {
		return inizioDa;
	}
	public void setInizioDa(Date inizioDa) {
		this.inizioDa = inizioDa;
	}
	public Date getInizioA() {
		return inizioA;
	}
	public void setInizioA(Date inizioA) {
		this.inizioA = inizioA;
	}

	public Long getIdTipoIntervento() {
		return idTipoIntervento;
	}

	public void setIdTipoIntervento(Long idTipoIntervento) {
		this.idTipoIntervento = idTipoIntervento;
	}

	public String getDescTipoIntervento() {
		return descTipoIntervento;
	}

	public void setDescTipoIntervento(String descTipoIntervento) {
		this.descTipoIntervento = descTipoIntervento;
	}

	public String getDescTipoInterventoCustom() {
		return descTipoInterventoCustom;
	}

	public void setDescTipoInterventoCustom(String descTipoInterventoCustom) {
		this.descTipoInterventoCustom = descTipoInterventoCustom;
	}

	public String getTipoDataInizio() {
		return tipoDataInizio;
	}

	public void setTipoDataInizio(String tipoDataInizio) {
		this.tipoDataInizio = tipoDataInizio;
	}

	public CsIPasti getPasti() {
		return pasti;
	}

	public void setPasti(CsIPasti pasti) {
		this.pasti = pasti!=null ? pasti : new CsIPasti();
		this.pasti.setInterventoId(idIntervento);
	}

	public CsIContrEco getContrEco() {
		return contrEco;
	}

	public void setContrEco(CsIContrEco contrEco) {
		this.contrEco = contrEco!=null ? contrEco : new CsIContrEco();
		this.contrEco.setInterventoId(idIntervento);
		
		//Valorizzo dati Componente da usare nel pannello
		if(contrEco!=null && contrEco.getCsAComponente()!=null){
			this.contrEcoComponente.setCompIndirizzo(this.contrEco.getCompIndirizzo());
			this.contrEcoComponente.setCompCitta(this.contrEco.getCompCitta());
			this.contrEcoComponente.setCompDenominazione(this.contrEco.getCompDenominazione());
			this.contrEcoComponente.setCompTelefono(this.contrEco.getCompTel());
			if(this.contrEco.getCompCitta()!=null){
				int index = this.contrEco.getCompCitta().lastIndexOf('-');
				String citta = this.contrEco.getCompCitta().substring(0,index);
				String prov = this.contrEco.getCompCitta().substring(index+1);
				this.contrEcoComponente.setComuneResidenzaMan(citta, prov);
			}
			this.contrEcoComponente.setIdComponente(this.contrEco.getCsAComponente().getId());
			if(this.contrEco.getCsTbTipoContributo() !=null)
				this.ceTipoContributo = this.contrEco.getCsTbTipoContributo().getId();
		}
		
	}
	
	
	public CsIVouchersad getSad() {
		return sad;
	}

	public void setSad(CsIVouchersad sad) {
		this.sad = sad!=null ? sad : new CsIVouchersad();
		this.sad.setInterventoId(idIntervento);
		
	}

	public CsIResiMinore getMinore() {
		return minore;
	}

	public void setMinore(CsIResiMinore minore) {
		this.minore = minore!=null ? minore : new CsIResiMinore();
		this.minore.setInterventoId(idIntervento);
		if(this.minore.getCsTbTipoRientriFami()!=null)
			this.rmTipoRientriFam = this.minore.getCsTbTipoRientriFami().getId();
		if(this.minore.getCsTbTipoRetta()!=null)
			this.rmTipoRetta = this.minore.getCsTbTipoRetta().getId();
		
		//Valorizzo dati Componente da usare nel pannello
		if(minore!=null && minore.getCsAComponente()!=null){
			this.rmComponente.setCompIndirizzo(this.minore.getCompIndirizzo());
			this.rmComponente.setCompCitta(this.minore.getCompCitta());
			this.rmComponente.setCompDenominazione(this.minore.getCompDenominazione());
			this.rmComponente.setCompTelefono(this.minore.getCompTel());
			if(this.minore.getCompCitta()!=null){
				int index = this.minore.getCompCitta().lastIndexOf('-');
				String citta = this.minore.getCompCitta().substring(0,index);
				String prov = this.minore.getCompCitta().substring(index+1);
				this.rmComponente.setComuneResidenzaMan(citta, prov);
			}
			this.rmComponente.setIdComponente(this.minore.getCsAComponente().getId());
		}
		
		if(minore!=null && minore.getCsCComunita()!=null)
			this.rmComunita.setIdComunita(minore.getCsCComunita().getSettoreId());
		
	}
	

	public CsICentrod getCentrod() {
		return centrod;
	}

	public void setCentrod(CsICentrod centrod) {
		this.centrod = centrod!=null ? centrod : new CsICentrod();
		this.centrod.setInterventoId(idIntervento);
	}

	public CsIBuonoSoc getBuonosoc() {
		return buonosoc;
	}

	public void setBuonosoc(CsIBuonoSoc buonosoc) {
		this.buonosoc = buonosoc!=null ? buonosoc : new CsIBuonoSoc();
		this.buonosoc.setInterventoId(idIntervento);
		
		//Valorizzo dati Componente da usare nel pannello
		if(buonosoc!=null && buonosoc.getCsAComponente()!=null){
			this.buonoSocComponente.setCompIndirizzo(this.buonosoc.getCompIndirizzo());
			this.buonoSocComponente.setCompCitta(this.buonosoc.getCompCitta());
			this.buonoSocComponente.setCompDenominazione(this.buonosoc.getCompDenominazione());
			this.buonoSocComponente.setCompTelefono(this.buonosoc.getCompTel());
			if(this.buonosoc.getCompCitta()!=null){
				int index = this.buonosoc.getCompCitta().lastIndexOf('-');
				String citta = this.buonosoc.getCompCitta().substring(0,index);
				String prov = this.buonosoc.getCompCitta().substring(index+1);
				this.buonoSocComponente.setComuneResidenzaMan(citta, prov);
			}
			this.buonoSocComponente.setIdComponente(this.buonosoc.getCsAComponente().getId());
		}
		
	}
	
	public CsIResiAdulti getResiAdu() {
		return resiAdu;
	}

	public void setResiAdu(CsIResiAdulti resiAdu) {
		this.resiAdu = resiAdu!=null ? resiAdu : new CsIResiAdulti();
		this.resiAdu.setInterventoId(idIntervento);	
		
		if(this.resiAdu.getCsTbTipoRetta()!=null)
			this.raTipoRetta = this.resiAdu.getCsTbTipoRetta().getId();
		
		if(resiAdu!=null && resiAdu.getCsCComunita()!=null)
			this.raComunita.setIdComunita(resiAdu.getCsCComunita().getSettoreId());

	}

	public CsIAdmAdh getAdm() {
		return adm;
	}

	public void setAdm(CsIAdmAdh adm) {
		this.adm = adm!=null ? adm : new CsIAdmAdh();
		this.adm.setInterventoId(this.idIntervento);
	}

	public CsISemiResiMin getSrm() {
		return srm;
	}

	public void setSrm(CsISemiResiMin srm) {
		this.srm = srm!=null ? srm : new CsISemiResiMin();
		this.srm.setInterventoId(this.idIntervento);
		
		if(this.srm.getCsTbTipoRetta()!=null)
			this.srmTipoRetta = this.srm.getCsTbTipoRetta().getId();
		
		if(srm!=null && srm.getCsCComunita()!=null)
			this.srmComunita.setIdComunita(srm.getCsCComunita().getSettoreId());
		
	}

	public CsIAffidoFam getAffido() {
		return affido;
	}

	public void setAffido(CsIAffidoFam affido) {
		this.affido = affido!=null ? affido : new CsIAffidoFam();
		this.affido.setInterventoId(this.idIntervento);
		
		try{
			//Valorizzo il comune della famiglia affiataria
			if(this.affido.getFamCitta()!=null){
				int index = this.affido.getFamCitta().lastIndexOf('-');
				String citta = this.affido.getFamCitta().substring(0,index);
				String prov = this.affido.getFamCitta().substring(index+1);
				AccessTableComuniSessionBeanRemote bean = (AccessTableComuniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableComuniSessionBean");
				AmTabComuni amComune = bean.getComuneByDenominazioneProv(citta, prov);
				ComuneBean comune = new ComuneBean(amComune.getCodIstatComune(),amComune.getDenominazione(),amComune.getSiglaProv());
				this.affComuneFamiglia.setComune(comune);
			}
		}catch(Exception e){}
		
	}
	
	public CsISchedaLavoro getSl() {
		return sl;
	}

	public void setSl(CsISchedaLavoro schedal) {
		this.sl = schedal!=null ? schedal : new CsISchedaLavoro();
		this.sl.setInterventoId(this.idIntervento);
		
		try{
			AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");	
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			
			if(sl.getCsOOperatore() != null && sl.getCsOOperatore().getUsername() != null)
				slEducatoreLabel = this.getDenominazioneOperatore(sl.getCsOOperatore());	
			else sl.setCsOOperatore(new CsOOperatore());
			
			if(sl.getInterventiUOLId() != null) {
				dto.setObj(sl.getInterventiUOLId());
				CsTbInterventiUOL cs = confService.getInterventiUOLById(dto);
				slInterventoUOLLabel = cs.getDescrizione();
			}
			
			if(sl.getTipoCirc4Id() != null) {
				dto.setObj(sl.getTipoCirc4Id());
				CsTbTipoCirc4 cs = confService.getTipoCirc4ById(dto);
				slTipoCirc4Label = cs.getDescrizione();
			}
			
			if(sl.getTipoProgettoId() != null) {
				dto.setObj(sl.getTipoProgettoId());
				CsTbTipoProgetto cs = confService.getTipoProgettoById(dto);
				slTipoProgettoLabel = cs.getDescrizione();
			}
		}catch(Exception e){
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}

	public List<CsFlgIntervento> getListaFogli() {
		return listaFogli;
	}

	public void setListaFogli(List<CsFlgIntervento> listaFogli) {
		this.listaFogli = listaFogli;
	}

	public Long getIdIntervento() {
		return idIntervento;
	}

	public void setIdIntervento(Long idIntervento) {
		this.idIntervento = idIntervento;
	}

	public Boolean getInterventoChiuso() {
		return interventoChiuso;
	}

	public void setInterventoChiuso(Boolean interventoChiuso) {
		this.interventoChiuso = interventoChiuso;
	}

	public Boolean getPastiDieta() {
		BigDecimal ds = this.pasti.getDietaSpeciale();
		this.pastiDieta = ds!=null && ds.intValue()==1 ? true : false;
		return pastiDieta;
	}

	public void setPastiDieta(Boolean dietaSpeciale) {
		this.pastiDieta = dietaSpeciale;
		this.pasti.setDietaSpeciale(dietaSpeciale ? new BigDecimal(1) : BigDecimal.ZERO);
	}


	public Boolean getCentrodDieta() {
		BigDecimal ds = this.centrod.getDietaSpeciale();
		this.centrodDieta = ds!=null && ds.intValue()==1 ? true : false;
		return centrodDieta;
	}

	public void setCentrodDieta(Boolean centrodDieta) {
		this.centrodDieta = centrodDieta;
		this.centrod.setDietaSpeciale(centrodDieta ? new BigDecimal(1) : BigDecimal.ZERO);
	}

	public Boolean getCentrodTrasporto() {
		BigDecimal ds = this.centrod.getFlagNecessTrasporto();
		this.centrodTrasporto = ds!=null && ds.intValue()==1 ? true : false;
		return centrodTrasporto;
	}

	public void setCentrodTrasporto(Boolean centrodTrasporto) {
		this.centrodTrasporto = centrodTrasporto;
		this.centrod.setFlagNecessTrasporto(this.centrodTrasporto ? new BigDecimal(1) : BigDecimal.ZERO);
	}

	public Boolean getContrSeStesso() {
		BigDecimal ds = this.contrEco.getRichSeStesso();
		contrSeStesso = ds!=null && ds.intValue()==1 ? true : false;
		return contrSeStesso;
	}

	public void setContrSeStesso(Boolean contrSeStesso) {
		this.contrSeStesso = contrSeStesso;
		this.contrEco.setRichSeStesso(contrSeStesso ? new BigDecimal(1) : BigDecimal.ZERO);
	}

	public Boolean getBuonoSeStesso() {
		BigDecimal ds = this.buonosoc.getRichSeStesso();
		buonoSeStesso = ds!=null && ds.intValue()==1 ? true : false;
		return buonoSeStesso;
	}

	public void setBuonoSeStesso(Boolean buonoSeStesso) {
		this.buonoSeStesso = buonoSeStesso;
		this.buonosoc.setRichSeStesso(buonoSeStesso ? new BigDecimal(1) : BigDecimal.ZERO);
	}

	public Long getSettore() {
		return settore;
	}

	public void setSettore(Long settore) {
		this.settore = settore;
	}

	private void valorizzaContrEco() throws NamingException{
		this.contrEco.setCompCitta(this.contrEcoComponente.getCompCitta());
		this.contrEco.setCompDenominazione(this.contrEcoComponente.getCompDenominazione());
		this.contrEco.setCompTel(this.contrEcoComponente.getCompTelefono());
		this.contrEco.setCompIndirizzo(this.contrEcoComponente.getCompIndirizzo());
		this.contrEco.setCsAComponente(this.contrEcoComponente.getComponenteSel());
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);		
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) 
				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		b.setObj(this.ceTipoContributo);
		this.contrEco.setCsTbTipoContributo(confService.getTipoContributo(b));
	}
	
	private void valorizzaBuonoSoc(){
		this.buonosoc.setCompCitta(this.buonoSocComponente.getCompCitta());
		this.buonosoc.setCompDenominazione(this.buonoSocComponente.getCompDenominazione());
		this.buonosoc.setCompTel(this.buonoSocComponente.getCompTelefono());
		this.buonosoc.setCompIndirizzo(this.buonoSocComponente.getCompIndirizzo());
		this.buonosoc.setCsAComponente(this.buonoSocComponente.getComponenteSel());
	}
	
	private void valorizzaResiMinore() throws NamingException{
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		
		this.minore.setCompCitta(this.rmComponente.getCompCitta());
		this.minore.setCompDenominazione(this.rmComponente.getCompDenominazione());
		this.minore.setCompTel(this.rmComponente.getCompTelefono());
		this.minore.setCompIndirizzo(this.rmComponente.getCompIndirizzo());
		this.minore.setCsAComponente(this.rmComponente.getComponenteSel());
		
		this.minore.setCsCComunita(this.rmComunita.getComunitaSel());
		
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) 
				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		
		b.setObj(this.rmTipoRetta);
		this.minore.setCsTbTipoRetta(confService.getTipoRetta(b));
		
		b.setObj(this.rmTipoRientriFam);
		this.minore.setCsTbTipoRientriFami(confService.getTipoRientriFami(b));
	}
	
	private void valorizzaAffidoFamiliare(){		
		if(this.affComuneFamiglia!=null && this.affComuneFamiglia.getComune()!=null){
			ComuneBean comune = this.affComuneFamiglia.getComune();
			this.affido.setFamCitta(comune.getDenominazione()+"-"+comune.getSiglaProv());
		}
		
	}
	private void valorizzaSemiResiMinore() throws NamingException{
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		
		this.srm.setCsCComunita(this.srmComunita.getComunitaSel());
		
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) 
				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		
		b.setObj(this.srmTipoRetta);
		this.srm.setCsTbTipoRetta(confService.getTipoRetta(b));
		
	}
	
	private void valorizzaResiAdulti() throws NamingException{
		
		BaseDTO b = new BaseDTO();
		fillEnte(b);
		
		this.resiAdu.setCsCComunita(this.raComunita.getComunitaSel());
		
		AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) 
				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
		
		b.setObj(this.raTipoRetta);
		this.resiAdu.setCsTbTipoRetta(confService.getTipoRetta(b));
		
	}
	
	private void valorizzaSchedaUOL() {
		if(sl.getCsOOperatore().getId()==null || sl.getCsOOperatore().getId()<=0)
			sl.setCsOOperatore(null);
	}
	
	public void valorizzaDettaglioIntervento(CsIIntervento intervento) throws NamingException{
		/*
		1	Voucher SAD
		2	Contributo Economico		
		3	Centro diurno Integrato		
		4	Pasti Domiciliari		
		5	Buono Sociale		
		6	Intervento Residenziale per Adulti		
		7	Residenzialità Minore		
		8	Affido Familiare		
		9	Assistenza Domiciliare Minori		
		10  Semi Residenzialità Minore
		11  Scheda UOL		
		*/
		
		intervento.setInizioDal(this.getInizioDa());
		intervento.setInizioAl(this.getInizioA());
		intervento.setFlagPrimaerRinnovo(this.getFlagPrimaErRinnovo());
		intervento.setFlagUnatantum(this.getFlagUnatantum());
		intervento.setFineDal(this.getFineDa());
		intervento.setFineAl(this.getFineA());
		intervento.setCompDenominazione(this.getComponente().getCompDenominazione());
		intervento.setCompCitta(this.getComponente().getCompCitta());
		intervento.setCompTel(this.getComponente().getCompTelefono());	
		intervento.setCompIndirizzo(this.getComponente().getCompIndirizzo());
		intervento.setCsAComponente(this.getComponente().getComponenteSel());
		intervento.setNote(this.getNote());
		

		if(this.idTipoIntrCustom!=null){
			int tipo = (int)this.idTipoIntrCustom.doubleValue();
			
			switch (tipo) {
				case 1 : 			
						HashSet<CsIVouchersad> vouchersad = new HashSet<CsIVouchersad>(); 
						vouchersad.add(sad);
	  				    intervento.setCsIVouchersad(vouchersad); 
	  				    break; 
				case 2 : valorizzaContrEco(); 
						 HashSet<CsIContrEco> contribeco = new HashSet<CsIContrEco>(); 
						 contribeco.add(contrEco);
						 intervento.setCsIContrEco(contribeco); 
						 break;
				case 3 : HashSet<CsICentrod> centrodset = new HashSet<CsICentrod>(); 
						 centrodset.add(centrod);
						 intervento.setCsICentrod(centrodset); 
						 break;
				case 4 : HashSet<CsIPasti> pas = new HashSet<CsIPasti>(); 
						 pas.add(pasti);
						 intervento.setCsIPasti(pas);
						 break;
				case 5 : valorizzaBuonoSoc();
						 HashSet<CsIBuonoSoc> bs = new HashSet<CsIBuonoSoc>(); 
						 bs.add(buonosoc);
						 intervento.setCsIBuonoSoc(bs);		 
				case 6 : valorizzaResiAdulti();
						 HashSet<CsIResiAdulti> ra = new HashSet<CsIResiAdulti>(); 
						 ra.add(resiAdu);
						 intervento.setCsIResiAdulti(ra);	
						 break;
				case 7 : valorizzaResiMinore();
						 HashSet<CsIResiMinore> rm = new HashSet<CsIResiMinore>(); 
						 rm.add(minore);
						 intervento.setCsIResiMinore(rm);
						 break;
				case 8 : valorizzaAffidoFamiliare();
						 HashSet<CsIAffidoFam> aff = new HashSet<CsIAffidoFam>(); 
						 aff.add(affido);
						 intervento.setCsIAffidoFam(aff);
						 break;
				case 9 : 
						 HashSet<CsIAdmAdh> admadhset = new HashSet<CsIAdmAdh>(); 
						 admadhset.add(adm);
						 intervento.setCsIAdmAdh(admadhset);
						 break;
				case 10: valorizzaSemiResiMinore();
						 HashSet<CsISemiResiMin> srmset = new HashSet<CsISemiResiMin>(); 
						 srmset.add(srm);
						 intervento.setCsISemiResiMin(srmset);
						 break;
				case 11: valorizzaSchedaUOL();
						 HashSet<CsISchedaLavoro> schedalav = new HashSet<CsISchedaLavoro>(); 
						 schedalav.add(sl);
						 intervento.setCsISchedaLavoro(schedalav);
						 break;
			}
		}		
	}

	public ComponenteAltroMan getContrEcoComponente() {
		return contrEcoComponente;
	}

	public void setContrEcoComponente(ComponenteAltroMan contrEcoComponente) {
		this.contrEcoComponente = contrEcoComponente;
	}

	public ComponenteAltroMan getBuonoSocComponente() {
		return buonoSocComponente;
	}

	public void setBuonoSocComponente(ComponenteAltroMan buonoSocComponente) {
		this.buonoSocComponente = buonoSocComponente;
	}

	public ComponenteAltroMan getAdmComponente() {
		return admComponente;
	}

	public void setAdmComponente(ComponenteAltroMan admComponente) {
		this.admComponente = admComponente;
	}

	public ComponenteAltroMan getSrmComponente() {
		return srmComponente;
	}

	public void setSrmComponente(ComponenteAltroMan srmComponente) {
		this.srmComponente = srmComponente;
	}

	public String getFlagPrimaErRinnovo() {
		return flagPrimaErRinnovo;
	}

	public void setFlagPrimaErRinnovo(String flagPrimaErRinnovo) {
		this.flagPrimaErRinnovo = flagPrimaErRinnovo;
	}

	public Date getFineDa() {
		return fineDa;
	}

	public void setFineDa(Date fineDa) {
		this.fineDa = fineDa;
	}

	public Date getFineA() {
		return fineA;
	}

	public void setFineA(Date fineA) {
		this.fineA = fineA;
	}

	public String getTipoDataFine() {
		return tipoDataFine;
	}

	public void setTipoDataFine(String tipoDataFine) {
		this.tipoDataFine = tipoDataFine;
	}
	
	
	public List<String> getRmSpeseExtra() {
		rmSpeseExtra = new ArrayList<String>();
		
		if(this.getBooleanFromNumber(this.minore.getFlagIncontriProtetti()))
			this.rmSpeseExtra.add("INCONTRI");
		if(this.getBooleanFromNumber(this.minore.getFlagSpesesani()))
			this.rmSpeseExtra.add("SANITARIE");
		if(this.getBooleanFromNumber(this.minore.getFlagSpeseVacanze()))
			this.rmSpeseExtra.add("VACANZE");
		if(this.getBooleanFromNumber(this.minore.getFlagInterventoEdu()))
			this.rmSpeseExtra.add("INT_EDU");
		if(this.getBooleanFromNumber(this.minore.getFlagRimborsoTesti()))
			this.rmSpeseExtra.add("TESTI");
		if(this.getBooleanFromNumber(this.minore.getFlagAltro()))
			this.rmSpeseExtra.add("ALTRO");
		
		return rmSpeseExtra;
	}
	
	public void setRmSpeseExtra(List<String> rmSpeseExtra) {
		this.rmSpeseExtra = rmSpeseExtra;
		this.minore.setFlagIncontriProtetti(this.getNumberFromBoolean(this.rmSpeseExtra.contains("INCONTRI")));
		this.minore.setFlagSpesesani(this.getNumberFromBoolean(this.rmSpeseExtra.contains("SANITARIE")));
		this.minore.setFlagSpeseVacanze(this.getNumberFromBoolean(this.rmSpeseExtra.contains("VACANZE")));
		this.minore.setFlagInterventoEdu(this.getNumberFromBoolean(this.rmSpeseExtra.contains("INT_EDU")));
		this.minore.setFlagRimborsoTesti(this.getNumberFromBoolean(this.rmSpeseExtra.contains("TESTI")));
		this.minore.setFlagAltro(this.getNumberFromBoolean(this.rmSpeseExtra.contains("ALTRO")));
	}
	
	public List<String> getAffSpeseExtra() {
		affSpeseExtra = new ArrayList<String>();
		
		if(this.getBooleanFromNumber(this.affido.getFlagIncontriProtetti()))
			this.affSpeseExtra.add("INCONTRI");
		if(this.getBooleanFromNumber(this.affido.getFlagSpesesani()))
			this.affSpeseExtra.add("SANITARIE");
		if(this.getBooleanFromNumber(this.affido.getFlagSpeseVacanze()))
			this.affSpeseExtra.add("VACANZE");
		if(this.getBooleanFromNumber(this.affido.getFlagInterventoEdu()))
			this.affSpeseExtra.add("INT_EDU");
		if(this.getBooleanFromNumber(this.affido.getFlagRimborsoTesti()))
			this.affSpeseExtra.add("TESTI");
		if(this.getBooleanFromNumber(this.affido.getFlagAltro()))
			this.affSpeseExtra.add("ALTRO");
		
		return affSpeseExtra;
	}
	
	public void setAffSpeseExtra(List<String> rmSpeseExtra) {
		this.affSpeseExtra = rmSpeseExtra;
		this.affido.setFlagIncontriProtetti(this.getNumberFromBoolean(this.affSpeseExtra.contains("INCONTRI")));
		this.affido.setFlagSpesesani(this.getNumberFromBoolean(this.affSpeseExtra.contains("SANITARIE")));
		this.affido.setFlagSpeseVacanze(this.getNumberFromBoolean(this.affSpeseExtra.contains("VACANZE")));
		this.affido.setFlagInterventoEdu(this.getNumberFromBoolean(this.affSpeseExtra.contains("INT_EDU")));
		this.affido.setFlagRimborsoTesti(this.getNumberFromBoolean(this.affSpeseExtra.contains("TESTI")));
		this.affido.setFlagAltro(this.getNumberFromBoolean(this.affSpeseExtra.contains("ALTRO")));
	}
	
	public List<String> getSrmSpeseExtra() {
		srmSpeseExtra =  new ArrayList<String>();
		
		if(this.getBooleanFromNumber(srm.getFlagPernottamento()))
			this.srmSpeseExtra.add("PERNOTTAMENTO");
		if(this.getBooleanFromNumber(srm.getFlagCene()))
			this.srmSpeseExtra.add("CENE");
		if(this.getBooleanFromNumber(srm.getFlagInterventoEdu()))
			this.srmSpeseExtra.add("INT_EDU");
		if(this.getBooleanFromNumber(srm.getFlagAltro()))
			this.srmSpeseExtra.add("ALTRO");
		
		return srmSpeseExtra;
	}

	public void setSrmSpeseExtra(List<String> rmSpeseExtra) {
		this.srmSpeseExtra = rmSpeseExtra;
		this.srm.setFlagCene(this.getNumberFromBoolean(this.srmSpeseExtra.contains("CENE")));
		this.srm.setFlagInterventoEdu(this.getNumberFromBoolean(this.srmSpeseExtra.contains("INT_EDU")));
		this.srm.setFlagPernottamento(this.getNumberFromBoolean(this.srmSpeseExtra.contains("PERNOTTAMENTO")));
		this.srm.setFlagAltro(this.getNumberFromBoolean(this.srmSpeseExtra.contains("ALTRO")));
	}
	
	
	public List<String> getAffTipo() {
		
		if(this.getBooleanFromNumber(affido.getFlagAEteroFam()))
			this.affTipo.add("ETERO_FAMILIARE");
		if(this.getBooleanFromNumber(affido.getFlagAParenti()))
			this.affTipo.add("PARENTI");
		if(this.getBooleanFromNumber(affido.getFlagADiurnoMese()))
			this.affTipo.add("DIURNO_MESE");
		if(this.getBooleanFromNumber(affido.getFlagADiurnoGiornaliero()))
			this.affTipo.add("DIURNO_GIORNALIERO");
			
		return affTipo;
	}

	public void setAffTipo(List<String> affTipo) {
		this.affTipo = affTipo;
		this.affido.setFlagADiurnoGiornaliero(getNumberFromBoolean(affTipo.contains("DIURNO_GIORNALIERO")));
		this.affido.setFlagADiurnoMese(getNumberFromBoolean(affTipo.contains("DIURNO_MESE")));
		this.affido.setFlagAEteroFam(getNumberFromBoolean(affTipo.contains("ETERO_FAMILIARE")));
		this.affido.setFlagAParenti(getNumberFromBoolean(affTipo.contains("PARENTI")));
	}

	public Boolean getRmSenzaProvvedimento() {
		this.rmSenzaProvvedimento = this.getBooleanFromNumber(this.minore.getFlagSenzaProvvedimento());
		return rmSenzaProvvedimento;
	}

	public void setRmSenzaProvvedimento(Boolean rmAenzaProvvedimento) {
		this.rmSenzaProvvedimento = rmAenzaProvvedimento;
		this.minore.setFlagSenzaProvvedimento(this.getNumberFromBoolean(this.rmSenzaProvvedimento));	
	}

	public Boolean getAdmSenzaProvvedimento() {
		this.admSenzaProvvedimento = this.getBooleanFromNumber(this.adm.getFlagSenzaProvvedimento());
		return admSenzaProvvedimento;
	}

	public void setAdmSenzaProvvedimento(Boolean admSenzaProvvedimento) {
		this.admSenzaProvvedimento = admSenzaProvvedimento;
		this.adm.setFlagSenzaProvvedimento(this.getNumberFromBoolean(this.admSenzaProvvedimento));	
	}

	
	public ComponenteAltroMan getRmComponente() {
		return rmComponente;
	}

	public void setRmComponente(ComponenteAltroMan rmComponente) {
		this.rmComponente = rmComponente;
	}

	private Boolean getBooleanFromNumber(BigDecimal ds){
		return ds!=null && ds.intValue()==1 ? true : false;
	}
	
	private BigDecimal getNumberFromBoolean(Boolean b){
		return b ? new BigDecimal(1) : BigDecimal.ZERO;
	}

	public Long getRmTipoRientriFam() {
		return rmTipoRientriFam;
	}

	public void setRmTipoRientriFam(Long rmTipoRientriFam) {
		this.rmTipoRientriFam = rmTipoRientriFam;
	}

	public Long getRmTipoRetta() {
		return rmTipoRetta;
	}

	public void setRmTipoRetta(Long rmTipoRetta) {
		this.rmTipoRetta = rmTipoRetta;
	}

	public ListaComunitaMan getRmComunita() {
		return rmComunita;
	}

	public void setRmComunita(ListaComunitaMan rmComunita) {
		this.rmComunita = rmComunita;
	}

	public CsRelSettCsocTipoInter getRelSettCsocTipoInter() {
		return relSettCsocTipoInter;
	}

	public void setRelSettCsocTipoInter(CsRelSettCsocTipoInter relSettCsocTipoInter) {
		this.relSettCsocTipoInter = relSettCsocTipoInter;
		this.setSettore(relSettCsocTipoInter.getId().getScsSettoreId());
		this.setIdTipoIntervento(relSettCsocTipoInter.getId().getCstiTipoInterventoId());
		this.setDescTipoIntervento(relSettCsocTipoInter.getCsRelCatsocTipoInter().getCsCTipoIntervento().getDescrizione());
		this.setIdCatSociale(relSettCsocTipoInter.getCsRelCatsocTipoInter().getCsCCategoriaSociale().getId());
	}

	public Boolean getSrmPranzi() {
		srmPranzi = this.getBooleanFromNumber(this.srm.getFlagPranzo());
		return srmPranzi;
	}

	public void setSrmPranzi(Boolean srmPranzi) {
		this.srmPranzi = srmPranzi;
		this.srm.setFlagPranzo(this.getNumberFromBoolean(this.srmPranzi));
	}

	public Boolean getSrmTrasportoSC() {
		srmTrasportoSC = this.getBooleanFromNumber(this.srm.getFlagTrasportoSc());
		return srmTrasportoSC;
	}

	public void setSrmTrasportoSC(Boolean srmTrasportoSC) {
		this.srmTrasportoSC = srmTrasportoSC;
		this.srm.setFlagTrasportoSc(getNumberFromBoolean(this.srmTrasportoSC));
	}

	public Boolean getSrmTrasportoCC() {
		srmTrasportoCC = this.getBooleanFromNumber(srm.getFlagTrasportoCc());
		return srmTrasportoCC;
	}

	public void setSrmTrasportoCC(Boolean srmTrasportoCC) {
		this.srmTrasportoCC = srmTrasportoCC;
		srm.setFlagTrasportoCc(getNumberFromBoolean(srmTrasportoCC));
	}

	public Boolean getSrmSenzaProvvedimento() {
		srmSenzaProvvedimento = getBooleanFromNumber(srm.getFlagSenzaProvvedimento());
		return srmSenzaProvvedimento;
	}

	public void setSrmSenzaProvvedimento(Boolean srmSenzaProvvedimento) {
		this.srmSenzaProvvedimento = srmSenzaProvvedimento;
		this.srm.setFlagSenzaProvvedimento(getNumberFromBoolean(this.srmSenzaProvvedimento));
	}

	public Long getSrmTipoRetta() {
		return srmTipoRetta;
	}

	public void setSrmTipoRetta(Long srmTipoRetta) {
		this.srmTipoRetta = srmTipoRetta;
	}

	public Boolean getAffSenzaProvvedimento() {
		affSenzaProvvedimento = getBooleanFromNumber(affido.getFlagSenzaProvvedimento());
		return affSenzaProvvedimento;
	}

	public void setAffSenzaProvvedimento(Boolean affSenzaProvvedimento) {
		this.affSenzaProvvedimento = affSenzaProvvedimento;
		this.affido.setFlagSenzaProvvedimento(getNumberFromBoolean(this.affSenzaProvvedimento));
	}

	public Boolean getAffConsensuale() {
		affConsensuale = getBooleanFromNumber(affido.getFlagConsensuale());
		return affConsensuale;
	}

	public void setAffConsensuale(Boolean affConsensuale) {
		this.affConsensuale = affConsensuale;
		this.affido.setFlagConsensuale(getNumberFromBoolean(this.affConsensuale));
	}

	public Long getIdSoggetto() {
		return idSoggetto;
	}

	public void setIdSoggetto(Long idSoggetto) {
		this.idSoggetto = idSoggetto;
	}

	public ListaComunitaMan getSrmComunita() {
		return srmComunita;
	}

	public void setSrmComunita(ListaComunitaMan srmComunita) {
		this.srmComunita = srmComunita;
	}

	//INIZIO residuo-evoluzione-pai
	public CsDPai getCsDPai() {
		return csDPai;
	}

	public void setCsDPai(CsDPai csDPai) {
		this.csDPai = csDPai;
	}
	//FINE residuo-evoluzione-pai

	public Long getRaTipoRetta() {
		return raTipoRetta;
	}

	public void setRaTipoRetta(Long raTipoRetta) {
		this.raTipoRetta = raTipoRetta;
	}

	public ListaComunitaMan getRaComunita() {
		return raComunita;
	}

	public void setRaComunita(ListaComunitaMan raComunita) {
		this.raComunita = raComunita;
	}

	public Boolean getAffSpeseExtraAltro() {
		affSpeseExtraAltro = this.getAffSpeseExtra().contains("ALTRO");
		if(!affSpeseExtraAltro)
			this.affido.setAltroDesc(null);
		return affSpeseExtraAltro;
	}

	public void setAffSpeseExtraAltro(Boolean affSpeseExtraAltro) {
		this.affSpeseExtraAltro = affSpeseExtraAltro;
	}

	public ComuneResidenzaMan getAffComuneFamiglia() {
		return affComuneFamiglia;
	}

	public void setAffComuneFamiglia(ComuneResidenzaMan affComuneFamiglia) {
		this.affComuneFamiglia = affComuneFamiglia;
	}
	
	public Boolean getAffDiurnoGiornaliero() {
		affDiurnoGiornaliero = this.getAffTipo().contains("DIURNO_GIORNALIERO");
		if(!affDiurnoGiornaliero)
			this.affido.setAffidoNumGiorniSett(null);
		return affDiurnoGiornaliero;
	}

	public void setAffDiurnoGiornaliero(Boolean affDiurnoGiornaliero) {
		this.affDiurnoGiornaliero = affDiurnoGiornaliero;
	}

	public String getSlEducatoreLabel() {
		return slEducatoreLabel;
	}

	public void setSlEducatoreLabel(String slEducatoreLabel) {
		this.slEducatoreLabel = slEducatoreLabel;
	}

	public String getSlTipoCirc4Label() {
		return slTipoCirc4Label;
	}

	public void setSlTipoCirc4Label(String slTipoCirc4Label) {
		this.slTipoCirc4Label = slTipoCirc4Label;
	}

	public String getSlInterventoUOLLabel() {
		return slInterventoUOLLabel;
	}

	public void setSlInterventoUOLLabel(String slInterventoUOLLabel) {
		this.slInterventoUOLLabel = slInterventoUOLLabel;
	}

	public String getSlTipoProgettoLabel() {
		return slTipoProgettoLabel;
	}

	public void setSlTipoProgettoLabel(String slTipoProgettoLabel) {
		this.slTipoProgettoLabel = slTipoProgettoLabel;
	}

	public Long getCeTipoContributo() {
		return ceTipoContributo;
	}

	public void setCeTipoContributo(Long ceTipoContributo) {
		this.ceTipoContributo = ceTipoContributo;
	}

	public Long getIdCatSociale() {
		return idCatSociale;
	}

	public void setIdCatSociale(Long idCatSociale) {
		this.idCatSociale = idCatSociale;
	}

	
//	public void setTipoInterventoCatSoc(String tipoInterventoCatSoc) {
//		if(tipoInterventoCatSoc!=null && tipoInterventoCatSoc.contains("@")){
//			String[] s = tipoInterventoCatSoc.split("@");
//			this.idCatSociale=new Long(s[0]);
//			this.idTipoIntervento = new Long(s[1]);
//		}
//	}

	public Boolean getInterventoAperto() {
		return interventoAperto;
	}

	public void setInterventoAperto(Boolean interventoAperto) {
		this.interventoAperto = interventoAperto;
	}

	
	public Boolean getInterventoRespinto() {
		return interventoRespinto;
	}

	public void setInterventoRespinto(Boolean interventoRespinto) {
		this.interventoRespinto = interventoRespinto;
	}

	public Long getIdTipoIntrCustom() {
		return idTipoIntrCustom;
	}

	public void setIdTipoIntrCustom(Long idTipoIntrCustom) {
		this.idTipoIntrCustom = idTipoIntrCustom;
	}

	public void inizializzaNuovoTipoIntervento(Long idTipoIntervento, Long idTipoIntrCustom, Long idCatSociale) {
		this.idTipoIntervento = idTipoIntervento;
		this.idTipoIntrCustom = idTipoIntrCustom;
		this.idCatSociale = idCatSociale;
		
		if( idTipoIntervento != null ){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idTipoIntervento);
			CsCTipoIntervento ti = interventoService.findTipiInterventoById(dto);
			descTipoIntervento = ti.getDescrizione();
		}
		
		if( idTipoIntrCustom != null ){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(idTipoIntrCustom);
			CsCTipoInterventoCustom cic = interventoService.findTipiInterventoCustomById(dto);
			descTipoInterventoCustom = cic.getDescrizione();
		}
	
	}

	public ComponenteAltroMan getComponente() {
		return componente;
	}

	public void setComponente(ComponenteAltroMan componente) {
		this.componente = componente;
	}
	
	public String getLabelConcatAll()
	{
		String ret = descTipoIntervento;
		if(getRelSettCsocTipoInter()!=null && getRelSettCsocTipoInter().getCsRelSettoreCatsoc()!=null && getRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsCCategoriaSociale()!=null)
		{
			ret+= ", " + getRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsCCategoriaSociale().getDescrizione();
		}
		if(getRelSettCsocTipoInter()!=null && getRelSettCsocTipoInter().getCsRelSettoreCatsoc()!=null && getRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsOSettore()!=null)
		{
			ret+= ", " + getRelSettCsocTipoInter().getCsRelSettoreCatsoc().getCsOSettore().getNome();
		}
	
		return ret;
	}
	
	@Override	
	public boolean equals(Object obj) {
		if(idIntervento!=null)
		{
			return idIntervento.equals(((DatiInterventoBean)obj).idIntervento);
		}
		return false;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
	
}
