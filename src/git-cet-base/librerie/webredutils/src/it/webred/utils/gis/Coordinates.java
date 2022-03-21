package it.webred.utils.gis;




/*
 * Restituisce il codice delle 
 */
public class Coordinates {

	
	private static final String eCoorSysOther = "OTHER";
	private static final String eCoorSysUTM = "UTM";
	private static final String eCoorSysGaussBoagaWest = "82087";
	private static final String eCoorSysGaussBoagaEast = "82088";
	private static final String eCoorSysCassini = "Cassini";
	

	/*
	 * restituisce il codice del sistema di coordinate di una x,y fornita (ES. 82087= eCoorSysGaussBoagaWest ) 
	 */
	public static String getCoordSystem(double pi_dX, double pi_dY) throws Exception {

		  String eRV = null;
			 
		  eRV = eCoorSysOther;

		  //north for Gauss Boaga West/Est/Utm

		  if (pi_dY >= 3800000 && pi_dY <= 5500000) {

		 

		    if (pi_dX >= 270000 && pi_dX <= 1300000) {

		      eRV = eCoorSysUTM;

		    }

		   

		    if (eCoorSysOther.equals(eRV)) {

		      if (pi_dX >= 1300000 && pi_dX <= 1800000) {

		        eRV = eCoorSysGaussBoagaWest;

		      }

		    }

		   

		    if (eCoorSysOther.equals(eRV)) {

		      if (pi_dX >= 2000000 && pi_dX <= 3000000) {

		        eRV = eCoorSysGaussBoagaEast;

		      }

		    }

		  }
		  
		  if (eCoorSysOther.equals(eRV)) {

		    if ((pi_dX > -100000 && pi_dX <= 1000000) && (pi_dY >= -100000 && pi_dY < 1000000)) {

		      eRV = eCoorSysCassini;

		    }

		  }

		  if (!eRV.equals(eCoorSysGaussBoagaEast) && !eRV.equals(eCoorSysGaussBoagaWest)) 
			  throw new Exception("Sistema di coordinate non gestito dal sistema :" + eRV );
		  else // TODO: l'unico gestito per oracle al momento e' gauss-boaga
			  return eRV;
		
	}
	
	public static void main (String args[] ) {
		try {
			System.out.println( Coordinates.getCoordSystem(2300000,4700000));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
