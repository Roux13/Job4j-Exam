package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "questions")
public class Question implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private final long id;

    @ColumnInfo(name = "text")
    private final String text;

    @ColumnInfo(name = "exam_id")
    private final long examId;

    @ColumnInfo(name = "correct_answer_id")
    private final int correctAnswerId;

    @ColumnInfo(name = "position")
    private final int position;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return id == question.id &&
                examId == question.examId &&
                correctAnswerId == question.correctAnswerId &&
                position == question.position &&
                Objects.equals(text, question.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, examId, correctAnswerId, position);
    }
}
