package it.webred.ct.data.access.basic.cnc.dao;

public interface CNCDAO {

	public String getCodiceTipoEntrataDescr(String codTipoEntrata) throws CNCDAOException;
	public String getCodiceUfficioDescr(String codUfficio, String codiceEnte,
			String codiceTipoUfficio)throws CNCDAOException ;

	public String getCodiceUfficioDescrDaPartita(String codiceEnte,
			String codicePartita) throws CNCDAOException;

	public String getCodiceUfficioDescrFull(String codiceEnte,
			String codTipoUfficio, String codUfficio) throws CNCDAOException;

	public String getCodiceEnte(String codComune)throws CNCDAOException;

}
