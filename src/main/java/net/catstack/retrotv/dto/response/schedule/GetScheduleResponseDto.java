package net.catstack.retrotv.dto.response.schedule;

import lombok.Data;
import net.catstack.retrotv.entity.schedule.DailyScheduleModel;

import java.util.List;

@Data
public class GetScheduleResponseDto {
    private List<DailyScheduleModel> schedule;
    private boolean showAnnouncements;
}
