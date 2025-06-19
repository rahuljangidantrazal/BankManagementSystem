package repo;

import model.Transaction;
import model.TransactionTypeEnum;
import util.DBConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static constants.RepoMessages.*;

public class TransactionRepo {

    private static TransactionRepo instance;

    private TransactionRepo() {
    }

    public static TransactionRepo getInstance() {
        if (instance == null) {
            instance = new TransactionRepo();
        }
        return instance;
    }

    public boolean insertTransaction(Transaction txn) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(INSERT_TRANSACTION, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, txn.getAccountId());
            ps.setInt(2, txn.getPerformedBy());
            ps.setString(3, txn.getType().name());
            ps.setDouble(4, txn.getAmount());
            ps.setTimestamp(5, Timestamp.valueOf(txn.getTimestamp()));
            ps.setBoolean(6, txn.isUndone());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    txn.setTransactionId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Transaction> getTransactionsByAccountId(int accountId) {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_TRANSACTION_BY_ACCOUNT_ID)) {

            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction txn = new Transaction(
                        rs.getInt("account_id"),
                        rs.getInt("performed_by"),
                        rs.getDouble("amount"),
                        TransactionTypeEnum.valueOf(rs.getString("type")),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getBoolean("is_undone"));
                txn.setTransactionId(rs.getInt("transaction_id"));

                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String performedByName = (firstName == null || lastName == null)
                        ? "Bank"
                        : firstName + " " + lastName;
                txn.setPerformedByName(performedByName);

                list.add(txn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean markTransactionAsUndone(int transactionId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(MARK_TRANSACTION_UNDONE)) {

            stmt.setInt(1, transactionId);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Transaction getLatestTransaction(int accountId) {
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement stmt = conn.prepareStatement(GET_LATEST_TRANSACTION)) {

            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Transaction txn = new Transaction(
                        rs.getInt("account_id"),
                        rs.getInt("performed_by"),
                        rs.getDouble("amount"),
                        TransactionTypeEnum.valueOf(rs.getString("type")),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getBoolean("is_undone"));
                txn.setTransactionId(rs.getInt("transaction_id"));
                return txn;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Transaction> getTransactionsByPan(String pan, String bankName) {
        List<Transaction> list = new ArrayList<>();
        try (Connection conn = DBConfig.getConnection();
                PreparedStatement ps = conn.prepareStatement(GET_TRANSACTION_BY_PAN)) {
            ps.setString(1, pan);
            ps.setString(2, bankName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Transaction txn = new Transaction(
                        rs.getInt("account_id"),
                        rs.getInt("performed_by"),
                        rs.getDouble("amount"),
                        TransactionTypeEnum.valueOf(rs.getString("type")),
                        rs.getTimestamp("timestamp").toLocalDateTime(),
                        rs.getBoolean("is_undone"));
                txn.setTransactionId(rs.getInt("transaction_id"));

                String firstName = rs.getString("performer_first_name");
                String lastName = rs.getString("performer_last_name");
                String performedByName = (firstName == null || lastName == null)
                        ? "Bank"
                        : firstName + " " + lastName;

                txn.setPerformedByName(performedByName);
                txn.setBankName(rs.getString("bank_name"));
                txn.setBranchName(rs.getString("branch_name"));

                list.add(txn);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

}
