package fileio;


public class FiltersInput {
    private SortInput sort;
    private ContainsInput contains;

    public FiltersInput() {
    }

    /**
     *
     * @return returns the SortInput from FiltersInput
     */
    public SortInput getSort() {
        return sort;
    }

    /**
     * Sets the SortInput in FiltersInput
     * @param sort SortInput Object to be set
     */
    public void setSort(final SortInput sort) {
        this.sort = sort;
    }

    /**
     *
     * @return returns the ContainsInput from FiltersInput
     */
    public ContainsInput getContains() {
        return contains;
    }

    /**
     * Sets the ContainsInput in FiltersInput
     * @param contains ContainsInput Object to be set
     */
    public void setContains(final ContainsInput contains) {
        this.contains = contains;
    }
}
