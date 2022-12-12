package ru.asayke.restapp.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.asayke.restapp.dto.MeasurementDTO;
import ru.asayke.restapp.dto.MeasurementsResponse;
import ru.asayke.restapp.models.Measurement;
import ru.asayke.restapp.services.MeasurementService;
import ru.asayke.restapp.util.ErrorsUtil;
import ru.asayke.restapp.util.MeasurementException;
import ru.asayke.restapp.util.MeasurementExceptionResponse;
import ru.asayke.restapp.util.MeasurementValidator;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementsController {

    private final MeasurementService measurementService;
    private final MeasurementValidator measurementValidator;
    private final ModelMapper mapper;

    @Autowired
    public MeasurementsController(MeasurementService measurementService, MeasurementValidator measurementValidator, ModelMapper mapper) {
        this.measurementService = measurementService;
        this.measurementValidator = measurementValidator;
        this.mapper = mapper;
    }

    @GetMapping
    public MeasurementsResponse getMeasurements(){
        return new MeasurementsResponse(measurementService.findAll().stream().map(this::convertToMeasurementDTO).collect(Collectors.toList()));
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){
        Measurement measurementToAdd = covertToMeasurement(measurementDTO);

        measurementValidator.validate(measurementToAdd, bindingResult);

        if(bindingResult.hasErrors())
            ErrorsUtil.returnErrorsToClient(bindingResult);

        measurementService.addMeasurement(measurementToAdd);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public Long getRainyDaysCount(){
        return measurementService.findAll().stream().filter(Measurement::isRaining).count();
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementExceptionResponse> handleException(MeasurementException e){
        MeasurementExceptionResponse response = new MeasurementExceptionResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Measurement covertToMeasurement(MeasurementDTO measurementDTO) {
        return mapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement) {
        return mapper.map(measurement, MeasurementDTO.class);
    }
}