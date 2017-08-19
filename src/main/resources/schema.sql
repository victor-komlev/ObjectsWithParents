CREATE TABLE IF NOT EXISTS objects_test (
  id VARCHAR NOT NULL,
  object_value VARCHAR(100) NOT NULL,
  parent_id VARCHAR,
  PRIMARY KEY (id),
  FOREIGN KEY (parent_id) REFERENCES objects_test(id)
);