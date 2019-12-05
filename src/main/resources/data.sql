DROP TABLE IF EXISTS employee;

CREATE TABLE employee (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  profession_id INT,
  department_id INT
);

INSERT INTO employee (name, description) VALUES
  ('ivanov', 'sys.admin.'),
  ('petrov', 'chief economist'),
  ('sidirov', 'chief engineer');


DROP TABLE IF EXISTS profession;

CREATE TABLE profession (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL
);

INSERT INTO profession (name, description) VALUES
  ('sys.admin.', 'sys.admin.desc.'),
  ('chief economist', 'chief economist desc.'),
  ('chief engineer', 'chief engineer desc.');

DROP TABLE IF EXISTS department;

CREATE TABLE  department (
  id INT AUTO_INCREMENT  PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  parentDept INT
);

INSERT INTO department (name, description) VALUES
  ('IT', 'IT dept.'),
  ('Economics', 'Economics dept.'),
  ('Technical', 'Technical dept.');
