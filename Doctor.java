public class Doctor extends Person {

    private String[] specialties;

    Doctor(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber, String ...specialties){
        super(firstName, lastName, emailAddress, accountPassword, phoneNumber, address, healthCardNumber);
        this.specialties = specialties;
    }

}