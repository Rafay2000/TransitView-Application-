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