--
-- PostgreSQL database dump
--



--
-- TOC entry 178 (class 1259 OID 56239)
-- Name: dem_alumne_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE public.dem_alumne_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dem_alumne_seq OWNER TO demogenapp;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 171 (class 1259 OID 19076)
-- Name: dem_alumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_alumne (
    alumneid bigint DEFAULT nextval('public.dem_alumne_seq'::regclass) NOT NULL,
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


ALTER TABLE public.dem_alumne OWNER TO demogenapp;

--
-- TOC entry 179 (class 1259 OID 56241)
-- Name: dem_assignatura_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE public.dem_assignatura_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dem_assignatura_seq OWNER TO demogenapp;

--
-- TOC entry 172 (class 1259 OID 19084)
-- Name: dem_assignatura; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_assignatura (
    assignaturaid bigint DEFAULT nextval('public.dem_assignatura_seq'::regclass) NOT NULL,
    nom character varying(100) NOT NULL,
    credits integer NOT NULL,
    diasetmana integer,
    hora time without time zone,
    descripcio character varying(4000)
);


ALTER TABLE public.dem_assignatura OWNER TO demogenapp;

--
-- TOC entry 180 (class 1259 OID 56243)
-- Name: dem_assignaturaalumne_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE public.dem_assignaturaalumne_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dem_assignaturaalumne_seq OWNER TO demogenapp;

--
-- TOC entry 173 (class 1259 OID 19091)
-- Name: dem_assignaturaalumne; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_assignaturaalumne (
    id bigint DEFAULT nextval('public.dem_assignaturaalumne_seq'::regclass) NOT NULL,
    alumneid bigint NOT NULL,
    assignaturaid bigint NOT NULL,
    nota double precision
);


ALTER TABLE public.dem_assignaturaalumne OWNER TO demogenapp;

--
-- TOC entry 181 (class 1259 OID 56245)
-- Name: dem_fitxer_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE public.dem_fitxer_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dem_fitxer_seq OWNER TO demogenapp;

--
-- TOC entry 174 (class 1259 OID 19095)
-- Name: dem_fitxer; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_fitxer (
    fitxerid bigint DEFAULT nextval('public.dem_fitxer_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(45) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);


ALTER TABLE public.dem_fitxer OWNER TO demogenapp;

--
-- TOC entry 175 (class 1259 OID 19103)
-- Name: dem_idioma; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.dem_idioma OWNER TO demogenapp;

--
-- TOC entry 182 (class 1259 OID 56247)
-- Name: dem_traduccio_seq; Type: SEQUENCE; Schema: public; Owner: demogenapp
--

CREATE SEQUENCE public.dem_traduccio_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.dem_traduccio_seq OWNER TO demogenapp;

--
-- TOC entry 176 (class 1259 OID 19108)
-- Name: dem_traduccio; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_traduccio (
    traduccioid bigint DEFAULT nextval('public.dem_traduccio_seq'::regclass) NOT NULL
);


ALTER TABLE public.dem_traduccio OWNER TO demogenapp;

--
-- TOC entry 177 (class 1259 OID 19112)
-- Name: dem_traducciomap; Type: TABLE; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE TABLE public.dem_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);


ALTER TABLE public.dem_traducciomap OWNER TO demogenapp;

--
-- TOC entry 1871 (class 2606 OID 19120)
-- Name: dem_alumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_alumne
    ADD CONSTRAINT dem_alumne_pk PRIMARY KEY (alumneid);


--
-- TOC entry 1880 (class 2606 OID 19122)
-- Name: dem_assigalumn_multiple_uk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_multiple_uk UNIQUE (alumneid, assignaturaid);


--
-- TOC entry 1875 (class 2606 OID 19124)
-- Name: dem_assignatura_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_assignatura
    ADD CONSTRAINT dem_assignatura_pk PRIMARY KEY (assignaturaid);


--
-- TOC entry 1882 (class 2606 OID 19126)
-- Name: dem_assignaturaalumne_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_assignaturaalumne
    ADD CONSTRAINT dem_assignaturaalumne_pk PRIMARY KEY (id);


--
-- TOC entry 1885 (class 2606 OID 19128)
-- Name: dem_fitxer_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_fitxer
    ADD CONSTRAINT dem_fitxer_pk PRIMARY KEY (fitxerid);


--
-- TOC entry 1888 (class 2606 OID 19130)
-- Name: dem_idioma_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_idioma
    ADD CONSTRAINT dem_idioma_pk PRIMARY KEY (idiomaid);


--
-- TOC entry 1891 (class 2606 OID 19132)
-- Name: dem_traduccio_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_traduccio
    ADD CONSTRAINT dem_traduccio_pk PRIMARY KEY (traduccioid);


--
-- TOC entry 1896 (class 2606 OID 19134)
-- Name: dem_traducmap_pk; Type: CONSTRAINT; Schema: public; Owner: demogenapp; Tablespace: 
--

ALTER TABLE ONLY public.dem_traducciomap
    ADD CONSTRAINT dem_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);


--
-- TOC entry 1868 (class 1259 OID 19135)
-- Name: dem_alumne_fotoid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_fotoid_fk_i ON public.dem_alumne USING btree (fotoid);


--
-- TOC entry 1869 (class 1259 OID 19136)
-- Name: dem_alumne_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_idiomaid_fk_i ON public.dem_alumne USING btree (idiomaid);


--
-- TOC entry 1872 (class 1259 OID 19137)
-- Name: dem_alumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_pk_i ON public.dem_alumne USING btree (alumneid);


--
-- TOC entry 1873 (class 1259 OID 19138)
-- Name: dem_alumne_titolaca_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_alumne_titolaca_fk_i ON public.dem_alumne USING btree (titolacademicid);


--
-- TOC entry 1877 (class 1259 OID 19139)
-- Name: dem_assigalumn_alumneid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_alumneid_fk_i ON public.dem_assignaturaalumne USING btree (alumneid);


--
-- TOC entry 1878 (class 1259 OID 19140)
-- Name: dem_assigalumn_assigna_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assigalumn_assigna_fk_i ON public.dem_assignaturaalumne USING btree (assignaturaid);


--
-- TOC entry 1876 (class 1259 OID 19141)
-- Name: dem_assignatura_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignatura_pk_i ON public.dem_assignatura USING btree (assignaturaid);


--
-- TOC entry 1883 (class 1259 OID 19142)
-- Name: dem_assignaturaalumne_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_assignaturaalumne_pk_i ON public.dem_assignaturaalumne USING btree (id);


--
-- TOC entry 1886 (class 1259 OID 19143)
-- Name: dem_fitxer_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_fitxer_pk_i ON public.dem_fitxer USING btree (fitxerid);


--
-- TOC entry 1889 (class 1259 OID 19144)
-- Name: dem_idioma_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_idioma_pk_i ON public.dem_idioma USING btree (idiomaid);


--
-- TOC entry 1892 (class 1259 OID 19145)
-- Name: dem_traduccio_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traduccio_pk_i ON public.dem_traduccio USING btree (traduccioid);


--
-- TOC entry 1893 (class 1259 OID 19146)
-- Name: dem_traducciomap_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traducciomap_idiomaid_fk_i ON public.dem_traducciomap USING btree (idiomaid);


--
-- TOC entry 1894 (class 1259 OID 19147)
-- Name: dem_traducciomap_pk_i; Type: INDEX; Schema: public; Owner: demogenapp; Tablespace: 
--

CREATE INDEX dem_traducciomap_pk_i ON public.dem_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1897 (class 2606 OID 19148)
-- Name: dem_alumne_fitxer_fotoid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_alumne
    ADD CONSTRAINT dem_alumne_fitxer_fotoid_fk FOREIGN KEY (fotoid) REFERENCES public.dem_fitxer(fitxerid);


--
-- TOC entry 1898 (class 2606 OID 19153)
-- Name: dem_alumne_idioma_idiomaid_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_alumne
    ADD CONSTRAINT dem_alumne_idioma_idiomaid_fk FOREIGN KEY (idiomaid) REFERENCES public.dem_idioma(idiomaid);


--
-- TOC entry 1899 (class 2606 OID 19158)
-- Name: dem_alumne_traduccio_titola_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_alumne
    ADD CONSTRAINT dem_alumne_traduccio_titola_fk FOREIGN KEY (titolacademicid) REFERENCES public.dem_traduccio(traduccioid);


--
-- TOC entry 1900 (class 2606 OID 19163)
-- Name: dem_assigalumn_alumne_alumn_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_alumne_alumn_fk FOREIGN KEY (alumneid) REFERENCES public.dem_alumne(alumneid);


--
-- TOC entry 1901 (class 2606 OID 19168)
-- Name: dem_assigalumn_assign_assig_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_assignaturaalumne
    ADD CONSTRAINT dem_assigalumn_assign_assig_fk FOREIGN KEY (assignaturaid) REFERENCES public.dem_assignatura(assignaturaid);


--
-- TOC entry 1902 (class 2606 OID 19173)
-- Name: dem_traducmap_traduccio_fk; Type: FK CONSTRAINT; Schema: public; Owner: demogenapp
--

ALTER TABLE ONLY public.dem_traducciomap
    ADD CONSTRAINT dem_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES public.dem_traduccio(traduccioid);


--
-- TOC entry 2017 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2022-03-07 11:38:02

--
-- PostgreSQL database dump complete
--

