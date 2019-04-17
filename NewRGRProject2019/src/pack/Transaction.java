package pack;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import stat.Histo;

public class Transaction extends Actor {
	// Флаг справності телевізору
	private boolean workable;
	// Флаг, котрий указує, що телевізор перевірено
	private boolean serviceCheck;
	// Флаг, котрий указує, що телевізор налаштовано
	private boolean serviceConf;

	// Гістограми для збереження часу очікування в черзі
	private Histo histoQueueCheck;
	private Histo histoQueueConf;

	// Гістограми для збереження часу для обробки телевізора
	private Histo histoProcessCheck;
	private Histo histoProcessConf;

	// Час створення транзакції
	private double createTime;

	// Черги
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
		nameForProtocol = "Транзакція " + createTime;
		while (true) {
			this.serviceCheck = false;
			this.serviceConf = false;

			queueCheck.add(this);
			//waitForCondition(() -> !queueCheck.contains(this), "мають забрати на перевірку");
			waitForCondition(() -> serviceCheck, "мають завершити перевірку");
			histoQueueCheck.add(dispatcher.getCurrentTime() - createTime);
			//histoProcessCheck.add(dispatcher.getCurrentTime() - createTime);
			if (this.workable) {
				dispatcher.printToProtocol(getNameForProtocol() + " Справний!");
				return;
			} else {
				queueConf.add(this);
				//waitForCondition(() -> !queueConf.contains(this), "мають забрати на налаштування");
				createTime = dispatcher.getCurrentTime();
				waitForCondition(() -> serviceConf, "мають завершити налаштування");
				histoQueueConf.add(dispatcher.getCurrentTime() - createTime);
				//histoProcessConf.add(dispatcher.getCurrentTime() - createTime);
			}
		}
	}

}
