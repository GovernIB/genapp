package org.fundaciobit.genapp.generator;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.common.GenAppUtils;
import org.fundaciobit.genapp.common.i18n.I18NException;
import org.fundaciobit.genapp.generator.CodeGenerator.DaoCommonCode;

/**
 * 
 * @author anadal
 *
 */
public class DaoEJBGenerator {

    public static void generaMissatges(String projectName, TableInfo[] tables, File dstEjbDir, String[] idiomes)
            throws IOException, Exception {
        File resourcesBack = new File(dstEjbDir, "src/main/resources");
        // String[] langs = new String[] { "ca" };

        for (String lang : idiomes) {
            File missatges = new File(resourcesBack, projectName.toLowerCase() + "_genapp_" + lang + ".properties");

            /*
             * String txt = FileUtils.readFileToString(missatges, "UTF-8");
             * 
             * int startMark = txt.indexOf("# ==== GENAPP MARK START"); int endMark =
             * txt.indexOf("# ==== GENAPP MARK END");
             * 
             * if (startMark == -1 || endMark == -1) { throw new
             * Exception("El fitxer de misstages " + missatges +
             * " no conté l'start i/o l'end tag"); }
             */

            StringBuffer str = new StringBuffer();

            // str.append(txt.substring(0, startMark));
            str.append("\n");
            str.append("#\n");

            str.append("##################################\n");
            str.append("#  NO MODIFICAR - DO NOT MODIFY  #\n");
            str.append("##################################\n");
            str.append("# NOTA: La següent secció està generada, per la qual cosa qualsevol modificació\n");
            str.append("# que es desitgi s´ha de fer sobre l´arxiu .genapp o serà borrat en la pròxima generació\n");
            // str.append("# ==== GENAPP MARK START\n");
            str.append("#\n");
            str.append("\n\n");

            for (TableInfo table : tables) {

                str.append("##############################################\n");
                str.append("# Classe " + table.getNameJava() + "\n");
                str.append("##############################################\n");
                str.append("\n");

                String model = CodeGenUtils.getModelName(table.getNameJava());
                String label = table.getLabels().get(lang).replace('\'', '´');
                String labelPlural = table.getLabelsPlural().get(lang);

                if (labelPlural == null) {
                    System.err.println(
                            " Plural de la taula ]" + table.nameJava + "[ per l'idioma {" + lang + "} val null");
                }

                labelPlural = labelPlural.replace('\'', '´');

                str.append(model + "." + model + "=" + label + "\n");
                str.append(model + "." + model + ".plural=" + labelPlural + "\n");
                // str.append(model + ".crear=Crear " + label + "\n");
                // str.append(model + ".creat=Creat " + label + " correctament\n");
                // str.append(model + ".llistar=Llistar " + label + "\n");
                // str.append(model + ".llistat=Llistat de " + label + "\n");
                // str.append(model + ".llistat.buit=No hi ha cap " + label + "\n");
                // str.append(model + ".modificar=Modificar " + label + "\n");
                str.append("#Camps\n");
                for (FieldInfo field : table.getFields()) {
                    str.append(
                            model + "." + field.javaName + "=" + field.getLabels().get(lang).replace('\'', '´') + "\n");
                }

                str.append("\n\n\n");

            }

            // str.append("#\n");
            // str.append(txt.substring(endMark));
            FileUtils.write(missatges, str.toString(), "UTF8");

        }
    }

    public static SourceFile generateCodeForManagerService(Project projecte, TableInfo taula, String jpaPackage,
            String ejbPackage, BasicPackages basicPackages, BeanCode beanCode) {

        String daoPackage = basicPackages.daoPackage;

        String tableNameJava = taula.getNameJava();
        
        final String jpa = tableNameJava + "JPA";
        final String iface = "I" + tableNameJava + "Manager";
        final String jpaIface = tableNameJava + "IJPAManager";
        final String managerFileName = tableNameJava + "Service";
        final String managerFileNameEJB = tableNameJava + "EJB";

        StringBuffer manager = new StringBuffer("");

        /** Cabecera */
        manager.append("\n");

        manager.append("package " + ejbPackage + ";\n\n");

        manager.append("// NO MODIFICAR - DO NOT MODIFY;\n");

        manager.append("import javax.ejb.Local;\n\n");

        DaoCommonCode dao = beanCode.daoCommonCode;
        if (dao.pkClass.endsWith("PK")) {
            manager.append("import " + daoPackage + "." + dao.pkClass + ";\n\n");
        }
        // manager.append("import " + packages.utilsPackage + ".StaticVersion;\n");
        manager.append("import " + jpaPackage + "." + jpa + ";\n");
        manager.append("import " + jpaPackage + "." + jpaIface + ";\n");
        manager.append("import " + daoPackage + "." + iface + ";\n\n");

        manager.append("import " + basicPackages.entityPackage + "." + tableNameJava + ";\n");
        manager.append("import " + I18NException.class.getName() + ";\n\n");

//        import org.fundaciobit.genapp.common.i18n.I18NException;


        manager.append("@Local\n");
        manager.append("public interface " + managerFileName + " extends " + jpaIface + "," + iface + " {\n");

        manager.append("\n");

        manager.append("    public static final String JNDI_NAME = \"java:app/" + projecte.projectName.toLowerCase()
                + "-ejb/" + managerFileNameEJB + "!" + ejbPackage + "." + managerFileName + "\";\n\n");

        // Sobre escriure findByPrimaryKey
        manager.append("    public " + jpa + " findByPrimaryKey(" + dao.pkClass + " _ID_);\n");
        manager.append("\n");

//      TableInfo[] tables = projecte.getTables();
//      List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, taula);
//      final boolean hasFileFields = fileFields != null && fileFields.size() != 0;

        manager.append("    public void deleteIncludingFiles(" + tableNameJava + " instance, " + "FitxerService fitxerEjb) throws I18NException;\n");
//        if (hasFileFields) {
//        }

        manager.append("}\n");

        return new SourceFile(managerFileName + ".java", manager.toString());

    }

    public static SourceFile generateCodeForManagerEJB(Project projecte, TableInfo taula, String jpaPackage,
            String ejbPackage, BasicPackages basicPackages, BeanCode beanCode) {

        String tableNameJava = taula.getNameJava();

        final String managerFileName = tableNameJava + "EJB";
        final String jpaManager = tableNameJava + "JPAManager";
        final String jpa = tableNameJava + "JPA";

        final String local = tableNameJava + "Service";

        StringBuffer manager = new StringBuffer("");

        /** Cabecera */
        manager.append("\n");

        manager.append("package " + ejbPackage + ";\n\n");

        manager.append("// NO MODIFICAR - DO NOT MODIFY;\n");
        manager.append("import javax.ejb.Stateless;\n");

//        manager.append("import java.util.ArrayList;\n");

        manager.append("import javax.annotation.security.RolesAllowed;\n");
        manager.append("import " + I18NException.class.getName() + ";\n");
        manager.append("import " + basicPackages.entityPackage + "." + tableNameJava + ";\n");
        manager.append("import " + jpaPackage + "." + jpa + ";\n");
        manager.append("import " + jpaPackage + "." + jpaManager + ";\n\n");
        manager.append("import " + basicPackages.utilsPackage + ".Constants;\n\n");

        // manager.append("import " + ejbPackage +
        // ".utils.CleanFilesSynchronization;\n\n");

        DaoCommonCode dao = beanCode.daoCommonCode;
        if (dao.pkClass.endsWith("PK")) {
            manager.append("import " + basicPackages.daoPackage + "." + dao.pkClass + ";\n\n");
        }

        // TODO falta DOC

        manager.append("@Stateless\n");
        manager.append("public class " + managerFileName + " extends " + jpaManager + " implements " + local + " {\n");
        manager.append("\n");

        // String prefix = projecte.getPrefix().toUpperCase();
        final String allRoles = "{Constants.ROLE_EJB_FULL_ACCESS, Constants.ROLE_EJB_BASIC_ACCESS, Constants.ROLE_EJB_WS_ACCESS}";
        // "{" + prefix + "_ADMIN," + "Constants." + prefix + "_USER}";

        TableInfo[] tables = projecte.getTables();

        List<FieldInfo> fileFields = CodeGenUtils.getFileFieldsOfTable(tables, taula);

        final boolean hasFileFields = fileFields != null && fileFields.size() != 0;

        if (hasFileFields) {
            manager.append("    @javax.annotation.Resource\n");
            manager.append("    protected javax.transaction.TransactionSynchronizationRegistry __tsRegistry;\n\n");
        }

        manager.append("    @Override\n");
        manager.append("    @RolesAllowed(" + allRoles + ")\n");
        manager.append("    public void delete(" + tableNameJava + " instance) {\n");
        manager.append("        super.delete(instance);\n");
        manager.append("    }\n\n");

        // public I create(I transientInstance) throws Exception
        manager.append("    @Override\n");
        manager.append("    @RolesAllowed(" + allRoles + ")\n");
        manager.append(
                "    public " + tableNameJava + " create(" + tableNameJava + " instance) throws I18NException {\n");
        manager.append("        return super.create(instance);\n");
        manager.append("    }\n\n");

        manager.append("    @Override\n");
        manager.append("    @RolesAllowed(" + allRoles + ")\n");
        manager.append(
                "    public " + tableNameJava + " update(" + tableNameJava + " instance) throws I18NException {\n");
        manager.append("         return super.update(instance);\n");
        manager.append("    }\n\n");

        if (fileFields != null) {

            manager.append("    @Override\n");
            manager.append("    @RolesAllowed(" + allRoles + ")\n");
            manager.append("    public void deleteIncludingFiles(" + tableNameJava + " instance, " + " FitxerService fitxerEjb)\n");
            manager.append("            throws I18NException {\n\n");

            if (hasFileFields) {
                manager.append("        java.util.ArrayList<Long> fitxers = new java.util.ArrayList<Long>();\n");

                for (FieldInfo field : fileFields) {
                    if (fileFields.contains(field)) {
                        String getter = GenAppUtils.getOnlyName(field, "get");
                        manager.append("        fitxers.add(instance." + getter + "());\n");
                    }
                }

                manager.append("\n");
            }

            manager.append("        this.delete(instance);\n");

            if (hasFileFields) {
                manager.append("\n");
                manager.append("        java.util.Set<Long> fitxersEsborrar = new java.util.HashSet<Long>();\n");
                manager.append("\n");
                manager.append("        // Borram fitxers a BD\n");

                manager.append("        for (Long f : fitxers) {\n");
                manager.append("            if (f != null) {\n");
                manager.append("                fitxerEjb.delete(f);\n");
                manager.append("                fitxersEsborrar.add(f);\n");
                manager.append("            }\n");
                manager.append("        }\n");

                manager.append("\n");
                manager.append("        // Borram fitxers fisic\n");
                manager.append("        __tsRegistry.registerInterposedSynchronization(new " + ejbPackage
                        + ".utils.CleanFilesSynchronization(fitxersEsborrar));\n");
            }
            manager.append("    }\n");
            manager.append("\n");
        }

        // Sobre escriure findByPrimaryKey
        manager.append("    @Override\n");
        manager.append("    @RolesAllowed(" + allRoles + ")\n");
        manager.append("    public " + jpa + " findByPrimaryKey(" + dao.pkClass + " _ID_) {\n");
        manager.append("        return (" + jpa + ")super.findByPrimaryKey(_ID_);\n");
        manager.append("    }\n\n");

        manager.append("}\n");

        return new SourceFile(managerFileName + ".java", manager.toString());
    }
}
