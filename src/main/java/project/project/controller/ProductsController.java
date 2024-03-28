package project.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.project.requestParam.ProductsParam;
import project.project.s3.S3Uploader;
import project.project.model.Products;
import project.project.service.ProductsParamService;
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

    @Autowired
    private ProductsParamService productsParamService;

    @GetMapping("/products")
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return ResponseEntity.ok().body(products);
    }

    @PostMapping("/products/add")
    public ResponseEntity<ProductsParam> insertProduct(@ModelAttribute ProductsParam product,
                                                       @RequestParam("productDescription") MultipartFile productDescription,
                                                       @RequestParam("thumnailImg") MultipartFile thumnailImageUrl,
                                                       @RequestParam("detailImg") List<MultipartFile> detailImageUrl) {

        try {
            ProductsParam productInfo = productsParamService.insertProductParam(product);
            // 이미지 S3 업로드
            String productDescriptionUrl = s3Uploader.uploadFiles(productDescription, "image2024");
            String thumnailImgUrl = s3Uploader.uploadFiles(thumnailImageUrl, "image2024");
            List<String> detailImgUrls = new ArrayList<>();
            for (MultipartFile detailImgFile : detailImageUrl) {
                String detailImgUrl = s3Uploader.uploadFiles(detailImgFile, "image2024");
                detailImgUrls.add(detailImgUrl);
            }

            productsService.insertProduct(productInfo, productDescriptionUrl, thumnailImgUrl, detailImgUrls);
            return ResponseEntity.status(HttpStatus.CREATED).body(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
