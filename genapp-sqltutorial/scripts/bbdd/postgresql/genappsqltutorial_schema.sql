--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.25
-- Dumped by pg_dump version 9.3.25
-- Started on 2021-07-30 13:38:28

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 1 (class 3079 OID 11750)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2069 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


--
-- TOC entry 185 (class 1259 OID 47332)
-- Name: gas_categories_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_categories_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_categories_seq OWNER TO genappsqltutorial;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 47271)
-- Name: gas_categories; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_categories (
    categoryid bigint DEFAULT nextval('public.gas_categories_seq'::regclass) NOT NULL,
    categoryname character varying(255),
    description character varying(255)
);


ALTER TABLE public.gas_categories OWNER TO genappsqltutorial;

--
-- TOC entry 186 (class 1259 OID 47335)
-- Name: gas_customers_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_customers_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_customers_seq OWNER TO genappsqltutorial;

--
-- TOC entry 178 (class 1259 OID 47277)
-- Name: gas_customers; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_customers (
    customerid bigint DEFAULT nextval('public.gas_customers_seq'::regclass) NOT NULL,
    customername character varying(255),
    contactname character varying(255),
    address character varying(255),
    city character varying(255),
    country character varying(255),
    postalcode character varying(255)
);


ALTER TABLE public.gas_customers OWNER TO genappsqltutorial;

--
-- TOC entry 187 (class 1259 OID 47338)
-- Name: gas_employees_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_employees_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_employees_seq OWNER TO genappsqltutorial;

--
-- TOC entry 179 (class 1259 OID 47283)
-- Name: gas_employees; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_employees (
    employeeid bigint DEFAULT nextval('public.gas_employees_seq'::regclass) NOT NULL,
    lastname character varying(255),
    firstname character varying(255),
    birthdate date,
    photo character varying(255),
    notes text
);


ALTER TABLE public.gas_employees OWNER TO genappsqltutorial;

--
-- TOC entry 171 (class 1259 OID 47226)
-- Name: gas_fitxer_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_fitxer_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_fitxer_seq OWNER TO genappsqltutorial;

--
-- TOC entry 173 (class 1259 OID 47230)
-- Name: gas_fitxer; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_fitxer (
    fitxerid bigint DEFAULT nextval('public.gas_fitxer_seq'::regclass) NOT NULL,
    descripcio character varying(1000) DEFAULT NULL::character varying,
    mime character varying(255) NOT NULL,
    nom character varying(255) NOT NULL,
    tamany bigint NOT NULL
);


ALTER TABLE public.gas_fitxer OWNER TO genappsqltutorial;

--
-- TOC entry 174 (class 1259 OID 47238)
-- Name: gas_idioma; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_idioma (
    idiomaid character varying(5) NOT NULL,
    nom character varying(50) NOT NULL,
    suportat boolean DEFAULT true NOT NULL,
    ordre integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.gas_idioma OWNER TO genappsqltutorial;

--
-- TOC entry 188 (class 1259 OID 47341)
-- Name: gas_orderdetails_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_orderdetails_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_orderdetails_seq OWNER TO genappsqltutorial;

--
-- TOC entry 180 (class 1259 OID 47289)
-- Name: gas_orderdetails; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_orderdetails (
    orderdetailid bigint DEFAULT nextval('public.gas_orderdetails_seq'::regclass) NOT NULL,
    orderid bigint,
    productid bigint,
    quantity integer
);


ALTER TABLE public.gas_orderdetails OWNER TO genappsqltutorial;

--
-- TOC entry 189 (class 1259 OID 47344)
-- Name: gas_orders_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_orders_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_orders_seq OWNER TO genappsqltutorial;

--
-- TOC entry 181 (class 1259 OID 47292)
-- Name: gas_orders; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_orders (
    orderid bigint DEFAULT nextval('public.gas_orders_seq'::regclass) NOT NULL,
    customerid bigint,
    employeeid bigint,
    orderdate date,
    shipperid bigint
);


ALTER TABLE public.gas_orders OWNER TO genappsqltutorial;

--
-- TOC entry 190 (class 1259 OID 47347)
-- Name: gas_products_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_products_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_products_seq OWNER TO genappsqltutorial;

--
-- TOC entry 182 (class 1259 OID 47295)
-- Name: gas_products; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_products (
    productid bigint DEFAULT nextval('public.gas_products_seq'::regclass) NOT NULL,
    productname character varying(255),
    supplierid bigint,
    categoryid bigint,
    unit character varying(255),
    price double precision
);


ALTER TABLE public.gas_products OWNER TO genappsqltutorial;

--
-- TOC entry 191 (class 1259 OID 47350)
-- Name: gas_shippers_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_shippers_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_shippers_seq OWNER TO genappsqltutorial;

--
-- TOC entry 183 (class 1259 OID 47301)
-- Name: gas_shippers; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_shippers (
    shipperid bigint DEFAULT nextval('public.gas_shippers_seq'::regclass) NOT NULL,
    shippername character varying(255),
    phone character varying(255)
);


ALTER TABLE public.gas_shippers OWNER TO genappsqltutorial;

--
-- TOC entry 192 (class 1259 OID 47353)
-- Name: gas_suppliers_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_suppliers_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_suppliers_seq OWNER TO genappsqltutorial;

--
-- TOC entry 184 (class 1259 OID 47307)
-- Name: gas_suppliers; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_suppliers (
    supplierid bigint DEFAULT nextval('public.gas_suppliers_seq'::regclass) NOT NULL,
    suppliername character varying(255),
    contactname character varying(255),
    address character varying(255),
    city character varying(255),
    postalcode character varying(255),
    country character varying(255),
    phone character varying(255)
);


ALTER TABLE public.gas_suppliers OWNER TO genappsqltutorial;

--
-- TOC entry 172 (class 1259 OID 47228)
-- Name: gas_traduccio_seq; Type: SEQUENCE; Schema: public; Owner: genappsqltutorial
--

CREATE SEQUENCE public.gas_traduccio_seq
    START WITH 1000
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.gas_traduccio_seq OWNER TO genappsqltutorial;

--
-- TOC entry 175 (class 1259 OID 47243)
-- Name: gas_traduccio; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_traduccio (
    traduccioid bigint DEFAULT nextval('public.gas_traduccio_seq'::regclass) NOT NULL
);


ALTER TABLE public.gas_traduccio OWNER TO genappsqltutorial;

--
-- TOC entry 176 (class 1259 OID 47247)
-- Name: gas_traducciomap; Type: TABLE; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE TABLE public.gas_traducciomap (
    traducciomapid bigint NOT NULL,
    idiomaid character varying(10) NOT NULL,
    valor character varying(4000)
);


ALTER TABLE public.gas_traducciomap OWNER TO genappsqltutorial;

--
-- TOC entry 1920 (class 2606 OID 47314)
-- Name: gas_categories_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_categories
    ADD CONSTRAINT gas_categories_pk PRIMARY KEY (categoryid);


--
-- TOC entry 1923 (class 2606 OID 47316)
-- Name: gas_customers_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_customers
    ADD CONSTRAINT gas_customers_pk PRIMARY KEY (customerid);


--
-- TOC entry 1926 (class 2606 OID 47318)
-- Name: gas_employees_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_employees
    ADD CONSTRAINT gas_employees_pk PRIMARY KEY (employeeid);


--
-- TOC entry 1907 (class 2606 OID 47254)
-- Name: gas_fitxer_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_fitxer
    ADD CONSTRAINT gas_fitxer_pk PRIMARY KEY (fitxerid);


--
-- TOC entry 1910 (class 2606 OID 47256)
-- Name: gas_idioma_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_idioma
    ADD CONSTRAINT gas_idioma_pk PRIMARY KEY (idiomaid);


--
-- TOC entry 1931 (class 2606 OID 47320)
-- Name: gas_orderdetails_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_orderdetails
    ADD CONSTRAINT gas_orderdetails_pk PRIMARY KEY (orderdetailid);


--
-- TOC entry 1936 (class 2606 OID 47322)
-- Name: gas_orders_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_orders
    ADD CONSTRAINT gas_orders_pk PRIMARY KEY (orderid);


--
-- TOC entry 1940 (class 2606 OID 47324)
-- Name: gas_products_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_products
    ADD CONSTRAINT gas_products_pk PRIMARY KEY (productid);


--
-- TOC entry 1943 (class 2606 OID 47326)
-- Name: gas_shippers_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_shippers
    ADD CONSTRAINT gas_shippers_pk PRIMARY KEY (shipperid);


--
-- TOC entry 1946 (class 2606 OID 47328)
-- Name: gas_suppliers_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_suppliers
    ADD CONSTRAINT gas_suppliers_pk PRIMARY KEY (supplierid);


--
-- TOC entry 1913 (class 2606 OID 47258)
-- Name: gas_traduccio_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_traduccio
    ADD CONSTRAINT gas_traduccio_pk PRIMARY KEY (traduccioid);


--
-- TOC entry 1918 (class 2606 OID 47260)
-- Name: gas_traducmap_pk; Type: CONSTRAINT; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

ALTER TABLE ONLY public.gas_traducciomap
    ADD CONSTRAINT gas_traducmap_pk PRIMARY KEY (traducciomapid, idiomaid);


--
-- TOC entry 1921 (class 1259 OID 47356)
-- Name: gas_categories_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_categories_pk_i ON public.gas_categories USING btree (categoryid);


--
-- TOC entry 1924 (class 1259 OID 47357)
-- Name: gas_customers_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_customers_pk_i ON public.gas_customers USING btree (customerid);


--
-- TOC entry 1927 (class 1259 OID 47358)
-- Name: gas_employees_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_employees_pk_i ON public.gas_employees USING btree (employeeid);


--
-- TOC entry 1908 (class 1259 OID 47261)
-- Name: gas_fitxer_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_fitxer_pk_i ON public.gas_fitxer USING btree (fitxerid);


--
-- TOC entry 1911 (class 1259 OID 47262)
-- Name: gas_idioma_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_idioma_pk_i ON public.gas_idioma USING btree (idiomaid);


--
-- TOC entry 1928 (class 1259 OID 47395)
-- Name: gas_orderdetai_productid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orderdetai_productid_fk_i ON public.gas_orderdetails USING btree (productid);


--
-- TOC entry 1929 (class 1259 OID 47394)
-- Name: gas_orderdetails_orderid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orderdetails_orderid_fk_i ON public.gas_orderdetails USING btree (orderid);


--
-- TOC entry 1932 (class 1259 OID 47359)
-- Name: gas_orderdetails_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orderdetails_pk_i ON public.gas_orderdetails USING btree (orderdetailid);


--
-- TOC entry 1933 (class 1259 OID 47396)
-- Name: gas_orders_customerid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orders_customerid_fk_i ON public.gas_orders USING btree (customerid);


--
-- TOC entry 1934 (class 1259 OID 47397)
-- Name: gas_orders_employeeid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orders_employeeid_fk_i ON public.gas_orders USING btree (employeeid);


--
-- TOC entry 1937 (class 1259 OID 47360)
-- Name: gas_orders_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orders_pk_i ON public.gas_orders USING btree (orderid);


--
-- TOC entry 1938 (class 1259 OID 47398)
-- Name: gas_orders_shipperid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_orders_shipperid_fk_i ON public.gas_orders USING btree (shipperid);


--
-- TOC entry 1941 (class 1259 OID 47361)
-- Name: gas_products_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_products_pk_i ON public.gas_products USING btree (productid);


--
-- TOC entry 1944 (class 1259 OID 47362)
-- Name: gas_shippers_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_shippers_pk_i ON public.gas_shippers USING btree (shipperid);


--
-- TOC entry 1947 (class 1259 OID 47363)
-- Name: gas_suppliers_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_suppliers_pk_i ON public.gas_suppliers USING btree (supplierid);


--
-- TOC entry 1914 (class 1259 OID 47263)
-- Name: gas_traduccio_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_traduccio_pk_i ON public.gas_traduccio USING btree (traduccioid);


--
-- TOC entry 1915 (class 1259 OID 47264)
-- Name: gas_traducciomap_idiomaid_fk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_traducciomap_idiomaid_fk_i ON public.gas_traducciomap USING btree (idiomaid);


--
-- TOC entry 1916 (class 1259 OID 47265)
-- Name: gas_traducciomap_pk_i; Type: INDEX; Schema: public; Owner: genappsqltutorial; Tablespace: 
--

CREATE INDEX gas_traducciomap_pk_i ON public.gas_traducciomap USING btree (traducciomapid);


--
-- TOC entry 1949 (class 2606 OID 47364)
-- Name: gas_orderdetai_orders_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_orderdetails
    ADD CONSTRAINT gas_orderdetai_orders_fk FOREIGN KEY (orderid) REFERENCES public.gas_orders(orderid);


--
-- TOC entry 1950 (class 2606 OID 47369)
-- Name: gas_orderdetai_products_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_orderdetails
    ADD CONSTRAINT gas_orderdetai_products_fk FOREIGN KEY (productid) REFERENCES public.gas_products(productid);


--
-- TOC entry 1951 (class 2606 OID 47374)
-- Name: gas_orders_customers_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_orders
    ADD CONSTRAINT gas_orders_customers_fk FOREIGN KEY (customerid) REFERENCES public.gas_customers(customerid);


--
-- TOC entry 1952 (class 2606 OID 47379)
-- Name: gas_orders_employees_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_orders
    ADD CONSTRAINT gas_orders_employees_fk FOREIGN KEY (employeeid) REFERENCES public.gas_employees(employeeid);


--
-- TOC entry 1953 (class 2606 OID 47389)
-- Name: gas_orders_shippers_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_orders
    ADD CONSTRAINT gas_orders_shippers_fk FOREIGN KEY (shipperid) REFERENCES public.gas_shippers(shipperid);


--
-- TOC entry 1948 (class 2606 OID 47266)
-- Name: gas_traducmap_traduccio_fk; Type: FK CONSTRAINT; Schema: public; Owner: genappsqltutorial
--

ALTER TABLE ONLY public.gas_traducciomap
    ADD CONSTRAINT gas_traducmap_traduccio_fk FOREIGN KEY (traducciomapid) REFERENCES public.gas_traduccio(traduccioid);


--
-- TOC entry 2068 (class 0 OID 0)
-- Dependencies: 7
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2021-07-30 13:38:28

--
-- PostgreSQL database dump complete
--

