package cl.dci.eshop.controller;

import cl.dci.eshop.model.Producto;
import cl.dci.eshop.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/producto") // Base URL for all handlers in this controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;

    // Display form to create a new product
    @GetMapping("/new")
    public String newProductoForm(Model model) {
        model.addAttribute("producto", new Producto());
        return "crear-producto"; // returns HTML view to create a product
    }

    // Save new product
    @PostMapping("/save")
    public String saveProducto(@ModelAttribute Producto producto, @RequestParam("imagen") MultipartFile imagenFile, RedirectAttributes attributes) {
        if (!imagenFile.isEmpty()) {
            String imagePath = saveImage(imagenFile); // Save image and get path
            producto.setImagenUrl(imagePath); // Set image URL to product
        }
        productoRepository.save(producto); // Save product to database
        attributes.addFlashAttribute("mensaje", "Producto guardado con éxito");
        return "redirect:/producto/productos"; // Redirect to product listing
    }

    // Edit product form
    @GetMapping("/edit/{id}")
    public String editProductoForm(@PathVariable Integer id, Model model) {
        Producto producto = productoRepository.findById(id).orElse(null);
        model.addAttribute("producto", producto);
        return "editar-producto"; // returns HTML view to edit the product
    }

    // List all products
    @GetMapping("/productos")
    public String listProductos(Model model) {
        model.addAttribute("productos", productoRepository.findAll());
        return "productos"; // returns HTML view displaying all products
    }

    // Utility method to save image
    private String saveImage(MultipartFile file) {
        try {
            String folder = "C:\\Users\\jkj65\\OneDrive\\Escritorio\\AlmacenImagenesTodito\\"; // Path to save the images
            Path path = Paths.get(folder + file.getOriginalFilename());
            Files.write(path, file.getBytes()); // Save the image file
            return path.toString(); // Return the path string where image is saved
        } catch (IOException e) {
            e.printStackTrace();
            return null; // Return null if there is an error
        }
    }
}
