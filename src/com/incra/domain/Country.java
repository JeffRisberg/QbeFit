package com.incra.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.incra.domain.util.Objects;

/**
 * The <i>Country</i> entity
 * 
 * @author Jeffrey Risberg
 * @since 11/16/10
 */
@Entity
@Table(name = "country")
public class Country extends AbstractDomain implements Serializable {
	public static final long serialVersionUID = 0L;

	@Id
	@Column(name = "iso_alpha2", length = 2)
	private String isoAlpha2;

	@Column(name = "name")
	private String name;

	@Column(name = "name_caps")
	private String nameCaps;

	@Column(name = "iso_alpha3", length = 3)
	private String isoAlpha3;

	@Column(name = "iso_num")
	private int isoNumeric;

	public String getIsoAlpha2() {
		return isoAlpha2;
	}

	public void setIsoAlpha2(String isoAlpha2) {
		this.isoAlpha2 = isoAlpha2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameCaps() {
		return nameCaps;
	}

	public void setNameCaps(String nameCaps) {
		this.nameCaps = nameCaps;
	}

	public String getIsoAlpha3() {
		return isoAlpha3;
	}

	public void setIsoAlpha3(String isoAlpha3) {
		this.isoAlpha3 = isoAlpha3;
	}

	public int getIsoNumeric() {
		return isoNumeric;
	}

	public void setIsoNumeric(int isoNumeric) {
		this.isoNumeric = isoNumeric;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Country) {
			Country that = (Country) obj;
			return Objects.equal(name, that.name);
		}
		return false;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("Country[id=");
		buffer.append(isoAlpha2);
		buffer.append(", name=");
		buffer.append(name);
		buffer.append("]");

		return buffer.toString();
	}
}
