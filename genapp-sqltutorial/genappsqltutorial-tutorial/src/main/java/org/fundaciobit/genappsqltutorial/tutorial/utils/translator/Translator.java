package org.fundaciobit.genappsqltutorial.tutorial.utils.translator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import org.fundaciobit.genappsqltutorial.commons.utils.Configuracio;
import org.json.JSONArray;

/**
 * 
 * @author anadal
 *
 */
public class Translator {

    public static long lastTranslation = 0;

    /**
     * 
     * @param lang
     * @param text
     * @return
     */
    public static String translate(String lang, String text) {
        String lines[] = text.split("\\r?\\n");
        String trad[] = new String[lines.length];

        for (int i = 0; i < lines.length; i++) {
            trad[i] = translateOneLine(lang, lines[i]);
        }

        return String.join("\n", trad);

    }

    private static String translateOneLine(String lang, String text) {

        if (lang.equals("en")) {
            return text;
        }

        if (text == null || text.trim().length() == 0) {
            return text;
        }

        if (System.currentTimeMillis() - lastTranslation < 1000) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException e) {
            }
        }

        final String[][] ignoreWords = { { "\"Country\"", "\"Cóuntry\"" },
                { "\"Customers\"", "\"Cústomers\"" }, { "\"CustomerName\"", "\"CústomerName\"" },
                { "\"City\"", "\"Cíty\"" }, { "SELECT", "SéLECT" }, { "result-set", "résult-set" },
                { "LIKE", "LëKE" }, { "BETWEEN", "BëTWEEN" }, { "OR", "öR" }, { "NOT", "NöT" },
                { "AND", "äND" }, { "\"where\"", "\"whërë\"" },
                { "\"Not Like\"", "\"Nöt Lïke\"" } };

        final String NEW_LINE_ENCODED = "@@@@@@@";

        String t = text.replaceAll("\n", NEW_LINE_ENCODED);
        for (String[] c : ignoreWords) {
            t = t.replace(c[0], c[1]);
        }

        String result;
        try {
            result = callUrlAndParseResult("en", lang, t);

            t = result;
            for (String[] c : ignoreWords) {
                t = t.replace(c[1], c[0]);
            }

            t = t.replaceAll(NEW_LINE_ENCODED, "\n");

            return t;

        } finally {
            lastTranslation = System.currentTimeMillis();
        }

    }

    private static java.util.Properties translations = null;

    /**
     * 
     * @param langFrom
     * @param langTo
     * @param word
     * @return
     * @throws Exception
     */
    private static String callUrlAndParseResult(String langFrom, String langTo, String word) {

        try {
            final String key = word + "[" + langFrom + "][" + langTo + "]";

            if (translations == null) {

                Properties prop = new Properties();

                File f = new File(Configuracio.getTranslationsFileProperties());

                if (f.exists()) {
                    InputStream is = null;
                    try {
                        is = new FileInputStream(f);
                        prop.load(is);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (is != null) {
                            is.close();
                        }
                    }
                }

                translations = prop;

            } else {

                String val = translations.getProperty(key);

                if (val != null) {
                    return val;
                }

            }

            /* TODO CERCAR UNA NOVA API DE TRADUCCIÓ */

            String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&"
                    + "sl=" + langFrom + "&tl=" + langTo + "&dt=t&q="
                    + URLEncoder.encode(word, "UTF-8");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            StringBuffer response = new StringBuffer();

            InputStream is = con.getInputStream();

            int i;
            while ((i = is.read()) != -1) {
                response.append((char) i);
            }

            /*
             * BufferedReader in = new BufferedReader(new
             * InputStreamReader(con.getInputStream())); String inputLine; while ((inputLine
             * = in.readLine()) != null) { response.append(inputLine); } in.close();
             */

            String result = parseResult(response.toString());

            translations.put(key, result);

            File f = new File(Configuracio.getTranslationsFileProperties());

            OutputStream os = null;
            try {
                os = new FileOutputStream(f);
                translations.save(os, "Traduccions");
            } finally {
                os.close();
            }

            return result;

        } catch (Throwable th) {

            th.printStackTrace();

            return langTo.toUpperCase() + "::" + word;

        }

    }

    private static String parseResult(String inputJson) throws Exception {
        /*
         * inputJson for word 'hello' translated to language Hindi from English-
         * [[["नमस्ते","hello",,,1]],,"en"] We have to get 'नमस्ते ' from this json.
         */

        JSONArray jsonArray = new JSONArray(inputJson);
        JSONArray jsonArray2 = (JSONArray) jsonArray.get(0);
        JSONArray jsonArray3 = (JSONArray) jsonArray2.get(0);

        return new String(jsonArray3.get(0).toString().getBytes(), "UTF-8");
    }
}
