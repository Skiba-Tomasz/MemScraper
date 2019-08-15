package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.skiba.fun.memscraper.Mem.Domains;
import com.skiba.fun.memscraper.Mem.MemObject;
import com.skiba.fun.memscraper.Mem.MemScraper;
import com.skiba.fun.memscraper.Mem.MemScraperDemotywatory;
import com.skiba.fun.memscraper.Mem.MemScraperJbzd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;



public class MemscraperMainWindow extends JFrame{
	
	private Domains currnetDomain = Domains.JBZD;
	private JScrollPane scrollPane;
	private JPanel contentPanel;
	private MemscraperMainWindowControlls navigationPanel;

	private List<MemObject> mems;
	
	private int currentPage = 1;
	private int lastMemeLoadedIndex = 0;
	private int scrollBarTrigger = 3000;
	private int scrollIncreasement = 32;
	private int memsToLoadByStep = 1;
	
	public enum LoadState{
		LOADING, WAITING
	}
	public LoadState loadingState;
	
	private Thread scrollWorker, domainWorker;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemscraperMainWindow window = new MemscraperMainWindow();
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MemscraperMainWindow() {
		loadingState = LoadState.LOADING;
		mems = new ArrayList<>();
		initializeFrame(); 	
		initializeFrameComponents();
		loadCurrentPage();		
		pack();			
	}
	
	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(5, 0));
		setMinimumSize(new Dimension(700, 800));
	}
	
	private void initializeFrameComponents() {	
		initializeContentPanel();
		initializeScrollPanel();
		initiateNavigationPanel();
	}

	private void initializeContentPanel() {
		contentPanel = new JPanel();
		contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		contentPanel.setBackground(Color.DARK_GRAY);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
	}
	
	private void initializeScrollPanel() {
		scrollPane = new JScrollPane();
		scrollPane.setViewportView(contentPanel);	
		scrollPane.getVerticalScrollBar().setUnitIncrement(scrollIncreasement);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		addScrollListener();
	}
	
	private void addScrollListener() {
		scrollPane.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> loadNewIfReachedBottom());
		scrollPane.getVerticalScrollBar().addMouseWheelListener((MouseWheelEvent e) -> scrollBehaviour(e));
	}
	
	private void loadNewIfReachedBottom() {
		if(loadingState == LoadState.WAITING) {
			if(scrollPane.getVerticalScrollBar().getValue() >=  contentPanel.getHeight() - scrollBarTrigger && scrollPane.getVerticalScrollBar().getValue() != 0) {
				System.out.println("Reached bottom");
				loadNewMems();	
			}
		}
	}
	
	private void scrollBehaviour(MouseWheelEvent e) {
		int currentValue = scrollPane.getVerticalScrollBar().getValue();
		if(e.getWheelRotation() > 0) {
			scrollPane.getVerticalScrollBar().setValue(currentValue + scrollIncreasement);
		}else {
			scrollPane.getVerticalScrollBar().setValue(currentValue - scrollIncreasement);
		}
	}
	
	private void initiateNavigationPanel() {
		navigationPanel = new MemscraperMainWindowControlls();
		getContentPane().add(navigationPanel, BorderLayout.NORTH);
		navigationPanel.domainComboBox.setSelectedItem((Object) currnetDomain); 
		navigationPanel.changePageButton.addActionListener((ActionEvent e) -> 	changePage());
		navigationPanel.changeDomainButton.addActionListener((ActionEvent e) -> changeDomain());
	}
	
	private synchronized void changePage() {
		currentPage = navigationPanel.getPageNumber();
		loadPage();
	}
	
	private synchronized void changeDomain() {
		currnetDomain = (Domains)navigationPanel.getSelectedDomain();
		currentPage =1;
		loadPage();
	}
	
	private synchronized void loadPage() {
		loadingState = LoadState.LOADING;	
		mems = new ArrayList<MemObject>();
		lastMemeLoadedIndex = 0;	
		clearContentPanel();		
		loadCurrentPage();
		loadingState = LoadState.WAITING;
	}

	private void clearContentPanel() {
		for(Component c : contentPanel.getComponents()) {
			contentPanel.remove(c);
		}
		contentPanel.revalidate();
		contentPanel.repaint();
	}
	
	private void loadCurrentPage() {
		if(domainWorker ==null||!domainWorker.isAlive()) {
		MemscraperDialog loadingDialog = new MemscraperDialog(this);
		domainWorker = new Thread() {
			public void run() {
				loadingDialog.showDomainLoadingDialog();
				loadMemInfo();
				for(int i = 0; i < mems.size(); i++) {
					addMemPanel();
				}
				loadingDialog.dispose();
				refreshWindow();
				}
			};
		domainWorker.start();
		}
	}

	private synchronized void loadMemInfo() {
		MemScraper scraper = null;
		switch (currnetDomain) {
			case JBZD:
			case JBZD_VIDEO:
				scraper = new MemScraperJbzd(currnetDomain);
				mems.addAll(scraper.getMemsFromPage(currentPage));
				setTitle("JBZD scraper [Strona: " + currentPage + "]");
				break;
			default:
				System.out.println("ERRORTYPE");
			case DEMOTYWATORY:
				scraper = new MemScraperDemotywatory();
				mems.addAll(scraper.getMemsFromPage(currentPage));
				setTitle("Demotywatory scraper [Strona: " + currentPage + "]");
				break;
		}
	}
	
	private synchronized void addMemPanel() {
		MemPanel memPanel = new MemPanel(mems.get(lastMemeLoadedIndex++));
		contentPanel.add(memPanel);		
	}

	private void refreshWindow() {	
		contentPanel.revalidate();
		contentPanel.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		revalidate();
		repaint();
		loadingState = LoadState.WAITING;
	}
	
	private synchronized void loadNewMems() {
		if(scrollWorker == null || !scrollWorker.isAlive()) {
		scrollWorker = new Thread() {
			public void run() {
				getContentPane().setCursor(new java.awt.Cursor(java.awt.Cursor.WAIT_CURSOR));
				loadingState = LoadState.LOADING;
				if(lastMemeLoadedIndex >= mems.size() - memsToLoadByStep) {
					currentPage++;
					System.out.println("Page++");
					loadMemInfo();
				}
				for(int i = 0; i < memsToLoadByStep; i++) {
					addMemPanel();
					refreshWindow();
				}	
				getContentPane().setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
			}
		};
		scrollWorker.start();
		}
	}	
}
