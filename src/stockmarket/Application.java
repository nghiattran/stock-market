package stockmarket;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import stockmarket.external.Stock;
import stockmarket.external.StockRepository;
import stockmarket.pattern.strategy.StockStrategy;
import stockmarket.pattern.strategy.impl.*;

/**
 * Application.java
 * 
 * @author Clayton Williams
 * @author Nghia Tran
 * @date Apr 4, 2016
 */
public class Application extends JFrame {

	/**
	 * Generated serial
	 */
	private static final long serialVersionUID = -7614879852405627793L;

	/**
	 * All available symbols
	 */
	private final List<String> allSymbols;

	/**
	 * The symbol list
	 */
	private JList<String> symbolList;

	/**
	 * Search symbol
	 */
	private JTextField searchSymbol;

	/**
	 * The stock information panel
	 */
	private StockInformation stockInformation;

	/**
	 * Selects a stock from the list to view
	 */
	private void selectStock() {
		if (symbolList.getSelectedIndex() != -1) {
			String symbol = symbolList.getSelectedValue();
			Stock stock = StockRepository.getInstance().getStock(symbol);
			if (stock != null) {
				stockInformation.updateInformation(stock);
				stockInformation.setVisible(true);
			}
		}
	}

	/**
	 * Filters the symbols based on the @code{searchSymbol}
	 */
	public void filterSymbols() {
		String search = searchSymbol.getText();
		ListModel<String> listModel = new DefaultListModel<String>();
		for (String s : allSymbols) {
			if (search.length() == 0 || s.contains(search.toUpperCase()))
				((DefaultListModel<String>) listModel).addElement(s);
		}
		symbolList.clearSelection();
		symbolList.setModel(listModel);
	}

	/**
	 * Application
	 */
	public Application() {
		this.setTitle("Stock Market");
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(new Rectangle(0, 23, 700, 430));

		JPanel container = new JPanel();
		container.setBackground(new Color(211, 211, 211));
		container.setBounds(6, 6, 688, 396);
		container.setLayout(null);

		searchSymbol = new JTextField();
		searchSymbol.setBounds(6, 362, 90, 28);
		searchSymbol.setColumns(10);
		searchSymbol.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				filterSymbols();
			}
		});
		container.add(searchSymbol);

		JLabel searchLabel = new JLabel("Search Symbol");
		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		searchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
		searchLabel.setBounds(6, 346, 90, 16);
		container.add(searchLabel);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 6, 90, 335);
		container.add(scrollPane);

		symbolList = new JList<String>();
		symbolList.setBorder(null);
		symbolList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					selectStock();
			}
		});
		scrollPane.setViewportView(symbolList);

		allSymbols = getAllSymbols();
		Collections.sort(allSymbols, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});

		filterSymbols();

		getContentPane().add(container);

		stockInformation = new StockInformation();
		container.add(stockInformation);

		this.setVisible(true);
	}

	/**
	 * Get all strategies
	 */
	public static List<String> getAllStrategies() {
		List<String> strategies = new ArrayList<String>();
		File folder = new File("src/stockmarket/pattern/strategy/impl");
		File[] listOfFiles = folder.listFiles();

		for (int i = 0; i < listOfFiles.length; i++) {
			if (listOfFiles[i].isFile()) {
				strategies.add(splitCamelCase(stripExtension(listOfFiles[i].getName())));
			}
		}

		return strategies;
	}

	static String splitCamelCase(String s) {
		return s.replaceAll(String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])",
				"(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
	}

	private static String stripExtension(String str) {
		if (str == null)
			return null;
		int pos = str.lastIndexOf(".");
		if (pos == -1)
			return str;
		return str.substring(0, pos);
	}

	/**
	 * Get all stock symbols from companylist.csv
	 */
	public static List<String> getAllSymbols() {
		Scanner scanner = null;
		List<String> symbols = new ArrayList<String>();
		try {
			scanner = new Scanner(new File("companylist.csv"));
			scanner.useDelimiter("\n");
			int index = -1;
			while (scanner.hasNext()) {
				String line = scanner.next();
				index++;
				if (index == 0)
					continue;
				String[] fields = line.split(",");
				for (int i = 0; i < fields.length; i++) {
					switch (i) {
					case 0:
						symbols.add(fields[i].replaceAll("\"", ""));
						break;
					default:
						break;
					}
				}
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return symbols;
	}

	/**
	 * The stock information panel
	 */
	private static class StockInformation extends JPanel {

		/**
		 * Generated serial
		 */
		private static final long serialVersionUID = -7188413730137740414L;

		/**
		 * The current stock being displayed
		 */
		private Stock currentStock;

		/**
		 * The current stock strategy
		 */
		private StockStrategy stockStrategy = new StockStrategy(new FirstStrategy());

		/**
		 * The stock name
		 */
		private JLabel stockName;

		/**
		 * All available strategy
		 */
		private final List<String> allStrategies;

		/**
		 * The symbol list
		 */
		private JList<String> strategyList;

		/**
		 * Stock information
		 */
		public StockInformation() {
			setBounds(103, 6, 578, 384);
			setLayout(null);
			setVisible(false);

			stockName = new JLabel("Stock Name");
			stockName.setFont(new Font("Lucida Grande", Font.BOLD, 14));
			stockName.setBounds(6, 6, 300, 18);
			add(stockName);

			/**
			 * Begin strategy panel
			 */
			JScrollPane scrollPaneStrategy = new JScrollPane();
			scrollPaneStrategy.setBorder(new LineBorder(new Color(0, 0, 0)));
			scrollPaneStrategy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPaneStrategy.setBounds(415, 6, 150, 335);
			add(scrollPaneStrategy);

			strategyList = new JList<String>();
			strategyList.setBorder(null);
			strategyList.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting()) {
						stockStrategy = selectStrategy();
						repaint();
					}
					
				}
			});
			scrollPaneStrategy.setViewportView(strategyList);

			allStrategies = getAllStrategies();
			Collections.sort(allStrategies, new Comparator<String>() {
				@Override
				public int compare(String s1, String s2) {
					return s1.compareToIgnoreCase(s2);
				}
			});

			filterStrategies();
			/**
			 * End strategy panel
			 */

			// JButton s1 = new JButton("First Strategy");
			// s1.setBounds(40, 300, 150, 30);
			// s1.addMouseListener(new MouseAdapter() {
			// @Override
			// public void mousePressed(MouseEvent e) {
			// stockStrategy.setStrategy(new FirstStrategy());
			// repaint();
			// }
			// });
			// add(s1);
			//
			// JButton s2 = new JButton("Alternative Strategy");
			// s2.setBounds(190, 300, 150, 30);
			// s2.addMouseListener(new MouseAdapter() {
			// @Override
			// public void mousePressed(MouseEvent e) {
			// stockStrategy.setStrategy(new AlternateStrategy());
			// repaint();
			// }
			// });
			// add(s2);
			//
			// JButton s3 = new JButton("Random Strategy");
			// s3.setBounds(340, 300, 150, 30);
			// s3.addMouseListener(new MouseAdapter() {
			// @Override
			// public void mousePressed(MouseEvent e) {
			// stockStrategy.setStrategy(new RandomStrategy());
			// repaint();
			// }
			// });
			// add(s3);
		}

		public void filterStrategies() {
			ListModel<String> listModel = new DefaultListModel<String>();
			for (String s : allStrategies) {
				((DefaultListModel<String>) listModel).addElement(s);
			}
			strategyList.clearSelection();
			strategyList.setModel(listModel);
		}

		/**
		 * Selects a stock from the list to view
		 */
		private StockStrategy selectStrategy() {
			if (strategyList.getSelectedIndex() != -1) {
				String strategy = strategyList.getSelectedValue();
				return createClass(strategy.replace(" ", ""));
			}

			return null;
		}

		/**
		 * Create an instance of className object
		 */
		public static StockStrategy createClass(String className) {
			try {
				className = "stockmarket.pattern.strategy.impl." + className;
				Class clazz = Class.forName(className);
				return new StockStrategy(clazz.newInstance());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (currentStock != null && isVisible()) {
				g.setColor(Color.BLACK);
				g.drawString("Current Price: $" + currentStock.getCurrentPrice(), 6, 40);
				g.drawString("Opening Price: $" + currentStock.getOpenPrice(), 6, 58);
				g.drawString("Closing Price (Previous Day): $ " + currentStock.getClosePrice(), 6, 76);
				g.drawString(
						"Today's High: "
								+ (currentStock.getHighPrice() <= 0 ? "N/A" : "$" + currentStock.getHighPrice()),
						6, 94);
				g.drawString(
						"Today's Low: " + (currentStock.getLowPrice() <= 0 ? "N/A" : "$" + currentStock.getLowPrice()),
						6, 112);
				g.drawString("Last Trade Date: " + currentStock.getLastTradeDate(), 6, 130);
				g.drawString("Daily Volume: " + NumberFormat.getInstance().format(currentStock.getVolume()) + " shares",
						6, 148);
				g.drawString(stockStrategy.analyze(currentStock), 80, 350);
			}
		}

		/**
		 * Updates the information
		 * 
		 * @param stock
		 */
		public void updateInformation(Stock stock) {
			currentStock = stock;
			stockName.setText(stock.getCompanyName() + " (" + stock.getSymbol() + ")");
			repaint();
		}
	}

	public static void main(String[] args) {
		new Application();
	}
}