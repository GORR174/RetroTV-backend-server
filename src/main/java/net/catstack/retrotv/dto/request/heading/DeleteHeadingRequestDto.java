package net.catstack.retrotv.dto.request.heading;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class DeleteHeadingRequestDto {
    @NotNull
    @Schema(example = "123")
    private Long id;
}
