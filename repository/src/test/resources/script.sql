create table if not exists tb_gift_certificates
(
    id               IDENTITY bigint not null unique
        constraint pk_gc_id
        primary key,
    name             varchar(100)            not null unique,
    price            numeric                 not null,
    duration         timestamp               not null,
    description      varchar                 not null,
    create_date      timestamp default now() not null,
    last_update_date timestamp
);

create table if not exists tb_tags
(
    id   IDENTITY bigint not null unique
        constraint pk_tg_id
        primary key,
    name varchar(100)
        constraint unique_tag_name
            unique
);

create table if not exists gift_certificate_has_tag
(
    gift_certificate_id bigint not null
        constraint fk_gcht_to_gc
            references tb_gift_certificates,
    tag_id              bigint not null
        constraint fk_gcht_to_tg
            references tb_tags,
    constraint pk_gc_tg_ids
        primary key (gift_certificate_id, tag_id)
);
