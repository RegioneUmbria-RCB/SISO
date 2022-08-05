package it.webred.cs.csa.web.bean.timertask;
import org.apache.commons.io.FilenameUtils;

import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.ConfiguratoreDatiEsterniDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoDTO;
import it.webred.ct.config.model.AmKeyValueExt;
import it.webred.ct.config.parameters.ParameterService;
import it.webred.ct.config.parameters.dto.ParameterSearchCriteria;
import it.webred.ejb.utility.ClientUtility;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

class DatiEsterniSoggettoTimerTaskHelper {

	private ParameterService paramService;
	private AccessTableDatiEsterniSoggettoSessionBeanRemote datiEsterniEjb;
	private Path importBaseDir;
	private String enteId;
	private String subPath="";
	
	private final FileFilter excelFormatFileFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isFile() && (pathname.getName().endsWith(".xls") || pathname.getName().endsWith(".xlsx"));
		}
	};
	
	private final FileFilter propertiesFormatFileFilter = new FileFilter() {

		@Override
		public boolean accept(File pathname) {
			return pathname.isFile() && (pathname.getName().endsWith(".properties"));
		}
	};

	private static final String AM_KEY_CONF = "smartwelfare.dir.importazione.datiesterni.base";

	private  String SUBDIR_IN = "in";
	private  String SUBDIR_OUT = "out";
	private  String SUBDIR_ERR = "err";
	private  String SUBDIR_CONF = "conf";
	
	private static final String ERR_NO_BASEDIR = "impossibile identificare la directory principale per l'importazione";

	private static final Logger log = Logger.getLogger("carsociale_timertask.log");
	
	DatiEsterniSoggettoTimerTaskHelper(String enteId) throws Exception {
		datiEsterniEjb = (AccessTableDatiEsterniSoggettoSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB",
				"AccessTableDatiEsterniSoggettoSessionBean");
		paramService = (ParameterService) ClientUtility.getEjbInterface("CT_Service", "CT_Config_Manager", "ParameterBaseService");

		this.enteId = enteId;
		this.subPath = "\\".concat(enteId);
		
		ParameterSearchCriteria criteria = new ParameterSearchCriteria();
		criteria.setKey(AM_KEY_CONF);
		criteria.setComune(null);
		criteria.setSection(null);

		AmKeyValueExt amKey = paramService.getAmKeyValueExt(criteria);
		if (amKey != null) {
			importBaseDir = Paths.get(amKey.getValueConf());
		} else {
			log.error(ERR_NO_BASEDIR);
			throw new Exception(ERR_NO_BASEDIR);
		}
	}

	void importaDatiEsterni() {
		
		Path inputDir = importBaseDir.resolve(SUBDIR_IN.concat(subPath));

		File[] sourceFiles = inputDir.toFile().listFiles(excelFormatFileFilter);

		if (sourceFiles != null && sourceFiles.length > 0) {
			processBatch(sourceFiles);
		} else {
			log.warn("nessun file da elaborare");
		}
	}
	
 

	private void processBatch(File[] files) {

		log.info("file trovati : " + files.length);
		for (File f : files) {
			Path filePath = f.toPath();
			
			log.debug("elaborazione di : " + filePath.toString());
			DatiEsterniSoggettoDTO dto = processFile(f);
			if (dto == null) {
				log.warn("nessun risultato ottenuto per l'elaborazione del file");
				moveToSubdir(filePath, SUBDIR_ERR.concat(subPath), "file non correttamente spostato nella directory di errore");
			} else {

				@SuppressWarnings("unchecked")
				Set<DatiEsterniSoggettoDTO> dtoSet = (Set<DatiEsterniSoggettoDTO>) dto.getObj();

				log.info("trovato/i <" + dtoSet.size() + "> dati da inviare");
				byte[] fileContents = (byte[]) dto.getObj2();
				String tipoFile = 	(String) dto.getObj3();
				ConfiguratoreDatiEsterniDTO configDatiEsterni =  dto.getObj4() != null ? (ConfiguratoreDatiEsterniDTO) dto.getObj4() : null;
				try {
					BaseDTO bdto = new BaseDTO();
					bdto.setEnteId(enteId);
					bdto.setObj(dtoSet);
					bdto.setObj2(filePath.getFileName().toString());
					bdto.setObj3(fileContents);
					bdto.setObj4(tipoFile); //TipoFile Se Presente
					bdto.setObj5(configDatiEsterni);
					datiEsterniEjb.aggiungiContenutiPerSoggetto(bdto);
				} catch (Exception e) {
					log.error("errore nell'importazione del file", e);
					moveToSubdir(filePath, SUBDIR_ERR.concat(subPath), "file non correttamente spostato nella directory di errore");
				}
				moveToSubdir(filePath, SUBDIR_OUT.concat(subPath), "file non correttamente spostato nella directory di destinazione");
			}
		}
	}

	private void moveToSubdir(Path sourceFile, String subdir, String errorMessage) {
		Path errorDestination = Paths.get(importBaseDir.toString(), subdir, sourceFile.getFileName().toString());
		try {
			Files.move(sourceFile, errorDestination, StandardCopyOption.REPLACE_EXISTING);
			String fileNameWithOutExt = FilenameUtils.removeExtension(sourceFile.getFileName().toString());
			try{
				Path propPath =  importBaseDir.resolve(SUBDIR_CONF.concat(subPath).concat("\\").concat(fileNameWithOutExt).concat(".properties"));
			    File fileProp =	propPath.toFile();
			    if(fileProp.exists() && !fileProp.isDirectory()){
				    Path filePath = fileProp.toPath();
					Path destinationFile =  Paths.get(importBaseDir.toString(), subdir,filePath.getFileName().toString());
					if(filePath != null) {
						Files.move(filePath, destinationFile, StandardCopyOption.REPLACE_EXISTING);
					}
			    }
			}catch(Exception ex) {
				log.error(errorMessage, ex);
			}
		 
		} catch (IOException e) {
			log.error(errorMessage, e);
		}

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

	private DatiEsterniSoggettoDTO processFile(File f) {
		DatiEsterniSoggettoDTO dto = null;
		InputStream fileIn = null;
		Workbook wb = null;
		byte[] fileContents;
		try {
			fileIn = new FileInputStream(f);
			wb = new XSSFWorkbook(fileIn);
			fileContents = Files.readAllBytes(f.toPath());
		} catch (Exception e) {
			log.error("errore nell'elaborazione del file", e);
			return null;
		} finally {
			if (fileIn != null)
				try {
					fileIn.close();
					fileIn = null;
				} catch (IOException e) { // "botta silente"
				}
		}

		Sheet mainSheet = wb.getSheetAt(0);
		if (mainSheet == null) {
			return null;
		} else {
			
			Path inputDir = importBaseDir.resolve(SUBDIR_CONF.concat(subPath));
			String absPath = inputDir.toAbsolutePath().toString();
				
			String fileNameWithOutExt = FilenameUtils.removeExtension(f.getName());
			String confFile = absPath.concat("\\".concat(fileNameWithOutExt).concat(".properties"));
			String newPath = confFile.replaceAll("\\\\", "/");
			String protPath = "file:///" + newPath;
			URL url ;
			ConfiguratoreDatiEsterniDTO confDatiEsterniDTO = new ConfiguratoreDatiEsterniDTO();
			String tipoFile = null;
			InputStream is = null;
			
			File properties = new File(newPath);
			boolean exists = properties.exists();
			boolean isDir = properties.isDirectory();
			if(exists && !isDir) { 
				try{
					 url= new URL(protPath);
					 Properties props = new Properties();
					 is = url.openStream();
					 props.load(is);
					 confDatiEsterniDTO.setNomeColonnaTipoPrestazione(props.getProperty("nome.colonna.tipo.prestazione"));
					 confDatiEsterniDTO.setNomeColonnaCodPrestazione(props.getProperty("nome.colonna.codice.prestazione"));
	
					 confDatiEsterniDTO.setNomeColonnaCF(props.getProperty("nome.colonna.codice.fiscale"));
					 confDatiEsterniDTO.setNomeColonnaEnte(props.getProperty("nome.colonna.ente"));
				     confDatiEsterniDTO.setNomeColonnaIndirizzo(props.getProperty("nome.colonna.indirizzo"));
	
					 confDatiEsterniDTO.setNomeColonnaDataApertura(props.getProperty("nome.colonna.data.apertura"));
					 confDatiEsterniDTO.setNomeColonnaDataChiusura(props.getProperty("nome.colonna.data.chiusura"));
					 confDatiEsterniDTO.setNomeColonnaServizioStato(props.getProperty("nome.colonna.servizio.stato"));
					 confDatiEsterniDTO.setNomeColonnaEntitaServizio(props.getProperty("nome.colonna.servizio.entita"));
					 
					 tipoFile = props.getProperty("tipo.file");
				} catch (Exception e) {
					log.warn(e.getMessage(), e);
				}finally {
					if(is!=null){
						 try {
							is.close();
						} catch (IOException e) {
							log.warn(e.getMessage(), e);
						}
					}
				} 
			}
			
			Set<DatiEsterniSoggettoDTO> codiciFiscali = processWorkbookSheetByCell(mainSheet, confDatiEsterniDTO);
			if(codiciFiscali!=null){
				dto = new DatiEsterniSoggettoDTO();
				dto.setObj(codiciFiscali);
				dto.setObj2(fileContents);
				dto.setObj3(tipoFile);
				dto.setObj4(confDatiEsterniDTO);
			}
		}
		return dto;
	}

	private String getCellValueAsString(Cell cell1) {
		if(cell1!=null) { 
			if(cell1.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				 
				 if(HSSFDateUtil.isCellDateFormatted(cell1)) {
					 DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				   	 Date date = cell1.getDateCellValue();
			         return df.format(date);
				 }
				 
				 BigDecimal big = new BigDecimal(cell1.getNumericCellValue());
				 big = big.setScale(0, RoundingMode.HALF_UP);
				 String rounded = big.toString();
				 return rounded;
			 
			 }else if(cell1.getCellType() == Cell.CELL_TYPE_STRING) {
				 return  cell1.getStringCellValue().trim();
			 }
		 }
		 return null;
	}
	
	@SuppressWarnings("unchecked")
	private Set<DatiEsterniSoggettoDTO> processWorkbookSheetByCell(Sheet mainSheet, ConfiguratoreDatiEsterniDTO confDatiEsterniDTO) {
		try{
			int totalRows = mainSheet.getPhysicalNumberOfRows();
			Map<String, Integer> mapsValori = getMapsValues(mainSheet);
			 
			Set<DatiEsterniSoggettoDTO> resultSet = new HashSet<DatiEsterniSoggettoDTO>();
			if(totalRows > 0 )
			for(int x = 1; x<=totalRows; x++){
				 DatiEsterniSoggettoDTO dto = null;
				 
				 Row dataRow = mainSheet.getRow(x); //get row 1 to row n (rows containing data)
				 	
				 if(dataRow == null)
					 break;
				 
				 if (dataRow.getLastCellNum() < 1) { // devono esserci almeno due colonne
						continue;
				 }	
				 
				 String cfSoggetto = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaCF(), 0);
				 String codiceEnte = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaEnte(), 1);
				 String dataApertura = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaDataApertura(), null);
				 String dataChiusura = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaDataChiusura(), null);
				 String entitaServizio = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaEntitaServizio(), null);
				 String indirizzo = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaIndirizzo(), null);
				 String tipoPrestazione = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaTipoPrestazione(), null);
				 String codicePrestazione = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaCodPrestazione(), null);
				 String statoDomanda = this.getValoreColonna(mapsValori, dataRow, confDatiEsterniDTO.getNomeColonnaServizioStato(), null);
				 
				 dto = new DatiEsterniSoggettoDTO(cfSoggetto, codiceEnte);	
				 dto.getParametriEsterni().setDataApertura(dataApertura);
				 dto.getParametriEsterni().setDataChiusura(dataChiusura);
				 dto.getParametriEsterni().setEntitaServizio(entitaServizio);
				 dto.getParametriEsterni().setIndirizzo(indirizzo);
				 dto.getParametriEsterni().setTipoPrestazione(tipoPrestazione);
				 dto.getParametriEsterni().setCodicePrestazione(codicePrestazione);
				 dto.getParametriEsterni().setStatoDomanda(statoDomanda);

				 dto.setConfiguratoreDatiEsterni(confDatiEsterniDTO);
				 resultSet.add(dto);
	
				}else{
					log.warn("problema, sembra che il foglio excel abbia solo una riga");
					return (Set<DatiEsterniSoggettoDTO>) Collections.EMPTY_SET;
				}
			
			return resultSet;
			
		}catch(Exception e){
			log.error("Errore nell'elaborazione processWorkbookSheetByCell", e);
			return null;
		}
	}

	private String getValoreColonna(Map<String,Integer> mapsValori,  Row dataRow, String nomeColonna, Integer indexDefault) {
		String value = null;
		if(nomeColonna != null && mapsValori.containsKey(nomeColonna)) {
			int idxForColumn = mapsValori.get(nomeColonna);  
			if(idxForColumn >= 0) {
			 Cell cell1 = dataRow.getCell(idxForColumn);  
			 value = getCellValueAsString(cell1);
			}
		}else if(indexDefault!=null) {
			Cell cell1 = dataRow.getCell(indexDefault);
			value = getCellValueAsString(cell1);
		}
		return value;
	}
}
