package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.project.model.ProductOptions;
import project.project.model.Stock;
import project.project.requestParam.ProductDetails;
import project.project.requestParam.ProductOptionDetails;
import project.project.requestParam.ProductOptionRequest;
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

    @GetMapping("/products/{productId}/product-options")
    public ResponseEntity<List<ProductOptions>> getProductOptionsByProductId(@PathVariable Integer productId) {
        List<ProductOptions> productOptionsList = productOptionsService.getProductOptionsByProductId(productId);
        if (productOptionsList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productOptionsList);
    }

    // 상품 재고관리페이지에서 사용되는 상세 정보(상품명, 색상명, 사이즈, 재고수량)
    @GetMapping("/products/{productId}/details")
    public ResponseEntity<List<ProductDetails>> getProductDetailsByProductId(@PathVariable Integer productId) {
        List<ProductDetails> productDetails = productOptionsService.getProductDetailsByProductId(productId);
        if (productDetails.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(productDetails);
    }

    // 상품 재고수정페이지에서 사용되는 상세 정보(상품 아이디, 상품명, 색상 아이디, 색상명, 사이즈 아이디, 사이즈명, 재고수량)
    @GetMapping("/product-options/{id}/details")
    public ResponseEntity<ProductOptionDetails> getProductOptionDetailsByProductOptionsId(@PathVariable Integer id) {
        try {
            List<ProductOptionDetails> productOptionDetails = productOptionsService.getProductOptionDetailsByProductOptionsId(id);
            if (productOptionDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.status(HttpStatus.OK).body(productOptionDetails.get(0));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/product-options/add")
    public ResponseEntity<String> insertProductOption(@RequestBody ProductOptionRequest productOptionRequest) {
        if (productOptionRequest == null || productOptionRequest.getProductId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ProductId is required");
        }

        try {
            ProductOptions productOption = new ProductOptions();
            productOption.setProductId(productOptionRequest.getProductId());
            productOption.setColorId(productOptionRequest.getColorId());
            productOption.setSizeId(productOptionRequest.getSizeId());

            // 중복값 확인
            int count = productOptionsService.checkDuplicate(productOption);
            if (count > 0 ) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate productOption");
            }

            Stock stock = new Stock();
            stock.setQuantity(productOptionRequest.getQuantity());

            productOptionsService.addProductOptionAndStock(productOption, stock);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the request");
        }
    }

    @PutMapping("/product-options/{id}/edit")
    public ResponseEntity<String> updateProductOptionStock(@PathVariable Integer id, @RequestBody ProductOptionRequest productOptionRequest) {
        try {
            ProductOptions productOption = productOptionsService.findProductOptionById(id);
            if (productOption == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("ProductOption not found");
            }

            // product option의 color, size에 수정이 필요한 경우
            productOption.setProductId(productOptionRequest.getProductId());
            productOption.setColorId(productOptionRequest.getColorId());
            productOption.setSizeId(productOptionRequest.getSizeId());

            // 중복값 확인
            int count = productOptionsService.checkDuplicateExcludeId(productOption);
            if (count > 0 ) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("duplicate productOption");
            }
            productOptionsService.updateProductOption(productOption);

            // Stock 업데이트가 필요한 경우
            Stock stock = new Stock();
            stock.setProductOptionsId(id);
            stock.setQuantity(productOptionRequest.getQuantity());
            productOptionsService.updateStock(stock);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while processing the request");
        }
    }

    // 개별 삭제
    @DeleteMapping("/product-options/{id}/delete")
    public ResponseEntity<Void> deleteProductOption(@PathVariable Integer id) {
        try {
            productOptionsService.deleteProductOption(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 다중 삭제
    @DeleteMapping("/product-options/delete")
    public ResponseEntity<Void> deleteProductOptions(@RequestBody List<Integer> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }

            productOptionsService.deleteProductOptions(ids);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
