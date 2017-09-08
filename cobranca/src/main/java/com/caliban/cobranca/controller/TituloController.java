package com.caliban.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.caliban.cobranca.model.StatusTitulo;
import com.caliban.cobranca.model.Titulo;

import com.caliban.cobranca.repository.filter.TituloFilter;
import com.caliban.cobranca.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	private static final String CADASTRO_VIEW = "CadastroTitulo";
		
	@Autowired
	private CadastroTituloService cadastroTituloService;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()) {
			return CADASTRO_VIEW;
		}
		//tratamento de erro de data inválida pelo spring
		try {
			cadastroTituloService.salvar(titulo);	
			attributes.addFlashAttribute("mensagem", "Título Salvo Com Sucesso!");
			return "redirect:/titulos/novo";
		}
		//captura a excessão lançada pelo service
		catch(IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return CADASTRO_VIEW;
		}
	}
	
	@RequestMapping
	public ModelAndView pesquisar(@ModelAttribute("filtro")TituloFilter filtro) {
		List<Titulo>todosTitulos = cadastroTituloService.filtrar(filtro);
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", todosTitulos);
		return mv;
	}
	
	//faz o mapeamento do codigo
	@RequestMapping("{codigo}")
	public ModelAndView editar(@PathVariable("codigo")Titulo titulo) {
		/*recupera o titulo do codigo mapeado que foi convertido em long
		Titulo titulo = titulos.findOne(codigo);
		*/
		//cria a página
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		//adiciona ná página o conteudo recuperado
		mv.addObject(titulo);
		return mv;
	}
	
	//quando a requisição for DELETE o method vai recebe-la e vai ser direcionado para o metodo excluir
	@RequestMapping(value="{codigo}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		cadastroTituloService.excluir(codigo);
		attributes.addFlashAttribute("mensagem", "Título Excluído Com Sucesso!");
		return "redirect:/titulos";
	}
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo) {
		return cadastroTituloService.receber(codigo);
		
	}
	
	/* os status de titulos será chamado pelo TL por aqui, que tem um array dos status*/
	@ModelAttribute("todosStatusTitulo")
	public List<StatusTitulo>todosStatusTitulo(){
		return Arrays.asList(StatusTitulo.values());
	}
}
