CREATE TABLE IF NOT EXISTS player (
    id INT NOT NULL,
    firstName varchar(250) NOT NULL,
    lastName varchar(250) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS teamsInformation (
    id INT NOT NULL,
    name varchar(250) NOT NULL,
    officialName varchar(250) NOT NULL,
    stadiumName varchar(250) NOT NULL,
    PRIMARY KEY (id)
);