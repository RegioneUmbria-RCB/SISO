package it.webred.mui.input.parsers;
import it.webred.mui.input.MuiFornituraRecordParser;
import it.webred.mui.input.MuiInvalidInputDataException;
import it.webred.mui.model.MiDupTerreniInfo;

public class MuiFornituraRecordTipo8Parser  extends MuiFornituraRecordAbstractParser implements MuiFornituraRecordParser{

	public void parse(String record) throws MuiInvalidInputDataException {
		MiDupTerreniInfo currentRecord= new MiDupTerreniInfo();
		_record=currentRecord;
		parsePojo(record,currentRecord);
	}
}
