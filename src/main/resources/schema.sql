CREATE SEQUENCE IF NOT EXISTS store_seq
    MINVALUE 50
    MAXVALUE 999999999
    INCREMENT BY 1
    START WITH 51
    NO CACHE
    NO CYCLE;

CREATE TABLE IF NOT EXISTS store (
    id BIGINT PRIMARY KEY,
    external_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(24) NOT NULL,
    street_number VARCHAR(8) NOT NULL,
    street_name VARCHAR(255) NOT NULL,
    zip_code VARCHAR(16) NOT NULL,
    city VARCHAR(24) NOT NULL,
    country VARCHAR(36) NOT NULL,
    created_by VARCHAR(255) NOT NULL DEFAULT 'ADMIN',
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (external_id),
    UNIQUE (code)
);

CREATE SEQUENCE IF NOT EXISTS product_seq
    INCREMENT BY 1
    MINVALUE 50
    START WITH 51
    NO CYCLE;

CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    external_id VARCHAR(36) NOT NULL,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(24) NOT NULL,
    type VARCHAR(24) NOT NULL,
    created_by VARCHAR(255) NOT NULL DEFAULT 'ADMIN',
    created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (external_id),
    UNIQUE (code)
    );

CREATE SEQUENCE IF NOT EXISTS store_item_seq
    INCREMENT BY 1
    MINVALUE 50
    START WITH 51
    NO CYCLE;

CREATE TABLE IF NOT EXISTS store_item (
     id BIGINT PRIMARY KEY,
     external_id VARCHAR(36) NOT NULL,
     name VARCHAR(255) NOT NULL,
     store_id BIGINT,
     product_id BIGINT,
     item_quantity BIGINT DEFAULT '0',
     created_by VARCHAR(255) NOT NULL DEFAULT 'ADMIN',
     created_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT FK_store FOREIGN KEY(store_id) REFERENCES store(id),
     CONSTRAINT FK_product FOREIGN KEY(product_id) REFERENCES product(id),
     UNIQUE (external_id),
     UNIQUE (name, store_id, product_id)
);