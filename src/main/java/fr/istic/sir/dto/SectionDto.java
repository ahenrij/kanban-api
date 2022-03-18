package fr.istic.sir.dto;


import java.io.Serializable;

public class SectionDto implements Serializable {

    private long id;
    private String title;
    private int position;
    private Long boardId;

    public SectionDto() {
    }

    public SectionDto(long id, String title, int position, Long boardId) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.boardId = boardId;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Long getBoardId() {
        return boardId;
    }

    public void setBoardId(Long boardId) {
        this.boardId = boardId;
    }
}
