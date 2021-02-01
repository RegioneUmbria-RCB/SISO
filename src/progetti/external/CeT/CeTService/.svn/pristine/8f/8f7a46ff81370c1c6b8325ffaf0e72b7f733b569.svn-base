package it.webred.ct.data.access.aggregator.elaborazioni;

import it.webred.ct.data.access.aggregator.elaborazioni.dto.ControlloClassamentoConsistenzaDTO;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ElaborazioniDataIn;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ElaborazioniDataOut;
import it.webred.ct.data.access.aggregator.elaborazioni.dto.ParamCalcoloValImponibileDTO;
import it.webred.ct.data.access.basic.common.utils.Info;
import it.webred.ct.data.access.basic.common.utils.StringUtils;
import it.webred.ct.data.access.basic.docfa.dao.DocfaDAO;
import it.webred.ct.data.access.basic.docfa.dto.FoglioMicrozonaDTO;
import it.webred.ct.data.access.basic.docfa.dto.RicercaOggettoDocfaDTO;
import it.webred.ct.data.access.basic.docfa.dto.ZonaOmiDTO;
import it.webred.ct.data.model.docfa.DocfaDatiCensuari;
import it.webred.ct.data.model.docfa.DocfaDatiGenerali;
import it.webred.ct.data.model.docfa.DocfaValori;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class ElaborazioniServiceBean implements ElaborazioniService {
	
	protected static Logger logger = Logger.getLogger("ctservice.log");
	
	private static final long serialVersionUID = 1L;
	private SimpleDateFormat yyyyMMdd = new SimpleDateFormat("yyyyMMdd");
	
	public static final HashMap<String, Integer> CLASSI_MIN_STR = new HashMap<String, Integer>();
	static {
		CLASSI_MIN_STR.put(null, new Integer("9999"));
		CLASSI_MIN_STR.put("ERROR", new Integer("8999"));
		CLASSI_MIN_STR.put("U", new Integer("7999"));
	}
	
	@Autowired
	private DocfaDAO docfaDAO;

	@Override
	public ElaborazioniDataOut getClassamentoCompatibile(ElaborazioniDataIn dataIn) {
		
		dataIn.setObj2(docfaDAO.getDocfaRapporto());
		
		ElaborazioniDataOut dataOut = new ElaborazioniDataOut();
		
		ControlloClassamentoConsistenzaDTO dto = (ControlloClassamentoConsistenzaDTO)dataIn.getObj1();
		
		Object rapportoParam = dto.getRapportoParam();  
		//String valoreCommerciale = StringUtils.formatDouble(dto.getValoreCommerciale());
		String consistenza = dto.getConsistenza();
		String categoria = dto.getCategoria();
		String classe = dto.getClasse();
		String zona = dto.getZona();
		String rapporto =  StringUtils.formatDouble((Double)dataIn.getObj2());
		if (rapportoParam != null) {
			if (rapportoParam instanceof String) {
				rapporto = (String) rapportoParam;
			} else if (rapportoParam instanceof Long) {
				rapporto = String.valueOf((Long) rapportoParam);
			} else if (rapportoParam instanceof Double) {
				rapporto = StringUtils.formatDouble((Double) rapportoParam);
			}
			logger.info("Rapporto Impostato: " + rapporto);
		}
		
		double consistenzaD = StringUtils.parsStringToDoubleZero(consistenza);
		double rapportoD    = StringUtils.parsStringToDoubleZero(rapporto);
		dto.setRapporto(rapportoD);
		
		logger.info("Rapporto: " + rapportoD);
		
		Double hopeAvg = dto.getValoreCommerciale()/ (105 * rapportoD);
		Double hopeAvgPerVano = dto.getValoreCommerciale()/ (105 * consistenzaD * rapportoD);
		if (hopeAvg != null && !hopeAvg.isNaN() && !hopeAvg.isInfinite())
			dto.setMediaAttesa(hopeAvg);
		if (hopeAvgPerVano != null && !hopeAvgPerVano.isNaN() && !hopeAvgPerVano.isInfinite())
			dto.setMediaAttesaPerVano(hopeAvgPerVano);
		

		dataOut.setObj1(dto);
		dataOut.setObj2(docfaDAO.getListaClassiCompatibili(dto.getValoreCommerciale(), consistenzaD, rapportoD, zona, categoria, classe));
	
		return dataOut;
		
	}//-------------------------------------------------------------------------
	
	@Override
	public List<ControlloClassamentoConsistenzaDTO> getDocfaDatiCensuariValori(RicercaOggettoDocfaDTO ro){
		
		List<ControlloClassamentoConsistenzaDTO> lst = new ArrayList<ControlloClassamentoConsistenzaDTO>();
		
		List<DocfaDatiCensuari> lstDatiCensuari = docfaDAO.getDocfaDatiCensuari(ro);
		if (lstDatiCensuari != null && lstDatiCensuari.size() > 0){
			ControlloClassamentoConsistenzaDTO ccc = null;
			for (DocfaDatiCensuari ddc : lstDatiCensuari){
				RicercaOggettoDocfaDTO rod = new RicercaOggettoDocfaDTO();
				rod.setFornitura(ro.getFornitura());
				rod.setProtocollo(ro.getProtocollo());
				rod.setTipoOperDocfa(ro.getTipoOperDocfa());
				
				rod.setFoglio(ddc.getFoglio());
				rod.setParticella(ddc.getNumero());
				rod.setUnimm(ddc.getSubalterno());
				
				ccc = this.getControlliClassConsistenzaByDocfaUiu(rod);
				lst.add(ccc);
			}
		}
		return lst;
	}//-------------------------------------------------------------------------
	
	@Override
	public List<ControlloClassamentoConsistenzaDTO> getControlliClassamentoConsistenzaDocfaUiu(RicercaOggettoDocfaDTO ro){
		
		List<ControlloClassamentoConsistenzaDTO> lst = new ArrayList<ControlloClassamentoConsistenzaDTO>();
		
		List<DocfaDatiCensuari> lstDatiCensuari = docfaDAO.getListaDatiCensuariDocfa(ro.getFornitura(), ro.getProtocollo());
		if (lstDatiCensuari != null && lstDatiCensuari.size() > 0){
			ControlloClassamentoConsistenzaDTO ccc = null;
			for (DocfaDatiCensuari ddc : lstDatiCensuari){
				RicercaOggettoDocfaDTO rod = new RicercaOggettoDocfaDTO();
				rod.setFornitura(ro.getFornitura());
				rod.setProtocollo(ro.getProtocollo());
				rod.setTipoOperDocfa(ro.getTipoOperDocfa());
				
				rod.setFoglio(ddc.getFoglio());
				rod.setParticella(ddc.getNumero());
				rod.setUnimm(ddc.getSubalterno());
				
				ccc = this.getControlliClassConsistenzaByDocfaUiu(rod);
				lst.add(ccc);
			}
		}
		return lst;
	}//-------------------------------------------------------------------------

	@Override
	public ControlloClassamentoConsistenzaDTO getControlliClassConsistenzaByDocfaUiu(RicercaOggettoDocfaDTO ro){
		ControlloClassamentoConsistenzaDTO ccc = null;
		DocfaDatiCensuari ddc = docfaDAO.getDatiCensuariByUiuDocfa(ro);
		
		if(ddc!=null){	
			ccc = new ControlloClassamentoConsistenzaDTO();
			ccc.setZona(ddc.getZona());
			ccc.setFoglio(ddc.getFoglio());
			ccc.setParticella(ddc.getNumero());
			ccc.setSubalterno(ddc.getSubalterno());
			ccc.setZona(ddc.getZona());
			ccc.setCategoria(ddc.getCategoria());
			ccc.setClasse(ddc.getClasse());
			ccc.setConsistenza(ddc.getConsistenza());
			ccc.setProtocolloDocfa(ddc.getProtocolloRegistrazione());
			ccc.setFornitura(ddc.getFornitura());
			ccc.setIdentificativoImmobile(ddc.getId().getIdentificativoImmobile());
			/*
			 * dal 16 lug 2014 il campo seguente è usato per passare il parametro 
			 * che decide il metodo di calcolo del Valore commerciale (= MICROZONE
			 * o KML_SHAPE) dall'applicativo BOD e DIOGENE verso il CT_Service
			 */
			ccc.setMsgZoneOmi(ro.getTipoOperDocfa());
			
			try{
				if(ddc.getDataRegistrazione()!=null)
					ccc.setDataRegistrazione(yyyyMMdd.parse(ddc.getDataRegistrazione()));
			}catch(ParseException pe){}
			
			String superf = ddc.getSuperficie();
			if(superf!=null)
				ccc.setSuperficie(StringUtils.parsStringToDoubleZero(superf));
			
			String rend = StringUtils.checkNullString( ddc.getRenditaEuro() );
			Double rendita = StringUtils.parsStringToDoubleZero(rend); 
			ccc.setRendita(rendita);
			
			ccc = this.getControlliClassamentoConsistenza(ccc);
			if (ccc != null && (ccc.getMsgZoneOmi().equalsIgnoreCase("KML_SHAPE") || ccc.getMsgZoneOmi().equalsIgnoreCase("MICROZONE") ) ){
				ccc.setMsgZoneOmi("");
			}
		}
		
		return ccc;
	}//-------------------------------------------------------------------------

	
	//Controlli sulla unità immobiliare del DOCFA specifico (passato a parametro)
	public ControlloClassamentoConsistenzaDTO getControlliClassamentoConsistenza(ControlloClassamentoConsistenzaDTO ccc){
		
		if (ccc.getCategoria() != null && !ccc.getCategoria().trim().equalsIgnoreCase("")){
			/*
			 * Logica per anomalie di classe: preve il recupero della classe
			 * minima e nel caso di C06 devo diminuire di 3 unità
			 */
			if (ccc.getCategoria().trim().toUpperCase().startsWith("C")|| ccc.getCategoria().trim().toUpperCase().equalsIgnoreCase("A10")) {
				/*
				 * Mostro la Classe Maggiormente Frequente
				 */
				ccc.setMostraClasseMaggiormenteFrequente(true);
	
				List<String> lstClassiMinime = docfaDAO.getListaClassiMin(ccc.getZona(), ccc.getFoglio(), ccc.getCategoria());
				if (lstClassiMinime != null && lstClassiMinime.size()>0){
					String minClasse = (String)lstClassiMinime.get(0);
					int classeMin = -1;
					try {
						classeMin = StringUtils.parsStringToInt(minClasse);
					} catch (Exception e) {
						try {
							classeMin = CLASSI_MIN_STR.get(minClasse) == null ? -1 : CLASSI_MIN_STR.get(minClasse).intValue();
						} catch (Exception e1) {
							classeMin = CLASSI_MIN_STR.get("ERR").intValue();
						}
					}
					if (classeMin == -1) {
						classeMin = CLASSI_MIN_STR.get("ERR").intValue();
					}
					if (ccc.getCategoria().trim().equalsIgnoreCase("C06")){
						classeMin -= 3;								
					}
					ccc.setClasseMin(classeMin);
					//(ccc.classe >= ccc.classeRif)?'black':'red'
					int intCls = StringUtils.parsStringToInt(ccc.getClasse());
					ccc.setClasseRif( intCls >= ccc.getClasseMin() ? "black" : "red" );
				}else{
					logger.warn("Lista Classi Min non trovata");
				}
			}else{
				//if ( (new Double(ccc.getClasse())).isNaN() )
				ccc.setClasseRif("black");
			}
				/*
				 * Calcolo la tolleranza (= 95% <= x <= 105%) per determinare 
				 * le anomalie di consistenza delle categorie diverse da C01, 
				 * C02, C03 alle quali invece è riservato un trattamento diverso
				 */
				if (ccc.getCategoria().trim().equalsIgnoreCase("C01")
						|| ccc.getCategoria().trim().equalsIgnoreCase("C02")
						|| ccc.getCategoria().trim().equalsIgnoreCase("C03")  ) {
					/*
					 * L'anomalia di consistenza si verifica quando il rapporto 
					 * tra consistenza e superficie catastale è inferiore a 0,85
					 */
					ccc.setSuperfMediaMin(0d);
					Double consistSuSuperfCatastale = StringUtils.parsStringToDoubleZero(ccc.getConsistenza()) / ccc.getSuperficie();
					ccc.setConsisAnomalia(consistSuSuperfCatastale);
					ccc.setSuperfMediaMax(0.85d);
					if (ccc.getSuperfMediaMin() <= ccc.getConsisAnomalia() && ccc.getConsisAnomalia()>= ccc.getSuperfMediaMax())
						ccc.setColore("black");
					else
						ccc.setColore("red");
	
				} else if (Info.htSuperficiMedie.containsKey(ccc.getCategoria())){
					if (!ccc.getCategoria().trim().equalsIgnoreCase("A10")){
						ccc.setClassamentoCompatibile(true);							
					}
	//						Double consistenzaMin = Number.parsStringToDouble(Info.htSuperficiMedie.get(ccc.getCategoria())) * 95 / 100;
	//						ccc.setSuperfMediaMin(consistenzaMin);
	//						Double consistenzaMax = Number.parsStringToDouble(Info.htSuperficiMedie.get(ccc.getCategoria())) * 105 / 100;
	//						ccc.setSuperfMediaMax(consistenzaMax);
	//						Double superfSuConsis = superficie	/ Number.parsStringToDoubleZero(ccc.getConsistenza());
	//						ccc.setConsisAnomalia(superfSuConsis);
					Double consistenzaMin = ccc.getSuperficie() / StringUtils.parsStringToDouble(Info.htSuperficiMedie.get(ccc.getCategoria())) * 0.95;
					ccc.setSuperfMediaMin(consistenzaMin);
					Double consistenzaMax = ccc.getSuperficie() / StringUtils.parsStringToDouble(Info.htSuperficiMedie.get(ccc.getCategoria())) * 1.05;
					ccc.setSuperfMediaMax(consistenzaMax);
					ccc.setConsisAnomalia(StringUtils.parsStringToDoubleZero(ccc.getConsistenza()));
					if (ccc.getConsisAnomalia() >= ccc.getSuperfMediaMin() && ccc.getConsisAnomalia() <= ccc.getSuperfMediaMax())
						ccc.setColore("black");
					else
						ccc.setColore("red");
				}
			}
	
			//ccc.setRendita100(Number.parsStringToDoubleZero(Number.formatDouble(rendita * 100)));
			Double rendita = ccc.getRendita();
			Double r100 = rendita * 100;
			ccc.setRenditaX100(r100);
			Double r105 = rendita * 105;
			ccc.setRenditaX105(r105);
			
		
			/*
			 * Recupero il rapporto
			 */
			Double rapporto= docfaDAO.getDocfaRapporto();
			
			ccc.setRapporto(rapporto);

			/*
			 * Determinazione del valore comm.le
			 */
			if(ccc.getFornitura()!=null && ccc.getProtocolloDocfa()!=null){
				/*
				 * DAL 16 LUG 2014 il valore commerciale delle uiu puo essere calcolato
				 * in due modi in base alla configurazione scelta dall'utente:
				 * 1. facendo riferimento alle microzone
				 * 2.facendo riferimento allo shape delle Zone OMI del comune
				 * nella tabella SHP_ZONE_OMI 
				 */
				if (ccc.getMsgZoneOmi().equalsIgnoreCase("KML_SHAPE")){
					this.loadValoreCommercialeKmlShape(ccc);
					logger.debug("Metodo Calcolo Valore Commerciale: KML_SHAPE");
				}else if (ccc.getMsgZoneOmi().equalsIgnoreCase("MICROZONE")){
					this.loadValoreCommerciale(ccc);
					logger.debug("Metodo Calcolo Valore Commerciale: MICROZONE");
				}

				
				if(!ccc.isErroreZoneOmi()){
					
					Double valCom = ccc.getValoreCommerciale();
					ccc.setValComSuRen100(valCom/r100);
					ccc.setValComSuRen105(valCom/r105);
					this.loadMediaAttesa(ccc);
					
				}else if(ccc.isErroreZoneOmi()){
					
					for(ControlloClassamentoConsistenzaDTO cccA : ccc.getLstPerZcDiverse()){
						
						Double valCom = cccA.getValoreCommerciale();
						cccA.setValComSuRen100(valCom/r100);
						cccA.setValComSuRen105(valCom/r105);
						this.loadMediaAttesa(cccA);
						
					}

				}
					
			}else
				logger.warn("getControlliClassamentoConsistenza - Paramteri del Docfa (fornitura/protocollo) non validi");
		
			return ccc;
	}
	
	private void loadMediaAttesa(ControlloClassamentoConsistenzaDTO ccc){
		
		Double hopeAvg = ccc.getValoreCommerciale()/(105 * StringUtils.parsStringToDoubleZero(ccc.getConsistenza()) * ccc.getRapporto());
		if (hopeAvg != null && !hopeAvg.isNaN() && !hopeAvg.isInfinite())
			ccc.setMediaAttesa(hopeAvg);
		
	}
	
	
	private void loadValoreCommerciale(ControlloClassamentoConsistenzaDTO dto){
		
		/*
		 * Recupero del flag di nuova costituzione
		 */
		List<DocfaDatiGenerali> lstDatiGenerali = docfaDAO.getDocfaDatiGenerali(dto.getFornitura(), dto.getProtocolloDocfa());
		
		/*
		 * In realtà è un solo record e un solo campo
		 */
		if (lstDatiGenerali != null && lstDatiGenerali.size()> 0){
			/*
			 * Se non ho il risultato non posso calcolare il valore commerciale
			 */
			DocfaDatiGenerali ddg = lstDatiGenerali.get(0);
			String flagNC = ((BigDecimal)ddg.getFlagNc()).toString();
			/*
			 * Recupero della microzona
			 */
			
			String tipolEdilizia = dto.getCategoria()!=null ? Info.htCategorieCatastali.get(dto.getCategoria()) : null;
			String stato = flagNC!=null ? Info.htFlagNuovaCostituzione.get(flagNC.trim()) : null;
			
			List<FoglioMicrozonaDTO> lstMicrozone = docfaDAO.getFoglioMicrozona( dto.getFoglio(),  dto.getZona() );
			if (lstMicrozone != null && lstMicrozone.size() > 0){
				FoglioMicrozonaDTO mzona = lstMicrozone.get(0);
				Double valCom = this.getValCommerciale(mzona, dto.getCategoria(), flagNC, dto.getSuperficie());
				
				dto.setValoreCommerciale(valCom);
				
			}else{
					dto.setErroreZoneOmi(true);
					String msg = "Zona OMI mancante per la coppia Foglio["+dto.getFoglio()+"], Zona["+(dto.getZona()!=null ? dto.getZona() : "")+"]";
					dto.setMsgZoneOmi(msg);
				
				logger.warn("getValoreCommerciale - Zona OMI mancante per la coppia Foglio["+dto.getFoglio()+"], Zona["+dto.getZona()+"]");
				
				//Carico i calcoli alternativi associati alle altre zone censuarie 
				logger.info("getValoreCommerciale - Ricerca Microzone per Foglio["+dto.getFoglio()+"]");
				lstMicrozone = docfaDAO.getFoglioMicrozona(dto.getFoglio());
				List<ControlloClassamentoConsistenzaDTO> lstAlterZc = new ArrayList<ControlloClassamentoConsistenzaDTO>();
				for(FoglioMicrozonaDTO mzona : lstMicrozone){
	
					ControlloClassamentoConsistenzaDTO cccAlter = new ControlloClassamentoConsistenzaDTO(dto);
					
					Double valCom = this.getValCommerciale(mzona, dto.getCategoria(), flagNC, dto.getSuperficie());
					cccAlter.setZona(mzona.getZc());
					cccAlter.setValoreCommerciale(valCom);
					lstAlterZc.add(cccAlter);
				}
				
				if(lstAlterZc.size()==0 ){
					logger.warn("getValoreCommerciale - Microzona mancante per Foglio["+dto.getFoglio()+"]");
					dto.setMsgZoneOmi("Zona OMI mancante per il foglio ["+dto.getFoglio()+"]");
				}
				dto.setLstPerZcDiverse(lstAlterZc);
			}
		}else
			logger.warn("getValoreCommerciale - Flag di nuova costituzione mancante");
		
	}//-------------------------------------------------------------------------
	
	private void loadValoreCommercialeKmlShape(ControlloClassamentoConsistenzaDTO dto){
		/*
		 * Recupero del flag di nuova costituzione
		 */
		List<DocfaDatiGenerali> lstDatiGenerali = docfaDAO.getDocfaDatiGenerali(dto.getFornitura(), dto.getProtocolloDocfa());
		/*
		 * In realtà è un solo record e un solo campo
		 */
		Double valCom = 0d;

		if (lstDatiGenerali != null && lstDatiGenerali.size()> 0){
			/*
			 * Se non ho il risultato non posso calcolare il valore commerciale
			 */
			DocfaDatiGenerali ddg = lstDatiGenerali.get(0);
			String flagNC = ((BigDecimal)ddg.getFlagNc()).toString();
			/*
			 * Recupero della microzona
			 */
			
			String tipolEdilizia = dto.getCategoria()!=null ? Info.htCategorieCatastali.get(dto.getCategoria()) : null;
			String stato = flagNC!=null ? Info.htFlagNuovaCostituzione.get(flagNC.trim()) : null;
			/*
			 * Determiniamo la Zona OMI di appartenenza della particella relativa 
			 * a questo DOCFA
			 */
			
			List<ZonaOmiDTO> lstZoneOmi = docfaDAO.getZoneOmiByFP( dto.getFoglio(),  dto.getParticella() );			
			if (lstZoneOmi != null && lstZoneOmi.size()>0){
				ZonaOmiDTO omi = null;
				if (lstZoneOmi.size() == 1){
					omi = lstZoneOmi.get(0);
				}else{
					/*
					 * Nel caso siano piu di una, tra la lista delle zone OMI elencate dovrei prendere quella
					 * con area di intersezione maggiore, per il momento prendo 
					 * la prima
					 */
					ZonaOmiDTO omiCur = null;
					Iterator<ZonaOmiDTO> itZomi = lstZoneOmi.iterator();
					BigDecimal bdMaxAreaMq = new BigDecimal(0); 
					while(itZomi.hasNext()){
						omiCur = itZomi.next();
						BigDecimal bdCurAreaMq = docfaDAO.getAreaIntersectZonaOmiParticella(omiCur.getName(), omiCur.getFoglio(), omiCur.getParticella());
						//cerco la zona omi con il max sup area di intersezione
						if (bdCurAreaMq != null && bdCurAreaMq.compareTo(bdMaxAreaMq) > 0){
							bdMaxAreaMq = bdCurAreaMq;
							omi = omiCur;
						}
					}
				}
				if (omi != null)
					valCom = this.getValCommerciale(omi, dto.getCategoria(), flagNC, dto.getSuperficie());
				
				else{
					dto.setErroreZoneOmi(true);
					String msg = "Zona OMI mancante per la coppia Foglio["+dto.getFoglio()+"], Particella["+dto.getParticella()+"]";
					dto.setMsgZoneOmi(msg);
					logger.warn("getValoreCommerciale - Zona OMI mancante per la coppia Foglio["+dto.getFoglio()+"], Particella["+dto.getParticella()+"]");
					
				}

			}else
				logger.warn("loadValoreCommercialeKmlShape - Zona OMI mancante");
			
		}else
			logger.warn("loadValoreCommercialeKmlShape - Flag di nuova costituzione mancante");
		
		dto.setValoreCommerciale(valCom);
		
	}//-------------------------------------------------------------------------
	
	private Double getValCommerciale(ZonaOmiDTO zomi, String categoria, String flagNC, Double superficie){
		Double valCom = new Double(0);
		
		String tipolEdilizia = categoria!=null ? Info.htCategorieCatastali.get(categoria) : null;
		String stato = flagNC!=null ? Info.htFlagNuovaCostituzione.get(flagNC.trim()) : null;
		/*
		 * Nel file KML che poi viene caricato come SHAPE la colonna NAME (sia in Monza che in CHIARI)
		 * è valorizzata nel seguente modo:
		 * CHIARI - Zona OMI R1 
		 * MONZA - Zona OMI B12
		 * quindi è necessario estrarre il contenuto dall'ultimo spazio fino alla fine 
		 * della stringa per ottenere soltanto il codice della Zona OMI
		 */
		String zonaOmi = StringUtils.checkNullString(zomi.getName());
		String comi = zonaOmi.substring(zonaOmi.lastIndexOf(" ") , zonaOmi.length()); 
			
		List<DocfaValori> lstDcfValori = docfaDAO.getDocfaValoriByZonaOmi(StringUtils.checkNullString(comi), tipolEdilizia, stato);
		if (lstDcfValori != null && lstDcfValori.size() > 0){
			
			BigDecimal valMed = lstDcfValori.get(0).getValMed();
			
			logger.debug("DocfaValori - VAL_MED:"+valMed);
			
			/*
			 * superficie * val_med = val_comm.le 
			 */	
			//valCom = Number.parsStringToDoubleZero(Number.formatDouble(valMed.doubleValue() * superficie));
			valCom = valMed.doubleValue() * superficie;
			
			logger.debug("DocfaValori - ValCommerciale: "+valCom);
		}else
			logger.warn("getValoreCommerciale - Lista DocfaValori mancante");
		
		return valCom;
	}//-------------------------------------------------------------------------
	
	
	private Double getValCommerciale(FoglioMicrozonaDTO mzona, String categoria, String flagNC, Double superficie){
		Double valCom = new Double(0);
		
		String tipolEdilizia = categoria!=null ? Info.htCategorieCatastali.get(categoria) : null;
		String stato = flagNC!=null ? Info.htFlagNuovaCostituzione.get(flagNC.trim()) : null;
			
		List<DocfaValori> lstDcfValori = docfaDAO.getDocfaValori(mzona.getNewMicrozona(), tipolEdilizia, stato);
		if (lstDcfValori != null && lstDcfValori.size() > 0){
			
			BigDecimal valMed = lstDcfValori.get(0).getValMed();
			
			logger.debug("DocfaValori - VAL_MED:"+valMed);
			
			/*
			 * superficie * val_med = val_comm.le 
			 */	
			//valCom = Number.parsStringToDoubleZero(Number.formatDouble(valMed.doubleValue() * superficie));
			valCom = valMed.doubleValue() * superficie;
			
			logger.debug("DocfaValori - ValCommerciale:"+valCom);
			
		}else
			logger.warn("getValoreCommerciale - Lista DocfaValori mancante");
		
		return valCom;
	}//-------------------------------------------------------------------------
	
	@Override
	public Double calcolaValImponibile(ParamCalcoloValImponibileDTO param){
		Double rendita = param.getRendita();
		String categoria = param.getCategoria();
		Double result = null;

		logger.info( "Calcola val.imponibile: " +
							"rendita["+rendita+"];" +
							"categoria["+categoria+"];");

		String tipo = categoria!=null ? categoria.substring(0,1) : null;
		if(tipo!=null && rendita !=null){
			result = rendita*105/100; 
			if(("A".equals(tipo) && !categoria.equals("A10")) || ("C".equals(tipo) && !categoria.equals("C01")))
				result = result * 100;
			else if("B".equals(tipo))
				result = result * 140;
			else if ("D".equals(tipo) || categoria.equals("A10")){
				result = result * 50; 

				//TODO: Va fatta eccezione per fabbricati classificabili nella categoria D, interamente posseduti 
				//da imprese e distintamente contabilizzati, sforniti di rendita catastale (per i quali si utilizzano costi contabili)
			}else if (categoria.equals("C01"))
				result = result * 34;
		}
		
		return result;
	}

}
