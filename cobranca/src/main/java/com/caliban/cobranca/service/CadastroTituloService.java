package com.caliban.cobranca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.caliban.cobranca.model.StatusTitulo;
import com.caliban.cobranca.model.Titulo;
import com.caliban.cobranca.repository.Titulos;
import com.caliban.cobranca.repository.filter.TituloFilter;
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

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		return StatusTitulo.RECEBIDO.getDescricao();
		
	}
	//filtro do campo de pesquisa
	public List<Titulo>filtrar(TituloFilter filtro){
		//pega a descrição, se ela for nula adiciona % se não já é a descricao
		String descricao = filtro.getDescricao()==null ? "%" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
		
	}
}
