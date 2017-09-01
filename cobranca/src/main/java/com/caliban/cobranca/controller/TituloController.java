package com.caliban.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.caliban.cobranca.model.StatusTitulo;
import com.caliban.cobranca.model.Titulo;
import com.caliban.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView salvar(Titulo titulo) {
		titulos.save(titulo);
		ModelAndView mv = new ModelAndView("CadastroTitulo");
		mv.addObject("mensagem","Título Cadastrado Com Sucesso!");
		return mv;
	}
	
	@RequestMapping
	public String pesquisar() {
		return "PesquisaTitulos";
	}
	
	/* os status de titulos será chamado pelo TL por aqui, que tem um array dos status*/
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo>todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
}
