CREATE TABLE "user"
(
    id            BIGSERIAL PRIMARY KEY,
    username      VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    user_type     VARCHAR(6)          NOT NULL
);

CREATE TABLE receipt
(
    id           BIGSERIAL PRIMARY KEY,
    user_id      BIGINT         NOT NULL,
    data         OID            NOT NULL,
    date         TIMESTAMP      NOT NULL DEFAULT NOW(),
    total_sum    DECIMAL(10, 2) NOT NULL,
    retail_place VARCHAR(255),

    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE
);

CREATE TABLE category
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE product
(
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT NOT NULL,
    receipt_id  BIGINT NOT NULL,
    name        VARCHAR(255),
    price       DECIMAL(10, 2),
    quantity    FLOAT  NOT NULL,
    total       DECIMAL(10, 2),
    category_id BIGINT NOT NULL,

    FOREIGN KEY (receipt_id) REFERENCES receipt (id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user" (id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES category (id) ON DELETE CASCADE
);