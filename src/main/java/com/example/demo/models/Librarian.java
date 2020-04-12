package com.example.demo.models;

import com.example.demo.utils.RoleEnum;

import javax.persistence.*;

@Entity
public class Librarian extends User {

    public void setRoleEnum() {
        this.roleEnum = RoleEnum.LIBRARIAN;
    }
    public RoleEnum getRoleEnum() {
        return this.roleEnum;
    }
}
