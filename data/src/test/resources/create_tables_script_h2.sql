DROP TABLE IF EXISTS gift_certificate_has_tag;
DROP TABLE IF EXISTS tb_gift_certificates;
DROP TABLE IF EXISTS tb_tags;

CREATE TABLE tb_gift_certificates
(
    id               BIGINT                  NOT NULL AUTO_INCREMENT,
    name             VARCHAR(100)            NOT NULL,
    price            NUMERIC                 NOT NULL,
    duration         TIMESTAMP               NOT NULL,
    description      VARCHAR,
    create_date      TIMESTAMP DEFAULT now() NOT NULL,
    last_update_date TIMESTAMP,

    CONSTRAINT pk_gift_certificate_id PRIMARY KEY (id),
    CONSTRAINT unique_certificate_id UNIQUE (id),
    CONSTRAINT unique_certificate_name UNIQUE (name)
);

CREATE TABLE tb_tags
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,

    CONSTRAINT pk_tag_id PRIMARY KEY (id),
    CONSTRAINT unique_tag_id UNIQUE (id),
    CONSTRAINT unique_tag_name UNIQUE (name)
);

CREATE TABLE gift_certificate_has_tag
(
    gift_certificate_id BIGINT NOT NULL,
    tag_id              BIGINT NOT NULL,

    CONSTRAINT pk_gift_certificate_has_tag_composite PRIMARY KEY (gift_certificate_id, tag_id),
    CONSTRAINT fk_gift_certificate_has_tag_to_gift_certificates FOREIGN KEY (gift_certificate_id) REFERENCES tb_gift_certificates (id),
    CONSTRAINT fk_gift_certificate_has_tag_to_tags FOREIGN KEY (tag_id) REFERENCES tb_tags (id)

);



