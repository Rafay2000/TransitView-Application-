package hiof_project.integration_test;

import hiof_project.domain.model.transport_system.*;
import hiof_project.domain.model.user_system.*;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
/*
public class IntegrationTest {

    //test område.
    @Test
    void testFullTripScenario() {
        // Opprett stopp
        Stop fredrikstad = new Stop(1, "Fredrikstad Busstasjon", 3.5,
                10, "Stopper ved Rema");
        Stop glemmen = new Stop(2, "Glemmen Videregående", 5.0,
                15, "Stopper ved store parkeringsplassen");

        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(fredrikstad);
        stops.add(glemmen);

        Route route = new Route(1,"FR-GR03", stops);

        ArrayList<ScheduleTimer> timers = new ArrayList<>();
        timers.add(new ScheduleTimer(LocalTime.of(8,50), LocalTime.of(9,0)));
        timers.add(new ScheduleTimer(LocalTime.of(9,10), LocalTime.of(9,20)));

       Schedule schedule = new Schedule(timers, LocalDate.of(2025,10,5));

        Bus bus = new Bus(1, "R4", "Bybuss - Elektrisk", 45);
        Trip trip = new Trip(1, route, schedule, bus);


        // Assertions
        assertEquals(1, trip.getRoute().getRouteId());
        assertEquals("FR-GR03", trip.getRoute().getRouteName());
        assertEquals("R4", trip.getBus().getVehicleName());
        assertEquals(2, trip.getRoute().getStops().size());
    }
}
*/