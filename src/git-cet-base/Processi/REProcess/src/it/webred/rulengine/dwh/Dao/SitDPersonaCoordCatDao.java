package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitDPersonaCoordCat;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitDPersonaCoordCatDao extends TabellaDwhDao {
	
	public SitDPersonaCoordCatDao(SitDPersonaCoordCat tab)
	{
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitDPersonaCoordCatDao(SitDPersonaCoordCat tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}