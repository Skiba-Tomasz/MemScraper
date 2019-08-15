package com.skiba.fun.memscraper.SwingGUI;

import java.awt.Dimension;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.skiba.fun.memscraper.Mem.Domains;

public class MemscraperMainWindowControlls extends JPanel{
	JButton changeDomainButton, changePageButton;
	JComboBox<Domains> domainComboBox;
	JTextField pageNumberTextField;

	public MemscraperMainWindowControlls() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		initiatePageNumberTextField();
		initiateButtons();
		initiateDomainCombo();		
		addComponentsInOrder();
	}
	
	public Domains getSelectedDomain() {
		return (Domains)domainComboBox.getSelectedItem();
	}
	
	public int getPageNumber() {
		return Integer.parseInt(pageNumberTextField.getText());
	}
	
	private void initiatePageNumberTextField() {
		pageNumberTextField = new JTextField();
		pageNumberTextField.setMaximumSize(new Dimension(100, 20));
		pageNumberTextField.setColumns(10);
		
	}

	private void initiateButtons() {
		changePageButton = new JButton("Zmieñ stronê");
		changeDomainButton = new JButton("Zmieñ domenê");
	}
	
	private void initiateDomainCombo() {
		domainComboBox = new JComboBox<Domains>(Domains.values());
		domainComboBox.setMinimumSize(new Dimension(300, 20));
	}
	
	private void addComponentsInOrder() {
		add(pageNumberTextField);	
		add(changePageButton);		
		add(domainComboBox);	
		add(changeDomainButton);
	}
}
