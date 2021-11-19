package it.prova.cartellaesattoriale.repository.contribuente;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.Contribuente;

public interface ContribuenteRepository extends CrudRepository<Contribuente, Long>, CustomContribuenteRepository {

	@Query("select c from Contribuente c join fetch c.cartelle")
	List<Contribuente> findAllEager();

	@Query("select c from Contribuente c join fetch c.cartelle where c.id=?1")
	Contribuente findByIdEager(Long id);

}
