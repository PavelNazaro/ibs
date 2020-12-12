drop table if exists documents;
create table documents (
  id                    bigserial NOT NULL UNIQUE,
  first_company            VARCHAR(50),
  second_company           VARCHAR(50),
  first_signature            boolean,
  second_signature           boolean,
  PRIMARY KEY (id)
);