package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "hints")
@ForeignKey(entity = Question.class,
        parentColumns = "id",
        childColumns = "question_id")
public class Hint {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "question_id")
    private int questionId;

    public Hint(int id, String text, int questionId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getQuestionId() {
        return questionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hint hint = (Hint) o;
        return id == hint.id &&
                questionId == hint.questionId &&
                Objects.equals(text, hint.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, questionId);
    }
}
