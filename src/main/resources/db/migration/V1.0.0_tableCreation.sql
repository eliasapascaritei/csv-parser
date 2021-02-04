CREATE TABLE opportunities(
    opportunity_id     varchar PRIMARY KEY,
    customer_name      varchar NOT NULL,
    booking_date       timestamp without time zone NOT NULL,
    booking_type       varchar NOT NULL,
    total              double precision NOT NULL,
    account_executive  varchar NOT NULL,
    sales_organization varchar NOT NULL,
    team               varchar NOT NULL,
    product            varchar NOT NULL,
    renewable          varchar NOT NULL
);

CREATE TABLE files_meta(
    id            serial PRIMARY KEY,
    file_name     varchar NOT NULL,
    creation_date timestamp without time zone NOT NULL,
    size          long,
    upload_date   timestamp without time zone NOT NULL
);