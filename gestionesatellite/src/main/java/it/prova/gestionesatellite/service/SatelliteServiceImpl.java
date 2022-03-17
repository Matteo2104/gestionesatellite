package it.prova.gestionesatellite.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.gestionesatellite.model.Satellite;
import it.prova.gestionesatellite.repository.SatelliteRepository;

@Service
public class SatelliteServiceImpl implements SatelliteService {
	@Autowired
	private SatelliteRepository satelliteRepository;
	
	@Override
	@Transactional
	public void aggiungiNuovo(Satellite satellite) {
		satelliteRepository.save(satellite);
	}
	
	@Override
	@Transactional
	public void aggiorna(Satellite satellite) {
		satelliteRepository.save(satellite);
	}
	
	@Override
	@Transactional
	public void rimuoviById(Long id) {
		satelliteRepository.deleteById(id);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Satellite> listAllElements() {
		return (List<Satellite>) satelliteRepository.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public Satellite caricaSingoloElemento(Long id) {
		return satelliteRepository.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Satellite> findByExample(Satellite example) {
		Specification<Satellite> specificationCriteria = (root, query, cb) -> {

			List<Predicate> predicates = new ArrayList<Predicate>();

			if (StringUtils.isNotEmpty(example.getDenominazione()))
				predicates.add(cb.like(cb.upper(root.get("denominazione")), "%" + example.getDenominazione().toUpperCase() + "%"));

			if (StringUtils.isNotEmpty(example.getCodice()))
				predicates.add(cb.like(cb.upper(root.get("codice")), "%" + example.getCodice().toUpperCase() + "%"));

			if (example.getDataLancio() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataLancio"), example.getDataLancio()));
			
			if (example.getDataRientro() != null)
				predicates.add(cb.greaterThanOrEqualTo(root.get("dataRientro"), example.getDataRientro()));
			
			if (example.getStato() != null)
				predicates.add(cb.equal(root.get("stato"), example.getStato()));

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};
		return satelliteRepository.findAll(specificationCriteria);
	}
	
	
	// FUNZIONI RAPIDE
	
	@Override
	@Transactional
	public List<Satellite> lanciatiDaPiuDiDueAnni() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -2);
		date = c.getTime();
		return satelliteRepository.lanciatiDaPiuDiDueAnni(new java.sql.Date(date.getTime()));
	}
	
	@Override
	@Transactional
	public List<Satellite> disattivatiMaNonRientrati() {
		Date date = new Date();
		return satelliteRepository.disattivatiMaNonRientrati(new java.sql.Date(date.getTime()));
	}
	
	@Override
	@Transactional
	public List<Satellite> fissiDaDieciAnni() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.YEAR, -10);
		date = c.getTime();
		//System.out.println(new java.sql.Date(date.getTime()));
		return satelliteRepository.fissiDaDieciAnni(new java.sql.Date(date.getTime()));
	}
}
