package model;

public class Branch {
    private int branchId;
    private int bankId;
    private String branchName;
    private String ifscCode;

    public Branch() {
    }

    public Branch(int branchId, int bankId, String branchName, String ifscCode) {
        this.branchId = branchId;
        this.bankId = bankId;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
    }

    public Branch(int bankId, String branchName, String ifscCode) {
        this.bankId = bankId;
        this.branchName = branchName;
        this.ifscCode = ifscCode;
    }

    public int getBranchId() {
        return branchId;
    }

    public int getBankId() {
        return bankId;
    }

    public String getBranchName() {
        return branchName;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }
}
