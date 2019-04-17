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

	// ��������� �� ����������
	private Dispatcher dispatcher;
	// ��������� �� �������� �������
	private Gui gui;

////////������\\\\\\\\\
// ��������� ����������
	private Generator generator;
// ������������� ������
	private DeviceCheck deviceCheck;
	private DeviceConf deviceConf;
// ������� ������������� ��������
	private MultiActor multiDeviceCheck;
	private MultiActor multiDeviceConf;

	///////// �����\\\\\\\\\
	// ����� ����������
	private QueueForTransactions<Transaction> queueForCheck;
	private QueueForTransactions<Transaction> queueForConf;

///////// ó��������\\\\\\\\\\\\

// ó�������� ��� ������ ����� �� ��������
	private DiscretHisto diagramSizeCheckQueue;
// ó�������� ��� ������ ����� �� ������������
	private DiscretHisto diagramSizeConfQueue;
	// ó�������� ��� ���� ����������� � ���� �� ��������
	private Histo diagramTimeForCheckQueue;

	// ó�������� ��� ���� ����������� � ���� �� ������������
	private Histo diagramTimeForConfQueue;
// ó�������� ��� ���� ������� ������������ ��������
	private Histo histoWaitCheck;
// ó�������� ��� ���� ������� �������������� ��������
	private Histo histoWaitConf;
// ó�������� ��� ���� �������������� ����������� ��������
	private Histo histoTimeProcessCheck;
// ó�������� ��� ���� �������������� ������������� ��������
	private Histo histoTimeProcessConf;

// ////////////////////////////////////////
// ������ ����� �������� ������, �� ��������� ��� �����������
// ³� ����������� ������ ��������� �� ���������� �� TransGUI
// ////////////////////////////////////////

	public Model(Dispatcher d, Gui g) {
		if (d == null || g == null) {
			System.out.println("�� ��������� ���������� ��� TransGUI ��� TV");
			System.out.println("�������� ������ ���������");
			System.exit(0);
		}
		dispatcher = d;
		gui = g;
		// �������� ������ �� ���������� ������ ����������
		componentsToStartList();
	}

	private void componentsToStartList() {
		// �������� ������ ����������
		dispatcher.addStartingActor(getGenerator());
		dispatcher.addStartingActor(getMultiDeviceCheck());
		dispatcher.addStartingActor(getMultiDeviceConf());
	}

	public void initForTest() {
		// �������� ������ painter-�� ��� �������� ���������
		getQueueCheck().setPainter(gui.getDiagramSizeCheckQueue().getPainter());
		getQueueConf().setPainter(gui.getDiagramSizeConfQueue().getPainter());
		// ����������� ��������� ��������� ��������� �� �������
		
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
			multiDeviceCheck.setNameForProtocol("MultiActor ��� ����������� ��������");
			multiDeviceCheck.setOriginal(getDeviceCheck());
			multiDeviceCheck.setNumberOfClones(gui.getChooseDataNumCheck().getInt());
		}
		return multiDeviceCheck;
	}

	public MultiActor getMultiDeviceConf() {
		if (multiDeviceConf == null) {
			multiDeviceConf = new MultiActor();
			multiDeviceConf.setNameForProtocol("MultiActor ��� ������������� ��������");
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
		map.put("ó�������� ��� ������� ����� �� ��������", getDiagramSizeCheckQueue());
		map.put("ó�������� ��� ���� ������� � ���� �� ��������", getDiagramTimeForCheckQueue());
		map.put("ó�������� ��� ���� ������� ������������ ��������", getHistoWaitCheck());
		map.put("ó�������� ��� ���� ��������", getHistoTimeProcessCheck());
		
		map.put("ó�������� ��� ������� ����� �� ������������", getDiagramSizeConfQueue());
		map.put("ó�������� ��� ���� ������� � ���� �� ������������", getDiagramTimeForConfQueue());
		map.put("ó�������� ��� ���� ������� �������������� ��������", getHistoWaitConf());
		map.put("ó�������� ��� ���� ������������", getHistoTimeProcessConf());

		return map;
	}

	@Override
	public void initForStatistics() {
		
	}

}
