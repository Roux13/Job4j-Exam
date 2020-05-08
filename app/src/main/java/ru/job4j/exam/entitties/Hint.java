package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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
}
