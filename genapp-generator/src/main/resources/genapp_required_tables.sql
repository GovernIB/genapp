

CREATE SEQUENCE PFX_fitxer_seq INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1000;
CREATE SEQUENCE PFX_traduccio_seq INCREMENT 1  MINVALUE 1  MAXVALUE 9223372036854775807  START 1000;

CREATE TABLE PFX_fitxer (
    fitxerid bigint DEFAULT nextval('PFX_fitxer_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(255) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);

CREATE TABLE PFX_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);

CREATE TABLE PFX_traduccio (
    traduccioid bigint DEFAULT nextval('PFX_traduccio_seq'::regclass) NOT NULL
);

CREATE TABLE PFX_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);

ALTER TABLE ONLY PFX_fitxer
    ADD CONSTRAINT PFX_fitxer_pk PRIMARY KEY (fitxerid);

ALTER TABLE ONLY PFX_idioma
    ADD CONSTRAINT PFX_idioma_pk PRIMARY KEY (idiomaid);

ALTER TABLE ONLY PFX_traduccio
    ADD CONSTRAINT PFX_traduccio_pk PRIMARY KEY (traduccioid);

ALTER TABLE ONLY PFX_traducciomap
    ADD CONSTRAINT PFX_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);

CREATE INDEX PFX_fitxer_pk_i ON PFX_fitxer USING btree (fitxerid);

CREATE INDEX PFX_idioma_pk_i ON PFX_idioma USING btree (idiomaid);

CREATE INDEX PFX_traduccio_pk_i ON PFX_traduccio USING btree (traduccioid);

CREATE INDEX PFX_traducciomap_idiomaid_fk_i ON PFX_traducciomap USING btree (idiomaid);

CREATE INDEX PFX_traducciomap_pk_i ON PFX_traducciomap USING btree (traducciomapid);

ALTER TABLE ONLY PFX_traducciomap
    ADD CONSTRAINT PFX_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES PFX_traduccio(traduccioid);

INSERT INTO PFX_idioma(idiomaid, nom, ordre) VALUES ('ca', 'Català', 0);
INSERT INTO PFX_idioma(idiomaid, nom, ordre) VALUES ('es', 'Castellano', 1);
INSERT INTO PFX_idioma(idiomaid, nom, ordre) VALUES ('en', 'English', 2);
    
    
