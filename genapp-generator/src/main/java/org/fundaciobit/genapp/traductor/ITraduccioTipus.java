package org.fundaciobit.genapp.traductor;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author anadal
 *
 */

public interface ITraduccioTipus {

    String getNom();

    String getTipus();

    List<ITraduccioItem> read() throws Exception;

    void save(List<ITraduccioItem> list, Map<Integer, ITraduccioItem> traduccionsPerHashDeClau) throws Exception;

}
