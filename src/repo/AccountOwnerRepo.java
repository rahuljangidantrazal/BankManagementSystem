package repo;

import util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static constants.RepoMessages.*;

public class AccountOwnerRepo {

    private static AccountOwnerRepo instance;

    private AccountOwnerRepo() {
    }

    public static AccountOwnerRepo getInstance() {
        if (instance == null) {
            instance = new AccountOwnerRepo();
        }
        return instance;
    }

    public boolean addOwner(int accountId, int userId, boolean isPrimary) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(ADD_OWNER)) {
            ps.setInt(1, accountId);
            ps.setInt(2, userId);
            ps.setBoolean(3, isPrimary);
            return ps.executeUpdate() == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserAlreadyOwner(int accountId, int userId) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(IS_USER_ALREADY_OWNER)) {
            ps.setInt(1, accountId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Integer> getOwnerIdsByAccountId(int accountId) {
        List<Integer> userIds = new ArrayList<>();
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_OWNER_IDS_BY_ACCOUNT)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                userIds.add(rs.getInt("user_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userIds;
    }

    public boolean hasAccountInBank(int userId, int bankId) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(HAS_ACCOUNT_IN_BANK)) {
            ps.setInt(1, userId);
            ps.setInt(2, bankId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean softDeleteAccount(int accountId, int userId) {
        try (Connection con = DBConfig.getConnection()) {
            con.setAutoCommit(false);
            try (
                    PreparedStatement ps1 = con.prepareStatement(SOFT_DELETE_DEACTIVATE_OWNER);
                    PreparedStatement ps2 = con.prepareStatement(SOFT_DELETE_CHECK_REMAINING_OWNERS);
                    PreparedStatement ps3 = con.prepareStatement(SOFT_DELETE_DEACTIVATE_ACCOUNT)) {
                ps1.setInt(1, accountId);
                ps1.setInt(2, userId);
                ps1.executeUpdate();

                ps2.setInt(1, accountId);
                ResultSet rs = ps2.executeQuery();

                if (rs.next() && rs.getInt(1) == 0) {
                    ps3.setInt(1, accountId);
                    ps3.executeUpdate();
                }

                con.commit();
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean hasJointHolder(int accountId) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(HAS_JOINT_HOLDER)) {
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
