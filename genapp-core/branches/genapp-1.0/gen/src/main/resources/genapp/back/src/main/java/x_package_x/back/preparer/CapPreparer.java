package ${package}.back.preparer;

import java.util.List;
import java.util.Map;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;

import org.apache.log4j.Logger;
import org.apache.tiles.AttributeContext;
import org.apache.tiles.context.TilesRequestContext;
import org.apache.tiles.preparer.PreparerException;
import org.apache.tiles.preparer.ViewPreparerSupport;
import org.springframework.stereotype.Component;


/**
 * @author GenApp
 *
 */
@RunAs("${prefix}_USER")
@Component
public class CapPreparer extends ViewPreparerSupport {
  
  protected final Logger log = Logger.getLogger(getClass());

	@Override
	public void execute(TilesRequestContext tilesContext, 
	    AttributeContext attributeContext) throws PreparerException {

	  Map<String, Object> request = tilesContext.getRequestScope();

    // TODO GENAPP  Select  available languages 

	}
}
