package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.project.requestParam.ProductsParam;
import project.project.s3.S3Uploader;
import project.project.model.Products;
import project.project.service.ProductsService;

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
    public ResponseEntity<ProductsParam> insertProduct(@ModelAttribute ProductsParam product,
                                                       @RequestParam("productDescription") MultipartFile product_description,
                                                       @RequestParam("thumnailImg") MultipartFile thumnail_image_url,
                                                       @RequestParam("detailImg") List<MultipartFile> detail_image_url) {

        try {
            // 이미지 S3 업로드
            String productDescriptionUrl = s3Uploader.uploadFiles(product_description, "image2024");
            String thumnailImgUrl = s3Uploader.uploadFiles(thumnail_image_url, "image2024");
            List<String> detailImgUrls = new ArrayList<>();
            for (MultipartFile detailImgFile : detail_image_url) {
                String detailImgUrl = s3Uploader.uploadFiles(detailImgFile, "image2024");
                detailImgUrls.add(detailImgUrl);
            }

            productsService.insertProduct(product, productDescriptionUrl, thumnailImgUrl, detailImgUrls);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
