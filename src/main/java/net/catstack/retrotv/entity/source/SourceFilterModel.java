package net.catstack.retrotv.entity.source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.catstack.retrotv.entity.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "source_filter")
public class SourceFilterModel extends BaseModel {
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
