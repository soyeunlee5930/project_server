package project.project.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.project.error.ErrorCode;
import project.project.exception.CustomException;
import project.project.requestParam.ProductsParam;
import project.project.s3.S3Uploader;
import project.project.model.Products;
import project.project.service.ProductsService;

import java.lang.reflect.Type;
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

    @GetMapping("/products/{id}")
    public ResponseEntity<Products> getProductById(@PathVariable Integer id) {
        Products product = productsService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok().body(product);
        } else {
            throw new CustomException("존재하지 않는 상품입니다.", ErrorCode.NOT_FOUND);
        }
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
            throw new CustomException("필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.", ErrorCode.INVALID_PARAMS);
        }
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id, @ModelAttribute ProductsParam product,
                                                @RequestParam(value = "productDescription", required = false) MultipartFile productDescription,
                                                @RequestParam(value = "thumnailImg", required = false) MultipartFile thumnailImageUrl,
                                                @RequestParam(value = "detailImg", required = false) List<MultipartFile> detailImageUrl) {

        try {
            Products existingProduct = productsService.getProductById(id);
            if (existingProduct == null) { // 일치하는 상품 아이디가 없는 경우
                throw new CustomException("존재하지 않는 상품입니다.", ErrorCode.NOT_FOUND);
            }

            // 이미지 업로드 및 URL 생성
            String productDescriptionUrl = null;
            String thumnailImgUrl = null;
            List<String> detailImgUrls = null;

            if (productDescription != null) {// 브라우저에서 새로 보낸 파일이 있을 때
                // 기존 S3에 업로드된 이미지 삭제처리(DB에 저장된 값을 이용하여 key값 추출)
                String productDescriptionKey = existingProduct.getProductDescription().substring(existingProduct.getProductDescription().indexOf("image2024/"));
                s3Uploader.fileDelete(productDescriptionKey);

                // 새 이미지 S3 업로드
                productDescriptionUrl = s3Uploader.uploadFiles(productDescription, "image2024");
                existingProduct.setProductDescription(productDescriptionUrl);
            } else {// 브라우저에서 보낸 파일이 없을 때, 기존 DB값 유지
                productDescriptionUrl = existingProduct.getProductDescription();
                existingProduct.setProductDescription(productDescriptionUrl);
            }

            if (thumnailImageUrl != null) {
                String thumnailImgUrlKey = existingProduct.getThumnailImageUrl().substring(existingProduct.getThumnailImageUrl().indexOf("image2024/"));
                s3Uploader.fileDelete(thumnailImgUrlKey);

                thumnailImgUrl = s3Uploader.uploadFiles(thumnailImageUrl, "image2024");
                existingProduct.setThumnailImageUrl(thumnailImgUrl);
            } else {
                thumnailImgUrl = existingProduct.getThumnailImageUrl();
                existingProduct.setThumnailImageUrl(thumnailImgUrl);
            }

            if (detailImageUrl != null && !detailImageUrl.isEmpty()) {
                // 브라우저에서 새로 보낸 파일이 있는 경우, DB에 저장된 값은 String 타입이므로 Gson을 사용하여 다시 List<String>으로 변환 필요
                Gson gson = new Gson();
                Type listType = new TypeToken<List<String>>() {}.getType();
                List<String> existingDetailImgUrls = gson.fromJson(existingProduct.getDetailImageUrl(), listType);
                if (existingDetailImgUrls != null && !existingDetailImgUrls.isEmpty()) {
                    for (String imageUrl : existingDetailImgUrls) {
                        String detailImageUrlKey = imageUrl.substring(imageUrl.indexOf("image2024/"));
                        s3Uploader.fileDelete(detailImageUrlKey);
                    }
                }
                
                detailImgUrls = new ArrayList<>();
                for (MultipartFile detailImgFile : detailImageUrl) {
                    String detailImgUrl = s3Uploader.uploadFiles(detailImgFile, "image2024");
                    detailImgUrls.add(detailImgUrl);

                    // List<String> 타입인 detailImgUrls를 Gson을 사용하여 String 타입으로 변환(JSON 문자열)
                    String detailImgUrlsToString = gson.toJson(detailImgUrls);
                    existingProduct.setDetailImageUrl(detailImgUrlsToString);
                }
            } else {
                // 이전 값들을 유지하기 위해 DB에 저장된 값을 그대로 설정, 타입이 맞지 않으므로 Gson 사용해서 역직렬화
                Gson gson = new Gson();
                String detailImgUrlsToString = existingProduct.getDetailImageUrl();
                Type listType = new TypeToken<List<String>>() {}.getType();
                detailImgUrls = gson.fromJson(existingProduct.getDetailImageUrl(), listType);

            }

            existingProduct.setProductName(product.getProductName());
            existingProduct.setCategoryId(product.getCategoryId());
            existingProduct.setDiscountRate(product.getDiscountRate());
            existingProduct.setPrice(product.getPrice());
            existingProduct.setDiscountPrice(product.getDiscountPrice());
            existingProduct.setQuantity(product.getQuantity());
            existingProduct.setAccumulatedAmount(product.getAccumulatedAmount());
            existingProduct.setProductCode(product.getProductCode());
            existingProduct.setDeliveryCountry(product.getDeliveryCountry());
            existingProduct.setUpdatedAt(LocalDateTime.now());

            productsService.updateProduct(existingProduct);

            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            throw new CustomException("필수데이터 누락, 또는 형식과 다른 데이터를 요청하셨습니다.", ErrorCode.INVALID_PARAMS);
        }
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            Products product = productsService.getProductById(id);
            if (product == null) {
                throw new CustomException("존재하지 않는 상품입니다.", ErrorCode.NOT_FOUND);
            }

            // S3 업로드 이미지파일 삭제처리
            // 1. 상품 설명 이미지
            String productDescriptionkey = product.getProductDescription().substring(product.getProductDescription().indexOf("image2024/"));
            s3Uploader.fileDelete(productDescriptionkey);

            // 2. 상품 대표 이미지
            String thumnailImgUrlKey = product.getThumnailImageUrl().substring(product.getThumnailImageUrl().indexOf("image2024/"));
            s3Uploader.fileDelete(thumnailImgUrlKey);

            // 3. 상품 상세 이미지
            Gson gson = new Gson();
            Type listType = new TypeToken<List<String>>() {}.getType();
            List<String> detailImgUrls = gson.fromJson(product.getDetailImageUrl(), listType);
            if (detailImgUrls != null && !detailImgUrls.isEmpty()) {
                for (String imageUrl : detailImgUrls) {
                    String detailImageUrlKey = imageUrl.substring(imageUrl.indexOf("image2024/"));
                    s3Uploader.fileDelete(detailImageUrlKey);
                }
            }

            // DB 데이터 삭제처리
            productsService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
