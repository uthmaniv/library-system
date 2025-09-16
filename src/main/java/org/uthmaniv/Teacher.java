package org.uthmaniv;

import java.util.Objects;
import java.util.Set;

public class Teacher extends Lender {
    private final Set<ClassLevel> classLevels;
    public Teacher(String id, String firstName, String lastName, long phoneNumber, Set<ClassLevel> classLevels) {
        super(id, firstName, lastName, phoneNumber);
        this.classLevels = classLevels;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(classLevels, teacher.classLevels);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(classLevels);
    }

    @Override
    public int getPriority() {
        return 1; // always highest
    }

}
