package org.fundaciobit.genapp.generator;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
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

	public static SourceFile generateCodeForManagerLocal(Project projecte, String tableNameJava, String jpaPackage,
			String ejbPackage, BasicPackages packages, BeanCode beanCode) {

		String daoPackage = packages.daoPackage;

		final String jpa = tableNameJava + "JPA";
		final String iface = "I" + tableNameJava + "Manager";
		final String jpaIface =  tableNameJava + "IJPAManager";
		final String managerFileName = tableNameJava + "Local";
		final String managerFileNameEJB = tableNameJava + "EJB";

		StringBuffer manager = new StringBuffer("");

		/** Cabecera */
		manager.append("\n");

		manager.append("package " + ejbPackage + ";\n\n");

		manager.append("import javax.ejb.Local;\n\n");
		

		DaoCommonCode dao = beanCode.daoCommonCode;
		if (dao.pkClass.endsWith("PK")) {
			manager.append("import " + daoPackage + "." + dao.pkClass + ";\n\n");
		}
		//manager.append("import " + packages.utilsPackage + ".StaticVersion;\n");
		manager.append("import " + jpaPackage + "." + jpa + ";\n");
		manager.append("import " + jpaPackage + "." + jpaIface+ ";\n");
		manager.append("import " + daoPackage + "." + iface + ";\n\n");

		manager.append("@Local\n");
		manager.append("public interface " + managerFileName + " extends " + jpaIface+ "," + iface + " {\n");

		manager.append("\n");

		manager.append(" public static final String JNDI_NAME = \"java:app/" + projecte.projectName.toLowerCase()
				+ "-ejb/" + managerFileNameEJB + "!" + ejbPackage + "." + managerFileName + "\";\n\n");

		// Sobre escriure findByPrimaryKey
		manager.append("    public " + jpa + " findByPrimaryKey(" + dao.pkClass + " _ID_);\n");
		manager.append("}\n");

		return new SourceFile(managerFileName + ".java", manager.toString());

	}

	public static SourceFile generateCodeForManagerEJB(Project projecte, String tableNameJava, String jpaPackage,
			String ejbPackage, BasicPackages basicPackages, BeanCode beanCode) {

		final String managerFileName = tableNameJava + "EJB";
		final String jpaManager = tableNameJava + "JPAManager";
		final String jpa = tableNameJava + "JPA";

		final String local = tableNameJava + "Local";

		StringBuffer manager = new StringBuffer("");

		/** Cabecera */
		manager.append("\n");

		manager.append("package " + ejbPackage + ";\n\n");

		manager.append("import javax.ejb.Stateless;\n");
		// manager.append("import org.jboss.ejb3.annotation.SecurityDomain;\n");
		manager.append("import javax.annotation.security.RolesAllowed;\n");
		manager.append("import " + I18NException.class.getName() + ";\n");
		manager.append("import " + basicPackages.entityPackage + "." + tableNameJava + ";\n");
		manager.append("import " + jpaPackage + "." + jpa + ";\n");
		manager.append("import " + jpaPackage + "." + jpaManager + ";\n\n");
		manager.append("import " + basicPackages.utilsPackage + ".Constants;\n\n");

		DaoCommonCode dao = beanCode.daoCommonCode;
		if (dao.pkClass.endsWith("PK")) {
			manager.append("import " + basicPackages.daoPackage + "." + dao.pkClass + ";\n\n");
		}

		// TODO falta DOC

		manager.append("@Stateless\n"); //(mappedName = \"" + managerFileName + "\")\n");
		/*
		 * manager.append("@LocalBinding(jndiBinding=\"" +
		 * projecte.getProjectName().toLowerCase() + "/" + managerFileName +
		 * "/local\")\n");
		 */
		// XYZ ZZZ manager.append("@SecurityDomain(\"" + projecte.getModulSeguretat() +
		// "\")\n");
		manager.append("public class " + managerFileName + " extends " + jpaManager + " implements " + local + " {\n");
		manager.append("\n");

		String prefix = projecte.getPrefix().toUpperCase();

		manager.append("  @Override\n");
		manager.append(
				"\t@RolesAllowed({Constants." + prefix + "_ADMIN,\r\n" + "        Constants." + prefix + "_USER})\n");
		manager.append("\tpublic void delete(" + tableNameJava + " instance) {\n");
		manager.append("\t\tsuper.delete(instance);\n");
		manager.append("\t}\n\n");

		// public I create(I transientInstance) throws Exception
		manager.append("  @Override\n");
		manager.append("\t@RolesAllowed({Constants." + prefix + "_ADMIN," + "Constants." + prefix + "_USER})\n");
		// manager.append("\t@PreAuthorize(\"hasRole('ROLE_USER')\")\n");
		manager.append(
				"\tpublic " + tableNameJava + " create(" + tableNameJava + " instance) throws I18NException {\n");
		manager.append("\t\treturn super.create(instance);\n");
		manager.append("\t}\n\n");

		// super.update(instance)
		manager.append("  @Override\n");
		manager.append(
				"\t@RolesAllowed({Constants." + prefix + "_ADMIN,\r\n" + "        Constants." + prefix + "_USER})\n");
		// manager.append("\t@PreAuthorize(\"hasRole('ROLE_USER')\")\n");
		manager.append(
				"\tpublic " + tableNameJava + " update(" + tableNameJava + " instance) throws I18NException {\n");
		manager.append("\t\t return super.update(instance);\n");
		manager.append("\t}\n\n");

		// Sobre escriure findByPrimaryKey
		manager.append("  @Override\n");
		manager.append(
				"\t@RolesAllowed({Constants." + prefix + "_ADMIN,\r\n" + "        Constants." + prefix + "_USER})\n");
		// manager.append("\t@PreAuthorize(\"hasRole('ROLE_USER')\")\n");
		manager.append("  public " + jpa + " findByPrimaryKey(" + dao.pkClass + " _ID_) {\n");
		manager.append("    return (" + jpa + ")super.findByPrimaryKey(_ID_);\n");
		manager.append("  }\n\n");

		manager.append("}\n");

		return new SourceFile(managerFileName + ".java", manager.toString());
	}
}
