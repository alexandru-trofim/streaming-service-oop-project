package fileio;


public class CredentialsInput {
    private  String name;
    private  String password;
    private  String accountType;
    private  String country;
    private  String balance;

    public CredentialsInput() {
    }

    /**
     *
     * @return return the name in CredentialsInput
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name in CredentialsInput
     * @param name the name to be set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     *
     * @return return the password in CredentialsInput
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password in CredentialsInput
     * @param password the password to be set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     *
     * @return return the accountType in CredentialsInput
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * Sets the accountType in CredentialsInput
     * @param accountType the accountType to be set
     */
    public void setAccountType(final String accountType) {
        this.accountType = accountType;
    }

    /**
     *
     * @return return the country in CredentialsInput
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the country in CredentialsInput
     * @param country the country to be set
     */
    public void setCountry(final String country) {
        this.country = country;
    }

    /**
     *
     * @return return the balance in CredentialsInput
     */
    public String getBalance() {
        return balance;
    }

    /**
     * Sets the balance in CredentialsInput
     * @param balance the balance to be set
     */
    public void setBalance(final String balance) {
        this.balance = balance;
    }


    /**
     *
     * @return returns all the fields of CredentialsInput as a string
     */
    @Override
    public String toString() {
        return "CredentialsInput{"
                + "name='" + name + '\''
                + ", password='" + password + '\''
                + ", accountType='" + accountType + '\''
                + ", country='" + country + '\''
                + ", balance=" + balance
                + '}';
    }
}
