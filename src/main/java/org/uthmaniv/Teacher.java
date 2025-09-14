package org.uthmaniv;

public class Teacher extends Lender {
    public Teacher(String firstName, String lastName, long phoneNumber) {
        super(firstName, lastName, phoneNumber);
    }

    @Override
    public int getPriority() {
        return 1; // always highest
    }
}
