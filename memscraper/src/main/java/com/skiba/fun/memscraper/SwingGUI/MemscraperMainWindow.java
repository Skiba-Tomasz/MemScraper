package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import com.skiba.fun.memscraper.Mem.Domains;
import com.skiba.fun.memscraper.Mem.MemObject;
import com.skiba.fun.memscraper.Mem.MemScraper;
import com.skiba.fun.memscraper.Mem.MemScraperDemotywatory;
import com.skiba.fun.memscraper.Mem.MemScraperJbzd;
import com.sun.glass.ui.Cursor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Label;

public class MemscraperMainWindow extends JFrame{
	
	private Domains currnetDomain = Domains.JBZD;
	private JScrollPane scrollPane;
	private JPanel contentPanel;

	private List<MemObject> mems;
	
	private int currentPage = 1;
	private int lastMemeLoadedIndex = 0;
	
	private int scrollBarTrigger = 3000;
	private int scrollIncreasement = 32;
	private int memsToLoadByStep = 1;
	
	public enum LoadState{
		LOADING, WAITING
	}
	public LoadState loadingState = LoadState.LOADING;
	private JTextField pageNumberTextField;
	private JComboBox<Domains> domainComboBox;
	private JButton changeDomainButton;
	private Thread worker;
	private Thread domainWorker;
	
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
		mems = new ArrayList<>();
		
		initializeFrame(); 	
		initializeFrameComponents();
		
		
		JPanel navigationPanel = new JPanel();
		getContentPane().add(navigationPanel, BorderLayout.NORTH);
		navigationPanel.setLayout(new BoxLayout(navigationPanel, BoxLayout.X_AXIS));
		
		pageNumberTextField = new JTextField();
		pageNumberTextField.setMaximumSize(new Dimension(100, 20));
		navigationPanel.add(pageNumberTextField);
		pageNumberTextField.setColumns(10);
		
		JButton changePageButton = new JButton("Zmieñ stronê");
		changePageButton.addActionListener((ActionEvent e) -> changePage());
		navigationPanel.add(changePageButton);
		
		domainComboBox = new JComboBox<Domains>(Domains.values());
		domainComboBox.setSelectedItem((Object) currnetDomain); 
		domainComboBox.setMinimumSize(new Dimension(300, 20));
		navigationPanel.add(domainComboBox);
		
		changeDomainButton = new JButton("Zmieñ domenê");
		changeDomainButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				currnetDomain = (Domains)domainComboBox.getSelectedItem();
				currentPage = 0;
				changeDomain();
				
			}
		});
		navigationPanel.add(changeDomainButton);
		loadCurrentPage();		
		pack();			
	}
	
	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(5, 0));
		setMinimumSize(new Dimension(700, 800));
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
	}
	
	private void initializeFrameComponents() {	
		initializeContentPanel();
		initializeScrollPanel();
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
	
	private synchronized void changePage() {
		loadPage(true);
	}
	
	private synchronized void changeDomain() {
		loadPage(false);
		//scrollPane.getVerticalScrollBar().setValue(0);
	}
	
	private synchronized void loadPage(boolean readTextBox) {
		loadingState = LoadState.LOADING;
		if(readTextBox)currentPage = Integer.parseInt(pageNumberTextField.getText());
		else currentPage =1;
		mems = new ArrayList<MemObject>();
		lastMemeLoadedIndex = 0;
		System.out.println("lats index" + lastMemeLoadedIndex);
		for(Component c : contentPanel.getComponents()) {
			contentPanel.remove(c);
		}
		contentPanel.revalidate();
		contentPanel.repaint();
		scrollPane.getVerticalScrollBar().setValue(0);
		loadCurrentPage();
		
		loadingState = LoadState.WAITING;
	}

	private void addScrollListener() {
		scrollPane.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> loadNewIfReachedBottom());
		scrollPane.getVerticalScrollBar().addMouseWheelListener((MouseWheelEvent e) -> scrollBehaviour(e));
	}
	
	private void loadNewIfReachedBottom() {
		//System.out.println("Viewpoint of scrollbar: " + scrollPane.getVerticalScrollBar().getValue());
		if(loadingState == LoadState.WAITING) {
			if(scrollPane.getVerticalScrollBar().getValue() >=  contentPanel.getHeight() - scrollBarTrigger && scrollPane.getVerticalScrollBar().getValue() != 0) {
				System.out.println("Reached bottom");
				loadNewMems();	
			}
		}/*else System.out.println("ASSHOLE");*/
	}
	
	private void scrollBehaviour(MouseWheelEvent e) {
		int currentValue = scrollPane.getVerticalScrollBar().getValue();
		if(e.getWheelRotation() > 0) {
			scrollPane.getVerticalScrollBar().setValue(currentValue + scrollIncreasement);
		}else {
			scrollPane.getVerticalScrollBar().setValue(currentValue - scrollIncreasement);
		}
		
	}
	
	private void loadCurrentPage() {
		if(domainWorker ==null||!domainWorker.isAlive()) {
		MemscraperDialog loadingDialog = new MemscraperDialog(this);
		domainWorker = new Thread() {
			public void run() {
				loadingDialog.showDomainLoadingDialog();
/*				JOptionPane optionPane = new JOptionPane("£adowanie proszê czekaæ", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);				
				JDialog dialog = new JDialog();
				dialog.setContentPane(optionPane);
				dialog.setSize(new Dimension(300,100));
				dialog.setLocation(getWidth()/2 - dialog.getWidth()/2, getHeight()/2 - dialog.getHeight()/2);
				dialog.setModal(false);
				dialog.setAlwaysOnTop(true);
				dialog.setVisible(true);*/
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
		loadingState = LoadState.WAITING;//???
	}
	
	private synchronized void loadNewMems() {
		if(worker == null || !worker.isAlive()) {
		worker = new Thread() {
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
				loadingState = LoadState.WAITING;
			}
		};
		worker.start();
		System.out.println("Starting to load memes");
		}else System.out.println("Previous worker not finished");
	}	

}
