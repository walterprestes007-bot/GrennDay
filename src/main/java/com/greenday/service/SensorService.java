package com.greenday.service;

import com.greenday.exception.ResourceNotFoundException;
import com.greenday.model.DadosSensor;
import com.greenday.model.Sensor;
import com.greenday.repository.DadosSensorRepository;
import com.greenday.repository.SensorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SensorService {

    private final SensorRepository sensorRepository;
    private final DadosSensorRepository dadosSensorRepository;
    private final PropriedadeService propriedadeService;

    public Sensor criar(Long propriedadeId, Sensor sensor) {
        sensor.setPropriedade(propriedadeService.buscarPorId(propriedadeId));
        return sensorRepository.save(sensor);
    }

    public List<Sensor> listarPorPropriedade(Long propriedadeId) {
        return sensorRepository.findByPropriedadeId(propriedadeId);
    }

    public Sensor buscarPorId(Long id) {
        return sensorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sensor nao encontrado: " + id));
    }

    public DadosSensor registrarLeitura(Long sensorId, Double valor) {
        Sensor sensor = buscarPorId(sensorId);
        DadosSensor dados = DadosSensor.builder()
                .sensor(sensor)
                .valor(valor)
                .medidoEm(LocalDateTime.now())
                .build();
        return dadosSensorRepository.save(dados);
    }

    public List<DadosSensor> historico(Long sensorId) {
        return dadosSensorRepository.findBySensorIdOrderByMedidoEmDesc(sensorId);
    }

    public Optional<Double> ultimaLeitura(Long sensorId) {
        return dadosSensorRepository.findBySensorIdOrderByMedidoEmDesc(sensorId)
                .stream()
                .findFirst()
                .map(DadosSensor::getValor);
    }
}
