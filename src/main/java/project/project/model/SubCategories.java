package project.project.model;

public class SubCategories {

    private Integer id;

    private String sub_category_name;

    private Integer category_id;

    private String category_name; // category_name 추가

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) { // N:1 조인에서 N쪽에 처리할 데이터를 추가
        this.category_name = category_name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSub_category_name() {
        return sub_category_name;
    }

    public void setSub_category_name(String sub_category_name) {
        this.sub_category_name = sub_category_name;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }
}
