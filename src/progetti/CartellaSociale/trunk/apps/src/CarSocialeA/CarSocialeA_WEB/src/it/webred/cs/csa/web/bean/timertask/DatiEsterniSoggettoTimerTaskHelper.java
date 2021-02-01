package it.webred.cs.csa.web.bean.timertask;

import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.log4j.Logger;
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

	private static final String AM_KEY_CONF = "smartwelfare.dir.importazione.datiesterni.base";

	private  String SUBDIR_IN = "in";
	private  String SUBDIR_OUT = "out";
	private  String SUBDIR_ERR = "err";

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

				try {
					BaseDTO bdto = new BaseDTO();
					bdto.setEnteId(enteId);
					bdto.setObj(dtoSet);
					bdto.setObj2(filePath.getFileName().toString());
					bdto.setObj3(fileContents);
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
		} catch (IOException e) {
			log.error(errorMessage, e);
		}

	}

	private DatiEsterniSoggettoDTO processFile(File f) {
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
			Set<DatiEsterniSoggettoDTO> codiciFiscali = processWorkbookSheet(mainSheet.iterator());
			DatiEsterniSoggettoDTO dto = new DatiEsterniSoggettoDTO();
			dto.setObj(codiciFiscali);
			dto.setObj2(fileContents);
			return dto;
		}
	}

	@SuppressWarnings("unchecked")
	private Set<DatiEsterniSoggettoDTO> processWorkbookSheet(Iterator<Row> rowIterator) {
		if (rowIterator.hasNext()) // salta la prima riga, dovendo contenere le intestazioni
			rowIterator.next();
		if (rowIterator.hasNext()) {
			Set<DatiEsterniSoggettoDTO> resultSet = new HashSet<DatiEsterniSoggettoDTO>();
			DatiEsterniSoggettoDTO dto;
			while (rowIterator.hasNext()) {
				Row r = rowIterator.next();
				if (r.getLastCellNum() < 1) { // devono esserci almeno due colonne
					continue;
				}
				String cfSoggetto = r.getCell(0).getStringCellValue().trim();
				String codiceEnte = r.getCell(1).getStringCellValue().trim();
				dto = new DatiEsterniSoggettoDTO(cfSoggetto, codiceEnte);
				resultSet.add(dto);
			}
			return resultSet;
		} else {
			log.warn("problema, sembra che il foglio excel abbia solo una riga");
			return (Set<DatiEsterniSoggettoDTO>) Collections.EMPTY_SET;
		}
	}

}
