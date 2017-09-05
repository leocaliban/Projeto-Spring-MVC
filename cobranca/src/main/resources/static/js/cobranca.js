//sempre que certa ação ocorrer executar uma função
$('#confirmarExcluir').on('show.bs.modal',function(event){
	
	//guarda o valor de quem disparar o evento
	var button = $(event.relatedTarget);
	//pega o valor data no botão acionado que é o código do título
	var codigoTitulo = button.data('codigo');
	var descricaoTitulo = button.data('descricao');
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
	modal.find('.modal-body span').html('Tem Certeza Que Deseja Exluir O Título <strong>'+ descricaoTitulo +'</strong>?');
});
