package ru.asayke.restapp.models;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "value")
    @Min(-100)
    @Max(100)
    private Double value;

    @Column(name = "raining")
    @NotNull
    private Boolean raining;

    @NotNull
    @Column(name = "measurement_date_time")
    private LocalDateTime measurementDatetime;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "sensor", referencedColumnName = "name")
    private Sensor sensor;

    public Integer getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public Boolean isRaining() { return raining; }

    public void setRaining(boolean raining) { this.raining = raining; }

    public LocalDateTime getMeasurementDatetime() { return measurementDatetime; }

    public void setMeasurementDatetime(LocalDateTime measurementDatetime) { this.measurementDatetime = measurementDatetime; }

    public Sensor getSensor() { return sensor; }

    public void setSensor(Sensor sensor) { this.sensor = sensor; }
}