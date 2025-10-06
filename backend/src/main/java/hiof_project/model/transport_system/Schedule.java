package hiof_project.model.transport_system;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;


public class Schedule {

    private ArrayList<ScheduleTimer> scheduleTimers;
    private LocalDate definedDate;

    public Schedule(ArrayList<ScheduleTimer> scheduleTimers, LocalDate definedDate) {
        this.scheduleTimers = scheduleTimers;
        this.definedDate = definedDate;
    }

    public ArrayList<ScheduleTimer> getScheduleTimers() {
        return scheduleTimers;
    }

    public void setScheduleTimers(ArrayList<ScheduleTimer> scheduleTimers) {
        this.scheduleTimers = scheduleTimers;
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
