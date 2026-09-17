package com.vitor.api.dto.response;

public record DayConditionsDTO(
        Double currentTemp,
        Double tempMax,
        Double tempMin
) {
    public DayConditionsDTO(Double currentTemp, Double tempMax, Double tempMin) {
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }
}
