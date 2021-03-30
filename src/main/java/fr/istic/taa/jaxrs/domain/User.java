package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.istic.taa.jaxrs.utils.Hashing;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    @JsonIgnore
    private List<Board> boards;
    @JsonIgnore
    private List<Card> assignedCards;
    @JsonIgnore
    private List<Team> teams;
    @JsonIgnore
    private List<Team> managedTeams;
    @JsonIgnore
    private List<Tag> tags;

    public User() {

    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    @ManyToMany(mappedBy = "assignees")
    public List<Card> getAssignedCards() {
        return assignedCards;
    }

    public void setAssignedCards(List<Card> assignedCards) {
        this.assignedCards = assignedCards;
    }

    @ManyToMany(mappedBy = "members")
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    @OneToMany(mappedBy = "manager")
    public List<Team> getManagedTeams() {
        return managedTeams;
    }

    public void setManagedTeams(List<Team> managedTeams) {
        this.managedTeams = managedTeams;
    }

    @OneToMany(mappedBy = "owner")
    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
