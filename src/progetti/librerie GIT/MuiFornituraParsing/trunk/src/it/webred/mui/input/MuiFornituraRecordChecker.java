package it.webred.mui.input;

import it.webred.mui.model.MiDupNotaTras;

public interface MuiFornituraRecordChecker {
	boolean check(Object pojo);
	void setIdNota(Long idNota);
	Long getIdNota();
	MiDupNotaTras getMiDupNotaTras();
	void setMiDupNotaTras(MiDupNotaTras dupNotaTras);
}
