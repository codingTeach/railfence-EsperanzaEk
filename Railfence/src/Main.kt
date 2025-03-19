import java.io.File
import java.io.IOException
import javax.swing.JFileChooser
import javax.swing.JOptionPane

/*
Nombre: Rocio de la Esperanza Ek Guerrero
Programa: Cifrado Rail Fence Cipher
Descripción: Implementación del algoritmo de cifrado Rail Fence Cipher
                el número de rejillas se le solicitará al usuario.
 */

fun cifrar(texto: String, rejillas: Int): String {
    if (rejillas <= 1) return texto
    if (texto.isEmpty()) return ""

    val rieles = Array(rejillas) { StringBuilder() }

    var actual = 0
    var direccion = 1 // 1 para bajar, -1 para subir

    for (caracter in texto) {
        rieles[actual].append(caracter)
        actual += direccion

        if (actual == 0 || actual == rejillas - 1) {
            direccion = -direccion
        }
    }

    val textoCifrado = StringBuilder()
    for (riel in rieles) {
        textoCifrado.append(riel)
    }

    return textoCifrado.toString()
}

fun descifrar(textoCifrado: String, rejillas: Int): String {
    if (rejillas <= 1) return textoCifrado
    if (textoCifrado.isEmpty()) return ""

    val matrizRieles = Array(rejillas) { CharArray(textoCifrado.length) { ' ' } }

    var actual = 0
    var direccion = 1

    for (i in textoCifrado.indices) {
        matrizRieles[actual][i] = '*'
        actual += direccion
        if (actual == 0 || actual == rejillas - 1) {
            direccion = -direccion
        }
    }

    var indice = 0
    for (i in 0 until rejillas) {
        for (j in textoCifrado.indices) {
            if (matrizRieles[i][j] == '*' && indice < textoCifrado.length) {
                matrizRieles[i][j] = textoCifrado[indice++]
            }
        }
    }

    val textoDescifrado = StringBuilder()
    actual = 0
    direccion = 1
    for (i in textoCifrado.indices) {
        textoDescifrado.append(matrizRieles[actual][i])
        actual += direccion
        if (actual == 0 || actual == rejillas - 1) {
            direccion = -direccion
        }
    }

    return textoDescifrado.toString()
}

fun main() {

    do{
        var opcion = Integer.parseInt( JOptionPane.showInputDialog(null,
            "CIFRADO RAIL FENCE\n\n****MENÚ****\n1. Cifrar\n2. Descifrar\n3. Salir"));

        if (opcion == 3) { break; }

        var textoOriginal:String = "" //texto plano
        var c: String = "" //cifrado
        var rejilla:Int = 0

        when (opcion){
            1 -> {
                val selectorArchivo = JFileChooser()
                val seleccion = selectorArchivo.showOpenDialog(null)

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    val archivo = selectorArchivo.selectedFile

                    textoOriginal = File(archivo.path).readText()
                    rejilla = JOptionPane.showInputDialog("Ingrese el número de rejillas: ").toInt()

                    c = cifrar(textoOriginal, rejilla)
                    JOptionPane.showMessageDialog(null, "Texto cifrado: $c")
                    //println(c)
                    val selectorGuardar = JFileChooser()
                    val seleccionGuardar = selectorGuardar.showSaveDialog(null)

                    if (seleccionGuardar == JFileChooser.APPROVE_OPTION) {
                        val archivoSalida = selectorGuardar.selectedFile
                        File(archivoSalida.path).writeText(c)

                        JOptionPane.showMessageDialog(null, "Texto cifrado guardado en: ${archivoSalida.path}")
                    }
                }
            }
            2 -> {
                val selectorArchivo = JFileChooser()
                val seleccion = selectorArchivo.showOpenDialog(null)

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    val archivo = selectorArchivo.selectedFile

                    c = File(archivo.path).readText()
                    rejilla = JOptionPane.showInputDialog("Ingrese el número de rejillas: ").toInt()

                    textoOriginal = descifrar(c, rejilla)
                    JOptionPane.showMessageDialog(null, "Texto descifrado: $textoOriginal")
                    val selectorGuardar = JFileChooser()
                    val seleccionGuardar = selectorGuardar.showSaveDialog(null)

                    if (seleccionGuardar == JFileChooser.APPROVE_OPTION) {
                        val archivoSalida = selectorGuardar.selectedFile
                        File(archivoSalida.path).writeText(textoOriginal)

                        JOptionPane.showMessageDialog(null, "Texto cifrado guardado en: ${archivoSalida.path}")
                    }
                }
            }
            else -> {
                JOptionPane.showMessageDialog(null, "Opción inválida")
            }
        }
    } while(true)
}