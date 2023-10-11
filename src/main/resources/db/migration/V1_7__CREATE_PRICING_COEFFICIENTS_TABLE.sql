CREATE TABLE IF NOT EXISTS PRICING_COEFFICIENTS (
                                                   brand VARCHAR(255),
                                                   category VARCHAR(255),
                                                   mfg_date_coefficient DOUBLE PRECISION,
                                                   category_coefficient DOUBLE PRECISION,
                                                   base_price DOUBLE PRECISION,
                                                   PRIMARY KEY (category, brand)
);
