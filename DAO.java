import java.sql.*;

public class DAO {

    private static  String UserName="root";
    private static String password="Fibo$#incmsacompssLMN**_**@secure..c@m";
    private static String URL="jdbc:mysql://localhost:3306/fbms";
    
    private String Query;
    private Connection connection;
    private UserInfo user;
    public DAO() throws Exception 
    {
    UserName="root";
    password="Fibo$#incmsacompssLMN**_**@secure..c@m";
    URL="jdbc:mysql://localhost:3306/fbms";
    Class.forName("com.mysql.cj.jdbc.Driver");//To specify a specific object in a class name
    connection=DriverManager.getConnection(URL,UserName,password);
    }
public void insertNewUser(UserInfo u) throws Exception
    {
        Query="Insert Into users(Fname,Mname,Lname,nationality,nationalID,yearOfBirth,email,PhoneNumber,job,userName,password) values(?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement s=connection.prepareStatement(Query);
            s.setString(1,u.getFname() );
            s.setString(2,u.getMname() );
            s.setString(3,u.getLname());
            s.setString(4,u.getNationality());
            s.setLong(5, u.getNationalID());
            s.setInt(6, u.getYearOfBirth());
            s.setString(7, u.getEmail());
            s.setLong(8, u.getPhoneNumber());
            s.setString(9, u.getJob());
            s.setString(10, u.getUsername());
            s.setString(11, u.getPassword());
            int NumberOfAffectedRows=s.executeUpdate();

    }
public void deleteNewUser(String username,String password) throws Exception {
    if (password == null) 
    {
        throw new IllegalArgumentException("password can't be zero or empty");
    }

    Query = "DELETE FROM USERS WHERE username = ? AND password = ?";
    
        PreparedStatement statement = connection.prepareStatement(Query);
        statement.setString(1, username);
        statement.setString(2, password);
        int rowsAffected = statement.executeUpdate();
        
        // Optional: You might want to check if any rows were actually deleted
        if (rowsAffected == 0) 
            System.out.println("No user found with ID: " + password);  
}

public boolean EntranceValidation(String username, String password) throws SQLException {
    String query = "SELECT password FROM USERS WHERE username = ?";

    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return password.equals(storedPassword); // Direct numeric comparison
            }
            return false; // User not found
        }
    }
}
public void updateUserProfile(int id, UserInfo u) throws SQLException {
    String query = "UPDATE USERS SET Fname=?, Mname=?, Lname=?, nationality=?, nationalID=?, yearOfBirth=?, email=?, PhoneNumber=?, job=?, username=?, password=? WHERE id=?";

    PreparedStatement stmt = connection.prepareStatement(query);
    stmt.setString(1, u.getFname());
    stmt.setString(2, u.getMname());
    stmt.setString(3, u.getLname());
    stmt.setString(4, u.getNationality());
    stmt.setLong(5, u.getNationalID());
    stmt.setInt(6, u.getYearOfBirth());
    stmt.setString(7, u.getEmail());
    stmt.setLong(8, u.getPhoneNumber());
    stmt.setString(9, u.getJob());
    stmt.setString(10, u.getUsername());
    stmt.setString(11, u.getPassword());
    stmt.setInt(12, id);

    int rows = stmt.executeUpdate();
    if (rows == 0) {
        System.out.println("No records updated.");
    }
}
                    public boolean validateUser(String username, String password) throws SQLException {
                        String query = "SELECT password FROM USERS WHERE username = ?";
                        
                        try (PreparedStatement stmt = connection.prepareStatement(query)) {
                            stmt.setString(1, username);
                            
                            try (ResultSet rs = stmt.executeQuery()) {
                                if (rs.next()) {
                                    String storedPassword = rs.getString("password");
                                    return password.equals(storedPassword);
                                }
                                return false;
                            }
                        }
                    }
        public void fetch() throws Exception
        {
            int count=0;
            Query="select * from USERS";
            Statement statement=connection.createStatement();
            ResultSet result=statement.executeQuery(Query);

                    while (result .next()) {
                        String user=result.getString("username");
                        String pasword=result.getString("password");
                        int i=result.getInt("id");

                        count++;
                        System.out.println(count+".Username : "+user+"  |  Password : "+pasword+"  |  ID : "+i);
                                            }
        }
        
public void grantAccess(int id, String role) throws SQLException {
    // Validate role first
    if (!role.equals("admin") && !role.equals("agent") && !role.equals("user")) {
        System.out.println("Invalid Role");
        return; // Exit if role is invalid
    }
    String query = "SELECT role FROM USERS WHERE id = ? AND role = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);       // Set the first parameter (id)
        stmt.setString(2, role);  // Set the second parameter (role)

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            System.out.println("Access granted for role: " + role);
        } else {
            System.out.println("Access denied: Invalid ID or role mismatch");
        }
    }
}

        public int getID(String username,String password)throws Exception{
            Query="SELECT id FROM USERS WHERE username = ? AND password =?";

            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setString(1, username);
            stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                return -1; // or throw an exception for invalid credentials
            }
        }
        }
            public String getUsername(int id)throws Exception{
            Query="SELECT userName FROM USERS WHERE id =?";

            PreparedStatement stmt = connection.prepareStatement(Query);
            stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("userName");
            } else {
                return null; // or throw an exception for invalid credentials
            }
        }
        }
            public String getPassword(int id)throws Exception{
            String q="SELECT password FROM USERS WHERE id =?";

            PreparedStatement stmt = connection.prepareStatement(q);
            stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("password");
            }
        return null;
        }
public String getUserRole(String username, String password) throws SQLException {
    String query = "SELECT role FROM users WHERE username = ? AND password = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, username);
        stmt.setString(2, password);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getString("role");
            } else {
                return null; // user not found or wrong credentials
            }
        }
    }
}
public void previewRow(int ID) throws Exception {
    Query = "SELECT * FROM USERS WHERE id = ?";

    PreparedStatement stmt = connection.prepareStatement(Query);
    stmt.setInt(1, ID);

    ResultSet result = stmt.executeQuery();

    if (result.next()) {
        int id = result.getInt("id");
        int yearOfBirth = result.getInt("yearOfBirth");
        long nationalID = result.getLong("nationalID");
        long phoneNumber = result.getLong("PhoneNumber");

        String firstName = result.getString("Fname");
        String middleName = result.getString("Mname");
        String lastName = result.getString("Lname");
        String email = result.getString("email");
        String job = result.getString("job");
        String username = result.getString("userName");
        String pass = result.getString("password");

        System.out.println("\n\nID : " + id);
        System.out.println("\n\nFirst Name : " + firstName);
        System.out.println("\n\nMiddle Name : " + middleName);
        System.out.println("\n\nLast Name : " + lastName);
        System.out.println("\n\nNational ID : " + nationalID);
        System.out.println("\n\nJob : " + job);
        System.out.println("\n\nPhone Number : " + phoneNumber);
        System.out.println("\n\nEmail : " + email);
        System.out.println("\n\nYear Of Birth : " + yearOfBirth);
        System.out.println("\n============================");
        System.out.println("User Name : " + username);
        System.out.println("\nPassword : " + pass);
        System.out.println("============================\n\n");
    } else {
        System.out.println("No user found with ID: " + ID);
    }
}
        public void updateUser(int id, UserInfo updatedUser) throws SQLException {
    String query = "UPDATE users SET Fname = ?, Mname = ?, Lname = ?, nationality = ?, nationalID = ?, " +
                   "yearOfBirth = ?, email = ?, PhoneNumber = ?, job = ?, username = ?, password = ? " +
                   "WHERE id = ?";
    
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setString(1, updatedUser.getFname());
        stmt.setString(2, updatedUser.getMname());
        stmt.setString(3, updatedUser.getLname());
        stmt.setString(4, updatedUser.getNationality());
        stmt.setLong(5, updatedUser.getNationalID());
        stmt.setInt(6, updatedUser.getYearOfBirth());
        stmt.setString(7, updatedUser.getEmail());
        stmt.setLong(8, updatedUser.getPhoneNumber());
        stmt.setString(9, updatedUser.getJob());
        stmt.setString(10, updatedUser.getUsername());
        stmt.setString(11, updatedUser.getPassword());
        stmt.setInt(12, id);

        int rows = stmt.executeUpdate();
        if (rows > 0) {
            System.out.println("Profile updated successfully.");
        } else {
            System.out.println("No user found with ID: " + id);
        }
    }
}
public UserInfo getUserById(int id) throws SQLException {
    String query = "SELECT * FROM USERS WHERE id = ?";
    try (PreparedStatement stmt = connection.prepareStatement(query)) {
        stmt.setInt(1, id);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                UserInfo user = new UserInfo();
                user.setFname(rs.getString("Fname"));
                user.setMname(rs.getString("Mname"));
                user.setLname(rs.getString("Lname"));
                user.setNationality(rs.getString("nationality"));
                user.setNationalID(rs.getLong("nationalID"));
                user.setYearOfBirth(rs.getInt("yearOfBirth"));
                user.setEmail(rs.getString("email"));
                user.setPhoneNumber(rs.getLong("PhoneNumber"));
                user.setJob(rs.getString("job"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                return user;
            }
        }
    }
    return null;
}
}