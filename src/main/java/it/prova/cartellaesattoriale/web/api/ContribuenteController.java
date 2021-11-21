package it.prova.cartellaesattoriale.web.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.cartellaesattoriale.dto.CartellaEsattorialeDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteBusinessDTO;
import it.prova.cartellaesattoriale.dto.ContribuenteDTO;
import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.model.StatoCartella;
import it.prova.cartellaesattoriale.service.ContribuenteService;
import it.prova.cartellaesattoriale.web.api.exceptions.ContribuenteConCartelleAssociateException;
import it.prova.cartellaesattoriale.web.api.exceptions.ContribuenteNotFoundException;

@RestController
@RequestMapping("/api/contribuente")
public class ContribuenteController {

	@Autowired
	private ContribuenteService contribuenteService;

	@GetMapping
	public List<ContribuenteDTO> getAll() {
		// senza DTO qui hibernate dava il problema del N + 1 SELECT
		// (probabilmente dovuto alle librerie che serializzano in JSON)
		return ContribuenteDTO.createContribuenteDTOListFromModelList(contribuenteService.listAllElements(), true);
	}

	@GetMapping("/{id}")
	public ContribuenteDTO findById(@PathVariable(value = "id", required = true) long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElementoConCartelle(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuente, true);
	}

	// gli errori di validazione vengono mostrati con 400 Bad Request ma
	// elencandoli grazie al ControllerAdvice
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ContribuenteDTO createNew(@Valid @RequestBody ContribuenteDTO contribuenteInput) {
		Contribuente contribuenteInserito = contribuenteService
				.inserisciNuovo(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteInserito, false);
	}

	@PutMapping("/{id}")
	public ContribuenteDTO update(@Valid @RequestBody ContribuenteDTO contribuenteInput,
			@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		contribuenteInput.setId(id);
		Contribuente contribuenteAggiornato = contribuenteService.aggiorna(contribuenteInput.buildContribuenteModel());
		return ContribuenteDTO.buildContribuenteDTOFromModel(contribuenteAggiornato, false);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable(required = true) Long id) {
		Contribuente contribuente = contribuenteService.caricaSingoloElemento(id);

		if (contribuente == null)
			throw new ContribuenteNotFoundException("Contribuente not found con id: " + id);

		if (contribuente.getCartelle().size() != 0)
			throw new ContribuenteConCartelleAssociateException("Contribuente con film associati, rimozione rifiutata");

		contribuenteService.rimuovi(contribuente);
	}

	@PostMapping("/search")
	public List<ContribuenteDTO> search(@RequestBody ContribuenteDTO example) {
		return ContribuenteDTO.createContribuenteDTOListFromModelList(
				contribuenteService.findByExample(example.buildContribuenteModel()), false);
	}

	@GetMapping("/verificaContenziosi")
	public List<ContribuenteBusinessDTO> verificaContenziosi() {

		List<Contribuente> daVerificare = contribuenteService.listAllElementsEager();

		List<ContribuenteBusinessDTO> result = new ArrayList<ContribuenteBusinessDTO>();
		ContribuenteBusinessDTO contribuentePerVerifica;

		for (Contribuente contribuenteItem : daVerificare) {

			// carico il contribuente lazy

			// -----Da Fixare-----
			contribuentePerVerifica = ContribuenteBusinessDTO.buildContribuenteBusinessDTOFromModel(contribuenteItem,
					false);

			for (CartellaEsattoriale cartellaEsattorialeItem : contribuenteItem.getCartelle()) {

				if (cartellaEsattorialeItem.getStatoCartella().equals(StatoCartella.IN_CONTENZIOSO)) {
					contribuentePerVerifica.setDaAttenzionare(true);
				}
			}
		}
		return result;
	}

	@GetMapping("reportContribuenti")
	public List<ContribuenteBusinessDTO> report() {

		List<Contribuente> listaPerReport = contribuenteService.listAllElementsEager();
		List<ContribuenteBusinessDTO> listaPerReportDTO = ContribuenteBusinessDTO
				.createContribuenteBusinessDTOListFromModelList(listaPerReport, true);
		
		Integer importoTotale=0;
		Integer totaleConclusoEPagato=0;
		Integer totaleContenzioso=0;
		
		for(ContribuenteBusinessDTO contribuenteItem: listaPerReportDTO) {
			
			for(CartellaEsattorialeDTO cartellaItem: contribuenteItem.getCartelle()) {
				
				importoTotale+=cartellaItem.getImporto();
				if(cartellaItem.getStato().equals(StatoCartella.IN_CONTENZIOSO))
					totaleContenzioso+=cartellaItem.getImporto();
				if(cartellaItem.getStato().equals(StatoCartella.CONCLUSA))
					totaleConclusoEPagato+=cartellaItem.getImporto();
					
			}
			
			contribuenteItem.setConclusoEPagato(totaleConclusoEPagato);
			contribuenteItem.setImportoCartelle(importoTotale);
			contribuenteItem.setInContenzioso(totaleContenzioso);
			
		}
		return listaPerReportDTO;
	}
}
