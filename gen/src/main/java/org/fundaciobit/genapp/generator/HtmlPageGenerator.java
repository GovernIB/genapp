package org.fundaciobit.genapp.generator;

import java.util.ArrayList;
import java.util.Arrays;

import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.generator.WebGenerator.Button;

/**
 * Title:        Rapit Entity Bean 2010
 * Description:
 * Copyright:    Copyright (c) 2014
 * Company:      XmasSoft
 * @author anadal
 * @version 1.0
 */
public class HtmlPageGenerator {
  
  public static final String FORM_NAME = "REB2010";
  
  public static final String SUBMITBUTTON = "SubmitButton";
  
  public static final String TableManagerJsp = "TableManager.jsp";
  
  final Project project;
  final String title;
  
  String tableName = null;
  final public StringBuffer javaScript = new StringBuffer();
  final public ArrayList<String> imports = new ArrayList<String>();
  String onLoad = null;
  Button submitButton = null;
  TableGenerator tableGenerator = null;
  String[] rolesOfPage = null;
  
  final StringBuffer formCode=new StringBuffer("");
  

  
  public HtmlPageGenerator(Project project, String title) {
    super();
    this.project = project;
    this.title = title;
  }
  
  
  public String getTableName() {
    return tableName;
  }
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getOnload() {
    return onLoad;
  }
  public void setOnload(String onload) {
    this.onLoad = onload;
  }

  public Button getSubmitButton() {
    return submitButton;
  }


  public void setSubmitButton(Button submitButton) {
    this.submitButton = submitButton;
  }

  public TableGenerator getTableGenerator() {
    return tableGenerator;
  }
  public void setTableGenerator(TableGenerator tableGenerator) {
    this.tableGenerator = tableGenerator;
  }
  public Project getProject() {
    return project;
  }
  public String getTitle() {
    return title;
  }
  
  /**
   * 
   * @return
   */
  public String generateHtmlPage() {
    StringBuffer jsp =  new StringBuffer();

    jsp.append(generateRolesOfPage());
    
    jsp.append(getHtmlHeader());

    if (this.tableGenerator != null) {
      jsp.append(this.tableGenerator.generateTableCode());
    }

    jsp.append(getHtmlFooter());
    
    return jsp.toString();

  }

  /**
   * 
   * @return
   */
  private String generateRolesOfPage() {
    if (rolesOfPage == null /*|| project.secInfo==null*/) {
      return "";
    } else {
      return ("<%! \n" +
        "  @Override" + "\n" +
        "  public String[] getRolesOfPage() {" + "\n" +
        "    return new String[] {" + Arrays.toString(rolesOfPage).replace('[', ' ').replace(']', ' ') + "};\n" +
        "  } %>" + "\n");
    }
  }

  /**
   * 
   * @return
   */
  private String getHtmlHeader() {

    StringBuffer importsJsp = new StringBuffer();
    for (String imp : this.imports) {
      importsJsp.append("<%@page import=\"" + imp + "\"%>\n");  
    }
    

    return "<%@ page language=\"java\" extends=\"" + project.getPackageName() 
      + "." + WebGenerator.WEB_PACKAGE_NAME + "."
      + project.projectName + "JspBase\"  contentType=\"text/html;"
      +	" charset=ISO-8859-1\" pageEncoding=\"ISO-8859-1\"%>\n"
      + importsJsp.toString()
      + "<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\"" +
      		" \"http://www.w3.org/TR/html4/loose.dtd\">\n"
      + "<html>\n"
      + "<head>\n"
      + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n"
      + "<title>" + title + "</title>\n"
      + "<link rel=\"StyleSheet\" href=\"style.css\" type=\"text/css\">\n"
      + "<link rel=\"StyleSheet\" href=\"table/datatable.css\" type=\"text/css\">\n"
      + "<script type=\"text/javascript\" src=\"javascript.js\"></script>\n"
      // @todo only if exists field Date or Time
      + "<script src=\"calendar/datetimepicker_css.js\"></script>\n"
      // @todo Add only if exists field RichText
      + "<script type=\"text/javascript\" src=\"tiny_mce/tiny_mce.js\"></script>\n"
      + "<script type=\"text/javascript\">\n"
      + javaScript
      + "</script>\n"
      + "</head>\n"
      + "<body " + ((onLoad== null)? "" : onLoad) + " >\n"
      + "<FORM name=\"" + FORM_NAME+ "\" method=\"post\">\n"
      + formCode.toString()
      + "   <input type=\"hidden\" name=\"loadedPage\" value=\"true\">\n"
      + (tableName == null ? "" : generateButtonBar(tableName) ) + "\n";
  }


  /**
   * 
   * @param tableName
   * @return
   */
  private String generateButtonBar(String tableName) {
    final String style = "style=\"vertical-align: middle; border: 0px;\"";
    final String tableClass = "border=\"0\""; //"class=\"dummy\" ";
    final String tab = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
    return 
        "<% String hideHeader = request.getParameter(\"hideHeader\");\n" +
        " if (hideHeader != null) { %>\n" +
        "  <input type=\"hidden\" name=\"hideHeader\" value=\"true\">\n" +
        " <% } else { %>\n" +
        "<table " + tableClass + ">" + "\n" +
        "<tr " + style + ">" + "\n" +
        "  <td " + style + "><a href=\"" + TableManagerJsp + "\">" +
        		"<img src=\"./images/home.gif\"></a></td>" + "\n" +
        "  <td " + style + "><a href=\"" + TableManagerJsp + "\">Home</a></td>" + "\n" +
        
        "  <td " + style + "> &nbsp;&nbsp;&nbsp; </td>\n" +
        "  <td " + style + "><a href=\"" + tableName + "_List.jsp\">" +
        		"<img src=\"./images/list.gif\"></a></td>" + "\n" +
        "  <td " + style + "><a href=\"" + tableName + "_List.jsp\">Llistat</a></td>\n" +
         
        
        "  <td " + style + "> &nbsp;&nbsp;&nbsp; </td>\n" +
        "  <td " + style + "><a href=\"" + tableName + "_New.jsp\">" +
        		"<img src=\"./images/new.gif\"></a></td>" + "\n" +
        "  <td " + style + "><a href=\"" + tableName + "_New.jsp\">" +
        		"Nou Element</a></td>" + "\n" +
        
        "  <% if(isSecurityEnabled()) { %>\n" +
        "  <td " + style + ">" +
        "     <table " + tableClass + ">" + "\n" +
        "       <tr><td " + style + ">" +
        	tab + "Usuari:<b>'<%=session.getAttribute(FULLNAME) + \"  \" + " +
        	" java.util.Arrays.toString(getSecurity().getRolesOfUser((String)session.getAttribute(USER)))%>'</b></td></tr>\n" +
        "       <tr><td " + style + " style=\"color:blue;\">" + tab 
        + "<a href=\"<%=getLogoutPage(request)%>\">Logout</a></td></tr>" + "\n" +
        "     </table>" + "\n" +
        "  </td>" + "\n" +
        " <% } %>\n" +
        "</tr>" + "\n" +
        "</table>\n" +
        "<hr>\n" +
        "<% } %>";

  }

  /**
   * 
   * @return
   */
  private String getHtmlFooter() {
    StringBuffer but = new StringBuffer();
    if (submitButton != null) {
      but.append("<table border=0 width=\"100%\">\n");
      but.append("  <tr><td align=\"center\">\n");

      but.append("    <INPUT type=\"submit\" name=\"" + SUBMITBUTTON 
          + "\" value=\"" + submitButton.getName() + "\">\n");
      
      but.append("<% if (redirectUrl!=null) { %>\n");      
      but.append(" <input type=\"button\" name=\"Tornar\" value=\"Tornar\"" +
          " onclick=\"location.href='<%=redirectUrl%>';\">\n");
      but.append("<% } %>\n");
      
      but.append("  </td></tr>\n");
      but.append("</table>\n");
      if (submitButton.getJavascript() != null) {
         but.append("<script type=\"text/javascript\">\n");
         but.append("   " + submitButton.getJavascript()+ ";\n");
         but.append("</script>\n");
      }      
    }
    return  but.toString()  + "\n</FORM>\n" + "</body>\n" + "</html>\n";
  }

}
