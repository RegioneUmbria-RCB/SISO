package it.webred.ct.data.access.basic.imu;

import it.webred.ct.data.access.basic.CTServiceBaseBean;
import it.webred.ct.data.access.basic.anagrafe.dto.DatiAnagrafeDTO;
import it.webred.ct.data.access.basic.f24.F24Service;
import it.webred.ct.data.access.basic.f24.F24ServiceBean;
import it.webred.ct.data.access.basic.f24.dto.RicercaF24DTO;
import it.webred.ct.data.access.basic.imu.dao.SaldoImuDAO;
import it.webred.ct.data.access.basic.imu.dto.JsonDatiCatastoDTO;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuBaseDTO;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuConsulenzaDTO;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuElaborazioneDTO;
import it.webred.ct.data.access.basic.imu.dto.XmlF24DTO;
import it.webred.ct.data.access.basic.imu.dto.XmlFiglioDTO;
import it.webred.ct.data.access.basic.imu.dto.XmlImmobileDTO;
import it.webred.ct.data.access.basic.imu.dto.ValImmobileDTO;
import it.webred.ct.data.model.imu.SaldoImuDatiElab;
import it.webred.ct.data.model.imu.SaldoImuDatiElabPK;
import it.webred.ct.data.model.imu.SaldoImuStorico;
import it.webred.ct.data.model.imu.SaldoImuStoricoPK;

import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@Stateless 
public class SaldoImuServiceBean extends CTServiceBaseBean implements SaldoImuService {
	
	@Autowired
	private SaldoImuDAO imuDAO;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/F24ServiceBean")
	private F24Service f24Service;
	
	
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void storicizza(SaldoImuBaseDTO search) {
		
		SaldoImuStorico jpaOld = this.getUltimoStorico(search);
		long progressivo = 1;
		if(jpaOld!=null)
			progressivo = jpaOld.getId().getProgressivo() + 1;
			
		//Genero l'oggetto e salvo nel db
		SaldoImuStoricoPK id = new SaldoImuStoricoPK();
		id.setCodfisc(search.getCodfisc());
		id.setProgressivo(progressivo);
		
		SaldoImuStorico jpaNew = new SaldoImuStorico();
		jpaNew.setId(id);
		
		jpaNew.setXml(search.getDati());
		
		DatiAnagrafeDTO dto = this.getDichiaranteConsulenzaXml(jpaNew.getXml());
		if(dto!=null){
			jpaNew.setCognome(dto.getCog());
			jpaNew.setNome(dto.getNom());
			jpaNew.setSesso(dto.getSesso());
			jpaNew.setComuNasc(dto.getComNas());
			jpaNew.setProvNasc(dto.getProvNas());
			jpaNew.setIndRes(dto.getIndRes());
			jpaNew.setComuRes(dto.getComRes());
			jpaNew.setProvRes(dto.getProvRes());
			jpaNew.setIban(dto.getIban());
			jpaNew.setBelfiore(dto.getBelfiore());
		}
		
		jpaNew.setDtIns(Calendar.getInstance().getTime());
		
		imuDAO.salvaStorico(jpaNew);
	}
	
	@Override
	public void salvaElaborazione(SaldoImuBaseDTO dati){
	
		SaldoImuDatiElab jpaOld = this.getUltimaElaborazione(dati);
		long progressivo = 1;
		if(jpaOld!=null)
			progressivo = jpaOld.getId().getProgressivo() + 1;
			
		//Genero l'oggetto e salvo nel db
		SaldoImuDatiElabPK id = new SaldoImuDatiElabPK();
		id.setCodfisc(dati.getCodfisc());
		id.setProgressivo(progressivo);
		
		SaldoImuDatiElab jpaNew = new SaldoImuDatiElab();
		jpaNew.setId(id);
		jpaNew.setJson(dati.getDati());
		jpaNew.setDtIns(new Date());
		
		imuDAO.salvaElaborazione(jpaNew);
	}
	
	@Override
	public String getJsonUltimaElaborazione(SaldoImuBaseDTO search){
		
		String json = null;
		
		SaldoImuDatiElab jpa = this.getUltimaElaborazione(search);
		json = jpa!=null ? jpa.getJson() : null;
		
		return json;
	}
	
	private String getJsonUnescaped(String s)  {
		
		String json=null;
		try {
			json = URLDecoder.decode(s, "UTF-8");
			logger.debug("UNESCAPED:["+json+"]");
		} catch (UnsupportedEncodingException e) {
			logger.error("Errore unescape string",e);
		}
		return json;		
	}
	
	@Override
	public SaldoImuElaborazioneDTO getJsonDTOUltimaElaborazione(SaldoImuBaseDTO search){
		
		SaldoImuElaborazioneDTO e = new SaldoImuElaborazioneDTO();
		
		String escaped = this.getJsonUltimaElaborazione(search);
		
		String unescaped = this.getJsonUnescaped(escaped);
		
		JSONObject obj;
	
			try {
				obj = new JSONObject(unescaped);
			
				//JSONObject myF24 = obj.getJSONObject("myF24pagato");
				
				e.setNumFigli(this.getIntJson(obj,"numeroFigli"));
				e.setUrlSportello(this.getStringJson(obj,"urlSportello"));
				e.setSidSportello(this.getStringJson(obj,"sidSportello"));
				e.setCodbelSportello(this.getStringJson(obj,"codbelSportello"));
				e.setCodfisSportello(this.getStringJson(obj,"codfisSportello"));
				
			    logger.debug( "numeroFigli: " + e.getNumFigli() );
			    logger.debug( "urlSportello: " + e.getUrlSportello() );
			    logger.debug( "sidSportello: " + e.getSidSportello() );
			    logger.debug( "codbelSportello: " + e.getCodbelSportello());
			    logger.debug( "codfisSportello: " + e.getCodfisSportello());
			    
			    //Imposto il DICHIARANTE
		    	e.setDichiarante(getJsonDichiarante(obj));
			    
			    //Caricamento dati TAB ANAGRAFE
			    e.setTabAnagrafe(this.getJsonTabAnagrafe(obj));
			    
			    try{
			    	
			    
				    JSONArray catasto = obj.getJSONArray("tabCatasto");
				    //Caricamento dati TAB CATASTO
				    for(int i=0; i<catasto.length();i++){
				    	
				    	JSONObject c;
						try {
							c = catasto.getJSONObject(i);
						
					    	JsonDatiCatastoDTO cat = new JsonDatiCatastoDTO();
			
					    	cat.setArea(this.getStringJson(c,"area"));
					    	cat.setCat(this.getStringJson(c,"categoria"));
					    	cat.setClasse(this.getStringJson(c,"classe"));
					    	cat.setCodFisc(this.getStringJson(c,"codFisc"));
					    	cat.setConsistenza(this.getStringJson(c,"consistenza"));
					    	cat.setDataInizio(this.getStringJson(c,"dat_val_ini"));
					    	cat.setDataFine(this.getStringJson(c,"dat_val_fin"));
					    	//Dt_val
					    	cat.setDenom(decode(this.getStringJson(c,"denom")));
					    	cat.setDtNas(this.getStringJson(c,"dtNas"));
					    	cat.setFoglio(this.getStringJson(c,"fog"));
					    	cat.setNum(this.getStringJson(c,"num"));
					    	cat.setPartita(this.getStringJson(c,"partita"));
					    	cat.setPercPoss(this.getStringJson(c,"percentuale"));
					    	cat.setProvCat(this.getStringJson(c,"provCat"));
					    	cat.setQualita(decode(this.getStringJson(c,"qualita")));
					    	cat.setRedditoAgrario(this.getStringJson(c,"redditoAgrario"));
					    	cat.setRedditoDominicale(this.getStringJson(c,"redditoDominicale"));
					    	cat.setRegime(this.getStringJson(c,"regime"));
					    	cat.setRendita(this.getStringJson(c,"rendita"));
					    	cat.setSede(decode(this.getStringJson(c,"sede")));
					    	cat.setSub(this.getStringJson(c,"sub"));
					    	cat.setTipoImm(this.getStringJson(c,"tipo_imm"));
					    	cat.setTit(this.getStringJson(c,"cod_dir"));
					    	cat.setUbi(decode(this.getStringJson(c,"ubi")));
					    	cat.setDataInizioTit(this.getStringJson(c,"dataInizio"));
					    	cat.setDataFineTit(this.getStringJson(c,"dataFine"));
					    	cat.setSez(this.getStringJson(c,"sez"));
					    	cat.setIdrec(this.getStringJson(c,"idrec"));
					    	cat.setTipCat(this.getStringJson(c,"tipcat"));
					    	cat.setTipCatOld(this.getStringJson(c,"tipcatold"));
					    	cat.setTxtTipologia(decode(this.getStringJson(c,"testoTipologia")));
					    	cat.setTipImm(this.getStringJson(c,"tipimm"));
					    	cat.setMesiPoss(this.getIntJson(c,"mesipossesso"));
					    	cat.setBelfiore(this.getStringJson(c,"codiceEnte"));
					    	cat.setTasso(this.getStringJson(c,"tasso"));
					    	cat.setAliquota(this.getStringJson(c,"aliquota"));
					    	cat.setDetrazione(this.getStringJson(c,"detrazione"));
					    	cat.setOrdImmobile(this.getIntJson(c,"ordineImmobile"));
					    	cat.setRenditaC2(this.getStringJson(c,"renditaC2"));
					    	cat.setQuotaC2(this.getStringJson(c,"quotaC2"));
					    	cat.setMesiPossC2(this.getIntJson(c,"mesipossessoC2"));
					    	cat.setRenditaC6(this.getStringJson(c,"renditaC6"));
					    	cat.setQuotaC6(this.getStringJson(c,"quotaC6"));
					    	cat.setMesiPossC6(this.getIntJson(c,"mesipossessoC6"));
					    	cat.setRenditaC7(this.getStringJson(c,"renditaC7"));
					    	cat.setQuotaC7(this.getStringJson(c,"quotaC7"));
					    	cat.setMesiPossC7(this.getIntJson(c,"mesipossessoC7"));
			
					    	logger.debug("Catasto: " + cat.stampaRecord());
					    	
					    	e.addCatasto(cat);
				    	
						} catch (JSONException e1) {
							logger.error("Oggetto Catasto (num."+i+") non presente" + e1.getMessage(),e1);
						}
				    }
			    }catch (JSONException ec) {
					logger.error("JSON tabCatasto non valido"+ec.getMessage());
				}
			    
			  
			
			} catch (JSONException e2) {
				logger.error("JSON non valido"+e2.getMessage());
			}
	    	
		return e;
	}
	
	private List<DatiAnagrafeDTO> getJsonTabAnagrafe(JSONObject obj) {
		
		List<DatiAnagrafeDTO> lst = new ArrayList<DatiAnagrafeDTO>();

		try {
			JSONArray anagrafe = obj.getJSONArray("tabAnagrafe");
			
			  //Caricamento dati TAB ANAGRAFE
		    for(int i=0; i<anagrafe.length();i++){
		    	JSONObject a = null;
		    	try{
		    	
		    		 a = anagrafe.getJSONObject(i);
		    	
		    	} catch (JSONException e) {
		    		logger.error("Oggetto tabAnagrafe (num."+i+") non valido" + e.getMessage(),e);
				}
		    	
		    	if(a!=null){
			    	DatiAnagrafeDTO anag = new DatiAnagrafeDTO();
			    	anag.setCodFisc(this.getStringJson(a,"COD_FIS"));
			    	anag.setCog(decode(this.getStringJson(a,"COG")));
					anag.setNom(decode(this.getStringJson(a,"NOM")));
					
			    	anag.setComNas(decode(this.getStringJson(a,"COM_NAS")));
			    	
			    	try {
						anag.setDatNas(SDF.parse(this.getStringJson(a,"DAT_NAS")));
					} catch (ParseException e1) {
						logger.error("Errore parse data", e1);
					}
			    	
			    	anag.setProvNas(decode(this.getStringJson(a,"PROV_NAS")));
			    	anag.setCodRel(this.getStringJson(a,"COD_REL"));
			    	
			    	logger.debug("Anagrafe: " +anag.stampaRecord());
			    	
			    	lst.add(anag);
		    	}
		    }
		
		} catch (JSONException e) {
			logger.error("JSON tabAnagrafe non valido"+e.getMessage());
		}
		
		return lst;
	}
	
	private DatiAnagrafeDTO getJsonDichiarante(JSONObject obj){
		
		DatiAnagrafeDTO dich = null;
		JSONObject dichiarante = null;
		try {
			
			dichiarante = obj.getJSONObject("Dichiarante");
		
		 //Valorizzazione DICHIARANTE
	    if(dichiarante!=null){
			dich = new DatiAnagrafeDTO();
			dich.setCodFisc(this.getStringJson(dichiarante,"CodiceFiscale"));
	    	dich.setCog(decode(this.getStringJson(dichiarante,"Cognome")));
	    	dich.setNom(decode(this.getStringJson(dichiarante,"Nome")));
	    	dich.setComNas(this.getStringJson(dichiarante,"ComuneNascita"));
	    	try {
				dich.setDatNas(SDF.parse(this.getStringJson(dichiarante,"DataNascita")));
			} catch (ParseException e1) {
				logger.error("Errore parse data", e1);
			}
	    	
	    	dich.setProvNas(decode(this.getStringJson(dichiarante,"ProvinciaNascita")));
	    	dich.setSesso(this.getStringJson(dichiarante,"Sesso"));
	    	dich.setComRes(this.getStringJson(dichiarante,"ComuneResidenza"));
	    	dich.setProvRes(this.getStringJson(dichiarante,"ProvinciaResidenza"));
	    	dich.setIndRes(this.getStringJson(dichiarante,"IndirizzoResidenza"));
	    	dich.setIban(this.getStringJson(dichiarante,"IBAN"));
	    	dich.setBelfiore(this.getStringJson(dichiarante,"CodiceBelfiore"));
	    	logger.debug("Dichiarante: " +dich.stampaRecord());
	    	}
		} catch (JSONException e) {
			logger.error("JSON Dichiarante non valido"+e.getMessage());
		}
    	
		return dich;
	}
	
	
	private String getStringJson(JSONObject obj, String label){
		
		String s = null;
		try{
			
			s = obj.getString(label);
		
		}catch(JSONException jsone){
			logger.error(jsone.getMessage());
		}
		return s;
	}
	
	private Integer getIntJson(JSONObject obj, String label){
		
		Integer in = null;
		try{
			
			in = obj.getInt(label);
		
		}catch(JSONException jsone){
			logger.error(jsone.getMessage());
		}
		return in;
	}
		
	private SaldoImuDatiElab getUltimaElaborazione(SaldoImuBaseDTO search){
		
		SaldoImuDatiElab jpa = null;
		
		List<SaldoImuDatiElab> lista = imuDAO.getDatiElaborazione(search.getCodfisc());
		
		if(lista.size()>0)
			jpa = lista.get(0);
			
		return jpa;
	}
	
	private SaldoImuStorico getUltimoStorico(SaldoImuBaseDTO search){
		
		SaldoImuStorico jpa = null;
		
		List<SaldoImuStorico> lista = imuDAO.getStorico(search.getCodfisc());
		
		if(lista.size()>0)
			jpa = lista.get(0);
			
		return jpa;
	}
	
	
	public SaldoImuConsulenzaDTO getConsulenza(SaldoImuBaseDTO search){
		SaldoImuConsulenzaDTO dto=null;
		
		SaldoImuStorico jpa = this.getUltimoStorico(search);
		if(jpa!=null){
			String xml = jpa.getXml();
			dto = new SaldoImuConsulenzaDTO();
			dto.setId(jpa.getId().getCodfisc()+"|"+jpa.getId().getProgressivo());
			dto.setDtConsulenza(jpa.getDtIns());
			dto.setDichiarante(this.getDichiaranteConsulenzaXml(xml));
			dto.setLstImmobili(this.getImmobiliConsulenzaXml(xml));
			dto.setLstF24(this.getF24ConsulenzaXml(xml));
			
			for(XmlF24DTO f24 : dto.getLstF24()){

   			 if(f24.getCodTributo()!=null){
   				 
   				 RicercaF24DTO s = new RicercaF24DTO();
   				 s.setEnteId(search.getEnteId());
   				 s.setSessionId(search.getSessionId());
   				 s.setCodTributo(f24.getCodTributo());
   				 
   				 String desc = f24Service.getDescTipoTributoByCod(s);
   				 f24.setDescTributo(desc!=null ? desc : "");
   			 }
			
			}
		}
		return dto;
	}

	
	private DatiAnagrafeDTO getDichiaranteConsulenzaXml(String xml){
		DatiAnagrafeDTO dto = null;
		
		try {

			 Element root = getRootByXmlString(xml);
		      NodeList lstAnagrafica = root.getElementsByTagName("Anagrafica");
		      if(lstAnagrafica.getLength()>0){
		    	  Node anagrafica = lstAnagrafica.item(0);
		    	  dto = new DatiAnagrafeDTO();
		    	  NodeList aChild = anagrafica.getChildNodes();
		    	 for(int i = 0; i< aChild.getLength(); i++) {
		    		 Node ac = aChild.item(i);
		    		 String nome = ac.getNodeName();
		    		 String val = ac.hasChildNodes() ? ac.getFirstChild().getNodeValue() : null;
	
		    		 if("CodiceFiscale".equals(nome))
		    			 dto.setCodFisc(val);
		    		 else if("Cognome".equals(nome))
		    			 dto.setCog(val);
		    		 else if("Nome".equals(nome))
		    			 dto.setNom(val);
		    		 else if("Sesso".equals(nome))
		    			 dto.setSesso(val);
		    		 else if("comuneNascita".equals(nome))
		    			 dto.setComNas(val);
		    		 else if("provinciaNascita".equals(nome))
		    			 dto.setProvNas(val);
		    		 else if("comuneResidenza".equals(nome))
		    			 dto.setComRes(val);
		    		 else if("provinciaResidenza".equals(nome))
		    			 dto.setProvRes(val);
		    		 else if("indirizzoResidenza".equals(nome))
		    			 dto.setIndRes(val);
		    		 else if("IBAN".equals(nome))
		    			 dto.setIban(val);
		    		 else if("codiceBelfiore".equals(nome))
		    			 dto.setBelfiore(val);
		    	 }
		    	
		     }

		}catch(Exception pce) {
			logger.error("getDichiaranteConsulenzaXml - Errore parsing XML", pce);
		}
		
		return dto;
	}
	
	
	private List<XmlImmobileDTO> getImmobiliConsulenzaXml(String xml){
		List<XmlImmobileDTO> lst = new ArrayList<XmlImmobileDTO>();
		
		try {
			 
		      Element root = getRootByXmlString(xml);
		      NodeList lstListaImmobili = root.getElementsByTagName("ListaImmobili");
		      if(lstListaImmobili.getLength()>0){
		    	  Node listaImmobili = lstListaImmobili.item(0);
		    	  NodeList lstElement = listaImmobili.getChildNodes();
			      for(int j = 0; j< lstElement.getLength(); j++) {
			    	  XmlImmobileDTO dto = new XmlImmobileDTO();
			    	  
			    	 ValImmobileDTO valAB = new ValImmobileDTO();
			    	 ValImmobileDTO valC2 = new ValImmobileDTO();
			    	 ValImmobileDTO valC6 = new ValImmobileDTO();
			    	 ValImmobileDTO valC7 = new ValImmobileDTO();
			    	  
			    	 Node elem = lstElement.item(j);
			    	 NodeList immChild = elem.getChildNodes();
			    	 for(int i = 0; i< immChild.getLength(); i++) {
			    		 
			    		 Node ac = immChild.item(i);
			    		 String nome = ac.getNodeName();
			    		 
			    		 if("listaFigli".equals(nome))
			    			 dto.setLstFigli(this.getFigliConsulenzaXml(ac));
			    		 else{
			    			 String val = ac.hasChildNodes() ? ac.getFirstChild().getNodeValue() : null;
				    		 if("codiceEnte".equals(nome))
				    			 dto.setCodiceEnte(val);
				    		 else if("categoriac".equals(nome))
				    			 dto.setCatImmBase(val);
				    		 else if("categoria".equals(nome))
				    			 dto.setCatEstesa(val);
				    		 else if("categoriaTesto".equals(nome))
				    			 dto.setCatDescrizione(val);
				    		 else if("renditaImmobileRiv".equals(nome))
				    			 dto.setRenditaImmobileRiv(getDouble(val));
				    		 else if("aliquota".equals(nome))
				    			 dto.setAliquota(getDouble(val));
				    		 else if("contitolari".equals(nome))
				    			 dto.setNumContitolari(val);
				    		 else if("figli".equals(nome))
				    			 dto.setNumFigli(val);
				    		 else if("DAB7".equals(nome))
				    			 dto.setDetrTerrAgr(getDouble(val));
				    		 else if("dovutoAB7".equals(nome))
				    			 dto.setDovutoTerrAgr(getDouble(val));
				    		 else if("dovutoStato".equals(nome))
				    			 dto.setDovutoStato(getDouble(val));
				    		 else if("dovutoComune".equals(nome))
				    			 dto.setDovutoComune(getDouble(val));
				    		 else if("dovutoStato_x_acconto".equals(nome))
				    			 dto.setDovutoAccStato(getDouble(val));
				    		 else if("dovutoComune_x_acconto".equals(nome))
				    			 dto.setDovutoAccComune(getDouble(val));
				    		 else if("detrazione_x_acconto".equals(nome))
				    			 dto.setDetrazioneAcc(getDouble(val));
				    		 else if("detrazioneComune_x_acconto7".equals(nome))
				    			 dto.setDetrazioneAccComune(getDouble(val));
				    		 else if("detrazioneStato_x_acconto7".equals(nome))
				    			 dto.setDetrazioneAccStato(getDouble(val));
				    		 else if("detrazioneComune7".equals(nome))
				    			 dto.setDetrTerrAgrComune(getDouble(val));
				    		 else if("detrazioneStato7".equals(nome))
				    			 dto.setDetrTerrAgrStato(getDouble(val));
				    		 else if("moltiplicatore".equals(nome))
				    			 dto.setMoltiplicatore(val);
				    		 else if("tasso".equals(nome))
				    			 dto.setTasso(getDouble(val));
				    		 else if("totaleimmobili".equals(nome))
				    			 dto.setImmTotali(val);
				    		 else if("immobiliAssegnati".equals(nome))
				    			 dto.setImmAssegnati(val);
				    		 else if("terremotato".equals(nome))
				    			 dto.setTerremotato("S".equalsIgnoreCase(val)? true : false);
				    		 else if("variazione".equals(nome))
				    			 dto.setVariazione("S".equalsIgnoreCase(val)? true : false);
				    		 else if("id".equals(nome))
				    			 dto.setId(val);
				    		 else if("Newid".equals(nome))
				    			 dto.setNewId(val);
				  
				    		 
				    		 //Valorizzo i dati dell'immobile principale
				    		 else if("renditaImmobile".equals(nome))
				    			 valAB.setRendita(getDouble(val));
				    		 else if("renditaRivalutataAB".equals(nome))
				    			 valAB.setRenditaRivalutata(getDouble(val));
				    		 else if("valoreAB".equals(nome))
				    			 valAB.setValore(getDouble(val));
				    		 else if("possesso".equals(nome))
				    			 valAB.setQuotaPoss(getDouble(val));
				    		 else if("mesiPossesso".equals(nome))
				    			 valAB.setMesiPoss(val);
				    		 else if("dovutoAB".equals(nome))
				    			 valAB.setDovuto(getDouble(val));
				    		 else if("DAB".equals(nome))
				    			 valAB.setDovutoMenoDetrazione(getDouble(val));
				    		 else if("DCAB".equals(nome))
				    			 valAB.setDetrazione(getDouble(val));
				    		 else if("storico".equals(nome))
				    			 valAB.setStorico(val);
			    		
				    		//Valorizzo i dati dell'immobile C2
				    		 else if("renditaC2".equals(nome))
				    			 valC2.setRendita(getDouble(val));
				    		 else if("renditaRivalutataC2".equals(nome))
				    			 valC2.setRenditaRivalutata(getDouble(val));
				    		 else if("valoreC2".equals(nome))
				    			 valC2.setValore(getDouble(val));
				    		 else if("quotaC2".equals(nome))
				    			 valC2.setQuotaPoss(getDouble(val));
				    		 else if("mesiPossessoC2".equals(nome))
				    			 valC2.setMesiPoss(val);
				    		 else if("dovutoC2".equals(nome))
				    			 valC2.setDovuto(getDouble(val));
				    		 else if("DC2".equals(nome))
				    			 valC2.setDovutoMenoDetrazione(getDouble(val));
				    		 else if("DCC2".equals(nome))
				    			 valC2.setDetrazione(getDouble(val));
				    		 else if("storicoC2".equals(nome))
				    			 valC2.setStorico(val);
				    		 
				    		//Valorizzo i dati dell'immobile C6
				    		 else if("renditaC6".equals(nome))
				    			 valC6.setRendita(getDouble(val));
				    		 else if("renditaRivalutataC6".equals(nome))
				    			 valC6.setRenditaRivalutata(getDouble(val));
				    		 else if("valoreC6".equals(nome))
				    			 valC6.setValore(getDouble(val));
				    		 else if("quotaC6".equals(nome))
				    			 valC6.setQuotaPoss(getDouble(val));
				    		 else if("mesiPossessoC6".equals(nome))
				    			 valC6.setMesiPoss(val);
				    		 else if("dovutoC6".equals(nome))
				    			 valC6.setDovuto(getDouble(val));
				    		 else if("DC6".equals(nome))
				    			 valC6.setDovutoMenoDetrazione(getDouble(val));
				    		 else if("DCC6".equals(nome))
				    			 valC6.setDetrazione(getDouble(val));
				    		 else if("storicoC6".equals(nome))
				    			 valC6.setStorico(val);
				    		 
				    		//Valorizzo i dati dell'immobile C7
				    		 else if("renditaC7".equals(nome))
				    			 valC7.setRendita(getDouble(val));
				    		 else if("renditaRivalutataC7".equals(nome))
				    			 valC7.setRenditaRivalutata(getDouble(val));
				    		 else if("valoreC7".equals(nome))
				    			 valC7.setValore(getDouble(val));
				    		 else if("quotaC7".equals(nome))
				    			 valC7.setQuotaPoss(getDouble(val));
				    		 else if("mesiPossessoC7".equals(nome))
				    			 valC7.setMesiPoss(val);
				    		 else if("dovutoC7".equals(nome))
				    			 valC7.setDovuto(getDouble(val));
				    		 else if("DC7".equals(nome))
				    			 valC7.setDovutoMenoDetrazione(getDouble(val));
				    		 else if("DCC7".equals(nome))
				    			 valC7.setDetrazione(getDouble(val));
				    		 else if("storicoC7".equals(nome))
				    			 valC7.setStorico(val);
			    		 }
			    		 dto.setValAB(valAB);
			    		 dto.setValC2(valC2);
			    		 dto.setValC6(valC6);
			    		 dto.setValC7(valC7);
			    	 }
			    	lst.add(dto); 
			      }
		      }
		    	
		}catch(Exception pce) {
			logger.error("getImmobiliConsulenzaXml - Errore parsing XML", pce);
		}
		
		return lst;
	}
	
	private Double getDouble(String v){
		Double d = null;
		if(v!=null && v.trim().length()>0){
			v = v.trim(); 
			logger.debug(v);
			//v = v.replaceAll("\\.", ",");
			logger.debug(v);
			BigDecimal bd = new BigDecimal(v);
			d = bd.doubleValue();
		}
		return d;
	}
	
	private List<XmlFiglioDTO> getFigliConsulenzaXml(Node lstFigli){
		List<XmlFiglioDTO> lst = new ArrayList<XmlFiglioDTO>();
		
		NodeList elements = lstFigli.getChildNodes();
		for(int i=0; i<elements.getLength() ; i++){
			XmlFiglioDTO dto = new XmlFiglioDTO();
			Node element = elements.item(i);
			NodeList elChild = element.getChildNodes();
			for(int j=0; j<elChild.getLength(); j++){
				Node p = elChild.item(j);
				 String nome = p.getNodeName();
				 String val = p.hasChildNodes() ? p.getFirstChild().getNodeValue() : null;
	    		
	    		if("Si".equals(nome))
	    			 dto.setPresente(val!=null && val.equalsIgnoreCase("X")? true : false);
	    		 else if("Perc".equals(nome))
	    			 dto.setPercDetrazione(val);
	    		 else if("Mesi".equals(nome))
	    			 dto.setMesiDetrazione(val);
			}
			
			lst.add(dto);
		}
		
		return lst;
	}
	
	
	private List<XmlF24DTO> getF24ConsulenzaXml(String xml){
		List<XmlF24DTO> lst = new ArrayList<XmlF24DTO>();
		
		try {
			 
		      Element root = getRootByXmlString(xml);
		      NodeList lstListaF24 = root.getElementsByTagName("ListaF24");
		      if(lstListaF24.getLength()>0){
			      Node listaF24 = lstListaF24.item(0);
			      NodeList lstElement = listaF24.getChildNodes();
			      for(int j = 0; j< lstElement.getLength(); j++) {
			    	  XmlF24DTO dto = new XmlF24DTO();
			    	 Node elem = lstElement.item(j);
			    	 NodeList f24Child = elem.getChildNodes();
			    	 for(int i = 0; i< f24Child.getLength(); i++) {
			    		 Node ac = f24Child.item(i);
			    		 String nome = ac.getNodeName();
			    		 String val = ac.hasChildNodes() ? ac.getFirstChild().getNodeValue() : null;
			    		 
			    		 if("codEnte".equals(nome))
			    			 dto.setCodEnte(val);
			    		 else if("flgRav".equals(nome))
			    			 dto.setFlgRav("S".equalsIgnoreCase(val)? true : false);
			    		 else if("flgImmVar".equals(nome))
			    			 dto.setFlgImmVar("S".equalsIgnoreCase(val)? true : false);
			    		 else if("flgAcconto".equals(nome))
			    			 dto.setFlgAcconto("S".equalsIgnoreCase(val)? true : false);
			    		 else if("flgSaldo".equals(nome))
			    			 dto.setFlgSaldo("S".equalsIgnoreCase(val)? true : false);
			    		 else if("numImm".equals(nome))
			    			 dto.setNumImm(val);
			    		 else if("flgRateazione".equals(nome))
			    			 dto.setFlgRateazione(val);
			    		 else if("annoRif".equals(nome))
			    			 dto.setAnnoRif(val);
			    		 else if("dovutoScadenza".equals(nome))
			    			 dto.setDovutoScadenza(val!=null ? new BigDecimal(val) : null);
			    		 else if("pagatoScadenza".equals(nome))
			    			 dto.setPagatoScadenza(val!=null ? new BigDecimal(val) : null);
			    		 else if("impDaRavvedere".equals(nome))
			    			 dto.setImpDaRavvedere(val!=null ? new BigDecimal(val) : null);
			    		 else if("Sanzione".equals(nome))
			    			 dto.setSanzione(val!=null ? new BigDecimal(val) : null);
			    		 else if("Interessi".equals(nome))
			    			 dto.setInteressi(val!=null ? new BigDecimal(val) : null);
			    		 else if("Tasso".equals(nome))
			    			 dto.setTasso(val!=null ? new BigDecimal(val) : null);
			    		 else if("Detrazione".equals(nome))
			    			 dto.setDetrazione(val!=null ? new BigDecimal(val) : null);
			    		 else if("impDebito".equals(nome))
			    			 dto.setImpDebito(val!=null ? new BigDecimal(val) : null);
			    		 else if ("codTri".equals(nome))
			    			 dto.setCodTributo(val);
			    			
			    			 
			    	 }
			    	lst.add(dto); 
			      }
		      }
		}catch(Exception pce) {
			logger.error("getF24ConsulenzaXml - Errore parsing XML", pce);
		}
		
		return lst;
	}
	
	private String decode(String txt){
		try {
			txt = URLDecoder.decode(URLDecoder.decode(txt, "UTF-8"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {}
		
		return txt;
		
	}
	
	private Element getRootByXmlString(String xml) throws ParserConfigurationException, SAXException, IOException{
		
		 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      InputSource is = new InputSource(new StringReader(xml));
	      Document dom =  builder.parse(is);
	      Element root = dom.getDocumentElement();
	      return root;
	}
	
	/*private DefaultTreeModel getTreeByXmlString(String xml) {
		DefaultTreeModel tree = null;
		
		SAXParserFactory factory = SAXParserFactory.newInstance();
		XMLTreeHandler handler = new XMLTreeHandler();
		try {
			InputSource is = new InputSource(new StringReader(xml));
			SAXParser saxParser = factory.newSAXParser();
			saxParser.parse(is, handler);
			tree = new DefaultTreeModel(handler.getRoot());
		} catch (Exception e) {
			logger.error("File Read Error: " + e);
		}
		
		return tree; 
	}*/

}
