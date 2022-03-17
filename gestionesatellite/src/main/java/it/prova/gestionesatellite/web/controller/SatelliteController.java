package it.prova.gestionesatellite.web.controller;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import it.prova.gestionesatellite.model.Satellite;
import it.prova.gestionesatellite.model.Stato;
import it.prova.gestionesatellite.service.SatelliteService;

@Controller
@RequestMapping(value = "/satellite")
public class SatelliteController {
	@Autowired
	private SatelliteService satelliteService;
	
	@GetMapping
	public ModelAndView listAll() {
		ModelAndView mv = new ModelAndView();
		List<Satellite> results = satelliteService.listAllElements();
		mv.addObject("satellite_list_attribute", results);
		mv.setViewName("satellite/list");
		return mv;
	}
	
	// CICLO RICERCA
	@GetMapping("/search")
	public String search() {
		return "satellite/search";
	}
	@PostMapping("/list")
	public String listByExample(Satellite example, ModelMap model) {
		List<Satellite> results = satelliteService.findByExample(example);
		model.addAttribute("satellite_list_attribute", results);
		return "satellite/list";
	}
	
	// CICLO AGGIUNTA
	@GetMapping("/insert")
	public String insert(Model model) {
		model.addAttribute("insert_satellite_attr", new Satellite());
		return "satellite/insert";
	}
	@PostMapping("/save")
	public String save(@Valid @ModelAttribute("insert_satellite_attr") Satellite satellite, BindingResult result,
			RedirectAttributes redirectAttrs) {

		if (result.hasErrors())
			return "satellite/insert";

		satelliteService.aggiungiNuovo(satellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
	// CICLO VISUALIZZA
	@GetMapping("/show/{idSatellite}")
	public String show(@PathVariable(required = true) Long idSatellite, Model model) {
		model.addAttribute("show_satellite_attr", satelliteService.caricaSingoloElemento(idSatellite));
		return "satellite/show";
	}
	
	
	// CICLO RIMOZIONE
	@GetMapping("/delete/{idSatellite}")
	public String delete(@PathVariable(required = true) Long idSatellite, Model model, RedirectAttributes redirectAttrs) {
		Satellite satelliteToRemove = satelliteService.caricaSingoloElemento(idSatellite);
		//System.out.println(satelliteToRemove);
		model.addAttribute("delete_satellite_attr", satelliteToRemove);
		
		if ( (satelliteToRemove.getDataRientro() != null && satelliteToRemove.getDataRientro().compareTo(new Date()) > 0 && satelliteToRemove.getStato().equals(Stato.DISATTIVATO)) || (satelliteToRemove.getDataLancio() == null || satelliteToRemove.getDataLancio().compareTo(new Date()) < 0) ) {
			return "satellite/delete";
		} 
		redirectAttrs.addFlashAttribute("errorMessage", "Non puoi rimuovere un satellite in orbita");
		return "redirect:/satellite";
	}
	@PostMapping("/remove")
	public String remove(@RequestParam(required = true) Long idSatellite,
			RedirectAttributes redirectAttrs) {
		
		//System.out.println(idImpiegato);
		satelliteService.rimuoviById(idSatellite);

		redirectAttrs.addFlashAttribute("successMessage", "Operazione eseguita correttamente");
		return "redirect:/satellite";
	}
	
}
