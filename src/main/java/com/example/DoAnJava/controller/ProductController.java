package com.example.DoAnJava.controller;



import com.example.DoAnJava.model.Product;
import com.example.DoAnJava.service.CategoryService;
import com.example.DoAnJava.service.ProductService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;// Đảm bảo bạn đã inject CategoryService
    // Display a list of all products
    @GetMapping
    public String showProducts(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        return "/products/products-list";
    }
    //For adding a new product
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories",categoryService.getAllCategories());// Load categories
        return "/products/add-product";
    }
    // Process the form for adding a new product
    @PostMapping("/add")
    public String addProduct(@Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "products/add-product";
        }
        productService.addProduct(product);
        return "redirect:/products";
    }

    // For editing a product
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + id));
        model.addAttribute("product", product);
        model.addAttribute("categories",categoryService.getAllCategories());//Load categories
        return "/products/update-product";
    }
    // Process the form for updating a product
    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") Long id, @Valid Product product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            product.setId(id);//// set id to keep it in the form in case of errors
            return "/products/update-product";
        }
        productService.updateProduct(product);
        return "redirect:/products";
    }

    // Handle request to delete a product
    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProductById(id);
        return "redirect:/products";
    }

    @GetMapping("/sorted-by-id")
    public String showProductsByIdDesc(Model model) {
        List<Product> products = productService.getAllProductsSortedByIdDesc();
        model.addAttribute("products", products);
        return "products/products-list-sorted-by-id"; // Đảm bảo rằng tệp products-list-sorted-by-id.html tồn tại
    }

//    @PostMapping("/sorted-by-id")
//    public String showProductsByIdDescPost(Model model) {
//        List<Product> products = productService.getAllProductsSortedByIdDesc();
//        model.addAttribute("products", products);
//        return "redirect:/products"; // Đảm bảo rằng tệp products-list-sorted-by-id.html tồn tại
//    }

    @GetMapping("/sorted-by-category")
    public String showProductsByCategory(Model model) {
        List<Product> products = productService.getAllProductsSortedByCategory();
        model.addAttribute("products", products);
        return "products/products-list-sorted-by-category"; // Đảm bảo rằng tệp products-list-sorted-by-category.html tồn tại
    }

    // Phương thức POST để sắp xếp sản phẩm theo category
//    @PostMapping("/sorted-by-category")
//    public String showProductsByCategoryPost(Model model) {
//        List<Product> products = productService.getAllProductsSortedByCategory();
//        model.addAttribute("products", products);
//        return "redirect:/products"; // Đảm bảo rằng tệp products-list-sorted-by-category.html tồn tại
//    }

    @GetMapping("/sorted-by-sales")
    public String showProductsBySalesCount(Model model) {
        List<Product> products = productService.getAllProductsSortedBySalesCount();
        model.addAttribute("products", products);
        return "products/products-list-sorted-by-sales"; // Đảm bảo rằng tệp products-list-sorted-by-sales.html tồn tại
    }

    // Phương thức POST để sắp xếp sản phẩm theo số lượng bán được
//    @PostMapping("/sorted-by-sales")
//    public String showProductsBySalesCountPost(Model model) {
//        List<Product> products = productService.getAllProductsSortedBySalesCount();
//        model.addAttribute("products", products);
//        return "redirect:/products"; // Đảm bảo rằng tệp products-list-sorted-by-sales.html tồn tại
//    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<Product> products = productService.searchProductsByName(query);
        model.addAttribute("products", products);
        return "products/products-list-search-results"; // Đảm bảo rằng tệp products-list-search-results.html tồn tại
    }


}
