------------------------------------------------------------
-- Ruter (3 stk for testing)
------------------------------------------------------------

INSERT INTO Routes (route_id, route_name) VALUES
(1, 'R1 Tistedal - Høgskolen i Østfold'),
(2, 'R2 Høgskolen i Østfold - Tistedal'),
(3, 'R3 Tista - Fredriksten Festning');

------------------------------------------------------------
-- Busser (5 stk for testing)
------------------------------------------------------------

INSERT INTO Buses (bus_id, bus_name, bus_type, capacity) VALUES
(1, 'Halden-101', 'Bybuss - Elektrisk', 55),
(2, 'Halden-202', 'Bybuss - Bensin', 60),
(3, 'Halden-303', 'Bybuss - Elektrisk', 50),
(4, 'Halden-404', 'Mini-buss - Elektrisk', 25),
(5, 'Halden-505', 'Turistbuss - Hybrid', 45);

------------------------------------------------------------
-- Tidsplan (3 stk for testing, testes på en bestemt dato)
------------------------------------------------------------

INSERT INTO Schedules (schedule_id, defined_date) VALUES
(1, '2025-11-20'),
(2, '2025-11-20'),
(3, '2025-11-20');

------------------------------------------------------------
-- Bussholdeplasser (5 stk for testing)
------------------------------------------------------------

-- 5 Bussholdeplasser i Halden for rute 1 (original rekkefølge)
INSERT INTO Stops (stop_id, stop_name, distance_to_next_km, time_to_next_stop, desc_next_stop, route_id) VALUES
(1, 'Rema 1000 Tistedal',  3.20, 6, 'Stopper på Fredriksten Festning', 1),
(2, 'Fredriksten Festning', 1.80, 5, 'Stopper på Coop Extra Halden', 1),
(3, 'Extra Halsen',        1.40, 3, 'Stopper på Tista Senter', 1),
(4, 'Tista Senter Halden', 2.30, 5, 'Stopper på Høgskolen i Østfold', 1),
(5, 'Høgskolen i Østfold', 0.00, NULL, NULL, 1);

-- 5 Bussholdeplasser i Halden for rute 2 (motsatt rekkefølge)
INSERT INTO Stops (stop_id, stop_name, distance_to_next_km, time_to_next_stop, desc_next_stop, route_id) VALUES
(11, 'Høgskolen i Østfold',   2.30, 4,  'Stopper på Tista Senter', 2),
(12, 'Tista Senter Halden',   1.40, 3,  'Stopper på Extra Halden', 2),
(13, 'Extra Halden',          1.80, 5,  'Stopper på Fredriksten Festning', 2),
(14, 'Fredriksten Festning',  3.20, 6,  'Stopper på Rema 1000 Tistedal', 2),
(15, 'Rema 1000 Tistedal',    0.00, NULL, NULL, 2);

-- 5 Bussholdeplasser i Halden for rute 3 (random rekkefølge)
INSERT INTO Stops (stop_id, stop_name, distance_to_next_km, time_to_next_stop, desc_next_stop, route_id) VALUES
(21, 'Tista Senter Halden',   3.20, 6,  'Stopper på Rema 1000 Tistedal',  3),
(22, 'Rema 1000 Tistedal',    2.00, 4,  'Stopper på Extra Halden',        3),
(23, 'Extra Halden',          2.30, 4,  'Stopper på Høgskolen i Østfold', 3),
(24, 'Høgskolen i Østfold',   3.80, 7,  'Stopper på Fredriksten Festning',3),
(25, 'Fredriksten Festning',  0.00, NULL, NULL,                           3);

------------------------------------------------------------
-- Ankomst/avreiser tider (5 stk for testing)
------------------------------------------------------------

-- 5 ankomst/avreiser i Halden for rute 1 (original rekkefølge)
INSERT INTO ScheduleTimers (schedule_id, arrival, departure, sequence) VALUES
(1, '08:00:00', '08:03:00', 1),  -- Rema 1000 Tistedal (3 min parkert)
(1, '08:09:00', '08:12:00', 2),  -- Fredriksten Festning (6 min kjøring)
(1, '08:17:00', '08:20:00', 3),  -- Extra Halsen        (5 min kjøring)
(1, '08:23:00', '08:26:00', 4),  -- Tista Senter        (3 min kjøring)
(1, '08:31:00', '08:34:00', 5);  -- Høgskolen i Østfold (5 min kjøring)

-- 5 ankomst/avreiser i Halden for rute 2 (motsatt rekkefølge)
INSERT INTO ScheduleTimers (schedule_id, arrival, departure, sequence) VALUES
(2, '09:00:00', '09:05:00', 1),  -- Høgskolen i Østfold (5 min parkert)
(2, '09:09:00', '09:14:00', 2),  -- Tista Senter (4 min kjøring)
(2, '09:17:00', '09:22:00', 3),  -- Extra Halden (3 min kjøring)
(2, '09:27:00', '09:32:00', 4),  -- Fredriksten  (5 min kjøring)
(2, '09:38:00', '09:43:00', 5);  -- Rema Tistedal (6 min kjøring)

-- 5 ankomst/avreiser i Halden for rute 3 (random rekkefølge)
INSERT INTO ScheduleTimers (schedule_id, arrival, departure, sequence) VALUES
(3, '10:00:00', '10:03:00', 1),  -- Tista Senter Halden (3 min parkert)
(3, '10:09:00', '10:12:00', 2),  -- Rema 1000 Tistedal (6 min kjøring)
(3, '10:16:00', '10:19:00', 3),  -- Extra Halden       (4 min kjøring)
(3, '10:23:00', '10:26:00', 4),  -- Høgskolen          (4 min kjøring)
(3, '10:33:00', '10:36:00', 5);  -- Fredriksten        (7 min kjøring)

------------------------------------------------------------
-- Turer (3 stk for testing)
------------------------------------------------------------

INSERT INTO Trips (trip_id, route_id, schedule_id, bus_id) VALUES
(1, 1, 1, 1),  -- Tistedal → Høgskolen
(2, 2, 2, 3),  -- Høgskolen → Tistedal
(3, 3, 3, 5); -- Tista → Fredriksten

------------------------------------------------------------
-- Sanntid oppdatering (5 stk for testing)
------------------------------------------------------------

-- Originale tider rute 1:: 08:00–08:03 → 08:09–08:12 → 08:17–08:20 → 08:23–08:26 → 08:31–08:34
INSERT INTO RealtimeScheduling (trip_id, stop_id, updated_arrival, updated_departure, status) VALUES
(1, 1, '08:02:00', '08:05:00', 'Forsinket ankomst: 2 min'), -- Rema 1000 Tistedal
(1, 2, '08:12:00', '08:16:00', 'Forsinket 3-4 min'), -- Fredriksten Festning
(1, 3, '08:17:00', '08:20:00', 'Ingen forsinkelser'), -- Extra Halsen
(1, 4, NULL, NULL, 'Kansellert'), -- Tista Senter Halden
(1, 5, '08:36:00', '08:40:00', 'Forsinket ankomst: 5 min'); -- Høgskolen i Østfold

-- Originale tider rute 2: 09:00–09:05 → 09:09–09:14 → 09:17–09:22 → 09:27–09:32 → 09:38–09:43
INSERT INTO RealtimeScheduling (trip_id, stop_id, updated_arrival, updated_departure, status) VALUES
(2, 11, '09:00:00', '09:05:00', NULL), -- Høgskolen i Østfold
(2, 12, '09:10:00', '09:15:00', 'Forsinket ankomst: 1 min'), -- Tista Senter Halden
(2, 13, '09:19:00', '09:25:00', 'Forsinket ankomst: 2 min'), -- Extra Halden
(2, 14, NULL, NULL, 'Kansellert: veg arbeid'), -- Fredriksten Festning
(2, 15, '09:45:00', '09:50:00', 'Forsinket ankomst: 7 min');        -- Rema 1000 Tistedal

-- Originale tider rute 3: 10:00–10:03 → 10:09–10:12 → 10:16–10:19 → 10:23–10:26 → 10:33–10:36
INSERT INTO RealtimeScheduling (trip_id, stop_id, updated_arrival, updated_departure, status) VALUES
(3, 21, '10:00:00', '10:04:00', NULL),              -- Tista Senter Halden
(3, 22, '10:11:00', '10:16:00', 'Forsinket 2-3 min'),      -- Rema 1000 Tistedal
(3, 23, '10:18:00', '10:22:00', 'Forsinket ankomst 2 min'),        -- Extra Halden
(3, 24, '10:25:00', '10:29:00', NULL),              -- Høgskolen i Østfold
(3, 25, NULL, NULL, 'Ingen sanntid data tilgjengelig');    -- Fredriksten Festning