package br.gov.rr.segad.dscatalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.gov.rr.segad.dscatalog.dto.CategoryDTO;
import br.gov.rr.segad.dscatalog.entities.Category;
import br.gov.rr.segad.dscatalog.repositories.CategoryRepository;
import br.gov.rr.segad.dscatalog.services.exceptions.EntityNotFoundException;

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

	// Permite a transação ao banco de dados, e o readOnly evita travar ou locking
	// no banco de dados
	@Transactional(readOnly = true)
	public List<CategoryDTO> findAll() {
		List<Category> list = repository.findAll(); // Lembrar que o Repository trabalha com Entidade

		// Opção 1: Funcao que converter a Lista da entidade Category em lista de
		// Categoria DTO,
		// Recurso Lambda apartir do Java 8
		// É necessário transformar para Stream e depois para List
		// Ou seja transformando uma Lista Category para uma Lista Category DTO
		return list.stream().map(x -> new CategoryDTO(x)).collect(Collectors.toList());

		/**
		 * Opcao 2: Funcao que converter a Lista da entidade Category em lista de
		 * Categoria DTO List<CategoryDTO> listDto = new ArrayList<>(); for (Category
		 * cat : list) { listDto.add(new CategoryDTO(cat)); } return listDto;
		 */

	}

	@Transactional(readOnly = true)
	public CategoryDTO findById(Long id) {
		Optional<Category> objeto = repository.findById(id);
		// Category entity = objeto.get(); //obtem o objeto do Optional, neste caso
		// Category
		Category entity = objeto.orElseThrow(() -> new EntityNotFoundException("Entity not found!"));
		return new CategoryDTO(entity);
	}

	@Transactional
	public CategoryDTO insert(CategoryDTO dto) {
		Category entity = new Category();
		entity.setName(dto.getName());
		entity = repository.save(entity);
		return new CategoryDTO(entity);
	}

}
