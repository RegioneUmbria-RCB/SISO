package it.webred.cs.csa.web.manbean.export;

import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jboss.logging.Logger;

import it.webred.cs.csa.ejb.dto.EsportazioneDTO;
import it.webred.cs.csa.ejb.dto.SinbaDTO;

abstract class SinbaXmlExporter {

	protected static final Logger logger = Logger.getLogger("carsociale.log");
	protected static final DecimalFormat df;
	protected static final DateFormat datef = new SimpleDateFormat("yyyy-MM-dd"); // SISO-606

	static { // SISO-606
		df = new DecimalFormat("#0.00");
		DecimalFormatSymbols custom = new DecimalFormatSymbols();
		custom.setDecimalSeparator('.');
		df.setDecimalFormatSymbols(custom);
	}



	abstract File doExport(
			String XML_PATH,
			List<SinbaDTO> erogDaEsportareList,
			String idFlusso,
			String denomEnte,
			String codEnte,
			String cfOperatore,
			String indirEnte) throws Exception;
	
	

}
