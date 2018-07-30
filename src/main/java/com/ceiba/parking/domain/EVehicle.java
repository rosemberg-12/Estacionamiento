package com.ceiba.parking.domain;

public enum EVehicle {

	CAR{

		@Override
		public Long getPricePerDay() {
			return 8000L;
		}

		@Override
		public Long getPricePerHour() {
			return 1000L;
		}
		
	},
	MOTOCYCLE{

		@Override
		public Long getPricePerDay() {
			return 4000L;
		}

		@Override
		public Long getPricePerHour() {
			return 500L;
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
}
