package com.ceiba.parking.domain;

import com.ceiba.parking.properties.ParkingProperties;

/**
 * Define all kind of vehicles in the system.
 * @author rosemberg.porras
 *
 */
public enum EVehicle {

	CAR{

		@Override
		public Long getPricePerDay() {
			return Long.parseLong(ParkingProperties.getValue("PRICE_PER_DAY_CAR"));
		}

		@Override
		public Long getPricePerHour() {
			return Long.parseLong(ParkingProperties.getValue("PRICE_PER_HOUR_CAR"));
		}

		@Override
		public Integer getMaxCapacity() {
			return Integer.parseInt(ParkingProperties.getValue("MAX_CARS"));
		}
		
		
	},
	MOTORCYCLE{

		@Override
		public Long getPricePerDay() {
			return Long.parseLong(ParkingProperties.getValue("PRICE_PER_DAY_MOTORCYCLE"));
		}

		@Override
		public Long getPricePerHour() {
			return Long.parseLong(ParkingProperties.getValue("PRICE_PER_HOUR_MOTORCYCLE"));
		}

		@Override
		public Integer getMaxCapacity() {
			return Integer.parseInt(ParkingProperties.getValue("MAX_MOTORCYCLES"));
		}
		
	};
	/**
	 * Define the Price Per day of the vehicles.
	 * @return
	 */
	public abstract Long getPricePerDay();
	
	/**
	 * Define the Price Per Hour of the vehicles.
	 * @return
	 */
	public abstract Long getPricePerHour();
	
	/**
	 * Define the Max capacity allowed
	 */
	public abstract Integer getMaxCapacity();
}
