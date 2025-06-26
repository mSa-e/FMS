import java.io.*;
import java.util.Scanner;

public abstract class USER 
{
private String username;
private String password;

public abstract void login()throws Exception;
public abstract void logOut()throws Exception;

public USER(String username,String password){
this.password=password;
this.username=username;
}
public USER(){}
 public String getUsername() {
        return username;
    }
    
public String getPassword() {
   return password;
   }

public void viewProfileDetails(int ID) throws Exception {
    DAO dao = new DAO();
    int id = dao.getID(this.getUsername(), this.getPassword());
    if (id != -1) {
        dao.previewRow(id);
    } else {
        System.out.println("Invalid user credentials.");
    }
}

public void updateProfile() throws Exception {
    DAO dao = new DAO();
    int id = dao.getID(this.getUsername(), this.getPassword());

    if (id == -1) {
        System.out.println("Cannot update profile. Invalid credentials.");
        return;
    }

    // Get existing data
    UserInfo updated = dao.getUserById(id);
    if (updated == null) {
        System.out.println("User not found.");
        return;
    }

    Scanner scanner = new Scanner(System.in);
    System.out.println("""
                       What data would you like to edit?
                       1. First name
                       2. Middle name
                       3. Last name
                       4. Nationality
                       5. National ID
                       6. Year of birth
                       7. Email
                       8. Phone number
                       9. Job
                       10. Username
                       11. Password""");

    System.out.print("Choice = ");
    int c = scanner.nextInt();
    scanner.nextLine(); // clear newline

    switch (c) {
        case 1 -> {
            System.out.print("Enter new First name: ");
            updated.setFname(scanner.nextLine());
        }
        case 2 -> {
            System.out.print("Enter new middle name: ");
            updated.setMname(scanner.nextLine());
        }
        case 3 -> {
            System.out.print("Enter new last name: ");
            updated.setLname(scanner.nextLine());
        }
        case 4 -> {
            System.out.print("Enter nationality: ");
            updated.setNationality(scanner.nextLine());
        }
        case 5 -> {
            System.out.print("Enter national ID: ");
            updated.setNationalID(scanner.nextLong());
            scanner.nextLine();
        }
        case 6 -> {
            System.out.print("Enter year of birth: ");
            updated.setYearOfBirth(scanner.nextInt());
            scanner.nextLine();
        }
        case 7 -> {
            System.out.print("Enter email: ");
            updated.setEmail(scanner.nextLine());
        }
        case 8 -> {
            System.out.print("Enter phone number: ");
            updated.setPhoneNumber(scanner.nextLong());
            scanner.nextLine();
        }
        case 9 -> {
            System.out.print("Enter job: ");
            updated.setJob(scanner.nextLine());
        }
        case 10 -> {
            System.out.print("Enter new username: ");
            updated.setUsername(scanner.nextLine());
        }
        case 11 -> {
            System.out.print("Enter new password: ");
            updated.setPassword(scanner.nextLine());
        }
        default -> System.out.println("Invalid choice.");
    }

    // Now update with complete object
    dao.updateUserProfile(id, updated);
}


public void createATextFile(UserInfo user) throws Exception {
        // Create a new file object
        File file = new File("Info.txt");

        // Check if the file already exists
        if (file.createNewFile()) {
            System.out.println("File created: " + file.getName());
        } else {
            System.out.println("File already exists.");
        }

        // Now writing to the file
        FileWriter writer = new FileWriter(file);

        // Write the user data to the file
        writer.write("User Profile Information:\n");
        writer.write("First Name: " + user.getFname() + "\n");
        writer.write("Middle Name: " + user.getMname() + "\n");
        writer.write("Last Name: " + user.getLname() + "\n");
        writer.write("Nationality: " + user.getNationality() + "\n");
        writer.write("National ID: " + user.getNationalID() + "\n");
        writer.write("Year of Birth: " + user.getYearOfBirth() + "\n");
        writer.write("Email: " + user.getEmail() + "\n");
        writer.write("Phone Number: " + user.getPhoneNumber() + "\n");
        writer.write("Job: " + user.getJob() + "\n");
        writer.write("Username: " + user.getUsername() + "\n");
        writer.write("Password: " + user.getPassword() + "\n");

        // Close the writer
        writer.close();

        System.out.println("Successfully wrote user information to the file.");
    }
}