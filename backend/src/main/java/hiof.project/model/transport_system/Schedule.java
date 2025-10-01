package hiof.project.model.transport_system;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;


public class Schedule {

    private ArrayList<LocalTime> arrival;
    private ArrayList<LocalTime> departure;
    private LocalDate definedDate;

    public Schedule(ArrayList<LocalTime> arrival, ArrayList<LocalTime> departure, LocalDate definedDate) {
        this.arrival = arrival;
        this.departure = departure;
        this.definedDate = definedDate;
    }

    public ArrayList<LocalTime> getArrival() {
        return arrival;
    }

    public ArrayList<LocalTime> getDeparture() {
        return departure;
    }

    public LocalDate getDefinedDate() {
        return definedDate;
    }

    public void setDefinedDate(LocalDate definedDate) {
        this.definedDate = definedDate;
    }
}
