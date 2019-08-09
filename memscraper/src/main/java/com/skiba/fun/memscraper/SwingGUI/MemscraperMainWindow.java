package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.skiba.fun.memscraper.JBZDmemscraper.MemObject;
import com.skiba.fun.memscraper.JBZDmemscraper.MemScraper;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.BoxLayout;

public class MemscraperMainWindow {

	private JFrame frame;
	private JScrollPane scrollPane;
	private JPanel contentPanel;
	
	private List<MemPanel> memPanels;
	private List<MemObject> mems;
	
	private int currentPage = 1;
	private int lastMemeIndex = 0;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MemscraperMainWindow window = new MemscraperMainWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MemscraperMainWindow() {
		initialize();
	}

	private void initialize() {
		mems = new ArrayList<>();
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(650,800));
		
		scrollPane = new JScrollPane();
		scrollPane.getVerticalScrollBar().setUnitIncrement(32);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		contentPanel = new JPanel();
		scrollPane.setViewportView(contentPanel);
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		
		
		loadStartingPage();
		frame.setVisible(true);
	}
	
	private void loadStartingPage() {
		loadMemInfo();
		for(int i = 0; i < mems.size(); i++) {
			addMemPanel(i);
		}
	}

	private void loadMemInfo() {
		MemScraper scraper = new MemScraper();
		mems.addAll(scraper.loadMemsFromPage(currentPage));
	}
	
	private void addMemPanel(int memIndex) {
		MemPanel memPanel = new MemPanel(mems.get(memIndex));
		contentPanel.add(memPanel);
		lastMemeIndex++;
		refreshWindow();
	}

	private void refreshWindow() {
		contentPanel.revalidate();
		contentPanel.repaint();
		scrollPane.revalidate();
		scrollPane.repaint();
		frame.revalidate();
		frame.repaint();
	}	

}
