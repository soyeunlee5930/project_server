package project.project.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.project.requestParam.ProductsParam;
import project.project.s3.S3Uploader;
import project.project.model.Products;
import project.project.service.ProductsService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @Autowired
    private S3Uploader s3Uploader;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/products/add")
    public ResponseEntity<Products> insertProduct(@ModelAttribute ProductsParam product,
                                                       @RequestParam("productDescription") MultipartFile productDescription,
                                                       @RequestParam("thumnailImg") MultipartFile thumnailImageUrl,
                                                       @RequestParam("detailImg") List<MultipartFile> detailImageUrl) {

        try {
            // 이미지 S3 업로드
            String productDescriptionUrl = s3Uploader.uploadFiles(productDescription, "image2024");
            String thumnailImgUrl = s3Uploader.uploadFiles(thumnailImageUrl, "image2024");
            List<String> detailImgUrls = new ArrayList<>();
            for (MultipartFile detailImgFile : detailImageUrl) {
                String detailImgUrl = s3Uploader.uploadFiles(detailImgFile, "image2024");
                detailImgUrls.add(detailImgUrl);
            }

            // List<String> 타입인 detailImgUrls를 String 타입으로 변환(JSON)
            Gson gson = new Gson();
            String detailImgUrlsToString = gson.toJson(detailImgUrls);

            // Products 객체 생성 및 데이터 넣기
            Products addProduct = new Products();
            addProduct.setProductName(product.getProductName());
            addProduct.setCategoryId(product.getCategoryId());
            addProduct.setDiscountRate(product.getDiscountRate());
            addProduct.setPrice(product.getPrice());
            addProduct.setDiscountPrice(product.getDiscountPrice());
            addProduct.setQuantity(product.getQuantity());
            addProduct.setAccumulatedAmount(product.getAccumulatedAmount());
            addProduct.setProductCode(product.getProductCode());
            addProduct.setDeliveryCountry(product.getDeliveryCountry());
            addProduct.setProductDescription(productDescriptionUrl);
            addProduct.setThumnailImageUrl(thumnailImgUrl);
            addProduct.setDetailImageUrl(detailImgUrlsToString);
            addProduct.setCreatedAt(LocalDateTime.now());
            addProduct.setUpdatedAt(LocalDateTime.now());

            // DB에 addProduct 추가
            productsService.insertProduct(addProduct);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
