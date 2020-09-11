package com.example.algamoney.api.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController // retorno facilitado, convertendo para JSON
@RequestMapping("/categorias") // Mapeamento da Requisição
public class CategoriaResource {

	@Autowired // Spring procure e injete a interface categoria aqui
	private CategoriaRepository categoriaRepository;

	@GetMapping
	public List<Categoria> listar() {

		return categoriaRepository.findAll(); // Lista todos os registros da tabela categoria

	}

	@PostMapping
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = categoriaRepository.save(categoria); // Salva um registro na tabela categoria

		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}") // Através da classe Servlet,
																						// Pegando a requisição ATUAL da
																						// URI e atribuindo há variavel
																						// URI
				.buildAndExpand(categoriaSalva.getCodigo()).toUri(); // adiciona o código na uri
		// response.setHeader("Location", uri.toASCIIString()); (Depreciado) // Setar o
		// header location com a URI que foi pega.

		return ResponseEntity.created(uri).body(categoriaSalva); // Resposta informando que o dado foi salvo e definindo
																	// o status
	}
	
	@GetMapping("/{codigo}") // para que busque o codigo após o categoria/ na URI
	public Optional<Categoria> buscarPeloCodigo(@PathVariable Long codigo) { // Path parametro do caminho da URI

		return categoriaRepository.findById(codigo);
	}

}
