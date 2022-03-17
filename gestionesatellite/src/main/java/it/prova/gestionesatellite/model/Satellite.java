package it.prova.gestionesatellite.model;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "satellite")
public class Satellite {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@NotBlank(message = "{denominazione.notblank}")
	@Column(name="denominazione")
	private String denominazione;
	
	@NotBlank(message = "{codice.notblank}")
	@Column(name="codice")
	private String codice;
	
	@Column(name="datalancio")
	private Date dataLancio;
	
	@Column(name="datarientro")
	private Date dataRientro;
	
	@Column(name="stato")
	private Stato stato;
	
	public Satellite() {}
	
	public Long getId() {
		return id;
	}
	public String getDenominazione() {
		return denominazione;
	}
	public String getCodice() {
		return codice;
	}
	public Date getDataLancio() {
		return dataLancio;
	}
	public Date getDataRientro() {
		return dataRientro;
	}
	public Stato getStato() {
		return stato;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public void setDataLancio(Date dataLancio) {
		this.dataLancio = dataLancio;
	}
	public void setDataRientro(Date dataRientro) {
		this.dataRientro = dataRientro;
	}
	public void setStato(Stato stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "Satellite [id=" + id + ", denominazione=" + denominazione + ", codice=" + codice + ", dataLancio="
				+ dataLancio + ", dataRientro=" + dataRientro + ", stato=" + stato + "]";
	}
	
	
}
