package pl.kielce.tu.repository;


import pl.kielce.tu.model.Stadium;

import java.util.List;

public interface StadiumRepository {

    List<Stadium> findAll();

    Stadium find(Long id);

    List<Stadium> find(Stadium stadium);

    void delete(Long id);

    Stadium createOrUpdate(Stadium entity);
}
