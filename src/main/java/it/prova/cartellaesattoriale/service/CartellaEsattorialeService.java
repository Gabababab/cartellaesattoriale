package it.prova.cartellaesattoriale.service;

import java.util.List;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;

public interface CartellaEsattorialeService {

	List<CartellaEsattoriale> listAllElements(boolean eager);

//	List<CartellaEsattoriale> listAllElementsEager();

	CartellaEsattoriale caricaSingoloElemento(Long id);

	CartellaEsattoriale caricaSingoloElementoConContribuenti(Long id);

	CartellaEsattoriale aggiorna(CartellaEsattoriale cartellaEsattorialeInstance);

	CartellaEsattoriale inserisciNuovo(CartellaEsattoriale cartellaEsattorialeInstance);

	void rimuovi(CartellaEsattoriale cartellaEsattorialeInstance);

	List<CartellaEsattoriale> findByExample(CartellaEsattoriale example);

//	List<CartellaEsattoriale> cercaByCognomeENomeILike(String term);
//	
//	CartellaEsattoriale findByNomeAndCognome(String nome, String cognome);
}
