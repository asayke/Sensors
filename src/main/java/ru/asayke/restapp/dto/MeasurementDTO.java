package ru.asayke.restapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class MeasurementDTO {
    @Min(-100)
    @Max(100)
    private Double value;

    @NotNull
    private Boolean raining;

    @NotNull
    private SensorDTO sensor;

    public Double getValue() { return value; }

    public void setValue(Double value) { this.value = value; }

    public Boolean getRaining() { return raining; }

    public void setRaining(Boolean raining) { this.raining = raining; }

    public SensorDTO getSensor() { return sensor; }

    public void setSensor(SensorDTO sensor) { this.sensor = sensor; }
}