package it.prova.cartellaesattoriale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.repository.cartellaesattoriale.CartellaEsattorialeRepository;


public class CartellaEsattorialeServiceImpl implements CartellaEsattorialeService{

	@Autowired
	private CartellaEsattorialeRepository repository;


	public List<CartellaEsattoriale> listAllElements() {
		return (List<CartellaEsattoriale>)repository.findAll();
	}

	public CartellaEsattoriale caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

//	public CartellaEsattoriale caricaSingoloElementoConFilms(Long id) {
//		return repository.findByIdEager(id);
//	}

	@Transactional
	public CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaEsattorialeInstance) {
		return repository.save(cartellaEsattorialeInstance);
	}

	@Transactional
	public CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaEsattorialeInstance) {
		return repository.save(cartellaEsattorialeInstance);
	}

	@Transactional
	public void rimuovi(CartellaEsattoriale cartellaEsattorialeInstance) {
		repository.delete(cartellaEsattorialeInstance);
	}

//	TO DO
//	public List<CartellaEsattoriale> findByExample(CartellaEsattoriale example) {
//		return repository.findByExample(example);
//	}

//	public List<CartellaEsattoriale> cercaByCognomeENomeILike(String term) {
//		return repository.findByCognomeIgnoreCaseContainingOrNomeIgnoreCaseContainingOrderByNomeAsc(term, term);
//	}
//
//	public CartellaEsattoriale findByNomeAndCognome(String nome, String cognome) {
//		return repository.findByNomeAndCognome(nome, cognome);
//	}
//
//	@Override
//	public List<CartellaEsattoriale> listAllElementsEager() {
//		return (List<CartellaEsattoriale>)repository.findAllEager();
//	}
}
