package IntegracionBackFront.backfront.Controller.Categorias;

import IntegracionBackFront.backfront.Models.DTO.Categories.CategoryDTO;
import IntegracionBackFront.backfront.Services.Categories.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class categoriaControlador {

    //Inyectamos el sevice osea la clase
    @Autowired
    private CategoryService service;

    @GetMapping("/getDataCategorias")
    private ResponseEntity<Page<CategoryDTO>> optenerDatos(
            @RequestParam(defaultValue = "0") int page, //La Api va a devolver paginas, pero 10 registro por paginas y minimo 0
            @RequestParam(defaultValue = "3") int size
    ){
        //parte 1. Se evalua cuantos registros quiere ver el usuario por pagina
        //Validacion para que el usuario seleccione una cantidad de datos correcta
        if (size <= 0 || size > 50) {
            ResponseEntity.badRequest().body(Map.of(
                    "status", "Ek tamaño de la agina deve de estar entre 1 y 50"
            ));
            return ResponseEntity.ok(null);
        }

        Page<CategoryDTO> category = service.getAllCategories(page, size);

        //parte 2. Invocando al metodo getAllCategories contenido en el service
        //Evaluamos si la API nos tira datos, y si no hay registros, entonces no deci este mensaje
        if (category == null) {
            ResponseEntity.badRequest().body(Map.of(
                    "status", "No hay categorias registradas"
            ));
        }

        return ResponseEntity.ok(category);
    }
}