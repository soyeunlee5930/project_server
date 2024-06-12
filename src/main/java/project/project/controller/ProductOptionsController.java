package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.ProductOptions;
import project.project.service.ProductOptionsService;

import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductOptionsController {
    @Autowired
    private ProductOptionsService productOptionsService;

    @GetMapping("/product-options")
    public ResponseEntity<List<ProductOptions>> getAllProductOptions() {
        List<ProductOptions> productOptions = productOptionsService.getAllProductOptions();
        return ResponseEntity.status(HttpStatus.OK).body(productOptions);
    }

    @GetMapping("/product-options/{id}")
    public ResponseEntity<ProductOptions> findProductOptionById(@PathVariable Integer id) {
        ProductOptions productOption = productOptionsService.findProductOptionById(id);
        if (productOption != null) {
            return ResponseEntity.status(HttpStatus.OK).body(productOption);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/product-options/add")
    public ResponseEntity<String> insertProductOption(@RequestBody ProductOptions productOption) {
        try {
            // 중복값 확인
            int count = productOptionsService.checkDuplicate(productOption);
            if (count > 0 ) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate productOption");
            }
            productOptionsService.insertProductOption(productOption);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the request");
        }
    }

    @PutMapping("/product-options/{id}")
    public ResponseEntity<String> insertProductOption(@PathVariable Integer id, @RequestBody ProductOptions productOption) {
        productOption.setId(id);
        try {
            // 중복값 확인
            int count = productOptionsService.checkDuplicate(productOption);
            if (count > 0 ) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate productOption");
            }
            productOptionsService.insertProductOption(productOption);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the request");
        }
    }

    @DeleteMapping("/product-options/{id}")
    public ResponseEntity<Void> deleteProductOption(@PathVariable Integer id) {
        productOptionsService.deleteProductOption(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
