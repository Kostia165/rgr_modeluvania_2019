package pack;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class DeviceCheck extends Actor {

	// ����� ��� ���������
	private QueueForTransactions<Transaction> queue;
	// ��������� ����, �� ������� ������ �� �������������� ����������
	private Randomable rnd;
	// ��� ������ ����������
	private double finishTime;

	public DeviceCheck(String name, Gui gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataNumCheck().getDouble();
		rnd = gui.getChooseRandomCheckQueue();
		queue = model.getQueueCheck();

	}

	@Override
	protected void rule() throws DispatcherFinishException {
		// ��������� �����, ��������� ��� ���� ������ �����
		BooleanSupplier queueSize = () -> queue.size() > 0;
		// ���� ��������� ������ 䳿
		while (getDispatcher().getCurrentTime() <= finishTime) {
			// �������� �������� ���������� �� ������� �� �� �����
			waitForCondition(queueSize, "� ���� �� �'������� ����������");
			Transaction transaction = queue.removeFirst();
			// ������� ������� ����������
			holdForTime(rnd.next());
			transaction.setServiceCheck(true);
		}
	}

}
