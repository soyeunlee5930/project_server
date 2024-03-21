package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.project.S3.S3Uploader;
import project.project.model.Products;
import project.project.requestParam.ProductsParam;
import project.project.service.ProductsService;

import java.io.IOException;
import java.math.BigDecimal;
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

    @PostMapping("/product/add")
    public ResponseEntity<ProductsParam> insertProduct(@RequestBody ProductsParam product,
                                                       @RequestParam("product_description") MultipartFile product_description,
                                                       @RequestParam("thumnail_image_url") MultipartFile thumnail_image_url,
                                                       @RequestParam("detail_image_url") MultipartFile[] detail_image_url) {

        // 이미지 업로드
        String productDescriptionUrl;
        String thumnailImgUrl;
        String[] detailImgUrls = new String[detail_image_url.length];
        try {
            productDescriptionUrl = s3Uploader.uploadFiles(product_description, "image2024");
            thumnailImgUrl = s3Uploader.uploadFiles(thumnail_image_url, "image2024");
            for (int i = 0; i < detail_image_url.length; i++) {
                detailImgUrls[i] = s3Uploader.uploadFiles(detail_image_url[i], "image2024");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // 여기서 받은 데이터와 이미지 URL들을 이용하여 상품 저장 등의 로직 수행
        productsService.insertProduct(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);




//        try {
//            s3Uploader.uploadFiles(multipartFile, "image2024");
//            s3Uploader.uploadFiles(multipartFile, "image2024");
//            s3Uploader.uploadFiles(multipartFile, "image2024");
//            productsService.insertProduct(product);
//
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
