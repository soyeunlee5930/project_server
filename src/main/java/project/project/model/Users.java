package project.project.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Users {

    private Integer id;
    private String user_id;
    private String pwd;
    private String name;
    private String phone_num;
    private String email;
    private Integer gender; // 0 : 남성, 1 : 여성
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private Integer state; // 0 : 탈퇴, 1 : 가입

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_num() {
        return phone_num;
    }

    public void setPhone_num(String phone_num) {
        this.phone_num = phone_num;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
