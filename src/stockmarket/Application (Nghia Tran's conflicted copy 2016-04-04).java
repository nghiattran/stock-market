//package stockmarket;
//
//import java.awt.Color;
//import java.awt.Font;
//import java.awt.Graphics;
//import java.awt.Rectangle;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//
//import javax.swing.DefaultListModel;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextField;
//import javax.swing.ListModel;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.SwingConstants;
//import javax.swing.border.LineBorder;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//
//import stockmarket.external.Stock;
//import stockmarket.external.StockRepository;
//
///**
// * Application.java
// * @author Clayton Williams
// * @author Nghia Tran
// * @date Apr 4, 2016
// */
//public class Application extends JFrame {
//	
//	/**
//	 * Generated serial
//	 */
//	private static final long serialVersionUID = -7614879852405627793L;
//	
//	/**
//	 * All available symbols
//	 */
//	private final List<String> allSymbols;
//	
//	/**
//	 * All available strategies
//	 */
//	private final List<String> allStrategies;
//	
//	/**
//	 * The symbol list
//	 */
//	private JList<String> symbolList;
//	
//	/**
//	 * The strategy list
//	 */
//	private JList<String> stratList;
//	
//	/**
//	 * Search symbol
//	 */
//	private JTextField searchSymbol;
//	
//	/**
//	 * The stock information panel
//	 */
//	private StockInformation stockInformation;
//	
//	/**
//	 * The strategy panel
//	 */
//	private Strategy strategy;
//	
//	/**
//	 * Selects a stock from the list to view
//	 */
//	private void selectStock() {
//		if (symbolList.getSelectedIndex() != -1) {
//			String symbol = symbolList.getSelectedValue();
//			Stock stock = StockRepository.getInstance().getStock(symbol);
//			if (stock != null) {
//				stockInformation.updateInformation(stock);
//				stockInformation.setVisible(true);
//			}
//		}
//	}
//	
//	/**
//	 * Selects a stock from the list to view
//	 */
//	private void selectStrat() {
//		if (stratList.getSelectedIndex() != -1) {
//			String symbol = stratList.getSelectedValue();
//			Stock stock = StockRepository.getInstance().getStock(symbol);
//			if (stock != null) {
////				strategy.updateInformation(stock);
//			}
//		}
//	}
//	
//	/**
//	 * Filters the symbols based on the @code{searchSymbol}
//	 */
//	public void filterSymbols() {
//		String search = searchSymbol.getText();
//		ListModel<String> listModel = new DefaultListModel<String>(); 
//		for (String s : allSymbols) {
//			if (search.length() == 0 || s.contains(search.toUpperCase()))
//				((DefaultListModel<String>) listModel).addElement(s);
//		}
//		symbolList.clearSelection();
//		symbolList.setModel(listModel);
//	}
//	
//	public void filterStrat() {
//		String search = "";
//		ListModel<String> listModel = new DefaultListModel<String>(); 
//		for (String s : allStrategies) {
//			if (search.length() == 0 || s.contains(search.toUpperCase()))
//				((DefaultListModel<String>) listModel).addElement(s);
//		}
//		stratList.clearSelection();
//		symbolList.setModel(listModel);
//	}
//	
//	/**
//	 * Application
//	 */
//	public Application() {
//		this.setTitle("Stock Market");
//		this.getContentPane().setLayout(null);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		this.setBounds(new Rectangle(0, 23, 700, 430));
//
//		
//		JPanel container = new JPanel();
//		container.setBackground(new Color(211, 211, 211));
//		container.setBounds(6, 6, 688, 396);
//		container.setLayout(null);
//	
//		searchSymbol = new JTextField();
//		searchSymbol.setBounds(6, 362, 90, 28);
//		searchSymbol.setColumns(10);
//		searchSymbol.addKeyListener(new KeyListener() {
//			@Override
//			public void keyTyped(KeyEvent e) {}
//			@Override
//			public void keyPressed(KeyEvent e) {}
//			@Override
//			public void keyReleased(KeyEvent e) {
//				filterSymbols();			
//			}			
//		});
//		container.add(searchSymbol);
//		
//		JLabel searchLabel = new JLabel("Search Symbol");
//		searchLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		searchLabel.setFont(new Font("Times New Roman", Font.PLAIN, 11));
//		searchLabel.setBounds(6, 346, 90, 16);
//		container.add(searchLabel);
//		
//		JScrollPane scrollPane = new JScrollPane();
//		scrollPane.setBorder(new LineBorder(new Color(0, 0, 0)));
//		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPane.setBounds(6, 6, 90, 335);
//		container.add(scrollPane);
//		
//		symbolList = new JList<String>();
//		symbolList.setBorder(null);
//		symbolList.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				if (!e.getValueIsAdjusting())
//					selectStock();
//			}
//		});
//		
//		scrollPane.setViewportView(symbolList);
//				
//		allSymbols = getAllSymbols();	 
//		Collections.sort(allSymbols, new Comparator<String>() {
//	        @Override
//	        public int compare(String s1, String s2) {
//	            return s1.compareToIgnoreCase(s2);
//	        }
//	    });
//		
//		filterSymbols();
//		
//		JScrollPane scrollPaneStrategy = new JScrollPane();
//		scrollPaneStrategy.setBorder(new LineBorder(new Color(0, 0, 0)));
//		scrollPaneStrategy.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		scrollPaneStrategy.setBounds(90, 6, 90, 335);
//		container.add(scrollPaneStrategy);
//		
//		stratList = new JList<String>();
//		stratList.setBorder(null);
//		stratList.addListSelectionListener(new ListSelectionListener() {
//			public void valueChanged(ListSelectionEvent e) {
//				if (!e.getValueIsAdjusting())
//					selectStock();
//			}
//		});
//		
//		scrollPaneStrategy.setViewportView(stratList);
//		
//		allStrategies = getAllStrategies();	 
//		Collections.sort(allSymbols, new Comparator<String>() {
//	        @Override
//	        public int compare(String s1, String s2) {
//	            return s1.compareToIgnoreCase(s2);
//	        }
//	    });
//		
//		filterSymbols();
//		
//		getContentPane().add(container);
//		
//		stockInformation = new StockInformation();
//		container.add(stockInformation);
//	
//		
//		strategy = new Strategy();
//		container.add(strategy);
//		
//		this.setVisible(true);
//	}
//	
//	public static List<String> getAllStrategies() {
//		List<String> strategies = new ArrayList<String>();
//		strategies.add("Strat 1");
//		return strategies;
//	}
//	
//	/**
//	 * Get all stock symbols from companylist.csv
//	 */
//	public static List<String> getAllSymbols() {
//		Scanner scanner = null;
//		List<String> symbols = new ArrayList<String>();
//		try {
//			scanner = new Scanner(new File("companylist.csv"));
//	        scanner.useDelimiter("\n");
//	        int index = -1;
//	        while(scanner.hasNext()){
//	        	String line = scanner.next();
//	        	index++;
//	        	if (index == 0)
//	        		continue;
//	        	String[] fields = line.split(",");
//	        	for(int i = 0; i < fields.length; i++) {
//	        		switch(i) {
//		        		case 0:
//		        			symbols.add(fields[i].replaceAll("\"", ""));
//		        			break;
//		        		default:
//		        			break;
//	        		}
//	        	}
//	        }
//	        scanner.close();
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//        return symbols;
//	}
//	
//	/**
//	 * The stock information panel
//	 */
//	private static class StockInformation extends JPanel {
//
//		/**
//		 * Generated serial
//		 */
//		private static final long serialVersionUID = -7188413730137740414L;
//		
//		/**
//		 * The current stock being displayed
//		 */
//		private Stock currentStock;
//		
//		/**
//		 * The stock name
//		 */
//		private JLabel stockName;
//
//		/**
//		 * Stock information
//		 */
//		public StockInformation() {
//			setBounds(103, 6, 578, 384);
//			setLayout(null);
//			setVisible(false);
//			
//			stockName = new JLabel("Stock Name");
//			stockName.setFont(new Font("Lucida Grande", Font.BOLD, 14));
//			stockName.setBounds(6, 6, 300, 18);
//			add(stockName);
//		}
//		
//		@Override
//		public void paintComponent(Graphics g) {
//			super.paintComponent(g);
//			if (currentStock != null && isVisible()) {
//				g.setColor(Color.BLACK);
//				g.drawString("Current Price: $" + currentStock.getCurrentPrice(), 6, 40);
//				g.drawString("Opening Price: $" + currentStock.getOpenPrice(), 6, 58);
//				g.drawString("Closing Price (Previous Day): $ " + currentStock.getClosePrice(), 6, 76);
//				g.drawString("Today's High: " + (currentStock.getHighPrice() <= 0 ? "N/A" : "$" + currentStock.getHighPrice()), 6, 94);
//				g.drawString("Today's Low: " + (currentStock.getLowPrice() <= 0 ? "N/A" : "$" + currentStock.getLowPrice()), 6, 112);
//				g.drawString("Last Trade Date: " + currentStock.getLastTradeDate(), 6, 130);
//				g.drawString("Daily Volume: " + NumberFormat.getInstance().format(currentStock.getVolume()) + " shares", 6, 148);
//				
//			}
//		}
//		
//		/**
//		 * Updates the information
//		 * @param stock
//		 */
//		public void updateInformation(Stock stock) {
//			currentStock = stock;
//			stockName.setText(stock.getCompanyName() + " (" + stock.getSymbol() + ")");
//			repaint();
//		}
//	}
//
//	private static class Strategy extends JPanel {
//		/**
//		 * 
//		 */
//		private static final long serialVersionUID = 409599519332893966L;
//		
//		/**
//		 * The stock name
//		 */
//		private JLabel stratName;
//		
//		/**
//		 * Stock information
//		 */
//		public Strategy() {
//			
//			setBounds(103, 6, 578, 384);
//			setLayout(null);
//			setVisible(true);
//			
//			stratName = new JLabel("Strat Name");
//			stratName.setFont(new Font("Lucida Grande", Font.BOLD, 14));
//			stratName.setBounds(6, 6, 300, 18);
//			add(stratName);
//		}
//		
//		@Override
//		public void paintComponent(Graphics g) {
//			System.out.println("her");
//			super.paintComponent(g);
//		}
//	}
//	
//	public static void main(String[] args) {
//		new Application();
//		/**TradeCenter trade = TradeCenter.getInstance();
//		SupStrategy strat = new SupStrategy(new ThirdStrategy());
//		trade.addSymbol("ALL");
//		try {
//			Thread.sleep(8000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		strat.setStrat(new SecondStrategy());**/
//	}
//}