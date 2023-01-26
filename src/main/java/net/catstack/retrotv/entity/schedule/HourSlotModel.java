package net.catstack.retrotv.entity.schedule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.catstack.retrotv.entity.BaseModel;
import net.catstack.retrotv.entity.heading.HeadingModel;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "hour_slot")
public class HourSlotModel extends BaseModel {
    @Schema(example = "1")
    private int hour;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "slot_heading",
            joinColumns = @JoinColumn(name = "hour_slot_id"),
            inverseJoinColumns = @JoinColumn(name = "heading_id")
    )
    private List<HeadingModel> headings;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    @ToString.Exclude
    private DailyScheduleModel dayModel;
}
