CREATE TABLE gym_class (
           id             UUID         PRIMARY KEY,
           name           VARCHAR(100) NOT NULL,
           capacity       INTEGER      NOT NULL,
           occupied_spots INTEGER      NOT NULL DEFAULT 0,
           CONSTRAINT gym_class_capacity_positive
               CHECK (capacity > 0),
           CONSTRAINT gym_class_occupied_in_range
               CHECK (occupied_spots >= 0 AND occupied_spots <= capacity)
);

CREATE TABLE reservation (
             id            UUID        PRIMARY KEY,
             member_id     UUID        NOT NULL,
             gym_class_id  UUID        NOT NULL REFERENCES gym_class(id),
             created_at    TIMESTAMPTZ NOT NULL,
             status        VARCHAR(20) NOT NULL,
             CONSTRAINT reservation_status_valid
                 CHECK (status IN ('ACTIVE', 'CANCELLED'))
);

-- Un socio no puede tener dos reservas ACTIVAS en la misma clase.
-- Las canceladas no cuentan: si cancela, puede volver a reservar.
CREATE UNIQUE INDEX reservation_one_active_per_member_and_class
    ON reservation (member_id, gym_class_id)
    WHERE status = 'ACTIVE';