package com.vitor.api.controller;

import com.vitor.api.dto.request.RequestDTO;
import com.vitor.api.dto.response.ResponseDTO;
import com.vitor.api.service.ApiConsumer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
public class ApiController {

    private final ApiConsumer apiConsumer;

    public ApiController(ApiConsumer apiConsumer) {
        this.apiConsumer = apiConsumer;
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getWeather(@RequestBody RequestDTO requestDTO) {
        return ResponseEntity.ok(apiConsumer.consumirApiExterna(requestDTO));
    }
}
