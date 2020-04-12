package com.example.demo.tests;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RepositoryRestResource
@Transactional
public interface BookRepositoryTest2 extends JpaRepository<Book,Long>, JpaSpecificationExecutor {


    @Transactional
    @Query("SELECT books FROM Book books where books.name like %:keyword% and books.isAvailable = true ")
    public List<Book> search(@Param("keyword") String keyword);
}
