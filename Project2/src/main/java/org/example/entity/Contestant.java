package org.example.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "contestant")
public class Contestant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @Column(name = "Username", nullable = false, length = 255)
    private String username;
    @Column(name = "Marks")
    private int marks;

    public Contestant() {}
    public Contestant(String username, int marks) {
        this.username = username;
        this.marks = marks;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "Contestant{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", marks=" + marks +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
