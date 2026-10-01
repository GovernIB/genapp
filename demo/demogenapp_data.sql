--
-- PostgreSQL database dump
--


--
-- TOC entry 2013 (class 0 OID 19095)
-- Dependencies: 174
-- Data for Name: dem_fitxer; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_fitxer VALUES (1008, NULL, 'image/png', 'an.png', 22504);
INSERT INTO public.dem_fitxer VALUES (2001, NULL, 'image/png', 'ar.png', 14768);
INSERT INTO public.dem_fitxer VALUES (2002, NULL, 'image/png', 'pt.png', 8118);

--
-- TOC entry 2034 (class 0 OID 0)
-- Dependencies: 181
-- Name: dem_fitxer_seq; Type: SEQUENCE SET; Schema: public; Owner: demogenapp
--

SELECT pg_catalog.setval('public.dem_fitxer_seq', 2002, true);

--
-- TOC entry 2014 (class 0 OID 19103)
-- Dependencies: 175
-- Data for Name: dem_idioma; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_idioma VALUES ('ca', 'Catala', true, 0);
INSERT INTO public.dem_idioma VALUES ('es', 'Castellano', true, 1);
INSERT INTO public.dem_idioma VALUES ('en', 'English', true, 2);



--
-- TOC entry 2015 (class 0 OID 19108)
-- Dependencies: 176
-- Data for Name: dem_traduccio; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_traduccio VALUES (1001);
INSERT INTO public.dem_traduccio VALUES (1009);
INSERT INTO public.dem_traduccio VALUES (2001);


--
-- TOC entry 2035 (class 0 OID 0)
-- Dependencies: 182
-- Name: dem_traduccio_seq; Type: SEQUENCE SET; Schema: public; Owner: demogenapp
--

SELECT pg_catalog.setval('public.dem_traduccio_seq', 2001, true);


--
-- TOC entry 2016 (class 0 OID 19112)
-- Dependencies: 177
-- Data for Name: dem_traducciomap; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_traducciomap VALUES (1001, 'ca', 'UIB');
INSERT INTO public.dem_traducciomap VALUES (1001, 'es', 'UIBER');
INSERT INTO public.dem_traducciomap VALUES (1001, 'en', 'UIB');
INSERT INTO public.dem_traducciomap VALUES (2001, 'en', 'Master GenApp');
INSERT INTO public.dem_traducciomap VALUES (2001, 'ca', 'Mestre GenApp');
INSERT INTO public.dem_traducciomap VALUES (2001, 'es', 'Maestro GenApp');
INSERT INTO public.dem_traducciomap VALUES (1009, 'en', 'en');
INSERT INTO public.dem_traducciomap VALUES (1009, 'ca', 'ca');
INSERT INTO public.dem_traducciomap VALUES (1009, 'es', 'es');




--
-- TOC entry 2010 (class 0 OID 19076)
-- Dependencies: 171
-- Data for Name: dem_alumne; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_alumne VALUES (123, 'Antoni Nadal', 'ca', false, '1975-04-24', NULL, '2020-03-19 13:38:22', 1008, 1009, NULL, '<p><span style="text-decoration: underline;"><em>Bones:</em></span></p>
<p>&nbsp;<strong>&nbsp;&nbsp; Com va tot per aqu&iacute; &nbsp;<span style="font-size: large; color: #ff0000;"> HOLA</span></strong></p>', true);
INSERT INTO public.dem_alumne VALUES (1000, 'Antoni Reus', 'es', true, '2020-03-05', NULL, '2020-03-02 12:37:55', 2001, 1001, NULL, NULL, false);
INSERT INTO public.dem_alumne VALUES (2002, 'Pau Trias', 'en', true, '2022-03-01', '11:28:10', '2022-03-15 11:26:38', 2002, 2001, 'pautrias.com', NULL, false);


--
-- TOC entry 2031 (class 0 OID 0)
-- Dependencies: 178
-- Name: dem_alumne_seq; Type: SEQUENCE SET; Schema: public; Owner: demogenapp
--

SELECT pg_catalog.setval('public.dem_alumne_seq', 2002, true);


--
-- TOC entry 2011 (class 0 OID 19084)
-- Dependencies: 172
-- Data for Name: dem_assignatura; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_assignatura VALUES (1002, 'Mates', 5, 2, '13:00:00', NULL);
INSERT INTO public.dem_assignatura VALUES (1004, 'Naturals', 13, 4, '15:00:00', NULL);


--
-- TOC entry 2032 (class 0 OID 0)
-- Dependencies: 179
-- Name: dem_assignatura_seq; Type: SEQUENCE SET; Schema: public; Owner: demogenapp
--

SELECT pg_catalog.setval('public.dem_assignatura_seq', 2000, true);


--
-- TOC entry 2012 (class 0 OID 19091)
-- Dependencies: 173
-- Data for Name: dem_assignaturaalumne; Type: TABLE DATA; Schema: public; Owner: demogenapp
--

INSERT INTO public.dem_assignaturaalumne VALUES (1003, 123, 1002, 2.2999999999999998);
INSERT INTO public.dem_assignaturaalumne VALUES (1005, 123, 1004, 34);


--
-- TOC entry 2033 (class 0 OID 0)
-- Dependencies: 180
-- Name: dem_assignaturaalumne_seq; Type: SEQUENCE SET; Schema: public; Owner: demogenapp
--

SELECT pg_catalog.setval('public.dem_assignaturaalumne_seq', 2000, true);


-- Completed on 2022-03-07 11:39:31

--
-- PostgreSQL database dump complete
--

