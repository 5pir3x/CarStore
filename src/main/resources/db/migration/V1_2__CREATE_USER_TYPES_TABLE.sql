CREATE TABLE IF NOT EXISTS USER_TYPES (
                                 id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
                                 type_name VARCHAR(255) UNIQUE,
                                 description VARCHAR(255)
)