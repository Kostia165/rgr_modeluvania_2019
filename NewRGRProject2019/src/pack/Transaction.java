package pack;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import stat.Histo;

public class Transaction extends Actor {
	// ���� ��������� ���������
	private boolean workable;
	// ����, ������ �����, �� �������� ���������
	private boolean serviceCheck;
	// ����, ������ �����, �� �������� �����������
	private boolean serviceConf;

	// ó�������� ��� ���������� ���� ���������� � ����
	private Histo histoQueueCheck;
	private Histo histoQueueConf;

	// ó�������� ��� ���������� ���� ��� ������� ���������
	private Histo histoProcessCheck;
	private Histo histoProcessConf;

	// ��� ��������� ����������
	private double createTime;

	// �����
	private QueueForTransactions<Transaction> queueCheck;
	private QueueForTransactions<Transaction> queueConf;

	public boolean isWorkable() {
		return workable;
	}

	public void setWorkable(boolean workable) {
		this.workable = workable;
	}

	public Transaction(Model model) {
		this.queueCheck = model.getQueueCheck();
		this.queueConf = model.getQueueConf();
		this.histoQueueCheck = model.getDiagramTimeForCheckQueue();
		this.histoQueueConf = model.getDiagramTimeForConfQueue();
		this.histoProcessCheck = model.getHistoTimeProcessCheck();
		this.histoProcessConf = model.getHistoTimeProcessConf();
	}

	public double getCreateTime() {
		return createTime;
	}

	public void setServiceCheck(boolean serviceCheck) {
		this.serviceCheck = serviceCheck;
	}

	public void setServiceConf(boolean serviceConf) {
		this.serviceConf = serviceConf;
	}

	@Override
	public String toString() {
		return "Transaction " + createTime;
	}

	@Override
	protected void rule() throws DispatcherFinishException {
		createTime = dispatcher.getCurrentTime();
		nameForProtocol = "���������� " + createTime;
		while (true) {
			this.serviceCheck = false;
			this.serviceConf = false;

			queueCheck.add(this);
			//waitForCondition(() -> !queueCheck.contains(this), "����� ������� �� ��������");
			waitForCondition(() -> serviceCheck, "����� ��������� ��������");
			histoQueueCheck.add(dispatcher.getCurrentTime() - createTime);
			//histoProcessCheck.add(dispatcher.getCurrentTime() - createTime);
			if (this.workable) {
				dispatcher.printToProtocol(getNameForProtocol() + " ��������!");
				return;
			} else {
				queueConf.add(this);
				//waitForCondition(() -> !queueConf.contains(this), "����� ������� �� ������������");
				createTime = dispatcher.getCurrentTime();
				waitForCondition(() -> serviceConf, "����� ��������� ������������");
				histoQueueConf.add(dispatcher.getCurrentTime() - createTime);
				//histoProcessConf.add(dispatcher.getCurrentTime() - createTime);
			}
		}
	}

}
