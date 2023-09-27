
import java.io.Serializable;

import javax.swing.JOptionPane;

public class ParttimeEmployee extends BaseEmployee implements Serializable {

	private boolean has_task;
	private Task task;

	public ParttimeEmployee(String name, String username, String password, String ManagerUserName, int Type) {
		super(name, username, password, ManagerUserName, Type);
		this.has_task = false;
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
			task = t;
			this.setHasTask(true);
		} else {
			JOptionPane.showMessageDialog(null, "This Parttime Employee has an undone job!", "Warning",
					JOptionPane.WARNING_MESSAGE);
		}
	}

	@Override
	public boolean assignedTasks() {
		if (getHasTask()) {
			JOptionPane.showInternalMessageDialog(null, task.toString(), "Employee Status",
					JOptionPane.INFORMATION_MESSAGE);
			return true;
		}

		JOptionPane.showInternalMessageDialog(null, "No task assigned to this employee", "Employee Status",
				JOptionPane.INFORMATION_MESSAGE);
		return false;
	}

	public Task getTask() {
		return (getHasTask()) ? task : null;
	}
}
