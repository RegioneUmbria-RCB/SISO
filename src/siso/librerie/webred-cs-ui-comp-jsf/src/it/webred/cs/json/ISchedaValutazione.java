package it.webred.cs.json;

import java.util.Date;

import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.json.dto.JsonBaseBean;

public interface ISchedaValutazione {
	public void init(CsDValutazione parent, CsDValutazione scheda );
	public void init(ISchedaValutazione bean);
	public boolean save(Long visSecondoLivello);
	public boolean save();
	public boolean validaData();
	public boolean elimina();
	public void restore();
	public String getVersionLowerCase();
	public <T extends JsonBaseBean> T  getSelected();
	public void setIdCaso(Long idCaso);
	public void setIdSchedaUdc(Long idUdc);
	public Long getIdCaso();
	public Long getIdSchedaUdc();
	public CsDValutazione getCurrentModel();
	public boolean isNew();
	public Date   getDtModifica();
	public String getOpModifica();
	public Date getCurrentDate();
}
