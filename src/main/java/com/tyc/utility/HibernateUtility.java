package com.tyc.utility;


import com.tyc.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
    private static final SessionFactory SESSION_FACTORY = sessionFactoryHibernate();

    private static SessionFactory sessionFactoryHibernate() {
        try {
            Configuration configuration = new Configuration();
            // Entity classlarımızı buraya ekliyoruz
            configuration.addAnnotatedClass(Answer.class);
            configuration.addAnnotatedClass(Lesson.class);
            configuration.addAnnotatedClass(Question.class);
            configuration.addAnnotatedClass(Subject.class);
            configuration.addAnnotatedClass(SubjectDetail.class);
            configuration.addAnnotatedClass(User.class);
            SessionFactory factory = configuration.configure("hibernate.cfg.xml").buildSessionFactory();
            return factory;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}
