package com.greenday.service;

import com.greenday.exception.ResourceNotFoundException;
import com.greenday.model.Alerta;
import com.greenday.model.Propriedade;
import com.greenday.repository.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final PropriedadeService propriedadeService;

    public Alerta criar(Long propriedadeId, Alerta.Tipo tipo, Alerta.Severidade severidade, String mensagem) {
        Propriedade propriedade = propriedadeService.buscarPorId(propriedadeId);
        Alerta alerta = Alerta.builder()
                .propriedade(propriedade)
                .tipo(tipo)
                .severidade(severidade)
                .mensagem(mensagem)
                .build();
        return alertaRepository.save(alerta);
    }

    public List<Alerta> listarPorPropriedade(Long propriedadeId) {
        return alertaRepository.findByPropriedadeIdOrderByCriadoEmDesc(propriedadeId);
    }

    public List<Alerta> listarNaoLidos(Long propriedadeId) {
        return alertaRepository.findByPropriedadeIdAndLidoFalse(propriedadeId);
    }

    public Alerta marcarComoLido(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta nao encontrado: " + id));
        alerta.setLido(true);
        return alertaRepository.save(alerta);
    }

    /** Verifica leituras de sensores e gera alertas automaticos de umidade critica. */
    public void avaliarUmidadeSolo(Long propriedadeId, double umidadePercentual) {
        if (umidadePercentual < 20) {
            criar(propriedadeId, Alerta.Tipo.SECA, Alerta.Severidade.ALTA,
                    "Umidade do solo critica: " + umidadePercentual + "%. Considere irrigar.");
        } else if (umidadePercentual > 90) {
            criar(propriedadeId, Alerta.Tipo.UMIDADE_ALTA, Alerta.Severidade.MEDIA,
                    "Umidade do solo muito alta: " + umidadePercentual + "%. Risco de encharcamento.");
        }
    }
}
