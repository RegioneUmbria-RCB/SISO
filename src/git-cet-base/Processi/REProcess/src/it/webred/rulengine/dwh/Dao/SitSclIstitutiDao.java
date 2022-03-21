package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitSclIstituti;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitSclIstitutiDao extends TabellaDwhDao {

	public SitSclIstitutiDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitSclIstitutiDao(SitSclIstituti tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
}
