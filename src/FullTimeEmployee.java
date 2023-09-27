
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

public class FullTimeEmployee extends BaseEmployee implements Serializable {

	private boolean has_task;
	private List<Task> listOfAssignedTasks = new ArrayList<>();
	private ArrayList<BaseEmployee> listOfEmployee = new ArrayList<>();
	private ArrayList<Task> listOfTasksAssignedBy = new ArrayList<>();

	public FullTimeEmployee(String name, String username, String password, String ManagerUserName, int Type) {
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
	public void addTasksAssignedBy(Task t) {
		listOfTasksAssignedBy.add(t);
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
	public boolean getHasTask() {
		return has_task;
	}

	@Override
	public void setHasTask(boolean has_task) {
		this.has_task = has_task;
	}

	@Override
	public String toString() {
		return "--------------------------------\n" + this.getName() + "\n" + this.getUsername() + "\n"
				+ this.getPassword() + "\n" + this.getHasTask() + "\n--------------------------------\n";
	}

	@Override
	public void addAssignedTasks(Task t) {
		if (!this.getHasTask()) {
			listOfAssignedTasks.add(t);
			this.setHasTask(true);
		} else {
			JOptionPane.showMessageDialog(null, "This Parttime Employee has an undone job!", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public boolean assignedTasks() {
		boolean res = false;
		Iterator<Task> iterator = (Iterator<Task>) listOfAssignedTasks.iterator();
		while (iterator.hasNext()) {
			res = true;
			Task t = (Task) iterator.next();
			JOptionPane.showMessageDialog(null, t.toString());
		}
		return res;
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
		}
		return null;
	}
}
