package it.webred.mui.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MiDupNotaTras extends MiDupNotaTrasBase {

	private Map<String, List<MiDupImportLog>> _loggedProperty = null;


	// private Map<String,Long> _loggedNonBloccanteProperty = null;

	/** default constructor */
	public MiDupNotaTras() {
		super();
	}

	public int getImportErrorBloccantiCount() {
		int res = 0;
		for (Iterator iter = getMiDupImportLogs().iterator(); iter.hasNext();) {
			MiDupImportLog element = (MiDupImportLog) iter.next();
			if (element.getCodiceRegolaInfranta().getFlagBloccante()) {
				res++;
			}
		}
		return res;
	}

	public int getImportErrorCount() {
		return getMiDupImportLogs().size();
	}

	public Map<String, List<MiDupImportLog>> getLoggedProperty() {
		if (_loggedProperty == null) {
			synchronized (this) {
				if (_loggedProperty == null) {
					_loggedProperty = new HashMap<String, List<MiDupImportLog>>();
					// _loggedNonBloccanteProperty = new HashMap<String,Long>();
					for (Iterator iter = getMiDupImportLogs().iterator(); iter
							.hasNext();) {
						MiDupImportLog element = (MiDupImportLog) iter.next();
						List<MiDupImportLog>  v = _loggedProperty.get(element.getPropertyRegolaInfranta());
						if(v == null){
							v = new ArrayList();
							_loggedProperty.put(
									element.getPropertyRegolaInfranta(), v);
						}
						v.add(element);
					}
				}
			}
		}
		return _loggedProperty;
	}
}
