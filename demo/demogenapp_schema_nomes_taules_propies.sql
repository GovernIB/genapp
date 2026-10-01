--
-- PostgreSQL database dump
--



--
-- TOC entry 178 (class 1259 OID 56239)
-- Name: dem_alumne_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE dem_alumne_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dem_alumne_seq OWNER TO demogenapp;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 19076)
-- Name: dem_alumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_alumne (
    alumneid bigint DEFAULT nextval('dem_alumne_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL,
    idiomaid character varying(2) NOT NULL,
    actiu boolean DEFAULT true NOT NULL,
    datanaixement date NOT NULL,
    despertador time without time zone,
    darreracces timestamp without time zone,
    fotoid bigint,
    titolacademicid bigint,
    paginaweb character varying(255),
    descripcio character varying(4000),
    sexe boolean
);


ALTER TABLE dem_alumne OWNER TO demogenapp;

--
-- TOC entry 179 (class 1259 OID 56241)
-- Name: dem_assignatura_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE dem_assignatura_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dem_assignatura_seq OWNER TO demogenapp;

--
-- TOC entry 172 (class 1259 OID 19084)
-- Name: dem_assignatura; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_assignatura (
    assignaturaid bigint DEFAULT nextval('dem_assignatura_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL,
    credits integer NOT NULL,
    diasetmana integer,
    hora time without time zone,
    descripcio character varying(4000)
);


ALTER TABLE dem_assignatura OWNER TO demogenapp;

--
-- TOC entry 180 (class 1259 OID 56243)
-- Name: dem_assignaturaalumne_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE dem_assignaturaalumne_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE dem_assignaturaalumne_seq OWNER TO demogenapp;

--
-- TOC entry 173 (class 1259 OID 19091)
-- Name: dem_assignaturaalumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE dem_assignaturaalumne (
    id bigint DEFAULT nextval('dem_assignaturaalumne_seq'::regclass) NOT NULL,
    alumneid bigint NOT NULL,
    assignaturaid bigint NOT NULL,
    nota double precision
);


ALTER TABLE dem_assignaturaalumne OWNER TO demogenapp;


--
-- TOC entry 1871 (class 2606 OID 19120)
-- Name: dem_alumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_pk PRIMARY KEY (alumneid);


--
-- TOC entry 1880 (class 2606 OID 19122)
-- Name: dem_assigalumn_multiple_uk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_multiple_uk UNIQUE (alumneid, assignaturaid);


--
-- TOC entry 1875 (class 2606 OID 19124)
-- Name: dem_assignatura_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignatura
    ADD CONSTRAINT dem_assignatura_pk PRIMARY KEY (assignaturaid);


--
-- TOC entry 1882 (class 2606 OID 19126)
-- Name: dem_assignaturaalumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assignaturaalumne_pk PRIMARY KEY (id);


--
-- TOC entry 1868 (class 1259 OID 19135)
-- Name: dem_alumne_fotoid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_fotoid_fk_i ON dem_alumne USING btree (fotoid);


--
-- TOC entry 1869 (class 1259 OID 19136)
-- Name: dem_alumne_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_idiomaid_fk_i ON dem_alumne USING btree (idiomaid);


--
-- TOC entry 1872 (class 1259 OID 19137)
-- Name: dem_alumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_pk_i ON dem_alumne USING btree (alumneid);


--
-- TOC entry 1873 (class 1259 OID 19138)
-- Name: dem_alumne_titolaca_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_titolaca_fk_i ON dem_alumne USING btree (titolacademicid);


--
-- TOC entry 1877 (class 1259 OID 19139)
-- Name: dem_assigalumn_alumneid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_alumneid_fk_i ON dem_assignaturaalumne USING btree (alumneid);


--
-- TOC entry 1878 (class 1259 OID 19140)
-- Name: dem_assigalumn_assigna_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_assigna_fk_i ON dem_assignaturaalumne USING btree (assignaturaid);


--
-- TOC entry 1876 (class 1259 OID 19141)
-- Name: dem_assignatura_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignatura_pk_i ON dem_assignatura USING btree (assignaturaid);


--
-- TOC entry 1883 (class 1259 OID 19142)
-- Name: dem_assignaturaalumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignaturaalumne_pk_i ON dem_assignaturaalumne USING btree (id);

--
-- TOC entry 1897 (class 2606 OID 19148)
-- Name: dem_alumne_fitxer_fotoid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_fitxer_fotoid_fk FOREIGN KEY (fotoid) REFERENCES dem_fitxer(fitxerid);


--
-- TOC entry 1898 (class 2606 OID 19153)
-- Name: dem_alumne_idioma_idiomaid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_idioma_idiomaid_fk FOREIGN KEY (idiomaid) REFERENCES dem_idioma(idiomaid);


--
-- TOC entry 1899 (class 2606 OID 19158)
-- Name: dem_alumne_traduccio_titola_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_alumne
    ADD CONSTRAINT dem_alumne_traduccio_titola_fk FOREIGN KEY (titolacademicid) REFERENCES dem_traduccio(traduccioid);


--
-- TOC entry 1900 (class 2606 OID 19163)
-- Name: dem_assigalumn_alumne_alumn_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_alumne_alumn_fk FOREIGN KEY (alumneid) REFERENCES dem_alumne(alumneid);


--
-- TOC entry 1901 (class 2606 OID 19168)
-- Name: dem_assigalumn_assign_assig_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_assign_assig_fk FOREIGN KEY (assignaturaid) REFERENCES dem_assignatura(assignaturaid);



--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2025-02-06 11:38:02

--
-- PostgreSQL database dump complete
--

