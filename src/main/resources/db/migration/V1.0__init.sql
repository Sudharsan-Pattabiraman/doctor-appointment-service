CREATE TABLE doctor
(
    id             INT AUTO_INCREMENT,
    doctor_name    VARCHAR(255),
    available_from TIME,
    available_till TIME,

    CONSTRAINT doctor_id_pk PRIMARY KEY (id)
);

CREATE TABLE appointment
(
    id               INT AUTO_INCREMENT,
    doctor_id        INT,
    patient_name     VARCHAR(255),
    appointment_date DATE,
    appointment_time TIME,
    appointment_id   VARCHAR(255),
    status           VARCHAR(55),
    created_datetime DATETIME,

    CONSTRAINT doctor_id_fk FOREIGN KEY (doctor_id) REFERENCES doctor (id)
);

CREATE INDEX doctor_name_idx ON doctor (doctor_name);
CREATE UNIQUE INDEX appointment_id_idx on appointment (appointment_id);