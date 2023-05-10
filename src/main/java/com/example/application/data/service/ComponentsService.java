package com.example.application.data.service;

import com.example.application.data.entity.Components;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ComponentsService {

    private final ComponentsRepository repository;

    public ComponentsService(ComponentsRepository repository) {
        this.repository = repository;
    }

    public Optional<Components> get(Long id) {
        return repository.findById(id);
    }

    public Components update(Components entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<Components> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Components> list(Pageable pageable, Specification<Components> filter) {
        return repository.findAll(filter, pageable);
    }

    public int count() {
        return (int) repository.count();
    }


    public List<Components> findAllContacts(String filterText){
        if(filterText == null || filterText.isEmpty()){
            return  repository.findAll();
        } else{
            return repository.search(filterText);
        }
    }
}
