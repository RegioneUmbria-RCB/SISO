package it.webred.mui.input.parsers;

import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupFornitura;
import it.webred.mui.model.MiDupFornituraBase;

public class MuiFornituraRecordTipo1Parser  extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser{

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupFornitura currentRecord= new MiDupFornitura();
		_record=currentRecord;
		parsePojo(record,currentRecord,MiDupFornituraBase.class);
	}
}
