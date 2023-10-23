
import javax.swing.*
import java.io.*

println ""
println " -------   POST GENERATE GROOVY -------"
println ""
def artifactId = request.getArtifactId()
println " + artifactId: " + artifactId
println " + groupId: " + request.getGroupId()
println " + version: " + request.getVersion()

Properties properties = request.properties
println " + package: " + properties.get("package")


def file = new File('./rungenapp/genapp_root.bat')
def dest = new File(file.getParentFile().getParentFile(), 'genapp.bat')
file.renameTo(dest);


def prompt = {

    if (java.awt.GraphicsEnvironment.isHeadless()) {
            // non gui mode
            println ""
            println "/-------------------------------CA------------------------------------------\\"
            println "|  Ara per posar en marxa GenApp nomes ha d'executar el programa genapp.bat |"
            println "\\---------------------------------------------------------------------------/"
            println ""
            println "/-------------------------------ES------------------------------------------\\"
            println "|   Ahora para arrancar GenApp solo debe ejecutar el programa genapp.bat    |"
            println "\\---------------------------------------------------------------------------/"
            println ""
            println "/-------------------------------EN------------------------------------------\\"
            println "|     Now to launch GenApp you just need to run the genapp.bat program      |"
            println "\\---------------------------------------------------------------------------/"
            println ""
    } else {
            // gui mode
            JFrame jframe = new JFrame()
            String answer = JOptionPane.showMessageDialog(jframe,
               "CA: Ara cada vegada que vulgui posar en marxa GenApp només ha d'executar el programa genapp.bat\n" 
               + "ES: Ahora para arrancar GenApp solo debe ejecutar el programa genapp.bat\n"
               + "EN: Now to launch GenApp you just need to run the genapp.bat program")
            jframe.dispose()
    }
}

prompt()

