package de.shop.artikelverwaltung.domain;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Fahrrad extends Produkt {
	
	
	enum Farbe {
		weiss, schwarz, grau, rot, blau, gelb, gruen, bunt,
	}

	enum Kategorie {
		Mountainbike, Tandem, BMX, Rennrad, Einrad, Laufrad, Strassenfahrrad, Liegerad, Hochrad,
	}

	enum Art {
		Herrenrad, Damenrad, Kinderfahrrad,
	}

	@Size(min = 1, max = 2)
	@Pattern(regexp = "[0-9][0-9]")
	private String gaenge;

	@Size(min = 1, max = 2)
	@Pattern(regexp = "[0-9][0-9]")
	private String zollgroesse;

	@Size(min = 2, max = 3)
	@Pattern(regexp = "[0-9]{3,5}")
	private String rahmenhoehe;

	public String getGaenge() {
		return gaenge;
	}

	public void setGaenge(String gaenge) {
		this.gaenge = gaenge;
	}

	public String getZollgroesse() {
		return zollgroesse;
	}

	public void setZollgroesse(String zollgroesse) {
		this.zollgroesse = zollgroesse;
	}

	public String getRahmenhoehe() {
		return rahmenhoehe;
	}

	public void setRahmenhoehe(String rahmenhoehe) {
		this.rahmenhoehe = rahmenhoehe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((gaenge == null) ? 0 : gaenge.hashCode());
		result = prime * result
				+ ((rahmenhoehe == null) ? 0 : rahmenhoehe.hashCode());
		result = prime * result
				+ ((zollgroesse == null) ? 0 : zollgroesse.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Fahrrad other = (Fahrrad) obj;
		if (gaenge == null) {
			if (other.gaenge != null)
				return false;
		}
		else if (!gaenge.equals(other.gaenge))
			return false;
		if (rahmenhoehe == null) {
			if (other.rahmenhoehe != null)
				return false;
		}
		else if (!rahmenhoehe.equals(other.rahmenhoehe))
			return false;
		if (zollgroesse == null) {
			if (other.zollgroesse != null)
				return false;
		}
		else if (!zollgroesse.equals(other.zollgroesse))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Fahrrad [gaenge=" + gaenge + ", zollgroesse=" + zollgroesse
				+ ", rahmenhoehe=" + rahmenhoehe + "]";
	}

}
