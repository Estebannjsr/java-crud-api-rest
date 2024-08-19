package com.example.demo.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.Repositories.ProductoRepository;  
import com.example.demo.Entities.Producto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;






//Estructura que ad end points para consumir informacion
@RestController
//de springboot es para saber a donde quiere que vaya la url. si una persona llamaa nuestro puerto donde va estar /productos podra ingresar a nuestra base de datos
@RequestMapping("/productos")
public class productoControlador {
    
    //Instanciamos directamente en el repositorio que vamos a utilizar (gracias spring)
    @Autowired
    //Instanciamos un procutoRepository
    private ProductoRepository ProductoRepository;


    //METODOS GET, POST, PATCH, DELETE, PUT
    //GET
    //Esto traera todos los productos en este caso
    @GetMapping
    public List<Producto> obtenerProductos(){
        //en este caso retornamos un productoRepository pero cual?? con el metodo.findAll me los trae todos
        return ProductoRepository.findAll();
    }
    
    /*
    Aca usamos un get individual, que solo me traiga un producto pero por eL id  creamos un metodo de clase Producto en este caso obtenerProductoPorId y le tenemos que pasar la variable
    en este caso el id lo traemos con y seguido el tipo de dato y el dato (todo esto viene de la clase producto) @PathVariable Long id | luego retornamos el valor y traemos el id con findById(id)
    y dentro le colocamos el nombre de nuestro id 
    */

    @GetMapping("/{id}")  
    public Producto obtenerProductoPorId(@PathVariable Long id){
        return ProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID " + id));

    } 
    

    /* 
     POST grabamos en la bbdd  |   createProducto(@RequestBody Producto producto) esto lo que hace es crear un producto y esto requiere un body que serian los atributos de producto por eso 
     creamos un objeto producto el cual lo traeremos en el return con el metodo .save(producto) 
    */

    @PostMapping
    public Producto crearProducto(@RequestBody Producto producto){
        return ProductoRepository.save(producto); 
    }    
        
    /* PUT actualizar
        //del objeto producto que creamos de tipo productoRepository.findbyid(id) lo que significa que traiga el produco por ese id, creamos un orElseThrow que seria como una excepcion
        //(dentro creamos una funcion flecha -> new RuntimeException("el mensaje que saldra si no encuentra el producto"))
        //luego del producto que trajimos con el id le cambiamos el setnombre y setprecio por los del detalle  @RequestBody Producto detallesProducto que seria el produc   to que trajo
      */

    @PutMapping("/{id}")  
    public Producto actualizarProducto(@PathVariable Long id, @RequestBody Producto detallesProducto){
        Producto producto = ProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID " + id));
     
        producto.setNombre(detallesProducto.getNombre());
        producto.setPrecio(detallesProducto.getPrecio());
    
        //.save actualiza la bbdd
        return ProductoRepository.save(producto);
    }
    

    /* 
     Delete borrar       | aca creamos el deletemapping que reciba el id creamos un metodo que devolvera un string y que recibe de parametros el id String borrarProducto(@PathVariable Long id)}
     luego el manejo de la excepcion si es que no se encuentra el id luego eliminamos el producto
     */
    @DeleteMapping("/{id}")
    public String borrarProducto(@PathVariable Long id){
        Producto producto = ProductoRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("No se encontro el producto con el ID " + id));
        ProductoRepository.delete(producto);
        return "el producto con el ID: " + id + " fue eiiminado correctamente guiño "; 
    }


 
}
