package project.project.requestParam;

import java.time.LocalDateTime;

public class ProductsParam {

    // @ModelAttribute에 담을 컬럼만 작성, image관련 컬럼은 @RequestParam을 사용할 예정
    private int id;
    private String productName;
    private int categoryId;
    private int discountRate;
    private int price;
    private int discountPrice;
    private int quantity;
    private int accumulatedAmount;
    private String productCode;
    private int deliveryCountry;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(int discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAccumulatedAmount() {
        return accumulatedAmount;
    }

    public void setAccumulatedAmount(int accumulatedAmount) {
        this.accumulatedAmount = accumulatedAmount;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public int getDeliveryCountry() {
        return deliveryCountry;
    }

    public void setDeliveryCountry(int deliveryCountry) {
        this.deliveryCountry = deliveryCountry;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
