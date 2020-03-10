package org.fundaciobit.genapp.generator;

import java.beans.XMLDecoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.fundaciobit.genapp.FieldInfo;
import org.fundaciobit.genapp.GenAppPackages;
import org.fundaciobit.genapp.MultipleUnique;
import org.fundaciobit.genapp.Project;
import org.fundaciobit.genapp.TableInfo;
import org.fundaciobit.genapp.Versio;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

/**
 * Title: Rapit Entity Bean 2010 Description: Copyright: Copyright (c) 2014
 * Company: XmasSoft
 * 
 * @author anadal
 */
public class CodeGenerator {

	private static final Logger log = Logger.getLogger(CodeGenerator.class.getSimpleName());

	/*
	 * public static void main(String[] args) {
	 * 
	 * try {
	 * 
	 * Map<String, File> map = listingJars(); final String core = "genapp-core-" +
	 * Versio.VERSIO + ".jar"; final String web = "genapp-web-" + Versio.VERSIO +
	 * ".jar"; final String gen = "genapp-gen-" + Versio.VERSIO + ".jar";
	 * 
	 * for(String name : map.keySet()) { if (name.equals(core) || name.equals(web)
	 * || name.equals(gen)) { //GenAppUtils.copyFile(map.get(name), dstPath)
	 * log.info(name); } }
	 * 
	 * 
	 * // final String path = "genapp/jpa"; // // // boolean overwrite = true; //
	 * File destDir = new File(".\\borrar"); // copyResourcesToDir(path,
	 * destDir,overwrite);
	 * 
	 * 
	 * } catch (Exception e) { e.printStackTrace(); } }
	 */

	public static void recursiveSubstitution(File dstBaseDir, String resource, Map<String, Object> prop,
			Project project, boolean overwrite, String... skipSubstitutionsByNames) throws Exception {

		List<String> resources = llistatDeRecursos(resource);

		if (resources.size() == 0) {
			throw new Exception("La ruta al recurs " + resource + " esta buida");
		}

		List<String> skipSubstitutions = Arrays.asList(skipSubstitutionsByNames);

		int beginIndex = resource.length();

		String thePackageFile = project.getPackageName().replace('.', '/');

		for (String res : resources) {
			log.info(res);

			String fileName = res.substring(beginIndex).replace("x_package_x", thePackageFile);
			fileName = fileName.replace("_x-name-x_", project.getProjectName().toLowerCase());
			fileName = fileName.replace("_x-fullname-x_", project.getProjectName());
			File dest = new File(dstBaseDir, fileName);
			if (res.endsWith("/")) {
				dest.mkdirs();
			} else {

				// TODO log.debug
				/*
				 * log.info("------" + resource + "-----"); log.info("RES : " + res);
				 * log.info("FILE: " + dest.getAbsolutePath()); log.info("-----------");
				 */

				if (dest.exists() && !overwrite) {
					continue;
				}

				dest.getParentFile().mkdirs();

				final byte[] data = getResource(res);
				if (skipSubstitutions(skipSubstitutions, dest)) {
					FileUtils.writeByteArrayToFile(dest, data);
				} else {
					SourceFile persist = substitution(dest.getName(), new String(data), prop);
					persist.saveToPath(dest.getParentFile());
				}
			}
		}

	}

	private static boolean skipSubstitutions(List<String> skipSubstitutions, File dest) {

		if (skipSubstitutions.contains(dest.getName())) {
			return true;
		}

		String pathDest = dest.getAbsolutePath().replace('\\', '/');
		for (String skip : skipSubstitutions) {
			if (skip.endsWith("/")) {
				if (pathDest.indexOf(skip) != -1) {
					return true;
				}
			}
		}

		return false;

	}

	public static void copyResourcesToDir(String path, File destDir, boolean overwrite) throws Exception {

		List<String> resources = llistatDeRecursos(path);

		if (resources.size() == 0) {
			throw new Exception("La ruta al recurs " + path + " esta buida");
		}

		for (String app : resources) {
			log.info(app);
		}

		int beginIndex = path.length();

		// Crear directoris
		destDir.mkdirs();
		for (String res : resources) {
			if (res.endsWith("/")) {
				new File(destDir, res.substring(beginIndex)).mkdirs();
			}
		}

		for (String res : resources) {
			if (!res.endsWith("/")) {
				File dest = new File(destDir, res.substring(beginIndex));
				if (dest.exists() && !overwrite) {
					continue;
				}
				InputStream is = CodeGenerator.class.getClassLoader().getResourceAsStream(res);
				FileOutputStream fos = new FileOutputStream(dest);
				IOUtils.copy(is, fos);
				fos.close();
				is.close();
			}
		}

	}

	public static byte[] getResource(String res) throws Exception {
		InputStream is = CodeGenerator.class.getClassLoader().getResourceAsStream(res);
		ByteArrayOutputStream fos = new ByteArrayOutputStream();
		IOUtils.copy(is, fos);
		fos.close();
		is.close();
		return fos.toByteArray();
	}

	public static List<String> llistatDeRecursos(String path) throws Exception {

		List<String> llistat = new ArrayList<String>();

		final File jarFile = new File(
				CodeGenerator.class.getProtectionDomain().getCodeSource().getLocation().getPath());

		log.info("genapp_run = " + System.getProperty("genapp_run"));

		if (jarFile.isFile() || System.getProperty("genapp_run") != null) { // Run with JAR file
			log.info("IS FILE " + System.getProperty("genapp_run"));

			final JarFile jar = new JarFile(jarFile.getAbsolutePath().replaceAll("%20", " "));
			final Enumeration<JarEntry> entries = jar.entries(); // gives ALL entries in jar
			while (entries.hasMoreElements()) {
				final String name = entries.nextElement().getName();
				if (name.startsWith(path + "/")) { // filter according to the path
					if (!name.endsWith("/dummy.txt")) {
						llistat.add(name);
					}
				}
			}
			jar.close();

		} else { // Run with IDE

			throw new Exception("DO NOT LAUNCH FROM ID !!!!");

			/*
			 * log.info("IS IDE" + System.getProperty("genapp_run"));
			 * 
			 * final URL url = Launcher.class.getResource("/" + path); if (url != null) {
			 * try { final File apps = new File(url.toURI()); recursiveIDE(path, apps,
			 * llistat);
			 * 
			 * } catch (URISyntaxException ex) { // never happens } }
			 */
		}

		return llistat;

	}

	public static void recursiveIDE(String path, File file, List<String> list) {

		if (file.isFile()) {
			if (!"dummy.txt".equals(file.getName())) {
				list.add(path);
			}
		} else {
			File[] files = file.listFiles();
			list.add(path + "/");
			for (File f : files) {
				recursiveIDE(path + "/" + f.getName(), f, list);
			}

		}
	}

	public static void main(String[] args) {

		// Path to .reb file
		// Destination dir
		if (args.length < 2) {
			log.info("java " + GenAppPackages.PKG_BASE + ".generator.CodeGenerator "
					+ "<Path to .reb file> <Destination Dir.>)");
			System.exit(-1);
		}
		try {
			String rebPath = args[0];
			String dstDir = args[1];

			FileInputStream fis = new FileInputStream(rebPath);
			XMLDecoder dec = new XMLDecoder(fis);
			Project project = (Project) dec.readObject();
			generateCode(project, new File(dstDir));
			dec.close();

			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}

	}

	/*
	 * public static void main2(String[] args) { // Driver // URL // package //
	 * sourceDst // projectName // user // password if (args.length < 6 ||
	 * args.length > 7) { System.out .println("java " + GenAppPackages.PKG_BASE +
	 * ".generator.CodeGenerator " +
	 * "<Driver> <URL> <package> <sourceDst> <projectName> <user> (<password>)");
	 * System.exit(-1); } try { String driver = args[0]; String url = args[1];
	 * String packageName = args[2]; String srcDst = args[3]; String projectName =
	 * args[4]; String user = args[5]; String pwd; if (args.length == 7) { pwd =
	 * args[5]; } else { pwd = ""; }
	 * 
	 * generateCode(driver, url, packageName, srcDst, projectName, user, pwd);
	 * 
	 * } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/*
	 * public static void generateCode(String driver, String url, String
	 * packageName, String srcDst, String projectName, String user, String pwd)
	 * throws Exception, SQLException, FileNotFoundException, IOException {
	 * 
	 * DataBaseInfo dbInfo = new DataBaseInfo(driver, url, user, pwd);
	 * 
	 * DatabaseManager dbManager = new DatabaseManager(dbInfo);
	 * 
	 * TableInfo[] tableInfos = dbManager.getTablesOfDataBase();
	 * 
	 * 
	 * 
	 * Project project = new Project(packageName, projectName, dbInfo, tableInfos);
	 * 
	 * File srcDir = new File(srcDst);
	 * 
	 * generateCode(project, srcDir); }
	 */

	public static SourceFile substitution(String name, String template, Map<String, Object> datamodel)
			throws Exception {
		Configuration cfg = new Configuration();

		StringTemplateLoader ftl1 = new StringTemplateLoader();
		ftl1.putTemplate(name, template);

		cfg.setTemplateLoader(ftl1);
		return substitution(datamodel, cfg, name);
	}

	public static SourceFile substitution(File template, Map<String, Object> datamodel) throws Exception {
		Configuration cfg = new Configuration();

		FileTemplateLoader ftl1 = new FileTemplateLoader(template.getParentFile());
		cfg.setTemplateLoader(ftl1);
		String name = template.getName();

		return substitution(datamodel, cfg, name);
	}

	private static SourceFile substitution(Map<String, Object> datamodel, Configuration cfg, String name)
			throws IOException, TemplateException {
		freemarker.template.Template tpl = cfg.getTemplate(name);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter output = new OutputStreamWriter(baos);

		tpl.process(datamodel, output);

		SourceFile sf = new SourceFile(name, baos.toString(Charset.defaultCharset()));

		return sf;
	}

	public static File generateCode(Project project, File projectDir) throws Exception {

		String packageName = project.getPackageName();
		String packagePath = packageName.replace('.', '/');
		List<String> moduls = new ArrayList<String>();

		// ============ Directories
		/*
		 * dstDir.mkdirs(); File projectDir = new File(dstDir, project.projectName);
		 */
		projectDir.mkdirs();

		TableInfo[] tables = project.tables;

		String resourceBase = new String("genapp");

		// Generate common Source Code for Bean and Interfaces
		Map<String, BeanCode> beanCodeBySqlTableName = new HashMap<String, BeanCode>();

		for (int i = 0; i < tables.length; i++) {
			if (!tables[i].isGenerate()) {
				continue;
			}
			BeanCode beanGenCode;
			beanGenCode = generateBeanPartialCode(tables[i].getNameJava(), tables[i].getFields());

			beanCodeBySqlTableName.put(tables[i].name, beanGenCode);
		}

		String appCurrentVersion;

		File cvFile = new File(projectDir, "versio.txt");
		if (cvFile.exists()) {
			appCurrentVersion = IOUtils.toString(new FileInputStream(cvFile), Charset.defaultCharset());
		} else {
			appCurrentVersion = "1.0.0";
		}

		// ====================================================================
		// ================== Copy jars to lib ===============================
		// ====================================================================
		/*
		 * File libDir = new File(projectDir, "lib"); libDir.mkdirs(); { Map<String,
		 * File> map = listingJars(); final String core = "genapp-core-" + Versio.VERSIO
		 * + ".jar"; final String web = "genapp-web-" + Versio.VERSIO + ".jar"; final
		 * String gen = "genapp-gen-" + Versio.VERSIO + ".jar";
		 * 
		 * boolean copiats = false; for(String name : map.keySet()) { if
		 * (name.equals(core) || name.equals(web) || name.equals(gen)) {
		 * log.info("Copiant Fitxer " + name + " a " + libDir.getAbsolutePath());
		 * copyFile(map.get(name), new File(libDir, name)); copiats = true; } }
		 * 
		 * if (!copiats) { final File coreJar = new
		 * File(Field.class.getProtectionDomain().getCodeSource().getLocation().getPath(
		 * ).replace("%20", " ")); log.info("CORE JAR = " + coreJar); copyFile(coreJar,
		 * new File(libDir, coreJar.getName()));
		 * 
		 * 
		 * final File webJar = new
		 * File(IDataExporter.class.getProtectionDomain().getCodeSource().getLocation().
		 * getPath().replace("%20", " ")); log.info("WEB JAR = " + webJar);
		 * copyFile(webJar, new File(libDir, webJar.getName()));
		 * 
		 * final File genJar = new
		 * File(CodeGenerator.class.getProtectionDomain().getCodeSource().getLocation().
		 * getPath().replace("%20", " ")); log.info("GEN JAR = " + genJar);
		 * copyFile(genJar, new File(libDir, genJar.getName())); } }
		 */

		BasicPackages packages = new BasicPackages(packageName);

		// ====================================================================
		// ========================== INTERFACE ===============================
		// ====================================================================

		// ================== MODUL MODEL: POM.XML, INTERFICIES ENTITY,

		File fieldSrcDir;
		fieldSrcDir = generateModel(project, projectDir, packagePath, moduls, tables, resourceBase,
				beanCodeBySqlTableName, appCurrentVersion, packages);

		// ==============================================================
		// ========================== JPA ===============================
		// ==============================================================

		// ============= MAVEN MODUL JPA: pom.xml i Bean amb annotacions

		String jpaPackage;
		final String jpaName = "jpa";
		jpaPackage = project.getPackageName() + "." + jpaName;
		String validatorPackage = generateJPA(project, projectDir, packagePath, moduls, tables, resourceBase,
				beanCodeBySqlTableName, appCurrentVersion, packages, fieldSrcDir, jpaPackage, jpaName);

		// ==============================================================
		// ========================== EJB ===============================
		// ==============================================================

		// ============= MAVEN MODUL EJB: pom.xml, Manager EJB i Interficie Local

		String ejbPackage;
		ejbPackage = generateEJB(project, projectDir, packagePath, moduls, tables, resourceBase, beanCodeBySqlTableName,
				appCurrentVersion, packages, jpaPackage);

		// ===============================================
		// ================== LOGIC ======================
		// ===============================================
		generateLogic(project, projectDir, packagePath, moduls, resourceBase, appCurrentVersion);

		// ===============================================
		// ================== PERSISTENCE ======================
		// ===============================================

		generatePersistence(project, projectDir, packagePath, moduls, tables, resourceBase, appCurrentVersion,
				jpaPackage);

		// ===============================================
		// ================== UTILS ======================
		// ===============================================

		generateUtils(project, projectDir, packagePath, moduls, resourceBase, appCurrentVersion);

		// ===============================================
		// ================== WS (WebServices) ======================
		// ===============================================
		if (project.isGenerateWS()) {
			generateWS(project, projectDir, packagePath, moduls, tables, resourceBase, appCurrentVersion);
		}

		// ===============================================
		// ================== BACK ======================
		// ===============================================
		if (project.isGenerateBack()) {
			generateBack(project, projectDir, packagePath, moduls, tables, resourceBase, beanCodeBySqlTableName,
					appCurrentVersion, packages, jpaPackage, validatorPackage, ejbPackage);
		}

		// ==================================================
		// ==================================================
		// ============== EAR =========================
		// ==================================================
		// ==================================================
		generateEar(project, projectDir, packagePath, moduls, resourceBase, appCurrentVersion);

		// ==================================================
		// ==================================================
		// ===== CODI COMU (ha de ser el darrer !!!) =======
		// ==================================================
		// ==================================================

		generateCommonCode(project, projectDir, packagePath, moduls, resourceBase, appCurrentVersion);

		return projectDir;

	}

	private static void generateEar(Project project, File projectDir, String packagePath, List<String> moduls,
			String resourceBase, String appCurrentVersion) throws Exception {
		{
			// (a) Afegir a la llista de mòduls a compilar

			moduls.add("ear");

			String earName = "ear";

			File dstEarDir = new File(projectDir, earName);
			// File baseCode = new File(dstEarDir, "src/main/java");
			// String backPackage = project.getPackageName() + "." + earName;
			// File backJavaCode = new File(baseCode, backPackage.replace('.', '/'));

			// (2) Còpia inicial: Copiar estructura directoris des de recursos
			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("genapp_version", Versio.VERSIO);

			prop.put("artifactid", project.projectName.toLowerCase() + "-back");
			prop.put("prefix", project.getPrefix().toUpperCase());

			prop.put("spring_version", "${spring.version}");

			prop.put("project_version", "${project.version}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("hibernate_annotations_version", "${hibernate.annotations.version}");

			prop.put("springsecurity_version", "${springsecurity.version}");

			prop.put("basedir", "${basedir}");

			prop.put("timestamp", "${timestamp}");

			prop.put("clientcert_properties", "${clientcert.properties}");

			prop.put("clientcert_properties_dir", "${clientcert.properties.dir}");

			prop.put("clientcert_properties_filename", "${clientcert_properties_filename}");

			prop.put("line_separator", "${line.separator}");

			prop.put("maven_build_timestamp", "${maven.build.timestamp}");

			prop.put("generatews", project.isGenerateWS());
			prop.put("generateback", project.isGenerateBack());

			// ----- Tots fitxers de forma recursiva i substituint
			String resourceUtils = resourceBase + "/" + earName;
			{
				boolean overwrite = false;
				recursiveSubstitution(dstEarDir, resourceUtils, prop, project, overwrite);
			}

		}
	}

	private static void generateBack(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, Map<String, BeanCode> beanCodeBySqlTableName,
			String appCurrentVersion, BasicPackages packages, String jpaPackage, String validatorPackage,
			String ejbPackage) throws Exception, FileNotFoundException, IOException {
		{
			// (0) Variables
			final String backName = "back";
			File dstBackDir = new File(projectDir, backName);
			File baseCode = new File(dstBackDir, "src/main/java");
			String backPackage = project.getPackageName() + "." + backName;
			// File backJavaCode = new File(baseCode, backPackage.replace('.', '/'));

			// (1) Preparar quins fitxers esborrar

			// final String name = project.projectName.toLowerCase();
			// final String fullname = project.projectName;

			final String[] fitxersABorrar = new String[] {
					dstBackDir.getAbsolutePath()
							+ "/src/main/java/es/caib/portafib/back/controller/admin/AdminController.java",
					dstBackDir.getAbsolutePath()
							+ "/src/main/java/es/caib/portafib/back/controller/common/CommonController.java",
					dstBackDir.getAbsolutePath()
							+ "/src/main/java/es/caib/portafib/back/controller/user/UserController.java",
					dstBackDir.getAbsolutePath() + "/src/main/webapp/WEB-INF/jsp/admin/admin.jsp",
					dstBackDir.getAbsolutePath() + "/src/main/webapp/WEB-INF/jsp/common/option.jsp",
					dstBackDir.getAbsolutePath() + "/src/main/webapp/WEB-INF/jsp/moduls/menu_admin.jsp",
					dstBackDir.getAbsolutePath() + "/src/main/webapp/WEB-INF/jsp/moduls/menu_user.jsp",
					dstBackDir.getAbsolutePath() + "/src/main/webapp/WEB-INF/jsp/user/user.jsp" };

			List<File> toDelete = new ArrayList<File>();

			for (int i = 0; i < fitxersABorrar.length; i++) {

				System.err.println("fitxersABorrar[" + i + "]:" + fitxersABorrar[i]);

				File exampleCode = new File(fitxersABorrar[i]);

				System.err.println("X_" + exampleCode.exists() + "_X " + exampleCode.getAbsolutePath());

				if (!exampleCode.exists() && exampleCode.getParentFile().exists()) {
					// OK: S'ha de borrar
					toDelete.add(exampleCode);
				}
			}

			// (2) Còpia inicial: Copiar estructura directoris des de recursos
			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("genapp_version", Versio.VERSIO);

			prop.put("artifactid", project.projectName.toLowerCase() + "-back");
			prop.put("prefix", project.getPrefix().toUpperCase());

			prop.put("prefixLowercase", project.getPrefix().toLowerCase());

			prop.put("role_admin", "<role-name>" + project.getPrefix().toLowerCase() + "_ADMIN</role-name>");

			prop.put("role_user", "<role-name>" + project.getPrefix().toLowerCase() + "_USER</role-name>");

			prop.put("spring_version", "${spring.version}");

			prop.put("project_version", "${project.version}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("hibernate_annotations_version", "${hibernate.annotations.version}");

			prop.put("springsecurity_version", "${springsecurity.version}");

			prop.put("basedir", "${basedir}");

			prop.put("dollar", "$");

			TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
			if (fileTable == null) {
				prop.put("fitxer", "<<Borrar Aquesta Classe>>");
			} else {
				prop.put("fitxer", fileTable.getNameJava());
			}

			File dstBaseDir = new File(projectDir, backName);
			dstBaseDir.mkdirs();

			// ----- Tots fitxers de forma recursiva i substituint
			String resourceUtils = resourceBase + "/" + backName;
			{
				boolean overwrite = false;
				final String[] ignoreSubstitution = new String[] {
						// "onlycontentlayout.jsp",
						"src/main/webapp/img/", "src/main/webapp/js/", "src/main/webapp/css/",
						"src/main/webapp/WEB-INF/tld/", "src/main/webapp/WEB-INF/lib/" };
				recursiveSubstitution(dstBaseDir, resourceUtils, prop, project, overwrite, ignoreSubstitution);
			}

			if (fileTable == null) {
				// TODO Borrar els següents fitxers
				/**
				 * [back]\src\main\java\[package]\back\
				 * utils\[projectName]CommonsMultipartResolver.java
				 * [back]\src\main\java\[package]\back\
				 * utils\[projectName]MaxUploadSizeExceededException.java
				 * [back]\src\main\java\[package]\back\
				 * utils\[projectName]MaxUploadSizeExceededExceptionHandler.java
				 * 
				 * 
				 * [back]\src\main\java\[package]\back\controller\FileDownloadController.java
				 * [back]\src\main\java\[package]\back\controller\[projectName]BaseController.java
				 * [back]\src\main\java\[package]\back\controller\[projectName]FilesBaseController.java
				 * [back]\src\main\java\[package]\back\controller\[projectName]FilesFormManager.java
				 */
			}

			// (3) Afegir a la llista de mòduls a compilar
			moduls.add(backName);

			// (4) Forms
			String packageForm = backPackage + ".form";
			String packageFormBack = packageForm + ".webdb";
			{
				File backFormJavaCode = new File(baseCode, packageFormBack.replace('.', '/'));
				backFormJavaCode.mkdirs();

				List<SourceFile> forms = BackGenerator.generateForms(packageForm, packageFormBack, packages, jpaPackage,
						project);
				for (SourceFile sourceFile : forms) {
					sourceFile.saveToPath(backFormJavaCode);
				}
				List<SourceFile> filterforms = BackGenerator.generateFilterForms(packageForm, packageFormBack,
						jpaPackage, ejbPackage, packages, tables, project);
				for (SourceFile sourceFile : filterforms) {
					sourceFile.saveToPath(backFormJavaCode);
				}
			}

			// (5) Validadors Web
			String packageValidatorBack = backPackage + ".validator.webdb";
			{
				List<SourceFile> valids = BackGenerator.generateValidator(packageValidatorBack, packageFormBack,
						jpaPackage, ejbPackage, validatorPackage, packages, project);
				File backValidatorJavaCode = new File(baseCode, packageValidatorBack.replace('.', '/'));
				backValidatorJavaCode.mkdirs();
				for (SourceFile sourceFile : valids) {
					sourceFile.saveToPath(backValidatorJavaCode);
				}
			}

			// (6) Controller
			String packageControllerBase = backPackage + ".controller";
			String packageControllerBack = packageControllerBase + ".webdb";
			{
				List<SourceFile> controls = BackGenerator.generateControllers(packageControllerBase,
						packageControllerBack, packageFormBack, packageValidatorBack, jpaPackage, ejbPackage, packages,
						project, beanCodeBySqlTableName);
				File backControllerJavaCode = new File(baseCode, packageControllerBack.replace('.', '/'));
				backControllerJavaCode.mkdirs();
				for (SourceFile sourceFile : controls) {
					sourceFile.saveToPath(backControllerJavaCode);
				}
			}

			// (7) Web JSP List
			File backWebJsp = new File(dstBackDir, "src\\main\\webapp\\WEB-INF\\jsp\\webdb");
			{
				backWebJsp.mkdirs();
				List<SourceFile> list = BackWebGenerator.generateListJsp(packageControllerBack, packageFormBack,
						packageValidatorBack, jpaPackage, ejbPackage, packages, project, beanCodeBySqlTableName);
				for (SourceFile sourceFile : list) {
					sourceFile.saveToPath(backWebJsp);
				}
			}

			// (8) Web JSP Form
			{
				List<SourceFile> list = BackWebGenerator.generateFormJsp(packageControllerBack, packageFormBack,
						packageValidatorBack, jpaPackage, ejbPackage, packages, project, beanCodeBySqlTableName);
				for (SourceFile sourceFile : list) {
					sourceFile.saveToPath(backWebJsp);
				}
			}

			// (9) Webdb.jsp
			{
				File webdb_jsp = new File(dstBackDir, "src/main/webapp/WEB-INF/jsp/webdb/menu_webdb.jsp");

				String txt;
				if (webdb_jsp.exists()) {
					txt = FileUtils.readFileToString(webdb_jsp, "UTF-8");
				} else {
					txt = "<%@ page contentType=\"text/html;charset=UTF-8\" language=\"java\"%>\n"
							+ " <%@ include file=\"/WEB-INF/jsp/moduls/includes.jsp\"%>\n"
							// + " <tiles:useAttribute id=\"opcionsMenu\" name=\"opcionsMenu\" />\n"
							// + " <tiles:useAttribute id=\"logo\" name=\"logo\" />\n"
							+ " <c:set var=\"url\" value=\"${urlActual}\" />\n" + " <div>\n" + " <h5>WebDatabase</h5>\n"
							+ " <ul class=\"tree\" style=\"margin:3px; padding:0px;\">\n"
							+ " <%-- ==== GENAPP MARK START --%>\n" + " <%-- ==== GENAPP MARK END --%>\n" + " </ul>\n"
							+ " </div>\n";
				}

				int startMark = txt.indexOf("<%-- ==== GENAPP MARK START --%>");
				int endMark = txt.indexOf("<%-- ==== GENAPP MARK END --%>");

				if (startMark == -1 || endMark == -1) {
					throw new Exception("El fitxer de webdb.jsp  no conté l'start i/o l'end tag");
				}

				String links = BackWebGenerator.generateMenu(tables);

				StringBuffer str = new StringBuffer();
				str.append(txt.substring(0, startMark));
				str.append("<%-- ==== GENAPP MARK START --%>\n");
				str.append("\n");

				str.append(links);

				str.append(txt.substring(endMark));

				FileUtils.write(webdb_jsp, str.toString(), "UTF-8");

			}

			// (10) tiles.xml
			{
				File webdb_jsp = new File(dstBackDir, "src/main/webapp/WEB-INF/tiles.xml");

				String txt;
				if (webdb_jsp.exists()) {
					txt = FileUtils.readFileToString(webdb_jsp, "UTF-8");
				} else {
					txt = BackWebGenerator.getEmptyTilesXML(backPackage);
				}

				int startMark = txt.indexOf("<!-- ==== GENAPP MARK START -->");
				int endMark = txt.indexOf("<!-- ==== GENAPP MARK END -->");

				if (startMark == -1 || endMark == -1) {
					throw new Exception("El fitxer de " + webdb_jsp.getName() + " no conté l'start i/o l'end tag");
				}

				String links = BackWebGenerator.generateTile(tables);

				StringBuffer str = new StringBuffer();
				str.append(txt.substring(0, startMark));
				str.append("<!-- ==== GENAPP MARK START -->\n");
				str.append("\n");

				str.append(links);

				str.append(txt.substring(endMark));

				FileUtils.write(webdb_jsp, str.toString(), "UTF-8");

			}

			// (11) Modificable files
			// webdbmodificable/" + model+ "FormModificable.jsp
			{
				File backWebDBModificableJsp = new File(dstBackDir,
						"src\\main\\webapp\\WEB-INF\\jsp\\webdbmodificable");
				backWebDBModificableJsp.mkdirs();
				for (int i = 0; i < tables.length; i++) {

					TableInfo table = tables[i];
					if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity()) {
						continue;
					}
					final String tableJavaName = table.getNameJava();
					final String model = CodeGenUtils.getModelName(tableJavaName);
					File[] filesform = new File[] { new File(backWebDBModificableJsp, model + "FormModificable.jsp"),
							new File(backWebDBModificableJsp, model + "ListModificable.jsp") };
					for (File file : filesform) {
						if (!file.exists()) {
							new FileOutputStream(file).close(); // Touch
						}
					}

				}
			}

			// (12) Esborrar Fitxers preCalculats
			for (File file : toDelete) {
				log.info("BORRANT: " + file.getAbsolutePath());
				if (!file.delete()) {
					file.deleteOnExit();
				}
			}

		}
	}

	private static void generatePersistence(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, String appCurrentVersion, String jpaPackage)
			throws Exception, FileNotFoundException, IOException {
		{
			// (a) Afegir a la llista de mòduls a compilar
			final String persistName = "persistence";

			moduls.add(persistName);

			// (b) persistence.xml
			{
				Map<String, Object> prop = new HashMap<String, Object>();
				prop.put("dollar", "$");
				prop.put("name", project.projectName.toLowerCase());
				prop.put("package", project.getPackageName());

				prop.put("fullname", project.projectName);
				prop.put("packagePath", packagePath);
				prop.put("app_current_version", appCurrentVersion);

				prop.put("artifactid", project.projectName.toLowerCase() + "-persistence");
				prop.put("persistencefullname", project.projectName + " Persistence");

				File dstBaseDir = new File(projectDir, persistName);
				dstBaseDir.mkdirs();

				// ----- POM pare
				String resourcePersist = resourceBase + "/" + persistName;
				{
					final String pomPath = "pom.xml";
					final String pomFileStr = new String(getResource(resourcePersist + "/" + pomPath));

					File pomFileDst = new File(dstBaseDir, pomPath);
					SourceFile persist = substitution(pomPath, pomFileStr, prop);
					persist.saveToPath(pomFileDst.getParentFile());
				}

				// ------ Common data

				String resourcePersistType = resourcePersist + "/" + "type";

				final String pomPath = "pom.xml";
				final String pomFileStr = new String(getResource(resourcePersistType + "/" + pomPath));

				final String persistencePath = "src/main/resources/META-INF/persistence.xml";
				final String persistenceFileStr = new String(getResource(resourcePersistType + "/" + persistencePath));

				List<String> llistaJPABeans = new ArrayList<String>();

				for (int i = 0; i < tables.length; i++) {
					if (!tables[i].isGenerate()) {
						continue;
					}
					llistaJPABeans.add(jpaPackage + "." + tables[i].nameJava + "JPA");
				}
				prop.put("classes", llistaJPABeans);

				// ----- JTA Persistence
				{
					File dstPersistJTADir = new File(dstBaseDir, "jta");
					dstPersistJTADir.mkdirs();

					prop.put("genapp_version", Versio.VERSIO);

					prop.put("persistencename", project.projectName.toLowerCase() + "DB");
					prop.put("transactiontype", "JTA");
					prop.put("jtadatasource",
							"<jta-data-source>java:/" + project.getPackageName() + ".db</jta-data-source>");

					prop.put("artifactid", project.projectName.toLowerCase() + "-persistence-jta");
					prop.put("persistencefullname", project.projectName + " Persistence JTA");

					{ // --- POM
						File pomFileDst = new File(dstPersistJTADir, pomPath);
						SourceFile persist = substitution(pomPath, pomFileStr, prop);
						persist.saveToPath(pomFileDst.getParentFile());
					}

					{ // --- Persistence.xml
						File persistenceFileDst = new File(dstPersistJTADir, persistencePath);
						persistenceFileDst.getParentFile().mkdirs();
						SourceFile persist = substitution(persistenceFileDst.getName(), persistenceFileStr, prop);
						persist.saveToPath(persistenceFileDst.getParentFile());
					}

				}

				// Standalone Persistence
				{
					File dstPersistResLocalDir = new File(dstBaseDir, "reslocal");
					dstPersistResLocalDir.mkdirs();

					prop.put("persistencename", project.projectName.toLowerCase() + "DBStandalone");
					prop.put("transactiontype", "RESOURCE_LOCAL");
					prop.put("jtadatasource", " ");

					prop.put("artifactid", project.projectName.toLowerCase() + "-persistence-resourcelocal");
					prop.put("persistencefullname", project.projectName + " Persistence Resource Local");

					{ // --- POM
						File pomFileDst = new File(dstPersistResLocalDir, pomPath);
						SourceFile persist = substitution(pomFileDst.getName(), pomFileStr, prop);
						persist.saveToPath(pomFileDst.getParentFile());
					}
					{ // --- Persistence.xml
						File persistenceFileDst = new File(dstPersistResLocalDir, persistencePath);
						persistenceFileDst.getParentFile().mkdirs();
						SourceFile persist = substitution(persistenceFileDst.getName(), persistenceFileStr, prop);
						persist.saveToPath(persistenceFileDst.getParentFile());
					}
				}

			}

		}
	}

	private static void generateUtils(Project project, File projectDir, String packagePath, List<String> moduls,
			String resourceBase, String appCurrentVersion) throws Exception {
		{
			// (a) Afegir a la llista de mòduls a compilar
			final String utilsName = "utils";

			moduls.add(utilsName);

			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("artifactid", project.projectName.toLowerCase() + "-utils");
			prop.put("prefix", project.getPrefix().toUpperCase());

			File dstBaseDir = new File(projectDir, utilsName);

			boolean utilsExists = new File(dstBaseDir, "src/main/java/es/caib/portafib/utils").exists();
			File constantsFile = new File(dstBaseDir, "src/main/java/es/caib/portafib/utils/Constants.java");
			boolean constantsExists = constantsFile.exists();

			// NO MOURE D´AQUI !!!!
			dstBaseDir.mkdirs();

			// ----- Tots fitxers de forma recursiva i substituint
			String resourceUtils = resourceBase + "/" + utilsName;
			{
				boolean overwrite = false;
				recursiveSubstitution(dstBaseDir, resourceUtils, prop, project, overwrite);
			}

			if (utilsExists && !constantsExists) {
				constantsFile.delete();
			}

		}
	}

	private static void generateCommonCode(Project project, File projectDir, String packagePath, List<String> moduls,
			String resourceBase, String appCurrentVersion) throws Exception, FileNotFoundException, IOException {
		{
			// (0) Afegir a la llista de mòduls a compilar
			final String sName = "scripts";
			moduls.add(sName);

		}

		log.info(" --------  MODULS ----------");
		for (String m : moduls) {
			log.info(" MODUL[" + m + "]");
		}

		// (1) Copiar estructura base si es necesaria
		{
			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("package", project.getPackageName());
			prop.put("genapp_version", Versio.VERSIO);
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package.path", packagePath);
			prop.put("fullname", project.projectName); // .toLowerCase()
			prop.put("moduls", moduls);
			prop.put("version_txt_template_file", "${version_txt_template_file}");
			prop.put("version_txt_file", "${version_txt_file}");
			prop.put("project_version", "${project.version}");
			prop.put("project_basedir", "${project.basedir}");
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("prefix", project.getPrefix().toUpperCase());

			prop.put("prefixLowercase", project.getPrefix().toLowerCase());

			prop.put("basedir", "${basedir}");
			prop.put("hibernate", "${hibernate}");
			prop.put("hibernate_dialect", "${hibernate.dialect}");

			String resourceBaseDirs = resourceBase + "/" + "baseDirs";
			recursiveSubstitution(projectDir, resourceBaseDirs, prop, project, false);
		}

		// (2) Copiar fitxers que no necessiten substitucions
//    {
//      String resourceBaseDirsBin = resourceBase + "/" +  "baseDirsBin";
//      copyResourcesToDir(resourceBaseDirsBin, projectDir, true);
//    }

		{
			// (3) generar fitxer de noms curts

			StringBuffer shortnames = new StringBuffer();
			shortnames.append("_projectName_=" + project.projectName.toLowerCase() + "\n");
			shortnames.append("_projectPrefix_=" + project.getPrefix().toLowerCase() + "\n");

			TableInfo[] taules = project.getTables();

			for (int i = 0; i < taules.length; i++) {
				shortnames.append(taules[i].name + "=" + taules[i].shortName + "\n");

				MultipleUnique[] uniques = taules[i].getMultipleUniques();
				if (uniques != null) {
					for (int j = 0; j < uniques.length; j++) {
						shortnames
								.append(taules[i].name + "_UNIQUE_"
										+ Arrays.toString(uniques[j].getSqlColumns()).replace('[', '(')
												.replace(']', ')').replace(" ", "\\ ")
										+ "=" + uniques[j].getName() + "\n");
					}
				}

				FieldInfo[] fields = taules[i].getFields();
				for (FieldInfo fieldInfo : fields) {
					if (fieldInfo.isPrimaryKey()) {
						shortnames.append(taules[i].name + "_PK=" + fieldInfo.getPK() + "\n");
					}
					if (fieldInfo.isUniqueKey()) {
						shortnames.append(
								taules[i].name + "_" + fieldInfo.sqlName + "_uk=" + fieldInfo.getUnique() + "\n");
					}
				}
			}

			SourceFile ver = new SourceFile("shortNames.properties", shortnames.toString());
			File sqlGen = new File(projectDir, "scripts/sqlgenerator/");
			sqlGen.mkdirs();
			ver.saveToPath(sqlGen);

		}
	}

	private static void generateWS(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, String appCurrentVersion) throws Exception {
		{
			moduls.add("ws");

			// (1) Variables
			final String wsName = "ws";

			// File dstWsDir = new File(projectDir, wsName);
			// File baseCode = new File(dstWsDir, "src/main/java");
			// String backPackage = project.getPackageName() + "." + wsName;
			// File backJavaCode = new File(baseCode, backPackage.replace('.', '/'));

			// (2) Directoris i fitxers a borrar
			File dstBaseDir = new File(projectDir, wsName);
			dstBaseDir.mkdirs();
			final String name = project.projectName.toLowerCase();
			final String fullname = project.projectName;

			final String api_main = name + "_api/src/main/java/" + packagePath + "/ws/api/v1/";
			final String api_test = name + "_api/src/main/java/" + packagePath + "/ws/v1/test/";

			final String srv_main = name + "_server/src/main/java/" + packagePath + "/ws/v1/impl/";
			final String[] fitxersABorrar = new String[] { api_test + fullname + "HelloWorldTest.java",
					api_main + fullname + "HelloWorldWithSecurityWs.java",
					api_main + fullname + "HelloWorldWithSecurityWsService.java",
					api_main + fullname + "HelloWorldWs.java", api_main + fullname + "HelloWorldWsService.java",
					srv_main + fullname + "HelloWorldWithSecurityWsImpl.java" };

			List<File> toDelete = new ArrayList<File>();

			System.err.println(dstBaseDir.getAbsolutePath());

			for (int i = 0; i < fitxersABorrar.length; i++) {

				System.err.println(fitxersABorrar[i]);

				File exampleCode = new File(dstBaseDir, fitxersABorrar[i]);

				System.err.println("X_" + exampleCode.exists() + "_X " + exampleCode.getAbsolutePath());

				if (!exampleCode.exists() && exampleCode.getParentFile().exists()) {
					// OK: S'ha de borrar
					toDelete.add(exampleCode);
				}
			}

			// (2) Còpia inicial: Copiar estructura directoris des de recursos
			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			System.out.println("project.getPackageName() = " + project.getPackageName());

			String[] parts = project.getPackageName().split("\\.");
			StringBuffer packageInverse = new StringBuffer();
			for (int i = parts.length - 1; i >= 0; i--) {
				if (packageInverse.length() != 0) {
					packageInverse.append('.');
				}
				packageInverse.append(parts[i]);
			}

			System.out.println("packageInverse.toString() = " + packageInverse.toString());
			// Thread.sleep(5000);

			prop.put("packageInverse", packageInverse.toString());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("prefix", project.getPrefix().toUpperCase());
			prop.put("dollar", "$");

			prop.put("genapp_version", Versio.VERSIO);

			prop.put("spring_version", "${spring.version}");
			prop.put("cxf_version", "${cxf.version}");

			prop.put("project_version", "${project.version}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("hibernate_annotations_version", "${hibernate.annotations.version}");

			prop.put("springsecurity_version", "${springsecurity.version}");

			prop.put("basedir", "${basedir}");

			TableInfo fileTable = CodeGenUtils.getSqlTableFile(tables);
			if (fileTable == null) {
				prop.put("fitxer", "<<Borrar Aquesta Classe>>");
			} else {
				prop.put("fitxer", fileTable.getNameJava());
			}

			// ----- Tots fitxers de forma recursiva i substituint
			String resourceUtils = resourceBase + "/" + wsName;

			if (!new File(dstBaseDir, project.getProjectName().toLowerCase() + "_api").exists()
					&& !new File(dstBaseDir, project.getProjectName().toLowerCase() + "_server").exists()) {
				boolean overwrite = false;
				recursiveSubstitution(dstBaseDir, resourceUtils, prop, project, overwrite);
			}

			for (File file : toDelete) {
				log.info("BORRANT: " + file.getAbsolutePath());
				if (!file.delete()) {
					file.deleteOnExit();
				}
			}

			if (fileTable == null) {
				// TODO Borrar els següents fitxers associats a files
				/**
				
				*/
			}

		}
	}

	private static void generateLogic(Project project, File projectDir, String packagePath, List<String> moduls,
			String resourceBase, String appCurrentVersion) throws Exception {
		{

			// (1) Afegir a la llista de mòduls a compilar
			moduls.add("logic");

			// (2) Borrar Classes de prova
			final String logicName = "logic";
			File dstBaseDir = new File(projectDir, logicName);
			dstBaseDir.mkdirs();

			File exampleCode = new File(dstBaseDir, "src/main/java/" + packagePath + "/logic/SampleLogicaEJB.java");

			if (!exampleCode.exists() && exampleCode.getParentFile().exists()) {
				// OK: S'ha de borrar
			} else {
				// No s'ha de fer res.
				exampleCode = null;
			}

			// (3) Afegir a la llista de mòduls a compilar

			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);
			prop.put("app_current_version", appCurrentVersion);
			prop.put("name_uppercase", project.projectName.toUpperCase());

			prop.put("artifactid", project.projectName.toLowerCase() + "-logic");

			prop.put("project_version", "${project.version}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("basedir", "${basedir}");

			prop.put("prefix", project.getPrefix().toUpperCase());

			// ----- Tots fitxers de forma recursiva i substituint
			String resourceUtils = resourceBase + "/" + logicName;
			{
				boolean overwrite = false;
				recursiveSubstitution(dstBaseDir, resourceUtils, prop, project, overwrite);
			}

			// (3) Borrar codi innecesari
			if (exampleCode != null) {
				if (!exampleCode.delete()) {
					exampleCode.deleteOnExit();
				}
				File exampleCode2 = new File(exampleCode.getParentFile(), "SampleLogicaLocal.java");
				if (!exampleCode2.delete()) {
					exampleCode2.deleteOnExit();
				}
			}
		}
	}

	private static String generateEJB(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, Map<String, BeanCode> beanCodeBySqlTableName,
			String appCurrentVersion, BasicPackages packages, String jpaPackage)
			throws Exception, FileNotFoundException, IOException {
		String ejbPackage;
		{
			final String ejbName = "ejb";
			ejbPackage = project.getPackageName() + "." + ejbName;

			File ejbDir = new File(projectDir, ejbName);
			// XYZ ZZZ File pomFile = new File(ejbDir, "pom.xml");

			// (a) Copiar estructura directoris
			String resourcesEJB = resourceBase + "/" + ejbName;
			// copyResourcesToDir(resourcesEJB, ejbDir, true);

			// (b) Adaptar pom.xml

			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);

			// TODO Parxe !!!!
			prop.put("version_template_file", "${version_template_file}");
			prop.put("version_file", "${version_file}");
			prop.put("project_version", "${project.version}");
			prop.put("project_basedir", "${project.basedir}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("app_current_version", appCurrentVersion);

			prop.put("package_ejb", ejbPackage);

			/*
			 * SourceFile pom = substitution(pomFile, prop); pom.saveToPath(ejbDir);
			 */

			boolean overwrite = false;
			recursiveSubstitution(ejbDir, resourcesEJB, prop, project, overwrite);

			// (c) Crear Local i EJB
			File ejbSrcDir = new File(ejbDir, "src/main/java/" + ejbPackage.replace('.', '/'));
			ejbSrcDir.mkdirs();

			// List<String> llistaEJBBeans = new ArrayList<String>();
			{
				for (int i = 0; i < tables.length; i++) {
					if (!tables[i].isGenerate() || tables[i].isTranslationMapEntity()) {
						continue;
					}
					BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);
					{
						log.info("Generating EJB Code for " + " " + tables[i].getNameJava() + "Local ");
						/*
						 * XYZ ZZZ String versioNumber;
						 * 
						 * File versio = new File(projectDir, "versio.txt"); if (versio.exists()) {
						 * versioNumber = FileUtils.readFileToString(versio, "UTF-8").trim(); } else {
						 * log.error("No puc llegir versió de versio.txt"); versioNumber = "0.0.1"; }
						 */

						SourceFile manager = DaoEJBGenerator.generateCodeForManagerLocal(project,
								tables[i].getNameJava(), jpaPackage, ejbPackage, packages, beanGenCode);
						// File file =
						manager.saveToPath(ejbSrcDir);
						// log.info(" + " + file.getAbsolutePath());
					}
					{
						log.info("Generating EJB Code for " + " " + tables[i].getNameJava() + "EJB ");
						SourceFile manager = DaoEJBGenerator.generateCodeForManagerEJB(project, tables[i].getNameJava(),
								jpaPackage, ejbPackage, packages, beanGenCode);
						// File file =
						manager.saveToPath(ejbSrcDir);
						// log.info(" + " + file.getAbsolutePath());
					}

				}
			}

			// (d) Traduccions
			// (3) Missatges
			DaoEJBGenerator.generaMissatges(project.projectName, project.getTables(), ejbDir, project.getLanguages());

			// (d) Afegir a la llista de mòduls a compilar
			moduls.add(ejbName);

		}
		return ejbPackage;
	}

	private static String generateJPA(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, Map<String, BeanCode> beanCodeBySqlTableName,
			String appCurrentVersion, BasicPackages packages, File fieldSrcDir, String jpaPackage, final String jpaName)
			throws Exception, FileNotFoundException, IOException {
		String validatorPackage;
		{

			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);

			// TODO Parxe !!!!
			prop.put("version_template_file", "${version_template_file}");
			prop.put("version_file", "${version_file}");
			prop.put("project_version", "${project.version}");
			prop.put("project_basedir", "${project.basedir}");
			prop.put("hibernate_version", "${hibernate.version}");
			prop.put("app_current_version", appCurrentVersion);

			prop.put("genapp_version", Versio.VERSIO);

			prop.put("package_jpa", jpaPackage);

			File jpaDir = new File(projectDir, jpaName);
			// File pomFile = new File(jpaDir, "pom.xml");

			// (a) Copiar estructura directoris
			final String jpaResource = resourceBase + "/" + jpaName;

			boolean overwrite = false;
			recursiveSubstitution(jpaDir, jpaResource, prop, project, overwrite);

			File jpaBaseSrcDir = new File(jpaDir, "src/main/java/");

			// (b) Crear fitxers: JPAManagers i JPA

			File jpaSrcDir = new File(jpaBaseSrcDir, jpaPackage.replace('.', '/'));
			jpaSrcDir.mkdirs();

			final String hibernatePackage = project.getPackageName() + ".hibernate";

			for (int i = 0; i < tables.length; i++) {
				if (!tables[i].isGenerate()) {
					continue;
				}

				BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);

				// Annotations Bean;
				{
					DaoJPAGenerator.foreignFields.clear();
					SourceFile bean = DaoJPAGenerator.generateJPABeanCode(project, tables[i], packages.entityPackage,
							jpaPackage, hibernatePackage, beanGenCode);
					// File file =
					bean.saveToPath(jpaSrcDir);
					// log.info(" + " + file.getAbsolutePath());
				}

				// Hibernate JPA MAnager;
				{
					log.info("Generating Code for " + " " + tables[i].getNameJava() + " Manager");
					SourceFile manager = DaoJPAGenerator.generateCodeForManager(tables[i], packages, jpaPackage,
							project, beanGenCode);
					if (manager != null) {
						manager.saveToPath(jpaSrcDir);
					}
					// log.info(" + " + file.getAbsolutePath());
				}

				// Hibernate Iface JPA Manager;
				{
					log.info("Generating Code for  " + tables[i].getNameJava() + " IFACE Manager");
					SourceFile manager = DaoJPAGenerator.generateCodeForIFaceManager(tables[i], packages, jpaPackage,
							project, beanGenCode);
					if (manager != null) {
						manager.saveToPath(jpaSrcDir);
					}
					// log.info(" + " + file.getAbsolutePath());
				}

				// Fitxers QueryPath
				{
					SourceFile queryPath = DaoJPAGenerator.generateQueryPath(packages, tables[i], tables[i].getFields(),
							beanGenCode);
					if (queryPath != null) {
						queryPath.saveToPath(fieldSrcDir);
					}
				}

			}

			// (c) Generar Validadors JPA
			{
				validatorPackage = jpaPackage + ".validator";

				List<SourceFile> valids = JPAValidatorGenerator.generateValidator(validatorPackage, jpaPackage,
						packages, project);
				File validatorJavaCode = new File(jpaBaseSrcDir, validatorPackage.replace('.', '/'));
				validatorJavaCode.mkdirs();
				for (SourceFile sourceFile : valids) {
					sourceFile.saveToPath(validatorJavaCode);
				}

			}

			// (d) Afegir a la llista de mòduls a compilar
			moduls.add(jpaName);

			// (e) DaoManagers
			{
				SourceFile daoManager;
				daoManager = DaoJPAGenerator.generateJPADaoManager(jpaPackage, packages, project);
				daoManager.saveToPath(jpaSrcDir);
			}

			// (f) HibernateUtil
			// XYZ ZZZ
			{
				File hibernateSrcDir = new File(jpaDir, "src/main/java/" + hibernatePackage.replace('.', '/'));
				hibernateSrcDir.mkdirs();
				String hibernateName = "HibernateUtil";
				String hibernateFileName = "HibernateFileUtil";
				// if (!new File(hibernateSrcDir, hibernateName).exists())
				{
					SourceFile[] files;
					files = DaoJPAGenerator.generateHibernateUtils(hibernatePackage, hibernateName, hibernateFileName);
					for (int i = 0; i < files.length; i++) {
						files[i].saveToPath(hibernateSrcDir);
					}

				}

			}

		}
		return validatorPackage;
	}

	private static File generateModel(Project project, File projectDir, String packagePath, List<String> moduls,
			TableInfo[] tables, String resourceBase, Map<String, BeanCode> beanCodeBySqlTableName,
			String appCurrentVersion, BasicPackages packages) throws Exception, FileNotFoundException, IOException {
		File fieldSrcDir;
		{
			final String modelName = "model";
			File modelDir = new File(projectDir, modelName);
			File pomFile = new File(modelDir, "pom.xml");

			// (a) Copiar estructura directoris
			String resourceModel = resourceBase + "/" + modelName;
			copyResourcesToDir(resourceModel, modelDir, true);

			// (b) Adaptar pom.xml
			Map<String, Object> prop = new HashMap<String, Object>();
			prop.put("name", project.projectName.toLowerCase());
			prop.put("package", project.getPackageName());

			prop.put("fullname", project.projectName);
			prop.put("packagePath", packagePath);

			// TODO Parxe !!!!
			prop.put("version_template_file", "${version_template_file}");
			prop.put("version_file", "${version_file}");
			prop.put("project_version", "${project.version}");
			prop.put("project_basedir", "${project.basedir}");
			prop.put("app_current_version", appCurrentVersion);

			prop.put("genapp_version", Versio.VERSIO);

			SourceFile pom = substitution(pomFile, prop);
			pom.saveToPath(modelDir);

			// (c) Crear fitxers de interficies
			File baseDir = new File(modelDir, "src/main/java/");

			File entitySrcDir = new File(baseDir, packages.entityPackage.replace('.', '/'));
			entitySrcDir.mkdirs();
			File daoSrcDir = new File(baseDir, packages.daoPackage.replace('.', '/'));
			daoSrcDir.mkdirs();
			fieldSrcDir = new File(baseDir, packages.fieldsPackage.replace('.', '/'));
			fieldSrcDir.mkdirs();
			File beanSrcDir = new File(baseDir, packages.beanPackage.replace('.', '/'));
			beanSrcDir.mkdirs();
			for (int i = 0; i < tables.length; i++) {
				if (!tables[i].isGenerate()) {
					continue;
				}
				// interface base de Entity;
				BeanCode beanGenCode = beanCodeBySqlTableName.get(tables[i].name);
				{
					log.info(" = Generating Interface base for " + tables[i].name);
					SourceFile interfaceBase = ModelGenerator.generateInterfaceBase(project, packages.entityPackage,
							tables[i], beanGenCode);
					// File file =
					interfaceBase.saveToPath(entitySrcDir);
					// log.info(" + " + file.getAbsolutePath());
				}

				// TODO (e) Generar Interficies dels DAO i ClauPrimaries multiples
				{
					SourceFile dao = ModelGenerator.generateInterfacesManager(tables[i], packages,
							tables[i].getFields(), beanGenCode);
					if (dao != null) {
						dao.saveToPath(daoSrcDir);
						if (beanGenCode.daoCommonCode.pkClass.endsWith("PK")) {
							SourceFile pk = ModelGenerator.generatePKClass(tables[i].getNameJava(), packages,
									beanGenCode);
							pk.saveToPath(daoSrcDir);
						}
					}

				}

				// Llista de camps Fields per entity;
				{
					SourceFile beanFields = ModelGenerator.generateTableFields(tables[i].getName(), packages,
							tables[i].getNameJava(), tables[i].getFields(), beanGenCode);
					// File file =
					beanFields.saveToPath(fieldSrcDir);
					// log.info(" + " + file.getAbsolutePath());
				}

				// bean;
				{
					SourceFile bean = ModelGenerator.generateBeanCode(project, tables[i], packages, beanGenCode);
					if (bean != null) {
						bean.saveToPath(beanSrcDir);
					}
					// log.info(" + " + file.getAbsolutePath());
				}

			}

			// (d) Generate database manager
			File modelSrcDir = new File(baseDir, packages.modelPackage.replace('.', '/'));
			modelSrcDir.mkdirs();

			// Falta generar I<Project>Managers
			ModelGenerator.generateInterfacesManager(packages, project).saveToPath(modelSrcDir);

			// Falta generar <Project>DaoManager
			ModelGenerator.generateManagerOfDaoManagers(packages.modelPackage, project.projectName)
					.saveToPath(modelSrcDir);

			// (e) Afegir a la llista de moduls a compilar
			moduls.add(modelName);
		}
		return fieldSrcDir;
	}

	public static void copyFile(File src, File dst) throws Exception {
		InputStream in = new FileInputStream(src);
		OutputStream out = new FileOutputStream(dst);

		byte[] buf = new byte[1024];
		int len;
		while ((len = in.read(buf)) > 0) {
			out.write(buf, 0, len);
		}
		in.close();
		out.close();
	}

	public static Map<String, File> listingJars() throws Exception {

		Map<String, File> jars = new HashMap<String, File>();

		// get the system classpath
		String classpath = System.getProperty("java.class.path", "");

		if (classpath.equals("")) {
			System.err.println("error: classpath is not set");
			return jars;
		}

		log.info("system classpath = " + classpath);

		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);

		int x = 0;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			File classpathElement = new File(token);

			if (!classpathElement.isDirectory()) {
				log.info((++x) + ".- JAR: " + classpathElement);
				jars.put(classpathElement.getName(), classpathElement);
			}
		}

		return jars;
	}

	static class BeanFieldCode {
		String prototipusGet;
		String prototipusSet;
		String contentGet;
		String contentSet;
	}

	private static BeanCode generateBeanPartialCode(String iname, FieldInfo[] fields) {
		BeanCode beanGenCode;
		{
			beanGenCode = new BeanCode();

			StringBuffer attributesNonPK = new StringBuffer();
			StringBuffer attributesPK = new StringBuffer();

			/** Variable de uso */
			Class<?> typeClass;
			String type;
			String name;
			boolean isPK;

			Map<FieldInfo, BeanFieldCode> codeByField = new HashMap<FieldInfo, BeanFieldCode>();

			List<FieldInfo> PKFields = new ArrayList<FieldInfo>();

			for (int r = 0; r < fields.length; r++) {
				FieldInfo field = fields[r];
				typeClass = field.getJavaType();
				type = typeClass.getName();
				name = field.javaName;
				isPK = field.isPrimaryKey();

				// Atributos
				if (isPK) {
					PKFields.add(field);
					attributesPK.append("\t" + type + " " + name + ";" + "// PK\n");
				} else {
					attributesNonPK.append("\t" + type + " " + name + ";\n");
				}

				// Metodes GET & SET
				BeanFieldCode bc = new BeanFieldCode();
				char upper = Character.toUpperCase(name.charAt(0));

				bc.prototipusGet = "\tpublic " + type + " " + CodeGenUtils.get(field);

				bc.prototipusSet = "\tpublic void set" + upper + name.substring(1, name.length()) + "(" + type + " _"
						+ name + "_)";
				bc.contentGet = " {\n\t\treturn(" + name + ");\n\t};\n";
				bc.contentSet = " {\n\t\tthis." + name + " = _" + name + "_;\n\t};\n\n";

				codeByField.put(field, bc);
			}

			beanGenCode.attributesNonPK = attributesNonPK.toString();
			beanGenCode.attributesPK = attributesPK.toString();
			beanGenCode.codeByField = codeByField;
			beanGenCode.pkFields = PKFields;
		}

		beanGenCode.constructors = getBeanConstructors(fields);

		beanGenCode.daoCommonCode = new DaoCommonCode(iname, fields);

		return beanGenCode;
	}

	private static Constructors getBeanConstructors(FieldInfo[] fields) {

		String param, assign;

		StringBuffer paramsAll = new StringBuffer();
		StringBuffer bodyAll = new StringBuffer();

		StringBuffer paramsNotAuto = new StringBuffer();
		StringBuffer bodyNotAuto = new StringBuffer();

		StringBuffer paramsNotNull = new StringBuffer();
		StringBuffer bodyNotNull = new StringBuffer();

		int countFieldsNotNull = 0;
		int countFieldsNotAuto = 0;
		for (int i = 0; i < fields.length; i++) {

			param = fields[i].getJavaType().getName() + " " + fields[i].javaName;
			assign = "    this." + fields[i].javaName + "=" + fields[i].javaName + ";\n";

			if (bodyAll.length() != 0) {
				paramsAll.append(" , ");
			}
			paramsAll.append(param);
			bodyAll.append(assign);

			if (!fields[i].isAutoIncrement) {

				if (bodyNotAuto.length() != 0) {
					paramsNotAuto.append(" , ");
				}
				paramsNotAuto.append(param);
				bodyNotAuto.append(assign);

				countFieldsNotAuto++;
			}

			if (fields[i].isNotNullable) {
				if (paramsNotNull.length() != 0) {
					paramsNotNull.append(" , ");
				}
				paramsNotNull.append(param);
				bodyNotNull.append(assign);
				countFieldsNotNull++;
			}
		}

		Constructors cons = new Constructors();

		cons.tots = new BeanConstructor();
		cons.tots.prototype = paramsAll.toString();
		cons.tots.content = bodyAll.toString();
		cons.tots.description = "Constructor amb tots els camps ";

		if (countFieldsNotAuto != fields.length && countFieldsNotAuto != 0) {
			cons.noautoincrementals = new BeanConstructor();
			cons.noautoincrementals.prototype = paramsNotAuto.toString();
			cons.noautoincrementals.content = bodyNotAuto.toString();
			cons.noautoincrementals.description = "Constructor sense valors autoincrementals";
		}

		// Constructor amb els camps NOT NULL
		if (countFieldsNotNull != fields.length && countFieldsNotNull != countFieldsNotAuto
				&& countFieldsNotNull != 0) {
			cons.notnulls = new BeanConstructor();
			cons.notnulls.prototype = paramsNotNull.toString();
			cons.notnulls.content = bodyNotNull.toString();
			cons.notnulls.description = "Constructor dels valors Not Null";
		}

		return cons;
	}

	public static String generateBeanConstructors(TableInfo tableInfo, String codeBeanFileName, Constructors cons) {
		StringBuffer beanCode = new StringBuffer();

		// Constructor Buit
		beanCode.append("  /** Constructor Buit */\n");
		beanCode.append("  public " + codeBeanFileName + "() {\n  }\n\n");

		if (tableInfo.isTranslationMapEntity()) {

			return beanCode.toString();
		}

		// Constructor amb tots els camps
		beanCode.append("  /** " + cons.tots.description + " */\n");
		beanCode.append("  public " + codeBeanFileName + "(");
		beanCode.append(cons.tots.prototype);
		beanCode.append(") {\n" + cons.tots.content + "}\n");

		// Constructor sense camps AUTOIncement
		if (cons.noautoincrementals != null) {
			beanCode.append("  /** " + cons.noautoincrementals.description + " */\n");
			beanCode.append("  public " + codeBeanFileName + "(");
			beanCode.append(cons.noautoincrementals.prototype);
			beanCode.append(") {\n" + cons.noautoincrementals.content + "}\n");
		}

		// Constructor amb els camps NOT NULL
		if (cons.notnulls != null) {
			beanCode.append("  /** " + cons.notnulls.description + " */\n");
			beanCode.append("  public " + codeBeanFileName + "(");
			beanCode.append(cons.notnulls.prototype);
			beanCode.append(") {\n" + cons.notnulls.content + "}\n");
		}

		return beanCode.toString();
	}

	public static class DaoCommonCode {
		final StringBuffer createParams = new StringBuffer();
		final StringBuffer createParamsNotNull = new StringBuffer();
		final StringBuffer createVariableNames = new StringBuffer();
		final StringBuffer createVariableNamesNotNull = new StringBuffer();

		final StringBuffer createVariableNamesWithoutPK = new StringBuffer();
		final StringBuffer createVariableNamesNotNullWithoutPK = new StringBuffer();

		// TODO I si hi ha més d'un valor d'autoincrement !!!!!!
		String existAutoIncrementKey = null;

		final StringBuffer findParams = new StringBuffer();
		final StringBuffer findParamsCall = new StringBuffer();

		public String pkClass = null;

		public DaoCommonCode(String iname, FieldInfo[] fields) {

			log.info("DAO de " + iname);

			String type;
			String name;
			Class<?> typeClass;

			boolean isPK; // , isNotNull;
			for (int r = 0; r < fields.length; r++) {
				FieldInfo field = fields[r];
				isPK = field.isPrimaryKey();
				// isNotNull = field.isNotNullable;
				name = field.javaName;
				typeClass = field.getJavaType();
				type = typeClass.getName();

				if (!field.isAutoIncrement) {
					createParamsNotNull.append(", " + type + " _" + name + "_");
				}
				createVariableNamesNotNull.append(", _" + name + "_");
				createVariableNamesNotNullWithoutPK.append(", _" + name + "_");

				if (isPK) {
					findParams.append("," + type + " _" + name + "_");
					pkClass = typeClass.getSimpleName();

					findParamsCall.append(",_" + name + "_");
				} else {
					if (field.getJavaType().isPrimitive()) {
						if (field.getJavaType() == Boolean.TYPE) {
							createVariableNamesNotNull.append(", false");
							createVariableNamesNotNullWithoutPK.append(", false");
						} else {
							createVariableNamesNotNull.append(", 0");
							createVariableNamesNotNullWithoutPK.append(", 0");
						}
					} else {
						createVariableNamesNotNull.append(", null");
						createVariableNamesNotNullWithoutPK.append(", null");
					}
				}

				// Parametros de create
				if (field.isAutoIncrement) {
					existAutoIncrementKey = name;
					createVariableNames.append(",_" + existAutoIncrementKey + "_");
					//
				} else {
					// createDBNames.append("," + db_name);
					createParams.append(", " + type + " _" + name + "_");
					createVariableNames.append(", _" + name + "_");
					createVariableNamesWithoutPK.append(",_" + name + "_");
				}

			}

			if (findParams.length() > 0) {
				findParams.deleteCharAt(0);
			}
			if (findParamsCall.length() > 0) {
				findParamsCall.deleteCharAt(0);
			}

			if (findParamsCall.toString().indexOf(',') != -1) {
				// Dues o més claus privades
				// String tmp = findParamsCall.toString();
				// findParamsCall.delete(0, findParamsCall.length());
				// findParamsCall.append("new " + pkName + "(" + tmp + ")");
				pkClass = iname + "PK";
			} else {
				// log.info("pkClass == " + pkClass);
				char c = pkClass.charAt(0);
				if (Character.isLowerCase(c)) {
					if (pkClass.equals("int")) {
						pkClass = "Integer";
					} else {
						pkClass = Character.toUpperCase(c) + pkClass.substring(1);
					}
				}
			}

			if (pkClass.equals("BigDecimal") || pkClass.equals("BigInteger")) {
				pkClass = "java.math." + pkClass;
			}

			createVariableNamesNotNull.deleteCharAt(0);
			createVariableNamesNotNullWithoutPK.deleteCharAt(0);
			if (createParams.length() > 0) {
				createParams.deleteCharAt(0);
			}
			createVariableNames.deleteCharAt(0);
			if (createVariableNamesWithoutPK.length() > 0) {
				createVariableNamesWithoutPK.deleteCharAt(0);
			}
			if (createParamsNotNull.length() > 0) {
				createParamsNotNull.deleteCharAt(0);
			}

		}
	}

}
