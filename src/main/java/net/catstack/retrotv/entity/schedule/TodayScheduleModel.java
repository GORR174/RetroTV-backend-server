package net.catstack.retrotv.entity.schedule;

import lombok.Data;
import lombok.EqualsAndHashCode;
import net.catstack.retrotv.entity.BaseModel;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "today_schedule")
public class TodayScheduleModel extends BaseModel {
    private String time;
    private String name;
    private Long headingId;
    private Long sourceId;
    private String path;
}