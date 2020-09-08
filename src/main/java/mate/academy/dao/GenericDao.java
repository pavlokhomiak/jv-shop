package mate.academy.dao;

import java.util.List;
import java.util.Optional;

public interface GenericDao<T, V> {

    T create(T element);

    T update(T element);

    Optional<T> get(V id);

    List<T> getAll();

    boolean delete(V id);
}
