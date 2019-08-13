package com.skiba.fun.memscraper.SwingGUI;

public class SharedData {
	private static SharedData instance;
	private boolean debugMode;
	
	public boolean isDebugMode() {
		return debugMode;
	}

	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	public static SharedData getInstance() {
		if(instance == null) {
			instance = new SharedData();
		}
		return instance;
	}
	
	private SharedData(){
		debugMode = false;
	}
}
