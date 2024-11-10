import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

class Appliance {
    private String name;
    private boolean isOn;

    public Appliance(String name) {
        this.name = name;
        this.isOn = false;
    }

    public void turnOn() {
        isOn = true;
        System.out.println(name + " is now ON.");
    }

    public void turnOff() {
        isOn = false;
        System.out.println(name + " is now OFF.");
    }

    public String getName() {
        return name;
    }
}

class VoiceAssistant {
    private HashMap<String, Appliance> appliances;
    private Connection conn;

    public VoiceAssistant(Connection conn) {
        appliances = new HashMap<>();
        this.conn = conn;
    }

    public void addAppliance(Appliance appliance) {
        appliances.put(appliance.getName().toLowerCase(), appliance);
    }

    public void processCommand(String command) {
        String[] words = command.toLowerCase().split(" ");
        if (words.length >= 2) {
            String action = words[0];
            String applianceName = words[1];
            Appliance appliance = appliances.get(applianceName);

            if (appliance != null) {
                if (action.equals("turnon")) {
                    appliance.turnOn();
                    saveCommandToDatabase(command, applianceName, action);
                } else if (action.equals("turnoff")) {
                    appliance.turnOff();
                    saveCommandToDatabase(command, applianceName, action);
                } else {
                    System.out.println("Invalid command. Use 'turnon' or 'turnoff'.");
                }
            } else {
                System.out.println("No appliance found with name " + applianceName + ".");
            }
        } else {
            System.out.println("Incomplete command. Try again.");
        }
    }

    private void saveCommandToDatabase(String command, String applianceName, String action) {
        String insertSQL = "INSERT INTO Commands (command, appliance_name, action) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
            stmt.setString(1, command);
            stmt.setString(2, applianceName);
            stmt.setString(3, action);
            stmt.executeUpdate();
            System.out.println("Command saved to database.");
        } catch (SQLException e) {
            System.out.println("Error saving command to database: " + e.getMessage());
        }
    }
}

public class HomeApplianceSimulator {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/HomeApplianceDB";
        String user = "root"; // Replace with your MySQL username
        String password = "Aditya@1805"; // Replace with your MySQL password

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            VoiceAssistant assistant = new VoiceAssistant(conn);

            // Adding appliances to the assistant
            assistant.addAppliance(new Appliance("light"));
            assistant.addAppliance(new Appliance("fan"));

            Scanner scanner = new Scanner(System.in);
            System.out.println("Voice Assistant Home Appliance Simulator. Type commands like 'turnon light' or 'turnoff fan'.");

            while (true) {
                System.out.print("Enter command: ");
                String command = scanner.nextLine();
                if (command.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting...");
                    break;
                }
                assistant.processCommand(command);
            }

            scanner.close();
        } catch (SQLException e) {
            System.out.println("Database connection error: " + e.getMessage());
        }
    }
}
