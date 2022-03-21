package test;
import it.webred.ct.support.model.CodiceEntrata;

import java.util.List;

import javax.ejb.Remote;

@Remote
public interface CodiceEntrataService {

	public List<CodiceEntrata> getListaCodici(TestParam param) ;

}
