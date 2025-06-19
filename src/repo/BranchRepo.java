package repo;

import model.Branch;
import util.DBConfig;
import constants.Messages;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static util.InputUtil.*;
import static constants.RepoMessages.*;

public class BranchRepo {

    private static BranchRepo instance;

    private BranchRepo() {
    }

    public static BranchRepo getInstance() {
        if (instance == null) {
            instance = new BranchRepo();
        }
        return instance;
    }

    public boolean insertBranch(Branch branch) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(INSERT_BRANCH)) {

            pstmt.setInt(1, branch.getBankId());
            pstmt.setString(2, branch.getBranchName());
            pstmt.setString(3, branch.getIfscCode());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_INSERTING_BRANCH + e.getMessage());
            return false;
        }
    }

    public List<Branch> getBranchesByBankId(int bankId) {
        List<Branch> branches = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BRANCHES_BY_BANK_ID)) {

            stmt.setInt(1, bankId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Branch branch = new Branch();
                branch.setBranchId(rs.getInt("branch_id"));
                branch.setBankId(rs.getInt("bank_id"));
                branch.setBranchName(rs.getString("branch_name"));
                branch.setIfscCode(rs.getString("ifsc_code"));
                branches.add(branch);
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BRANCHES + e.getMessage());
        }

        return branches;
    }

    public Branch getBranchById(int branchId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BRANCH_BY_ID)) {

            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Branch branch = new Branch();
                branch.setBranchId(rs.getInt("branch_id"));
                branch.setBankId(rs.getInt("bank_id"));
                branch.setBranchName(rs.getString("branch_name"));
                branch.setIfscCode(rs.getString("ifsc_code"));
                return branch;
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BRANCH + e.getMessage());
        }

        return null;
    }

    public Integer getBranchIdByName(String branchName) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_BRANCH_ID_BY_NAME)) {

            ps.setString(1, branchName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("branch_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean existsByIFSC(String ifscCode) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(EXISTS_BY_IFSC)) {

            stmt.setString(1, ifscCode);
            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_IFSC + e.getMessage());
            return true;
        }
    }

    public Integer getBankIdByBranchId(int branchId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_BANK_ID_BY_BRANCH_ID)) {

            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("bank_id");
            }

        } catch (SQLException e) {
            print(Messages.RepoErrors.ERROR_FETCHING_BRANCH + e.getMessage());
        }

        return null;
    }

}