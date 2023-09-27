
import java.io.Serializable;
import java.net.Socket;

public abstract class BaseEmployee implements Serializable {

	private int type;// 1: Manager / 2: Full Time Employee / 3: Part Time Employee
	private String name;
	private String username;
	private String password;
	private String ManagerUN;
	private Socket connection;

	public BaseEmployee(String name, String username, String password, String ManagerUserName, int Type) {
		this.name = name;
		this.username = username;
		this.password = password;
		this.ManagerUN = ManagerUserName;
		this.type = Type;
	}

	public void setConnection(Socket connection) {
		this.connection = connection;
	}

	public boolean checkInfo(String username, String password) {
		return this.username.equals(username) && this.password.equals(password);
	}

	boolean isLoggedIn() {
		return connection != null;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getManagerUserName() {
		return ManagerUN;
	}

	public void setManagerUserName(String ManagerUserName) {
		this.ManagerUN = ManagerUserName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getHasTask() {
		throw new UnsupportedOperationException();
	}

	public void setHasTask(boolean hasTask) {
		throw new UnsupportedOperationException();
	}

	public void printEmployees() {
		throw new UnsupportedOperationException();
	}

	public void addEmployee(BaseEmployee employee) {
		throw new UnsupportedOperationException();
	}

	public BaseEmployee removeEmployee(BaseEmployee employee) {
		throw new UnsupportedOperationException();
	}

	public boolean assignedTasks() {
		throw new UnsupportedOperationException();
	}

	public void addAssignedTasks(Task t) {
		throw new UnsupportedOperationException();
	}

	public boolean tasksAssignedBy(int choice) {
		throw new UnsupportedOperationException();
	}

	public void addTasksAssignedBy(Task t) {
		throw new UnsupportedOperationException();
	}

	public void getReports(int choice) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		throw new UnsupportedOperationException();
	}

	public boolean isEmployee(BaseEmployee emp) {
		throw new UnsupportedOperationException();
	}

	public Task getTaskById(int id, int type) {
		throw new UnsupportedOperationException();
	}

}