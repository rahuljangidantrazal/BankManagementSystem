package repo;

import model.Account;
import model.User;
import model.UserTypeEnum;
import util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import constants.Messages;
import static util.InputUtil.*;
import static constants.RepoMessages.*;

public class UserRepo {
    private static UserRepo instance;

    private UserRepo() {
    }

    public static UserRepo getInstance() {
        if (instance == null) {
            instance = new UserRepo();
        }
        return instance;
    }

    public void insertAdmin() {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement checkStmt = conn.prepareStatement(INSERT_ADMIN);
                ResultSet rs = checkStmt.executeQuery()) {

            rs.next();
            if (rs.getInt(1) == 0) {
                User admin = new User();
                admin.setFirstName("Admin");
                admin.setLastName("");
                admin.setUsername("admin");
                admin.setPassword("admin123");
                admin.setEmail(null);
                admin.setPhone(null);
                admin.setUserType(UserTypeEnum.ADMIN);
                admin.setAadhar(null);
                admin.setPan(null);
                admin.setBranchId(null);
                admin.setCreditScore(0);
                admin.setActive(true);

                if (!insertUser(admin)) {
                    print(Messages.UserServiceMessages.ERROR_INSERT_ADMIN);
                }
            }

        } catch (SQLException e) {
            print(Messages.UserServiceMessages.ERROR_INSERT_ADMIN + e.getMessage());
        }
    }

    public boolean insertUser(User user) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(INSERT_USER)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getAadhar());
            ps.setString(6, user.getPan());
            ps.setString(7, user.getUsername());
            ps.setString(8, user.getPassword());
            ps.setString(9, user.getUserType().name());

            if (user.getBranchId() != null) {
                ps.setInt(10, user.getBranchId());
            } else {
                ps.setNull(10, Types.INTEGER);
            }

            ps.setInt(11, user.getCreditScore());
            ps.setBoolean(12, true);

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public User getUserByUsernameAndPassword(String username, String password) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_USER_BY_USERNAME_AND_PASSWORD)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getUserIdByUsername(String username) {
        try (Connection con = DBConfig.getConnection();
                PreparedStatement ps = con.prepareStatement(GET_USER_ID_BY_USERNAME)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private User extractUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setPhone(rs.getString("phone"));
        user.setEmail(rs.getString("email"));
        user.setAadhar(rs.getString("aadhar_number"));
        user.setPan(rs.getString("pan_number"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setUserType(UserTypeEnum.valueOf(rs.getString("user_type")));
        int branchId = rs.getInt("branch_id");
        user.setBranchId(rs.wasNull() ? null : branchId);
        user.setCreditScore(rs.getInt("credit_score"));
        user.setActive(rs.getBoolean("is_active"));
        return user;
    }

    public User getUserByUsername(String username) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_USER_BY_USERNAME)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean existsByUsername(String username) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_USERNAME)) {
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByPan(String pan) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_PAN)) {
            ps.setString(1, pan);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByPhone(String phone) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_PHONE)) {
            ps.setString(1, phone);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByEmail(String email) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_EMAIL)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existsByAadhar(String aadhar) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_AADHAR)) {
            ps.setString(1, aadhar);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User findByAadhar(String aadhar) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(EXISTS_BY_AADHAR)) {
            ps.setString(1, aadhar);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean branchHasManager(int branchId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(BRANCH_HAS_MANAGER)) {
            stmt.setInt(1, branchId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            print(Messages.UserServiceMessages.ERROER_CHECKING_MANAGER + e.getMessage());
        }
        return false;
    }

    public User findById(int userId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(FIND_BY_ID)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return extractUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(UPDATE_USER)) {

            ps.setString(1, user.getFirstName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPan());
            ps.setString(6, user.getUsername());
            ps.setString(7, user.getPassword());

            if (user.getBranchId() != null) {
                ps.setInt(8, user.getBranchId());
            } else {
                ps.setNull(8, Types.INTEGER);
            }

            ps.setInt(9, user.getCreditScore());
            ps.setInt(10, user.getUserId());

            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Account> getAccountsByPanAndBank(String pan, int bankId) {
        List<Account> accounts = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_ACCOUNTS_BY_PAN_AND_BANK)) {

            stmt.setString(1, pan);
            stmt.setInt(2, bankId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Account acc = new Account();
                acc.setAccountId(rs.getInt("account_id"));
                acc.setAccountNumber(rs.getString("account_number"));
                acc.setBranchId(rs.getInt("branch_id"));
                acc.setBalance(rs.getDouble("balance"));
                acc.setStatus(rs.getString("status"));
                accounts.add(acc);
            }

        } catch (SQLException e) {
            print(Messages.UserServiceMessages.ERROR_FETCHING_ACCOUNT + e.getMessage());
        }

        return accounts;
    }

    public List<String> getAllPans() {
        List<String> pans = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_ALL_PANS);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                pans.add(rs.getString("pan_number"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pans;
    }

}
