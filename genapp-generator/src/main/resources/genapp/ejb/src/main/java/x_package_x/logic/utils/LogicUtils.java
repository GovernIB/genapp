package ${package}.logic.utils;

import org.apache.log4j.Logger;
import ${package}.commons.utils.Configuracio;
import ${package}.commons.utils.Version;

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
