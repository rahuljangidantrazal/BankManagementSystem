package model;

import java.util.List;

public class AccountDetails {
    private Account account;
    private Branch branch;
    private User primaryHolder;
    private List<User> jointHolders;

    public AccountDetails(Account account, Branch branch, User primaryHolder, List<User> jointHolders) {
        this.account = account;
        this.branch = branch;
        this.primaryHolder = primaryHolder;
        this.jointHolders = jointHolders;
    }

    public Account getAccount() { return account; }
    public Branch getBranch() { return branch; }
    public User getPrimaryHolder() { return primaryHolder; }
    public List<User> getJointHolders() { return jointHolders; }
}
