package net.catstack.retrotv.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatusMessageResponseDto {
    @Schema(example = "0")
    private Integer status;
    @Schema(example = "Success")
    private String message;
}
