package it.webred.rulengine.dwh.Dao;

public class DaoException extends Exception
{
	public DaoException(String message) {
		super(message);
	}
	public DaoException(Exception message) {
		super(message);
	}
	
}
