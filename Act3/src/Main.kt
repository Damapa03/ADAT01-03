import org.w3c.dom.*
import java.io.BufferedReader
import java.nio.file.Files
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Result
import javax.xml.transform.Source
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.io.path.createFile
import kotlin.io.path.exists

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    // 1. Lectura de empleados desde archivo de texto:
    val file: Path = Path.of("src/ficheros/empleados.csv")
    val br: BufferedReader = Files.newBufferedReader(file)
    var list: MutableList<Empleado> = mutableListOf()
    var aux = 0
    br.forEachLine { line ->
        val splitLine = line.split(",")
        if (aux != 0) {
            val empleado: Empleado = Empleado(splitLine[0],splitLine[1],splitLine[2],splitLine[3])
            list.add(empleado)
        }else aux++
    }

    // 2.Generación de un archivo XML:
    val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    val db: DocumentBuilder = dbf.newDocumentBuilder()
    val imp: DOMImplementation = db.domImplementation
    val document: Document = imp.createDocument(null, "empleados", null)
    val xml = Path.of("src/ficheros/empleados.xml")

    if (!xml.exists()) {
        xml.createFile()
    }

    list.forEach(){
        element ->
        val empleado: Element = document.createElement("empleado")
        document.documentElement.appendChild(empleado)
        empleado.setAttribute("id", element.id)

        val empleadoApellido: Element = document.createElement("apellido")
        val empleadoDepartamento: Element = document.createElement("departamento")
        val empleadoSalario: Element = document.createElement("salario")
        empleadoApellido.appendChild(document.createTextNode(element.apellido))
        empleadoDepartamento.appendChild(document.createTextNode(element.departamento))
        empleadoSalario.appendChild(document.createTextNode(element.salario))

        empleado.appendChild(empleadoApellido)
        empleado.appendChild(empleadoDepartamento)
        empleado.appendChild(empleadoSalario)

        val source: Source = DOMSource(document)

        //-> Qué clase usamos para escribir: StreamResult
        val result: Result = StreamResult(xml.toFile())

        // -> Qué herramienta usamos par realizar la transformación: transformer
        val transformer: Transformer = TransformerFactory.newInstance().newTransformer()

        //Bonus Point
        // Para Identar el XML correctamente
        transformer.setOutputProperty(OutputKeys.INDENT, "yes")

        //Por ultimo realizamos la transformacion
        transformer.transform(source, result)
    }

    // 3. Modificación de un nodo en el archivo XML:
        println("Introduce el id del empleado")
        val id = readln()
        println("Introduce el nuevo salario")
        val salario = readln()
        modify(id, salario, xml)

    // 4. Lectura del archivo XML modificado y salida en consola:
        read(xml)
}