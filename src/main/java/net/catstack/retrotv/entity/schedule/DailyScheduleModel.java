package net.catstack.retrotv.entity.schedule;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import net.catstack.retrotv.entity.BaseModel;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "daily_schedule")
public class DailyScheduleModel extends BaseModel {
    @Schema(example = "1")
    private int dayNumber;
    @Schema(example = "06:00")
    private String startTime;
    @Schema(example = "23:00")
    private String endTime;
    @OneToMany(mappedBy = "dayModel", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<HourSlotModel> slots;
}
