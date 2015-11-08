package de.herzog.db.beans;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.Audited;

@Entity
@Audited
public class PersonBean extends AbstractDomainBean {

	private static final long serialVersionUID = 4763311787769761002L;

	private Long vaterId;
	private boolean vaterFamilySearch = false;
	private Long mutterId;
	private boolean mutterFamilySearch = false;
	
	private boolean mann;
	
	private String nachname;
	private boolean nachnameFamilySearch = false;
	private String vornamen;
	private boolean vornamenFamilySearch = false;
	
	private String beruf;
	private boolean berufFamilySearch = false;
	private String konfession;
	private boolean konfessionFamilySearch = false;
	
	private String location;
	private boolean locationFamilySearch = false;
	
	private String notice;
	
	@Column(columnDefinition = "text")
	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	@Column(columnDefinition = "text")
	public String getVornamen() {
		return vornamen;
	}

	public void setVornamen(String vornamen) {
		this.vornamen = vornamen;
	}

	public boolean isMann() {
		return mann;
	}

	public void setMann(boolean mann) {
		this.mann = mann;
	}

	public Long getVaterId() {
		return vaterId;
	}

	public void setVaterId(Long vaterId) {
		this.vaterId = vaterId;
	}

	public Long getMutterId() {
		return mutterId;
	}

	public void setMutterId(Long mutterId) {
		this.mutterId = mutterId;
	}

	public boolean isVaterFamilySearch() {
		return vaterFamilySearch;
	}

	public void setVaterFamilySearch(boolean vaterFamilySearch) {
		this.vaterFamilySearch = vaterFamilySearch;
	}

	public boolean isMutterFamilySearch() {
		return mutterFamilySearch;
	}

	public void setMutterFamilySearch(boolean mutterFamilySearch) {
		this.mutterFamilySearch = mutterFamilySearch;
	}

	public boolean isNachnameFamilySearch() {
		return nachnameFamilySearch;
	}

	public void setNachnameFamilySearch(boolean nachnameFamilySearch) {
		this.nachnameFamilySearch = nachnameFamilySearch;
	}

	public boolean isVornamenFamilySearch() {
		return vornamenFamilySearch;
	}

	public void setVornamenFamilySearch(boolean vornamenFamilySearch) {
		this.vornamenFamilySearch = vornamenFamilySearch;
	}

	@Column(columnDefinition = "text")
	public String getBeruf() {
		return beruf;
	}

	public void setBeruf(String beruf) {
		this.beruf = beruf;
	}

	public boolean isBerufFamilySearch() {
		return berufFamilySearch;
	}

	public void setBerufFamilySearch(boolean berufFamilySearch) {
		this.berufFamilySearch = berufFamilySearch;
	}

	@Column(columnDefinition = "text")
	public String getKonfession() {
		return konfession;
	}

	public void setKonfession(String konfession) {
		this.konfession = konfession;
	}

	public boolean isKonfessionFamilySearch() {
		return konfessionFamilySearch;
	}

	public void setKonfessionFamilySearch(boolean konfessionFamilySearch) {
		this.konfessionFamilySearch = konfessionFamilySearch;
	}

	@Column(columnDefinition = "text")
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public boolean isLocationFamilySearch() {
		return locationFamilySearch;
	}

	public void setLocationFamilySearch(boolean locationFamilySearch) {
		this.locationFamilySearch = locationFamilySearch;
	}

	@Column(columnDefinition = "text")
	public String getNotice() {
		return notice;
	}

	public void setNotice(String notice) {
		this.notice = notice;
	}

}
