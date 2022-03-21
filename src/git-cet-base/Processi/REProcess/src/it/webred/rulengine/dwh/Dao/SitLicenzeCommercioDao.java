package it.webred.rulengine.dwh.Dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Map.Entry;

import it.webred.rulengine.dwh.DwhUtils;
import it.webred.rulengine.dwh.def.ChiaveEsterna;
import it.webred.rulengine.dwh.def.ChiaveOriginale;
import it.webred.rulengine.dwh.def.DataDwh;
import it.webred.rulengine.dwh.def.DtExpDato;
import it.webred.rulengine.dwh.def.DtFineDato;
import it.webred.rulengine.dwh.def.DtIniDato;
import it.webred.rulengine.dwh.def.ProcessId;
import it.webred.rulengine.dwh.table.SitLicenzeCommercio;
import it.webred.rulengine.dwh.table.TabellaDwh;
import it.webred.rulengine.impl.bean.BeanEnteSorgente;

public class SitLicenzeCommercioDao extends TabellaDwhDao {

	public SitLicenzeCommercioDao(TabellaDwh tab) {
		super(tab);
		// TODO Auto-generated constructor stub
	}

	public SitLicenzeCommercioDao(SitLicenzeCommercio tab, BeanEnteSorgente bes)
	{
		super(tab,  bes);
	}
	
	
	private static String getKeyMap(Entry entry) {
		return (String) (entry).getKey();
	}
	
	
	
}
