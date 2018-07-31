package com.ceiba.parking.properties;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * Properties implementation with purpose to manage the String used in the aplication and 
 * @author rosemberg.porras
 *
 */
public final class ParkingProperties {
	private static final ResourceBundle RESOURCE_BUNDLE = PropertyResourceBundle.getBundle("parking.Parking");
	
	private ParkingProperties() {
	    throw new IllegalStateException("Utility class");
	  }

	public static String getValue(String key){
		return RESOURCE_BUNDLE.getString(key);
	}
}
