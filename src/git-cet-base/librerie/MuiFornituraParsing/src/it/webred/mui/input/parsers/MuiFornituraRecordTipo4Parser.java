package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupTitolarita;

public class MuiFornituraRecordTipo4Parser extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupTitolarita currentRecord= new MiDupTitolarita();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}

}
