CREATE TABLE IF NOT EXISTS CARS (
                                     id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                     owner_id INT null,
                                     name VARCHAR(255),
                                     category VARCHAR(255),
                                     brand VARCHAR(255),
                                     description VARCHAR(255),
                                     mfc_date DATE,
                                     price DOUBLE PRECISION,
                                     sold_date TIMESTAMP
);