CREATE TABLE account (
     account_id uuid PRIMARY KEY,
     name text NOT NULL,
     email text NOT NULL,
     cpf text NOT NULL,
     car_plate text NULL,
     IS_PASSENGER boolean NOT NULL,
     is_driver boolean NOT NULL,
     created_at timestamp,
     updated_at timestamp
);