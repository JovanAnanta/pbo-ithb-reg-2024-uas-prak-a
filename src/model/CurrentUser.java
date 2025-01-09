package model;

public class CurrentUser {
    private static CurrentUser instance;
    private Customer customer;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void clearUser() {
        this.customer = null;
    }

    public boolean isLoggedIn() {
        return this.customer != null;
    }
}
