package it.prova.gestionesatellite.service;

import java.util.List;

import it.prova.gestionesatellite.model.Satellite;

public interface SatelliteService {

	public List<Satellite> findByExample(Satellite example);

	public void aggiungiNuovo(Satellite satellite);

	public List<Satellite> listAllElements();

	public Satellite caricaSingoloElemento(Long id);

	public void rimuoviById(Long id);

}
