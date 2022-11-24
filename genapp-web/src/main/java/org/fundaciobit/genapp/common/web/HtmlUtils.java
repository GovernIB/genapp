package org.fundaciobit.genapp.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 
 * @author anadal
 * 
 */
public class HtmlUtils {

    public static final String MISSATGES = "missatges";

    public static final String ERROR = "danger";

    public static final String WARN = "warning";

    public static final String SUCCESS = "success";

    public static final String INFO = "info";

    public static void saveMessageInfo(HttpServletRequest request, String missatge) {
        addMessage(request, INFO, missatge);
    }

    public static void saveMessageWarning(HttpServletRequest request, String missatge) {
        addMessage(request, WARN, missatge);

    }

    public static void saveMessageSuccess(HttpServletRequest request, String missatge) {
        addMessage(request, SUCCESS, missatge);
    }

    public static void saveMessageError(HttpServletRequest request, String missatge) {
        addMessage(request, ERROR, missatge);
    }

    public static Map<String, List<String>> getAllMessages(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return (Map<String, List<String>>) session.getAttribute(MISSATGES);
    }

    private static void addMessage(HttpServletRequest request, String type, String missatge) {

        Map<String, List<String>> missatges = getAllMessages(request);

        if (missatges == null) {
            missatges = new HashMap<String, List<String>>();
            HttpSession session = request.getSession();
            session.setAttribute(MISSATGES, missatges);
        }

        List<String> missatgesTipus = missatges.get(type);

        if (missatgesTipus == null) {
            missatgesTipus = new ArrayList<String>();
            missatges.put(type, missatgesTipus);
        }

        missatgesTipus.add(missatge);

    }

    public static void deleteMessages(HttpServletRequest request) {

        Map<String, List<String>> missatges = getAllMessages(request);

        if (missatges == null) {
            missatges = new HashMap<String, List<String>>();
            HttpSession session = request.getSession();
            session.setAttribute(MISSATGES, missatges);
        }

        missatges.clear();
    }
}
