CREATE TABLE Users (
    Id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Type integer NOT NULL CHECK (Type >= 0),
    Name varchar(20) NOT NULL,
    Surname varchar(20),
    Email varchar(20) NOT NULL UNIQUE,
    Login varchar(20) NOT NULL UNIQUE,
    Password varchar(20) NOT NULL
);

CREATE TABLE Stores (
    Id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Name varchar(20) NOT NULL,
    Url varchar(100) NOT NULL UNIQUE,
    SearchUrl varchar(100) NOT NULL UNIQUE,
    PropertyFile varchar(50) NOT NULL UNIQUE
);

CREATE TABLE SearchHistory (
    Id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    Query varchar(100) NOT NULL,
    Date date NOT NULL,
    Rating integer NOT NULL CHECK (Rating >= 0)
);