
import javax.swing.*

println ""
println " -------   POST GENERATE GROOVY -------"
println ""
def artifactId = request.getArtifactId()
println " + artifactId: " + artifactId
println " + groupId: " + request.getGroupId()
println " + version: " + request.getVersion()

Properties properties = request.properties
println " + package: " + properties.get("package")

def prompt = {
  JFrame jframe = new JFrame()
  String answer = JOptionPane.showMessageDialog(jframe, "Ara cada vegada que vulgui posar en marxa GenApp només ha d'executar el programa genapp.bat")
  jframe.dispose()
  answer
}

def first = prompt("Enter a number")
