package it.prova.cartellaesattoriale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.Contribuente;
import it.prova.cartellaesattoriale.repository.contribuente.ContribuenteRepository;


public class ContribuenteServiceImpl implements ContribuenteService {

	@Autowired
	private ContribuenteRepository repository;


	public List<Contribuente> listAllElements() {
		return (List<Contribuente>)repository.findAll();
	}

	public Contribuente caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

//	public Contribuente caricaSingoloElementoConFilms(Long id) {
//		return repository.findByIdEager(id);
//	}

	@Transactional
	public Contribuente aggiorna(Contribuente contribuenteInstance) {
		return repository.save(contribuenteInstance);
	}

	@Transactional
	public Contribuente inserisciNuovo(Contribuente contribuenteInstance) {
		return repository.save(contribuenteInstance);
	}

	@Transactional
	public void rimuovi(Contribuente contribuenteInstance) {
		repository.delete(contribuenteInstance);
	}

	public List<Contribuente> findByExample(Contribuente example) {
		return repository.findByExample(example);
	}

//	public List<Contribuente> cercaByCognomeENomeILike(String term) {
//		return repository.findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(term, term);
//	}
//
//	public Contribuente findByNomeAndCognome(String nome, String cognome) {
//		return repository.findByNomeAndCognome(nome, cognome);
//	}
//
//	@Override
//	public List<Contribuente> listAllElementsEager() {
//		return (List<Contribuente>)repository.findAllEager();
//	}
}
