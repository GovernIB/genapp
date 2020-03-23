--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.5
-- Dumped by pg_dump version 9.3.5
-- Started on 2017-02-22 11:43:29

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;



--
-- TOC entry 170 (class 1259 OID 46117)
-- Name: dem_demogenapp_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE dem_demogenapp_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 46160)
-- Name: dem_alumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_alumne (
    alumneid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL,
    idiomaid character varying(2) NOT NULL,
    actiu boolean DEFAULT true NOT NULL,
    datanaixement date NOT NULL,
    despertador time without time zone,
    darreracces timestamp without time zone,
    fotoid bigint,
    titolacademicid bigint,
    paginaweb character varying(255)
);


--
-- TOC entry 176 (class 1259 OID 46170)
-- Name: dem_assignatura; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_assignatura (
    assignaturaid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL,
    credits integer NOT NULL,
    diasetmana integer,
    hora time without time zone
);



--
-- TOC entry 177 (class 1259 OID 46179)
-- Name: dem_assignaturaalumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_assignaturaalumne (
    id bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    alumneid bigint NOT NULL,
    assignaturaid bigint NOT NULL,
    nota double precision
);


--
-- TOC entry 171 (class 1259 OID 46119)
-- Name: dem_fitxer; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_fitxer (
    fitxerid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(45) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);


--
-- TOC entry 172 (class 1259 OID 46127)
-- Name: dem_idioma; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);



--
-- TOC entry 173 (class 1259 OID 46132)
-- Name: dem_traduccio; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_traduccio (
    traduccioid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL
);



--
-- TOC entry 174 (class 1259 OID 46136)
-- Name: dem_traducciomap; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_traducciomap (
    traducciomapid bigint DEFAULT nextval('dem_demogenapp_seq'::regclass) NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);


--
-- TOC entry 1875 (class 2606 OID 46169)
-- Name: dem_alumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_pk PRIMARY KEY (alumneid);


--
-- TOC entry 1884 (class 2606 OID 67445)
-- Name: dem_assigalumn_multiple_uk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_multiple_uk UNIQUE (alumneid, assignaturaid);


--
-- TOC entry 1879 (class 2606 OID 46175)
-- Name: dem_assignatura_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignatura
    ADD CONSTRAINT dem_assignatura_pk PRIMARY KEY (assignaturaid);


--
-- TOC entry 1886 (class 2606 OID 46184)
-- Name: dem_assignaturaalumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assignaturaalumne_pk PRIMARY KEY (id);


--
-- TOC entry 1860 (class 2606 OID 46143)
-- Name: dem_fitxer_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_fitxer
    ADD CONSTRAINT dem_fitxer_pk PRIMARY KEY (fitxerid);


--
-- TOC entry 1863 (class 2606 OID 46145)
-- Name: dem_idioma_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_idioma
    ADD CONSTRAINT dem_idioma_pk PRIMARY KEY (idiomaid);


--
-- TOC entry 1866 (class 2606 OID 46147)
-- Name: dem_traduccio_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_traduccio
    ADD CONSTRAINT dem_traduccio_pk PRIMARY KEY (traduccioid);


--
-- TOC entry 1871 (class 2606 OID 46149)
-- Name: dem_traducmap_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_traducciomap
    ADD CONSTRAINT dem_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);


--
-- TOC entry 1872 (class 1259 OID 67459)
-- Name: dem_alumne_fotoid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_fotoid_fk_i ON dem_alumne USING btree (fotoid);


--
-- TOC entry 1873 (class 1259 OID 67458)
-- Name: dem_alumne_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_idiomaid_fk_i ON dem_alumne USING btree (idiomaid);


--
-- TOC entry 1876 (class 1259 OID 67457)
-- Name: dem_alumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_pk_i ON dem_alumne USING btree (alumneid);


--
-- TOC entry 1877 (class 1259 OID 67460)
-- Name: dem_alumne_titolaca_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_titolaca_fk_i ON dem_alumne USING btree (titolacademicid);


--
-- TOC entry 1881 (class 1259 OID 67463)
-- Name: dem_assigalumn_alumneid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_alumneid_fk_i ON dem_assignaturaalumne USING btree (alumneid);


--
-- TOC entry 1882 (class 1259 OID 67464)
-- Name: dem_assigalumn_assigna_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_assigna_fk_i ON dem_assignaturaalumne USING btree (assignaturaid);


--
-- TOC entry 1880 (class 1259 OID 67461)
-- Name: dem_assignatura_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignatura_pk_i ON dem_assignatura USING btree (assignaturaid);


--
-- TOC entry 1887 (class 1259 OID 67462)
-- Name: dem_assignaturaalumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignaturaalumne_pk_i ON dem_assignaturaalumne USING btree (id);


--
-- TOC entry 1861 (class 1259 OID 46150)
-- Name: dem_fitxer_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_fitxer_pk_i ON dem_fitxer USING btree (fitxerid);


--
-- TOC entry 1864 (class 1259 OID 46151)
-- Name: dem_idioma_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_idioma_pk_i ON dem_idioma USING btree (idiomaid);


--
-- TOC entry 1867 (class 1259 OID 46152)
-- Name: dem_traduccio_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traduccio_pk_i ON dem_traduccio USING btree (traduccioid);


--
-- TOC entry 1868 (class 1259 OID 46153)
-- Name: dem_traducciomap_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traducciomap_idiomaid_fk_i ON dem_traducciomap USING btree (idiomaid);


--
-- TOC entry 1869 (class 1259 OID 46154)
-- Name: dem_traducciomap_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traducciomap_pk_i ON dem_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1890 (class 2606 OID 67434)
-- Name: dem_alumne_fitxer_fotoid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_fitxer_fotoid_fk FOREIGN KEY (fotoid) REFERENCES dem_fitxer(fitxerid);


--
-- TOC entry 1889 (class 2606 OID 67429)
-- Name: dem_alumne_idioma_idiomaid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_idioma_idiomaid_fk FOREIGN KEY (idiomaid) REFERENCES dem_idioma(idiomaid);


--
-- TOC entry 1891 (class 2606 OID 67439)
-- Name: dem_alumne_traduccio_titola_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_traduccio_titola_fk FOREIGN KEY (titolacademicid) REFERENCES dem_traduccio(traduccioid);


--
-- TOC entry 1892 (class 2606 OID 67446)
-- Name: dem_assigalumn_alumne_alumn_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_alumne_alumn_fk FOREIGN KEY (alumneid) REFERENCES dem_alumne(alumneid);


--
-- TOC entry 1893 (class 2606 OID 67451)
-- Name: dem_assigalumn_assign_assig_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_assign_assig_fk FOREIGN KEY (assignaturaid) REFERENCES dem_assignatura(assignaturaid);


--
-- TOC entry 1888 (class 2606 OID 46155)
-- Name: dem_traducmap_traduccio_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_traducciomap
    ADD CONSTRAINT dem_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES dem_traduccio(traduccioid);


-- Completed on 2017-02-22 11:43:30

--
-- PostgreSQL database dump complete
--

