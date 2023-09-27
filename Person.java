public class Person {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private String accountPassword;
    private String phoneNumber;
    private String address;
    private int healthCardNumber;

    public Person(String firstName, String lastName, String emailAddress, String accountPassword, String phoneNumber, String address, int healthCardNumber){
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.accountPassword = accountPassword;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.healthCardNumber = healthCardNumber;
    }

    public void setFirstName(String firstName) {
		this.firstName = firstName;
		
	}

	
	public String getFirstName(){
		return this.firstName;
	}



    public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getLastName(){
		return this.lastName;

	} 


    public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;	
	}


	public String getEmailAddress(){
		return this.emailAddress;
	} 


    public void setAccountPassword(String accountPassword) {
		this.accountPassword = accountPassword;
	}


	public String getAccountPassword(){
		return this.accountPassword;
	} 


    public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getPhoneNumber(){
		return this.phoneNumber;
	}
    

    public void setAddress(String address){
        this.address = address;
    }


    public String getAddress(){
        return this.address;
    }

    public void setHealthCardNumber(String healthCardNumber){
        this.healthCardNumber = healthCardNumber;
    }
    

    public String setHealthCardNumber(){
        return this.healthCardNumber;

    }


}
