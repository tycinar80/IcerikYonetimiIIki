package com.tyc.main;

import com.tyc.entity.*;
import com.tyc.repository.QuestionRepository;
import com.tyc.repository.SubjectDetailRepository;
import com.tyc.service.*;

import java.time.LocalDate;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        loadData();
        SubjectDetailRepository subjectDetailRepository = new SubjectDetailRepository();
//        subjectDetailRepository.enCokIcerikUretenEditor();
//        QuestionRepository questionRepository = new QuestionRepository();
//        questionRepository.soruSorulanDersler();
        subjectDetailRepository.birdenFazlaDersIcinIcerikUretenEditor();

    }

    public static void loadData() {
        AnswerService answerService = new AnswerService();
        LessonService lessonService = new LessonService();
        QuestionService questionService = new QuestionService();
        SubjectDetailService subjectDetailService = new SubjectDetailService();
        SubjectService subjectService = new SubjectService();
        UserService userService = new UserService();

        User editor = new User("Taha", "ÇINAR", ERole.Editor, LocalDate.now(), LocalDate.now(), true);
        User editor2 = new User("Yasin", "ÇINAR", ERole.Editor, LocalDate.now(), LocalDate.now(), true);
        User editor3 = new User("İpek", "ÇINAR", ERole.Editor, LocalDate.now(), LocalDate.now(), true);
        User user = new User("Şakir", "GÖL", ERole.User, LocalDate.now(), LocalDate.now(), true);
        User user2 = new User("Mahmut", "ÇAYCI", ERole.User, LocalDate.now(), LocalDate.now(), true);

        Lesson lesson = new Lesson("Java", "Java Programlama Temelleri", LocalDate.now(), LocalDate.now(), true);
        Lesson lesson2 = new Lesson("C++", "C++ Programlama Temelleri", LocalDate.now(), LocalDate.now(), true);

        Subject subject = new Subject("Java OOP", lesson, LocalDate.now(), LocalDate.now(), true);
        Subject subject2 = new Subject("Stream Api", lesson, LocalDate.now(), LocalDate.now(), true);
        Subject subject3 = new Subject("Collections", lesson, LocalDate.now(), LocalDate.now(), true);
        Subject subject4 = new Subject("C++ Oyun Programlama", lesson2, LocalDate.now(), LocalDate.now(), true);
        Subject subject5 = new Subject("Multi Threading", lesson2, LocalDate.now(), LocalDate.now(), true);

        SubjectDetail subjectDetail = new SubjectDetail(subject, editor2, lesson, "OOP Giriş", "Soyutlama", "Nesnenin soyutlanması", LocalDate.now(), LocalDate.now(), true);
        SubjectDetail subjectDetail2 = new SubjectDetail(subject2, editor2, lesson, "Stream Api Kullanımı", "Stream Api Metotlarının Kullanımı", "Api metotları", LocalDate.now(), LocalDate.now(), true);
        SubjectDetail subjectDetail3 = new SubjectDetail(subject3, editor, lesson, "Listler", "Listlerin Kullanımı", "En çok kullanılan türü ArrayListler'dir", LocalDate.now(), LocalDate.now(), true);
        SubjectDetail subjectDetail4 = new SubjectDetail(subject4, editor3, lesson2, "Oyun Programlama Temelleri", "C++ ile Oyun Programlamaya Giriş", "C++ ile oyun programlama temellerini öğreneceğiz", LocalDate.now(), LocalDate.now(), true);
        SubjectDetail subjectDetail5 = new SubjectDetail(subject5, editor2, lesson2, "C++ MultiThread", "MultiThread Kullanımı", "C++'da multi threading ...", LocalDate.now(), LocalDate.now(), true);

        Question question = new Question(subjectDetail, user, lesson, "OOP", "Soru-1", LocalDate.now(), LocalDate.now(), true);
        Question question2 = new Question(subjectDetail, user, lesson, "OOP", "Soru-2", LocalDate.now(), LocalDate.now(), true);
        Question question3 = new Question(subjectDetail2, user2, lesson, "Stream Api", "Soru-3", LocalDate.now(), LocalDate.now(), true);
        Question question4 = new Question(subjectDetail3, user, lesson2, "Collections", "Soru-4", LocalDate.now(), LocalDate.now(), true);
        Question question5 = new Question(subjectDetail5, user2, lesson2, "Multi Threading", "Soru-5", LocalDate.now(), LocalDate.now(), true);

        Answer answer = new Answer(question, editor2, "Cevap-1", LocalDate.now(), LocalDate.now(), true);
        Answer answer2 = new Answer(question2, editor2, "Cevap-2", LocalDate.now(), LocalDate.now(), true);
        Answer answer3 = new Answer(question3, editor2, "Cevap-3", LocalDate.now(), LocalDate.now(), true);
        Answer answer4 = new Answer(question4, editor2, "Cevap-4", LocalDate.now(), LocalDate.now(), true);
        Answer answer5 = new Answer(question5, editor, "Cevap-5", LocalDate.now(), LocalDate.now(), true);

        userService.save(List.of(editor, editor2, editor3, user, user2));
        lessonService.save(List.of(lesson, lesson2));
        subjectService.save(List.of(subject, subject2, subject3, subject4, subject5));
        subjectDetailService.save(List.of(subjectDetail, subjectDetail2, subjectDetail3, subjectDetail4, subjectDetail5));
        questionService.save(List.of(question, question2, question3, question4, question5));
        answerService.save(List.of(answer, answer2, answer3, answer4, answer5));

    }
}
