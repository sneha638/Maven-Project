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

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/quizdb", "root", "Sneha123*");
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
        SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.persist(q);
        session.getTransaction().commit();
        System.out.println("Sample question added to the database.");

    }

    public void startQuiz() {
        String username;
        int marks = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        username = scanner.nextLine();

        try (Connection conn = getConnection()) {
            if (conn == null) {
                System.out.println("Failed to make connection!");
                return;
            }
            PreparedStatement checkStmt = conn.prepareStatement("SELECT COUNT(*) FROM contestants WHERE username = ?");
            checkStmt.setString(1, username);
            ResultSet checkRs = checkStmt.executeQuery();
            checkRs.next();
            if (checkRs.getInt(1) > 0) {
                System.out.println("Sorry " + username + ", you have already attended the quiz.");
                return;
            }
            System.out.println("Hi " + username + ", let's start the Quiz.\nGood Luck!");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM questions ORDER BY RAND() LIMIT 5");

            while (rs.next()) {
                do {
                    System.out.println(rs.getString("question") + "\n1. " + rs.getString("option1") + "\n2. " + rs.getString("option2") + "\n3. " + rs.getString("option3") + "\nEnter your answer (1/2/3): ");
                    int answer = scanner.nextInt();
                    if (answer == 1 || answer == 2 || answer == 3) {
                        if (answer == rs.getInt("correct_option")) {
                            marks++;
                        }
                        break;
                    } else {
                        System.out.println("Invalid option, please try again.");
                    }
                } while (true);
            }
            Contestant contestant = new Contestant(username, marks);
            SessionFactory sf = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();
            session.persist(contestant);
            session.getTransaction().commit();
            System.out.println("Quiz completed \n You scored: "  + marks);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void viewResult() {
        String username;
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your username: ");
        username = scanner.nextLine();

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT marks FROM contestants WHERE username = ?")) {

            if (conn == null) {
                System.out.println("Failed to make connection!");
                return;
            }

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                System.out.println(username + ", your score is: " + rs.getInt("marks"));
            } else {
                System.out.println("No results found for user: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public void viewAllContestants() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT username, marks FROM contestants")) {

            if (conn == null) {
                System.out.println("Failed to make connection!");
                return;
            }

            System.out.println("Result of all contestants:");
            while (rs.next()) {
                System.out.println("Username: " + rs.getString("username") + ", Marks: " + rs.getInt("marks"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}










