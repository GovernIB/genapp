package org.fundaciobit.genapp.traductor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.fundaciobit.genapp.generator.gui.SharedData;

/**
 * 
 * @author anadal
 *
 */
public class TraduccioTipusBundleFile implements ITraduccioTipus {

    protected final File genappFile;

    protected final String relativePath;

    // SharedData.projectFile.getParentFile()

    // CONSTRUCTOR

    @Override
    public String getTipus() {
        return relativePath;
    }

    public TraduccioTipusBundleFile(File genappFile, String relativePath) {
        super();
        this.genappFile = genappFile;
        this.relativePath = relativePath;
    }

    @Override
    public void save(List<ITraduccioItem> list, Map<Integer, ITraduccioItem> traduccionsPerHashDeClau)
            throws Exception {

        Set<String> languages = new HashSet<String>();

        for (ITraduccioItem tra : list) {
            languages.addAll(tra.getLanguages());
        }

        List<String> idiomesNoExistents = new ArrayList<String>();

        File lastOrigenFile = null;

        for (String idioma : languages) {

            File f = new File(genappFile.getParent(), relativePath + "_" + idioma + ".properties");
            if (f.exists()) {
                lastOrigenFile = f;
                File forig = f;
                File fdest = f;

                processTranslationFile(traduccionsPerHashDeClau, idioma, forig, fdest);

            } else {
                idiomesNoExistents.add(idioma);
            }

        }

        for (String idioma : idiomesNoExistents) {

            File f = new File(genappFile.getParent(), relativePath + "_" + idioma + ".properties");

            File forig = lastOrigenFile;
            File fdest = f;

            processTranslationFile(traduccionsPerHashDeClau, idioma, forig, fdest);

        }

    }

    protected void processTranslationFile(Map<Integer, ITraduccioItem> traduccionsPerHashDeClau, final String idioma,
            File forig, File fdest) throws IOException {
        List<String> originalLines = FileUtils.readLines(forig, "UTF8");

        List<String> newLines = new ArrayList<String>();

        //final String sep = System.getProperty("line.separator");

        for (String orig : originalLines) {

            if (orig.trim().length() == 0) {
                newLines.add(orig);
            } else if (orig.trim().startsWith("#") || orig.trim().startsWith("!")) {
                newLines.add(orig);
            } else {

                int pos = orig.indexOf('=');

                if (pos == -1) {
                    newLines.add(orig);
                } else {

                    String key = orig.substring(0, pos);

                    ITraduccioItem item = traduccionsPerHashDeClau.get(key.hashCode());

                    String value = item.getStringValue(idioma);

                    if (value == null) {
                        System.err.println("No trobo el valor de la clau ]" + key + "[ per l'idioma {" + idioma
                                + "} per '" + relativePath + "'");

                    } else {
                        value = value.replace(":", "\\:").replace("\\\\:", "\\:").replace('\'', '´');
                    }

                    newLines.add(key + "=" + value);

                }

            }

        }

        FileUtils.writeLines(fdest, "UTF8", newLines, "\r\n", false);
    }

    @Override
    public List<ITraduccioItem> read() throws Exception {
        return getTraduccionsFitxer();
    }

    public List<ITraduccioItem> getTraduccionsFitxer() throws Exception {

        Map<String, ITraduccioItem> map = new HashMap<String, ITraduccioItem>();

        String[] languages = SharedData.data.getLanguages();

        for (int j = 0; j < languages.length; j++) {
            String idioma = languages[j];
            File fitxer = new File(genappFile.getParentFile(), relativePath + "_" + idioma + ".properties");

            List<String> lines = FileUtils.readLines(fitxer, "UTF8");

            int countLine = 0;

            for (String line : lines) {
                countLine++;

                if (line.trim().length() == 0 || line.trim().startsWith("#") || line.trim().startsWith("!")
                        || 65279 == (int) line.trim().charAt(0)) {
                    continue;
                }

                int pos = line.indexOf('=');

                if (pos == -1) {
                    System.err.println("No s'ha pogut processar la linia " + countLine + " del fitxer "
                            + fitxer.getName() + ": ]" + line + "[ {" + line.length() + "}" + (int) line.charAt(0));
                    continue;
                }

                String key = line.substring(0, pos);

                if (key.trim().length() <= 1 || key.equals("﻿ ") || key.equals("ï»¿")) {
                    continue;
                }

                TraduccioBundleFileAppItem tra = (TraduccioBundleFileAppItem) map.get(key);

                if (tra == null) {

                    tra = new TraduccioBundleFileAppItem(relativePath, key);
                    map.put(key, tra);
                }

                tra.setStringValue(idioma, line.substring(pos + 1));
            }

        }

        return new ArrayList<ITraduccioItem>(map.values());

    }

}
