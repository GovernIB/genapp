package ${package}.back.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genapp.common.web.i18n.I18NUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author anadal
 * 
 */
@ControllerAdvice
public class ${fullname}MaxUploadSizeExceededExceptionHandler {

    protected final Logger log = Logger.getLogger(getClass());

    @ExceptionHandler(${fullname}MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxUploadSizeExceededException(${fullname}MaxUploadSizeExceededException e) {
        /*
         * log.info(" ++++ Scheme: " + request.getScheme());
         * log.info(" ++++ PathInfo: " + request.getPathInfo());
         * log.info(" ++++ PathTrans: " + request.getPathTranslated());
         * log.info(" ++++ ContextPath: " + request.getContextPath());
         * log.info(" ++++ ServletPath: " + request.getServletPath());
         * log.info(" ++++ getRequestURI: " + request.getRequestURI());
         * log.info(" ++++ getRequestURL: " + request.getRequestURL().toString());
         * log.info(" ++++ getQueryString: " + request.getQueryString());
         */
        String maxUploadSize = "???";
        String currentSize = "???";
        String msgCode = "midafitxerpujatsuperat";
        if (e instanceof MaxUploadSizeExceededException) {
            MaxUploadSizeExceededException musee = (MaxUploadSizeExceededException) e;
            if (musee instanceof ${fullname}MaxUploadSizeExceededException) {
                msgCode = ((${fullname}MaxUploadSizeExceededException) musee).getMsgCode();
            }

            // log.error(" YYYYYYYYYYYY  CAUSE: " + musee.getCause());
            if (musee.getCause() instanceof SizeLimitExceededException) {
                SizeLimitExceededException slee = (SizeLimitExceededException) musee.getCause();
                maxUploadSize = String.valueOf(slee.getPermittedSize());
                currentSize = String.valueOf(slee.getActualSize());
            } else {
                maxUploadSize = String.valueOf(musee.getMaxUploadSize());
            }
        }
        
        HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        HtmlUtils.saveMessageError(r, I18NUtils.tradueix(msgCode, currentSize, maxUploadSize));

        ModelAndView mav = new ModelAndView("redirect:" + r.getRequestURL());
        return mav;
    }

}
