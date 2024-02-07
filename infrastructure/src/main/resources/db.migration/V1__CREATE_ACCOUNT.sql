CREATE SCHEMA cccat15;

CREATE TABLE cccat15.account (
     account_id uuid PRIMARY KEY,
     name text NOT NULL,
     email text NOT NULL,
     cpf text NOT NULL,
     car_plate text NULL,
     is_passenger boolean NOT NULL DEFAULT false,
     is_driver boolean NOT NULL DEFAULT false,
     created_at timestamp,
     updated_at timestamp
);