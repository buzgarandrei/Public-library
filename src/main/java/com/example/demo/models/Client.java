package com.example.demo.models;

import com.example.demo.utils.RoleEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Client extends User {

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_list_of_client",
            joinColumns = @JoinColumn(name = "id_client"),
            inverseJoinColumns = @JoinColumn(name = "id_book"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"id_client","id_book"})})
    private List<Book> bookList =  new ArrayList<>();

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }

    public void setRoleEnum() {
        this.roleEnum = RoleEnum.CLIENT;
    }
    public RoleEnum getRoleEnum() {
        return this.roleEnum;
    }
}
