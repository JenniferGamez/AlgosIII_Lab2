// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 1
// nombre del archivo: GrafoNoDirigido.kt
package ve.usb.libGrafo

import java.io.File

import java.io.InputStream

public class GrafoNoDirigido: Grafo {

    var vertices: Int = 0
    var lados: Int = 0

    var grafo = Array(vertices){mutableListOf<Arista>()}


    /*Constructor con vertices, se construye un grafo a partir del numero de vertices
    
    precondicion: numDeVertices > 0
    postcondicion: this.grafo.size > 0

    tiempo = O(|V|)
    */
    
    constructor(numDeVertices: Int) {
        this.vertices = numDeVertices
        this.grafo = Array(this.vertices){mutableListOf<Arista>()}

    }

    /*
    Constructor 
    Construye un grafo con vertices extraidos del archivo.txt

    precondicion: nombreArchivo == nombreArchivo.toString()

    Tiempo = O(n)
     */  
    constructor(nombreArchivo: String) {
        //captando la informacion del archivo txt

        val inputStream: InputStream = File(nombreArchivo).inputStream()

        val linea = mutableListOf<String>()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { linea.add(it) } }

        this.vertices = linea[0].toInt() // Numero de vertices linea 1
        this.lados = linea[1].toInt() // numero de lados linea 2

        this.grafo = Array(this.vertices){mutableListOf<Arista>()} //Declaramos grafos como una lista

        var n = linea.subList(2,linea.size) // Captando los vertices desde la liena 3
        n.forEach {
            val lineaArista = it.split(" ")
            val arista = Arista(lineaArista[0].toInt(),lineaArista[1].toInt()) //Construyendo arco

            //Agregando lado/arista arco al grafo
            this.agregarArista(arista)

        }
    }

    /*
    DESCRIPCIÓN: Revisa que no los vértices de la arista no representen un bucle. Posteriormente
    revisa en cada lado adyacente de del vertice x, el cual representa uno de los vertices de la 
    arista, el otro vértice distinto a cualquiera de que se encuentren ingresados, reservando la 
    afirmación en la variable cambio. Por último, si cambio es igual a true, se introduce la arista
    tanto en la lista de adyacencia de x como en la lista de adyacencia de y, suma un lado al grafo.
    Finalmnete, retorna cambio.
    
    PRECONDICIÓN: ((verOne in 0..this.grafo.size-1) && (verTwo in 0..this.grafo.size-1))
    
    POSTCONDICIÓN: (a in grafo)
    
    TIEMPO: O(|E|)
    */
    fun agregarArista(a: Arista) : Boolean {
        var verOne:Int = a.cualquieraDeLosVertices()
        var verTwo = a.elOtroVertice(verOne)
        var agregar: Boolean = true
        
        try {
            if (verOne > this.grafo.size-1 || (verTwo > this.grafo.size-1)){
                throw ArrayIndexOutOfBoundsException()
            }
            if (verOne != verTwo){
                this.grafo[verOne].forEach { k ->
                    var u = k.elOtroVertice(verOne)
                    agregar = agregar && (verTwo != u )
                }
                if (agregar) {
                    this.grafo[verOne].add(a)
                    this.grafo[verTwo].add(a)
                    this.lados = this.lados + 1
                }
            }
        } catch (e: ArrayIndexOutOfBoundsException) {
            //e.printStackTrace()
            println("Un vertice no pertence al grafo.")
        }
        return agregar
    }

    
    // Retorna el número de lados del grafo

    /*Retorna el numero de lados del grafo. Hemos almacenado ese dato en la variable lados
    
    precondicion: true
    postcondicion: obtenerNumeroDeLados = lados

    Tiempo: O(|E|)
    */
    override fun obtenerNumeroDeLados() : Int {
        return this.lados
        
    }

    /* Retorna el numero del vertices del grafo
    
    Precondicion: true
    Postcondicion: obtenerNummeroDeVertices = vertices
    tiempo: O(|V|)

    
    */

    override fun obtenerNumeroDeVertices() : Int {
        return this.vertices 
    }

    /*Descripcion:retorna los lados adyacentes al vertice v, es decir, los lados que contienen al vertice v 
    parametro de entrada v: Int
    precondicion: 0 <= v < vertices
    postcondicion: adyacentes(v) == grafo[v]

    tiempo de ejecucion O(|V|)
    */
    
    override fun adyacentes(v: Int) : Iterable<Arista> {
        //Precondicion V pertenece a los vertices
        if (v !in 0..this.vertices-1){
            throw RuntimeException("v no pertenece a un vertice del grafo.")
        }
        // el grafo es distinto de vacio
        if (this.grafo.isEmpty()){
            throw RuntimeException("El grafo esta vacio")
        }
        return this.grafo[v]

    
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
    */
    @Throws(RuntimeException::class)
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
        var verOne:Int = l.cualquieraDeLosVertices()
        var verTwo:Int = l.elOtroVertice(verOne)
        var existe: Boolean = false
        var ladosAdy: MutableList<Arista> = mutableListOf()
        
        try {
            if ((verOne > this.grafo.size-1) ||  (verTwo > this.grafo.size-1)){
                throw ArrayIndexOutOfBoundsException()
            }
            
        } catch (e: ArrayIndexOutOfBoundsException) {
            //e.printStackTrace()
            println("Un vertice no pertence al grafo.")
            return ladosAdy
        }

        for (vertice in 0..this.grafo.size-1){
            for (lado in this.grafo[vertice]){
                var verOneLado:Int = lado.cualquieraDeLosVertices()
                var verTwoLado:Int = lado.elOtroVertice(verOneLado)
                if ((verOneLado == verOne || verOneLado==verTwo) && (verTwoLado == verOne ||  verTwoLado==verTwo)){
                    existe = true
                }
            }
        }

        if (existe){
            for (vert in 0..this.grafo.size-1) {
                for (lado in this.grafo[vert] ){
                    var verOneLado:Int = lado.cualquieraDeLosVertices()
                    var verTwoLado:Int = lado.elOtroVertice(verOneLado)
                    if ((verOneLado == verOne || verTwoLado == verOne ||  verOneLado == verTwo || verTwoLado == verTwo) && (lado !in ladosAdy)){
                        ladosAdy += mutableListOf(lado)
                    }
                }      
            }
        }else{
            println("El arco no pertence al grafo")
        }
        return ladosAdy 
    }
    
    // Retorna todos los lados del grafo no dirigido

    /*
     Precondicion: !grafo.isEmpty() and v in V
    Postcondicion: !arista.isEmpty()

    Tiempo = O(|V|*|E|)
    */ 
    override operator fun iterator() : Iterator<Arista> {
         // Precondicion
         if (this.grafo.isEmpty()) {
            throw RuntimeException ("El grafo esta vacio.")
        }

        //variable que contiene la lista con los arcos
        var arista = mutableListOf<Arista>()
        for (ar in this.grafo) {
            ar.forEach { lado -> 
                if (lado !in arista && Arista(lado.u,lado.v) !in arista) {
                    arista += lado
                }
            }
        }
        return arista.iterator()
    }


    /* 
    // Grado del grafo

    Precondicion: v in V
    Postcondicion: grado >= 0

    T(n) = O(|V|)
    */
    override fun grado(v: Int) : Int {
        // Assertion cuando v no pertenece a un vertice del grafo
        if (v !in 0..this.vertices-1) {
            throw RuntimeException ("v no pertenece a un vertice del grafo.")
        }
        return this.grafo[v].size
    }


    /*
    DESCRIPCIÓN: Función que retorna el grafo como String
    
    PRECONDICIÓN: (aristas == E && vertices > 0)
    
    POSTCONDICIÓN: (G = (V, E) as String)
    
    TIEMPO: O(|V|*|E|)
    */
    override fun toString() : String {
        // Precondicion
        var grafoString : String = "" 
        var verticesAdy : Int 
        for( i in 0..this.grafo.size-1){
            grafoString = grafoString + ("$i -> [")
            verticesAdy = grafo[i].size
            for (arista in this.grafo[i]){
                if (arista != grafo[i][verticesAdy-1]){
                    grafoString = grafoString + ("$arista, ")
                } else {
                    grafoString = grafoString + ("$arista]")
                }
            }
        
            if (verticesAdy == 0){
                grafoString = grafoString + ("]")
            } 
            
            if (i != grafo.size -1 ){
                grafoString = grafoString + ("\n")
            }
                
        }
        return grafoString
    }
}
