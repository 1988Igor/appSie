package com.example.application.data.service;

import com.example.application.data.entity.Components;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComponentsRepository extends JpaRepository<Components, Long>, JpaSpecificationExecutor<Components> {


    @Query("select c from Components c " +
            "where lower(c.filteredProducts) like lower(concat('%', :searchTerm, '%')) "
            )
    List<Components> search(@Param("searchTerm") String searchTerm);
}

