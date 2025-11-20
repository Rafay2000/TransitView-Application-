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