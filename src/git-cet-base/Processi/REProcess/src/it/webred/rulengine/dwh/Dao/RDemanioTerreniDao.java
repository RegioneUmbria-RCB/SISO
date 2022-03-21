package it.webred.rulengine.dwh.Dao;


import it.webred.rulengine.dwh.table.RDemanioTerreni;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class RDemanioTerreniDao extends TabellaDwhDao
{

	public RDemanioTerreniDao(RDemanioTerreni tab){
		super(tab);
	}//-------------------------------------------------------------------------
	
	public RDemanioTerreniDao(RDemanioTerreni tab, BeanEnteSorgente bes){
		super(tab,  bes);
	}//-------------------------------------------------------------------------

}
