package com.greenday.service;

import com.greenday.model.Clima;
import com.greenday.model.Propriedade;
import com.greenday.model.Recomendacao;
import com.greenday.repository.PropriedadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecomendacaoService {

    private final PropriedadeRepository propriedadeRepository;
    private final ClimaService climaService;

    /** Gera recomendacoes simples a partir do ultimo dado climatico da propriedade. */
    public List<Recomendacao> gerarParaPropriedade(Long propriedadeId) {
        Propriedade propriedade = propriedadeRepository.findById(propriedadeId)
                .orElseThrow(() -> new IllegalArgumentException("Propriedade nao encontrada: " + propriedadeId));

        Clima clima = climaService.ultimo(propriedadeId);
        List<Recomendacao> recomendacoes = new ArrayList<>();

        if (clima.getPrecipitacaoMm() != null && clima.getPrecipitacaoMm() > 20) {
            recomendacoes.add(Recomendacao.builder()
                    .propriedade(propriedade)
                    .categoria("IRRIGACAO")
                    .texto("Chuva recente acima de 20mm. Recomenda-se suspender a irrigacao programada.")
                    .build());
        }

        if (clima.getTemperaturaCelsius() != null && clima.getTemperaturaCelsius() < 5) {
            recomendacoes.add(Recomendacao.builder()
                    .propriedade(propriedade)
                    .categoria("GEADA")
                    .texto("Temperatura proxima de zero. Avalie protecao das culturas sensiveis a geada.")
                    .build());
        }

        if (clima.getUmidadeRelativa() != null && clima.getUmidadeRelativa() > 85
                && clima.getTemperaturaCelsius() != null && clima.getTemperaturaCelsius() > 20) {
            recomendacoes.add(Recomendacao.builder()
                    .propriedade(propriedade)
                    .categoria("PRAGA")
                    .texto("Alta umidade e temperatura favorecem fungos e pragas. Monitore as culturas de perto.")
                    .build());
        }

        return recomendacoes;
    }
}
