

CREATE SEQUENCE gas_fitxer_seq INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1000;
CREATE SEQUENCE gas_traduccio_seq INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1000;

CREATE TABLE gas_fitxer (
    fitxerid bigint DEFAULT nextval('gas_fitxer_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(255) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);

CREATE TABLE gas_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);

CREATE TABLE gas_traduccio (
    traduccioid bigint DEFAULT nextval('gas_traduccio_seq'::regclass) NOT NULL
);

CREATE TABLE gas_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);

ALTER TABLE ONLY gas_fitxer
    ADD CONSTRAINT gas_fitxer_pk PRIMARY KEY (fitxerid);

ALTER TABLE ONLY gas_idioma
    ADD CONSTRAINT gas_idioma_pk PRIMARY KEY (idiomaid);

ALTER TABLE ONLY gas_traduccio
    ADD CONSTRAINT gas_traduccio_pk PRIMARY KEY (traduccioid);

ALTER TABLE ONLY gas_traducciomap
    ADD CONSTRAINT gas_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);

CREATE INDEX gas_fitxer_pk_i ON gas_fitxer USING btree (fitxerid);

CREATE INDEX gas_idioma_pk_i ON gas_idioma USING btree (idiomaid);

CREATE INDEX gas_traduccio_pk_i ON gas_traduccio USING btree (traduccioid);

CREATE INDEX gas_traducciomap_idiomaid_fk_i ON gas_traducciomap USING btree (idiomaid);

CREATE INDEX gas_traducciomap_pk_i ON gas_traducciomap USING btree (traducciomapid);

ALTER TABLE ONLY gas_traducciomap
    ADD CONSTRAINT gas_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES gas_traduccio(traduccioid);

INSERT INTO gas_idioma(idiomaid, nom, ordre) VALUES ('ca', 'Català', 0);
INSERT INTO gas_idioma(idiomaid, nom, ordre) VALUES ('es', 'Castellano', 1);
INSERT INTO gas_idioma(idiomaid, nom, ordre) VALUES ('en', 'English', 2);
    
    
