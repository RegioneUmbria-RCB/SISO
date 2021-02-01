package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.client.AccessTableDatiEsterniSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dao.DatiEsterniSoggettoDAO;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO.DatiEsterni;
import it.webred.cs.csa.ejb.dto.DatiEsterniSoggettoViewDTO.RigaDatiEsterni;
import it.webred.cs.data.model.CsADatiEsterni;
import it.webred.cs.data.model.CsASoggettoDatiEsterni;
import it.webred.ct.support.validation.annotation.AuditConsentiAccessoAnonimo;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

@Stateless
public class AccessTableDatiEsterniSoggettoSessionBean extends CarSocialeBaseSessionBean implements AccessTableDatiEsterniSoggettoSessionBeanRemote {

	private static final long serialVersionUID = -5262658293732772325L;

	@Autowired
	private DatiEsterniSoggettoDAO dao;

	@Override
	@AuditConsentiAccessoAnonimo
	@AuditSaltaValidazioneSessionID
	public void aggiungiContenutiPerSoggetto(BaseDTO bdto) {
		Set<DatiEsterniSoggettoDTO> soggetti = (Set<DatiEsterniSoggettoDTO>) bdto.getObj();
		String nomeFile = (String) bdto.getObj2();
		byte[] contenuti = (byte[]) bdto.getObj3();

		// crea nel db i dati esterni - per ipotesi, non esistono dati pregressi da aggiornare
		CsADatiEsterni datiEsterni = writeDatiEsterni(nomeFile, contenuti);

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
			Map<String, Set<String>> cfPerEnte = new HashMap<String, Set<String>>();

			for (DatiEsterniSoggettoDTO dto : soggetti) {
				String codiceEnte = dto.getCodiceEnte();
				if (!cfPerEnte.containsKey(codiceEnte)) {
					cfPerEnte.put(codiceEnte, new HashSet<String>());
				}
				cfPerEnte.get(codiceEnte).add(dto.getCodiceFiscale());
			}

			for (Map.Entry<String, Set<String>> entry : cfPerEnte.entrySet()) {
				for (String cf : entry.getValue()) {
					CsASoggettoDatiEsterni sda = mapCfToSoggetto.get(cf);
					String codiceEnte = entry.getKey();
					dao.save(sda, datiEsterni, codiceEnte);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private CsADatiEsterni writeDatiEsterni(String nomeFile, byte[] contenuti) {
		CsADatiEsterni datiEsterni = new CsADatiEsterni();
		datiEsterni.setNomeFile(nomeFile);
		datiEsterni.setContenuti(contenuti);
		datiEsterni.setDataImportazione(new Date());
		dao.save(datiEsterni);
		return datiEsterni;
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
					if (colonna < 2) {
						continue;
					}
					Cell c = intestazione.getCell(colonna);
					if (c == null) {
						// sembrano esserci file che hanno colonne con intestazioni o contenuti vuoti o nulli. Queste
						// non vanno considerate nei dati da passare al client.
						continue;
					}
					nomiCampi.put(colonna, c.getStringCellValue());
				}

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
				viewDtoList.add(viewDto);
			} // !- ciclo for sulle entità
		} catch (IOException e) { // dalla creazione dell'inputStream
			e.printStackTrace();
		}
		return viewDtoList;
	}
}
