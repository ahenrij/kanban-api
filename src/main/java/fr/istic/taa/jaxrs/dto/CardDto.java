package fr.istic.taa.jaxrs.dto;

import java.io.Serializable;

public class CardDto implements Serializable {

    private Long id;
    private String label;
    private String deadline;
    private int duration; // Estimated required time in minutes
    private String place;
    private String url;
    private String description;
    private Long sectionId;

    public CardDto() {
    }

    public CardDto(Long id, String label, String deadline, int duration, String place, String url, String description, Long sectionId) {
        this.id = id;
        this.label = label;
        this.deadline = deadline;
        this.duration = duration;
        this.place = place;
        this.url = url;
        this.description = description;
        this.sectionId = sectionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
