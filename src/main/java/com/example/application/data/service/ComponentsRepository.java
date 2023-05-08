package com.example.application.data.service;

import com.example.application.data.entity.Components;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ComponentsRepository extends JpaRepository<Components, Long>, JpaSpecificationExecutor<Components> {

}
