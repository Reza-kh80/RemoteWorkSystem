
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JOptionPane;

public class Manager extends BaseEmployee implements Serializable {

	private ArrayList<Task> listOfTasksAssignedBy = new ArrayList<>();
	private ArrayList<BaseEmployee> listOfEmployee = new ArrayList<>();
	private ArrayList<Task> listOfAssignedTasks = new ArrayList<>();

	public Manager(String name, String username, String password, String ManagerUserName, int Type) {
		super(name, username, password, ManagerUserName, Type);
	}

	@Override
	public void addEmployee(BaseEmployee employee) {
		listOfEmployee.add(employee);
	}

	@Override
	public BaseEmployee removeEmployee(BaseEmployee employee) {
		int index = listOfEmployee.indexOf(employee);
		BaseEmployee emp = listOfEmployee.get(index);
		listOfEmployee.remove(index);
		return emp;
	}

	@Override
	public void printEmployees() {
		Iterator<BaseEmployee> iterator = (Iterator<BaseEmployee>) listOfEmployee.iterator();
		while (iterator.hasNext()) {
			BaseEmployee e = (BaseEmployee) iterator.next();
			JOptionPane.showMessageDialog(null, e.toString());
		}
	}

	@Override
	public String toString() {
		return "--------------------------------\n" + this.getName() + "\n" + this.getUsername() + "\n"
				+ this.getPassword() + "\n--------------------------------\n";
	}

	@Override
	public void addTasksAssignedBy(Task t) {
		listOfTasksAssignedBy.add(t);
	}

	@Override
	public void addAssignedTasks(Task t) {
		listOfAssignedTasks.add(t);
	}

	@Override
	public boolean assignedTasks() {
		boolean result = false;
		Iterator<Task> iterator = (Iterator<Task>) listOfAssignedTasks.iterator();
		while (iterator.hasNext()) {
			result = true;
			Task t = (Task) iterator.next();
			JOptionPane.showMessageDialog(null, t.toString());
		}
		return result;
	}

	@Override
	public boolean tasksAssignedBy(int choice) {
		boolean result = false;
		Iterator<Task> iterator = (Iterator<Task>) listOfTasksAssignedBy.iterator();
		while (iterator.hasNext()) {
			result = true;
			Task t = (Task) iterator.next();
			if (choice == 1)
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 2 && t.getStatue().equals("UnDone"))
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 3 && t.getStatus().equals("Done"))
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 4 && t.getStatus().equals("Expired"))
				JOptionPane.showMessageDialog(null, t.toString());
		}
		return result;
	}

	@Override
	public void getReports(int choice) {
		Iterator<Task> iterator = (Iterator<Task>) listOfTasksAssignedBy.iterator();
		while (iterator.hasNext()) {
			Task t = (Task) iterator.next();
			if (choice == 1 && t.getReport() != null)
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 2 && t.getStatus().equals("UnDone") && t.getReport() != null)
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 3 && t.getStatus().equals("Done") && t.getReport() != null)
				JOptionPane.showMessageDialog(null, t.toString());
			else if (choice == 4 && t.getStatus().equals("Expired") && t.getReport() != null)
				JOptionPane.showMessageDialog(null, t.toString());
		}
	}

	@Override
	public boolean isEmployee(BaseEmployee emp) {
		Iterator<BaseEmployee> iterator = (Iterator<BaseEmployee>) listOfEmployee.iterator();
		while (iterator.hasNext()) {
			BaseEmployee e = (BaseEmployee) iterator.next();
			if (e.getName().equals(emp.getName()))
				return true;
		}
		return false;
	}

	@Override
	public Task getTaskById(int id, int type) {
		if (type == 3) {
			Iterator<Task> iterator = (Iterator<Task>) listOfAssignedTasks.iterator();
			while (iterator.hasNext()) {
				Task t = (Task) iterator.next();
				if (t.getId() == id) {
					return t;
				}
			}
		} else {
			Iterator<Task> iterator = (Iterator<Task>) listOfTasksAssignedBy.iterator();
			while (iterator.hasNext()) {
				Task t = (Task) iterator.next();
				if (t.getId() == id) {
					return t;
				}
			}
		}
		return null;
	}
}