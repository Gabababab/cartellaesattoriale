package it.prova.cartellaesattoriale.repository.contribuente;

import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.Contribuente;

public interface ContribuenteRepository extends CrudRepository<Contribuente, Long>, CustomContribuenteRepository {

}
