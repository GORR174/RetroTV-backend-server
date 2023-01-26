package net.catstack.retrotv.entity.heading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.catstack.retrotv.entity.BaseModel;
import net.catstack.retrotv.entity.source.SourceModel;
import net.catstack.retrotv.entity.schedule.HourSlotModel;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "heading")
public class HeadingModel extends BaseModel {
    @Schema(example = "Животные")
    private String name;
    @Schema(example = "3")
    private Integer color;
    @OneToMany(mappedBy = "heading", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JsonIgnore
    @ToString.Exclude
    private List<SourceModel> sources;
    @ManyToMany(mappedBy = "headings", cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private List<HourSlotModel> slots;
}
