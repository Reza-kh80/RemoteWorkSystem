
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*import NecessaryClasses.BaseEmployee;
import NecessaryClasses.FilleManager;
import NecessaryClasses.FullTimeEmployee;
import NecessaryClasses.Manager;
import NecessaryClasses.ParttimeEmployee;
import NecessaryClasses.Report;
import NecessaryClasses.Task;*/
public class Main extends Thread {

    private static Scanner input = new Scanner(System.in);

    private static boolean loggedIn = false;
    private static ArrayList<BaseEmployee> listOfEmployees = new ArrayList<>();
    private static ArrayList<Task> listOfTasks = new ArrayList<>();
    private static Manager manager = null;
    private static FilleManager fleManage;

    @Override
    public void run() {
        System.out.println("LoginManager started, waiting for client");
        try {
            ServerSocket server = new ServerSocket(9836);
            while (true) {
                Socket connection = server.accept();
                InputStream inputStream = connection.getInputStream();
                DataInputStream reader = new DataInputStream(inputStream);

                String username = reader.readUTF();
                String password = reader.readUTF();
                BaseEmployee connected = null;
                for (BaseEmployee e : listOfEmployees) {
                    if (e.checkInfo(username, password)) {
                        connected = e;
                    }
                }
                boolean isCorrect = connected != null;

                if (isCorrect) {
                    connected.setConnection(connection);
                }

                OutputStream outputSteam = connection.getOutputStream();
                DataOutputStream writer = new DataOutputStream(outputSteam);
                writer.writeBoolean(isCorrect);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        fleManage = new FilleManager();
        listOfEmployees = fleManage.getSavedEmployee();

        Thread Main = new Main();
        Main.start();
        System.out.println("MAIN THREAD STILL RUNNING");

        Iterator<BaseEmployee> mn = (Iterator<BaseEmployee>) listOfEmployees.iterator();
        while (mn.hasNext()) {
            manager = (Manager) mn.next();
            if (manager.getType() == 1) {
                break;
            }
        }

        BaseEmployee currentUser = new Manager("fake", "fake", "fake", null, 0);

        while (!loggedIn) {

            currentUser = login();
            if (currentUser != null) {
                JOptionPane.showMessageDialog(null, "Welcome " + currentUser.getName() + ".", "Welcome",
                        JOptionPane.INFORMATION_MESSAGE);

                JOptionPane.showMessageDialog(null, "Press Enter to continue...");
            } // end of condition

        } // end of loop

        while (loggedIn) {

            String cho = JOptionPane.showInputDialog(null,
                    "1. Add New Employee\n" + "2. Add New Task\n" + "3. List Tasks Assigned By You\n"
                    + "4. List Tasks Assigend To You\n" + "5. Edit your Employees\n" + "6. Logout",
                    "Select", JOptionPane.INFORMATION_MESSAGE);
            int choice = Integer.parseInt(cho);

            switch (choice) {

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                case 1:
                    consoleMessage("## Message: Wating for choosing employee type ...");
                    int fullOrPart = 0;
                    String a = JOptionPane.showInputDialog(null, "1. Fulltime\n" + "2. Parttime",
                            "Choose employee type(enter the number):", JOptionPane.QUESTION_MESSAGE);
                    fullOrPart = Integer.parseInt(a);
                    consoleMessage("## Message: the type choosed ...");

                    consoleMessage("## Message: Now, wating for entering the new employees info ...");
                    String name = JOptionPane.showInputDialog(null, "Enter Name:", "Name");
                    String user = JOptionPane.showInputDialog(null, "Enter username:\n\n"
                            + "Warning: Please be careful, the user name should not be duplicated(every one has own user name).\n",
                            "User Name", JOptionPane.QUESTION_MESSAGE);
                    if (fleManage.checkUserNameInventory(user)) {
                        while (fleManage.checkUserNameInventory(user)) {
                            JOptionPane.showMessageDialog(null,
                                    "User Name already exist, please try agane and choose another.", "Repittive User Name",
                                    JOptionPane.ERROR_MESSAGE);
                            user = JOptionPane.showInputDialog(null, "Enter username again:\n\n"
                                    + "Warning: Please be careful, the user name should not be duplicated(every one has own user name).\n",
                                    "User Name", JOptionPane.QUESTION_MESSAGE);
                        }
                        fleManage.saveUserName(user);
                    } else {
                        fleManage.saveUserName(user);
                    }

                    String pass = JOptionPane.showInputDialog(null, "Enter password:", "Password");
                    consoleMessage("## Message: the info add succesfully ...");

                    BaseEmployee employee;
                    if (fullOrPart == 1) {
                        employee = new FullTimeEmployee(name, user, pass, currentUser.getUsername(), 2);
                    } else {
                        employee = new ParttimeEmployee(name, user, pass, currentUser.getUsername(), 3);
                    }
                    currentUser.addEmployee(employee);
                    listOfEmployees.add(employee);
                    fleManage.saveEmployee(listOfEmployees);
                    JOptionPane.showMessageDialog(null, "Employee created successfully!");
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ creation
                case 2:
                    consoleMessage("## Message: Wating for task creation ...");

                    String body = JOptionPane.showInputDialog(null, "Enter task body:");

                    JOptionPane.showMessageDialog(null, "Enter task deadline[Enter OK]");
                    String yea = JOptionPane.showInputDialog(null, "Enter year:");
                    int year = Integer.parseInt(yea);
                    String mont = JOptionPane.showInputDialog(null, "Enter month (1-12):");
                    int month = Integer.parseInt(mont);
                    String da = JOptionPane.showInputDialog(null, "Enter day:");
                    int day = Integer.parseInt(da);
                    Calendar myCal = Calendar.getInstance();
                    myCal.set(Calendar.YEAR, year);
                    myCal.set(Calendar.MONTH, month - 1);
                    myCal.set(Calendar.DAY_OF_MONTH, day);
                    Date deadline = myCal.getTime();

                    String name1 = JOptionPane.showInputDialog(null, "Enter the name of employee to assign the task to :");

                    Iterator<BaseEmployee> iterator = (Iterator<BaseEmployee>) listOfEmployees.iterator();
                    boolean created = false;
                    boolean access_user = false;
                    while (iterator.hasNext()) {
                        BaseEmployee e = (BaseEmployee) iterator.next();
                        if (e.getName().equals(name1)) {
                            if (currentUser.isEmployee(e)) {
                                if (e instanceof ParttimeEmployee && e.getHasTask()) {
                                    JOptionPane.showMessageDialog(null, "This parttime employee already has a task!",
                                            "Warnning", JOptionPane.WARNING_MESSAGE);
                                    access_user = true;
                                    break;
                                } else {
                                    Task task = new Task(body, deadline, e, currentUser);
                                    listOfTasks.add(task);
                                    e.addAssignedTasks(task);
                                    currentUser.addTasksAssignedBy(task);
                                    created = true;
                                    access_user = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!access_user) {
                        JOptionPane.showMessageDialog(null, "No employee with this name or he/she is not your supervisee!",
                                "Warnning", JOptionPane.WARNING_MESSAGE);
                    }
                    if (created) {
                        JOptionPane.showMessageDialog(null, "Task created and assigned successfully!");
                        consoleMessage("## Message: new task created succesfully ...");
                    } else {
                        JOptionPane.showMessageDialog(null, "Task did not get created!");
                        consoleMessage("## Message: not created ...");
                    }
                    JOptionPane.showMessageDialog(null, "Press Enter to continue...");
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                case 3:
                    consoleMessage("## Message: wating for choice ...");
                    String tasktyp = JOptionPane.showInputDialog(null,
                            "1. All tasks\n" + "2. UnDone tasks\n" + "3. Done tasks\n" + "4. Expired tasks\n",
                            "Enter the tasks you want to list", JOptionPane.QUESTION_MESSAGE);

                    int taskType = Integer.parseInt(tasktyp);
                    boolean found_task = currentUser.tasksAssignedBy(taskType);
                    if (found_task) {
                        String identifie = JOptionPane.showInputDialog(null,
                                "Enter task Id to see it's report or change it's status:");
                        int identifier = Integer.parseInt(identifie);
                        Task tsk = currentUser.getTaskById(identifier, 4);
                        if (tsk != null) {
                            JOptionPane.showMessageDialog(null, tsk.toString());
                            if (tsk.getReport() != null) {
                                JOptionPane.showMessageDialog(null, "Report:\n" + tsk.getReport().toString());
                            } else {
                                JOptionPane.showMessageDialog(null, "No report for this task!");
                            }
                            String c = JOptionPane.showInputDialog(null,
                                    "Change status to: \n1.UnDone \n2.Done \n3.Expired\n", "Select",
                                    JOptionPane.QUESTION_MESSAGE);
                            int ch = Integer.parseInt(c);
                            if (ch == 1) {
                                tsk.setStatus("UnDone");
                                JOptionPane.showMessageDialog(null, "Status changed successfully");
                            } else if (ch == 2) {
                                tsk.setStatus("Done");
                                if (tsk.getAssignedTo() instanceof ParttimeEmployee) {
                                    tsk.getAssignedTo().setHasTask(false);
                                }
                                JOptionPane.showMessageDialog(null, "Status changed successfully");
                            } else if (ch == 3) {
                                tsk.setStatus("Expired");
                                JOptionPane.showMessageDialog(null, "Status changed successfully");
                            } else {
                                JOptionPane.showMessageDialog(null, "Invalid status!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Id!");
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Press Enter to continue...");
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                case 4:
                    found_task = currentUser.assignedTasks();
                    if (found_task) {
                        String identifie = JOptionPane.showInputDialog(null, "Enter task Id to write report for:");
                        int identifier = Integer.parseInt(identifie);
                        Task tsk = currentUser.getTaskById(identifier, 3);
                        if (tsk != null) {
                            JOptionPane.showMessageDialog(null, tsk.toString());
                            if (tsk.getReport() != null) {
                                JOptionPane.showMessageDialog(null, "Current report for this task:");
                                JOptionPane.showMessageDialog(null, tsk.getReport().toString());
                            } else {
                                JOptionPane.showMessageDialog(null, "No report for this task!");
                            }
                            String c = JOptionPane.showInputDialog(null,
                                    "Do you want to write report for this? \n1.Yes \n2.No\n", "Selsect",
                                    JOptionPane.QUESTION_MESSAGE);
                            int ch = Integer.parseInt(c);
                            if (ch == 1) {
                                String tit = JOptionPane.showInputDialog(null, "Enter report title:");
                                String bod = JOptionPane.showInputDialog(null, "Enter report body:");
                                Report rep = new Report(tit, bod);
                                tsk.setReport(rep);
                                JOptionPane.showMessageDialog(null, "Report Sent!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Report unchanged!");
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Incorrect Id!");
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Press Enter to continue...");
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                case 5:
                    consoleMessage("## Message: editing employees ...");
                    String[] allOfMembers = new String[10000];
                    int cMembers = 0;
                    BaseEmployee empl1 = null;
                    Iterator<BaseEmployee> it1 = (Iterator<BaseEmployee>) listOfEmployees.iterator();
                    while (it1.hasNext()) {
                        empl1 = (BaseEmployee) it1.next();
                        allOfMembers[cMembers++] = empl1.getName() + "     /User Name: # " + empl1.getUsername();
                    }
                    consoleMessage("## Message: program is loading ...");
                    String userName = (String) JOptionPane.showInputDialog(null,
                            "Choose the employee that you want to edit him/her info.\n", "Find Employee",
                            JOptionPane.INFORMATION_MESSAGE, null, allOfMembers, allOfMembers[1]);
                    consoleMessage("## Message: program loaded ...");
                    userName = (String) userName.substring(userName.indexOf('#') + 2, userName.length());

                    Iterator<BaseEmployee> it2 = (Iterator<BaseEmployee>) listOfEmployees.iterator();
                    while (it2.hasNext()) {
                        empl1 = (BaseEmployee) it2.next();
                        String temp = (String) empl1.getUsername();
                        if (temp.equals(userName)) {
                            break;
                        }
                    }

                    String p = "";
                    if (empl1 != null) {
                        p = JOptionPane.showInputDialog(null,
                                "User: " + empl1.getUsername() + "\nName: " + empl1.getName() + "\nPass: "
                                + empl1.getPassword() + "\nManager User Name : " + empl1.getManagerUserName()
                                + "\nState: " + ((empl1.getHasTask()) ? "Working" : "have No task")
                                + "\n\n\n\n1. Show Members(Part time employees) of this employee.\n"
                                + "2. Delete this employee\n" + "3. Change Password\n" + "4. Change Name\n"
                                + "5. Change User Name\n" + "6. Change Manager\n\n",
                                JOptionPane.QUESTION_MESSAGE);
                    }
                    int i = Integer.parseInt(p);

                    String[] curentEmployeeMembers = new String[10000];
                    int cCurentEmployeeMembers = 0;
                    Iterator<BaseEmployee> it3 = (Iterator<BaseEmployee>) listOfEmployees.iterator();
                    BaseEmployee empl2 = null;
                    if (i == 1) {
                        while (it3.hasNext()) {
                            empl2 = (BaseEmployee) it3.next();
                            String temp2 = empl2.getManagerUserName();
                            if (temp2.equals(empl1.getManagerUserName())) {
                                curentEmployeeMembers[cCurentEmployeeMembers++] = "Name: " + empl2.getName() + "/UserName: "
                                        + empl2.getUsername();
                            }
                        }
                        String g;
                        if (cCurentEmployeeMembers == 0) {
                            JOptionPane.showMessageDialog(null, "This Employee has no member");
                        } else {
                            g = (String) JOptionPane.showInputDialog(null,
                                    "Choose the employee that you want to edit him/her info.\n", "Find Employee",
                                    JOptionPane.INFORMATION_MESSAGE, null, curentEmployeeMembers, curentEmployeeMembers[1]);
                            g = g.substring(g.indexOf('#') + 1);

                            String f = JOptionPane.showInputDialog(null,
                                    "\n1. delet Employee\n" + "2. Change this employes admin\n");
                            int chh = Integer.parseInt(f);
                            if (chh == 1) {
                                int index = listOfEmployees.indexOf(empl2);
                                int confirm = JOptionPane.showConfirmDialog(null,
                                        "Are you sure you want to delet this employee?\n\n");
                                if (confirm == 0) {
                                    listOfEmployees.remove(index);
                                } else {
                                    consoleMessage("## Message: the employee not deleted ...");
                                }
                            } else if (chh == 2) {
                                empl1.setManagerUserName(
                                        JOptionPane.showInputDialog("Enter the new manager user name to this employee:"));
                            }
                        }
                    } else if (i == 2) {
                        int index = listOfEmployees.indexOf(empl1);
                        int confirm = JOptionPane.showConfirmDialog(null,
                                "Are you sure you want to delet this employee?\n\n");
                        if (confirm == 0) {
                            listOfEmployees.remove(index);
                        } else {
                            consoleMessage("## Message: the employee not deleted ...");
                        }
                    } else if (i == 3) {
                        empl1.setPassword(JOptionPane.showInputDialog("Enter the new password to this employee:"));
                    } else if (i == 4) {
                        empl1.setName(JOptionPane.showInputDialog("Enter the new name to this employee:"));
                    } else if (i == 5) {
                        String u = JOptionPane.showInputDialog("Enter the new user name to this employee:");
                        if (fleManage.checkUserNameInventory(u)) {
                            while (fleManage.checkUserNameInventory(u)) {
                                JOptionPane.showMessageDialog(null,
                                        "User Name already exist, please try agane and choose another.",
                                        "Repittive User Name", JOptionPane.ERROR_MESSAGE);
                                u = JOptionPane.showInputDialog(null, "Enter username again:\n\n"
                                        + "Warning: Please be careful, the user name should not be duplicated(every one has own user name).\n",
                                        "User Name", JOptionPane.QUESTION_MESSAGE);
                            }
                            fleManage.saveUserName(u);
                        }

                    } else if (i == 6) {
                        empl1.setManagerUserName(
                                JOptionPane.showInputDialog("Enter the new manager user name to this employee:"));
                    }

                    fleManage.saveEmployee(listOfEmployees);
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                case 6:
                    loggedIn = logout();
                    String ch = JOptionPane.showInputDialog(null, "Enter your choice: \n1.Log in\n2.Exit", "Selsect",
                            JOptionPane.QUESTION_MESSAGE);
                    int chc = Integer.parseInt(ch);
                    while (!loggedIn && chc == 1) {
                        currentUser = login();
                        if (currentUser != null) {
                            JOptionPane.showMessageDialog(null, "Welcome " + currentUser.getName() + ".", "Welcome",
                                    JOptionPane.INFORMATION_MESSAGE);
                            JOptionPane.showMessageDialog(null, "Press Enter to continue...");
                        }
                    }
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                default:
                    JOptionPane.showMessageDialog(null, "Enter a correct choice!", "Warning", JOptionPane.WARNING_MESSAGE);
                    break;
                // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            }// end of switch

        } // end of loop

    }// end of main

    public static BaseEmployee login() {

        consoleMessage("## Message: User is logining...");

        String user = JOptionPane.showInputDialog(null,
                "Please be careful this program is only for Admin, if you are a employee please use the other program.\n\n"
                + "Enter your username:",
                "Log In: Admin Program", JOptionPane.INFORMATION_MESSAGE);

        String pass = JOptionPane.showInputDialog(null, "Enter your password:", "Log Iin: Admin Program",
                JOptionPane.INFORMATION_MESSAGE);

        if (manager.getUsername().equals(user) && manager.getPassword().equals(pass)) {
            loggedIn = true;
            return manager;
        }

        return null;
    }// end of login

    public static void consoleMessage(String msg) {
        System.out.print(msg + "\n");
    }// end of clearScreen

    public static boolean logout() {
        return false;
    }// end of logout
}
