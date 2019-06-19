package phonebook;

public class Contact {

    private final String name;
    private String number;
    private Integer numberOfOutgoingCalls;

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    void setNumber(String number) {
        this.number = number;
    }

    Contact(String name, String number) {
        this.name = name;
        this.number = number;
        this.numberOfOutgoingCalls = 0;
    }

    void makeACall() {
        numberOfOutgoingCalls++;
    }

    Integer getNumberOfOutgoingCalls() {
        return numberOfOutgoingCalls;
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getNumber();
    }
}
