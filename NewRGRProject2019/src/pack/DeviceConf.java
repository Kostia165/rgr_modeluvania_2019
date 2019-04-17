package pack;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class DeviceConf extends Actor {

	// ����� ��� ���������
	private QueueForTransactions<Transaction> queue;
	// ��������� ����, �� ������� ������ �� �������������� ����������
	private Randomable rnd;
	// ��� ������ ����������
	private double finishTime;

	public DeviceConf(String name, Gui gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataNumConf().getDouble();
		rnd = gui.getChooseRandomConfQueue();
		queue = model.getQueueConf();
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
			transaction.setWorkable(true);
			transaction.setServiceConf(true);
		}
	}

}
