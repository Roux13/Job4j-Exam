package ru.job4j.exam.entitties;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity(tableName = "exams")
public class Exam implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String desc;

    @ColumnInfo(name = "result",
    defaultValue = "0")
    private int result;

    @ColumnInfo(name = "date",
            defaultValue = "0")
    private long time;

    public Exam(long id, String title, String desc, int result, long time) {
        this.id = id;
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.result = result;
    }

    @Ignore
    public Exam(String title, String desc, int result, long time) {
        this.title = title;
        this.desc = desc;
        this.time = time;
        this.result = result;
    }

    @Ignore
    public Exam(String title) {
        this.title = title;
    }

    @Ignore
    public Exam(long id, String title, String desc) {
        this(id, title, desc, 0, 0L);
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public long getTime() {
        return time;
    }

    public int getResult() {
        return result;
    }

    public long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id &&
                result == exam.result &&
                time == exam.time &&
                Objects.equals(title, exam.title) &&
                Objects.equals(desc, exam.desc);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, desc, result, time);
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + title + '\'' +
                ", time=" + time +
                ", result=" + result +
                '}';
    }
}
