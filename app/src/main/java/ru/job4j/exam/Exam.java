package ru.job4j.exam;

public class Exam {

    private int id;
    private String name;
    private long time;
    private int result;

    public Exam(int id, String name, long time, int result) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public long getTime() {
        return time;
    }

    public int getResult() {
        return result;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        return id == exam.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", time=" + time +
                ", result=" + result +
                '}';
    }
}
