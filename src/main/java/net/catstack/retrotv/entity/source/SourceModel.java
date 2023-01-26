package net.catstack.retrotv.entity.source;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import net.catstack.retrotv.entity.BaseModel;
import net.catstack.retrotv.entity.heading.HeadingModel;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "source")
public class SourceModel extends BaseModel {
    @Schema(example = "Smart Cats tv")
    private String name;
    @Schema(example = "https://www.youtube.com/@Smart_Cats_tv")
    private String link;
    @ManyToOne
    private HeadingModel heading;
    @OneToOne(cascade = CascadeType.ALL)
    private SourceFilterModel filters;
}
