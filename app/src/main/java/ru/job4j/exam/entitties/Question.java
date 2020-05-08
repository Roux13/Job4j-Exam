package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "questions")
public class Question implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "exam_id")
    private long examId;

    @ColumnInfo(name = "correct_answer_id")
    private int correctAnswerId;

    @ColumnInfo(name = "position")
    private int position;

    public Question(long id, String text, long examId, int correctAnswerId, int position) {
        this.id = id;
        this.text = text;
        this.examId = examId;
        this.correctAnswerId = correctAnswerId;
        this.position = position;
    }

    public long getId() {
        return id;
    }

    public long getExamId() {
        return examId;
    }

    public int getCorrectAnswerId() {
        return correctAnswerId;
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public int getAnswer() {
        return correctAnswerId;
    }
}
