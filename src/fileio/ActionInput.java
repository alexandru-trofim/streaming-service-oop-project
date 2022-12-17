package fileio;


public class ActionInput {
    private String type;
    private String page;
    private String movie;
    private String feature;
    private CredentialsInput credentials;
    private FiltersInput filters;
    private int count;
    private String startsWith;
    private int rate;

    public ActionInput() {
    }

    /**
     *
     * @return return the rate from ActionsInput
     */
    public int getRate() {
        return rate;
    }

    /**
     * Sets the rate in ActionsInput
     * @param rate the rate to be set
     */
    public void setRate(final int rate) {
        this.rate = rate;
    }

    /**
     *
     * @return return the count from ActionsInput
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count in ActionsInput
     * @param count the count to be set
     */
    public void setCount(final int count) {
        this.count = count;
    }

    /**
     *
     * @return return the startsWith from ActionsInput
     */
    public String getStartsWith() {
        return startsWith;
    }

    /**
     * Sets the startsWith in ActionsInput
     * @param startsWith the startsWith to be set
     */
    public void setStartsWith(final String startsWith) {
        this.startsWith = startsWith;
    }

    /**
     *
     * @return return the type from ActionsInput
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type in ActionsInput
     * @param type the type to be set
     */
    public void setType(final String type) {
        this.type = type;
    }

    /**
     *
     * @return return the page from ActionsInput
     */
    public String getPage() {
        return page;
    }

    /**
     * Sets the page in ActionsInput
     * @param page the page to be set
     */
    public void setPage(final String page) {
        this.page = page;
    }

    /**
     *
     * @return return the movie from ActionsInput
     */
    public String getMovie() {
        return movie;
    }

    /**
     * Sets the movie in ActionsInput
     * @param movie the movie to be set
     */
    public void setMovie(final String movie) {
        this.movie = movie;
    }

    /**
     *
     * @return return the feature from ActionsInput
     */
    public String getFeature() {
        return feature;
    }

    /**
     * Sets the feature in ActionsInput
     * @param feature the feature to be set
     */
    public void setFeature(final String feature) {
        this.feature = feature;
    }

    /**
     *
     * @return return the credential from ActionsInput
     */
    public CredentialsInput getCredentials() {
        return credentials;
    }

    /**
     * Sets the credentials in ActionsInput
     * @param credentials the credentials to be set
     */
    public void setCredentials(final CredentialsInput credentials) {
        this.credentials = credentials;
    }

    /**
     *
     * @return return the filters from ActionsInput
     */
    public FiltersInput getFilters() {
        return filters;
    }

    /**
     * Sets the filters in ActionsInput
     * @param filters the filters to be set
     */
    public void setFilters(final FiltersInput filters) {
        this.filters = filters;
    }
}
