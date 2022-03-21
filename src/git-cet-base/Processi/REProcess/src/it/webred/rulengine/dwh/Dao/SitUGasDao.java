package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitCConcessioni;
import it.webred.rulengine.dwh.table.SitUGas;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitUGasDao extends TabellaDwhDao {

	public SitUGasDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitUGasDao(SitUGas tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
