package org.uthmaniv;

public class Teacher extends Lender {
    public Teacher(String id, String firstName, String lastName, long phoneNumber) {
        super(id, firstName, lastName, phoneNumber);
    }

    @Override
    public int getPriority() {
        return 1; // always highest
    }
}
