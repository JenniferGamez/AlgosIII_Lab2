// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 2
// nombre del archivo: Arco.kt
package ve.usb.libGrafo

public open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    // Retorna el vértice inicial del arco
    /*
    DESCRIPCIÓN: Función que retorna el vértice fuente del Arco

    PRECONDICIÓN: (inicio es un dato tipo Entero)

    POSTCONDICIÓN: (fuente() retorna un dato tipo Entero)
    
    TIEMPO: O(1)
    */
    fun fuente() : Int {
        return this.inicio
    }

    // Retorna el vértice final del arco
    /*
    DESCRIPCIÓN: Función que retorna el vértice sumidero del Arco

    PRECONDICIÓN: (fin es un entero)
    
    POSTCONDICIÓN: (sumidero() retorna una variable tipo entero)
    
    TIEMPO: O(1)
    */
    fun sumidero() : Int {
        return this.fin
    }

    // Representación del arco
    /*
    DESCRIPCIÓN: Función que convierte el arco en un String

    PRECONDICIÓN: ((inicio,fin) == par)

    POSTCONDICIÓN: (toString() retorna un dato tipo String)
    
    TIEMPO: O(1)
    */
    override fun toString() : String {
        return "${this.inicio} -> ${this.fin}"
     }
}
