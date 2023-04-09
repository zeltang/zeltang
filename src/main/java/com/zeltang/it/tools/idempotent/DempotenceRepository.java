package com.zeltang.it.tools.idempotent;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public class DempotenceRepository implements CrudRepository<String, String> {
    @Override
    public <S extends String> S save(S s) {
        return null;
    }

    @Override
    public <S extends String> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<String> findById(String s) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(String s) {
        return false;
    }

    @Override
    public Iterable<String> findAll() {
        return null;
    }

    @Override
    public Iterable<String> findAllById(Iterable<String> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(String s) {

    }

    @Override
    public void delete(String dempotenceVo) {

    }

    @Override
    public void deleteAll(Iterable<? extends String> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
