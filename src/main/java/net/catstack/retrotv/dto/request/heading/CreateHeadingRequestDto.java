package net.catstack.retrotv.dto.request.heading;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class CreateHeadingRequestDto {
    private Long id;
    @NotEmpty
    @Schema(example = "Животные")
    private String name;
    @NotNull
    @Min(1)
    @Max(12)
    @Schema(example = "3")
    private Integer color;

    public CreateHeadingRequestDto(String name, Integer color) {
        this.name = name;
        this.color = color;
    }
}
