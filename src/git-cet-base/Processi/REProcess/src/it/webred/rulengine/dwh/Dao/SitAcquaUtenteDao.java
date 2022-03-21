package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitAcquaUtente;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitAcquaUtenteDao extends TabellaDwhDao {

	public SitAcquaUtenteDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitAcquaUtenteDao(SitAcquaUtente tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
