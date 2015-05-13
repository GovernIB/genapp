

CREATE SEQUENCE dem_demogenapp_seq INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1000;

CREATE TABLE dem_fitxer (
    fitxerid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(45) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);

CREATE TABLE dem_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);

CREATE TABLE dem_traduccio (
    traduccioid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL
);

CREATE TABLE dem_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);

ALTER TABLE ONLY dem_fitxer
    ADD CONSTRAINT dem_fitxer_pk PRIMARY KEY (fitxerid);

ALTER TABLE ONLY dem_idioma
    ADD CONSTRAINT dem_idioma_pk PRIMARY KEY (idiomaid);

ALTER TABLE ONLY dem_traduccio
    ADD CONSTRAINT dem_traduccio_pk PRIMARY KEY (traduccioid);

ALTER TABLE ONLY dem_traducciomap
    ADD CONSTRAINT dem_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);

CREATE INDEX dem_fitxer_pk_i ON dem_fitxer USING btree (fitxerid);

CREATE INDEX dem_idioma_pk_i ON dem_idioma USING btree (idiomaid);

CREATE INDEX dem_traduccio_pk_i ON dem_traduccio USING btree (traduccioid);

CREATE INDEX dem_traducciomap_idiomaid_fk_i ON dem_traducciomap USING btree (idiomaid);

CREATE INDEX dem_traducciomap_pk_i ON dem_traducciomap USING btree (traducciomapid);

ALTER TABLE ONLY dem_traducciomap
    ADD CONSTRAINT dem_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES dem_traduccio(traduccioid);


