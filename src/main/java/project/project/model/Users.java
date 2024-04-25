package project.project.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class Users {
    private int id;
    private String userId;
    private String name;
    private String phoneNum;
    private String email;
    private int gender; // 0 : 남성, 1 : 여성
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
    private int state; // 0 : 탈퇴, 1 : 가입

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
