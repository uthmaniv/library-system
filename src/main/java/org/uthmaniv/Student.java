package org.uthmaniv;

public class Student extends Lender {
    private final ClassLevel level;

    public Student(String firstName, String lastName, long phoneNumber, ClassLevel level) {
        super(firstName, lastName, phoneNumber);
        this.level = level;
    }

    public ClassLevel getLevel() {
        return level;
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
