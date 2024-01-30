package org.fundaciobit.demogenapp.logic.utils;

import org.apache.log4j.Logger;
import org.fundaciobit.demogenapp.commons.utils.Configuracio;
import org.fundaciobit.demogenapp.commons.utils.Version;

/**
 * 
 * @author anadal
 *
 */
public class LogicUtils {

	protected static Logger log = Logger.getLogger(LogicUtils.class);

	public static String getVersio() {
		return new Version().getVersion() + (Configuracio.isCAIB() ? "-caib" : "");
	}

}
