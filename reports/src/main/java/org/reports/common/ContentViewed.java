package org.reports.common;

public enum ContentViewed {
	CPU(0),
	Memory(1),
	NetWork(2),
	Disks(3);
	
	private int value;
	private ContentViewed(int value){
		this.value = value;
	}
}
