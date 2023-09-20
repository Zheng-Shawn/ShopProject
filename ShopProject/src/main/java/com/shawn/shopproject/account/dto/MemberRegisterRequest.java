package com.shawn.shopproject.account.dto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class MemberRegisterRequest {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private String tel;
    @NotBlank
    private String validcode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }
}
