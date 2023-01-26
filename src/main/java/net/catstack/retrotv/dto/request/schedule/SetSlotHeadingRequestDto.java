package net.catstack.retrotv.dto.request.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class SetSlotHeadingRequestDto {
    @Min(1)
    @Max(7)
    @Schema(example = "2")
    private int dayNumber;
    @Min(0)
    @Max(23)
    @Schema(example = "8")
    private int hour;
    @Size(min = 0, max = 4)
    @Schema(example = "[\"Животные\"]")
    private List<String> headings;
}
