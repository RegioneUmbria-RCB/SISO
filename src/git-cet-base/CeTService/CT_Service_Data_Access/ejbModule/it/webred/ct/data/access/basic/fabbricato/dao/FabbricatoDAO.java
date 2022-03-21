package it.webred.ct.data.access.basic.fabbricato.dao;

import it.webred.ct.data.model.fabbricato.FabbricatoExRurale;
import it.webred.ct.data.model.fabbricato.FabbricatoMaiDichiarato;

import java.util.List;

public interface FabbricatoDAO {

	public List<FabbricatoExRurale> getListaFabbricatiExRurali(String sez, String foglio, String particella, String sub);

	public List<FabbricatoMaiDichiarato> getListaFabbricatiMaiDichiarati(String sez, String foglio, String particella);

}
