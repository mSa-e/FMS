import java.util.*;
public class LuftHansa {
    @SuppressWarnings({"BoxedValueEquality", "NumberEquality", "unused"})
    public static void main(String[] args)throws Exception{

Scanner in=new Scanner(System.in);
Scanner str=new Scanner(System.in);
Scanner STR=new Scanner(System.in);
boolean flag=true;
int USEDID;
USER currentUser = null;

System.out.println("======== Welcome to LuftHansa HUB ========\n");


while(flag)
{
    dis();

    System.out.print("Enter your choice: ");
    int Choice =in.nextInt();

    switch (Choice) {
        case 1 -> 
        {
            System.out.println();
            UserInfo u=new UserInfo(true);
            DAO x=new DAO();
            x.insertNewUser(u);
            System.out.println("\nAccount created successfully!");
            System.out.println("\nYour account's username is : "+u.getUsername() +"\n\nYour account's password is : "+u.getPassword());
            System.out.println("\nYour id is "+x.getID(u.getUsername(),u.getPassword())+"\n");
            u.login();
            currentUser=u;
            flag=false;
        }
        case 2 -> 
        {
        DAO x=new DAO();
        AdminInfo admin = new AdminInfo();
        TravelAgentInfo agent = new TravelAgentInfo(true);
        System.out.print("Insert your username:");
        String u=str.next().trim();
        System.out.print("\nInsert your account's password :");
        String pass=STR.next();

        if(x.EntranceValidation(u, pass))
        {
            switch (x.getUserRole(u, pass)) {
                case "user" -> {
                    USEDID=x.getID(u, pass);
                    UserInfo existingUser = new UserInfo();
                    existingUser.username = u;
                    existingUser.setPassword(pass);
                    existingUser.login();
                    currentUser = existingUser;
                    System.out.println("Login successful (Normal User)");
                    flag=false;
                }
                case "admin" -> {
                    admin.login();
                    currentUser=admin;
                    System.out.println("Login successful (ADMIN)");
                    flag=false;
                }
                case "agent" -> {
                    agent.login();
                    currentUser = agent;
                    System.out.println("Login successful (Travel Agent)");
                    flag=false;
                }
            }
        }
        else
        {
            System.out.println("\n\nInvalid Username/password\n\nPlease try again\n\n");
        }
    }
        case 3 -> 
        {
            DAO x=new DAO();
            System.out.print("\nInsert your account's username :");
            String u=str.next();
            System.out.print("\nInsert your account's password :");
            String pass=STR.next();
            
            if(x.validateUser(u, pass))
            {
            x.deleteNewUser(u,pass);
            System.out.println("\nThe Account has been deleted Successfully");
            flag=false;
            }
            else{
                System.out.println("Invalid User/password");
            }
        }
        case 4 -> {
            System.out.println("\nThanks for using LuftHansa HUB. Goodbye!");
            flag=false;
        }
        default -> 
        {
            System.out.println("\nOut Of Range Choice\n\nType a valid choice\n");
        }
    }
}
        if (currentUser != null) {
    switch (currentUser) {

        case AdminInfo adminInfo -> adminInfo.adminMenu(adminInfo);

        case TravelAgentInfo travelAgentInfo -> travelAgentInfo.agentMenu(travelAgentInfo);

        case UserInfo userInfo -> userInfo.userMenu(userInfo);
        
        default -> {}
    }
        }
        if (currentUser != null) 
        {
            currentUser.logOut();
            in.close();
            STR.close();
            str.close();
        }
    }
    public static void dis()
    {
        System.out.println("======== MAIN MENU ========");
        System.out.println("1. Sign Up (Create an Account)");
        System.out.println("2. Sign In (Login to Existing Account)");
        System.out.println("3. Delete Account");
        System.out.println("4. Exit");
        System.out.println("===========================\n");
    }
}   