package project.project.model;

import java.math.BigDecimal;
import java.util.Date;

public class Products {
    private Integer id;
    private String product_name;
    private Integer sub_category_id;
    private Integer discount_rate;
    private BigDecimal price;
    private BigDecimal discount_price;
    private Integer quantity;
    private BigDecimal accumulated_amount;
    private String product_code;
    private Integer delivery_country;
    private String product_description;
    private String thumnail_image_url;
    private String detail_image_url;
    private Date created_at;
    private Date updated_at;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public Integer getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(Integer sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public Integer getDiscount_rate() {
        return discount_rate;
    }

    public void setDiscount_rate(Integer discount_rate) {
        this.discount_rate = discount_rate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(BigDecimal discount_price) {
        this.discount_price = discount_price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAccumulated_amount() {
        return accumulated_amount;
    }

    public void setAccumulated_amount(BigDecimal accumulated_amount) {
        this.accumulated_amount = accumulated_amount;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public Integer getDelivery_country() {
        return delivery_country;
    }

    public void setDelivery_country(Integer delivery_country) {
        this.delivery_country = delivery_country;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getThumnail_image_url() {
        return thumnail_image_url;
    }

    public void setThumnail_image_url(String thumnail_image_url) {
        this.thumnail_image_url = thumnail_image_url;
    }

    public String getDetail_image_url() {
        return detail_image_url;
    }

    public void setDetail_image_url(String detail_image_url) {
        this.detail_image_url = detail_image_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }
}
