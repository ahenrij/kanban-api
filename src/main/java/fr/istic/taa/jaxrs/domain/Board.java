package fr.istic.taa.jaxrs.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Board implements Serializable {

    private long id;
    private String title;
    private String color; //Hex board color
    @Column(columnDefinition="tinyint(1) default 1")
    private boolean isPrivate;
    @JsonIgnore
    private User owner;
    @JsonIgnore
    private List<Section> sections;
    @JsonIgnore
    private List<Team> teams;

    @Id
    @GeneratedValue
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    @ManyToOne
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY)
    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    @ManyToMany(mappedBy = "boards", fetch = FetchType.LAZY)
    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
