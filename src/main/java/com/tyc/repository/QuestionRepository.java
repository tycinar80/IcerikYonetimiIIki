package com.tyc.repository;

import com.tyc.entity.Answer;
import com.tyc.entity.Lesson;
import com.tyc.entity.Question;
import com.tyc.utility.MyFactoryRepository;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class QuestionRepository extends MyFactoryRepository<Question, Long> {
    public QuestionRepository() {
        super(new Question());
    }

    public void soruSorulanDersler() {
        CriteriaQuery<Tuple> criteriaQuery = getCriteriaBuilder().createQuery(Tuple.class);
        Root<Question> root = criteriaQuery.from(Question.class);
//        Root<Lesson> lessonRoot = criteriaQuery.from(Lesson.class);

        criteriaQuery.multiselect(root.get("lesson").get("id"), root.get("lesson").get("name"), getCriteriaBuilder().count(root.get("lesson").get("id"))).groupBy(root.get("lesson").get("id"),  root.get("lesson").get("name"));
        TypedQuery<Tuple> typedQuery = getEntityManager().createQuery(criteriaQuery);
        List<Tuple> list = typedQuery.getResultList();
        list.forEach(x -> {
            System.out.println("Id: " + x.get(0) + " - Ders: " + x.get(1) + " - Soru Sayisi: " + x.get(2));
        });
//        System.out.println(list.size());
    }
}


