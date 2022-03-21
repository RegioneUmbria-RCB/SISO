package it.webred.ct.aggregator.ejb.imu;
 

import it.webred.ct.aggregator.ejb.CTServiceAggregatorBaseBean;
import it.webred.ct.aggregator.ejb.imu.dto.ImuBaseDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuConduzCatastoDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuConduzCatastoIntervalloDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiAnagrafeDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiCatastaliDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiElaborazioneDTO;
import it.webred.ct.aggregator.ejb.imu.dto.ImuDatiF24DTO;
import it.webred.ct.data.access.basic.anagrafe.AnagrafeService;
import it.webred.ct.data.access.basic.anagrafe.dto.ComuneProvinciaDTO;
import it.webred.ct.data.access.basic.anagrafe.dto.RicercaSoggettoAnagrafeDTO;
import it.webred.ct.data.access.basic.catasto.CatastoService;
import it.webred.ct.data.access.basic.catasto.dto.ImmobiliAccatastatiOutDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaOggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.RicercaSoggettoCatDTO;
import it.webred.ct.data.access.basic.catasto.dto.SoggettoCatastoDTO;
import it.webred.ct.data.access.basic.catasto.dto.TerrenoPerSoggDTO;
import it.webred.ct.data.access.basic.common.CommonDataIn;
import it.webred.ct.data.access.basic.f24.F24Service;
import it.webred.ct.data.access.basic.f24.dto.F24VersamentoDTO;
import it.webred.ct.data.access.basic.f24.dto.RicercaF24DTO;
import it.webred.ct.data.access.basic.imu.SaldoImuService;
import it.webred.ct.data.access.basic.imu.dto.SaldoImuBaseDTO;
import it.webred.ct.data.model.anagrafe.SitDPersona;
import it.webred.ct.data.model.catasto.ConsSoggTab;
import it.webred.ct.data.model.catasto.Siticomu;
import it.webred.ct.support.audit.AuditStateless;
import it.webred.ct.support.validation.CeTToken;
import it.webred.ct.support.validation.ValidationStateless;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
 
@Stateless
@WebService
@SOAPBinding(style=Style.RPC)
public class IMUServiceBean extends CTServiceAggregatorBaseBean implements IMUService {
	
	private static final long serialVersionUID = 1L;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/CatastoServiceBean")
	private CatastoService catastoService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/AnagrafeServiceBean")
	private AnagrafeService anagService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/SaldoImuServiceBean")
	private SaldoImuService imuService;
	
	@EJB(mappedName = "java:global/CT_Service/CT_Service_Data_Access/F24ServiceBean")
	private F24Service f24Service;
	
	public IMUServiceBean() {}
	
	
	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuDatiF24DTO[] getDatiF24(@WebParam(name = "token") CeTToken token, 
			  						  @WebParam(name = "codfiscale") String codfiscale) {
		
		List<ImuDatiF24DTO> lista = new ArrayList<ImuDatiF24DTO>();
		
		try{
		
			if(codfiscale!=null && codfiscale.length()>11){
				RicercaF24DTO search = new RicercaF24DTO();
				search.setEnteId(token.getEnte());
				search.setSessionId(token.getSessionId());
				search.setCf(codfiscale);
				
				List<F24VersamentoDTO> res =  f24Service.getListaVersamentiByCF(search);
				for(F24VersamentoDTO vin : res){
					ImuDatiF24DTO v = new ImuDatiF24DTO();
					v.setAnnoRif(vin.getAnnoRif().toString());
					v.setCodBel(vin.getCodEnteCom());
					v.setCodTri(vin.getCodTributo());
					v.setDatPag(sdf.format(vin.getDtRiscossione()));
					v.setFlgRavv(vin.getRavvedimento()==new BigDecimal(1) ? "S" : "N");
					v.setImpPag(df2.format(vin.getImpDebito().doubleValue()));
					
					lista.add(v);
					
				}
			
			}else{
				ImuDatiF24DTO err = new ImuDatiF24DTO();
				err.setErrorCode("02");
				err.setErrorMessage("Codice Fiscale non valido.");
				lista.add(err);
			}
		
		}catch (Throwable t) {
					
					ImuDatiF24DTO err = new ImuDatiF24DTO();
					err.setErrorCode("01");
					err.setErrorMessage("Errore recupero Dati F24:" + t.getMessage());
					lista.add(err);
					
					logger.error("Eccezione: "+t.getMessage(), t);
		 }
		
		//Se non ottengo risultati, inserisco cmq un record 
		if(lista.size()==0){
			ImuDatiF24DTO err = new ImuDatiF24DTO();
			err.setErrorCode("03");
			err.setErrorMessage("Cod.Fiscale non presente.");
			lista.add(err);
		}
		
		ImuDatiF24DTO[] array = new ImuDatiF24DTO[lista.size()];
		for(int i=0; i<lista.size(); i++){
			array[i]=lista.get(i);
			logger.debug("getDatiF24 - " + array[i].stampaRecord());
		}
		
		return array;
	}
	
	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuDatiAnagrafeDTO[] getDatiAnagrafe(@WebParam(name = "token") CeTToken token, 
												@WebParam(name = "codfiscale") String codfiscale,
												@WebParam(name = "cognome") String cognome,
												@WebParam(name = "nome") String nome,
												@WebParam(name = "datanascita") String datanascita){
		
		List<ImuDatiAnagrafeDTO> lista = new ArrayList<ImuDatiAnagrafeDTO>();
		
		boolean residente = false;
		boolean ricercaCf = codfiscale!=null && codfiscale.trim().length()== 16;
		boolean ricercaCogNomDtNas =(cognome!=null && !cognome.trim().equals("") && !cognome.equalsIgnoreCase("null")) && 
									(nome!=null && !nome.trim().equals("") && !nome.equalsIgnoreCase("null")) && 
									(datanascita!=null && !datanascita.trim().equals("") && !datanascita.equalsIgnoreCase("null"));	
		try{
			
			
			logger.debug("codfiscale ["+codfiscale+"]");
			logger.debug("cognome ["+cognome+"]");
			logger.debug("nome ["+nome+"]");
			logger.debug("datanascita ["+datanascita+"]");
			
			if(ricercaCf || ricercaCogNomDtNas){
				
				RicercaSoggettoAnagrafeDTO rs = new RicercaSoggettoAnagrafeDTO();	
				rs.setEnteId(token.getEnte());
				rs.setSessionId(token.getSessionId());
				rs.setDtRif(new Date());
				
				if(ricercaCf){
					rs.setCodFis(codfiscale);
					residente = anagService.verificaResidenzaByCFAllaData(rs);
					
					if(residente){
						List<SitDPersona> familiari = anagService.getFamigliaByCF(rs);
						lista.addAll(this.copyAnagToDto(token, familiari, 1));
					}
					
				}
				
				if(ricercaCogNomDtNas){
					
					Date dtNascita = super.getDateFromString(datanascita);
					
					//Se non trovo risultati per codfiscale e sono disponibili anche i parametri per l'altra ricerca procedo
					if(lista.size()==0){
						rs.setCognome(cognome);
						rs.setNome(nome);
						rs.setDtNas(dtNascita);
						residente = anagService.verificaResidenzaByCogNomDtNascAllaData(rs);
						
						if(residente){
							List<SitDPersona> familiari = anagService.getFamigliaByCogNomDtNascita(rs);
							lista.addAll(this.copyAnagToDto(token, familiari, 0));
						}
					}
				}
				
				
			}else{
				ImuDatiAnagrafeDTO err = new ImuDatiAnagrafeDTO();
				err.setErrorCode("02");
				err.setErrorMessage("Parametri di ricerca anagrafe non validi.");
				lista.add(err);
			}
			
		}catch (Throwable t) {
			
			ImuDatiAnagrafeDTO err = new ImuDatiAnagrafeDTO();
			err.setErrorCode("01");
			err.setErrorMessage("Errore recupero Dati Anagrafe:" + t.getMessage());
			lista.add(err);
			
			logger.error("Eccezione: "+t.getMessage(), t);
		}
		
		//Se non ottengo risultati, inserisco cmq un record 
		if(lista.size()==0){
			ImuDatiAnagrafeDTO err = new ImuDatiAnagrafeDTO();
			err.setErrorCode("03");
			err.setErrorMessage("Soggetto non residente");
			lista.add(err);
		}
		
		ImuDatiAnagrafeDTO[] array = new ImuDatiAnagrafeDTO[lista.size()];
		for(int i=0; i<lista.size(); i++){
			array[i]=lista.get(i);
			logger.debug("getDatiAnagrafe - " + array[i].stampaRecord());
		}
		
		return array;
	}
	
	private List<ImuDatiAnagrafeDTO> copyAnagToDto(CeTToken token, List<SitDPersona> familiari, int ricercaCod ){
		
		List<ImuDatiAnagrafeDTO> lista = new ArrayList<ImuDatiAnagrafeDTO>();
		for(int i=0; i < familiari.size(); i++){
		
			SitDPersona f = familiari.get(i);
			ImuDatiAnagrafeDTO imu = new ImuDatiAnagrafeDTO();
			
			imu.setRicercaCod(ricercaCod);
				
			Date dtnasc = f.getDataNascita();
			
			imu.setCodFisc(getNullString(f.getCodfisc()));
			imu.setCog(getNullString(f.getCognome()));
			imu.setNom(getNullString(f.getNome()));
			imu.setDatNas(dtnasc!=null ? sdf.format(dtnasc) : null);
			
			CommonDataIn dataIn = new CommonDataIn();
			dataIn.setEnteId(token.getEnte());
			dataIn.setUserId(token.getSessionId());
			dataIn.setObj(f.getIdExtComuneNascita());
			
			ComuneProvinciaDTO cp = anagService.getDescrizioneComuneProvByIdExt(dataIn);
			if(cp!=null){
				imu.setComNas(getNullString(cp.getDescComune())); 
				imu.setProvNas(getNullString(cp.getSiglaProv())); 
			}
			
			lista.add(imu);
		}
		
		return lista;
	}
	
	private String getSiglaProvincia(CeTToken obj){
		
		String prov = null;
		
		//Estraggo le informazioni sulla provincia del Catasto
		RicercaOggettoCatDTO ro = new RicercaOggettoCatDTO();
		ro.setEnteId(obj.getEnte());
		ro.setSessionId(obj.getSessionId());
		ro.setCodEnte(obj.getEnte());
		
		List<Siticomu> comuni = catastoService.getSiticomuSezioni(ro);
		if(comuni.size()>0){
			Siticomu comune = comuni.get(0);
			prov = comune.getSiglaProv();
		}
		
		return prov;
		
	}
	
	private ConsSoggTab getSoggettoByPkCuaa(CeTToken obj, BigDecimal pkcuaa) {

		RicercaSoggettoCatDTO rs = new RicercaSoggettoCatDTO();
		rs.setEnteId(obj.getEnte());
		rs.setSessionId(obj.getSessionId());
		rs.setIdSogg(pkcuaa);

		return catastoService.getSoggettoByPkCuaa(rs);
		
	}
	
	private ImuDatiCatastaliDTO copySogG2Dto(ConsSoggTab s, ImuDatiCatastaliDTO dto){
		
		String sede = getNullString(s.getComuNasc());
		String denominazione = getNullString(s.getRagiSoci());
		
		dto.setCodFisc(getNullString(s.getCodiPiva()));
		dto.setDenom(denominazione);
		dto.setDtNas(s.getDataNasc()!=null ? sdf.format(s.getDataNasc()) : "");
		dto.setSede(sede);
		
		return dto;
	}
	
	private ImuDatiCatastaliDTO copySogF2Dto(ConsSoggTab s, ImuDatiCatastaliDTO dto){
		
		String sede = getNullString(s.getComuNasc());
		String cog_denom = getNullString(s.getRagiSoci());
		String r_nome = getNullString(s.getNome());
		String denominazione = (cog_denom+" "+r_nome).trim();
		
		dto.setCodFisc(getNullString(s.getCodiFisc()));
		dto.setDenom(denominazione);
		dto.setDtNas(s.getDataNasc()!=null ? sdf.format(s.getDataNasc()) : "");
		dto.setSede(sede);
		
		return dto;
	}
	
	private ImuDatiCatastaliDTO copyDto2DtoTerreni(TerrenoPerSoggDTO r, ImuDatiCatastaliDTO dto){
		
		dto.setTipImm("T");
		dto.setUbi("");
		dto.setFoglio(getNullString(r.getFoglio()));
		dto.setNum(getNullString(r.getParticella()));
		dto.setSub(getNullString(r.getSubalterno()));
		dto.setPartita(getNullString(r.getPartita()));
		dto.setPercPoss(getNullString(r.getPercPoss()).replaceAll(",", "."));
		dto.setTit(getNullString(r.getDesTipoTitolo()));
		dto.setRegime(getNullString(r.getRegime()));
		dto.setQualita(getNullString(r.getQualita())+"-"+getNullString(r.getDescQualita()));
		dto.setClasse(getNullString(r.getClasse()));
		dto.setArea(getNullString(r.getSuperficie()).replaceAll(",", "."));
		dto.setRendita(getNullString(r.getRendita()).replaceAll(",", "."));
		dto.setRedditoDominicale(getNullString(r.getRedditoDominicale()).replaceAll(",", "."));
		dto.setRedditoAgrario(getNullString(r.getRedditoAgrario()).replaceAll(",", "."));
		
		dto.setDataInizio(r.getDataIniVal()!=null ?  sdf.format(r.getDataIniVal()) : "" );
		dto.setDataFine(r.getDataFinVal()!=null ?  sdf.format(r.getDataFinVal()) : "" );
		dto.setDataInizioTit(r.getDtIniPos()!=null ?  sdf.format(r.getDtIniPos()) : "" );
		dto.setDataFineTit(r.getDtFinPos()!=null ?  sdf.format(r.getDtFinPos()) : "" );
		
		return dto;
	}
		
	private ImuDatiCatastaliDTO copyDto2DtoImmobili(ImmobiliAccatastatiOutDTO r, ImuDatiCatastaliDTO dto){
		
		dto.setTipImm("F");
		dto.setTit(r.getDesTipoTitolo());
		dto.setRegime(getNullString(r.getRegime()));
		dto.setUbi(r.getIndirizzoCatastale());
		dto.setFoglio(r.getFoglio());
		dto.setNum(r.getNumero());
		dto.setSub(r.getSubalterno());
		dto.setCat(r.getCategoria());
		dto.setClasse(r.getClasse());
		dto.setConsistenza(r.getConsistenza());
		dto.setRendita(r.getRendita().replaceAll(",", "."));
		dto.setPartita(r.getPartitaCatastale());
		
		Double percPoss = Double.parseDouble(r.getPercentualePossesso());
		
		dto.setPercPoss((percPoss!=null ? df2.format(percPoss.doubleValue()):"").replaceAll(",", "."));
		dto.setDataInizio(r.getDataInizioUiu());
		dto.setDataFine(r.getDataFineUiu());
		dto.setDataInizioTit(r.getDataInizioTit());
		dto.setDataFineTit(r.getDataFineTit());
		
		return dto;
		
	}
	
	private List<ImuDatiCatastaliDTO> getDatiImmobiliByDatiSoggetto(CeTToken obj, String prov, String cf, String piva, String cognome, String nome, Date dtNasc, Date dtInizio, Date dtFine){
			
		ArrayList<ImuDatiCatastaliDTO> lista = new ArrayList<ImuDatiCatastaliDTO>();
		HashMap<BigDecimal,ConsSoggTab> mappaSogg = new HashMap<BigDecimal,ConsSoggTab>();
		ConsSoggTab soggetto;
		
		//Ricerco i dati degli immobili associati al soggetto
		RicercaSoggettoCatDTO ric = new RicercaSoggettoCatDTO();
		ric.setEnteId(obj.getEnte());
		ric.setSessionId(obj.getSessionId());
		ric.setCognome(cognome);
		ric.setNome(nome);
		ric.setCodFis(cf);
		ric.setPartIva(piva);
		ric.setDtNas(dtNasc);
		ric.setDtRifDa(dtInizio);
		ric.setDtRifA(dtFine);
		ric.setRicercaRegime(true);
		
		List<ImmobiliAccatastatiOutDTO>  resUiu = catastoService.getImmobiliAccatastatiByDatiSoggetto(ric);
			for(int i=0; i<resUiu.size(); i++){
				ImmobiliAccatastatiOutDTO r = resUiu.get(i);
				ImuDatiCatastaliDTO dto = new ImuDatiCatastaliDTO();
				
				if(mappaSogg.containsKey(r.getPkCuaa()))
					soggetto = mappaSogg.get(r.getPkCuaa());
				else{
					soggetto = this.getSoggettoByPkCuaa(obj, r.getPkCuaa());
					mappaSogg.put(r.getPkCuaa(), soggetto);
				}
				
				if("G".equals(soggetto.getFlagPersFisica()))
					dto = this.copySogG2Dto(soggetto, dto);
				else
					dto = this.copySogF2Dto(soggetto, dto);
				
				dto.setRicercaCod((cf!=null || piva!=null) ? 1 : 0);
				dto.setProvCat(getNullString(prov));  //Fisso per il comune: provincia corrispondente
				dto = copyDto2DtoImmobili(r,dto);
				
				lista.add(dto);
			}
		
			
		return lista;
	}
	
	private List<ImuDatiCatastaliDTO> getDatiTerreniByDatiSoggetto(CeTToken obj, String prov, String cf, String piva, String cognome, String nome, Date dtNasc, Date dtInizio, Date dtFine){
			
		
		ArrayList<ImuDatiCatastaliDTO> lista = new ArrayList<ImuDatiCatastaliDTO>();
		HashMap<BigDecimal,ConsSoggTab> mappaSogg = new HashMap<BigDecimal,ConsSoggTab>();
		ConsSoggTab soggetto;
		
		//Ricerco i dati degli immobili associati al soggetto
		RicercaSoggettoCatDTO ric = new RicercaSoggettoCatDTO();
		ric.setEnteId(obj.getEnte());
		ric.setSessionId(obj.getSessionId());
		ric.setCognome(cognome);
		ric.setNome(nome);
		ric.setCodFis(cf);
		ric.setPartIva(piva);
		ric.setDtNas(dtNasc);
		ric.setDtRifDa(dtInizio);
		ric.setDtRifA(dtFine);
		ric.setRicercaRegime(true);
		
		List<TerrenoPerSoggDTO>  resTerr = catastoService.getTerreniAccatastatiByDatiSoggetto(ric);
		for(int i=0; i<resTerr.size(); i++){
			TerrenoPerSoggDTO r = resTerr.get(i);
	
			ImuDatiCatastaliDTO dto = new ImuDatiCatastaliDTO();
			
			if(mappaSogg.containsKey(r.getPkCuaa()))
				soggetto = mappaSogg.get(r.getPkCuaa());
			else{
				soggetto = this.getSoggettoByPkCuaa(obj, r.getPkCuaa());
				mappaSogg.put(r.getPkCuaa(), soggetto);
			}
			
			if("G".equals(soggetto.getFlagPersFisica()))
				dto = this.copySogG2Dto(soggetto, dto);
			else
				dto = this.copySogF2Dto(soggetto, dto);
			
			dto.setRicercaCod((cf!=null || piva!=null) ? 1 : 0);
			dto.setProvCat(getNullString(prov));  //Fisso per il comune: provincia corrispondente
			dto = copyDto2DtoTerreni(r,dto);

			lista.add(dto);
		}
		
		
		return lista;
	}
	
	
	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuDatiCatastaliDTO[] getDatiCatasto(@WebParam(name = "token") CeTToken token, 
												@WebParam(name = "codfiscale") String codfiscale,
												@WebParam(name = "cognome") String cognome,
												@WebParam(name = "nome") String nome,
												@WebParam(name = "datanascita") String datanascita){
												
		
		List<ImuDatiCatastaliDTO> listaImm = new ArrayList<ImuDatiCatastaliDTO>();
		List<ImuDatiCatastaliDTO> listaTerr = new ArrayList<ImuDatiCatastaliDTO>();
		List<ImuDatiCatastaliDTO> lista = new ArrayList<ImuDatiCatastaliDTO>();
		
		
		boolean ricercaCf = codfiscale!=null && !codfiscale.trim().equals("") && !codfiscale.equalsIgnoreCase("null");
		boolean ricercaCogNomDtNas =(cognome!=null && !cognome.trim().equals("") && !cognome.equalsIgnoreCase("null")) && 
									(nome!=null && !nome.trim().equals("") && !nome.equalsIgnoreCase("null")) && 
									(datanascita!=null && !datanascita.trim().equals("") && !datanascita.equalsIgnoreCase("null"));	
		try{
			
			
			logger.debug("codfiscale ["+codfiscale+"]");
			logger.debug("cognome ["+cognome+"]");
			logger.debug("nome ["+nome+"]");
			logger.debug("datanascita ["+datanascita+"]");
			
			if(ricercaCf || ricercaCogNomDtNas){
				
				Date dtFine = new Date();
				int year = Calendar.getInstance().get(Calendar.YEAR);
				Date dtInizio = sdf.parse("01/01/"+ year);
				String prov = this.getSiglaProvincia(token);
				
				if(ricercaCf){
					
					if(codfiscale.length()==16){
						//Ricerco nei soggetti fisici
						listaImm.addAll(getDatiImmobiliByDatiSoggetto(token, prov, codfiscale, null, null, null, null, dtInizio, dtFine));
						listaTerr.addAll(getDatiTerreniByDatiSoggetto(token, prov, codfiscale, null, null, null, null, dtInizio, dtFine));
					
					}else if(codfiscale.length()==11){
						
						String piva = codfiscale;
						
						//Ricerco nei soggetti giuridici
						listaImm.addAll(getDatiImmobiliByDatiSoggetto(token, prov, null, piva, null, null, null, dtInizio, dtFine));
						listaTerr.addAll(getDatiTerreniByDatiSoggetto(token, prov, null, piva, null, null, null, dtInizio, dtFine));
						
					}
				}
				
				if(ricercaCogNomDtNas){
					
					Date dtNascita = super.getDateFromString(datanascita);
					
					//Se per codice fiscale non trovo nulla, ricerco per dati anagrafici
					if(listaImm.size()==0)
						listaImm.addAll(getDatiImmobiliByDatiSoggetto(token, prov, null, null, cognome, nome, dtNascita, dtInizio, dtFine));
					
					//Se per codice fiscale non trovo nulla, ricerco per dati anagrafici
					if(listaTerr.size()==0)
						listaTerr.addAll(getDatiTerreniByDatiSoggetto(token, prov, null, null, cognome, nome, dtNascita, dtInizio, dtFine));
				
				}
				
			}else{
				ImuDatiCatastaliDTO err = new ImuDatiCatastaliDTO();
				err.setErrorCode("02");
				err.setErrorMessage("Parametri di ricerca catasto non validi.");
				lista.add(err);
			}
			
			lista.addAll(listaImm);
			lista.addAll(listaTerr);
	
		}catch (Throwable t) {
			
			ImuDatiCatastaliDTO err = new ImuDatiCatastaliDTO();
			err.setErrorCode("01");
			err.setErrorMessage("Errore recupero Dati Catasto:" + t.getMessage());
			lista.add(err);
			
			logger.error("Eccezione - getDatiCatasto: "+t.getMessage(), t);
		}
		
		//Se non ottengo risultati, inserisco cmq un record 
		if(lista.size()==0){
			ImuDatiCatastaliDTO err = new ImuDatiCatastaliDTO();
			err.setErrorCode("03");
			err.setErrorMessage("Dati non presenti");
			lista.add(err);
		}
		
		ImuDatiCatastaliDTO[] array = new ImuDatiCatastaliDTO[lista.size()];
		for(int i=0; i<lista.size(); i++){
			array[i]=lista.get(i);
			logger.debug("getDatiCatasto - " + array[i].stampaRecord());
		}
		return array;
	}
	
	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuConduzCatastoIntervalloDTO[] getConduzioneCatasto(@WebParam(name = "token") CeTToken token, 
			 										  @WebParam(name = "tipo") String tipo,
													  @WebParam(name = "foglio") String foglio,
													  @WebParam(name = "num") String num,
													  @WebParam(name = "sub") String sub) { 
		
		List<ImuConduzCatastoIntervalloDTO> listaCondIntervalli = new ArrayList<ImuConduzCatastoIntervalloDTO>();
		
		
		logger.debug("foglio ["+foglio+"]");
		logger.debug("num ["+num+"]");
		logger.debug("sub ["+sub+"]");
		
		try{
			
			Date dtFine = new Date();
			int year = Calendar.getInstance().get(Calendar.YEAR);
			Date dtInizio = sdf.parse("01/01/"+ year);
			boolean tipImmValido = tipo!=null && ("F".equalsIgnoreCase(tipo) ||"T".equalsIgnoreCase(tipo));
			
			boolean FPvalido = (foglio!=null && foglio.length()>0 && !foglio.equalsIgnoreCase("null")) &&
							   (num!=null && num.length()>0 && !num.equalsIgnoreCase("null"));
			
			if(FPvalido && tipImmValido){
				
				//Ricerca Conduzioni Immobili
				RicercaOggettoCatDTO ro = new RicercaOggettoCatDTO();
				ro.setEnteId(token.getEnte());
				ro.setSessionId(token.getSessionId());
				ro.setDtInizioRif(dtInizio);
				ro.setDtFineRif(dtFine);
				ro.setFoglio(foglio);
				ro.setParticella(num);
				ro.setUnimm(sub);
				
				List<SoggettoCatastoDTO> listaC = new ArrayList<SoggettoCatastoDTO>();
				List<Date[]> listaI = new ArrayList<Date[]>();
				
				if("F".equalsIgnoreCase(tipo)){
					//Ricerca Conduzioni Immobili
				//	listaC = catastoService.getListaProprietariUiuByFPSDataRange(ro);
					listaI = catastoService.getIntervalliProprietaUiuByFPSDataRange(ro);
				}else{
					//Ricerca Conduzioni Terreni
				 //	listaC = catastoService.getListaProprietariTerrByFPSDataRange(ro);
					listaI = catastoService.getIntervalliProprietaTerrByFPSDataRange(ro);
				}
				

				//Calcolo gli intervalli di possesso 
				List<Date[]> listaIntervalli = this.ordinaIntervalli(listaI, dtInizio, dtFine);
				
				//Per ciascun intervallo recupero la lista di proprietari, quindi calcolo la somma della perc.di possesso
				for(Date[] intervallo : listaIntervalli){
					
					ImuConduzCatastoIntervalloDTO result = new ImuConduzCatastoIntervalloDTO();
					
					ro.setDtInizioRif(intervallo[0]);
					ro.setDtFineRif(intervallo[1]);
				
					
					if("F".equalsIgnoreCase(tipo)){
						//Ricerca Conduzioni Immobili
						listaC = catastoService.getListaProprietariUiuByFPSDataRange(ro);
						
					}else{
						//Ricerca Conduzioni Terreni
						listaC = catastoService.getListaProprietariTerrByFPSDataRange(ro);
					}
					
					List<ImuConduzCatastoDTO> listaConduz = new ArrayList<ImuConduzCatastoDTO>();
					BigDecimal sumPercPoss = new BigDecimal(0);
					for(SoggettoCatastoDTO s : listaC){
						ImuConduzCatastoDTO cimu = new ImuConduzCatastoDTO();
						
						ConsSoggTab sogg = s.getConsSoggTab();
						cimu.setTipImm(getNullString(s.getTipImm()));
						cimu.setTipSogg(getNullString(sogg.getFlagPersFisica()));
						if("G".equalsIgnoreCase(sogg.getFlagPersFisica())){
							cimu.setDenom(getNullString(sogg.getRagiSoci()));
							cimu.setCodFisc(getNullString(sogg.getCodiPiva()));
							
						}else{
							cimu.setCodFisc(getNullString(sogg.getCodiFisc()));
							Date dtNasc = sogg.getDataNasc();
							cimu.setDatNas(dtNasc!=null ? sdf.format(dtNasc) : "");
							cimu.setDenom((getNullString(sogg.getRagiSoci())+" "+getNullString(sogg.getNome())).trim());
							
						}
						cimu.setSede(getNullString(sogg.getComuNasc()));
						cimu.setDataInizioTit(sdf.format(s.getDataInizioPossesso()));
						cimu.setDataFineTit(sdf.format(s.getDataFinePossesso()));
						
						BigDecimal percPoss = s.getPercPoss();
						sumPercPoss = percPoss!=null ? sumPercPoss.add(percPoss) : sumPercPoss;
						
						cimu.setPercPoss((percPoss!=null ? df2.format(percPoss.doubleValue()):"").replaceAll(",", "."));
						cimu.setTit(getNullString(s.getTitolo()));
					
						listaConduz.add(cimu);
					}
					
					result.setSovraconduzione(sumPercPoss.compareTo(new BigDecimal(100))>0 ? "1" : "0"); 
					result.setQuota(df2.format(sumPercPoss.doubleValue()).replaceAll(",", "."));
					result.setDtIniRif(sdf.format(intervallo[0]));
					result.setDtFinRif(sdf.format(intervallo[1]));
					
					ImuConduzCatastoDTO[] array = new ImuConduzCatastoDTO[listaConduz.size()];
					
					for(int i=0; i<listaConduz.size(); i++){
						ImuConduzCatastoDTO dto = listaConduz.get(i);
						array[i]=dto;
						
						logger.debug("getConduzioneCatasto lista conduzione ["+ result.getDtIniRif() + " - " + result.getDtFinRif() +"] " + array[i].stampaRecord());
					}
					
					result.setConduzioni(array);
					
					
					
					listaCondIntervalli.add(result);
					
				}
				
				
			}else{
				ImuConduzCatastoIntervalloDTO err = new ImuConduzCatastoIntervalloDTO();
				err.setErrorCode("02");
				err.setErrorMessage("Parametri di ricerca - Dati Conduzione Catasto - non validi");
				listaCondIntervalli.add(err);
			}
			
		}catch(Exception e){
			ImuConduzCatastoIntervalloDTO err = new ImuConduzCatastoIntervalloDTO();
			err.setErrorCode("01");
			err.setErrorMessage("Errore recupero Dati Conduzione Catasto:" + e.getMessage());
			listaCondIntervalli.add(err);
			
			logger.error("Eccezione - getConduzioneCatasto: "+e.getMessage(), e);
		}
		
		
		//Se non ottengo risultati, inserisco cmq un record 
		if(listaCondIntervalli.size()==0){
			ImuConduzCatastoIntervalloDTO err = new ImuConduzCatastoIntervalloDTO();
			err.setErrorCode("03");
			err.setErrorMessage("Dati non presenti");
			listaCondIntervalli.add(err);
		}
		
		ImuConduzCatastoIntervalloDTO[] array = new ImuConduzCatastoIntervalloDTO[listaCondIntervalli.size()];
		for(int i=0; i<listaCondIntervalli.size(); i++){
			array[i]=listaCondIntervalli.get(i);
			logger.debug("getConduzioneCatasto " + array[i].stampaRecord());
		}
		return array;
		
	}
	
	private List<Date[]> ordinaIntervalli(List<Date[]> listaI, Date dtIniRif, Date dtFinRif) throws ParseException{

		List<Date> listaTemp = new ArrayList<Date>();
		
		//Creo la lista NON ORDINATA di date a disposizione
		for(Date[] intervallo : listaI){
			
			Date data1 = intervallo[0]!=null ? intervallo[0] : sdf.parse("01/01/0001");
			Date data2 = intervallo[1]!=null ? intervallo[1] : sdf.parse("31/12/9999");
			
			if(!listaTemp.contains(data1))
				listaTemp.add(data1);
				
			if(!listaTemp.contains(data2))
				listaTemp.add(data2);
		}
		
		if(!listaTemp.contains(dtIniRif))
			listaTemp.add(dtIniRif);
		if(!listaTemp.contains(dtFinRif))
			listaTemp.add(dtFinRif);
		
		//Ordino la lista (Bubble Sort)
		boolean scambio = true;
		int stop = listaTemp.size();
		while(scambio){
			scambio = false;
			for(int i=0; i<stop-1; i++){
				Date data_i = listaTemp.get(i);
				Date data_i_1 = listaTemp.get(i+1);
				if(data_i.compareTo(data_i_1)>0){
					//scambio
					listaTemp.set(i+1, data_i);
					listaTemp.set(i, data_i_1);
					scambio=true;
				}
			}
			
			stop--;
		}
		
		//Tolgo le date precedenti al 01/01
		boolean trovato = false;
		while(!trovato){
			Date data = listaTemp.get(0);
			if(data.compareTo(dtIniRif)==0)
				trovato = true;
			else
				listaTemp.remove(0);
		}
		
		//Tolgo le date successive ad OGGI
		trovato = false;
		while(!trovato){
			Date data = listaTemp.get(listaTemp.size()-1);
			if(data.compareTo(dtFinRif)==0)
				trovato = true;
			else
				listaTemp.remove(listaTemp.size()-1);
		}
		
		//A questo punto creo la lista di intervalli da utilizzare (tra il 01/01/ANNO_CORRENTE e la data corrente)
		List<Date[]> listaIntervalli = new ArrayList<Date[]>();
		for(int i=0; i<listaTemp.size()-1; i++){
			Date[] intervallo = new Date[2];
			intervallo[0]=listaTemp.get(i);
			intervallo[1]=listaTemp.get(i+1);
			listaIntervalli.add(intervallo);
			
		}
		
		return listaIntervalli;
		
	}
	
	
	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuBaseDTO salvaElaborazione(@WebParam(name = "token") CeTToken token,  
							            @WebParam(name = "codFisc") String codFisc,
						                @WebParam(name = "json")  String json){
		
		ImuBaseDTO out = new ImuBaseDTO();
		
		logger.debug("salvaElaborazione - codFisc: ["+codFisc+"]");
		logger.debug("salvaElaborazione - json: ["+json+"]");
			
		try{
			
			if(codFisc!=null && codFisc.length()>=11){
				
				SaldoImuBaseDTO in = new SaldoImuBaseDTO();
				in.setEnteId(token.getEnte());
				in.setSessionId(token.getSessionId());
				in.setCodfisc(codFisc);
				in.setDati(json);
				
				imuService.salvaElaborazione(in);
				logger.debug("IMUServiceBean - salvaElaborazione completato con successo.");
			
			}else{
				out.setErrorCode("02");
				out.setErrorMessage("IMUServiceBean - salvaElaborazione: codice fiscale ["+codFisc+"] non valido.");
			}
			
		}catch(Exception e){
			out.setErrorCode("01");
			out.setErrorMessage("Errore salvaElaborazione:" + e.getMessage());
			logger.error("Errore IMUServiceBean - salvaElaborazione:", e);
		}
		
		logger.debug("salvaElaborazione - " + out.stampaRecord());
		
		return out;
		
	}

	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuDatiElaborazioneDTO getPrecedentiElaborazioni(@WebParam(name = "token") CeTToken token, 
											                @WebParam(name = "codfiscale") String codfiscale) {
		
		logger.debug("getPrecedentiElaborazioni: ["+codfiscale+"]");
		ImuDatiElaborazioneDTO out = new ImuDatiElaborazioneDTO();
		
		try{
		
			if(codfiscale!=null && codfiscale.length()>=11){
			
				SaldoImuBaseDTO in = new SaldoImuBaseDTO();
				in.setEnteId(token.getEnte());
				in.setSessionId(token.getSessionId());
				in.setCodfisc(codfiscale);
				
				String json = imuService.getJsonUltimaElaborazione(in);
				out.setCodFisc(codfiscale);
				
				if(json!=null)
					out.setJson(json);
				else{
					out.setErrorCode("03");
					out.setErrorMessage("Dati non disponibili");
				}
				
				logger.debug("IMUServiceBean - getPrecedentiElaborazioni completato con successo.");
				
			}else{
				out.setErrorCode("02");
				out.setErrorMessage("Parametri di ricerca - getPrecedentiElaborazioni non validi");
			}
		
		}catch(Exception e){
			out.setErrorCode("01");
			out.setErrorMessage("Errore recupero Precedenti Elaborazioni:" + e.getMessage());
		}
		
		logger.debug("getPrecedentiElaborazioni - " + out.stampaRecord());
		
		return out;
	}

	@WebMethod
	@Interceptors({ValidationStateless.class, AuditStateless.class})
	public ImuBaseDTO storicizza(@WebParam(name = "token") CeTToken token, 
            					 @WebParam(name = "codFisc") String codFisc,
            					 @WebParam(name = "xml") String xml) {
		
		ImuBaseDTO out = new ImuBaseDTO();
		
		logger.debug("storicizza - codFisc: ["+codFisc+"]");
		logger.debug("storicizza - xml: ["+xml+"]");
			
		try{

			if(codFisc!=null && codFisc.length()>=11){
				
				SaldoImuBaseDTO in = new SaldoImuBaseDTO();
				in.setEnteId(token.getEnte());
				in.setSessionId(token.getSessionId());
				in.setCodfisc(codFisc);
				in.setDati(xml);
				
				imuService.storicizza(in);
			
			}else{
				out.setErrorCode("02");
				out.setErrorMessage("IMUServiceBean - storicizza: codice fiscale ["+codFisc+"] non valido.");
			}
			
			logger.debug("IMUServiceBean - storicizza completato con successo.");
			
		}catch(Exception e){
			out.setErrorCode("01");
			out.setErrorMessage("Errore storicizza:" + e.getMessage());
			logger.error("Errore IMUServiceBean - storicizza:", e);
		}
		
		logger.debug("storicizza - " + out.stampaRecord());
		
		return out;
		
	}


	

}
