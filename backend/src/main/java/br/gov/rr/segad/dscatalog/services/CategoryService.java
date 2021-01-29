package br.gov.rr.segad.dscatalog.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.rr.segad.dscatalog.entities.Category;
import br.gov.rr.segad.dscatalog.repositories.CategoryRepository;

/** A notação @Service Registra a classe CategoryService como um componente que vai participar do sistema de
 Injecao de Depencia automatizado do Spring, ou seja, quem vai gerenciar as instancias das dependencias
 dos objetos do tipo CategoryService vai ser o Spring
*/

@Service  
public class CategoryService {
	
	/** @Autowired Injetar uma instância( repository ) válida e  gerenciada pelo Spring, 
	ou seja, pra injetar automaticamente a dependência
	*/
	
	@Autowired
	private CategoryRepository repository;
	
	// Permite a transação ao banco de dados, e o readOnly evita travar ou locking no banco de dados
	@Transactional(readOnly = true)
	public List<Category> findAll(){
		return repository.findAll();
	}

}
