
INSERT INTO user_detail (user_name, first_name, last_name, email, phone_number, address) VALUES
('aa', 'Al', 'Sm', 'aa@ex.com',123456,'aabb'),
('bb', 'Bo', 'Jo', 'bb@ex.com',524875,'dkn v'),
('cc', 'Ca', 'Wi', 'cc@ex.com',342495,'sd 123'),
('jj', 'Ja', 'Ma', 'jj@ex.com',656482,'rdn 12435');

INSERT INTO train (train_number, train_name, source, destination) VALUES
(1, 'Euro Express' , 'A','B'),
(2, 'London Runner', 'C','F'),
(3, 'Night liner', 'E','D'),
(4, 'City Connect', 'D','U'),
(5, 'Ocean View', 'V','R'),
(6, 'SkyTrack', 'R','A'),
(7, 'Mountain Rail', 'J','T'),
(8, 'Desert Storm', 'U','O'),
(9, 'Valley Express', 'P','S'),
(10, 'Coastal Ride', 'S','P');

INSERT INTO section (section_id, name, train_number) VALUES
(1, 'A', 1), (2, 'B', 1),
(3, 'A', 2), (4, 'B', 2),
(5, 'A', 3), (6, 'B', 3),
(7, 'A', 4), (8, 'B', 4),
(9, 'A', 5), (10, 'B', 5);

INSERT INTO seat (id, seat_number, is_allocated, section_id) VALUES
(1, 'A1', true, 1), (2, 'A2', false, 1), (3, 'A3', false, 1), (4, 'A4', false, 1), (5, 'A5', false, 1),
(6, 'B1', true, 2), (7, 'B2', false, 2), (8, 'B3', false, 2), (9, 'B4', false, 2), (10, 'B5', false, 2),
(11, 'A1', false, 3), (12, 'A2', false, 3), (13, 'A3', false, 3), (14, 'A4', false, 3), (15, 'A5', false, 3),
(16, 'B1', true, 4), (17, 'B2', false, 4), (18, 'B3', false, 4), (19, 'B4', false, 4), (20, 'B5', false, 4),
(21, 'A1', true, 5), (22, 'A2', false, 5), (23, 'A3', false, 5), (24, 'A4', false, 5), (25, 'A5', false, 5),
(26, 'B1', false, 6), (27, 'B2', false, 6), (28, 'B3', false, 6), (29, 'B4', false, 6), (30, 'B5', false, 6),
(31, 'A1', false, 7), (32, 'A2', false, 7), (33, 'A3', false, 7), (34, 'A4', false, 7), (35, 'A5', false, 7),
(36, 'B1', false, 8), (37, 'B2', false, 8), (38, 'B3', false, 8), (39, 'B4', false, 8), (40, 'B5', false, 8),
(41, 'A1', false, 9), (42, 'A2', false, 9), (43, 'A3', false, 9), (44, 'A4', false, 9), (45, 'A5', false, 9),
(46, 'B1', false, 10), (47, 'B2', false, 10), (48, 'B3', false, 10), (49, 'B4', false, 10), (50, 'B5', false, 10);


INSERT INTO booking (pnr_number, start, destination, user_name, seat_id, train_number, section_name, fare, travel_date) VALUES
(11102, 'LD', 'FR', 'aa', 1, 1,'A', 20.0, '2025-04-18'),
(21102, 'LD', 'FR', 'bb', 16, 2,'B', 20.0, '2025-04-12'),
(31102, 'LD', 'FR', 'cc', 21, 3,'A', 20.0, '2025-04-15'),
(41102, 'LD', 'FR', 'jj', 6, 1,'B', 20.0, '2025-04-11');
