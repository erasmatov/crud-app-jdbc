package net.erasmatov.crudapp.repository;

import java.util.List;

public interface GenericRepository<T, ID> {
    List<T> getAll();

    T getById(ID id);

    void deleteById(ID id);

    T save(T t);

    T update(T t);
}
