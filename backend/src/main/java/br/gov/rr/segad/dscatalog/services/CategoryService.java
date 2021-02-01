package br.gov.rr.segad.dscatalog.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.rr.segad.dscatalog.dto.CategoryDTO;
import br.gov.rr.segad.dscatalog.entities.Category;
import br.gov.rr.segad.dscatalog.repositories.CategoryRepository;
import br.gov.rr.segad.dscatalog.services.exceptions.DatabaseException;
import br.gov.rr.segad.dscatalog.services.exceptions.ResourceNotFoundException;

/**
 * A notação @Service Registra a classe CategoryService como um componente que
 * vai participar do sistema de Injecao de Depencia automatizado do Spring, ou
 * seja, quem vai gerenciar as instancias das dependencias dos objetos do tipo
 * CategoryService vai ser o Spring
 */

@Service
public class CategoryService {

	/**
	 * @Autowired Injetar uma instância( repository ) válida e gerenciada pelo
	 *            Spring, ou seja, pra injetar automaticamente a dependência
	 */

	@Autowired
	private CategoryRepository repository;

	// List All Categories
	// versão 2.0
	public Page<CategoryDTO> findAllPaged(PageRequest pageRequest) {
		Page<Category> list = repository.findAll(pageRequest);
		return list.map(x -> new CategoryDTO(x));
	}
	
	/**
	/ Permite a transação ao banco de dados, e o readOnly evita travar ou locking
	// no banco de dados
	// List All Categories
	// versão 1.0
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll(); // Lembrar que o Repository trabalha com Entidade

		// Opção 1: Funcao que converter a Lista da entidade Category em lista de
		// Categoria DTO,
		// Recurso Lambda apartir do Java 8
		// É necessário transformar para Stream e depois para List
		// Ou seja transformando uma Lista Category para uma Lista Category DTO
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

	 */
		/**
		 * Opcao 2: Funcao que converter a Lista da entidade Category em lista de
		 * Categoria DTO List<CategoryDTO> listDto = new ArrayList<>(); for (Category
		 * cat : list) { listDto.add(new CategoryDTO(cat)); } return listDto;
		 */

	

	// Find by Id Category
	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> objeto = repository.findById(id);
		// Category entity = objeto.get(); //obtem o objeto do Optional, neste caso
		// Category
		Category entity = objeto.orElseThrow(() -> new ResourceNotFoundException("Entity not found!"));
		return new CategoryDTO(entity);
	}

	// Insert
	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

	// Update
	@Transactional
	public CategoryDTO update(Long id, CategoryDTO dto) {
		try {
			Category entity = repository.getOne(id);
			entity.setName(dto.getName());
			entity = repository.save(entity);
			return new CategoryDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

	}

	// Delete
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}catch (EmptyResultDataAccessException e ) {
			throw new ResourceNotFoundException("Id not found " + id);
		}catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
		
	}

}
