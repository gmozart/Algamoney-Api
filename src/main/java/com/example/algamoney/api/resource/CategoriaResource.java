package com.example.algamoney.api.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.algamoney.api.model.Categoria;
import com.example.algamoney.api.repository.CategoriaRepository;

@RestController  //retorno facilitado, convertendo para JSON 
@RequestMapping("/categorias") //Mapeamento da Requisição
public class CategoriaResource {
	
	@Autowired  //Spring ache e injete a interface categoria aqui
	private CategoriaRepository categoriaRepository;  
	
	@GetMapping
	public List<Categoria> listar() {
		
		return categoriaRepository.findAll(); // o findAll já foi implementado e está fazendo um select em categoria 
	}

}
