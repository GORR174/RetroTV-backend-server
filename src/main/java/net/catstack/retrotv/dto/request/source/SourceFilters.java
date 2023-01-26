package net.catstack.retrotv.dto.request.source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class SourceFilters {
    @Schema(example = "4")
    private Integer firstN;
    @Schema(example = "13")
    private Integer lastN;
    @Schema(example = "01.01.2023")
    private String firstDate;
    @Schema(example = "06.01.2023")
    private String lastDate;
    @Schema(example = "коты")
    private String phrase;
}
