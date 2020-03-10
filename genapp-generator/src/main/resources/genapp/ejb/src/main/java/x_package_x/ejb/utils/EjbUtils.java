package ${package}.ejb.utils;

import ${package}.utils.StaticVersion;

/**
 * 
 * @author anadal
 *
 */
public class EjbUtils {

	public static final String getJndiName(Class<?> ifaceEJB) {

		String simpleName = ifaceEJB.getSimpleName();
		if (!simpleName.endsWith("Local")) {
			throw new RuntimeException("El nom de la interficie del Servei EJB ha d'acabar en Local");
		}

		String tableName = simpleName.substring(0, simpleName.length() - "Local".length());

		return "java:app/" + StaticVersion.PROJECT_NAME + "-ejb"

				+ tableName + "EJB!" + ifaceEJB.getName();

	}

	public static final String getEjbName(Class<?> ejb) {

		String simpleName = ejb.getSimpleName();
		if (!simpleName.endsWith("EJB")) {
			throw new RuntimeException("El nom de la implementació del Servei EJB ha d'acabar en EJB");
		}

		return ejb.getSimpleName();
	}

}