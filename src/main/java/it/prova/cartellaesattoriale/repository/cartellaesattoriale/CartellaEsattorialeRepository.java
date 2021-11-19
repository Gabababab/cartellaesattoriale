package it.prova.cartellaesattoriale.repository.cartellaesattoriale;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import it.prova.cartellaesattoriale.model.CartellaEsattoriale;


public interface CartellaEsattorialeRepository extends CrudRepository<CartellaEsattoriale, Long>, CustomCartellaEsattorialeRepository {
	
	@Query("select c from CartellaEsattoriale c join fetch c.contribuente where c.id = ?1")
	CartellaEsattoriale findByIdEager(Long id);

	@Query("select c from CartellaEsattoriale c join fetch c.contribuente")
	List<CartellaEsattoriale> findAllCartellaEsattorialeEager();

}
