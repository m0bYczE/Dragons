package cz.m0bY_czE.dragons.Listeners.Entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.weather.WeatherChangeEvent;

import cz.m0bY_czE.dragons.Listeners.DeadEndListener;

public class Weather extends DeadEndListener {
    public Weather() {
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        event.setCancelled(true);
    }
}
