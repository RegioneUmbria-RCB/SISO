package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupFabbricatiInfo;

public class MuiFornituraRecordTipo6Parser extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupFabbricatiInfo currentRecord= new MiDupFabbricatiInfo();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}
}
