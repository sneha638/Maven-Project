package org.example;

import org.example.entity.Question;
import org.example.entity.Contestant;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Quiz {

        private SessionFactory sessionFactory;
        public Quiz() {
            sessionFactory  = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }
    public void addQuestion() {
        Scanner scanner = new Scanner(System.in);
        int correctOption = 0;
        System.out.print("Enter the question: ");
        String question = scanner.nextLine();
        System.out.print("Enter option 1: ");
        String option1 = scanner.nextLine();
        System.out.print("Enter option 2: ");
        String option2 = scanner.nextLine();
        System.out.print("Enter option 3: ");
        String option3 = scanner.nextLine();
        System.out.print("Enter the correct option (1/2/3): ");
        correctOption = Integer.parseInt(scanner.nextLine());

        Question q = new Question(question, option1, option2, option3, correctOption);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(q);
        session.getTransaction().commit();
        System.out.println("Sample question added to the database.");

    }
    public void startQuiz() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        Session session = sessionFactory.openSession();
            Query<Long> checkQuery = session.createQuery("SELECT COUNT(c) FROM Contestant c WHERE c.username = :username", Long.class);
            checkQuery.setParameter("username", username);
            Long count = checkQuery.uniqueResult();

            if (count > 0) {
                System.out.println("Sorry " + username + ", you have already attended the quiz.");
                return;
            } else {
                System.out.println("Hi " + username + ", let's start the Quiz.\nGood Luck!");
            }

            // Fetch random questions
            Query<Question> questionQuery = session.createQuery("FROM Question ORDER BY RAND()", Question.class);
            questionQuery.setMaxResults(5);
            List<Question> questions = questionQuery.list();
            int marks = 0;

            for (Question question : questions) {
                int answer;
                do {
                    System.out.println(question.getQuestion() + "\n1. " + question.getOption1() + "\n2. " + question.getOption2() + "\n3. " + question.getOption3());
                    System.out.print("Enter your answer (1/2/3): ");
                    answer = scanner.nextInt();
                    if (answer == 1 || answer == 2 || answer == 3) {
                        if (answer == question.getCorrectOption()) {
                            marks++;
                        }
                        break;
                    } else {
                        System.out.println("Invalid option, please try again.");
                    }
                } while (true);
            }

            Transaction transaction = session.beginTransaction();
            Contestant contestant = new Contestant(username, marks);
            session.persist(contestant);
            transaction.commit();
            System.out.println("Quiz completed.\nYou scored: " + marks);

    }

    public void viewResult() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        Session session = sessionFactory.openSession();
            Query<Contestant> query = session.createQuery("FROM Contestant WHERE username = :username", Contestant.class);
            query.setParameter("username", username);
            Contestant contestant = query.uniqueResult();

            if (contestant != null) {
                System.out.println(username + ", your score is: " + contestant.getMarks());
            } else {
                System.out.println("No results found for user: " + username);
            }

    }

    public void viewAllContestants() {
       Session session = sessionFactory.openSession();
            Query<Contestant> query = session.createQuery("FROM Contestant", Contestant.class);
            List<Contestant> contestants = query.list();

            System.out.println("Results of all contestants:");
            contestants.forEach(contestant ->
                    System.out.println("Username: " + contestant.getUsername() + ", Marks: " + contestant.getMarks()));

    }
}










