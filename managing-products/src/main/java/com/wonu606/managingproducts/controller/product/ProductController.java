package com.wonu606.managingproducts.controller.product;

import com.wonu606.managingproducts.service.product.ProductService;
import com.wonu606.managingproducts.service.product.dto.ProductCreateRequestDto;
import com.wonu606.managingproducts.service.product.dto.ProductResponseDto;
import com.wonu606.managingproducts.service.product.dto.ProductUpdateRequestDto;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public String showProductList(Model model) {
        List<ProductResponseDto> products = service.getAllProducts();
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/new")
    public String showCreateProductForm(Model model) {
        model.addAttribute("product", new ProductCreateRequestDto("", "", 0L, 0));
        return "new-product";
    }

    @PostMapping
    public String createProduct(@ModelAttribute("product") ProductCreateRequestDto product) {
        service.createProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        ProductResponseDto product = service.getProductsById(id)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 product Id: " + id));
        model.addAttribute("product", product);
        return "update-product";
    }

    @PostMapping("/{id}/edit")
    public String updateProduct(@PathVariable("id") Long id,
            @ModelAttribute("product") ProductUpdateRequestDto product) {
        ProductUpdateRequestDto productToUpdate = new ProductUpdateRequestDto(id,
                product.name(), product.category(), product.price(), product.quantity());

        service.updateProduct(productToUpdate);
        return "redirect:/products";
    }
}
