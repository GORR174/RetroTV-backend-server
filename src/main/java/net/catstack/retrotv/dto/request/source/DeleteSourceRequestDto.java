package net.catstack.retrotv.dto.request.source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DeleteSourceRequestDto {
    @NotNull
    @Schema(example = "123")
    private Long id;
}
