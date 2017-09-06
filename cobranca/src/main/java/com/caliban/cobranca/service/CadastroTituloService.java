package com.caliban.cobranca.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.caliban.cobranca.model.Titulo;
import com.caliban.cobranca.repository.Titulos;
//permite que o spring possa injetar essa classe em outra para utilizar as regras de negócio
@Service
public class CadastroTituloService {
	
	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		//tratamento de erro de data inválida pelo spring
		try {
			titulos.save(titulo);
		}
		catch(DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato De Data Inválido");
		}
	}
	
	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}
}
