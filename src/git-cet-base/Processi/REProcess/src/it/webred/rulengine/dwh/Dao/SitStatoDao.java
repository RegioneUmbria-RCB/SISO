package it.webred.rulengine.dwh.Dao;

import it.webred.rulengine.dwh.table.SitStato;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

import java.util.LinkedHashMap;

public class SitStatoDao extends TabellaDwhDao 
{


	public SitStatoDao(SitStato tab)
	{
		super(tab);

	
	}

	
	public SitStatoDao(TabellaDwh tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}











}
