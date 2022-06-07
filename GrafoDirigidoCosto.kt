// Estudiantes: Jennifer Gamez 16-10396
//              Amaranta Villegas 16-11247
// laboratorio Semana 1
// nombre del archivo: GrafoDirigidoCosto.kt
package ve.usb.libGrafo

import java.io.File

public class GrafoDirigidoCosto : Grafo {
    var grafo : Array<MutableList<ArcoCosto>> = arrayOf()
    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        this.grafo = Array(numDeVertices){mutableListOf<ArcoCosto>()}
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. La segunda línea es el número de lados. 
     Las siguientes líneas corresponden a los lados. Cada línea de los lados tiene a dos enteros
     que corresponden a los vértices inicial y final de los lados,
     y un número real que es el costo del lado.
     Se asume que los datos del archivo están correctos, no se verifican.
     */  
    constructor(nombreArchivo: String)  {
        var lineas: List<String> = File(nombreArchivo).bufferedReader().readLines()

        //variable de lectura que lee el numero de vertices
        val numDeVertices: Int = lineas[0].toInt()
        val TotaldeLineas: Int = lineas.size
        this.grafo = Array(numDeVertices){mutableListOf<ArcoCosto>()}

        //slice() retorna los elementos de una coleccion de especificados por sus indices

        for (linea in lineas.slice(2..(TotaldeLineas-1))){
            //separamos los elementos de los arcos
            val separarArcos = linea.split(" ")
            //verificamos el tamano
            if (separarArcos.size < 2){
                throw RuntimeException("Error : entrada vacia")
            }
            //construimos las variables inicio y fin para almacenar los nodos
            val inicio = separarArcos[0].toInt()
            val final = separarArcos[1].toInt()
            var valorCosto = separarArcos[2].toDouble()
            this.agregarArcoCosto(ArcoCosto(inicio,final, valorCosto))
        }
    }

    /* Agrega un lado al digrafo. Si el lado no se encuentra en el grafo se agrega y retorna True, en caso contrario no se agrega  al grafo y se retorna false
     */
    fun agregarArcoCosto(a: ArcoCosto) : Boolean {
        //vertice de inicio
        val fuente : Int = a.fuente()
        val sumidero: Int = a.sumidero()
        var existe: Boolean = true
        var lados : Int = 0

        //vemos si existe el lado en el grafo y retornamos true
        if ((fuente in 0..this.grafo.size -1) && (sumidero in 0..this.grafo.size-1)){
            if (this.grafo[fuente].isEmpty()){
                this.grafo[fuente].add(a)
                lados +=1
            } else {
                this.grafo[fuente].forEach {p ->
                    existe = existe && (p.sumidero() != sumidero)
                }
                if (existe) {
                    this.grafo[fuente].add(a)
                    lados += 1
                }
            }
        }
        
        return existe
    }

    /*
    DESCRIPCIÓN: Función que retorna el grado de un vértice, el cual corresponde al tamaño de la lista grafo[v],
    grado exterior, sumado al grado interior el cual corresponde a la cantidad de aristas adyacentes a otros vértices
    de las cuales v es sumidero.
    
    PRECONDICIÓN: (v in 0..rhis.grado.size-1)
    
    POSTCONDICIÓN: (grado(v) >= 0  && grado(v) == gradoInterior(v) + gradoExterior(v))
    
    TIEMPO: O(|V|*|E|)
    */
    @Throws(RuntimeException::class)
    override fun grado(v: Int) : Int {
        if ((v <0 ) or (v >= this.grafo.size)){
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        var valor: Int = this.gradoExterior(v) + this.gradoInterior(v)
        return valor 
    }

    /*
    DESCRIPCIÓN: Función que retorna el grado de un vértice, el cual corresponde al tamaño de la lista grafo[v],
    grado exterior.
    
    PRECONDICIÓN: (v in 0..this.grafo.size-1)
    
    POSTCONDICIÓN: (gradoExterior(v) >= 0)
    
    TIEMPO: O(|E|)
    */
    @Throws(RuntimeException::class)
    fun gradoExterior(v: Int) : Int {
        if (( v <0) or (v >= this.grafo.size)){
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        var exterior = this.grafo[v].size
        return exterior
    }

    /*
    DESCRIPCIÓN: Función que retorna el grado interior, correspondiente la cantidad de aristas adyacentes 
    a otros vértices de las cuales v es sumidero.
    
    PRECONDICIÓN: (v in 0..this.grafo.size-1)
    
    POSTCONDICIÓN: (gradoInterior(v) >= 0)
    
    TIEMPO: O(|E|)
    */
    @Throws(RuntimeException::class)
    fun gradoInterior(v: Int) : Int {
        if ((v < 0) or (v >= this.grafo.size)){
            throw RuntimeException("EL vertice no pertenece al grafo")
        }
        var interior: Int = 0
        // por cada lista en el arreglo
        for (x in this.grafo){
            //por cada arco en la lista
            x.forEach { y ->
                if (y.fin == v) {
                    interior += 1
                }
            }
        }
        return interior 
    }
    /*
    DESCRIPCIÓN: Función que retorna la variable lados, donde se reserva el número de lados del grafo.
    
    PRECONDICIÓN: (lados es un dato tipo entero)
    
    POSTCONDICIÓN: (obtenerNumeroDeLados() retorna un dato tipo entero)
    
    TIEMPO: O(1)
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
    
    TIEMPO: O(1)
    */
    override fun obtenerNumeroDeVertices() : Int {
        return this.grafo.size
    }
    /*
    DESCRIPCIÓN: Función que retorna una lista de adyacentes de v, que es la lista que se encuentra guardada
    en la posición v del arreglo grafo.
    
    PRECONDICIÓN: (v in 0..this.grafo.size-1)
    
    POSTCONDICIÓN: (adyacentes() es una lista de arcos)
    
    TIEMPO: O(1)
    */ 
    @Throws(RuntimeException::class)
    override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
        if ((v <0) or (v >= this.grafo.size)){
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        return this.grafo[v]
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    @Throws(RuntimeException::class)
    fun ladosAdyacentes(l: ArcoCosto) : Iterable<ArcoCosto> {
        val inic: Int = l.fuente()
        val fin: Int = l.sumidero()
        var existe: Boolean = false
        var ladosAdy: MutableList<ArcoCosto> = mutableListOf()
        
        try {
            if ((inic > this.grafo.size-1) ||  (fin > this.grafo.size-1)){
                throw ArrayIndexOutOfBoundsException()
            }
            
        } catch (e: ArrayIndexOutOfBoundsException) {
            //e.printStackTrace()
            println("Un vertice no pertence al grafo.")
            return ladosAdy
        }

        for (vertice in 0..this.grafo.size-1){
            for (lado in this.grafo[vertice]){
                if ((lado.fuente()==inic && lado.sumidero()==fin) && !(lado.fuente()==fin && lado.sumidero()==inic)){
                    existe = true
                }
            }
        }

        if (existe){
            for (vert in 0..this.grafo.size-1) {
                for (lado in this.grafo[vert] ){
                    if ((lado.sumidero() == inic || lado.fuente() == inic || lado.sumidero() == fin || lado.fuente() == fin) && (lado !in ladosAdy)){
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
    DESCRIPCIÓN: Función que retorna una lista con todos los arcos, iterando sobre la variable 
    grafo que contiene todas las listas de adyacencias de todos los vértices.
    
    PRECONDICIÓN: (!grafo.isEmpty())
    
    POSTCONDICIÓN: (!arco.isEmpty())
    
    TIEMPO: O(|V|*|E|)
    */
    override operator fun iterator() : Iterator<ArcoCosto> {
        var listArcoCosto = listOf<ArcoCosto>()

        for (t in this.grafo){
            listArcoCosto+=(t)
        }

        return listArcoCosto.iterator()
    }


    /*
    DESCRIPCIÓN: Función que retorna el grafo como String
    
    PRECONDICIÓN: (arcos == E && vertices > 0)
    
    POSTCONDICIÓN: (G = (V, E) as String)
    
    TIEMPO: O(|V|*|E|)
    */
    override fun toString() : String {
                    
        var grafoString : String = "" 
        var verticesAdy : Int 
        for( i in 0..this.grafo.size-1){
            grafoString = grafoString + ("$i -> [")
            verticesAdy = grafo[i].size
            for (arco in this.grafo[i]){
                if (arco != grafo[i][verticesAdy-1]){
                    grafoString = grafoString + ("$arco, ")
                } else {
                    grafoString = grafoString + ("$arco]")
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

