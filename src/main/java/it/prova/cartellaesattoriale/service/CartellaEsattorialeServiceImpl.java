package it.prova.cartellaesattoriale.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;
import it.prova.cartellaesattoriale.model.StatoCartella;
import it.prova.cartellaesattoriale.repository.cartellaesattoriale.CartellaEsattorialeRepository;

@Service
public class CartellaEsattorialeServiceImpl implements CartellaEsattorialeService{

	@Autowired
	private CartellaEsattorialeRepository repository;


	public List<CartellaEsattoriale> listAllElements(boolean eager) {
		if (eager)
			return (List<CartellaEsattoriale>) repository.findAllCartellaEsattorialeEager();

		return (List<CartellaEsattoriale>) repository.findAll();
	}

	public CartellaEsattoriale caricaSingoloElemento(Long id) {
		return repository.findById(id).orElse(null);
	}

	public CartellaEsattoriale caricaSingoloElementoConContribuenti(Long id) {
		return repository.findByIdEager(id);
	}

	@Transactional
	public CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaEsattorialeInstance) {
		return repository.save(cartellaEsattorialeInstance);
	}

	@Transactional
	public CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaEsattorialeInstance) {
		cartellaEsattorialeInstance.setStatoCartella(StatoCartella.CREATA);
		return repository.save(cartellaEsattorialeInstance);
	}

	@Transactional
	public void rimuovi(CartellaEsattoriale cartellaEsattorialeInstance) {
		repository.delete(cartellaEsattorialeInstance);
	}

	public List<CartellaEsattoriale> findByExample(CartellaEsattoriale example) {
		return repository.findByExample(example);
	}

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
