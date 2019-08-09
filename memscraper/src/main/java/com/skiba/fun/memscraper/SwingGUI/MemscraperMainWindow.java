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

	private void addMemsToViewpoint(int page) {
		MemScraper scraper = new MemScraper();
		List<MemObject> mems = scraper.loadMemsFromPage(page);
		memPanels = new ArrayList<>();
		
		for(MemObject mem : mems) {
			memPanels.add(new MemPanel(mem));
		}
		for(MemPanel memPanel : memPanels) {
			contentPanel.add(memPanel);
		}
	}
	
	private void addScrollListener() {
		scrollPane.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {			
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				JViewport vp = scrollPane.getViewport();
				System.out.println(vp.getViewPosition().y + " x " +  (contentPanel.getHeight()- scrollMargin));
				if(e.getAdjustmentType() == AdjustmentEvent.TRACK) {
					if(vp.getViewPosition().y >=  contentPanel.getHeight() - scrollMargin) {
						currentPage ++;
						int backingPosition = contentPanel.getHeight() - scrollMargin;
						addMemsToViewpoint(currentPage);
						scrollPane.getVerticalScrollBar().setValue(backingPosition);
						contentPanel.revalidate();
						contentPanel.repaint();
						scrollPane.revalidate();
						scrollPane.repaint();
						frame.revalidate();
						frame.repaint();
					}
				}
			}
		});
		/*scrollPane.getViewport().addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//System.out.println(scrollPane.getVerticalScrollBar().getValue());
				JViewport vp = scrollPane.getViewport();
				System.out.println(vp.getView().getHeight() * 0.8+ " x " + vp.getViewPosition().y );
				System.out.println(memPanels.get(memPanels.size()-1).getY());
				if(vp.getView().getHeight() - scrollMargin < vp.getViewPosition().y) {
					System.out.println("Loading Next");
					
					currentPage ++;
					int savedYPosition = memPanels.get(memPanels.size()-1).getY();
					addMemsToViewpoint(currentPage);
					contentPanel.revalidate();
					contentPanel.repaint();
					scrollPane.revalidate();
					scrollPane.repaint();
					vp = scrollPane.getViewport();
					vp.setViewPosition(new Point(vp.getViewPosition().x, savedYPosition));
				}
			}
		});*/

	}

}
