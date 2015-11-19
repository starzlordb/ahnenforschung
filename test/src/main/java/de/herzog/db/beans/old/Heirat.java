package de.herzog.db.beans.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "heirat_old")
public class Heirat {
	private int id;
	private int mannId;
	private int frauId;
	private String datum;
	private int datumFs;
	private String ort;
	private int ortFs;
	private String quelle;
	private String notiz;
	private int changedDate;
	private int deleted;
	private int familysearch;
	private int dokDa;
	private int dokUs;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "mann_id")
	public int getMannId() {
		return mannId;
	}

	public void setMannId(int mannId) {
		this.mannId = mannId;
	}

	@Column(name = "frau_id")
	public int getFrauId() {
		return frauId;
	}

	public void setFrauId(int frauId) {
		this.frauId = frauId;
	}

	public String getDatum() {
		return datum;
	}

	public void setDatum(String datum) {
		this.datum = datum;
	}

	@Column(name = "datum_fs")
	public int getDatumFs() {
		return datumFs;
	}

	public void setDatumFs(int datumFs) {
		this.datumFs = datumFs;
	}

	public String getOrt() {
		return ort;
	}

	public void setOrt(String ort) {
		this.ort = ort;
	}

	@Column(name = "ort_fs")
	public int getOrtFs() {
		return ortFs;
	}

	public void setOrtFs(int ortFs) {
		this.ortFs = ortFs;
	}

	public String getQuelle() {
		return quelle;
	}

	public void setQuelle(String quelle) {
		this.quelle = quelle;
	}

	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	@Column(name = "changed_date")
	public int getChangedDate() {
		return changedDate;
	}

	public void setChangedDate(int changedDate) {
		this.changedDate = changedDate;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	public int getFamilysearch() {
		return familysearch;
	}

	public void setFamilysearch(int familysearch) {
		this.familysearch = familysearch;
	}

	@Column(name = "dok_da")
	public int getDokDa() {
		return dokDa;
	}

	public void setDokDa(int dokDa) {
		this.dokDa = dokDa;
	}

	@Column(name = "dok_us")
	public int getDokUs() {
		return dokUs;
	}

	public void setDokUs(int dokUs) {
		this.dokUs = dokUs;
	}
}
