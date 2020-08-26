package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController  //retorno facilitado, convertendo para JSON 
@RequestMapping("/categorias") //Mapeamento da Requisição
public class CategoriaResource {
	
	@Autowired  //Spring procure  e injete a interface categoria aqui
	private CategoriaRepository categoriaRepository;  
	
	@GetMapping
	public List<Categoria> listar() {
		
		return categoriaRepository.findAll(); //Lista todos os registros da tabela categoria
		
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED) //Resposta informando que o dado foi salvo
	public void criar(@RequestBody Categoria categoria) {
		
    categoriaRepository.save(categoria); //Salva um registr na tabela categoria
		
	}

}
