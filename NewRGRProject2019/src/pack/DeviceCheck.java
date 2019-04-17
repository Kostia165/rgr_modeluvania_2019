package pack;

import java.util.function.BooleanSupplier;

import process.Actor;
import process.DispatcherFinishException;
import process.QueueForTransactions;
import rnd.Randomable;

public class DeviceCheck extends Actor {

	// Черга для тразакцій
	private QueueForTransactions<Transaction> queue;
	// Генератор часу, що витрачає прилад на обслуговування транзакції
	private Randomable rnd;
	// Час роботи генератора
	private double finishTime;

	public DeviceCheck(String name, Gui gui, Model model) {
		setNameForProtocol(name);
		finishTime = gui.getChooseDataNumCheck().getDouble();
		rnd = gui.getChooseRandomCheckQueue();
		queue = model.getQueueCheck();

	}

	@Override
	protected void rule() throws DispatcherFinishException {
		// Створюємо умову, виконання якої буде чекати актор
		BooleanSupplier queueSize = () -> queue.size() > 0;
		// цикл виконання правил дії
		while (getDispatcher().getCurrentTime() <= finishTime) {
			// Перевірка наявності транзакції та чекання на її появу
			waitForCondition(queueSize, "у черзі має з'явиться транзакція");
			Transaction transaction = queue.removeFirst();
			// Імітація обробки транзакції
			holdForTime(rnd.next());
			transaction.setServiceCheck(true);
		}
	}

}
