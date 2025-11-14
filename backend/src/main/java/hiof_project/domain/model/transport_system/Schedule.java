package hiof_project.domain.model.transport_system;

import java.time.LocalDate;
import java.util.ArrayList;


public class Schedule {

    private int scheduleId;
    private ArrayList<ScheduleTimer> scheduleTimer;
    private LocalDate definedDate;

    //Kontruktør.
    public Schedule(int scheduleId, ArrayList<ScheduleTimer> scheduleTimer, LocalDate definedDate) {
        this.scheduleId = scheduleId;
        this.scheduleTimer = scheduleTimer;
        this.definedDate = definedDate;
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public ArrayList<ScheduleTimer> getScheduleTimer() {
        return scheduleTimer;
    }

    public void setScheduleTimer(ArrayList<ScheduleTimer> scheduleTimer) {
        this.scheduleTimer = scheduleTimer;
    }

    public LocalDate getDefinedDate() {
        return definedDate;
    }

    public void setDefinedDate(LocalDate definedDate) {
        this.definedDate = definedDate;
    }

    @Override
    public String toString() {
        return "Dato valgt: " + definedDate;
    }
}
