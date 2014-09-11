package ${package}.ws.v1.test;

import java.io.FileInputStream;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;

import ${package}.ws.api.v1.${fullname}HelloWorldWs;
import ${package}.ws.api.v1.${fullname}HelloWorldWsService;
import ${package}.ws.api.v1.${fullname}HelloWorldWithSecurityWs;
import ${package}.ws.api.v1.${fullname}HelloWorldWithSecurityWsService;
import ${package}.ws.api.v1.utils.I18NUtils;

/**
 * 
 * @author anadal
 * 
 */
public abstract class ${fullname}TestUtils {

  public static final String HELLO_WORLD = "${fullname}HelloWorld";
  
  public static final String HELLO_WORLD_WITH_SECURITY = "${fullname}HelloWorldWithSecurity";
 
  // TODO GEN APP ADD OTHERS
  
  private static Properties testProperties = new Properties();
  
  static {
    // Traduccions
    try {
      Class.forName(I18NUtils.class.getName());
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    // Propietats del Servidor
    try {
      testProperties.load(new FileInputStream("test.properties"));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
 

  public static String getEndPoint(String api) {
    return testProperties.getProperty("test_host") + api;
  }

  public static String getTestAppUserName() {
    String caib = System.getProperty("caib");
    return testProperties.getProperty("test_usr" + (caib == null? "" : "_caib"));
  }
  
  public static boolean isCAIB() {
    return System.getProperty("caib") != null;
  }

  public static String getTestAppPassword() {
    String caib = System.getProperty("caib");
    return testProperties.getProperty("test_pwd"  + (caib == null? "" : "_caib"));
  }


  public static void configAddressUserPassword(String usr, String pwd,
      String endpoint, Object api) {

    Map<String, Object> reqContext = ((BindingProvider) api).getRequestContext();
    reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);
    reqContext.put(BindingProvider.USERNAME_PROPERTY, usr);
    reqContext.put(BindingProvider.PASSWORD_PROPERTY, pwd);
  }

  public static ${fullname}HelloWorldWs getHelloWorldApi() {

    final String endpoint = getEndPoint(HELLO_WORLD);

    ${fullname}HelloWorldWsService helloService = new ${fullname}HelloWorldWsService();

    ${fullname}HelloWorldWs helloApi = helloService.get${fullname}HelloWorldWs();

    // Adreça servidor
    Map<String, Object> reqContext = ((BindingProvider) helloApi).getRequestContext();
    reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpoint);

    return helloApi;

  }


  
 
  public static ${fullname}HelloWorldWithSecurityWs getHelloWorldWithSecurityApi() {
    final String endpoint = getEndPoint(HELLO_WORLD_WITH_SECURITY);

    ${fullname}HelloWorldWithSecurityWsService service = new ${fullname}HelloWorldWithSecurityWsService();

    ${fullname}HelloWorldWithSecurityWs api = service.get${fullname}HelloWorldWithSecurityWs();

    configAddressUserPassword(getTestAppUserName(), getTestAppPassword(), endpoint, api);

    return api;
  }

  
}
