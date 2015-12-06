package de.herzog.views.person;

import de.herzog.util.Roman;

public class KekuleView {
	private long kekule;
	private long generation;
	
	public KekuleView(long kekule, long generation) {
		this.kekule = kekule;
		this.generation = generation;
	}
	
	public String toString() {
		return String.valueOf(kekule) + " (" + Roman.toRoman(generation) + ")";
	}
	
	public long getKekule() {
		return kekule;
	}
	public void setKekule(long kekule) {
		this.kekule = kekule;
	}
	public long getGeneration() {
		return generation;
	}
	public void setGeneration(long generation) {
		this.generation = generation;
	}
}
