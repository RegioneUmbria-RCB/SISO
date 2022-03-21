package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitAcquaUtenze;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitAcquaUtenzeDao extends TabellaDwhDao {

	public SitAcquaUtenzeDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitAcquaUtenzeDao(SitAcquaUtenze tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
