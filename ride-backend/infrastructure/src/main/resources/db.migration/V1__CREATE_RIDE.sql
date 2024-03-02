DROP TABLE IF EXISTS cccat15.ride;
CREATE TABLE cccat15.ride (
      ride_id uuid PRIMARY KEY,
      passenger_id uuid,
      driver_id uuid,
      status text,
      fare numeric,
      distance numeric,
      from_lat numeric,
      from_long numeric,
      to_lat numeric,
      to_long numeric,
      last_lat numeric,
      last_long numeric,
      created_at timestamp,
      updated_at timestamp
);