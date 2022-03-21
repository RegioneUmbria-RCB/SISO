package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupSoggetti;

public class MuiFornituraRecordTipo3Parser extends
		MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {


	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupSoggetti currentRecord = null;
		currentRecord = new MiDupSoggetti();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}


}
