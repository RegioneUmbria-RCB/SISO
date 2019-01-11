package it.webred.cs.csa.web.manbean.export;

abstract class PsaExportFactory {

	static PsaXmlExporter getExporter(SchemaVersion psaVersion) {
		switch (psaVersion) {
		case PSA_2015:
			return new XmlExport2015Impl();
		case PSA_2016_PS:
			return new XmlExport2016Provisional();
		case PSA_2016_PS_SINA:
			return new XmlExport2016Impl();
		default:
			return null;
		}
	}

	
}
