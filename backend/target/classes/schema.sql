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

CREATE TABLE IF NOT EXISTS example.comtnbloguser (
    BLOG_ID char(20) NOT NULL,
    EMPLYR_ID varchar(20) NOT NULL,
    MNGR_AT	char(1)	NOT NULL,
    MBER_STTUS varchar(15),
    SBSCRB_DE datetime,
    SECSN_DE char(2),
    USE_AT char(1),
    FRST_REGIST_PNTTM datetime NOT NULL,
    FRST_REGISTER_ID varchar(20) NOT NULL,
    LAST_UPDT_PNTTM datetime,
    LAST_UPDUSR_ID varchar(20),
    PRIMARY KEY (BLOG_ID, EMPLYR_ID)
);