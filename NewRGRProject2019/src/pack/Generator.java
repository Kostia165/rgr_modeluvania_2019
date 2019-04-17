package pack;

import java.util.Random;

import process.Actor;
import rnd.Randomable;

public class Generator extends Actor {

	// ��������� �� ������ �������
	private Model model;
	// ��������� ����������� ���� ��������� ����������
	private Randomable rnd;
	// ��������� ������ ����������
	private double finishTime;

	private Random rndW;

	// �����������
	public Generator(String name, Gui gui, Model model) {
		setNameForProtocol(name);
		this.model = model;
		finishTime = gui.getChooseDataTimeWork().getDouble();
		rnd = gui.getChooseRandomVhPotik();
		rndW = new Random();
	}

	// ������� 䳿
	public void rule() {
		while (getDispatcher().getCurrentTime() <= finishTime) {
			holdForTime(rnd.next());
			getDispatcher().printToProtocol("  " + getNameForProtocol() + " ������� ����������.");
			Transaction transaction = new Transaction(model);
			transaction.setWorkable(true);
			dispatcher.addStartingActor(transaction);
		}
	}

}
