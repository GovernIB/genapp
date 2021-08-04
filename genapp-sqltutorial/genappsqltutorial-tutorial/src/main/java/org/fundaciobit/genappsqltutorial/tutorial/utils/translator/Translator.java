package org.fundaciobit.genappsqltutorial.tutorial.utils.translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

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

        if (lang.equals("en")) {
            return text;
        }

        if (System.currentTimeMillis() - lastTranslation < 1000) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
        }

        final String[][] ignoreWords = { { "\"Country\"", "\"Cóuntry\"" },
                { "\"Customers\"", "\"Cústomers\"" }, { "\"CustomerName\"", "\"CústomerName\"" },
                { "\"City\"", "\"Cíty\"" }, { "SELECT", "SÉLECT" },
                { "result-set", "résult-set" } };

        String t = text;
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

            return t;

        } catch (Exception e) {

            e.printStackTrace();

            return lang.toUpperCase() + "::" + text;

        } finally {
            lastTranslation = System.currentTimeMillis();
        }

    }

    /**
     * 
     * @param langFrom
     * @param langTo
     * @param word
     * @return
     * @throws Exception
     */
    private static String callUrlAndParseResult(String langFrom, String langTo,
            String word) throws Exception  {

        String url = "https://translate.googleapis.com/translate_a/single?" + "client=gtx&" + "sl="
                + langFrom + "&tl=" + langTo + "&dt=t&q=" + URLEncoder.encode(word, "UTF-8");

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return parseResult(response.toString());
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
