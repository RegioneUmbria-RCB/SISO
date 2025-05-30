package it.webred.rulengine.brick.elab.cartellaSociale;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.GeocodingApiRequest;
import com.google.maps.errors.AccessNotConfiguredException;
import com.google.maps.errors.InvalidRequestException;
import com.google.maps.errors.MaxElementsExceededException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.errors.OverDailyLimitException;
import com.google.maps.errors.OverQueryLimitException;
import com.google.maps.errors.RequestDeniedException;
import com.google.maps.errors.UnknownErrorException;
import com.google.maps.errors.ZeroResultsException;
import com.google.maps.model.GeocodingResult;

public class GoogleGeocodeClient implements GeolocalizationService {

	private static final Logger log = Logger.getLogger(GoogleGeocodeClient.class.getName());

	final GeoApiContext context;

	GoogleGeocodeClient(String apiKey) {
		context = new GeoApiContext().setApiKey(apiKey);
	}

	@Override
	public List<IndirizziZoneDto> geolocalizzaIndirizzi(List<IndirizziZoneDto> addressList) throws GeocodingException {

		GeocodingApiRequest gcRequest;
		for (IndirizziZoneDto dto : addressList) {
			gcRequest = GeocodingApi.geocode(context, dto.getIndirizzoEsteso());

			try {
				GeocodingResult[] gcResults = gcRequest.await();
				if (gcResults.length != 1) {
					dto.setGeocodeStatus(GeolocalizationResult.NON_UNIQUE_RESULT.name());
					dto.setGeocodeStatusDetail(GeolocalizationResult.NON_UNIQUE_RESULT.description());
				} else {
					dto.setGeocodeStatus(GeolocalizationResult.OK.name());
					dto.setGeocodeStatusDetail(GeolocalizationResult.OK.description());
					dto.setGmFormattedAddress(gcResults[0].formattedAddress);
					dto.setLatitudine(gcResults[0].geometry.location.lat);
					dto.setLongitudine(gcResults[0].geometry.location.lng);
				}
			} catch (AccessNotConfiguredException ance) {
				// SMTODO Indicates that the API call was not configured for the
				// supplied credentials and environmental conditions. Check the
				// error message for details.
				throw new GeocodingException("Geocoding api signalled :" + ance.getMessage(), ance);
			} catch (InvalidRequestException ire) {
				dto.setGeocodeStatus(GeolocalizationResult.MALFORMED_REQUEST.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.MALFORMED_REQUEST.description());
			} catch (MaxElementsExceededException meee) {
				// SMTODO Indicates that the product of origins and destinations exceeds the
				// per-query limit
				break;
			} catch (NotFoundException nfe) {
				// SMTODO Indicates that at least one of the locations specified in
				// the request's origin, destination, or waypoints could not be
				// geocoded
				dto.setGeocodeStatus(GeolocalizationResult.NOT_FOUND.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.NOT_FOUND.description());
			} catch (OverDailyLimitException odle) {
				// SMTODO Indicates that the requesting account has exceeded its
				// daily quota
				break;
			} catch (OverQueryLimitException oqle) {
				// SMTODO Indicates that the requesting account has exceeded its
				// short-term quota
				break;
			} catch (RequestDeniedException rde) {
				// SMTODO Indicates that the API denied the request. Check the
				// message for more detail.
				dto.setGeocodeStatus(GeolocalizationResult.DENIED.name());
				// controllare il messaggio
				dto.setGeocodeStatusDetail(rde.getMessage());
			} catch (UnknownErrorException uee) {
				// SMTODO Indicates that the server encountered an unknown error. In
				// some cases these are safe to retry

				// SMTODO logga l'errore, al massimo
			} catch (ZeroResultsException zre) {
				// SMTODO In some cases, this will be treated as a success state
				// and you will only see an empty array. For time zone data, it
				// means that no time zone information could be found for the
				// specified position or time. Confirm that the request is for
				// a location on land, and not over water.
				dto.setGeocodeStatus(GeolocalizationResult.ZERO_RESULT.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.ZERO_RESULT.description());

			} catch (Exception e) {
				log.error("Indirizzo processato : " + dto.getIndirizzoEsteso());
				log.error("Errore API di Google : " + e.getMessage(),e);			
				dto.setGeocodeStatus(GeolocalizationResult.GENERIC.name());
				dto.setGeocodeStatusDetail(GeolocalizationResult.GENERIC.description());
				//e.printStackTrace();

			}

		} // - for

		return addressList;
	}

}
