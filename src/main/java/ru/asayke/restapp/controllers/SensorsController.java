package ru.asayke.restapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.asayke.restapp.dto.SensorDTO;
import ru.asayke.restapp.models.Sensor;
import ru.asayke.restapp.services.SensorService;
import ru.asayke.restapp.util.ErrorsUtil;
import ru.asayke.restapp.util.MeasurementException;
import ru.asayke.restapp.util.MeasurementExceptionResponse;
import ru.asayke.restapp.util.SensorValidator;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensors")
public class SensorsController {

    private final SensorService sensorService;
    private final ModelMapper mapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorService sensorService, ModelMapper mapper, SensorValidator sensorValidator) {
        this.sensorService = sensorService;
        this.mapper = mapper;
        this.sensorValidator = sensorValidator;
    }

    @GetMapping
    public List<SensorDTO> getSensors(){
        return sensorService.findAll().stream().map(this::convertToSensorDTO).collect(Collectors.toList());
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDTO sensorDTO,
                                                   BindingResult bindingResult){
        Sensor sensorToAdd = convertToSensor(sensorDTO);

        sensorValidator.validate(sensorToAdd, bindingResult);

        if(bindingResult.hasErrors())
            ErrorsUtil.returnErrorsToClient(bindingResult);

        sensorService.register(sensorToAdd);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementException e){
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Sensor convertToSensor(SensorDTO sensorDTO) {
        return mapper.map(sensorDTO, Sensor.class);
    }

    private SensorDTO convertToSensorDTO(Sensor sensor){
        return mapper.map(sensor, SensorDTO.class);
    }
}