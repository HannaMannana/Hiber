package ru.clevertec.house.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.entity.PersonEntity;
import ru.clevertec.house.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class PersonRepoImpl implements PersonRepository {

    private static final String FIND_ALL = "from " + PersonEntity.class.getName();

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Optional<PersonEntity> findById(Long id) {

        return Optional.ofNullable(manager.find(PersonEntity.class, id));
    }

    @Override
    public Optional<PersonEntity> findByUUID(UUID uuid) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<PersonEntity> query = criteriaBuilder.createQuery(PersonEntity.class);

        Root<PersonEntity> from = query.from(PersonEntity.class);

        query.select(from).where(criteriaBuilder.equal(from.get("uuid"), uuid));

        return Optional.ofNullable(manager.createQuery(query).getSingleResult());
    }

    @Override
    public List<PersonEntity> findAll() {

        return manager.createQuery(FIND_ALL, PersonEntity.class).getResultList();
    }

    @Override
    public Optional<PersonEntity> save(PersonEntity entity) {
        manager.persist(entity);
        return Optional.ofNullable(entity);

    }

    @Override
    public Optional<PersonEntity> update(PersonEntity entity) {

        return Optional.ofNullable(manager.merge(entity));
    }

    @Override
    public void delete(UUID uuid) {

        Optional<PersonEntity> person = findByUUID(uuid);
        manager.remove(person.orElseThrow());

    }
}
