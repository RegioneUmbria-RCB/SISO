package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupIndirizziSog;

public class MuiFornituraRecordTipo5Parser extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {
	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupIndirizziSog currentRecord= new MiDupIndirizziSog();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}

}
