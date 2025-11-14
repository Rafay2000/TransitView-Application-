package hiof_project;

import hiof_project.domain.model.transport_system.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {

        SpringApplication.run(Main.class, args);

        // Opprett stopp
        Stop fredrikstad = new Stop(1, "Fredrikstad Busstasjon", 3.5,
                10, "Stopper ved Rema 1000");
        Stop glemmen = new Stop(2, "Glemmen Videregående", 5.0,
                15, "Stopper ved store parkeringsplassen");
        Stop ostfoldhallen = new Stop(3, "Østfoldhallen", 12.0,
                17, "Stopper ved hovedveien fra skolen");
        Stop greaaker = new Stop(4, "Greåker Videregående", 23.0,
                31, "Siste stopp på Fredrikstad Bussterminal");

        // Opprett buss og rute
        Bus bus = new Bus(1, "R4", "Bybuss - Elektrisk", 45);
        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(fredrikstad);
        stops.add(glemmen);
        stops.add(ostfoldhallen);
        stops.add(greaaker);

        Route route = new Route(1,"FR-GR03", stops);

        // Opprett tidsplan (Schedule) med ScheduleTimer
        ArrayList<ScheduleTimer> fre_sarps = new ArrayList<>();

        fre_sarps.add(1, new ScheduleTimer(LocalTime.of(8, 50), LocalTime.of(9, 0)));
        fre_sarps.add(1, new ScheduleTimer(LocalTime.of(9, 10), LocalTime.of(9, 25)));
        fre_sarps.add(1, new ScheduleTimer(LocalTime.of(9, 40), LocalTime.of(9, 50)));
        fre_sarps.add(1, new ScheduleTimer(LocalTime.of(10, 7), LocalTime.of(10, 14))); // siste stopp

        Schedule schedule = new Schedule(1, fre_sarps, LocalDate.of(2025, 10, 5));

        // Opprett tur
        Trip trip = new Trip(1, route, schedule, bus);

        // Print ut resultatet
        System.out.println("Søkt på rute 'FR-GR03'");
        System.out.println();
        System.out.println(trip);
    }
}


