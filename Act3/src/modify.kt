import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import java.nio.file.Path
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.*
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

fun modify(id: String, nuevoSalario: String, file: Path){

    val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    val db: DocumentBuilder = dbf.newDocumentBuilder()
    val document: Document = db.parse(file.toFile())
    val root = document.documentElement
    val listaNodos: NodeList = root.getElementsByTagName("empleado")
    for (i in 0..<listaNodos.length){
        val nodo: Node = listaNodos.item(i)

        if (nodo.nodeType == Node.ELEMENT_NODE) {
            val nodoElemento: Element = nodo as Element

            val elementoId = nodoElemento.attributes.getNamedItem("id").nodeValue
            val elementSalario = nodoElemento.getElementsByTagName("salario")

            if (elementoId == id){
                elementSalario.item(0).textContent = nuevoSalario
                break
            }

        }
    }

    val source: Source = DOMSource(document)
    val result: Result = StreamResult(file.toFile())
    val optimus: Transformer = TransformerFactory.newInstance().newTransformer()
    optimus.transform(source, result)

}

fun read(file: Path){
    val dbf: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    val db: DocumentBuilder = dbf.newDocumentBuilder()
    val document: Document = db.parse(file.toFile())
    val root = document.documentElement
    val listaNodos: NodeList = root.getElementsByTagName("empleado")

    for (i in 0..<listaNodos.length) {
        val nodo: Node = listaNodos.item(i)

        if (nodo.nodeType == Node.ELEMENT_NODE) {
            val nodoElemento: Element = nodo as Element

            val id = nodoElemento.getAttribute("id")
            val apellido = nodoElemento.getElementsByTagName("apellido").item(0).textContent
            val departamento = nodoElemento.getElementsByTagName("departamento").item(0).textContent
            val salario = nodoElemento.getElementsByTagName("salario").item(0).textContent

            println("ID: $id, Apellido: $apellido, Departamento: $departamento, Salario: $salario")

        }

    }
}
