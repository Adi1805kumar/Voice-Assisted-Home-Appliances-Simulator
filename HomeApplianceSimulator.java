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

    public boolean getStatus() {
        return isOn;
    }

    public String getName() {
        return name;
    }
}

class VoiceAssistant {
    private HashMap<String, Appliance> appliances;

    public VoiceAssistant() {
        appliances = new HashMap<>();
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
                    // Here, you could add code to store the command in your SQL database
                } else if (action.equals("turnoff")) {
                    appliance.turnOff();
                    // Store command in SQL database if needed
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
}

public class HomeApplianceSimulator {
    public static void main(String[] args) {
        VoiceAssistant assistant = new VoiceAssistant();
        
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
    }
}
