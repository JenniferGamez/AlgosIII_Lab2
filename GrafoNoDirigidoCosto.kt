// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 1
// nombre del archivo: GrafoNoDirigidoCosto.kt

package ve.usb.libGrafo

import java.io.File

import java.io.InputStream
public class GrafoNoDirigidoCosto: Grafo {

    var vertices: Int = 0
    var lados: Int = 0 //conexiones
    // lista de lados vacio
    var grafo = Array(vertices){mutableListOf<AristaCosto>()}
    var E = Array(vertices){mutableListOf<AristaCosto>()}
    /*
    descrpcion: Se construye un grafo a partir del número de vértices.
    parametros de entrada = numDeVertices: Int
    Precondicion: numDeVertices > 0
    Postcondicion: this.grafo.size > 0
    
    T(n) = O(|vertices|)
    */
    
    constructor(numDeVertices: Int) {
        this.vertices = numDeVertices
        this.grafo = Array(this.vertices){mutableListOf<AristaCosto>()}
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. La segunda línea es el número de lados. 
     Las siguientes líneas corresponden a los lados. Cada línea de los lados tiene a dos enteros
     que corresponden a los vértices inicial y final de los lados,
     y un número real que es el costo del lado.
     Se asume que los datos del archivo están correctos, no se verifican.
     */ 
    constructor(nombreArchivo: String) {
        val inputStream: InputStream = File(nombreArchivo).inputStream()
        val linea = mutableListOf<String>()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { linea.add(it) } }

        // 
        this.vertices = linea[0].toInt() // Numero del vertices. Linea 1
        this.lados = linea[1].toInt() // Numero de lados. Linea 2
       
        this.grafo = Array(this.vertices){mutableListOf<AristaCosto>()}

        var n = linea.subList(2,linea.size)// Captando los vertices desde la linea 3
        n.forEach { 
            val lineaArista = it.split(" ")
            val arista = AristaCosto(lineaArista[0].toInt(), lineaArista[1].toInt(), lineaArista[2].toDouble()) 

            this.agregarAristaCosto(arista)
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
    @Throws(RuntimeException::class)
    fun agregarAristaCosto(a: AristaCosto) : Boolean {
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
                    agregar = agregar && (verTwo != u)
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

    /*
    DESCRIPCIÓN: Función que retorna la variable lados, donde se reserva el número de lados del grafo.
    
    PRECONDICIÓN: (lados es un dato tipo entero)
    
    POSTCONDICIÓN: (obtenerNumeroDeLados() retorna un dato tipo entero)
    
    TIEMPO: O(|E|)
    */
    override fun obtenerNumeroDeLados() : Int {
        var numeroLados : Int = 0
        for(i in 0..this.grafo.size-1){
            numeroLados = numeroLados + this.grafo[i].size
            
        }
        return numeroLados 
    }
    /*
    DESCRIPCIÓN: Función que retorna la variable vértices, donde se reserva el número de vértices del grafo.
    
    PRECONDICIÓN: (vertices es un dato tipo entero)
    
    POSTCONDICIÓN: (obtenerNumeroDeLados() retorna un dato tipo entero)
    
    TIEMPO: O(|V|)
    */
    override fun obtenerNumeroDeVertices() : Int {
        return this.grafo.size
    }

    /*
    DESCRIPCIÓN: Función que retorna una lista de adyacentes de v, que es la lista que se encuentra guardada
    en la posición v del arreglo grafo.
    
    PRECONDICIÓN: (v in 0..this.grafo.size-1)
    
    POSTCONDICIÓN: (adyacentes() es una lista de aristas)
    
    TIEMPO: O(|V|)
    */
    override fun adyacentes(v: Int) : Iterable<AristaCosto> {
        if (v !in 0..this.vertices-1) {
            throw RuntimeException ("v no pertenece a un vertice del grafo.")
        } 
        // el grafo es distinto de vacio
        if (this.grafo.isEmpty()) {
            throw RuntimeException ("El grafo esta vacio.")
        }
        return this.grafo[v]
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    @Throws(RuntimeException::class)
    fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
        var verOne:Int = l.cualquieraDeLosVertices()
        var verTwo:Int = l.elOtroVertice(verOne)
        var existe: Boolean = false
        var ladosAdy: MutableList<AristaCosto> = mutableListOf()
        
        try {
            if ((verOne > this.grafo.size-1) || (verTwo > this.grafo.size-1)){
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
                    if ((verOneLado == verOne || verTwoLado == verOne  || verOneLado == verTwo || verTwoLado == verTwo) && (lado !in ladosAdy)){
                        ladosAdy += mutableListOf(lado)
                    }
                }      
            }
        }else{
            println("El arco no pertence al grafo")
        }
        return ladosAdy 
    }

    /*
    DESCRIPCIÓN: Función que retorna una lista con todos las aristas, iterando sobre la variable 
    grafo que contiene todas las listas de adyacencias de todos los vértices.
    
    PRECONDICIÓN: (!grafo.isEmpty())
    
    POSTCONDICIÓN: (!arista.isEmpty())
    
    TIEMPO: O(|V|*|E|)
    */
     override operator fun iterator() : Iterator<AristaCosto> {
         // si el grafo es vacio lanza un Throw RuntimeException
        if (this.grafo.isEmpty()) {
            throw RuntimeException ("El grafo esta vacio.")
        }

        var arista = mutableListOf<AristaCosto>()
        for (ar in this.grafo) {
            ar.forEach { lado -> 
                if (lado !in arista && AristaCosto(lado.u,lado.v,lado.costo) !in arista) {
                    arista += lado
                }
            }
        }
        return arista.iterator()
     }
    
   /*
    DESCRIPCIÓN: Función que retorna el grado de un vértice, el cual corresponde al tamaño de la lista grafo[v]
    
    PRECONDICIÓN: (v in 0..vertices-1)
    
    POSTCONDICIÓN: (grado >= 0)
    
    TIEMPO: O(|V|)
    */
    override fun grado(v: Int) : Int {
                // Cuando el vertice no esta en el rango lanza unza un RuntimeException
                if (v !in 0..this.vertices-1) {
                    throw RuntimeException ("v no pertenece a un vertice del grafo.")
                }
                return this.adyacentes(v).count()
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
