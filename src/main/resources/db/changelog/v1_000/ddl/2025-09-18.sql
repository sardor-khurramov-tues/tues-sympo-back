--liquibase formatted sql

create table attendee (
    id              bigserial           primary key,
    email           varchar(127)        not null    unique,
    phone           varchar(15)         not null,
    register_time   timestamp           not null,
    first_name      varchar(63)         not null,
    last_name       varchar(63)         not null,
    affiliation     varchar(255)        not null,
    job_position    varchar(255)        not null,
    degree          varchar(31)         not null,
    field           varchar(127)        not null,
    country         varchar(127)        not null,
    pres_title      varchar(127),
    pres_abstract   varchar(2000),
    comments        varchar(2000)
);
