package hiof_project.transport_test;

import hiof_project.domain.model.transport_system.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

public class TransportTest {

    @Test
    void testStop() {
        Stop stop = new Stop(1, "Fredrikstad Busstasjon", 3.5, 10, "Stopper ved Rema");
        assertEquals(1, stop.getStopId());
        assertEquals("Fredrikstad Busstasjon", stop.getStopName());
        assertEquals(3.5, stop.getDistanceInKm());
        assertEquals(10, stop.getTimeToNextStop());
        assertEquals("Stopper ved Rema", stop.getDescriptionNextStop());
    }

    @Test
    void testRoute() {
        ArrayList<Stop> stopTest = new ArrayList<>();
        stopTest.add(new Stop(1, "Fredrikstad", 3.5, 10, "Stopper ved Rema"));
        Route route = new Route(1, "FR-GR03", stopTest);

        assertEquals(1, route.getRouteId());
        assertEquals("FR-GR03", route.getRouteName());
        assertEquals(1, route.getStops().size());
    }

    @Test
    void testBus() {
        Bus bus = new Bus(1, "R4", "Bybuss - Elektrisk", 45);
        assertEquals(1, bus.getVehicleId());
        assertEquals("R4", bus.getVehicleName());
        assertEquals("Bybuss - Elektrisk", bus.getVehicleType());
        assertEquals(45, bus.getCapacity());
    }

    @Test
    void testSchedule() {
        ArrayList<ScheduleTimer> timers = new ArrayList<>();
        timers.add(new ScheduleTimer(LocalTime.of(8,50), LocalTime.of(9,0)));
        Schedule schedule = new Schedule(timers, LocalDate.of(2025,10,5));

        assertEquals(1, schedule.getScheduleTimer().size());
        assertEquals(LocalTime.of(8,50), schedule.getScheduleTimer().get(0).getArrival());
        assertEquals(LocalTime.of(9,0), schedule.getScheduleTimer().get(0).getDeparture());
        assertEquals(LocalDate.of(2025,10,5), schedule.getDefinedDate());
    }

    @Test
    void testTrip() {
        ArrayList<Stop> stops = new ArrayList<>();
        stops.add(new Stop(1, "Fredrikstad", 3.5, 10, "Stopper ved Rema"));
        Route route = new Route(1, "FR-GR03", stops);

        ArrayList<ScheduleTimer> timers = new ArrayList<>();
        timers.add(new ScheduleTimer(LocalTime.of(8,50), LocalTime.of(9,0)));
        Schedule schedule = new Schedule(timers, LocalDate.of(2025,10,5));

        Bus bus = new Bus(1, "R4", "Bybuss - Elektrisk", 45);
        Trip trip = new Trip(1, route, schedule, bus);

        assertEquals(route, trip.getRoute());
        assertEquals(bus, trip.getBus());
        assertEquals(schedule, trip.getSchedule());
        assertEquals(1, trip.getTripId());
    }
}

