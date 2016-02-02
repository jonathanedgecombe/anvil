package com.wyverngame.anvil.api.client.event;

import com.wyverngame.anvil.api.event.Event;

public final class UpdateWeatherEvent extends Event<Void> {
	private final float cloudiness, fog, rain, windRot, windPower, temperature;

	public UpdateWeatherEvent(float cloudiness, float fog, float rain, float windRot, float windPower, float temp) {
		this.cloudiness = cloudiness;
		this.fog = fog;
		this.rain = rain;
		this.windRot = windRot;
		this.windPower = windPower;
		this.temperature = temp;
	}

	public float getCloudiness() {
		return cloudiness;
	}

	public float getFog() {
		return fog;
	}

	public float getRain() {
		return rain;
	}

	public float getWindRot() {
		return windRot;
	}

	public float getWindPower() {
		return windPower;
	}

	public float getTemperature() {
		return temperature;
	}
}
