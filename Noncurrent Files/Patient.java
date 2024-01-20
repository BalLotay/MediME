public class Patient extends Person {

    private int healthCardNumber;

    public Patient(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address);
        this.healthCardNumber = healthCardNumber;
    }

    public int getHealthCardNumber() {
        return healthCardNumber;
    }

    public void setHealthCardNumber(int newHealthCardNumber) {
        healthCardNumber = newHealthCardNumber;
    }


}