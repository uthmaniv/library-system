package org.uthmaniv;

import java.util.Objects;

public class Student extends Lender {
    private final ClassLevel level;

    public Student(String id, String firstName, String lastName, long phoneNumber, ClassLevel level) {
        super(id, firstName, lastName, phoneNumber);
        this.level = level;
    }

    public ClassLevel getLevel() {
        return level;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return level == student.level;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(level);
    }

    @Override
    public int getPriority() {
        // Teachers = 1,
        return switch (level) {
            case SSS3 -> 2;
            case SSS2 -> 3;
            case SSS1 -> 4;
            case JSS3 -> 5;
            case JSS2 -> 6;
            case JSS1 -> 7;
        };
    }
}
