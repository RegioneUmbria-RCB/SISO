package it.webred.cs.json.stranieri;

import it.webred.classfactory.WebredClassFactory;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsTbPermesso;
import it.webred.cs.data.model.CsTbStatus;
import it.webred.cs.jsf.manbean.ComuneProvenienzaMan;
import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.json.SchedaValutazioneManBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.naming.NamingException;

public abstract class StranieriManBaseBean extends SchedaValutazioneManBean implements IStranieri{
	
	private static final long serialVersionUID = 1L;
	private List<SelectItem> listaStatus;
	private List<SelectItem> listaNazioni;
	private List<SelectItem> listaPermessi;
	private List<String> listaAnni;
	protected ComuneProvenienzaMan comuneManITA;
	protected ComuneProvenienzaMan comuneManREG;
	protected ComuneProvenienzaMan comuneManRilascio;
	protected boolean disableTipoPermesso = true;
	protected boolean disableDatiPermesso = true;
	protected boolean disablePnlStatoPermesso = false;
	protected boolean validaConoscenzaLingua = true;
	protected boolean validaDatiImmigrazione=true;
	
	public static class ListaStatus
	{
		public static final String CITTADINO_COMUNITARIO_RESIDENTE = "6";
		public static final String CITTADINO_COMUNITARIO_NON_RESIDENTE = "9";
	}
	
	public static class ListaStatoPermesso{
		public static final String IN_ATTESA_PERMESSO = "in attesa di permesso";
		public static final String IN_ATTESA_RINNOVO = "in attesa di rinnovo";
		public static final String IN_POSSESSO_PERMESSO = "in possesso di permesso";
	}
	
	public static IStranieri initByVersion(String defaultVersion)
	{
		IStranieri interfaccia = null;
		try {
			interfaccia = (IStranieri) WebredClassFactory.newInstance("", IStranieri.class, defaultVersion!=null ? defaultVersion : "");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return interfaccia;
	}

	public static IStranieri initByModel(CsDValutazione val) throws Exception{
		IStranieri interfaccia = null;
		if (val != null) {
			String jsonString = val.getCsDDiario().getCsDClob().getDatiClob();
			String className = val.getVersioneScheda();

			// la versione di default è utile se si vuole comunque
			// instanziare una versione intermedia tra la prima e la max
			String defaultVersion = "";
			interfaccia = (IStranieri) WebredClassFactory.newInstance(className, IStranieri.class, defaultVersion);
			
			// Initialize scheda barthel
			interfaccia.init(null, val);
		}
		return interfaccia;
	}
	
	public static IStranieri init(IStranieri man){
		IStranieri interfaccia = initByVersion(null);
		interfaccia.init(man);
		return interfaccia;
	}
	
	public StranieriManBaseBean(){
		comuneManITA = new ComuneProvenienzaMan();
		comuneManREG = new ComuneProvenienzaMan();
		comuneManRilascio = new ComuneProvenienzaMan();
	}
	
	
    @Override
	public List<String> getListaAnni(){
		if(listaAnni==null || listaAnni.isEmpty()){
			listaAnni = new ArrayList<String>();
			Calendar now = Calendar.getInstance();
			Integer year = now.get(Calendar.YEAR);
			for(int i=0; i<100; i++)
				listaAnni.add((year--).toString());
		}
		return listaAnni;
	}
	
    @Override
	public List<SelectItem> getListaPermessi() {
		if(listaPermessi==null || listaPermessi.isEmpty()){
			listaPermessi = new ArrayList<SelectItem>();
			try{
			   CeTBaseObject per = new CeTBaseObject();
			   fillEnte(per);
			   List<CsTbPermesso> lstp = confService.getPermesso(per);
			   
			   HashMap<Integer,List<SelectItem>> mappa = new HashMap<Integer,List<SelectItem>>();
			   for(CsTbPermesso p : lstp){
				   List<SelectItem> slist = mappa.get(p.getGruppo());
				   if(slist==null)
					   slist = new ArrayList<SelectItem>();
				   SelectItem st = new SelectItem(p.getId(),p.getDescrizione());
				   st.setDisabled("0".equals(p.getAbilitato()));
				   
				  slist.add(st);
				  mappa.put(p.getGruppo(),slist);
			   }
			   Iterator iter = mappa.entrySet().iterator();
			   while(iter.hasNext()){
				   SelectItemGroup gr = new SelectItemGroup();
				   Entry o = (Entry)iter.next();
				   List<SelectItem> s = (List<SelectItem>)o.getValue();
				   gr.setSelectItems(s.toArray(new SelectItem[s.size()]));
				   listaPermessi.add(gr);
			   }
				   
			  }catch(Exception e){
				  logger.error("getPermesso", e);
			  }
			
			
		}
		return listaPermessi;
	}
    
    protected String getPermesso(String id){
    	String s = null;
    	BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(id);
    	CsTbPermesso permesso = confService.getPermessoById(dto);
    	return permesso!=null ? permesso.getDescrizione() : null;
    }

    @Override
	public List<SelectItem> getListaNazioni(){
		if(listaNazioni==null || listaNazioni.isEmpty()){
			listaNazioni = new ArrayList<SelectItem>();
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				List<AmTabNazioni> beanLstNazione= bean.getNazioni();
				if (beanLstNazione != null) {
					for (AmTabNazioni p : beanLstNazione) {
						listaNazioni.add(new SelectItem(p.getCodIstatNazione(), p.getNazione()));
					}
				}
			} catch (NamingException e) {
				  logger.error("getListaNazioni", e);
			}
		}
		
		return listaNazioni;
	}
    
    protected String getNazione(String id){
    	String s = null;
		try {
			AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
			AmTabNazioni nazione= bean.getNazioneByIstat(id);
			s = nazione!=null ? nazione.getNazione() : null;
		} catch (NamingException e) {
			  logger.error("getNazione", e);
		}
    	return s;
    }
	
    @Override
	public List<SelectItem> getListaStatus() {
		if(listaStatus==null || listaStatus.isEmpty()){
			listaStatus = new ArrayList<SelectItem>();
			try{
			CeTBaseObject stat = new CeTBaseObject();
			   fillEnte(stat);
			   List<CsTbStatus> lstS = confService.getStatus(stat);
			   for(CsTbStatus p : lstS){
				   SelectItem st = new SelectItem(p.getId(),p.getDescrizione());
				   st.setDisabled("0".equals(p.getAbilitato()));
				   listaStatus.add(st);
			   }
			   
			  }catch(Exception e){
				  logger.error("getStatus", e);
			  }
			
			
		}
		return listaStatus;
	}

    protected String getStatus(String id){
    	String s = null;
    	BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(id);
    	CsTbStatus status = confService.getStatusById(dto);
    	s = status!=null ? status.getDescrizione() : null;
    	return s;
    }
    
	public ComuneMan getComuneManITA() {
		return comuneManITA;
	}

	public ComuneMan getComuneManREG() {
		return comuneManREG;
	}

	public void setComuneManITA(ComuneProvenienzaMan comuneManITA) {
		this.comuneManITA = comuneManITA;
	}


	public void setComuneManREG(ComuneProvenienzaMan comuneManREG) {
		this.comuneManREG = comuneManREG;
	}

	public ComuneProvenienzaMan getComuneManRilascio() {
		return comuneManRilascio;
	}

	public void setComuneManRilascio(ComuneProvenienzaMan comuneManRilascio) {
		this.comuneManRilascio = comuneManRilascio;
	}

	public boolean isDisablePnlStatoPermesso() {
		return disablePnlStatoPermesso;
	}

	public void setDisablePnlStatoPermesso(boolean disablePnlStatoPermesso) {
		this.disablePnlStatoPermesso = disablePnlStatoPermesso;
	}

	public boolean isDisableTipoPermesso() {
		return disableTipoPermesso;
	}

	public boolean isDisableDatiPermesso() {
		return disableDatiPermesso;
	}

	public void setDisableTipoPermesso(boolean disableTipoPermesso) {
		this.disableTipoPermesso = disableTipoPermesso;
	}

	public void setDisableDatiPermesso(boolean disableDatiPermesso) {
		this.disableDatiPermesso = disableDatiPermesso;
	}
	
	public void setValidaConoscenzaLingua(boolean valida) {
		this.validaConoscenzaLingua = valida;
	}

	@Override
	public void setValidaCampiImmigrazione(boolean valida) {
		this.validaDatiImmigrazione=valida;
	}



}
