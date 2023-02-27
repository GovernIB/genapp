package org.fundaciobit.genapp.generator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.ForeignKey;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.common.DataBaseInfoUtils;

/**
 * 
 * @author anadal
 *
 */
public class ProjectValidator {

    private static final Logger log = Logger.getLogger(ProjectValidator.class);

    public static StringBuffer checkAll(Project project) throws Exception {

        StringBuffer allErrors = new StringBuffer();

        String[] idiomes = project.getLanguages();

        Set<String> sequencesOrphan = null;

        List<SequenceInfo> seqInfoList = getSequenceList(project);

        if (seqInfoList != null) {
            Set<String> sequences = new HashSet<String>();
            sequencesOrphan = new HashSet<String>();

            for (SequenceInfo sequenceInfo : seqInfoList) {
                if (sequenceInfo.getCurrentValue() < 1000) {
                    allErrors.append("-- La sequència " + sequenceInfo.getName()
                            + " ha de tenir un start value a 1000 (CurrentValue  " + sequenceInfo.getCurrentValue()
                            + ")\n");
                    allErrors.append(" SELECT setval('" + sequenceInfo.getName() + "', 1000, true);\n\n");
                }
                sequences.add(sequenceInfo.getName());
                sequencesOrphan.add(sequenceInfo.getName().toLowerCase());
            }
        }

        // Eliminar descripcions buides i noms java amb espais
        // i Limitar noms de camps i taules de BBDD a 30 chars
        for (TableInfo table : project.getTables()) {

            final String expectedSequence = (table.getName() + "_seq").toLowerCase();

            if (table.isTranslationMapEntity() || table.isTranslationEntity()) {

                if (sequencesOrphan != null) {
                    sequencesOrphan.remove(expectedSequence);
                }

                continue;
            }

            StringBuffer errors = new StringBuffer();

            boolean errorgreu = false;

            // Limitar noms taules de BBDD a 30 chars
            if (table.name.length() > 30) {
                errors.append("-- El nom sql (" + table.getName() + ") de la taula " + table.nameJava
                        + " supera els 30 caracters de longitud.\n");
                errorgreu = true;
            }

            // Noms curts menors o iguals a 10
            String shortStr = table.getShortName();
            if (shortStr == null) {
                shortStr = table.getNameJava().toLowerCase();
                table.setShortName(shortStr);
            }

            if (shortStr.length() > 10) {
                errors.append("-- El camp shortname de la taula " + table.getNameJava()
                        + " ha de tenir longitud de 10 o menys caracters (" + shortStr + ")");
                errorgreu = true;
            }

            // Check Traduccions

            /*
             * { Map<String, String> labels = table.getLabels(); Map<String, String>
             * labelsPlural = table.getLabelsPlural();
             * 
             * for(String key : labels.keySet()) { labelsPlural.put(key, labels.get(key) +
             * "_XX_" + key); } }
             */
            // Traduccions nom de la taula
            checkTraduccions(idiomes, table.getNameJava(), table.getLabels(), errors);
            checkTraduccions(idiomes, table.getNameJava() + "_plural", table.getLabelsPlural(), errors);

            // Traduccions dels camps
            for (FieldInfo field : table.getFields()) {

                // Sequències
                if (field.isAutoIncrement()) {
                    // nextval(\u0027efi_fitxer_seq\u0027::regclass)
                    final String defautValue = field.getDefaultValue();

                    // System.out.println(" XYZ ZZZ Field[" + field.getSqlName() + "] DEFVAL => " +
                    // defautValue);
                    // System.out.println(" XYZ ZZZ Field[" + field.getSqlName() + "] EXPECTED SEQ
                    // => " + expectedSequence + "\n\n");

                    if (defautValue == null || defautValue.trim().length() == 0) {
                        errors.append("-- El camp " + field.javaName + " de la taula " + table.getNameJava()
                                + " és autoincrmental però no té seqüència ]" + expectedSequence
                                + "[ definida el el defaultValue.\n");
                    } else {

                        if (defautValue.toLowerCase().indexOf(expectedSequence) == -1) {
                            errors.append("-- El camp " + field.javaName + " de la taula " + table.getNameJava()
                                    + " és autoincrmental i s'esperava una seqüència ]" + expectedSequence
                                    + "[ en el default value, però el valor del default value és ]" + defautValue
                                    + "[\n"
                                    + "CREATE SEQUENCE " + expectedSequence + " INCREMENT 1 START 1000;\n"
                                    + "ALTER TABLE " + table.getName() + " ALTER COLUMN " + field.sqlName + " SET DEFAULT nextval('" + expectedSequence + "');\n"
                                    + "\n");
                        } else {
                            // OK
                            if (sequencesOrphan != null) {
                                sequencesOrphan.remove(expectedSequence);
                            }
                        }
                    }
                }

                // Traduccions de Camps
                Map<String, String> labels = field.getLabels();
                for (String lang : idiomes) {
                    String value = labels.get(lang);
                    boolean showerror = false;
                    if (value == null) {
                        labels.put(lang, field.javaName + "_XX_" + lang);
                        showerror = true;
                    } else {
                        if (value.endsWith("_XX_" + lang)) {
                            showerror = true;
                        }
                    }
                    if (showerror) {
                        errors.append("-- El camp " + field.javaName + " de la taula " + table.getNameJava()
                                + " no té definida l'etiqueta per l'idioma " + " [" + lang + "]\n");
                    }

                }
            }

            if (errorgreu) {
                errors.append("\n-- No es faran comprobacions de Longitud de camps ni de constraints\n"
                        + "--      (PrimaryKeys, ForeignKeys, Indexs i UNIQUEs) mentre no es\n"
                        + "--      solventin els problemes anteriors.\n\n");
            } else {

                // Check constraints de multiple uniques
                MultipleUnique[] multipleUniques = table.getMultipleUniques();
                if (multipleUniques != null /* && multipleUniques.length == 1 */) {

                    for (int i = 0; i < multipleUniques.length; i++) {

                        String ukName = multipleUniques[i].getName();

                        {
                            boolean error = false;
                            if (ukName.length() > 30) {
                                errors.append("-- La Constraint unique " + ukName + " de la taula " + table.nameJava
                                        + " té un tamany de més de 30 caracters\n");
                                error = true;
                            }

                            String ukNameStartsExpected = project.getPrefix() + "_" + shortStr + "_";

                            if (!ukName.toLowerCase().startsWith(ukNameStartsExpected.toLowerCase())
                                    || !ukName.toLowerCase().endsWith("_uk")) {
                                errors.append("-- La Constraint unique " + ukName + " de la taula " + table.nameJava
                                        + " té un nom incorrecte.\n");
                                error = true;
                            }

                            if (error) {

                                String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + ukName + ";\n";

                                sql = sql + "ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + ukNameStartsExpected
                                        + "MULTIPLE_uk" + " UNIQUE (";

                                String[] cols = multipleUniques[i].getSqlColumns();
                                for (int j = 0; j < cols.length; j++) {
                                    if (j != 0) {
                                        sql = sql + ",";
                                    }
                                    sql = sql + cols[j];
                                }

                                sql = sql + ");\n";

                                errors.append(sql);

                            }
                        }
                    }
                }

                // Longitud de camps i constraints (PrimaryKeys, ForeignKeys, Indexs i UNIQUEs)
                Map<String, String> constraints = new HashMap<String, String>();

                int countPKs = 0;
                for (FieldInfo field : table.getFields()) {
                    if (field.isPrimaryKey()) {
                        countPKs++;
                    }
                }

                for (FieldInfo field : table.getFields()) {
                    String desc = field.getDescripcio();
                    if (desc != null && desc.trim().length() == 0) {
                        field.setDescripcio(null);
                    }
                    // Espais
                    String name = field.getJavaName();
                    if (name.indexOf(' ') != -1) {
                        name = name.trim();
                        if (name.indexOf(' ') != -1) {
                            name = name.replace(' ', '_');
                        }
                        field.setJavaName(name);
                    }
                    // Limitar noms de camps de BBDD a 30 chars
                    if (field.sqlName.length() > 30) {
                        errors.append("-- El nom sql (" + field.sqlName + ") del camp " + field.javaName
                                + " de la taula " + table.nameJava + " supera els 30 caracters de longitud.\n");
                    }

                    // Check PK constarint
                    String pk = field.getPK();
                    if (pk != null) {
                        if (pk.length() > 30) {
                            errors.append("-- El nom sql de la contraint PK (" + pk + ") pel camp " + field.javaName
                                    + " de la taula " + table.nameJava + " supera els 30 caracters de longitud.\n");
                        }

                        String expected = table.name + "_pk";
                        if (expected.length() > 30) {
                            expected = project.getPrefix() + "_" + shortStr + "_pk";
                        }
                        if (!pk.equals(expected)) {
                            errors.append(" --  Error de forma en contraint Primary KEY \n");
                            errors.append("ALTER INDEX " + pk + " RENAME to " + expected + ";\n");
                        }
                    }

                    // Check UNIQUE Constraint
                    String unique = field.getUnique();
                    if (unique != null) {
                        if (unique.length() > 30) {
                            errors.append("-- El nom sql de la contraint unique (" + unique + ") pel camp "
                                    + field.javaName + " de la taula " + table.nameJava
                                    + " supera els 30 caracters de longitud.\n");
                        }

                        String expectedPre = project.getPrefix() + "_" + shortStr + "_";
                        String expectedPost = "_uk";

                        if (unique.startsWith(expectedPre) && unique.endsWith(expectedPost)) {
                            // OK
                        } else {

                            errors.append(" --  Error de forma en contraint unique\n");
                            errors.append("ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + unique + ";\n");
                            errors.append("ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + expectedPre
                                    + field.sqlName + expectedPost + " unique (" + field.sqlName + ");\n");

                        }
                    }

                    // Check Foreign Keys Constraint
                    ForeignKey[] fkImport = field.getForeignKeysByType(ForeignKey.IMPORTED);
                    if (fkImport != null && fkImport.length > 1) {
                        errors.append("\n-- ForeignKey.IMPORTED duplicades [" + table.nameJava + "."
                                + field.getJavaName() + " ]\n\n");
                    }

                    for (ForeignKey fk : fkImport) {

                        String fkName = fk.getName();
                        // TODO optimitzar
                        TableInfo expTable = CodeGenUtils.findTableInfoByTableSQLName(project, fk.getTable());
                        FieldInfo expField = CodeGenUtils.findFieldInfoByColumnSQLName(expTable, fk.getField());

                        String fkNameStartsExpected = project.getPrefix() + "_" + shortStr + "_"
                                + expTable.getShortName() + "_"; // fk";

                        if (!fkName.toLowerCase().startsWith(fkNameStartsExpected.toLowerCase())
                                || !fkName.toLowerCase().endsWith("_fk") || fkName.length() > 30) {

                            if (fkName.length() > 30) {
                                errors.append("-- La ForeignKey " + fkName + " de la taula " + table.nameJava
                                        + " té un tamany de més de 30 caracters\n");
                            }

                            errors.append("-- La ForeignKey " + fkName + " de la taula " + table.nameJava
                                    + " no té un nom correcte (esperat " + fkNameStartsExpected + "*_fk )\n");
                            {
                                int tamanyField = 30 - (fkNameStartsExpected + "_fk").length();
                                String fieldfk;
                                if (tamanyField < 5) {
                                    fieldfk = field.sqlName;
                                } else {
                                    if (field.sqlName.length() <= tamanyField) {
                                        fieldfk = field.sqlName;
                                    } else {
                                        fieldfk = field.sqlName.substring(0, tamanyField);
                                    }
                                }

                                String newC = fkNameStartsExpected + fieldfk + "_fk";
                                String sql = "ALTER TABLE " + table.getName() + " DROP CONSTRAINT " + fkName + ";\n";
                                sql = sql + "ALTER TABLE " + table.getName() + " ADD CONSTRAINT " + newC + " "
                                        + " FOREIGN KEY (" + field.sqlName + ") " + " REFERENCES " + expTable.name
                                        + " (" + expField.sqlName + ");\n";
                                errors.append(sql);
                                constraints.put(String.valueOf(System.nanoTime()), sql);
                            }

                        }
                    }

                    // Check INDEXES
                    String index = field.getIndex();
                    if (index == null) {
                        // Revisar si es necessaria

                        // Check PK: PK haurien de tenir INDEX
                        if (field.isPrimaryKey()) {

                            checkExpectedIndexPK(errors, project, table, shortStr, field, countPKs, index);

                        }

                        if (fkImport != null && fkImport.length != 0) {
                            // Check FK: FK haurien de tenir INDEX

                            String expected = getExpectedIndexFK(project, table, shortStr, field);

                            String avis = (expected.length() > 30) ? "**" : "";

                            errors.append(" -- Es recomanable tenir un index de la clau forania.\n");
                            errors.append(avis + " create index " + expected + " on " + table.name + " ("
                                    + field.sqlName + ");\n");

                        }

                    } else {

                        // Format PK requerit per INDEX
                        if (field.isPrimaryKey()) {
                            checkExpectedIndexPK(errors, project, table, shortStr, field, countPKs, index);

                        } else if (fkImport != null && fkImport.length != 0) {
                            // Format FK requerit per INDEX
                            if ((index.startsWith(table.name + "_")
                                    || index.startsWith(project.getPrefix() + "_" + shortStr + "_"))
                                    && index.endsWith("_fk_i") && index.length() <= 30) {
                                // OK
                            } else {
                                String expected = getExpectedIndexFK(project, table, shortStr, field);
                                errors.append(" --  Error de forma en l'index FK  " + index
                                        + ((expected.length() > 30) ? ": més gran de 30 caracters" : "") + "\n");
                                errors.append(" ALTER INDEX " + index + " RENAME to " + expected + ";" + "\n");
                            }

                        } else {
                            // format camp especific

                            if ((index.startsWith(table.name + "_")
                                    || index.startsWith(project.getPrefix() + "_" + shortStr + "_"))
                                    && index.endsWith("_i") && index.length() <= 30) {
                                // OK
                            } else {
                                String expected = table.name + "_" + field.sqlName + "_i";
                                if (expected.length() > 30) {
                                    expected = project.getPrefix() + "_" + shortStr + "_" + field.sqlName + "_i";
                                }
                                String avis = (expected.length() > 30) ? "**" : "";
                                errors.append(" --  Error de forma en l'index especific  " + index + "\n");
                                errors.append(avis + " ALTER INDEX " + index + " RENAME to " + expected + ";" + "\n");
                            }

                        }

                    }

                } // Final for fields

                if (constraints.size() != 0) {
                    System.err.println(" -- " + table.getName() + " (" + table.getNameJava() + ")");
                    System.err.println();
                    for (String val : constraints.values()) {
                        System.err.println(val);
                    }
                }
            }

            if (errors.length() != 0) {
                allErrors.append("\n-- ==== ERRORS EN TAULA ]" + table.getNameJava() + "[:\n\n");
                allErrors.append(errors.toString());
                allErrors.append("\n");

            }

        }

        if (sequencesOrphan != null && !sequencesOrphan.isEmpty()) {
            allErrors.append("\n --- Les següents seqüències no estan assignades a cap taula: "
                    + Arrays.toString(sequencesOrphan.toArray()));
        }

        // Final Check tables

        return allErrors;
    }

    protected static String getExpectedIndexFK(Project project, TableInfo table, String shortStr, FieldInfo field) {
        String expected = table.name + "_" + field.sqlName + "_fk_i";
        if (expected.length() > 30) {
            expected = project.getPrefix() + "_" + shortStr + "_" + field.sqlName + "_fk_i";
        }
        return expected;
    }

    protected static String checkExpectedIndexPK(StringBuffer errors, Project project, TableInfo table, String shortStr,
            FieldInfo field, int countPKs, String index) {

        String expected = null;
        final String[] tableNames = new String[] { table.name, project.getPrefix() + "_" + shortStr };
        String expectedtable = null;
        for (int i = 0; i < tableNames.length; i++) {
            expectedtable = tableNames[i];
            if (countPKs == 1) {
                expected = expectedtable + "_pk_i";
            } else {
                expected = expectedtable + "_" + field.sqlName + "_pk_i";
            }
            if (expected.length() > 30) {
                continue;
            }
            break;
        }

        if (index == null) {
            errors.append(" -- Es recomanable tenir un index de la clau primaria.\n");

            errors.append(" create index " + expected + ((expected.length() > 30) ? "**" : "") + " on " + table.name
                    + " (" + field.sqlName + ");\n");

        } else {
            if (expected.length() > 30 || !expected.startsWith(expectedtable) || !expected.endsWith("_pk_i")) {
                errors.append(" --  Error de forma en l'index PK  " + index + "\n");
                errors.append("ALTER INDEX " + index + " RENAME to " + expected + ";\n");
            }
        }
        return expected;
    }

    protected static void checkTraduccions(String[] idiomes, String name, Map<String, String> labelsTaula,
            StringBuffer errors) {
        for (String lang : idiomes) {
            String value = labelsTaula.get(lang);
            boolean showerror = false;
            if (value == null) {
                labelsTaula.put(lang, name + "_XX_" + lang);
                showerror = true;
            } else {
                if (value.endsWith("_XX_" + lang)) {
                    showerror = true;
                }
            }
            if (showerror) {
                errors.append("-- La taula " + name + " no té definida l'etiqueta per l'idioma " + " [" + lang + "]\n");
            }
        }
    }

    public static class SequenceInfo {
        protected String name;
        protected long currentValue;

        public SequenceInfo() {
            super();
        }

        public SequenceInfo(String name, long currentValue) {
            super();
            this.name = name;
            this.currentValue = currentValue;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public long getCurrentValue() {
            return currentValue;
        }

        public void setCurrentValue(long currentValue) {
            this.currentValue = currentValue;
        }

    }

    protected static List<SequenceInfo> getSequenceList(Project project) {

        try {
            DataBaseInfoUtils.reuseConnection = false;
            Connection cnx = DataBaseInfoUtils.getConnection(project.getDataBaseInfo());

            String schema = project.getSchema();

            String catalog = "";
            String tableNamePattern = "%";
            String[] tableTypes = new String[] { "SEQUENCE" };
            ResultSet tablesRS = cnx.getMetaData().getTables(catalog, schema, tableNamePattern, tableTypes);

            List<SequenceInfo> listTables = new ArrayList<SequenceInfo>();

            while (tablesRS.next()) {
                String seq = tablesRS.getString("TABLE_NAME");

                // ORACLE "select SEQ_NAME.CURRVAL from dual";

                String sqlIdentifier = "select last_value from " + seq + ";";
                PreparedStatement pst = cnx.prepareStatement(sqlIdentifier);

                ResultSet rs = pst.executeQuery();
                long currvalue = -1;
                if (rs.next()) {
                    currvalue = rs.getLong(1);

                }

                listTables.add(new SequenceInfo(seq, currvalue));
            }

            return listTables;

        } catch (Throwable e) {
            log.error(" Error intentant consultar SEQUENCIES: " + e.getMessage(), e);
            return null;
        }
    }

}
