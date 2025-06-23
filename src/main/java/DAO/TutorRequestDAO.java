package DAO;

import Utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TutorRequestDAO {
    public static class TutorRequest {
        private final int id;
        private final String accountId;
        private final String name;
        private final String birth;
        private final String email;
        private final String phone;
        private final String idCardNumber;
        private final String bankAccountNumber;
        private final String bankName;
        private final String address;
        private final String specialization;
        private final String description;

        public TutorRequest(int id, String accountId, String name, String birth, String email, String phone,
                            String idCardNumber, String bankAccountNumber, String bankName, String address,
                            String specialization, String description) {
            this.id = id;
            this.accountId = accountId;
            this.name = name;
            this.birth = birth;
            this.email = email;
            this.phone = phone;
            this.idCardNumber = idCardNumber;
            this.bankAccountNumber = bankAccountNumber;
            this.bankName = bankName;
            this.address = address;
            this.specialization = specialization;
            this.description = description;
        }

        public int getId() { return id; }
        public String getAccountId() { return accountId; }
        public String getName() { return name; }
        public String getBirth() { return birth; }
        public String getEmail() { return email; }
        public String getPhone() { return phone; }
        public String getIdCardNumber() { return idCardNumber; }
        public String getBankAccountNumber() { return bankAccountNumber; }
        public String getBankName() { return bankName; }
        public String getAddress() { return address; }
        public String getSpecialization() { return specialization; }
        public String getDescription() { return description; }
    }

    public void saveTutorRequest(String accountId, String name, String birth, String email, String phone,
                                 String idCardNumber, String bankAccountNumber, String bankName, String address,
                                 String specialization, String description) throws SQLException {
        String sql = """
            INSERT INTO tutor_requests (account_id, name, birth, email, phone, id_card_number, bank_account_number, 
                                       bank_name, address, specialization, description, created_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, accountId);
            ps.setString(2, name);
            ps.setString(3, birth);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, idCardNumber);
            ps.setString(7, bankAccountNumber);
            ps.setString(8, bankName);
            ps.setString(9, address);
            ps.setString(10, specialization);
            ps.setString(11, description);
            ps.executeUpdate();
        }
    }

    public List<TutorRequest> getAllTutorRequests() throws SQLException {
        List<TutorRequest> requests = new ArrayList<>();
        String sql = """
            SELECT id, account_id, name, birth, email, phone, id_card_number, bank_account_number, 
                   bank_name, address, specialization, description
            FROM tutor_requests
        """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                requests.add(new TutorRequest(
                        rs.getInt("id"),
                        rs.getString("account_id"),
                        rs.getString("name"),
                        rs.getString("birth"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("id_card_number"),
                        rs.getString("bank_account_number"),
                        rs.getString("bank_name"),
                        rs.getString("address"),
                        rs.getString("specialization"),
                        rs.getString("description")
                ));
            }
        }
        return requests;
    }

    public void rejectTutorRequest(String requestId) throws SQLException {
        String sql = "DELETE FROM tutor_requests WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(requestId));
            ps.executeUpdate();
        }
    }
}