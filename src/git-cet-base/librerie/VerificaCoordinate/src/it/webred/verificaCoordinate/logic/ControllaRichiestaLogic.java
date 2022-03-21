package it.webred.verificaCoordinate.logic;

import java.sql.Connection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import it.webred.verificaCoordinate.dto.request.C6DTO;
import it.webred.verificaCoordinate.dto.request.NonResidenzialeDTO;
import it.webred.verificaCoordinate.dto.request.ResidenzialeDTO;
import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

public class ControllaRichiestaLogic extends VerificaCoordinateLogic {
	
	private static final Logger log = Logger.getLogger(ControllaRichiestaLogic.class.getName());
	
	public ControllaRichiestaLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public VerificaCoordinateResponseDTO controllaRichiesta(VerificaCoordinateRequestDTO params) {
		VerificaCoordinateResponseDTO resp = new VerificaCoordinateResponseDTO();
		
		log.info("Inizio controllo formale della richiesta");
		ArrayList<String> errs = new ArrayList<String>();
		String nomeVia = params.getDatiToponomastici().getNomeVia();
		if (nomeVia == null || nomeVia.equals("")) {
			errs.add("il Nome Via è obbligatorio");
		}
		String civico = params.getDatiToponomastici().getCivico();
		String esponente = params.getDatiToponomastici().getEsponente();
		String tipoCatasto = params.getDatiCatastali().getTipoCatasto().value();
		String subalterno = params.getDatiCatastali().getSubalterno();
		if (civico == null || civico.equals("")) {
			if (esponente != null && !esponente.equals("")) {
				errs.add("il Civico è obbligatorio se è valorizzato l'Esponente");
			}
			if (tipoCatasto != null && tipoCatasto.equalsIgnoreCase("Fabbricati") &&
				subalterno != null && !subalterno.equals("")) {
				errs.add("il Civico è obbligatorio se il Tipo Catasto  uguale a \"Fabbricati\" ed è valorizzato il Subalterno");
			}
		}
		String foglio = params.getDatiCatastali().getFoglio();
		if (foglio == null || foglio.equals("")) {
			errs.add("il Foglio è obbligatorio");
		} else {
			try {
				foglio = "" + Integer.parseInt(foglio);
			} catch (Exception e) {
				errs.add("il Foglio deve essere numerico");
			}
		}
		String mappale = params.getDatiCatastali().getMappale();
		if (mappale == null || mappale.equals("")) {
			errs.add("il Mappale è obbligatorio");
		}
		if (tipoCatasto == null || tipoCatasto.equals("")) {
			errs.add("il Tipo Catasto è obbligatorio");
		} else {
			if (!tipoCatasto.equalsIgnoreCase("Terreni") && !tipoCatasto.equalsIgnoreCase("Fabbricati")) {
				//questo caso non dovrebbe mai verificarsi, visto che tipoCatasto è una enum e non più una String
				errs.add("il Tipo Catasto non è corretto (valori validi: \"Terreni\" o \"Fabbricati\")");
			} else {
				if (subalterno != null && !subalterno.equals("")) {
					if (!tipoCatasto.equalsIgnoreCase("Fabbricati")) {
						errs.add("se è valorizzato il Subalterno, il Tipo Catasto deve essere \"Fabbricati\"");
					} else {
						try {
							subalterno = "" + Integer.parseInt(subalterno);
						} catch (Exception e) {
							errs.add("se è valorizzato il Subalterno, deve essere numerico");
						}
					}					
				}
			}
		}
		ResidenzialeDTO residenziale = new ResidenzialeDTO();
		if (residenziale != null) {
			String categoriaEdilizia = residenziale.getCategoriaEdilizia();
			if (categoriaEdilizia != null && !categoriaEdilizia.equals("") &&
			!categoriaEdilizia.equalsIgnoreCase("Civile") && !categoriaEdilizia.equalsIgnoreCase("Economica")) {
				errs.add("la Categoria Edilizia della tipologia \"Residenziale\" non è corretta (valori validi: \"Civile\" o \"Economica\")");
			}
			String tipoIntervento = residenziale.getTipoIntervento();
			if (tipoIntervento != null && !tipoIntervento.equals("") &&
			!tipoIntervento.equalsIgnoreCase("Nuova Costruzione") && !tipoIntervento.equalsIgnoreCase("Ristrutturazione")) {
				errs.add("il Tipo Intervento della tipologia \"Residenziale\" non è corretto (valori validi: \"Nuova Costruzione\" o \"Ristrutturazione\")");
			}
			String ascensore = residenziale.getAscensoreOrMin3MFT();
			if (ascensore != null && !ascensore.equals("") &&
			!ascensore.equalsIgnoreCase("S") && !ascensore.equalsIgnoreCase("N")) {
				errs.add("l'Ascensore della tipologia \"Residenziale\" non è corretto (valori validi: \"S\" o \"N\")");
			}
		}
		NonResidenzialeDTO nonResidenziale = new NonResidenzialeDTO();
		if (nonResidenziale != null) {
			String categoriaEdilizia = nonResidenziale.getCategoriaEdilizia();
			if (categoriaEdilizia != null && !categoriaEdilizia.equals("") &&
			!categoriaEdilizia.equalsIgnoreCase("A10") && !categoriaEdilizia.equalsIgnoreCase("C01") &&
			!categoriaEdilizia.equalsIgnoreCase("C02") && !categoriaEdilizia.equalsIgnoreCase("C03") &&
			!categoriaEdilizia.equalsIgnoreCase("C06")) {
				errs.add("la Categoria Edilizia della tipologia \"Non Residenziale\" non è corretta (valori validi: \"A10\", \"C01\", \"C02\", \"C03\", \"C06\")");
			}
			C6DTO c6 = nonResidenziale.getC6();
			if (c6 != null) {
				String postoAutoCoperto = c6.getPostoAutoCoperto();
				if (postoAutoCoperto != null && !postoAutoCoperto.equals("") &&
				!postoAutoCoperto.equalsIgnoreCase("S") && !postoAutoCoperto.equalsIgnoreCase("N")) {
					errs.add("il Posto Auto Coperto della tipologia \"Non Residenziale\" non è corretto (valori validi: \"S\" o \"N\")");
				}
			}			
		}

		if (errs.size() > 0) {
			String msg = "I dati specificati nella richiesta non sono corretti, in quanto" +
						(errs.size() == 1 ? " " : ": ");
			int idx = 0;
			for (String err : errs) {
				if (idx > 0) {
					msg += "; ";
				}
				msg += err;
				idx++;
			}
			setRespError(resp, msg);
		}
		log.info("Fine controllo formale della richiesta");
		
		return resp;
	}
	
}
