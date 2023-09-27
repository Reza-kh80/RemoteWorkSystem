
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FilleManager {

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Attributes
    private static ArrayList<BaseEmployee> listOfEmployees = new ArrayList<>();
    private static ArrayList<String> UserNames = new ArrayList<>();

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ Constructors
    public FilleManager() throws FileNotFoundException {
        File userNamesFile = new File("Files\\userNames.txt");

        Scanner input = new Scanner(userNamesFile);
        while (input.hasNext()) {
            UserNames.add(input.nextLine());
        }
    }// end of Constructor

    public void saveEmployee(ArrayList<BaseEmployee> employees) throws FileNotFoundException {

        File employeeList = new File("Files\\EmployeInfo.txt");
        PrintWriter write = new PrintWriter(employeeList);

        Iterator<BaseEmployee> iterator = (Iterator<BaseEmployee>) employees.iterator();
        while (iterator.hasNext()) {
            BaseEmployee em = (BaseEmployee) iterator.next();

            String Position;
            if (em.getType() == 1) {
                Position = "Manager";
            } else if (em.getType() == 2) {
                Position = "Full Time Employee";
            } else {
                Position = "Part Time Employee";
            }

            write.append("------------------------\n");
            write.append("^\n");
            write.append("User Name : #" + em.getUsername() + "\n");
            write.append("Name      : #" + em.getName() + "\n");
            write.append("Password  : #" + em.getPassword() + "\n");
            write.append("***Employee of " + em.getManagerUserName() + "\n");
            write.append("***Hired as " + Position + "      ////Type : #" + em.getType() + "\n");
            write.append("------------------------\n");
            write.flush();
        }
        write.close();

    }

    public ArrayList<BaseEmployee> getSavedEmployee() throws FileNotFoundException {

        File employeeList = new File("Files\\EmployeInfo.txt");
        Scanner in = new Scanner(employeeList);

        BaseEmployee employee;
        while (in.hasNextLine()) {
            String temp = in.nextLine();
            if (temp.contains("^")) {
                String User = in.nextLine();
                User = User.substring(User.indexOf('#') + 1, User.length());
                String Name = in.nextLine();
                Name = Name.substring(Name.indexOf('#') + 1, Name.length());
                String Pass = in.nextLine();
                Pass = Pass.substring(Pass.indexOf('#') + 1, Pass.length());
                String managerUserName = in.nextLine();
                managerUserName = managerUserName.substring(managerUserName.indexOf('f') + 1, managerUserName.length());

                String Tp = in.nextLine();
                Tp = Tp.substring(Tp.indexOf('#') + 1, Tp.length());
                int Type = Integer.parseInt(Tp);

                if (Type == 1) {
                    employee = new Manager(Name, User, Pass, managerUserName, Type);
                } else if (Type == 2) {
                    employee = new FullTimeEmployee(Name, User, Pass, managerUserName, Type);
                } else {
                    employee = new ParttimeEmployee(Name, User, Pass, managerUserName, Type);
                }

                this.listOfEmployees.add(employee);
            }
        }

        return listOfEmployees;
    }

    public void saveUserName(String userName) throws FileNotFoundException {

        UserNames.clear();
        File userNamesFile = new File("Files\\userNames.txt");

        Scanner input = new Scanner(userNamesFile);
        while (input.hasNext()) {
            UserNames.add(input.nextLine());
        }
        UserNames.add(userName);

        PrintWriter print = new PrintWriter(userNamesFile);
        Iterator<String> iterator = (Iterator<String>) UserNames.iterator();
        while (iterator.hasNext()) {
            String p = (String) iterator.next();
            print.append(p + "\n");
        }

        print.flush();
        print.close();

    }

    public boolean checkUserNameInventory(String UserName) {

        if (UserNames.contains(UserName)) {
            return true;
        }

        return false;
    }

}
