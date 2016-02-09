package org.fundaciobit.genapp.common.web.exportdata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author anadal
 *
 */
public class DataExporterManager {

  private static final Map<String, IDataExporter> dataExporters = new HashMap<String, IDataExporter>();

  public static void addDataExporter(IDataExporter dataExporter) {
    if (dataExporter != null) {
      dataExporters.put(dataExporter.getID(),dataExporter);
    }
  }

  public static List<IDataExporter> getAllDataExporters() {
    return new ArrayList<IDataExporter>(dataExporters.values());
  }

  public static IDataExporter getByID(String id) {
    return dataExporters.get(id);
  }

}
