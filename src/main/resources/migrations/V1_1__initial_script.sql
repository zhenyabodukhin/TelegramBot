create table m_cities
(
    id          bigserial    not null
        constraint m_cities_pk
            primary key,
    city_name   varchar(50)  not null,
    description varchar(255) not null
);

alter table m_cities
    owner to postgres;

create unique index m_cities_city_name_uindex
    on m_cities (city_name);

create unique index m_cities_id_uindex
    on m_cities (id);