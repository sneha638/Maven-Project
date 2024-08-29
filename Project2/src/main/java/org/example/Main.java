package org.example;

import org.example.entity.Contestant;
import org.example.entity.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Quiz quiz = new Quiz();
        int choice;
        do {
            System.out.println("Quiz Application \n 1. Add Question \n 2. Start Quiz \n 3. View Results \n 4. View all contestants \n 5. Exit \n Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    quiz.addQuestion();
                    break;
                case 2:
                    quiz.startQuiz();
                    break;
                case 3:
                    quiz.viewResult();
                    break;
                case 4:
                    quiz.viewAllContestants();
                    break;
                case 5:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }


        } while (choice != 5);

    }
}

