package com.vitor.api.dto.response;

import java.util.List;

public record DaysDTO(
        String resolvedAddress,
        List<ResponseDTO> days
) {
}
