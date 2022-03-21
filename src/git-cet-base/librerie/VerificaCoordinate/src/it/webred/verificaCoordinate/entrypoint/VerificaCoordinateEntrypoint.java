package it.webred.verificaCoordinate.entrypoint;

import java.sql.Connection;

import org.apache.log4j.Logger;

import it.webred.verificaCoordinate.dto.VerificaCoordinateBaseDTO;
import it.webred.verificaCoordinate.dto.request.C6DTO;
import it.webred.verificaCoordinate.dto.request.CredenzialiDTO;
import it.webred.verificaCoordinate.dto.request.DatiCatastaliDTO;
import it.webred.verificaCoordinate.dto.request.DatiToponomasticiDTO;
import it.webred.verificaCoordinate.dto.request.NonResidenzialeDTO;
import it.webred.verificaCoordinate.dto.request.ResidenzialeDTO;
import it.webred.verificaCoordinate.dto.request.TipoCatastoDTO;
import it.webred.verificaCoordinate.dto.request.UserInfoDTO;
import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.ErrorDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;
import it.webred.verificaCoordinate.logic.VerificaCoordinateLogic;

public class VerificaCoordinateEntrypoint {
	
	private static final Logger log = Logger.getLogger(VerificaCoordinateEntrypoint.class.getName());
	
	public VerificaCoordinateBaseDTO verifica(VerificaCoordinateBaseDTO params, Connection connFromWS) {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		if (!(params instanceof VerificaCoordinateRequestDTO)) {
			setRespError(resp, "Il formato della richiesta non è corretto");
			return resp;
		}
		
		log.info("Inizio verifica coordinate");
		VerificaCoordinateRequestDTO reqParams = (VerificaCoordinateRequestDTO)params;
		String codEnte = reqParams.getInfoUtente().getEnteId();
		
		VerificaCoordinateLogic logic = new VerificaCoordinateLogic(codEnte, connFromWS);		
		resp = logic.verifica(reqParams);
		
		try {
			logic.closeConnection(connFromWS, true);
		} catch (Exception e) {
			log.error("Errore nella chiusura della connessione ricevuta come parametro", e);
		}
		log.info("Fine verifica coordinate");

		return resp;
	}
	
	/*
	 * solo per test...
	 */
	public static void main(String[] args) throws Exception {
		VerificaCoordinateRequestDTO testReq = new VerificaCoordinateRequestDTO();
		/*UserInfoDTO infoUtente = new UserInfoDTO();
		infoUtente.setEnteId("DEFAULT");
		testReq.setInfoUtente(infoUtente);
		CredenzialiDTO credenziali = new CredenzialiDTO();
		credenziali.setSistema("DIA-AUTO");
		credenziali.setToken("XDDFFF$$FFGGGDDS120");
		credenziali.setUtente("pippo");
		testReq.setCredenziali(credenziali);
		DatiToponomasticiDTO topo = new DatiToponomasticiDTO();*/
		
		//prova 1
		/*//topo.setCodiceVia("1542");
		topo.setTipoArea("VIA");
		topo.setNomeVia("SALEMI");
		topo.setCivico("5");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("14");
		cat.setMappale("36");
		cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);
		NonResidenzialeDTO nonResidenziale = new NonResidenzialeDTO();
		nonResidenziale.setCategoriaEdilizia("C06");
		nonResidenziale.setC6(new C6DTO());
		nonResidenziale.getC6().setPostoAutoCoperto("S");
		testReq.setNonResidenziale(nonResidenziale);*/
		
		//prova 2
		/*topo.setTipoArea("VIA");
		topo.setNomeVia("LONGARONE");
		topo.setCivico("18");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("11");
		cat.setMappale("92");
		//cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);
		ResidenzialeDTO residenziale = new ResidenzialeDTO();
		residenziale.setCategoriaEdilizia("Economica");
		residenziale.setTipoIntervento("Ristrutturazione");		
		residenziale.setConsistenza("5,00");
		residenziale.setSuperficie("100,00");
		residenziale.setAscensoreOrMin3MFT("S");
		testReq.setResidenziale(residenziale);*/
		
		//prova 3
		//topo.setCodiceVia("3102");
		/*topo.setTipoArea(null);
		topo.setNomeVia("romagna");
		topo.setCivico("10");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("11"); //errore, restituisce 357 che deve essere buono
		cat.setMappale("42"); //errore, restituisce 307 o 315 che devono essere buoni
		//cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);*/
		
		//prova vincoli ambientali
		/*topo.setTipoArea("VIA");
		topo.setNomeVia("SAN MAMETE");
		topo.setCivico("110");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("87");
		cat.setMappale("52");
		//cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);*/
		
		//prova vincoli archeologici
		/*topo.setTipoArea("VIA");
		topo.setNomeVia("VIGNA");
		topo.setCivico("1");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("388");
		cat.setMappale("3");
		//cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);*/
		
		//prova vincoli monumentali
		/*topo.setTipoArea("VIA");
		topo.setNomeVia("PARETO VILFREDO");
		topo.setCivico("14");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("90");
		cat.setMappale("180");
		//cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);
		
		testReq.setDatiCatastali(cat);*/
		
		/*UserInfoDTO infoUtente = new UserInfoDTO();
		infoUtente.setEnteId("DEFAULT");
		infoUtente.setUserId("test");
		infoUtente.setPassword("test");
		testReq.setInfoUtente(infoUtente);
		CredenzialiDTO credenziali = new CredenzialiDTO();
		credenziali.setSistema("OOPE");
		credenziali.setUtente("test");
		credenziali.setToken(null);
		testReq.setCredenziali(credenziali);
		DatiToponomasticiDTO topo = new DatiToponomasticiDTO();
		topo.setTipoArea("VIA");
		topo.setNomeVia("lod");
		topo.setCivico("2");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("1");
		cat.setMappale("1");
		cat.setSubalterno(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);
		testReq.setDatiCatastali(cat);
		ResidenzialeDTO residenziale = new ResidenzialeDTO();
		residenziale.setCategoriaEdilizia("Civile");
		residenziale.setTipoIntervento("Ristrutturazione");		
		residenziale.setConsistenza("5,00");
		residenziale.setSuperficie("100,00");
		residenziale.setAscensoreOrMin3MFT(null);
		testReq.setResidenziale(residenziale);*/
		
		UserInfoDTO infoUtente = new UserInfoDTO();
		infoUtente.setEnteId("DEFAULT");
		infoUtente.setUserId("test");
		infoUtente.setPassword("test");
		testReq.setInfoUtente(infoUtente);
		CredenzialiDTO credenziali = new CredenzialiDTO();
		credenziali.setSistema("OOPE");
		credenziali.setUtente("test");
		credenziali.setToken(null);
		testReq.setCredenziali(credenziali);
		DatiToponomasticiDTO topo = new DatiToponomasticiDTO();
		topo.setTipoArea("VIA");
		topo.setNomeVia("SALEMI");
		topo.setCivico("5");
		topo.setEsponente(null);
		topo.setCodiceVia(null);
		testReq.setDatiToponomastici(topo);
		DatiCatastaliDTO cat = new DatiCatastaliDTO();
		cat.setFoglio("14");
		cat.setMappale("36");
		cat.setSubalterno("2");
		cat.setSezione(null);
		cat.setTipoCatasto(TipoCatastoDTO.FABBRICATI);
		testReq.setDatiCatastali(cat);
		ResidenzialeDTO residenziale = new ResidenzialeDTO();
		residenziale.setCategoriaEdilizia("Civile");
		residenziale.setTipoIntervento("Ristrutturazione");		
		residenziale.setConsistenza("5,00");
		residenziale.setSuperficie("100,00");
		residenziale.setAscensoreOrMin3MFT(null);
		testReq.setResidenziale(residenziale);
		
		
		VerificaCoordinateResponseDTO testResp = (VerificaCoordinateResponseDTO)new VerificaCoordinateEntrypoint().verifica(testReq,null);
		
		log.info("ERRORI: " + (testResp.getError() == null ? "NULL" : testResp.getError().size()));
		if (testResp.getError() != null & testResp.getError().size() > 0) {
			log.info(testResp.getError().get(0).getDesc());
		}
		log.info("VIE: " + (testResp.getElencoVie() == null || testResp.getElencoVie().getVia() == null 
							? "NULL" : testResp.getElencoVie().getVia().size()));
		log.info("CIVICI: " + (testResp.getElencoCivico() == null || testResp.getElencoCivico().getCivico() == null 
							? "NULL" : testResp.getElencoCivico().getCivico().size()));
		log.info("UIU: " + (testResp.getElencoUiu() == null || testResp.getElencoUiu().getUiu() == null 
							? "NULL" : testResp.getElencoUiu().getUiu().size()));
		log.info("MAPPALI: " + (testResp.getElencoMappale() == null || testResp.getElencoMappale().getMappale() == null 
							? "NULL" : testResp.getElencoMappale().getMappale().size()));
		log.info("INFO: " + (testResp.getInfo() == null ? "NULL" : testResp.getInfo().getDesc()));
		log.info("OK: " + (testResp.getOk() == null ? "NULL" : testResp.getOk().getDesc()));
		if (testResp.getElencoMappale() != null && 
		testResp.getElencoMappale().getMappale().size() == 1 && 
		testResp.getElencoMappale().getMappale().get(0).getDatiAttesi() != null &&
		testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiResidenziale().size() > 0) {
			log.info("DATI ATTESI RESIDENZIALE N. " + testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiResidenziale().size());
			log.info("CLASSAMENTI PRIMO GRUPPO N. " + 
			(testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiResidenziale().get(0).getClassamenti().getClassamento() == null ?
			0 :
			testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiResidenziale().get(0).getClassamenti().getClassamento().size()));
		}
		if (testResp.getElencoMappale() != null && 
		testResp.getElencoMappale().getMappale().size() == 1 && 
		testResp.getElencoMappale().getMappale().get(0).getDatiAttesi() != null &&
		testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiNonResidenziale().size() > 0) {
			log.info("DATI ATTESI NON RESIDENZIALE N. " + testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiNonResidenziale().size());
			log.info("CLASSE MEDIA RIF.: " + testResp.getElencoMappale().getMappale().get(0).getDatiAttesi().getDatiAttesiNonResidenziale().get(0).getClasseMediaRiferimento());
		}
		if (testResp.getElencoMappale() != null && 
				testResp.getElencoMappale().getMappale().size() == 1 && 
				testResp.getElencoMappale().getMappale().get(0).getVincoli() != null &&
				testResp.getElencoMappale().getMappale().get(0).getVincoli().getVincolo() != null &&
				testResp.getElencoMappale().getMappale().get(0).getVincoli().getVincolo().size() > 0 &&
				testResp.getElencoMappale().getMappale().get(0).getVincoli().getVincolo().get(0).getRiga() != null) {
					log.info("RECORD VINCOLO N.: " + testResp.getElencoMappale().getMappale().get(0).getVincoli().getVincolo().get(0).getRiga().size());
		}
		
		if (testResp.getCoordinate() != null) {
			log.info("LATITUDINE CIVICO: " + (testResp.getCoordinate().getLat() == null ? "NULL" : testResp.getCoordinate().getLat()));
			log.info("LONGITUDINE CIVICO: " + (testResp.getCoordinate().getLon() == null ? "NULL" : testResp.getCoordinate().getLon()));
		}
	}
	
	private void setRespError(VerificaCoordinateResponseDTO resp, String errDesc) {
		ErrorDTO err = new ErrorDTO();
		err.setDesc(errDesc);
		resp.getError().add(err);
	}

}
