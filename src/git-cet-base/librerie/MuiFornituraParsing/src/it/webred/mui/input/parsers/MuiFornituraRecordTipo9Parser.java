package it.webred.mui.input.parsers;
import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupFornituraInfo;

public class MuiFornituraRecordTipo9Parser  extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser{

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupFornituraInfo currentRecord= new MiDupFornituraInfo();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}
}
