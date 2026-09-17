package com.vitor.api.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {
    private String key;
    private String url;
    private Integer cacheTtlHours;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCacheTtlHours() {
        return cacheTtlHours;
    }

    public void setCacheTtlHours(Integer cacheTtlHours) {
        this.cacheTtlHours = cacheTtlHours;
    }
}
