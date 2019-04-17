package pack;

import java.util.HashMap;
import java.util.Map;

import process.Dispatcher;
import process.MultiActor;
import process.QueueForTransactions;
import stat.DiscretHisto;
import stat.Histo;
import stat.IHisto;
import widgets.stat.IStatisticsable;

public class Model implements IStatisticsable {

	// Посилання на диспетчера
	private Dispatcher dispatcher;
	// Посилання на візуальну частину
	private Gui gui;

////////Актори\\\\\\\\\
// Генератор транзакцій
	private Generator generator;
// Обслуговуючий прилад
	private DeviceCheck deviceCheck;
	private DeviceConf deviceConf;
// Бригада обслуговуючих пристроїв
	private MultiActor multiDeviceCheck;
	private MultiActor multiDeviceConf;

	///////// Черги\\\\\\\\\
	// Черга транзакцій
	private QueueForTransactions<Transaction> queueForCheck;
	private QueueForTransactions<Transaction> queueForConf;

///////// Гістограми\\\\\\\\\\\\

// Гістограма для розміру черги до перевірки
	private DiscretHisto diagramSizeCheckQueue;
// Гістограма для розміру черги до налаштування
	private DiscretHisto diagramSizeConfQueue;
	// Гістограма для часу перебування в черзі на перевірку
	private Histo diagramTimeForCheckQueue;

	// Гістограма для часу перебування в черзі на налаштування
	private Histo diagramTimeForConfQueue;
// Гістограма для часу простою перевіряючого пристрою
	private Histo histoWaitCheck;
// Гістограма для часу простою налаштовуючого пристрою
	private Histo histoWaitConf;
// Гістограма для часу обслуговування перевіряючим пристроєм
	private Histo histoTimeProcessCheck;
// Гістограма для часу обслуговування налаштовуючим пристроєм
	private Histo histoTimeProcessConf;

// ////////////////////////////////////////
// Єдиний спосіб створити модель, це викликати цей конструктор
// Він гарантовано передає посилання на диспетчера та TransGUI
// ////////////////////////////////////////

	public Model(Dispatcher d, Gui g) {
		if (d == null || g == null) {
			System.out.println("Не визначено диспетчера або TransGUI для TV");
			System.out.println("Подальша робота неможлива");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		// Передаємо акторів до стартового списку диспетчера
		componentsToStartList();
	}

	private void componentsToStartList() {
		// Передаємо акторів диспетчеру
		dispatcher.addStartingActor(getGenerator());
		dispatcher.addStartingActor(getMultiDeviceCheck());
		dispatcher.addStartingActor(getMultiDeviceConf());
	}

	public void initForTest() {
		// Передаємо чергам painter-ів для динамічної індикації
		getQueueCheck().setPainter(gui.getDiagramSizeCheckQueue().getPainter());
		getQueueConf().setPainter(gui.getDiagramSizeConfQueue().getPainter());
		// Налаштовуємо можливість виведення протоколу на консоль
		
		if (gui.getCheckBoxOutput().isSelected())
			dispatcher.setProtocolFileName("Console");
		else
			dispatcher.setProtocolFileName("");

	}

	public Generator getGenerator() {
		if (generator == null) {
			generator = new Generator("Generator", gui, this);
		}
		return generator;
	}

	public QueueForTransactions<Transaction> getQueueCheck() {
		if (queueForCheck == null) {
			queueForCheck = new QueueForTransactions<>("QueueCheck", dispatcher, getDiagramSizeCheckQueue());
		}
		return queueForCheck;
	}

	public QueueForTransactions<Transaction> getQueueConf() {
		if (queueForConf == null) {
			queueForConf = new QueueForTransactions<>("QueueConf", dispatcher, getDiagramSizeConfQueue());
		}
		return queueForConf;
	}

	public DeviceCheck getDeviceCheck() {
		if (deviceCheck == null) {
			deviceCheck = new DeviceCheck("DeviceCheck", gui, this);
			deviceCheck.setHistoForActorWaitingTime(getHistoWaitCheck());
		}
		return deviceCheck;
	}

	public DeviceConf getDeviceConf() {
		if (deviceConf == null) {
			deviceConf = new DeviceConf("DeviceConf", gui, this);
			deviceConf.setHistoForActorWaitingTime(getHistoWaitConf());
		}
		return deviceConf;
	}

	public Histo getHistoWaitCheck() {
		if (histoWaitCheck == null) {
			histoWaitCheck = new Histo();
		}
		return histoWaitCheck;
	}

	public Histo getHistoWaitConf() {
		if (histoWaitConf == null) {
			histoWaitConf = new Histo();
		}
		return histoWaitConf;
	}

	public MultiActor getMultiDeviceCheck() {
		if (multiDeviceCheck == null) {
			multiDeviceCheck = new MultiActor();
			multiDeviceCheck.setNameForProtocol("MultiActor для перевіряючих пристроїв");
			multiDeviceCheck.setOriginal(getDeviceCheck());
			multiDeviceCheck.setNumberOfClones(gui.getChooseDataNumCheck().getInt());
		}
		return multiDeviceCheck;
	}

	public MultiActor getMultiDeviceConf() {
		if (multiDeviceConf == null) {
			multiDeviceConf = new MultiActor();
			multiDeviceConf.setNameForProtocol("MultiActor для налаштовуючих пристроїв");
			multiDeviceConf.setOriginal(getDeviceConf());
			multiDeviceConf.setNumberOfClones(gui.getChooseDataNumConf().getInt());
		}
		return multiDeviceConf;
	}

	public DiscretHisto getDiagramSizeCheckQueue() {
		if (diagramSizeCheckQueue == null) {
			diagramSizeCheckQueue = new DiscretHisto();
		}
		return diagramSizeCheckQueue;
	}

	public DiscretHisto getDiagramSizeConfQueue() {
		if (diagramSizeConfQueue == null) {
			diagramSizeConfQueue = new DiscretHisto();
		}
		return diagramSizeConfQueue;
	}

	public Histo getHistoTimeProcessCheck() {
		if (histoTimeProcessCheck == null) {
			histoTimeProcessCheck = new Histo();
		}
		return histoTimeProcessCheck;
	}

	public Histo getHistoTimeProcessConf() {
		if (histoTimeProcessConf == null) {
			histoTimeProcessConf = new Histo();
		}
		return histoTimeProcessConf;
	}

	public Histo getDiagramTimeForCheckQueue() {
		if (diagramTimeForCheckQueue == null) {
			diagramTimeForCheckQueue = new Histo();
		}
		return diagramTimeForCheckQueue;
	}

	public Histo getDiagramTimeForConfQueue() {
		if (diagramTimeForConfQueue == null) {
			diagramTimeForConfQueue = new Histo();
		}
		return diagramTimeForConfQueue;
	}

	@Override
	public Map<String, IHisto> getStatistics() {
		Map<String, IHisto> map = new HashMap<>();
		map.put("Гістограма для довжини черги на перевірку", getDiagramSizeCheckQueue());
		map.put("Гістограма для часу чекання у черзі на перевірку", getDiagramTimeForCheckQueue());
		map.put("Гістограма для часу простою перевіряючого пристрою", getHistoWaitCheck());
		map.put("Гістограма для часу перевірки", getHistoTimeProcessCheck());
		
		map.put("Гістограма для довжини черги на налаштування", getDiagramSizeConfQueue());
		map.put("Гістограма для часу чекання у черзі на налаштування", getDiagramTimeForConfQueue());
		map.put("Гістограма для часу простою налаштовуючого пристрою", getHistoWaitConf());
		map.put("Гістограма для часу налаштування", getHistoTimeProcessConf());

		return map;
	}

	@Override
	public void initForStatistics() {
		
	}

}
