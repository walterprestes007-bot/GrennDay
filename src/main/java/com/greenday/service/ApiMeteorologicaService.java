package com.greenday.service;

import com.greenday.model.Clima;
import com.greenday.model.Propriedade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiMeteorologicaService {

    private final RestClient restClient;
    private final ClimaService climaService;

    @Value("${greenday.clima.api-key}")
    private String apiKey;

    @Value("${greenday.clima.base-url}")
    private String baseUrl;

    /**
     * Busca o clima atual na API externa (OpenWeatherMap) para as coordenadas
     * da propriedade e persiste o resultado.
     */
    @SuppressWarnings("unchecked")
    public Clima buscarEArmazenarClimaAtual(Propriedade propriedade) {
        String url = String.format("%s/weather?lat=%s&lon=%s&appid=%s&units=metric&lang=pt_br",
                baseUrl, propriedade.getLatitude(), propriedade.getLongitude(), apiKey);

        Map<String, Object> resposta = restClient.get()
                .uri(url)
                .retrieve()
                .body(Map.class);

        Map<String, Object> main = (Map<String, Object>) resposta.get("main");
        Map<String, Object> wind = (Map<String, Object>) resposta.get("wind");
        var weatherList = (java.util.List<Map<String, Object>>) resposta.get("weather");

        Clima clima = Clima.builder()
                .propriedade(propriedade)
                .temperaturaCelsius(((Number) main.get("temp")).doubleValue())
                .umidadeRelativa(((Number) main.get("humidity")).doubleValue())
                .velocidadeVentoKmh(wind != null ? ((Number) wind.get("speed")).doubleValue() * 3.6 : null)
                .descricao(weatherList != null && !weatherList.isEmpty()
                        ? String.valueOf(weatherList.get(0).get("description")) : null)
                .build();

        return climaService.registrar(propriedade.getId(), clima);
    }
}
