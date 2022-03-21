package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupFabbricatiIdentifica;

public class MuiFornituraRecordTipo7Parser extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser {

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupFabbricatiIdentifica currentRecord= new MiDupFabbricatiIdentifica();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}
}
