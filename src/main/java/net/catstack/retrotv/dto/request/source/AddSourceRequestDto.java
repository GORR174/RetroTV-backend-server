package net.catstack.retrotv.dto.request.source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AddSourceRequestDto {
    private Long id;
    @NotEmpty
    @Schema(example = "Smart Cats tv")
    private String name;
    @NotEmpty
    @Schema(example = "https://www.youtube.com/@Smart_Cats_tv")
    private String link;
    @NotEmpty
    @Schema(example = "Животные")
    private String heading;
    private SourceFilters filters;
}
