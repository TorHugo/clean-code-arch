create table cccat15.position (
      position_id UUID PRIMARY KEY,
      ride_id UUID,
      lat numeric,
      long numeric,
      created_at timestamp,
      updated_at timestamp
);