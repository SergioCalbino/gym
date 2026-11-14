CREATE TABLE orders (
                        id         UUID        PRIMARY KEY,
                        member_id  UUID        NOT NULL ,
                        status     VARCHAR(20) NOT NULL ,
                        CONSTRAINT orders_status_valid
                            CHECK (status IN ('PENDING', 'PAID'))
);

CREATE TABLE order_item (
                            id          UUID          PRIMARY KEY,
                            order_id    UUID          NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
                            description VARCHAR(200)  NOT NULL,
                            price       NUMERIC(12,2) NOT NULL,
                            CONSTRAINT order_item_price_not_negative
                                CHECK (price > 0)
    );