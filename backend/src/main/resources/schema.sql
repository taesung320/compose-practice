CREATE TABLE IF NOT EXISTS example.products (
    id INTEGER AUTO_INCREMENT,
    title varchar(50) NOT NULL,
    description varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS example.files (
    id INTEGER AUTO_INCREMENT,
    name varchar(200) NOT NULL,
    uuid varchar(200) NOT NULL,
    path varchar(200) NOT NULL,
    PRIMARY KEY (id)
);