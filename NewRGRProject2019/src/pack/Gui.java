package pack;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import widgets.ChooseRandom;
import widgets.ChooseData;
import widgets.Diagram;
import javax.swing.JCheckBox;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import widgets.stat.StatisticsManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.event.CaretListener;

import process.Dispatcher;
import process.IModelFactory;
import rnd.RandomDlg;
import rnd.RandomGenerators;

import javax.swing.event.CaretEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class Gui {

	private JFrame frame;
	private Diagram diagramSizeCheckQueue;
	private Diagram diagramSizeConfQueue;
	private JCheckBox checkBoxOutput;
	private JButton buttonStart;
	private ChooseRandom chooseRandomVhPotik;
	private ChooseRandom chooseRandomCheckQueue;
	private ChooseRandom chooseRandomConfQueue;
	private ChooseData chooseDataNumCheck;
	private ChooseData chooseDataNumConf;
	private ChooseData chooseDataTimeWork;
	private JPanel panelTest;
	private JScrollPane scrollPane;
	private JTextPane textPane;
	
	private StatisticsManager statisticsManager;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui window = new Gui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Gui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 682, 384);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 210, 346, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		frame.getContentPane().setLayout(gridBagLayout);

		JPanel panelInf = new JPanel();
		panelInf.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"\u041F\u0430\u0440\u0430\u043C\u0435\u0442\u0440\u0438 \u0441\u0438\u0441\u0442\u0435\u043C\u0438",
				TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_panelInf = new GridBagConstraints();
		gbc_panelInf.insets = new Insets(0, 0, 0, 5);
		gbc_panelInf.fill = GridBagConstraints.BOTH;
		gbc_panelInf.gridx = 0;
		gbc_panelInf.gridy = 0;
		frame.getContentPane().add(panelInf, gbc_panelInf);
		GridBagLayout gbl_panelInf = new GridBagLayout();
		gbl_panelInf.columnWidths = new int[] { 0, 0 };
		gbl_panelInf.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panelInf.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelInf.rowWeights = new double[] { 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelInf.setLayout(gbl_panelInf);

		chooseRandomVhPotik = new ChooseRandom();
		GridBagLayout gbl_chooseRandomVhPotik = (GridBagLayout) chooseRandomVhPotik.getLayout();
		gbl_chooseRandomVhPotik.rowWeights = new double[] { 1.0 };
		gbl_chooseRandomVhPotik.rowHeights = new int[] { 0 };
		gbl_chooseRandomVhPotik.columnWeights = new double[] { 0.0, 1.0 };
		gbl_chooseRandomVhPotik.columnWidths = new int[] { 32, 0 };
		GridBagConstraints gbc_chooseRandomVhPotik = new GridBagConstraints();
		gbc_chooseRandomVhPotik.insets = new Insets(0, 0, 5, 0);
		gbc_chooseRandomVhPotik.fill = GridBagConstraints.BOTH;
		gbc_chooseRandomVhPotik.gridx = 0;
		gbc_chooseRandomVhPotik.gridy = 0;
		panelInf.add(chooseRandomVhPotik, gbc_chooseRandomVhPotik);
		chooseRandomVhPotik.setTitle("Інтервал між появами телевізорів");

		chooseRandomCheckQueue = new ChooseRandom();
		GridBagLayout gbl_chooseRandomCheckQueue = (GridBagLayout) chooseRandomCheckQueue.getLayout();
		gbl_chooseRandomCheckQueue.rowWeights = new double[] { 1.0 };
		gbl_chooseRandomCheckQueue.rowHeights = new int[] { 0 };
		gbl_chooseRandomCheckQueue.columnWeights = new double[] { 0.0, 1.0 };
		gbl_chooseRandomCheckQueue.columnWidths = new int[] { 32, 0 };
		GridBagConstraints gbc_chooseRandomCheckQueue = new GridBagConstraints();
		gbc_chooseRandomCheckQueue.insets = new Insets(0, 0, 5, 0);
		gbc_chooseRandomCheckQueue.fill = GridBagConstraints.BOTH;
		gbc_chooseRandomCheckQueue.gridx = 0;
		gbc_chooseRandomCheckQueue.gridy = 1;
		panelInf.add(chooseRandomCheckQueue, gbc_chooseRandomCheckQueue);
		chooseRandomCheckQueue.setTitle("Час перевірки телевізора");

		chooseRandomConfQueue = new ChooseRandom();
		GridBagLayout gbl_chooseRandomConfQueue = (GridBagLayout) chooseRandomConfQueue.getLayout();
		gbl_chooseRandomConfQueue.rowWeights = new double[] { 1.0 };
		gbl_chooseRandomConfQueue.rowHeights = new int[] { 0 };
		gbl_chooseRandomConfQueue.columnWeights = new double[] { 0.0, 1.0 };
		gbl_chooseRandomConfQueue.columnWidths = new int[] { 32, 0 };
		GridBagConstraints gbc_chooseRandomConfQueue = new GridBagConstraints();
		gbc_chooseRandomConfQueue.insets = new Insets(0, 0, 5, 0);
		gbc_chooseRandomConfQueue.fill = GridBagConstraints.BOTH;
		gbc_chooseRandomConfQueue.gridx = 0;
		gbc_chooseRandomConfQueue.gridy = 2;
		panelInf.add(chooseRandomConfQueue, gbc_chooseRandomConfQueue);
		chooseRandomConfQueue.setTitle("Час налаштування телевізора");

		chooseDataNumCheck = new ChooseData();
		GridBagConstraints gbc_chooseDataNumCheck = new GridBagConstraints();
		gbc_chooseDataNumCheck.insets = new Insets(0, 0, 5, 0);
		gbc_chooseDataNumCheck.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooseDataNumCheck.gridx = 0;
		gbc_chooseDataNumCheck.gridy = 3;
		panelInf.add(chooseDataNumCheck, gbc_chooseDataNumCheck);
		chooseDataNumCheck.setTitle("Кількість перевіряючих пунктів");

		chooseDataNumConf = new ChooseData();
		GridBagConstraints gbc_chooseDataNumConf = new GridBagConstraints();
		gbc_chooseDataNumConf.insets = new Insets(0, 0, 5, 0);
		gbc_chooseDataNumConf.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooseDataNumConf.gridx = 0;
		gbc_chooseDataNumConf.gridy = 4;
		panelInf.add(chooseDataNumConf, gbc_chooseDataNumConf);
		chooseDataNumConf.setTitle("Кількість налаштовуючих пунктів");

		chooseDataTimeWork = new ChooseData();
		chooseDataTimeWork.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				onTimeCaretUpdate();
			}
		});
		GridBagConstraints gbc_chooseDataTimeWork = new GridBagConstraints();
		gbc_chooseDataTimeWork.insets = new Insets(0, 0, 5, 0);
		gbc_chooseDataTimeWork.fill = GridBagConstraints.HORIZONTAL;
		gbc_chooseDataTimeWork.gridx = 0;
		gbc_chooseDataTimeWork.gridy = 5;
		panelInf.add(chooseDataTimeWork, gbc_chooseDataTimeWork);
		chooseDataTimeWork.setTitle("Час моделювання");

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GridBagConstraints gbc_tabbedPane = new GridBagConstraints();
		gbc_tabbedPane.fill = GridBagConstraints.BOTH;
		gbc_tabbedPane.gridx = 1;
		gbc_tabbedPane.gridy = 0;
		frame.getContentPane().add(tabbedPane, gbc_tabbedPane);

		panelTest = new JPanel();
		panelTest.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent e) {
				onPanelTestComponentShown();
			}
		});

		scrollPane = new JScrollPane();
		tabbedPane.addTab(
				"\u0422\u0435\u0445\u043D\u0456\u0447\u043D\u0435 \u0437\u0430\u0432\u0434\u0430\u043D\u043D\u044F",
				null, scrollPane, null);

		textPane = new JTextPane();

		String str = "tz.htm";
		URL url = getClass().getResource(str);
		try {
			textPane.setPage(url);
		} catch (IOException e33) {
			System.err.println("Problems with file " + str);
		}
		scrollPane.setViewportView(textPane);
		tabbedPane.addTab("test", null, panelTest, null);
		GridBagLayout gbl_panelTest = new GridBagLayout();
		gbl_panelTest.columnWidths = new int[] { 162, 193, 0 };
		gbl_panelTest.rowHeights = new int[] { 0, 0, 35, 0 };
		gbl_panelTest.columnWeights = new double[] { 1.0, 0.0, Double.MIN_VALUE };
		gbl_panelTest.rowWeights = new double[] { 1.0, 1.0, 0.0, Double.MIN_VALUE };
		panelTest.setLayout(gbl_panelTest);

		diagramSizeCheckQueue = new Diagram();
		diagramSizeCheckQueue.setPainterColor(Color.RED);
		GridBagLayout gbl_diagramSizeCheckQueue = (GridBagLayout) diagramSizeCheckQueue.getLayout();
		gbl_diagramSizeCheckQueue.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_diagramSizeCheckQueue.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_diagramSizeCheckQueue.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_diagramSizeCheckQueue.columnWidths = new int[] { 0, 0, 0 };
		GridBagConstraints gbc_diagramSizeCheckQueue = new GridBagConstraints();
		gbc_diagramSizeCheckQueue.gridwidth = 2;
		gbc_diagramSizeCheckQueue.insets = new Insets(0, 0, 5, 0);
		gbc_diagramSizeCheckQueue.fill = GridBagConstraints.BOTH;
		gbc_diagramSizeCheckQueue.gridx = 0;
		gbc_diagramSizeCheckQueue.gridy = 0;
		panelTest.add(diagramSizeCheckQueue, gbc_diagramSizeCheckQueue);
		diagramSizeCheckQueue.setTitleText("Розмір черги до перевірки");

		diagramSizeConfQueue = new Diagram();
		diagramSizeConfQueue.setPainterColor(Color.RED);
		GridBagLayout gbl_diagramSizeConfQueue = (GridBagLayout) diagramSizeConfQueue.getLayout();
		gbl_diagramSizeConfQueue.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0 };
		gbl_diagramSizeConfQueue.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_diagramSizeConfQueue.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_diagramSizeConfQueue.columnWidths = new int[] { 0, 0, 0 };
		GridBagConstraints gbc_diagramSizeConfQueue = new GridBagConstraints();
		gbc_diagramSizeConfQueue.gridwidth = 2;
		gbc_diagramSizeConfQueue.insets = new Insets(0, 0, 5, 5);
		gbc_diagramSizeConfQueue.fill = GridBagConstraints.BOTH;
		gbc_diagramSizeConfQueue.gridx = 0;
		gbc_diagramSizeConfQueue.gridy = 1;
		panelTest.add(diagramSizeConfQueue, gbc_diagramSizeConfQueue);
		diagramSizeConfQueue.setTitleText("Розмір черги до налаштування");

		checkBoxOutput = new JCheckBox();
		checkBoxOutput.setText("Протокол на консоль");
		checkBoxOutput.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
		checkBoxOutput.setActionCommand("Вывод протокола на консоль");
		GridBagConstraints gbc_checkBoxOutput = new GridBagConstraints();
		gbc_checkBoxOutput.insets = new Insets(0, 0, 0, 5);
		gbc_checkBoxOutput.gridx = 0;
		gbc_checkBoxOutput.gridy = 2;
		panelTest.add(checkBoxOutput, gbc_checkBoxOutput);

		buttonStart = new JButton();
		buttonStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startTest();
			}
		});
		buttonStart.setText("Старт");
		GridBagConstraints gbc_buttonStart = new GridBagConstraints();
		gbc_buttonStart.gridx = 1;
		gbc_buttonStart.gridy = 2;
		panelTest.add(buttonStart, gbc_buttonStart);

		JPanel panelStat = new JPanel();
		tabbedPane.addTab("stat", null, panelStat, null);
		GridBagLayout gbl_panelStat = new GridBagLayout();
		gbl_panelStat.columnWidths = new int[] { 0, 0 };
		gbl_panelStat.rowHeights = new int[] { 0, 0 };
		gbl_panelStat.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panelStat.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		panelStat.setLayout(gbl_panelStat);

		statisticsManager = new StatisticsManager();
		statisticsManager.setFactory((d) -> new Model(d, this));
		GridBagLayout gridBagLayout_6 = (GridBagLayout) statisticsManager.getLayout();
		gridBagLayout_6.rowWeights = new double[] { 1.0, 0.0, 0.0 };
		gridBagLayout_6.rowHeights = new int[] { 0, 0, 0 };
		gridBagLayout_6.columnWeights = new double[] { 1.0, 0.0 };
		gridBagLayout_6.columnWidths = new int[] { 202, 56 };
		GridBagConstraints gbc_statisticsManager = new GridBagConstraints();
		gbc_statisticsManager.fill = GridBagConstraints.BOTH;
		gbc_statisticsManager.gridx = 0;
		gbc_statisticsManager.gridy = 0;
		panelStat.add(statisticsManager, gbc_statisticsManager);
	}

	public Diagram getDiagramSizeCheckQueue() {
		return diagramSizeCheckQueue;
	}

	public Diagram getDiagramSizeConfQueue() {
		return diagramSizeConfQueue;
	}

	public JCheckBox getCheckBoxOutput() {
		return checkBoxOutput;
	}

	public JButton getButtonStart() {
		return buttonStart;
	}

	public ChooseRandom getChooseRandomVhPotik() {
		return chooseRandomVhPotik;
	}

	public ChooseRandom getChooseRandomCheckQueue() {
		return chooseRandomCheckQueue;
	}

	public ChooseRandom getChooseRandomConfQueue() {
		return chooseRandomConfQueue;
	}

	public ChooseData getChooseDataNumCheck() {
		return chooseDataNumCheck;
	}

	public ChooseData getChooseDataNumConf() {
		return chooseDataNumConf;
	}

	public ChooseData getChooseDataTimeWork() {
		return chooseDataTimeWork;
	}

	public JPanel getPanelTest() {
		return panelTest;
	}
	
	public StatisticsManager getStatisticsManager() {
		return statisticsManager;
	}

	protected void onPanelTestComponentShown() {
		// Штучно формуємо подію CaretUpdate,
		// щоб обновити налаштування діаграми
		getChooseDataTimeWork().select(0, 0);
	}

	protected void onTimeCaretUpdate() {
		if (getPanelTest().isShowing()) {
			getDiagramSizeCheckQueue().setHorizontalMaxText(chooseDataTimeWork.getText());
			getDiagramSizeConfQueue().setHorizontalMaxText(chooseDataTimeWork.getText());
		}
	}

	protected void startTest() {
		// Готуємо діаграму для виведення графіку
		getDiagramSizeCheckQueue().clear();
		getDiagramSizeConfQueue().clear();
		// Створюємо диспетчера
		Dispatcher dispatcher = new Dispatcher();
		// Створюємо модель за допомогою фабрики
		IModelFactory factory = (d) -> new Model(d, this);
		Model model = (Model) factory.createModel(dispatcher);
		// Робимо кнопку «Старт» недосяжною на період роботи моделі
		getButtonStart().setEnabled(false);
		dispatcher.addDispatcherFinishListener(() -> getButtonStart().setEnabled(true));
		// Готуємо модель до роботи у режимі тестування
		
		model.initForTest();
		// Запускаємо модель
		dispatcher.start();
	}

	public JTextPane getTextPane() {
		return textPane;
	}

}
