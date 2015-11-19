package de.herzog.db.beans.old;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "person_old")
public class Person {
	private int id;
	private String geschlecht;
	private String nachname;
	private int nachnameFs;
	private String vornamen;
	private int vornamenFs;
	private String geburtsdatum;
	private int geburtsdatumFs;
	private String geburtsort;
	private int geburtsortFs;
	private String geburtsnotiz;
	private String geburtsquelle;
	private String taufdatum;
	private int taufdatumFs;
	private String taufort;
	private int taufortFs;
	private String taufnotiz;
	private String taufquelle;
	private String sterbedatum;
	private int sterbedatumFs;
	private String sterbeort;
	private int sterbeortFs;
	private String sterbenotiz;
	private String sterbequelle;
	private String konfession;
	private int konfessionFs;
	private String beruf;
	private int berufFs;
	private String wohnort;
	private int wohnortFs;
	private String notiz;
	private int vaterId;
	private int vaterIdFs;
	private int mutterId;
	private int mutterIdFs;
	private int changedDate;
	private int deleted;
	private int geburtDokDa;
	private int geburtDokUs;
	private int taufDokDa;
	private int taufDokUs;
	private int sterbeDokDa;
	private int sterbeDokUs;

	@Id
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(columnDefinition = "text")
	public String getGeschlecht() {
		return geschlecht;
	}

	public void setGeschlecht(String geschlecht) {
		this.geschlecht = geschlecht;
	}

	@Column(columnDefinition = "text")
	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Column(name = "nachname_fs")
	public int getNachnameFs() {
		return nachnameFs;
	}

	public void setNachnameFs(int nachnameFs) {
		this.nachnameFs = nachnameFs;
	}

	@Column(columnDefinition = "text")
	public String getVornamen() {
		return vornamen;
	}

	public void setVornamen(String vornamen) {
		this.vornamen = vornamen;
	}

	@Column(name = "vornamen_fs")
	public int getVornamenFs() {
		return vornamenFs;
	}

	public void setVornamenFs(int vornamenFs) {
		this.vornamenFs = vornamenFs;
	}

	@Column(columnDefinition = "text")
	public String getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(String geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

	@Column(name = "geburtsdatum_fs")
	public int getGeburtsdatumFs() {
		return geburtsdatumFs;
	}

	public void setGeburtsdatumFs(int geburtsdatumFs) {
		this.geburtsdatumFs = geburtsdatumFs;
	}

	@Column(columnDefinition = "text")
	public String getGeburtsort() {
		return geburtsort;
	}

	public void setGeburtsort(String geburtsort) {
		this.geburtsort = geburtsort;
	}

	@Column(name = "geburtsort_fs")
	public int getGeburtsortFs() {
		return geburtsortFs;
	}

	public void setGeburtsortFs(int geburtsortFs) {
		this.geburtsortFs = geburtsortFs;
	}

	@Column(columnDefinition = "text")
	public String getGeburtsnotiz() {
		return geburtsnotiz;
	}

	public void setGeburtsnotiz(String geburtsnotiz) {
		this.geburtsnotiz = geburtsnotiz;
	}

	@Column(columnDefinition = "text")
	public String getGeburtsquelle() {
		return geburtsquelle;
	}

	public void setGeburtsquelle(String geburtsquelle) {
		this.geburtsquelle = geburtsquelle;
	}

	@Column(columnDefinition = "text")
	public String getTaufdatum() {
		return taufdatum;
	}

	public void setTaufdatum(String taufdatum) {
		this.taufdatum = taufdatum;
	}

	@Column(name = "taufdatum_fs")
	public int getTaufdatumFs() {
		return taufdatumFs;
	}

	public void setTaufdatumFs(int taufdatumFs) {
		this.taufdatumFs = taufdatumFs;
	}

	@Column(columnDefinition = "text")
	public String getTaufort() {
		return taufort;
	}

	public void setTaufort(String taufort) {
		this.taufort = taufort;
	}

	@Column(name = "taufort_fs")
	public int getTaufortFs() {
		return taufortFs;
	}

	public void setTaufortFs(int taufortFs) {
		this.taufortFs = taufortFs;
	}

	@Column(columnDefinition = "text")
	public String getTaufnotiz() {
		return taufnotiz;
	}

	public void setTaufnotiz(String taufnotiz) {
		this.taufnotiz = taufnotiz;
	}

	@Column(columnDefinition = "text")
	public String getTaufquelle() {
		return taufquelle;
	}

	public void setTaufquelle(String taufquelle) {
		this.taufquelle = taufquelle;
	}

	@Column(columnDefinition = "text")
	public String getSterbedatum() {
		return sterbedatum;
	}

	public void setSterbedatum(String sterbedatum) {
		this.sterbedatum = sterbedatum;
	}

	@Column(name = "sterbedatum_fs")
	public int getSterbedatumFs() {
		return sterbedatumFs;
	}

	public void setSterbedatumFs(int sterbedatumFs) {
		this.sterbedatumFs = sterbedatumFs;
	}

	@Column(columnDefinition = "text")
	public String getSterbeort() {
		return sterbeort;
	}

	public void setSterbeort(String sterbeort) {
		this.sterbeort = sterbeort;
	}

	@Column(name = "sterbeort_fs")
	public int getSterbeortFs() {
		return sterbeortFs;
	}

	public void setSterbeortFs(int sterbeortFs) {
		this.sterbeortFs = sterbeortFs;
	}

	@Column(columnDefinition = "text")
	public String getSterbenotiz() {
		return sterbenotiz;
	}

	public void setSterbenotiz(String sterbenotiz) {
		this.sterbenotiz = sterbenotiz;
	}

	@Column(columnDefinition = "text")
	public String getSterbequelle() {
		return sterbequelle;
	}

	public void setSterbequelle(String sterbequelle) {
		this.sterbequelle = sterbequelle;
	}

	@Column(columnDefinition = "text")
	public String getKonfession() {
		return konfession;
	}

	public void setKonfession(String konfession) {
		this.konfession = konfession;
	}

	@Column(name = "konfession_fs")
	public int getKonfessionFs() {
		return konfessionFs;
	}

	public void setKonfessionFs(int konfessionFs) {
		this.konfessionFs = konfessionFs;
	}

	@Column(columnDefinition = "text")
	public String getBeruf() {
		return beruf;
	}

	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}

	@Column(name = "beruf_fs")
	public int getBerufFs() {
		return berufFs;
	}

	public void setBerufFs(int berufFs) {
		this.berufFs = berufFs;
	}

	@Column(columnDefinition = "text")
	public String getWohnort() {
		return wohnort;
	}

	public void setWohnort(String wohnort) {
		this.wohnort = wohnort;
	}

	@Column(name = "wohnort_fs")
	public int getWohnortFs() {
		return wohnortFs;
	}

	public void setWohnortFs(int wohnortFs) {
		this.wohnortFs = wohnortFs;
	}

	@Column(columnDefinition = "text")
	public String getNotiz() {
		return notiz;
	}

	public void setNotiz(String notiz) {
		this.notiz = notiz;
	}

	@Column(name = "vater_id")
	public int getVaterId() {
		return vaterId;
	}

	public void setVaterId(int vaterId) {
		this.vaterId = vaterId;
	}

	@Column(name = "vater_id_fs")
	public int getVaterIdFs() {
		return vaterIdFs;
	}

	public void setVaterIdFs(int vaterIdFs) {
		this.vaterIdFs = vaterIdFs;
	}

	@Column(name = "mutter_id")
	public int getMutterId() {
		return mutterId;
	}

	public void setMutterId(int mutterId) {
		this.mutterId = mutterId;
	}

	@Column(name = "mutter_id_fs")
	public int getMutterIdFs() {
		return mutterIdFs;
	}

	public void setMutterIdFs(int mutterIdFs) {
		this.mutterIdFs = mutterIdFs;
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

	@Column(name = "geburt_dok_da")
	public int getGeburtDokDa() {
		return geburtDokDa;
	}

	public void setGeburtDokDa(int geburtDokDa) {
		this.geburtDokDa = geburtDokDa;
	}

	@Column(name = "geburt_dok_us")
	public int getGeburtDokUs() {
		return geburtDokUs;
	}

	public void setGeburtDokUs(int geburtDokUs) {
		this.geburtDokUs = geburtDokUs;
	}

	@Column(name = "tauf_dok_da")
	public int getTaufDokDa() {
		return taufDokDa;
	}

	public void setTaufDokDa(int taufDokDa) {
		this.taufDokDa = taufDokDa;
	}

	@Column(name = "tauf_dok_us")
	public int getTaufDokUs() {
		return taufDokUs;
	}

	public void setTaufDokUs(int taufDokUs) {
		this.taufDokUs = taufDokUs;
	}

	@Column(name = "sterbe_dok_da")
	public int getSterbeDokDa() {
		return sterbeDokDa;
	}

	public void setSterbeDokDa(int sterbeDokDa) {
		this.sterbeDokDa = sterbeDokDa;
	}

	@Column(name = "sterbe_dok_us")
	public int getSterbeDokUs() {
		return sterbeDokUs;
	}

	public void setSterbeDokUs(int sterbeDokUs) {
		this.sterbeDokUs = sterbeDokUs;
	}
}
