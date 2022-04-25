package org.fundaciobit.genappsqltutorial.back.controller.all;

import org.apache.log4j.Logger;
import org.fundaciobit.genapp.common.web.HtmlUtils;
import org.fundaciobit.genappsqltutorial.back.utils.DAOProviderEjb;
import org.fundaciobit.genappsqltutorial.back.utils.WebFormatPrinterResultsImpl;
import org.fundaciobit.genappsqltutorial.commons.utils.Constants;
import org.fundaciobit.genappsqltutorial.logic.CustomersAllPermissionsService;
import org.fundaciobit.genappsqltutorial.logic.OrderDetailsAllPermissionsService;
import org.fundaciobit.genappsqltutorial.model.dao.ICustomersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrderDetailsManager;
import org.fundaciobit.genappsqltutorial.model.dao.IOrdersManager;
import org.fundaciobit.genappsqltutorial.model.dao.IProductsManager;
import org.fundaciobit.genappsqltutorial.model.dao.ISuppliersManager;
import org.fundaciobit.genappsqltutorial.tutorial.dao.IDAOProvider;
import org.fundaciobit.genappsqltutorial.tutorial.printer.PrinterResultsManager;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitInfo;
import org.fundaciobit.genappsqltutorial.tutorial.utils.UnitsManager;
import org.fundaciobit.genappsqltutorial.tutorial.utils.compiler.JavaCompileAndExecute;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;
import java.util.Base64;

import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * @autor anadal
 * 
 */
@Controller
@RunAs(Constants.GAS_ADMIN)
public class PublicController implements IDAOProvider {

    protected final Logger log = Logger.getLogger(getClass());

    @EJB(mappedName = CustomersAllPermissionsService.JNDI_NAME)
    protected CustomersAllPermissionsService customersEjb;

    @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.ProductsService.JNDI_NAME)
    protected org.fundaciobit.genappsqltutorial.ejb.ProductsService productsEjb;

    @EJB(mappedName = OrderDetailsAllPermissionsService.JNDI_NAME)
    protected OrderDetailsAllPermissionsService orderDetailsEjb;

    @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.SuppliersService.JNDI_NAME)
    protected org.fundaciobit.genappsqltutorial.ejb.SuppliersService suppliersEjb;
    
    @EJB(mappedName = org.fundaciobit.genappsqltutorial.ejb.OrdersService.JNDI_NAME)
    protected org.fundaciobit.genappsqltutorial.ejb.OrdersService ordersEjb;

    @RequestMapping(value = "/public/index.html")
    public ModelAndView principal(HttpSession session, HttpServletRequest request,
            HttpServletResponse response) throws Exception {

        Boolean initialized = (Boolean) session.getAttribute("inicialitzat");

        if (initialized == null) {
            HtmlUtils.saveMessageInfo(request, "MessageInfo : Benvingut a GenApp SQL Tutorial");
            session.setAttribute("inicialitzat", true);
        }

        return new ModelAndView("homepublic");

    }

    @RequestMapping("/public/unit/{unitClassName}")
    public ModelAndView arxiu(@PathVariable("unitClassName") String unitClassName,
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        UnitInfo unitInfo = UnitsManager.getPartsOfUnit(unitClassName,
                LocaleContextHolder.getLocale().getLanguage());

        ModelAndView mav = new ModelAndView("unitinfo");

        mav.addObject("unitInfo", unitInfo);

        return mav;

    }

    @RequestMapping("/public/unit/test/{unitClassName}/{methodName}")
    public void executeTestV2(@PathVariable("unitClassName") String unitClassName,
            @PathVariable("methodName") String methodName, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        try {
            WebFormatPrinterResultsImpl pr = (WebFormatPrinterResultsImpl) PrinterResultsManager
                    .getPrinterResults();
            pr.reset();

            DAOProviderEjb.setIDAOProviderOfSession(this);

            UnitInfo ui = UnitsManager.getUnitByClassName(unitClassName, "en");

            Class<?> cls = ui.getCls();

            Object instance = cls.newInstance();
            Method instanceMethod = cls.getMethod(methodName);
            instanceMethod.invoke(instance);

            response.setContentType("text/html");
            response.setCharacterEncoding("utf-8");

            String res = pr.getResult();
            // log.info("RESULTS => \n" + res );

            response.getWriter().print(res);
        } catch (Throwable th) {
            response.getWriter()
                    .print("<h2><p style=\"color:red\">" + th.getMessage() + "</p></h2>");
        }

    }

    /**
     * Executa un codi enviat per paràmetre
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping("/public/unit/testv2")
    public void executeTestV2(/* @PathVariable("unitClassName") String unitClassName, */
            HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("ENTRA A executeTest ...");

        String code2 = request.getParameter("code");

        log.info("\n]" + code2 + "[\n\n");

        try {

            log.info("\n  " + new String(Base64.getDecoder().decode(code2)) + "\n\n");

        } catch (Throwable th) {
            log.error(th);
        }

        WebFormatPrinterResultsImpl pr = (WebFormatPrinterResultsImpl) PrinterResultsManager
                .getPrinterResults();
        pr.reset();

        DAOProviderEjb.setIDAOProviderOfSession(this);

        String imports = "import java.util.*;\n" + "import org.fundaciobit.genapp.common.*;\n"
                + "import org.fundaciobit.genapp.common.query.*;\n"
                + "import org.fundaciobit.genappsqltutorial.model.entity.*;\n"
                + "import org.fundaciobit.genappsqltutorial.model.fields.*;\n"
                + "import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;\n\n";

        String test = "SelectMultipleKeyValue<String> select;\n"
                + "select = new SelectMultipleKeyValue<String>(CustomersFields.CUSTOMERNAME.select, CustomersFields.CITY.select);\n"
                + "List<KeyValue<String>> customersFields = this.getCustomerManager().executeQuery(select, null);\n"
                + "print(CustomersFields.CUSTOMERNAME, CustomersFields.CITY, customersFields);\n";

        String paquet = "genappsqltutorial";
        String className = "ExecuteSql" + +System.currentTimeMillis();
        String methodName = "execute";
/*
        String codeOK = "package " + paquet + ";\n\n" + imports + "public class " + className
                + " extends AbstractUnit {\n" + "    public void " + methodName
                + "() throws Exception {\n" + test + "    }\n" + "}\n";
*/
        String code = "package " + paquet + ";\n\n" + "import java.util.List;\r\n" + "\r\n"
                + "import org.fundaciobit.genapp.common.KeyValue;\r\n"
                + "import org.fundaciobit.genapp.common.query.SelectMultipleKeyValue;\r\n"
                + "import org.fundaciobit.genappsqltutorial.model.fields.CustomersFields;\r\n"
                + "import org.fundaciobit.genappsqltutorial.tutorial.utils.AbstractUnit;\r\n"
                + "\r\n" + "public class " + className + " extends AbstractUnit {\r\n"
                + "    public void execute() throws Exception {\r\n"
                + "SelectMultipleKeyValue<String> select;\r\n"
                + "select = new SelectMultipleKeyValue<String>(CustomersFields.CUSTOMERNAME.select, CustomersFields.CITY.select);\r\n"
                + "List<KeyValue<String>> customersFields = this.getCustomerManager().executeQuery(select, null);\r\n"
                + "print(CustomersFields.CUSTOMERNAME, CustomersFields.CITY, customersFields);\r\n"
                + "    }\r\n" + "}";

        log.info("CODE TO EXECUTE: \n\n" + code + "\n\n");

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        try {
            JavaCompileAndExecute.compileAndExecute(paquet, className, methodName, code,
                    Thread.currentThread().getContextClassLoader());

            // List<Customers> customers = customersEjb.select();
            // pr.print(customers);

            String res = pr.getResult();
            // log.info("RESULTS => \n" + res );

            response.getWriter().print(res);
        } catch (Throwable th) {
            response.getWriter()
                    .print("<h2><p style=\"color:red\">" + th.getMessage() + "</p></h2>");
        }

    }

    // =========================================================
    // =========================================================
    // ============ METODES DE IDAOProvider ===============
    // =========================================================
    // =========================================================

    @Override
    public void close() throws Exception {
        // No fer res
    }

    @Override
    public EntityManager getEntityManagerFactory() throws Exception {
        return customersEjb.getEntityManagerFactory();
    }

    @Override
    public ICustomersManager getCustomerManager() throws Exception {
        return this.customersEjb;
    }

    @Override
    public IProductsManager getProductsManager() throws Exception {
        return this.productsEjb;
    }

    @Override
    public IOrderDetailsManager getOrderDetailsManager() throws Exception {
        return this.orderDetailsEjb;
    }

    @Override
    public ISuppliersManager getSuppliersManager() throws Exception {
        return this.suppliersEjb;
    }
    
    @Override
    public IOrdersManager getOrdersManager() throws Exception {
        return this.ordersEjb;
    }

}
