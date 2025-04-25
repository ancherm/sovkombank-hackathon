CREATE TABLE "user"
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL
);

CREATE TABLE receipt
(
    id      BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    data    OID    NOT NULL,

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE product
(
    id         BIGSERIAL PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    receipt_id BIGINT NOT NULL,
    name       VARCHAR(255),
    price      DECIMAL(10, 2),
    category   VARCHAR(255),

    FOREIGN KEY (receipt_id) REFERENCES receipt (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);