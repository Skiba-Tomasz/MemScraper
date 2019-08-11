package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.AdjustmentEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JFrame;
import javax.swing.JScrollPane;


import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;
import com.skiba.fun.memscraper.JBZDmemscraper.MemScraper;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class MemscraperMainWindow extends JFrame{

	private JScrollPane scrollPane;
	private JPanel contentPanel;

	private List<MemObject> mems;
	
	private int currentPage = 1;
	private AtomicInteger lastMemeIndex = new AtomicInteger(0);
	
	private int scrollBarTrigger = 1800;
	private int scrollIncreasement = 32;
	private int memsToLoadByStep = 2;
	
	public enum LoadState{
		LOADING, WAITING
	}
	public LoadState loadingState = LoadState.LOADING;
	
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
		loadStartingPage();
			
		pack();	
	}
	
	private void initializeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(5, 0));
		setMinimumSize(new Dimension(700, 800));
		setExtendedState(JFrame.MAXIMIZED_BOTH);
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

	private void addScrollListener() {
		scrollPane.getVerticalScrollBar().addAdjustmentListener((AdjustmentEvent e) -> loadNewIfReachedBottom());
		scrollPane.getVerticalScrollBar().addMouseWheelListener((MouseWheelEvent e) -> scrollBehaviour(e));
	}
	
	private void loadNewIfReachedBottom() {
		if(loadingState == LoadState.WAITING) {
			if(scrollPane.getVerticalScrollBar().getValue() >=  contentPanel.getHeight() - scrollBarTrigger) {
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
	
	private void loadStartingPage() {
		loadMemInfo();
		for(int i = 0; i < mems.size(); i++) {
			addMemPanel();
		}
	}

	private synchronized void loadMemInfo() {
		MemScraper scraper = new MemScraper();
		mems.addAll(scraper.loadMemsFromPage(currentPage));
		setTitle("JBZD scraper [Strona: "+ currentPage +"]");
	}
	
	private synchronized void addMemPanel() {
		MemPanel memPanel = new MemPanel(mems.get(lastMemeIndex.intValue()));
		contentPanel.add(memPanel);
		lastMemeIndex.addAndGet(1);
		refreshWindow();
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
		loadingState = LoadState.LOADING;
		Thread worker = new Thread() {
			public void run() {
				if(lastMemeIndex.intValue() >= mems.size() - memsToLoadByStep) {
					currentPage++;
					loadMemInfo();
				}
				for(int i = 0; i < memsToLoadByStep; i++) {
					addMemPanel();
				}	
				loadingState = LoadState.WAITING;
			}
		};
		worker.start();
	}	

}
