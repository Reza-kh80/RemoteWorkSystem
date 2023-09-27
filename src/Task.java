
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Task {

	private Date deadline;
	private String body;
	private String status;
	private Report report;
	private static final AtomicInteger atomic = new AtomicInteger(0);
	private int id;
	private BaseEmployee assignedTo;
	private BaseEmployee assignedBy;

	public Task(String body, Date deadline, BaseEmployee assignedTo, BaseEmployee assignedBy) {
		this.body = body;
		this.deadline = deadline;
		this.status = "UnDone";
		this.id = atomic.incrementAndGet();
		this.assignedTo = assignedTo;
		this.assignedBy = assignedBy;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public BaseEmployee getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(BaseEmployee emp) {
		this.assignedTo = emp;
	}

	public BaseEmployee getAssignedBy() {
		return assignedBy;
	}

	public void setAssignedBy(BaseEmployee emp) {
		this.assignedBy = emp;
	}

	public Report getReport() {
		return report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	@Override
	public String toString() {
		return "--------------------------------\n" + "ID:" + this.getId() + " " + this.getStatus() + " "
				+ this.getDeadline() + "\n" + "Assigned by " + this.getAssignedBy().getName() + " to "
				+ this.getAssignedTo().getName() + "\n" + this.getBody() + "\n--------------------------------\n";
	}

	Object getStatue() {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

}