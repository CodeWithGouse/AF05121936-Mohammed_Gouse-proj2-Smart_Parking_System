import java.sql.*;
import java.util.Scanner;

public class ParkingService {

    public void viewAvailableSlots() {
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(
                "SELECT * FROM parking_slots WHERE is_available=TRUE");
            while(rs.next()) {
                System.out.println("Slot: " + rs.getString("slot_number"));
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void parkVehicle(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Vehicle Number: ");
            String num = sc.nextLine();
            System.out.print("Enter Type: ");
            String type = sc.nextLine();

            ResultSet rs = con.createStatement().executeQuery(
                "SELECT * FROM parking_slots WHERE is_available=TRUE LIMIT 1");

            if(rs.next()) {
                int slot = rs.getInt("slot_id");

                PreparedStatement ps = con.prepareStatement(
                "INSERT INTO vehicles(vehicle_number,vehicle_type,entry_time,slot_id) VALUES(?,?,NOW(),?)");
                ps.setString(1,num);
                ps.setString(2,type);
                ps.setInt(3,slot);
                ps.executeUpdate();

                con.prepareStatement("UPDATE parking_slots SET is_available=FALSE WHERE slot_id="+slot).executeUpdate();

                System.out.println("Parked Successfully!");
            } else {
                System.out.println("No slots available");
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void removeVehicle(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Vehicle Number: ");
            String num = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM vehicles WHERE vehicle_number=? AND exit_time IS NULL");
            ps.setString(1,num);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int id = rs.getInt("vehicle_id");
                int slot = rs.getInt("slot_id");

                con.prepareStatement("UPDATE vehicles SET exit_time=NOW() WHERE vehicle_id="+id).executeUpdate();
                con.prepareStatement("UPDATE parking_slots SET is_available=TRUE WHERE slot_id="+slot).executeUpdate();

                System.out.println("Exited Successfully!");
            } else {
                System.out.println("Not Found!");
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void viewAllVehicles() {
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery("SELECT * FROM vehicles");
            while(rs.next()) {
                System.out.println(rs.getString("vehicle_number")+" | "+rs.getString("vehicle_type"));
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void searchVehicle(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Vehicle Number: ");
            String num = sc.nextLine();

            PreparedStatement ps = con.prepareStatement("SELECT * FROM vehicles WHERE vehicle_number=?");
            ps.setString(1,num);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                System.out.println("Found: "+rs.getString("vehicle_number"));
            } else {
                System.out.println("Not Found");
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void calculateFee(Scanner sc) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.print("Enter Vehicle Number: ");
            String num = sc.nextLine();

            PreparedStatement ps = con.prepareStatement(
            "SELECT TIMESTAMPDIFF(HOUR, entry_time, NOW()) AS hours FROM vehicles WHERE vehicle_number=?");
            ps.setString(1,num);
            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                int hours = rs.getInt("hours");
                int fee = hours * 20;
                System.out.println("Fee: Rs."+fee);
            }
        } catch(Exception e){e.printStackTrace();}
    }

    public void dailyReport() {
        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement().executeQuery(
            "SELECT COUNT(*) AS total FROM vehicles WHERE DATE(entry_time)=CURDATE()");
            if(rs.next()) {
                System.out.println("Total Vehicles Today: "+rs.getInt("total"));
            }
        } catch(Exception e){e.printStackTrace();}
    }
}
