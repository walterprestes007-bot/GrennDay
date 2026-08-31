package com.greenday.service;

import com.greenday.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RelatorioService {

    private final PropriedadeService propriedadeService;
    private final CulturaService culturaService;
    private final SensorService sensorService;
    private final ClimaService climaService;
    private final AlertaService alertaService;
    private final IrrigacaoService irrigacaoService;

    /** Monta um resumo consolidado da propriedade para relatorios/dashboard. */
    public Map<String, Object> gerarResumoPropriedade(Long propriedadeId) {
        Propriedade propriedade = propriedadeService.buscarPorId(propriedadeId);
        List<Cultura> culturas = culturaService.listarPorPropriedade(propriedadeId);
        List<Sensor> sensores = sensorService.listarPorPropriedade(propriedadeId);
        List<Alerta> alertasNaoLidos = alertaService.listarNaoLidos(propriedadeId);
        List<Irrigacao> irrigacoes = irrigacaoService.listarPorPropriedade(propriedadeId);

        Clima climaAtual = null;
        try {
            climaAtual = climaService.ultimo(propriedadeId);
        } catch (Exception ignored) {
            // sem dados climaticos ainda
        }

        return Map.of(
                "propriedade", propriedade.getNome(),
                "totalCulturas", culturas.size(),
                "totalSensores", sensores.size(),
                "alertasNaoLidos", alertasNaoLidos.size(),
                "totalIrrigacoes", irrigacoes.size(),
                "climaAtual", climaAtual
        );
    }
}
