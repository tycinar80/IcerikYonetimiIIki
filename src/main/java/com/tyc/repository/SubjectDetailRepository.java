package com.tyc.repository;

import com.tyc.entity.Lesson;
import com.tyc.entity.Subject;
import com.tyc.entity.SubjectDetail;
import com.tyc.entity.User;
import com.tyc.service.UserService;
import com.tyc.utility.MyFactoryRepository;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class SubjectDetailRepository extends MyFactoryRepository<SubjectDetail, Long> {
    public SubjectDetailRepository() {
        super(new SubjectDetail());
    }

    public void enCokIcerikUretenEditor() {
        CriteriaQuery<Object[]> criteriaQuery = getCriteriaBuilder().createQuery(Object[].class);
        Root<SubjectDetail> root = criteriaQuery.from(SubjectDetail.class);

        criteriaQuery.multiselect(root.get("user").get("id"), getCriteriaBuilder().count(root.get("user").get("id"))).groupBy(root.get("user").get("id")).orderBy(getCriteriaBuilder().desc(getCriteriaBuilder().count(root.get("user").get("id"))));
        TypedQuery<Object[]> typedQuery = getEntityManager().createQuery(criteriaQuery);
        typedQuery.setMaxResults(1);
        Object[] object = typedQuery.getSingleResult();
        UserService userService = new UserService();
        User user = userService.findById(Long.parseLong(object[0].toString())).get();
        System.out.println("En Cok Icerik Ureten Kullanici");
        System.out.println("Id: " + object[0] + "\nIsim: " + user.getName()  + "\nIcerik Sayisi " + object[1]);
    }

    public void birdenFazlaDersIcinIcerikUretenEditor() {
        CriteriaQuery<Tuple> criteriaQuery = getCriteriaBuilder().createQuery(Tuple.class);
        Root<SubjectDetail> rootSubjectDetail = criteriaQuery.from(SubjectDetail.class);

        criteriaQuery.multiselect(rootSubjectDetail.get("user").get("name"), rootSubjectDetail.get("lesson").get("name"),
                        getCriteriaBuilder().count(rootSubjectDetail.get("lesson").get("id")))
                .groupBy(rootSubjectDetail.get("user").get("name"), rootSubjectDetail.get("lesson").get("name"));
        TypedQuery<Tuple> typedQuery = getEntityManager().createQuery(criteriaQuery);
        List<Tuple> list = typedQuery.getResultList();
        list.forEach(x -> {
            System.out.println(x.get(0) + " - " + x.get(1) + " - " + x.get(2));
        });
    }
}
