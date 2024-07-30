package org.fundaciobit.demogenapp.hibernate;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.fundaciobit.demogenapp.commons.utils.Configuracio;
/**
 * ========= FITXER AUTOGENERAT - NO MODIFICAR !!!!!
 * @author jagarcia
 */
public class CustomHibernatePersistenceProvider extends HibernatePersistenceProvider {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, @SuppressWarnings("rawtypes") Map properties) {
		
		Map<String,String> projecteProperties = new HashMap<String,String>();
		
		if(!properties.isEmpty()) {
			projecteProperties.putAll(properties);
		}
		
		Properties fitxerProperties = Configuracio.getSystemAndFileProperties();
		if(fitxerProperties != null) {
			fitxerProperties.forEach((k,v) -> {
               String kStr = k.toString();
	        	if (kStr.startsWith("org.fundaciobit.demogenapp.hibernate")) {
	        		projecteProperties.put(kStr.replace("org.fundaciobit.demogenapp.",""), v.toString());
	        	} else if(kStr.startsWith("hibernate.")) {
	        		//if (!projecteProperties.containsKey(kStr)) {
	        			projecteProperties.put(kStr, v.toString());
	        		//}
	        	}
	        });
		}
		if (Configuracio.isDesenvolupament()) {
			projecteProperties.forEach((k,v) -> {
	        	log.info("HibernateProperties: " + k + ", Value : " + v);
	        });
		}
		return super.createContainerEntityManagerFactory(info, projecteProperties);
	}
}