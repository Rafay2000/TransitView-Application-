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
