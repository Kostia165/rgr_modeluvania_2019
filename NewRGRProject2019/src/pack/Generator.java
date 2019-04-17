package pack;

import java.util.Random;

import process.Actor;
import rnd.Randomable;

public class Generator extends Actor {

	// Посилання на модель системи
	private Model model;
	// Генератор випадкового часу створення транзакції
	private Randomable rnd;
	// Тривалість роботи генератора
	private double finishTime;

	private Random rndW;

	// Конструктор
	public Generator(String name, Gui gui, Model model) {
		setNameForProtocol(name);
		this.model = model;
		finishTime = gui.getChooseDataTimeWork().getDouble();
		rnd = gui.getChooseRandomVhPotik();
		rndW = new Random();
	}

	// Правила дії
	public void rule() {
		while (getDispatcher().getCurrentTime() <= finishTime) {
			holdForTime(rnd.next());
			getDispatcher().printToProtocol("  " + getNameForProtocol() + " створює транзакцію.");
			Transaction transaction = new Transaction(model);
			transaction.setWorkable(true);
			dispatcher.addStartingActor(transaction);
		}
	}

}
