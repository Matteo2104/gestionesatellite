package it.prova.gestionesatellite.repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.gestionesatellite.model.Satellite;

public interface SatelliteRepository extends CrudRepository<Satellite, Long>, JpaSpecificationExecutor<Satellite> {
	@Query("select s from Satellite s where s.dataLancio > ?1")
	public List<Satellite> lanciatiDaPiuDiDueAnni(Date date);
}
