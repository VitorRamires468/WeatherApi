package com.vitor.api.service;

import com.vitor.api.config.WeatherApiProperties;
import com.vitor.api.dto.request.RequestDTO;
import com.vitor.api.dto.response.DaysDTO;
import com.vitor.api.dto.response.ResponseDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ApiConsumer {

    private final RestClient restClient;
    private final WeatherApiProperties weatherApiProperties;

    public ApiConsumer(WeatherApiProperties weatherApiProperties, RestClient restClient) {
        this.weatherApiProperties = weatherApiProperties;
        this.restClient= restClient;
    }

    @Cacheable(value = "weatherCache", key = "#requestDTO.city() + '-' + #requestDTO.state() + '-' + #requestDTO.country()")
    public ResponseDTO getWeatherApi(RequestDTO requestDTO) {
        String uri = UriComponentsBuilder
                .fromUriString(weatherApiProperties.getUrl())
                .pathSegment(requestDTO.city() + ", " + requestDTO.state() + ", " + requestDTO.country())
                .queryParam("unitGroup", "metric")
                .queryParam("key", weatherApiProperties.getKey())
                .build()
                .toUriString();

        var daysDTO = restClient.get().uri(uri).retrieve().body(DaysDTO.class);
        var day = daysDTO.days().get(0);
        return new ResponseDTO(day.maxTemp(), day.minTemp(), day.currentTemp());
    }
}
