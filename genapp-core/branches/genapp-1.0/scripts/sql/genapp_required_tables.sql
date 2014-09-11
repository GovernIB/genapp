--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.14
-- Dumped by pg_dump version 9.3.1
-- Started on 2014-08-20 13:02:35

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = portafib, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 150 (class 1259 OID 69544)
-- Name: pfi_fitxer; Type: TABLE; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE TABLE pfi_fitxer (
    fitxerid bigint DEFAULT nextval('pfi_portafib_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(45) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);


ALTER TABLE portafib.pfi_fitxer OWNER TO portafib;

--
-- TOC entry 169 (class 1259 OID 92635)
-- Name: pfi_idioma; Type: TABLE; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE TABLE pfi_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);


ALTER TABLE portafib.pfi_idioma OWNER TO portafib;

--
-- TOC entry 186 (class 1259 OID 210385)
-- Name: pfi_traduccio; Type: TABLE; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE TABLE pfi_traduccio (
    traduccioid bigint DEFAULT nextval('pfi_portafib_seq'::regclass) NOT NULL
);


ALTER TABLE portafib.pfi_traduccio OWNER TO portafib;

--
-- TOC entry 183 (class 1259 OID 210326)
-- Name: pfi_traducciomap; Type: TABLE; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE TABLE pfi_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);


ALTER TABLE portafib.pfi_traducciomap OWNER TO portafib;

--
-- TOC entry 1842 (class 2606 OID 70326)
-- Name: pfi_fitxer_pk; Type: CONSTRAINT; Schema: portafib; Owner: portafib; Tablespace: 
--

ALTER TABLE ONLY pfi_fitxer
    ADD CONSTRAINT pfi_fitxer_pk PRIMARY KEY (fitxerid);


--
-- TOC entry 1845 (class 2606 OID 96099)
-- Name: pfi_idioma_pk; Type: CONSTRAINT; Schema: portafib; Owner: portafib; Tablespace: 
--

ALTER TABLE ONLY pfi_idioma
    ADD CONSTRAINT pfi_idioma_pk PRIMARY KEY (idiomaid);


--
-- TOC entry 1852 (class 2606 OID 210396)
-- Name: pfi_traduccio_pk; Type: CONSTRAINT; Schema: portafib; Owner: portafib; Tablespace: 
--

ALTER TABLE ONLY pfi_traduccio
    ADD CONSTRAINT pfi_traduccio_pk PRIMARY KEY (traduccioid);


--
-- TOC entry 1850 (class 2606 OID 210479)
-- Name: pfi_traducmap_pk; Type: CONSTRAINT; Schema: portafib; Owner: portafib; Tablespace: 
--

ALTER TABLE ONLY pfi_traducciomap
    ADD CONSTRAINT pfi_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);


--
-- TOC entry 1843 (class 1259 OID 202159)
-- Name: pfi_fitxer_pk_i; Type: INDEX; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE INDEX pfi_fitxer_pk_i ON pfi_fitxer USING btree (fitxerid);


--
-- TOC entry 1846 (class 1259 OID 202163)
-- Name: pfi_idioma_pk_i; Type: INDEX; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE INDEX pfi_idioma_pk_i ON pfi_idioma USING btree (idiomaid);


--
-- TOC entry 1853 (class 1259 OID 210461)
-- Name: pfi_traduccio_pk_i; Type: INDEX; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE INDEX pfi_traduccio_pk_i ON pfi_traduccio USING btree (traduccioid);


--
-- TOC entry 1847 (class 1259 OID 210477)
-- Name: pfi_traducciomap_idiomaid_fk_i; Type: INDEX; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE INDEX pfi_traducciomap_idiomaid_fk_i ON pfi_traducciomap USING btree (idiomaid);


--
-- TOC entry 1848 (class 1259 OID 210373)
-- Name: pfi_traducciomap_pk_i; Type: INDEX; Schema: portafib; Owner: portafib; Tablespace: 
--

CREATE INDEX pfi_traducciomap_pk_i ON pfi_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1854 (class 2606 OID 210469)
-- Name: pfi_traducmap_traduccio_fk; Type: FK CONSTRAINT; Schema: portafib; Owner: portafib
--

ALTER TABLE ONLY pfi_traducciomap
    ADD CONSTRAINT pfi_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES pfi_traduccio(traduccioid);


-- Completed on 2014-08-20 13:02:36

--
-- PostgreSQL database dump complete
--

