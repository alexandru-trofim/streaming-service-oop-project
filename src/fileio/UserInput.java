package fileio;

public class UserInput {
    private CredentialsInput credentials;

    public UserInput() {
    }

    /**
     *
     * @return returns the Credentials of the User object
     */
    public CredentialsInput getCredentials() {
        return credentials;
    }

    /**
     * Sets the credentials of the User
     * @param credentials the credentials to be set
     */
    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }
}
