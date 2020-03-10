--
-- PostgreSQL database dump
--

-- Dumped from database version 8.4.14
-- Dumped by pg_dump version 9.3.1
-- Started on 2014-08-25 12:39:21

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

SET search_path = ${name}, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 150 (class 1259 OID 69544)
-- Name: ${prefixLowercase}_fitxer; Type: TABLE; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE TABLE ${prefixLowercase}_fitxer (
    fitxerid bigint DEFAULT nextval('${prefixLowercase}_${name}_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(45) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);


ALTER TABLE ${name}.${prefixLowercase}_fitxer OWNER TO ${name};

--
-- TOC entry 169 (class 1259 OID 92635)
-- Name: ${prefixLowercase}_idioma; Type: TABLE; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE TABLE ${prefixLowercase}_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);


ALTER TABLE ${name}.${prefixLowercase}_idioma OWNER TO ${name};

--
-- TOC entry 184 (class 1259 OID 210385)
-- Name: ${prefixLowercase}_traduccio; Type: TABLE; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE TABLE ${prefixLowercase}_traduccio (
    traduccioid bigint DEFAULT nextval('${prefixLowercase}_${name}_seq'::regclass) NOT NULL
);


ALTER TABLE ${name}.${prefixLowercase}_traduccio OWNER TO ${name};

--
-- TOC entry 183 (class 1259 OID 210326)
-- Name: ${prefixLowercase}_traducciomap; Type: TABLE; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE TABLE ${prefixLowercase}_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(5) NOT NULL,
    valor character varying(4000)
);


ALTER TABLE ${name}.${prefixLowercase}_traducciomap OWNER TO ${name};

--
-- TOC entry 1836 (class 2606 OID 70326)
-- Name: ${prefixLowercase}_fitxer_pk; Type: CONSTRAINT; Schema: ${name}; Owner: ${name}; Tablespace: 
--

ALTER TABLE ONLY ${prefixLowercase}_fitxer
    ADD CONSTRAINT ${prefixLowercase}_fitxer_pk PRIMARY KEY (fitxerid);


--
-- TOC entry 1839 (class 2606 OID 96099)
-- Name: ${prefixLowercase}_idioma_pk; Type: CONSTRAINT; Schema: ${name}; Owner: ${name}; Tablespace: 
--

ALTER TABLE ONLY ${prefixLowercase}_idioma
    ADD CONSTRAINT ${prefixLowercase}_idioma_pk PRIMARY KEY (idiomaid);


--
-- TOC entry 1846 (class 2606 OID 210396)
-- Name: ${prefixLowercase}_traduccio_pk; Type: CONSTRAINT; Schema: ${name}; Owner: ${name}; Tablespace: 
--

ALTER TABLE ONLY ${prefixLowercase}_traduccio
    ADD CONSTRAINT ${prefixLowercase}_traduccio_pk PRIMARY KEY (traduccioid);


--
-- TOC entry 1842 (class 2606 OID 210501)
-- Name: ${prefixLowercase}_traducciomap_pk; Type: CONSTRAINT; Schema: ${name}; Owner: ${name}; Tablespace: 
--

ALTER TABLE ONLY ${prefixLowercase}_traducciomap
    ADD CONSTRAINT ${prefixLowercase}_traducciomap_pk PRIMARY KEY (traducciomapid, idiomaid);


--
-- TOC entry 1837 (class 1259 OID 202159)
-- Name: ${prefixLowercase}_fitxer_pk_i; Type: INDEX; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE INDEX ${prefixLowercase}_fitxer_pk_i ON ${prefixLowercase}_fitxer USING btree (fitxerid);


--
-- TOC entry 1840 (class 1259 OID 202163)
-- Name: ${prefixLowercase}_idioma_pk_i; Type: INDEX; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE INDEX ${prefixLowercase}_idioma_pk_i ON ${prefixLowercase}_idioma USING btree (idiomaid);


--
-- TOC entry 1847 (class 1259 OID 210461)
-- Name: ${prefixLowercase}_traduccio_pk_i; Type: INDEX; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE INDEX ${prefixLowercase}_traduccio_pk_i ON ${prefixLowercase}_traduccio USING btree (traduccioid);


--
-- TOC entry 1843 (class 1259 OID 210529)
-- Name: ${prefixLowercase}_traducmap_idiomaid_pk_i; Type: INDEX; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE INDEX ${prefixLowercase}_traducmap_idiomaid_pk_i ON ${prefixLowercase}_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1844 (class 1259 OID 210528)
-- Name: ${prefixLowercase}_traducmap_tradmapid_pk_i; Type: INDEX; Schema: ${name}; Owner: ${name}; Tablespace: 
--

CREATE INDEX ${prefixLowercase}_traducmap_tradmapid_pk_i ON ${prefixLowercase}_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1848 (class 2606 OID 210469)
-- Name: ${prefixLowercase}_traducmap_traduccio_fk; Type: FK CONSTRAINT; Schema: ${name}; Owner: ${name}
--

ALTER TABLE ONLY ${prefixLowercase}_traducciomap
    ADD CONSTRAINT ${prefixLowercase}_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES ${prefixLowercase}_traduccio(traduccioid);


-- Completed on 2014-08-25 12:39:21

--
-- PostgreSQL database dump complete
--

