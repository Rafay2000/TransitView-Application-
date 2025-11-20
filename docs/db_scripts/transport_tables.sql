-- Tabell for ruter
CREATE TABLE Routes (
    route_id INT PRIMARY KEY,
    route_name NVARCHAR(55) NOT NULL
);

-- Tabell for busser
CREATE TABLE Buses (
    bus_id INT PRIMARY KEY,
    bus_name NVARCHAR(50) NOT NULL,
    bus_type NVARCHAR(50) NOT NULL,
    capacity INT NOT NULL
);

-- Tabell for tider med ankomst og avgang med dato
CREATE TABLE Schedules (
    schedule_id INT PRIMARY KEY,
    defined_date DATE NOT NULL
)

-- Tabell for bussholdeplasser
CREATE TABLE Stops (
    stop_id INT PRIMARY KEY,
    stop_name NVARCHAR(75) NOT NULL,
    distance_to_next_km DECIMAL(5,2) NULL,
    time_to_next_stop INT NULL,
    desc_next_stop NVARCHAR(125) NULL,
    route_id INT NOT NULL,
    CONSTRAINT FK_Stops_On_Routes FOREIGN KEY (route_id) REFERENCES Routes(route_id)
);

-- Tabell for ankomst og avgang per stopp i en tur
CREATE TABLE ScheduleTimers (
    schedule_timer_id INT IDENTITY(1,1) PRIMARY KEY,
    schedule_id INT NOT NULL,
    arrival TIME NOT NULL,
    departure TIME NOT NULL,
    sequence INT NOT NULL,
    CONSTRAINT FK_Timer_On_Schedule FOREIGN KEY (schedule_id) REFERENCES Schedules(schedule_id)
)

-- Tabell for turer
CREATE TABLE Trips (
    trip_id INT PRIMARY KEY,
    route_id INT NOT NULL,
    schedule_id INT NOT NULL,
    bus_id INT NOT NULL,
    CONSTRAINT FK_Route_On_Trip FOREIGN KEY (route_id) REFERENCES Routes(route_id),
    CONSTRAINT FK_Schedule_On_Trip FOREIGN KEY (schedule_id) REFERENCES Schedules(schedule_id),
    CONSTRAINT FK_Bus_On_Trip FOREIGN KEY (bus_id) REFERENCES Buses(bus_id)
)

-- Tabell for sanntid oppdatering til et
CREATE TABLE RealtimeScheduling (
    realtime_id INT IDENTITY(1,1) PRIMARY KEY,
    trip_id INT NOT NULL,
    stop_id INT NOT NULL,
    updated_arrival TIME NULL,
    updated_departure TIME NULL,
    status NVARCHAR(50) NULL,
    CONSTRAINT FK_RealTimeOn_Trip FOREIGN KEY (trip_id) REFERENCES Trips(trip_id),
    CONSTRAINT FK_RealTimeOn_Stop FOREIGN KEY (stop_id) REFERENCES Stops(stop_id)
);
