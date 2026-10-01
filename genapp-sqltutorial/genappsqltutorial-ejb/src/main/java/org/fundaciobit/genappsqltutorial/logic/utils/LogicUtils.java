package org.fundaciobit.genappsqltutorial.logic.utils;



import org.apache.log4j.Logger;

import org.fundaciobit.genappsqltutorial.commons.utils.Configuracio;
import org.fundaciobit.genappsqltutorial.commons.utils.StaticVersion;

/**
 * 
 * @author anadal
 *
 */
public class LogicUtils {

	protected static Logger log = Logger.getLogger(LogicUtils.class);

	public static String getVersio() {
		return StaticVersion.VERSION + (Configuracio.isCAIB() ? "-caib" : "");
	}

}
