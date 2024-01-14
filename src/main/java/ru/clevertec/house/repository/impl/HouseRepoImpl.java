package ru.clevertec.house.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.house.entity.HouseEntity;
import ru.clevertec.house.repository.HouseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Transactional
public class HouseRepoImpl implements HouseRepository {

    private static final String FIND_ALL = "from " + HouseEntity.class.getName();

    @PersistenceContext
    private EntityManager manager;


    @Override
    public Optional<HouseEntity> findById(Long id) {
        return Optional.ofNullable(manager.find(HouseEntity.class, id));
    }

    @Override
    public Optional<HouseEntity> findByUUID(UUID uuid) {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<HouseEntity> query = criteriaBuilder.createQuery(HouseEntity.class);

        Root<HouseEntity> from = query.from(HouseEntity.class);

        query.select(from).where(criteriaBuilder.equal(from.get("uuid"), uuid));

        return Optional.ofNullable(manager.createQuery(query).getSingleResult());
    }

    @Override
    public List<HouseEntity> findAll() {
        return manager.createQuery(FIND_ALL, HouseEntity.class).getResultList();
    }

    @Override
    public Optional<HouseEntity> save(HouseEntity entity) {
        manager.persist(entity);
        return Optional.ofNullable(entity);

    }

    @Override
    public Optional<HouseEntity> update(HouseEntity entity) {

        return Optional.ofNullable(manager.merge(entity));
    }

    @Override
    public void delete(UUID uuid) {

        Optional<HouseEntity> house = findByUUID(uuid);
        manager.remove(house.orElseThrow());

    }
}
