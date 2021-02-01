package it.webred.rulengine.brick.superc.importDati;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import it.webred.rulengine.exception.RulEngineException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.rulengine.type.def.TypeFactory;

/**
 * @author MARCO
 * Classe che legge un file di testo fornito nel formato standard C&T 
 * con il tipo record A contenente la data di esportazioen del file
 */
/**
 * @author MARCO
 *
 */
/**
 * @author MARCO
 *
 */
@Deprecated
public abstract class ImportStandardFiles extends ImportFiles {

	public ImportStandardFiles(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		// TODO Auto-generated constructor stub
	}


	protected void preProcesingFile(String file) throws RulEngineException {
		BufferedReader fileIn = null;
		try {
			try {
				
					fileIn = new BufferedReader(new FileReader(this.percorsoFiles+file));
					String currentLine=null;
					while ((currentLine = fileIn.readLine()) != null)
					{
						List<String> valori = Arrays.asList(currentLine.split("\\|", -1));
						String dtExport = valori.get(3);
						if (dtExport!=null && dtExport.length()<=10)
							dtExport = dtExport + " 00:00";
						this.dataExport = (Timestamp)TypeFactory.getType(dtExport, "java.sql.Timestamp");
						break;
					}
				
			} catch (Exception e) {
				log.error("Errore cercando di recuperare la data di elaborazione del file",e);
				throw new RulEngineException("Errore cercando di recuperare la data di elaborazione del file",e);
			} finally {
				if (fileIn!=null)
					fileIn.close();
			}
		} catch (Exception e) {
			log.error("Errore cercando di recuperare la data di elaborazione del file",e);
			throw new RulEngineException("Errore cercando di recuperare la data di elaborazione del file",e);
		}
	}

}
