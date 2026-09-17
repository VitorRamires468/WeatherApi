package com.vitor.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ResponseDTO(
        @JsonProperty("tempmax")
        Double maxTemp,
        @JsonProperty("tempmin")
        Double minTemp,
        @JsonProperty("temp")
        Double currentTemp
) {
        public ResponseDTO(Double maxTemp, Double minTemp, Double currentTemp) {
                this.maxTemp = maxTemp;
                this.minTemp = minTemp;
                this.currentTemp = currentTemp;
        }
}
