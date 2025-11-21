package hiof_project.infrastructure.adapters.api.dto;

import java.time.LocalDate;
import java.util.ArrayList;

public class ScheduleDTO {

    private final int scheduleId;
    private final LocalDate definedDate;
    private final ArrayList<ScheduleTimerDTO> scheduleTimers;

    public ScheduleDTO(int scheduleId, LocalDate definedDate, ArrayList<ScheduleTimerDTO> scheduleTimers) {
        this.scheduleId = scheduleId;
        this.definedDate = definedDate;
        this.scheduleTimers = scheduleTimers;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public LocalDate getDefinedDate() {
        return definedDate;
    }

    public ArrayList<ScheduleTimerDTO> getScheduleTimers() {
        return scheduleTimers;
    }
}
