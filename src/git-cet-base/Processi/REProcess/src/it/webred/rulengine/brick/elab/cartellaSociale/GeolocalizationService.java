package it.webred.rulengine.brick.elab.cartellaSociale;

import java.util.List;

public interface GeolocalizationService {

	List<IndirizziZoneDto> geolocalizzaIndirizzi(List<IndirizziZoneDto> listaIndirizzi) throws GeocodingException;

}
