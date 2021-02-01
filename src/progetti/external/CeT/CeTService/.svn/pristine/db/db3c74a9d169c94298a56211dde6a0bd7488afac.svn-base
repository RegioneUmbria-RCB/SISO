package it.webred.ct.data.access.basic.tarsu;

import it.webred.ct.data.access.basic.tarsu.dto.DichiarazioniTarsuSearchCriteria;
import it.webred.ct.data.access.basic.tarsu.dto.RicercaOggettoTarsuDTO;
import it.webred.ct.data.model.tarsu.VTTarCiviciAll;

import java.util.List;

public class TarsuOggQueryBuilder {
	private RicercaOggettoTarsuDTO criteri;

	private final String SQL_OGGETTO_VIA = "SELECT o FROM SitTTarOggetto o, SitTTarVia v WHERE o.idExtVia=v.idExt ";

	
	public TarsuOggQueryBuilder() {
	}
	public TarsuOggQueryBuilder(RicercaOggettoTarsuDTO criteria) {
		this.criteri=criteria;
	}
	
	public String createQueryOggettoVia() {
		String sql = "";
		sql= SQL_OGGETTO_VIA;
		String provenienza=null;
		if (criteri.getProvenienza()!=null)
			provenienza=criteri.getProvenienza();
		if (provenienza !=null)
			sql += " AND o.provenienza = '" + provenienza + "' AND v.provenienza ='" + provenienza + "'";
		List<VTTarCiviciAll> listaCivTar=null;
		if (criteri.getListaCivTarsu()!=null)
			listaCivTar=criteri.getListaCivTarsu();
		if (listaCivTar!=null && listaCivTar.size() >0 ) {
			sql += " AND ( ";
			int i = 0;
			for (VTTarCiviciAll civTar: listaCivTar ) {
				if (i>0)
					sql += " OR ";
				sql += " ( v.id='" + civTar.getId() + "' AND o.numCiv ='" + civTar.getNumCiv() + "'";
				if (civTar.getEspCiv() ==null )
					sql += " AND o.espCiv IS NULL )";
				else
					sql += " AND o.espCiv ='" + civTar.getEspCiv()  +"' )";
				i++;
			}
			sql += ") ORDER BY o.sez, o.foglio, o.numero, o.sub, o.desInd, o.numCiv, o.datIni desc";
		}
		return sql;
	}
}
