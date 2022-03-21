package it.webred.cs.csa.web.manbean.export;

abstract class SinbaExportFactory {

	static SinbaXmlExporter getExporter(SchemaVersion sinbaVersion) {
		switch (sinbaVersion) {
		
		case SINBA_2018:
			return new XmlExport2018ImplSINBA();
		default:
			return null;
		}
	}

	
}
