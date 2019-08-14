package com.skiba.fun.memscraper.SwingGUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import javafx.util.Duration;

public class MemPanelVideoControlls extends JPanel{
	JButton play, stop, restart, mute, load;
	private List<JButton> controlls;
	
	public MemPanelVideoControlls() {
        controlls = new ArrayList<>();
        setBackground(Color.DARK_GRAY);
        setAlignmentX(CENTER_ALIGNMENT);
		initializeButtons();
		showLoadButton();
	}
	
	public void setControlsToView() {
		remove(load);
		for(Component c : controlls) {
			add(c);
		}
		revalidate();
		repaint();
	}
	
	private void initializeButtons() {
        play = new JButton("Play");
        play.setAlignmentX(CENTER_ALIGNMENT);
        controlls.add(play);
        stop = new JButton("Stop");
        controlls.add(stop);    
        restart = new JButton("Resart");
        controlls.add(restart);   
        mute = new JButton("Mute");
        controlls.add(mute); 
        load = new JButton("Load video"); 
	}

	private void showLoadButton() {
		add(load);
	}
}
