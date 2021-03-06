//sempre que certa ação ocorrer executar uma função
$('#confirmarExcluir').on('show.bs.modal',function(event){
	
	//guarda o valor de quem disparar o evento
	var button = $(event.relatedTarget);
	//pega o valor data no botão acionado que é o código do título
	var codigoTitulo = button.data('codigo');
	var descricaoTitulo = button.data('descricao');
	var valorTitulo = button.data('valor');
	//pega o modal como referencia de quem diusparou o evento (this)
	var modal = $(this);
	//busca o form do modal
	var form = modal.find('form');
	//busca o action no form que é a string ou atributo do endereço "/titulos" está th:attr="data-url-base='/titulos'" para nao concatenar
	var action = form.data('url-base');
	//se o endereço não terminar com uma barra então adicionamos uma barra para não dar problema
	if(!action.endsWith('/')){
		action += '/';
	}
	//agora no atributo do formulario concatena em action o action+codigo
	form.attr('action',action + codigoTitulo);
	//aqui procuranmos o modal-body dentro dele o span e escrevemos em html a mensagem concatenada com a descrição
	modal.find('.modal-body span').html('Tem Certeza Que Deseja Exluir O Título <strong>'+ descricaoTitulo +'</strong>?<br/>Valor: '+valorTitulo);
});
//essa função inicializa sempre que a página for aberta
$(function(){
	$('[rel="tooltip"]').tooltip();
	//carregando a mascara de valor
	$('.js-currency').maskMoney({decimal:',',thousands:'.',allowZero: true});
	
	$('.js-atualizar-status').on('click',function(event){
		//evita o comportamento default do link que é abrir uma nova página da url formada
		event.preventDefault();
		//pega o evento do botão ao ser clicado
		var botaoReceber = $(event.currentTarget);
		//pega a url do botao
		var urlReceber = botaoReceber.attr('href');
		
		var response = $.ajax({
			url: urlReceber,
			type: 'PUT'
		});
		response.done(function(e){
			var codigoTitulo = botaoReceber.data('codigo');
			//altera o status para sucesso contatenando com o 'e' que é a mensagem lá do método receber no controle
			$('[data-role='+codigoTitulo+']').html('<span class="label label-success">'+e+'</span>');
			//esconde o botaoReceber
			botaoReceber.hide();
		});
		response.fail(function(e){
			console.log(e);
			alert('Erro Recebendo Cobrança');
		});
	});
});
