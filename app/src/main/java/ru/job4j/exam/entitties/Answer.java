package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers")
@ForeignKey(entity = Question.class,
        parentColumns = "id",
        childColumns = "question_id")
public class Answer {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "question_id")
    private int questionId;

    public int getQuestionId() {
        return questionId;
    }

    public Answer(int id, String text, int questionId) {
        this.id = id;
        this.text = text;
        this.questionId = questionId;
    }

    @Ignore
    public Answer(String text, int questionId) {
        this.text = text;
        this.questionId = questionId;
    }

    @Ignore
    public Answer(int id, String text) {
        this.id = id;
        this.text = text;
    }



    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
