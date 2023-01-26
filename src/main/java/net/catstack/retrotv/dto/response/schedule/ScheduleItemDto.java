package net.catstack.retrotv.dto.response.schedule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.catstack.retrotv.dto.request.heading.CreateHeadingRequestDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleItemDto {
    private CreateHeadingRequestDto heading;
    private String time;
    private String sourceName;
    private String videoName;
}
