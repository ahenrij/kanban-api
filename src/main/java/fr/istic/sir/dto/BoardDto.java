package fr.istic.sir.dto;

import java.io.Serializable;

public class BoardDto implements Serializable {

    private long id;
    private String title;
    private String description;
    private String color;
    private boolean isPrivate;

    public BoardDto(){}

    public BoardDto(long id, String title, String description, String color, boolean isPrivate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.color = color;
        this.isPrivate = isPrivate;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
