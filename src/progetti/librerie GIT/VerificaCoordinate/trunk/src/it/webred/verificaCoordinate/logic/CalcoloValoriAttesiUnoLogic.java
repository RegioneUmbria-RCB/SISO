package it.webred.verificaCoordinate.logic;

import it.webred.verificaCoordinate.dto.request.VerificaCoordinateRequestDTO;
import it.webred.verificaCoordinate.dto.response.ClassamentiDTO;
import it.webred.verificaCoordinate.dto.response.ClassamentoDTO;
import it.webred.verificaCoordinate.dto.response.ClasseMaxCategoriaDTO;
import it.webred.verificaCoordinate.dto.response.ClassiMaxCategoriaDTO;
import it.webred.verificaCoordinate.dto.response.DatiAttesiDTO;
import it.webred.verificaCoordinate.dto.response.DatiAttesiResidenzialeDTO;
import it.webred.verificaCoordinate.dto.response.MappaleDTO;
import it.webred.verificaCoordinate.dto.response.VerificaCoordinateResponseDTO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

public class CalcoloValoriAttesiUnoLogic extends CalcoloValoriAttesiLogic {
	
	private static final Logger log = Logger.getLogger(CalcoloValoriAttesiUnoLogic.class.getName());
	
	private static final String SQL_CLASSI_MAX = "SELECT CATEGORIA, MAX(CLASSE) AS MAXCLASSE " +
								"FROM SITIUIU " +
								"WHERE DATA_FINE_VAL = TO_DATE('99991231','YYYYMMDD') " +
								"AND FOGLIO = ? " +
								"AND PARTICELLA = ? " +
								"AND CATEGORIA IN [IN_CATEGORIA] " +
								"GROUP BY CATEGORIA";
	
	private static final String SQL_VAL_COMM_MQ = "SELECT * " +
								"FROM DOCFA_VALORI " +
								"WHERE MICROZONA = ? " +
								"AND TIPOLOGIA_EDILIZIA = ? " +
								"AND STATO = ?";
	
	private static final String SQL_DOCFA_RAPPORTO = "SELECT VALORE " +
								"FROM SIT_TAB_PARAMS " +
								"WHERE NOME = 'DOCFA_RAPPORTO'";
	
	private final static String SQL_CLASSI_COMP = "SELECT * " +
								"FROM DOCFA_CLASSE " +
								"WHERE [ZONA] " +
								"CATEGORIA IN [IN_CATEGORIA] ";
	
	private final static String ZONA = "ZONA";
	private final static String CATEGORIA = "CATEGORIA";
	private final static String CLASSE = "CLASSE";
	private final static String TARIFFA = "TARIFFA";
	private final static String TARIFFA_EURO = "TARIFFA_EURO";
	private final static String VANISUP = "VANISUP";
	private final static String CONTEGGIO = "CONTEGGIO";
	private final static String RENDITA_COMPLESSIVA = "RENDITA_COMPLESSIVA";
	
	private static final DecimalFormat DF = new DecimalFormat();
	static {
		DF.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DF.setDecimalFormatSymbols(dfs);
	}
	
	private static final DecimalFormat DF_VALUTA = new DecimalFormat();
	static {
		DF_VALUTA.setGroupingUsed(false);
		DecimalFormatSymbols dfs = new DecimalFormatSymbols();
		dfs.setDecimalSeparator(',');
		DF_VALUTA.setDecimalFormatSymbols(dfs);
		DF_VALUTA.setMaximumFractionDigits(2);
	}
	
	public CalcoloValoriAttesiUnoLogic(String codEnte, Connection extConn) {
		super(codEnte, extConn);
	}
	
	public void calcola(VerificaCoordinateRequestDTO params, VerificaCoordinateResponseDTO resp) throws Exception {
		Connection conn = null;
		
		try {
			conn = getConnection();
			
			String foglio = params.getDatiCatastali().getFoglio();
			String particella = padding(params.getDatiCatastali().getMappale(), 5, '0', true);
			String consistenza = params.getResidenziale().getConsistenza();
			String superficie = params.getResidenziale().getSuperficie();
			String categoriaEdilizia = params.getResidenziale().getCategoriaEdilizia();
			String tipoIntervento = params.getResidenziale().getTipoIntervento();
			String ascensore = params.getResidenziale().getAscensoreOrMin3MFT();
			
			MappaleDTO mappale = resp.getElencoMappale().getMappale().get(0);
			
			Exception exc = null;
			if (categoriaEdilizia == null || categoriaEdilizia.equals("") || tipoIntervento == null || tipoIntervento.equals("")) {
				exc = new Exception("Devono essere valorizzati sia \"Categoria Edilizia\" che \"Tipo Intervento\"");
				log.error(exc.getMessage());
				setRespError(resp, exc.getMessage());
				return;
			}
			
			DatiAttesiDTO datiAttesi = new DatiAttesiDTO();
			
			double numConsistenza = 0;
			if (consistenza != null && !consistenza.equals("")) {
				try {
					numConsistenza = DF.parse(consistenza).doubleValue();
				} catch (Exception e) {
					exc = new Exception("\"Consistenza\" deve essere un numero decimale valido");
					log.error(exc.getMessage());
					setRespError(resp, exc.getMessage());
					return;
				}
			}		
			double numSuperficie = 0;
			if (superficie != null && !superficie.equals("")) {
				try {
					numSuperficie = DF.parse(superficie).doubleValue();
				} catch (Exception e) {
					exc = new Exception("\"Superficie\" deve essere un numero decimale valido");
					log.error(exc.getMessage());
					setRespError(resp, exc.getMessage());
					return;
				}
			}
			
			if (tipoIntervento.equalsIgnoreCase("Ristrutturazione")) {
				if (categoriaEdilizia.equalsIgnoreCase("Civile")) {
					ascensore = null;
				}
			} else {
				particella = null;
				ascensore = null;
			}

			//calcolo	
			List<HashMap<String, String>> datiZona = getDatiZona(conn, foglio);
			for (HashMap<String, String> datoZona : datiZona) {
				String zona = datoZona.get(ZC);
				String newMicrozona = datoZona.get(NEW_MICROZONA);
				
				DatiAttesiResidenzialeDTO datiAttesiResidenziale = new DatiAttesiResidenzialeDTO();				
				
				HashMap<String, String> hashClassiMax = null;
				double valoreCommMq = 0;
				double valoreComm = 0;
				double renditaMinima = 0;
				double tariffaVano = 0;
				//se sono nel caso di ristrutturazione calcolo le classi max per categoria in base al mappale
				 if (tipoIntervento.equalsIgnoreCase("Ristrutturazione")) {			 
					LinkedHashMap<String, String> hashClassi = getHashClassiMax(conn, foglio, categoriaEdilizia, particella);
					if (hashClassi != null && hashClassi.size() > 0) {
						ClassiMaxCategoriaDTO classi = new ClassiMaxCategoriaDTO();
						Iterator<String> it = hashClassi.keySet().iterator();
						while(it.hasNext()) {
							String key = it.next();
							String value = hashClassi.get(key);
							ClasseMaxCategoriaDTO classe = new ClasseMaxCategoriaDTO();
							classe.setCategoria(key);
							classe.setClasse(value);
							classi.getClasseMaxCategoria().add(classe);
						}
						datiAttesiResidenziale.setClassiMaxCategoria(classi);
					}			
				 }
				//ricavo il valore commerciale 
				String tipologiaEdilizia = null;
				String stato = null;
				if (categoriaEdilizia.equalsIgnoreCase("Civile")) {
					tipologiaEdilizia = "ABITAZIONI CIVILI";
				} else if (categoriaEdilizia.equalsIgnoreCase("Economica")) {
					tipologiaEdilizia = "ABITAZIONI TIPO ECONOMICO";
				}
				if (tipoIntervento.equalsIgnoreCase("Nuova Costruzione")) {
					stato = "Ottimo";
				} else if (tipoIntervento.equalsIgnoreCase("Ristrutturazione")) {
					stato = "Normale";
				}
				
				BigDecimal valoreCommMqBD = getValoreCommercialeMq(conn, padding(newMicrozona, 3, '0', true), tipologiaEdilizia,  stato);
				if (valoreCommMqBD != null) {
					valoreCommMq = valoreCommMqBD.doubleValue();
				}
				valoreComm = valoreCommMq * numSuperficie;
				if (valoreCommMq > 0) {
					String strValoreCommMq = DF_VALUTA.format(valoreCommMq);
					datiAttesiResidenziale.setValoreCommercialeMq(strValoreCommMq);
				}
				if (valoreComm > 0) {
					String strValoreComm = DF_VALUTA.format(valoreComm);
					datiAttesiResidenziale.setValoreCommerciale(strValoreComm);
				}
				
				double docfaRapporto = getDocfaRapporto(conn);
				double denom = docfaRapporto * 105;
				renditaMinima = denom == 0 ? 0 : (valoreComm / denom);
				
				tariffaVano = numConsistenza == 0 ? 0 : (renditaMinima / numConsistenza);
				
				if (renditaMinima > 0) {
					String strRenditaMinima = DF_VALUTA.format(renditaMinima);
					datiAttesiResidenziale.setRenditaMinima(strRenditaMinima);
				}
				if (tariffaVano > 0) {
					String strTariffaVano = DF_VALUTA.format(tariffaVano);
					datiAttesiResidenziale.setTariffaPerVano(strTariffaVano);
				}
				
				//ricavo la lista delle classi compatibili con l'intervento
				List<HashMap<String, String>> listaClassiCompatibili = getListaClassiComp(conn, zona, categoriaEdilizia, tipoIntervento, hashClassiMax, ascensore, new Double(numConsistenza), new Double(tariffaVano));
				if (listaClassiCompatibili != null && listaClassiCompatibili.size() > 0) {
					ClassamentiDTO classamenti = new ClassamentiDTO();
					for (HashMap<String, String> hmClassamento : listaClassiCompatibili) {
						ClassamentoDTO classamento = new ClassamentoDTO();
						classamento.setCategoria(hmClassamento.get(CATEGORIA));
						classamento.setClasse(hmClassamento.get(CLASSE));
						classamento.setTariffa(hmClassamento.get(TARIFFA_EURO));
						classamento.setRenditaComplessiva(hmClassamento.get(RENDITA_COMPLESSIVA));
						classamenti.getClassamento().add(classamento);
					}
					datiAttesiResidenziale.setClassamenti(classamenti);
				}
						
				datiAttesi.getDatiAttesiResidenziale().add(datiAttesiResidenziale);
			}
			
			mappale.setDatiAttesi(datiAttesi);
			
		} catch (Exception e) {
			log.error("Errore nel calcolo dei valori attesi (residenziale)", e);
			throw e;
		} finally {
			try {
				closeConnection(conn, false);
			} catch (Exception e) {
				log.error("Errore nel calcolo dei valori attesi (residenziale)", e);
			}
		}		
	}
	
	private LinkedHashMap<String, String> getHashClassiMax(Connection conn, String foglio, String categoriaEdilizia, String mappale) throws Exception {
		
		LinkedHashMap<String, String> hashClassiMax = null;
		
		String sql = SQL_CLASSI_MAX;
		if (categoriaEdilizia.equalsIgnoreCase("Civile")) {
			sql = sql.replace("[IN_CATEGORIA]", "('A01','A02','A07','A08')");
		} else if (categoriaEdilizia.equalsIgnoreCase("Economica")) {
			sql = sql.replace("[IN_CATEGORIA]", "('A03','A04')");
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, Integer.parseInt(foglio));
		ps.setString(2, mappale);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			if (hashClassiMax == null) {
				hashClassiMax = new LinkedHashMap<String, String>();
			}
			String categoria = rs.getString("CATEGORIA");
			String maxClasse = rs.getString("MAXCLASSE");
			if (categoria != null && !categoria.equals("") && maxClasse != null && !maxClasse.equals("")) {
				hashClassiMax.put(categoria, maxClasse);
			}
		}
		rs.close();
		ps.close();
		
		return hashClassiMax;
	}
	
	private BigDecimal getValoreCommercialeMq(Connection conn, String microzona, String tipologiaEdilizia, String stato) throws Exception {
		
		BigDecimal valoreCommerciale = null;
		
		String sql = SQL_VAL_COMM_MQ;
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setString(1, microzona);
		ps.setString(2, tipologiaEdilizia);
		ps.setString(3, stato);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			valoreCommerciale = rs.getBigDecimal("VAL_MED");
		}
		rs.close();
		ps.close();
		
		return valoreCommerciale;		
	}
	
	private double getDocfaRapporto(Connection conn) throws Exception {
		
		double rapportoD = 0;
		
		String sql = SQL_DOCFA_RAPPORTO;
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		String rapporto = "1";
		while (rs.next()) {
			String valore = rs.getString("VALORE");
			if (valore != null && !valore.equals("")) {
				rapporto = valore;
			}
		}
		rs.close();
		ps.close();

		rapportoD = new Double(rapporto).doubleValue();
		
		return rapportoD;
	}
	
	public List<HashMap<String, String>> getListaClassiComp(Connection conn,
															String zc, 
															String categoriaEdilizia, 
															String tipologiaIntervento, 
															HashMap<String, String> hashClassiMax, 
															String flgAscensore, 
															Double consistenza, 
															Double tariffa) throws Exception {
		
		List<HashMap<String, String>> listaClassi = new ArrayList<HashMap<String, String>>();
		
		String sql = SQL_CLASSI_COMP;
		if (zc != null && !zc.equals("") ){
			sql = sql.replace("[ZONA]", "ZONA = ? AND");
		}
		if (categoriaEdilizia.equalsIgnoreCase("Civile")) {
			sql = sql.replace("[IN_CATEGORIA]", "('A01','A02','A07','A08')");
		} else if (categoriaEdilizia.equalsIgnoreCase("Economica")) {
			sql = sql.replace("[IN_CATEGORIA]", "('A03','A04')");
		}
		PreparedStatement ps = conn.prepareStatement(sql);
		if (zc != null && !zc.equals("") ){
			ps.setString(1, zc);
		}
		ResultSet rs = ps.executeQuery();
		List<HashMap<String, Object>> lstObj = new ArrayList<HashMap<String, Object>>();
		while (rs.next()) {
			HashMap<String, Object> objs = new HashMap<String, Object>();
			objs.put(ZONA, rs.getObject("ZONA"));
			objs.put(CATEGORIA, rs.getObject("CATEGORIA"));
			objs.put(CLASSE, rs.getObject("CLASSE"));
			objs.put(TARIFFA, rs.getObject("TARIFFA"));
			objs.put(TARIFFA_EURO, rs.getObject("TARIFFA_EURO"));
			objs.put(VANISUP, rs.getObject("VANISUP"));
			objs.put(CONTEGGIO, rs.getObject("CONTEGGIO"));
			lstObj.add(objs);
		}
		rs.close();
		ps.close();

		if (lstObj != null && lstObj.size() > 0) {				
			for (int i = 0; i < lstObj.size(); i++) {
				HashMap<String, Object> objs = (HashMap<String, Object>)lstObj.get(i);					
				String categoria = (String)objs.get(CATEGORIA);
				String classe = (String)objs.get(CLASSE);
				BigDecimal tariffaEuro = (BigDecimal)objs.get(TARIFFA_EURO);

				//se nuova costruzione e abitazioni economiche, allora scarto categoria A4
				if (tipologiaIntervento != null && tipologiaIntervento.equalsIgnoreCase("Nuova Costruzione") && 
				categoriaEdilizia != null && categoriaEdilizia.equalsIgnoreCase("Economica")){
					if (categoria != null && categoria.equalsIgnoreCase("A04"))
						continue;
				}
					
				//se ristrutturazione e abitazioni economiche e flgAscensore = S, allora scarto categoria A4
				if (tipologiaIntervento != null && tipologiaIntervento.equalsIgnoreCase("Ristrutturazione") && 
				categoriaEdilizia != null && categoriaEdilizia.equalsIgnoreCase("Economica") && 
				flgAscensore != null && flgAscensore.equalsIgnoreCase("S")){
					if (categoria != null && categoria.equalsIgnoreCase("A04"))
						continue;
				}					
					
				//se ristrutturazione scartare le categorie per cui la classe è minore di quella massima per la categoria
				if (tipologiaIntervento != null && tipologiaIntervento.equalsIgnoreCase("Ristrutturazione")){
					if (hashClassiMax != null && hashClassiMax.get(categoria) != null && !hashClassiMax.get(categoria).equals("")){
						if (Integer.valueOf(classe).intValue() < Integer.valueOf((String)hashClassiMax.get(categoria)).intValue())
							continue;
					}
				}
				
				double tariffaEuroD = 0;
				double tariffaCalc = 0;
				if (tariffaEuro != null){
					tariffaEuroD = tariffaEuro.doubleValue();
				}
				if (tariffa != null) {
					tariffaCalc= tariffa.doubleValue();
				}
							
				//se sono nel caso di nuova costruzione verifico che la tariffa sia compresa tra il 90% e il 110% della tariffa per vano calcolata
				//se sono nel caso di ristrutturazione verifico  che la tariffa sia compresa tra il 90% e il 110% della tariffa per vano calcolata; 
				//oppure, se la classe è = classeMax per categoria, allora la tariffa può essere anche superiore al 110%
				boolean add = false;
				if (tipologiaIntervento != null && tipologiaIntervento.equalsIgnoreCase("Nuova Costruzione")) {
					if (tariffaEuroD > (tariffaCalc * 0.9) && tariffaEuroD < (tariffaCalc * 1.1)) {							
						add = true;	
					}
				} else if (tipologiaIntervento != null && tipologiaIntervento.equalsIgnoreCase("Ristrutturazione")) {
					if ((tariffaEuroD > (tariffaCalc * 0.9) && tariffaEuroD < (tariffaCalc * 1.1)) || 
					(tariffaEuroD > (tariffaCalc * 1.1) && hashClassiMax != null && hashClassiMax.get(categoria) != null && (Integer.valueOf(classe).intValue() == Integer.valueOf((String)hashClassiMax.get(categoria)).intValue()))) {
						add = true;
					}
				}
				if (add) {
					HashMap<String, String> classamento = new HashMap<String, String>();
					classamento.put(ZONA, (String)objs.get(ZONA));
					classamento.put(CATEGORIA, categoria);
					classamento.put(CLASSE, classe);
					classamento.put(TARIFFA_EURO, DF_VALUTA.format(tariffaEuroD));
					if (tariffaEuro != null){
						double tariffaPerVanoD = tariffaEuro.doubleValue();
						if (consistenza != null){
							double consistenzaD = consistenza.doubleValue();
							double renditaComplessivaD = tariffaPerVanoD * consistenzaD;
							classamento.put(RENDITA_COMPLESSIVA, DF_VALUTA.format(renditaComplessivaD));
						}
					}						
					listaClassi.add(classamento);
				}
			}
		}

		return listaClassi;		
	}

}
