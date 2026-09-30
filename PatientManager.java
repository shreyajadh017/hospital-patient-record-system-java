package hospital;

import java.sql.*;
import java.time.LocalDate;
import java.util.Scanner;

public class PatientManager {
    public void addPatient(Scanner sc) {
        System.out.print("Name: "); String name = sc.nextLine().trim();
        System.out.print("Age: "); int age = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Gender: "); String gender = sc.nextLine().trim();
        System.out.print("Phone: "); String phone = sc.nextLine().trim();
        System.out.print("Diagnosis: "); String diagnosis = sc.nextLine().trim();

        String sql = "INSERT INTO patients(name, age, gender, phone, diagnosis) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name); ps.setInt(2, age); ps.setString(3, gender);
            ps.setString(4, phone); ps.setString(5, diagnosis);
            ps.executeUpdate();
            System.out.println("Patient added successfully.");
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void viewPatients() {
        String sql = "SELECT patient_id, name, age, gender, phone, diagnosis FROM patients ORDER BY patient_id";
        try (Connection con = DBConnection.getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            System.out.println("\n----- Patient Records -----");
            while (rs.next()) {
                System.out.printf("ID: %d | Name: %s | Age: %d | Gender: %s | Phone: %s | Diagnosis: %s%n",
                        rs.getInt("patient_id"), rs.getString("name"), rs.getInt("age"),
                        rs.getString("gender"), rs.getString("phone"), rs.getString("diagnosis"));
            }
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void searchPatient(Scanner sc) {
        System.out.print("Patient ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        String sql = "SELECT * FROM patients WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    System.out.printf("ID: %d | Name: %s | Age: %d | Gender: %s | Phone: %s | Diagnosis: %s%n",
                            rs.getInt("patient_id"), rs.getString("name"), rs.getInt("age"),
                            rs.getString("gender"), rs.getString("phone"), rs.getString("diagnosis"));
                } else System.out.println("Patient not found.");
            }
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void updatePatient(Scanner sc) {
        System.out.print("Patient ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("New diagnosis: "); String diagnosis = sc.nextLine().trim();
        System.out.print("New phone: "); String phone = sc.nextLine().trim();
        String sql = "UPDATE patients SET diagnosis = ?, phone = ? WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, diagnosis); ps.setString(2, phone); ps.setInt(3, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Patient updated successfully." : "Patient not found.");
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void deletePatient(Scanner sc) {
        System.out.print("Patient ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        String sql = "DELETE FROM patients WHERE patient_id = ?";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "Patient deleted successfully." : "Patient not found.");
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void addHistory(Scanner sc) {
        System.out.print("Patient ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        System.out.print("Visit date (YYYY-MM-DD): "); LocalDate date = LocalDate.parse(sc.nextLine().trim());
        System.out.print("Notes: "); String notes = sc.nextLine().trim();
        String sql = "INSERT INTO medical_history(patient_id, visit_date, notes) VALUES (?, ?, ?)";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id); ps.setDate(2, Date.valueOf(date)); ps.setString(3, notes);
            ps.executeUpdate();
            System.out.println("Medical history added successfully.");
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }

    public void viewHistory(Scanner sc) {
        System.out.print("Patient ID: "); int id = Integer.parseInt(sc.nextLine().trim());
        String sql = "SELECT visit_date, notes FROM medical_history WHERE patient_id = ? ORDER BY visit_date DESC";
        try (Connection con = DBConnection.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n----- Medical History -----");
                while (rs.next()) System.out.println(rs.getDate("visit_date") + " | " + rs.getString("notes"));
            }
        } catch (SQLException e) { System.out.println("Database error: " + e.getMessage()); }
    }
}
