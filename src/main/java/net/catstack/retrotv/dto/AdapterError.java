package net.catstack.retrotv.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AdapterError {
    @Schema(description = "Код ошибки", example = "2")
    private Integer code;
    @Schema(description = "Сообщение ошибки", example = "Data is already exists error")
    private String message;
}
