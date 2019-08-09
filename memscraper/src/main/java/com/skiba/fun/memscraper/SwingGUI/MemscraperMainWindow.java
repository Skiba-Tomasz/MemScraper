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
	
	private int currentPage = 1;
	private int scrollMargin = 1000;
	private List<MemPanel> memPanels;
	private List<MemObject> mems;
	
	private int lastMemeIndex = 0;
	private int memLoadedCount = 0;

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
		
		addMemsToViewpoint(currentPage);
		frame.setVisible(true);
		
		addScrollListener();
	}
	
	private void loadNextMeme() {
		
	}

	private void addMemsToViewpoint(int page) {
		MemScraper scraper = new MemScraper();
		mems.addAll(scraper.loadMemsFromPage(page));
		memLoadedCount = mems.size()-1;
		memPanels = new ArrayList<>();
		
		for(; lastMemeIndex < memLoadedCount; lastMemeIndex++) {
			MemObject mem = mems.get(lastMemeIndex);
			memPanels.add(new MemPanel(mem));
		}
		for(MemPanel memPanel : memPanels) {
			int backingPos = scrollPane.getVerticalScrollBar().getValue();
			contentPanel.add(memPanel);
			contentPanel.revalidate();
			contentPanel.repaint();
			scrollPane.revalidate();
			scrollPane.repaint();
			frame.revalidate();
			frame.repaint();
			scrollPane.getVerticalScrollBar().setValue(backingPos);
		}
	}
	
	private void addScrollListener() {
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				
				System.out.println(scrollPane.getVerticalScrollBar().getValue() + " x " +  scrollPane.getVerticalScrollBar().getMaximum());
				if(e.getAdjustmentType() == AdjustmentEvent.TRACK) {
					if(scrollPane.getVerticalScrollBar().getValue() >= scrollPane.getVerticalScrollBar().getMaximum() - scrollMargin) {
						loadMemsAndRefresh();
					}
				}
			}
		});
	}

	private void loadMemsAndRefresh() {
		Thread worker = new Thread() {
			public void run() {
				currentPage ++;
				addMemsToViewpoint(currentPage);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		worker.start();
	}

}
