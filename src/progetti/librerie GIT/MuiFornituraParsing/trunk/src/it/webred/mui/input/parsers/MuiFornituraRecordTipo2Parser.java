package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupNotaTras;

public class MuiFornituraRecordTipo2Parser extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupNotaTras currentRecord= new MiDupNotaTras();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}


}
