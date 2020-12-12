drop table if exists documents;
create table documents (
  id                    bigserial NOT NULL UNIQUE,
  first_side            VARCHAR(50),
  second_side           VARCHAR(50),
  PRIMARY KEY (id)
);

-- insert into documents (first_side, second_side)
-- values
-- ('Ivan', 'Pavel');