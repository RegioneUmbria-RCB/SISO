package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.DatiEsterniSoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ConfiguratoreDatiEsterniDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO.DatiEsterni;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO.RigaDatiEsterni;
import it.webred.cs.csa.ejb.dto.ParametriDatiEsterniSoggettoDTO;
import it.webred.cs.data.model.CsADatiEsterni;
import it.webred.cs.data.model.CsASoggettoDatiEsterni;
import it.webred.cs.data.model.CsCfgBelfiore;
import it.webred.ct.support.validation.ValidationStateless;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@Interceptors(ValidationStateless.class)
public class AccessTableDatiEsterniSoggettoSessionBean extends CarSocialeBaseSessionBean implements AccessTableDatiEsterniSoggettoSessionBeanRemote {

	private static final long serialVersionUID = -5262658293732772325L;
	
	@Autowired
	private DatiEsterniSoggettoDAO dao;
	
	private String serializzaJson(ConfiguratoreDatiEsterniDTO configuratore ) {
		String result ="";
		
		if(configuratore != null) {
			
			ObjectMapper mapper = new ObjectMapper();
			try {
				result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(configuratore);
			} catch (JsonProcessingException e) {
				logger.error(e.getMessage(), e);
			}
		}
			
		return result;
	}
	
	private ConfiguratoreDatiEsterniDTO deserializzaJson(String configuratore ) {
		ConfiguratoreDatiEsterniDTO result  = null;
		
		if(configuratore != null) {
			
			ObjectMapper mapper = new ObjectMapper();
			 
				try {
					result = mapper.readValue(configuratore, ConfiguratoreDatiEsterniDTO.class);
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			 
		}
			
		return result;
	}
	
	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void aggiungiContenutiPerSoggetto(BaseDTO bdto) {
		Set<DatiEsterniSoggettoDTO> soggetti = (Set<DatiEsterniSoggettoDTO>) bdto.getObj();
		String nomeFile = (String) bdto.getObj2();
		byte[] contenuti = (byte[]) bdto.getObj3();
		String tipologiaFile = (String) bdto.getObj4();
		ConfiguratoreDatiEsterniDTO configDatiEsterni =  bdto.getObj5() != null ? (ConfiguratoreDatiEsterniDTO) bdto.getObj5() : null;
		//Serializzo il configuratore che mi servirà successivamente per l'analisi del risultato
		String confJson = serializzaJson(configDatiEsterni);
	
		// crea nel db i dati esterni - per ipotesi, non esistono dati pregressi da aggiornare
		CsADatiEsterni datiEsterni = writeDatiEsterni(nomeFile, contenuti, tipologiaFile, confJson);
		
		
		// creazione dei soggetti a cui associare i dati esterni

		// il dato passato, generalmente, contiene più occorrenze dello stesso CF, perciò occorre trovare un
		// sottoinsieme proprio
		Set<String> cfSoggetti = new HashSet<String>();
		for (DatiEsterniSoggettoDTO dto : soggetti) {
			cfSoggetti.add(dto.getCodiceFiscale());
		}

		// invariante : cfSoggetti contiene CF unici

		// si determinano i soggetti eventualmente da inserire nel db, l'obiettivo è comunque di avere una ST che mette
		// in relazione il CF del soggetto con la corrispondente entità. Questo perché quando si scriveranno le
		// occorrenze delle relazioni tra soggetto e contenuti del file sarà necessario conoscere l'id del soggetto
		// stesso
		Map<String, CsASoggettoDatiEsterni> mapCfToSoggetto = new HashMap<String, CsASoggettoDatiEsterni>();

		for (String cf : cfSoggetti) { // per ogni soggetto fornito in ingresso
			// verifica se è già presente nel db
			CsASoggettoDatiEsterni sda = dao.getSoggettoByCf(cf);
			if (sda == null) { // non c'è ==> lo inserisce
				CsASoggettoDatiEsterni nuovoSoggetto = new CsASoggettoDatiEsterni();
				nuovoSoggetto.setCf(cf);
				dao.save(nuovoSoggetto);
				sda = nuovoSoggetto;
			}
			mapCfToSoggetto.put(cf, sda);
		}

		try {
//			Map<String, Set<String>> cfPerEnte = new HashMap<String, Set<String>>();
//			Map<String,String> cfStatoDomanda = new HashMap<String, String>();
//			Map<String,String> cfCodPrestazione = new HashMap<String, String>();
			 
			Map<String, Set<ParametriDatiEsterniSoggettoDTO>> cfPerEnte = new HashMap<String, Set<ParametriDatiEsterniSoggettoDTO>>();
		

			for (DatiEsterniSoggettoDTO dto : soggetti) {
				String codiceEnte = dto.getCodiceEnte();
				if (!cfPerEnte.containsKey(codiceEnte)) {
					cfPerEnte.put(codiceEnte, new HashSet<ParametriDatiEsterniSoggettoDTO>());
				}
				cfPerEnte.get(codiceEnte).add(dto.getParametriEsterni());
				 
			}
			for (Map.Entry<String, Set<ParametriDatiEsterniSoggettoDTO>> entry : cfPerEnte.entrySet()) {
				for (ParametriDatiEsterniSoggettoDTO parametri : entry.getValue()) {
					CsASoggettoDatiEsterni sda = mapCfToSoggetto.get(parametri.getCodiceFiscale());
					String codiceEnte = entry.getKey();
					//se presente il codice nella tabella Belfiore, viene estratto..
					 
					CsCfgBelfiore csCfgBelfiore = dao.findBelfioreByCodTerritorialeId(codiceEnte);
					if(csCfgBelfiore != null) {
						codiceEnte = csCfgBelfiore.getCodBelfiore();
					}
//					String codPrestazione = cfCodPrestazione.get(cf);
//					String statoDomanda = cfStatoDomanda.get(cf);
					dao.save(sda, datiEsterni, codiceEnte, parametri);
				}
			}
			
//			
//			for (DatiEsterniSoggettoDTO dto : soggetti) {
//				String codiceEnte = dto.getCodiceEnte();
//				if (!cfPerEnte.containsKey(codiceEnte)) {
//					cfPerEnte.put(codiceEnte, new HashSet<String>());
//				}
//				cfPerEnte.get(codiceEnte).add(dto.getCodiceFiscale());
//				if(!cfStatoDomanda.containsKey(dto.getCodiceFiscale())) {
//					cfStatoDomanda.put(dto.getCodiceFiscale(), dto.getStatoDomanda());
//				}
//				if(!cfCodPrestazione.containsKey(dto.getCodiceFiscale())) {
//					cfCodPrestazione.put(dto.getCodiceFiscale(), dto.getCodicePrestazione());
//				}
//			}

//			for (Map.Entry<String, Set<String>> entry : cfPerEnte.entrySet()) {
//				for (String cf : entry.getValue()) {
//					CsASoggettoDatiEsterni sda = mapCfToSoggetto.get(cf);
//					String codiceEnte = entry.getKey();
//					//se presente il codice nella tabella Belfiore, viene estratto..
//					 
//					CsCfgBelfiore csCfgBelfiore = dao.findBelfioreByCodTeeritorialeId(codiceEnte);
//					if(csCfgBelfiore != null) {
//						codiceEnte = csCfgBelfiore.getCodBelfiore();
//					}
//					String codPrestazione = cfCodPrestazione.get(cf);
//					String statoDomanda = cfStatoDomanda.get(cf);
//					dao.save(sda, datiEsterni, codiceEnte, codPrestazione, statoDomanda);
//				}
//			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	private CsADatiEsterni writeDatiEsterni(String nomeFile, byte[] contenuti, String tipologia, String configDatiEsterni ) {
		CsADatiEsterni datiEsterni = new CsADatiEsterni();
		datiEsterni.setNomeFile(nomeFile);
		datiEsterni.setContenuti(contenuti);
		datiEsterni.setTipologia(tipologia);
		datiEsterni.setConfigurazioneFile(configDatiEsterni); 
		datiEsterni.setDataImportazione(new Date());
		dao.save(datiEsterni);
		return datiEsterni;
	}
	private String getCellValueAsString(Cell cell1) {
		 if( cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			 if(HSSFDateUtil.isCellDateFormatted(cell1)) {
				 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			   	 Date date = cell1.getDateCellValue();
		         return df.format(date);
			 }
			 BigDecimal big = new BigDecimal(cell1.getNumericCellValue());
			 big = big.setScale(0, RoundingMode.HALF_UP);
			 String rounded = big.toString();
			 return rounded;
		 }
		 else if(cell1.getCellType() == Cell.CELL_TYPE_STRING) {
			 return  cell1.getStringCellValue().trim();
		 }
		 return null;
	}
	private Map<String, Integer> getMapsValues(Sheet sheet1) {
		

		Map<String, Integer> map = new HashMap<String,Integer>(); //Create map
		
		Row row = sheet1.getRow(0); //Get first row
		//following is boilerplate from the java doc
		short minColIx = row.getFirstCellNum(); //get the first column index for a row
		short maxColIx = row.getLastCellNum(); //get the last column index for a row
		for(short colIx=minColIx; colIx<maxColIx; colIx++) { //loop from first to last index
		    Cell cell = row.getCell(colIx); //get the cell
			map.put(cell.getStringCellValue(),cell.getColumnIndex()); //add the cell contents (name of column) and cell index to the map
		}
		return map;
	}
	
	private String handleCell(String nomeCampo, Cell cell, int cellType) {
		 String cellValueAsString ="";
		 
        if (Cell.CELL_TYPE_NUMERIC == cellType)
        	cellValueAsString = getCellValueAsString(cell);
        else if (Cell.CELL_TYPE_STRING == cellType) {
        	cellValueAsString = getCellValueAsString(cell);
        	//forzatura per recuperare la data della domanda nel formato corretto (VALE GEPI)
			if(nomeCampo.toUpperCase().contains("DATA PRESENTAZIONE DOMANDA")){
				DateFormat df = null;
				Date dateOut = null;

					  df = new SimpleDateFormat("MM/dd/yyyy");
					  try {
						dateOut = df.parse(cellValueAsString);
						
					} catch (ParseException e) {
						logger.error(e.getMessage(), e);
					}

				if(dateOut != null) {
					df = new SimpleDateFormat("dd/MM/yyyy");
					cellValueAsString = df.format(dateOut); 
				}
			
			}
		 			
        }
        else if (Cell.CELL_TYPE_BOOLEAN == cellType)
        	cellValueAsString = Boolean.valueOf(cell.getBooleanCellValue()).toString();
        else if (Cell.CELL_TYPE_BLANK == cellType)
        	cellValueAsString = "[BLANK]";
        else if (Cell.CELL_TYPE_FORMULA == cellType)
        	cellValueAsString = NumberFormat.getNumberInstance().format(cell.getNumericCellValue());
            //handleCell(cell, cell.getCachedFormulaResultType());
        else if(Cell.CELL_TYPE_ERROR == cellType)
        	cellValueAsString = Byte.valueOf(cell.getErrorCellValue()).toString();
        else 
            System.out.print("Unknown cell type " + cellType);
        return cellValueAsString;
   }
	
	private DatiEsterniSoggettoViewDTO getDatiEsterni(String tipologia, String nomeFile, Date dataImportazione, String codFiscale, String codEnte, String colCfNome, 
									String colEnteNome,  Sheet sheet, Map<Integer, String> nomiCampi ) {
		
		
		int totalRows = sheet.getPhysicalNumberOfRows();
		Map<String, Integer> mapsValori = getMapsValues(sheet);
		DatiEsterniSoggettoViewDTO viewDto = new DatiEsterniSoggettoViewDTO();
		viewDto.setNomeFile(nomeFile);
		viewDto.setDataImportazione(dataImportazione);
		viewDto.setTipologia(tipologia);
	 	if(totalRows > 0 )
		for(int x = 1; x<=totalRows; x++){
			 DatiEsterniSoggettoDTO dto = null;
			 String cfSoggetto = null;
			 String codiceEnte = null;
		 	 String codiceEnteNew = "";
		 	 
			 Row dataRow = sheet.getRow(x); //get row 1 to row n (rows containing data)
			 if(dataRow == null)
				 break;	
			 boolean corrispondenzaCodiceFiscale = false; //r.getCell(0).getStringCellValue().equalsIgnoreCase(codiceFiscale);
			 boolean corrispondenzaCodiceEnte = false;
			 if(colCfNome != null && mapsValori.containsKey(colCfNome)) {
				  
				 int idxForColumnPrestazione = mapsValori.get(colCfNome); //get the column index for the column with header name = "Column1"
					if(idxForColumnPrestazione > 0) {
					 Cell cell1 = dataRow.getCell(idxForColumnPrestazione); //Get the cells for each of the indexes
					 cfSoggetto = cell1.getStringCellValue().trim();
					
					}
					 corrispondenzaCodiceFiscale = cfSoggetto.equalsIgnoreCase(codFiscale);
			 }
			 
			
			
			if(colEnteNome != null && mapsValori.containsKey(colEnteNome)) {
				int idxForColumnEnte = mapsValori.get(colEnteNome); //get the column index for the column with header name = "Column1"
				if(idxForColumnEnte > 0) {
				 Cell cell1 = dataRow.getCell(idxForColumnEnte); //Get the cells for each of the indexes
				 	codiceEnte = getCellValueAsString(cell1);
				 	
				 	 if(codiceEnte != null) {
				 		String[] codiciEnte = codiceEnte.split(",");
				 		for(String cod : codiciEnte) {
				 			CsCfgBelfiore csCfgBelfiore  = dao.findBelfioreByCodTerritorialeId(cod);
				 			String codBelfiore = csCfgBelfiore != null && !StringUtils.isBlank(csCfgBelfiore.getCodBelfiore()) ? csCfgBelfiore.getCodBelfiore() : cod;
				 			codiceEnteNew = codiceEnteNew.concat(codBelfiore);
					 		codiceEnteNew = codiceEnteNew.concat(",");
				 		}
				 		
				 	 }
				}
				 corrispondenzaCodiceEnte = codiceEnteNew.contains(codEnte);
			}
			
			if (corrispondenzaCodiceFiscale && corrispondenzaCodiceEnte) {
				 
				RigaDatiEsterni rigaDatiEsterni = new RigaDatiEsterni();
				for (int colonna = 0; colonna < dataRow.getLastCellNum(); colonna++) {
					if (colonna < 2  && (tipologia  == null || tipologia.equals(""))) { // per le marche le importazioni restano standard..
						
						continue;
					}
					String nomeCampo = nomiCampi.get(colonna);
					if (nomeCampo == null) {
						continue;
					}
					String cellValueAsString = "";
					Cell c = dataRow.getCell(colonna);
					if (c == null) {
						continue;
					}
					 
					cellValueAsString = this.handleCell(nomeCampo, c, c.getCellType());

					if(nomeCampo.equalsIgnoreCase(colEnteNome))
						cellValueAsString = strip(codiceEnteNew);
					rigaDatiEsterni.addDatiEsterni(new DatiEsterni(nomeCampo, cellValueAsString));
				}
				viewDto.addRigaDatiEsterni(rigaDatiEsterni);
			
			}
		}
		return viewDto;	 
	}
	private String strip(String s) {
	    if(s== null)
	    	return s;
		if (s.endsWith(":") || s.endsWith(",")){
	        s = s.substring(0, s.length()-1);
	    }
	    return s;
	}
	private DatiEsterniSoggettoViewDTO getDatiEsterni(String tipologia , String nomeFile, Date dataImportazione, Iterator<Row> rows, String codiceFiscale, String codiceEnte, Map<Integer, String> nomiCampi ) {
		DatiEsterniSoggettoViewDTO viewDto = new DatiEsterniSoggettoViewDTO();
		viewDto.setNomeFile(nomeFile);
		viewDto.setTipologia(tipologia);
		
		viewDto.setDataImportazione(dataImportazione);
		while (rows.hasNext()) {
			Row r = rows.next();
			boolean corrispondenzaCodiceFiscale = r.getCell(0).getStringCellValue().equalsIgnoreCase(codiceFiscale);
			boolean corrispondenzaCodiceEnte = r.getCell(1).getStringCellValue().contains(codiceEnte);
			if (corrispondenzaCodiceFiscale && corrispondenzaCodiceEnte) {
				RigaDatiEsterni rigaDatiEsterni = new RigaDatiEsterni();
				for (int colonna = 0; colonna < r.getLastCellNum(); colonna++) {
					if (colonna < 2) {
						continue;
					}
					String nomeCampo = nomiCampi.get(colonna);
					if (nomeCampo == null) {
						continue;
					}
					String cellValueAsString = "";
					Cell c = r.getCell(colonna);
					if (c == null) {
						continue;
					}
					switch (c.getCellType()) {
					case Cell.CELL_TYPE_BLANK:
						cellValueAsString = "[BLANK]";
						break;
					case Cell.CELL_TYPE_BOOLEAN:
						cellValueAsString = Boolean.valueOf(c.getBooleanCellValue()).toString();
						break;
					case Cell.CELL_TYPE_ERROR:
						cellValueAsString = Byte.valueOf(c.getErrorCellValue()).toString();
						break;
					case Cell.CELL_TYPE_FORMULA:
						cellValueAsString = "[FORMULA]";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						cellValueAsString = Double.valueOf(c.getNumericCellValue()).toString();
						break;
					case Cell.CELL_TYPE_STRING:
						cellValueAsString = c.getStringCellValue();
						break;
					}
					rigaDatiEsterni.addDatiEsterni(new DatiEsterni(nomeCampo, cellValueAsString));
				}
				viewDto.addRigaDatiEsterni(rigaDatiEsterni);
			} // -!- la riga è inerente al codice fiscale del soggetto in esame
		}
		return viewDto;
	}
	
	
	@Override
	public List<DatiEsterniSoggettoViewDTO> getDatiEsterniSoggetto(BaseDTO dto) {
		String codiceFiscale = (String)dto.getObj();
		String codiceEnte = dto.getEnteId();
		List<DatiEsterniSoggettoViewDTO> viewDtoList = new ArrayList<DatiEsterniSoggettoViewDTO>();
		List<CsADatiEsterni> datiEsterni = dao.getDatiEsterniByCfSoggetto(codiceFiscale, codiceEnte);
		try {
			for (CsADatiEsterni de : datiEsterni) {
				DatiEsterniSoggettoViewDTO viewDto = new DatiEsterniSoggettoViewDTO();
				viewDto.setNomeFile(de.getNomeFile());
				viewDto.setDataImportazione(de.getDataImportazione());

				Workbook wb = new XSSFWorkbook(new ByteArrayInputStream(de.getContenuti()));
				Sheet sheet = wb.getSheetAt(0);
				Iterator<Row> rows = sheet.rowIterator();
				Row intestazione = rows.next();
				Map<Integer, String> nomiCampi = new HashMap<Integer, String>();
				for (int colonna = 0; colonna < intestazione.getLastCellNum(); colonna++) {
					if (colonna < 2  && StringUtils.isBlank(de.getTipologia())) { // per le marche le importazioni restano standard..
						continue;
					}
					Cell c = intestazione.getCell(colonna);
					if (c == null) {
						// sembrano esserci file che hanno colonne con intestazioni o contenuti vuoti o nulli. Queste
						// non vanno considerate nei dati da passare al client.
						continue;
					}
					nomiCampi.put(colonna, this.getCellValueAsString(c)); // getCellValueAsString(c ));
					
				}
				
				if(!StringUtils.isBlank(de.getTipologia())) {
					// Composizione del dato nel nuovo formato...
					String nomeColonnaCF = null;
					String nomeColonnaEnte = null;
					ConfiguratoreDatiEsterniDTO  configuratoreFile = null;
					if(!StringUtils.isBlank(de.getConfigurazioneFile()))
						configuratoreFile = deserializzaJson(de.getConfigurazioneFile());
						if(configuratoreFile!=null){
							nomeColonnaCF = configuratoreFile.getNomeColonnaCF();
							nomeColonnaEnte = configuratoreFile.getNomeColonnaEnte();
						}
						viewDto= getDatiEsterni(de.getTipologia(), 
								de.getNomeFile(),
								de.getDataImportazione(), 
								codiceFiscale,
								codiceEnte,
								nomeColonnaCF, 
								nomeColonnaEnte,
								sheet,
								nomiCampi);
						
				}else {
						viewDto = getDatiEsterni(de.getTipologia(),
								de.getNomeFile(),
								de.getDataImportazione(),
								rows, 
								codiceFiscale, 
								codiceEnte, 
								nomiCampi);	
					}			
				viewDtoList.add(viewDto);
			} // !- ciclo for sulle entità
		} catch (IOException e) { // dalla creazione dell'inputStream
			logger.error(e.getMessage(), e);
		}
		return viewDtoList;
	}
	
	public Boolean existsPrestazione(BaseDTO dto){
		boolean trovato = false;
		List<DatiEsterniSoggettoViewDTO> listaDatiEsterni = this.getDatiEsterniSoggetto(dto);
		if(listaDatiEsterni  != null){
			for(DatiEsterniSoggettoViewDTO de : listaDatiEsterni) {
				if(de.getTipologia()!=null && de.getTipologia().contains( "Prestazione")) {
					trovato = true;
					break;
				}
			}
		}
		return trovato;
	}
}
