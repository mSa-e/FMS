import java.util.Scanner;
public class AdminInfo extends USER {
    public AdminInfo() {
                super("SystemAdmin.luftHansa","4015cCLBI)ik?y&?Lj5");
    }
                @Override
                public void login()throws Exception{
                    try {
                        DAO dao = new DAO();
                        boolean valid = dao.validateUser(this.getUsername(), this.getPassword());
                        if (valid) {
                            System.out.println("Admin '" + getUsername() + "' logged in.");
                        } else {
                            System.out.println("Invalid admin credentials.");
                        }
                    } catch (Exception e) {
                        System.out.println("Admin login error: " + e.getMessage());
                    }
                }
    @SuppressWarnings("ConvertToTryWithResources")
 public  void adminMenu(AdminInfo admin) throws Exception {
        Scanner in = new Scanner(System.in);
        boolean session = true;
        int choice = 0;
        while (session) {
            try{
            System.out.println("\n Admin DASHBOARD ");
            System.out.println("1. Update Profile");
            System.out.println("2. Create User");
            System.out.println("3 View System Users");
            System.out.println("4. Manage User Access"); 
            System.out.println("5. View profile Details");
            System.out.println("6. Create a File about Admin");
            System.out.println("7. Log Out");
            System.out.print("Choice: ");
            choice = in.nextInt();
                        }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
            switch (choice) {
                case 1 ->      updateProfile();
                case 2 ->
                {
                    DAO dao=new DAO();
                    UserInfo u=new UserInfo(true);
                    dao.insertNewUser(u);
                }
                case 3->
                {
                    DAO dao=new DAO();
                    dao.fetch();
                }
                case 4 -> 
                {
                    DAO dao=new DAO();
            Scanner i=new Scanner(System.in);
            System.out.print("Insert User ID:");
            int iden=i.nextInt();
            i.nextLine();
            System.out.print("Insert the new role [admin - user - agent]:");
            String r=i.next();

            dao.grantAccess(iden, r);
            i.close();
                }
                case 5 ->
                {

                    viewProfileDetails(9);
                }
                case 6 -> 
                {
                    Scanner scan=new Scanner(System.in);
                            DAO dao = new DAO();
                            System.out.print("Insert ID : ");
                            int identification =scan.nextInt(); 
                            UserInfo user = dao.getUserById(identification);

                            UserInfo userObj = new UserInfo();
                            userObj.createATextFile(user);
                            scan.close();
                }
                case 7 -> {
                    session = false;
                }
                default -> {
                    System.out.println("Invalid choice.");
                    in.close();
                }
            }
        }
    }
    @Override
public void logOut() throws Exception {
    System.out.println("Admin '" + getUsername() + "' logged out and activity logged.");
}

}   