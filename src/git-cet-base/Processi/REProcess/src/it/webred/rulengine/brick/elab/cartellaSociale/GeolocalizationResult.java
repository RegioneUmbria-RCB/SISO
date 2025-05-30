package it.webred.rulengine.brick.elab.cartellaSociale;

enum GeolocalizationResult {
	UNDEFINED("UNDEFINED"),
	OK("coordinates obtained"), 
	NON_UNIQUE_RESULT("API returned more than one result"),
	MALFORMED_REQUEST("invalid or malformed request"),
	NOT_FOUND("no geocoding information was found"),
	DENIED("API denied the request"),
	ZERO_RESULT("Location probably is not on land"),
	GENERIC("generic error");
	
	

	private String d;

	private GeolocalizationResult(String descr) {
		d = descr;
	}

	String description() {
		return d;
	}
}
