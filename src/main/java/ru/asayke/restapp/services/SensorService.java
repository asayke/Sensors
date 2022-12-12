package ru.asayke.restapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.asayke.restapp.models.Sensor;
import ru.asayke.restapp.repositories.SensorRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    public Optional<Sensor> findByName(String name){
        return sensorRepository.findByName(name);
    }

    public List<Sensor> findAll(){
        return sensorRepository.findAll();
    }

    @Transactional
    public void register(Sensor sensorToAdd) {
        sensorRepository.save(sensorToAdd);
    }
}