package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitDUnione;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;



public class SitDUnioneDao extends TabellaDwhDao 
{


	public SitDUnioneDao(SitDUnione tab)
	{
		super(tab);

	
	}

	public SitDUnioneDao(TabellaDwh tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}







}
