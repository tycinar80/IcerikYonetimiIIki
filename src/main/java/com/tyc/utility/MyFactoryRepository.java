package com.tyc.utility;

import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transaction;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MyFactoryRepository<T, ID> implements IMyCrud<T, ID> {
    private Session session;
    private EntityManager entityManager;
    private CriteriaBuilder criteriaBuilder;
    private Transaction transaction;
    private T t;

    public MyFactoryRepository(T t) {
        entityManager = HibernateUtility.getSessionFactory().createEntityManager();
        criteriaBuilder = entityManager.getCriteriaBuilder();
        this.t = t;
    }

    public void openSession() {
        session = HibernateUtility.getSessionFactory().openSession();
        session.getTransaction().begin();
    }

    public void closeSucces() {
        session.getTransaction().commit();
        session.close();
    }

    public void closeError() {
        session.getTransaction().rollback();
        session.close();
    }

    /**
     * kayit edildikten sonra id alan entity nesnesi geri dönderilir.
     *
     * @param <S>
     * @param entity
     * @return
     */
    @Override
    public <S extends T> S save(S entity) {
        try {
            openSession();
            session.save(entity);
            closeSucces();
            return entity;
        } catch (Exception e) {
            closeError();
            throw e;
        }
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        try {
            openSession();
            entities.forEach(entity -> {
                session.save(entity);
            });
            closeSucces();
            return entities;
        } catch (Exception e) {
            closeError();
            throw e;
        }
    }

    @Override
    public void deleteById(ID id) {
        T deleteEntity = null;
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // root.get("ad") gibi tek tek yapilabilir
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); // "id" degisebilir
        List<T> result = entityManager.createQuery(criteriaQuery).getResultList();

        if (!result.isEmpty()) {
            deleteEntity = (T) result.get(0);
            try {
                openSession();
                session.delete(deleteEntity);
                closeSucces();
            } catch (Exception e) {
                closeError();
            }
        }
    }

    @Override
    public void delete(T entity) {
        try {
            openSession();
            session.delete(entity);
            closeSucces();
        } catch (Exception e) {
            closeError();
            throw e;
        }
    }

    @Override
    public Optional<T> findById(ID id) {
        // select * from tblmusteri where id = ?
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root); // root.get("ad") gibi tek tek yapilabilir
        criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); // "id" degisebilir
        // T result = entityManager.createQuery(criteriaQuery).getSingleResult(); veya
        List<T> result = entityManager.createQuery(criteriaQuery).getResultList();
        if (!result.isEmpty())
            return Optional.of(result.get(0));
        else
            return Optional.empty();
    }

    @Override
    public boolean existById(ID id) {
        try {
            CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
            Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
            criteriaQuery.select(root); // root.get("ad") gibi tek tek yapilabilir
            criteriaQuery.where(criteriaBuilder.equal(root.get("id"), id)); // "id" degisebilir
            // T result = entityManager.createQuery(criteriaQuery).getSingleResult(); veya
            List<T> result = entityManager.createQuery(criteriaQuery).getResultList();
            return !result.isEmpty(); // Eger bos ise deger true doner fakat !(degil) kullandigimiz icin flase olarak
            // doner
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<T> findAll() {
        // select * from tblmusteri
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
//		List<T> result = entityManager.createQuery(criteriaQuery).getResultList();
//		return result;
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public List<T> findByColumnAndValue(String column, Object value) {
        // select * from tblxxx where ? = ?
        CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(t.getClass());
        Root<T> root = (Root<T>) criteriaQuery.from(t.getClass());
        criteriaQuery.select(root);
        criteriaQuery.where(criteriaBuilder.equal(root.get(column), value));
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    /**
     * Musteri mst = new Musteri(); mst.setAd("a"); mst.setAdres("Ankara");
     * findByEntity(mst);
     *
     * where icin kolon adi = degiskenimizin adi deger = degiskenin degeri
     *
     * @param entity
     * @return
     */
    @Override
    public List<T> findByEntity(T entity) {
        List<T> result = null;
        Class clss = entity.getClass();
        Field[] field = clss.getDeclaredFields(); // Bir nesne(sinif) icindeki degiskenleri alir
        try {
            // select * from tblxxx where ? = ? and ? = ? ...
            CriteriaQuery<T> criteriaQuery = (CriteriaQuery<T>) criteriaBuilder.createQuery(entity.getClass());
            Root<T> root = (Root<T>) criteriaQuery.from(entity.getClass());
            criteriaQuery.select(root);
            List<Predicate> list = new ArrayList<Predicate>();
            for (int i = 0; i < field.length; i++) {
                /**
                 * DIKKAT!! reflect ile alanlari okurken okumaya acmaliyiz
                 */
                field[i].setAccessible(true);
                if (field[i].get(entity) != null && field[i].getName().equals("id")) {
                    if (field[i].getType().isAssignableFrom(String.class)) // Cektigimiz degiskenin datatype'nin String
                        // olup olmadigini kontrol ediyoruz
                        list.add(criteriaBuilder.like(root.get(field[i].getName()), "%" + field[i].get(entity) + "%"));
                    else
                        list.add(criteriaBuilder.equal(root.get(field[i].getName()), field[i].get(entity)));
                }
            }
            criteriaQuery.where(list.toArray(new Predicate[] {})); // new Predicate(), new{323, 23, 56}
            result = entityManager.createQuery(criteriaQuery).getResultList();

            // criteriaQuery.where(criteriaBuilder.equal(root.get("id"), 323)); where 'in
            // icine predicate denir
        } catch (Exception e) {
            System.out.println("findByEntity Error...: " + e.getLocalizedMessage());
        }

        return null;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CriteriaBuilder getCriteriaBuilder() {
        return criteriaBuilder;
    }

    public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
        this.criteriaBuilder = criteriaBuilder;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
