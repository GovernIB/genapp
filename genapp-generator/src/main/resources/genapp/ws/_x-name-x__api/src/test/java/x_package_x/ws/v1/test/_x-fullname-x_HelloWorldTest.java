package ${package}.ws.v1.test;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;

import ${package}.ws.api.v1.${fullname}HelloWorldWs;
import ${package}.ws.api.v1.utils.I18NUtils;

/**
 * 
 * @author anadal
 * 
 */
public class ${fullname}HelloWorldTest extends ${fullname}TestUtils {
  
 
  
  protected static ${fullname}HelloWorldWs helloWorldApi;
  
  /**
   * S'executa una vegada abans de l'execució de tots els tests d'aquesta classe
   * 
   * @throws Exception
   */
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    helloWorldApi = getHelloWorldApi();
  }
  
  //@Test
  public void testVersio() throws Exception {
    if (isCAIB()) {
      Assert.assertEquals("${app_current_version}-caib", helloWorldApi.getVersion());
    } else {
      Assert.assertEquals("${app_current_version}", helloWorldApi.getVersion());
    }
  }

  //@Test
  public void testVersioWs() throws Exception {
    Assert.assertEquals(1,helloWorldApi.getVersionWs());
  }
  
  
  //@Test
  public void testEcho() throws Exception {
    final String echo = "eco ecooooo";
    Assert.assertEquals(helloWorldApi.echo(echo), echo);
  }
  

  public static void main(String[] args) {
    try {
      
      System.out.println(I18NUtils.tradueix(null, new Locale("ca"), "signant", new String[]{}));

      ${fullname}HelloWorldWs helloApi = getHelloWorldApi();

      System.out.println("Versió ${fullname}   : " + helloApi.getVersion());
      System.out.println("Versió ${fullname}-WS: " + helloApi.getVersionWs());


    } catch (Throwable th) {
      th.printStackTrace();
    }
  }


}
