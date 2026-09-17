package com.vitor.api.service;

import com.vitor.api.config.WeatherApiProperties;
import com.vitor.api.dto.request.RequestDTO;
import com.vitor.api.dto.response.DaysDTO;
import com.vitor.api.dto.response.ResponseDTO;
import com.vitor.api.exceptions.InvalidRequest;
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

        var daysDTO = restClient.get()
                .uri(uri)
                .retrieve()
                .body(DaysDTO.class);
        if(daysDTO.resolvedAddress() == null){throw new InvalidRequest("Could not resolve address");}
        String resolvedAddress = daysDTO.resolvedAddress().toLowerCase();
        if(!validateAddress(requestDTO, resolvedAddress)) throw new InvalidRequest("Your address is invalid");
        var day = daysDTO.days().getFirst();
        return new ResponseDTO(day.maxTemp(), day.minTemp(), day.currentTemp());
    }

    private static boolean validateAddress(RequestDTO requestDTO, String resolvedAddress) {
        return resolvedAddress.contains(requestDTO.country().toLowerCase()) && requestDTO.state().contains(resolvedAddress) && requestDTO.state().contains(requestDTO.city());
    }
}
