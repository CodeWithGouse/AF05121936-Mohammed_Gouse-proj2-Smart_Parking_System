CREATE DATABASE parking_db;
USE parking_db;

CREATE TABLE parking_slots (
    slot_id INT PRIMARY KEY AUTO_INCREMENT,
    slot_number VARCHAR(10),
    is_available BOOLEAN DEFAULT TRUE
);

CREATE TABLE vehicles (
    vehicle_id INT PRIMARY KEY AUTO_INCREMENT,
    vehicle_number VARCHAR(20),
    vehicle_type VARCHAR(20),
    entry_time DATETIME,
    exit_time DATETIME,
    slot_id INT,
    FOREIGN KEY (slot_id) REFERENCES parking_slots(slot_id)
);

INSERT INTO parking_slots (slot_number) VALUES
('A1'), ('A2'), ('A3'), ('B1'), ('B2');



INSERT INTO vehicles (vehicle_number, vehicle_type, entry_time, exit_time, slot_id) VALUES
('KA09QR7788', 'Truck', '2026-03-15 06:00:00', '2026-04-27 09:00:00', 4),
('KA10ST9900', 'Car', '2026-03-20 08:30:00', NULL, 5),
('KA11UV2233', 'Bike', '2026-04-29 13:00:00', NULL, 1),
('KA12WX4455', 'Car', '2026-04-29 14:15:00', NULL, 2);
