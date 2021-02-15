CREATE TABLE orders (
    id BIGINT NOT NULL PRIMARY KEY,
    order_id VARCHAR(100) NOT NULL,
    order_date VARCHAR(2) NOT NULL,
    user_email VARCHAR(100) NOT NULL,
    order_lines_str VARCHAR(100) NOT NULL,
    last_modified DATETIME(6),
    status  VARCHAR(2) NOT NULL,
    retry_count INT(4) NOT NULL
);

CREATE TABLE files (
    id BIGINT NOT NULL PRIMARY KEY,
    full_path VARCHAR(200) NOT NULL,
    last_modified DATETIME(6),
    status  VARCHAR(2) NOT NULL,
    retry_count INT(4) NOT NULL
);


CREATE SEQUENCE orders_seq
  MINVALUE 1
  START WITH 50
  INCREMENT BY 50;


CREATE SEQUENCE files_seq
  MINVALUE 1
  START WITH 50
  INCREMENT BY 50;
