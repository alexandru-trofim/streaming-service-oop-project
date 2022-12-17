package database.elements;

import fileio.CredentialsInput;
import lombok.Getter;
import lombok.Setter;

public class Credentials {
    private @Getter @Setter String name;
    private @Getter @Setter String password;
    private @Getter @Setter String accountType;
    private @Getter @Setter String country;
    private @Getter @Setter String balance;


    public Credentials(final CredentialsInput credentialsInput) {
        this.name = credentialsInput.getName();
        this.password = credentialsInput.getPassword();
        this.accountType = credentialsInput.getAccountType();
        this.country = credentialsInput.getCountry();
        this.balance = credentialsInput.getBalance();
    }
}
