package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MemscraperDialog extends JDialog{
	JFrame windowParent;
	
	public MemscraperDialog(JFrame parent) {
		this.windowParent = parent;
	}
	
	
	public void showDomainLoadingDialog() {
		JOptionPane optionPane = new JOptionPane("£adowanie proszê czekaæ", JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);				
		setContentPane(optionPane);
		setSize(new Dimension(300,100));
		setLocation(getParentOffsetX() + getParentCenterX(), getParentOffsetY() + getParentCenterY());
		setModal(false);
		setAlwaysOnTop(true);
		setVisible(true);
	}

	
	private int getParentCenterX() {
		return windowParent.getWidth()/2 - getWidth()/2;
	}
	private int getParentCenterY() {
		return windowParent.getHeight()/2 - getHeight()/2;
	}
	private int getParentOffsetX() {
		return windowParent.getLocation().x;
	}
	private int getParentOffsetY() {
		return windowParent.getLocation().y;
	}
}
