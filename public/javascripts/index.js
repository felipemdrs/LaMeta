var MAX_SEMANAS = 6;

$(document).on('ready', function() {
	addEvents();
	$(".popconfirm").popConfirm({
		title : 'Confirmação',
		content : 'Você deseja desistir dessa meta?',
		yesBtn : 'Sim',
		noBtn : 'Não'
	});
	
	var todosStatus = JSON.parse($("#container").attr("data-todosStatus"));
	
	for (var i = 0; i < MAX_SEMANAS; i++) {
		var element = $('#semana-'+i);
		element.attr('data-naoAlcancada', todosStatus[i]._1).attr('data-alcancada', todosStatus[i]._2);
		atualizarStatus(element);
	}
});

function atualizarStatus(element){
	var naoAlcancadas = Number(element.attr('data-naoAlcancada'));
	var alcancadas = Number(element.attr('data-alcancada'));
			
	element.find('.status').html('Total de metas: '+String(naoAlcancadas+alcancadas)+'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Metas para alcançar: '+naoAlcancadas);
}

function atualizarData(element, alcancou){
	var incremento = (alcancou ? -1 : 1)
	
	var naoAlcancadas = Number(element.attr('data-naoAlcancada'))+incremento;
	var alcancadas = Number(element.attr('data-alcancada'))-incremento;
	
	element.attr('data-naoAlcancada', naoAlcancadas).attr('data-alcancada', alcancadas);
}

function addEvents() {

	$('.alcancou').click(
			function() {
				var element = $(this);
				var id = element.data('id');
				var alcancada = (element.attr('data-alcancada') == "0" ? 1 : 0);

				$.ajax({
					url : '/meta/' + id + '/' + alcancada,
					type : 'PUT',
					success : function(result) {
						var divElement = element.parent().parent().parent().parent();
						
						if (alcancada == 0) {
							element.parent().parent().removeClass('green')
									.addClass('yellow');
							
							atualizarData(divElement, false);
							atualizarStatus(divElement);
							
							element.attr('data-alcancada', alcancada);
							element.html("Não alcançada");
							element.remove('active').removeClass('btn-success').addClass('btn-warning');
						} else {
							element.parent().parent().removeClass('yellow')
									.addClass('green');
							
							atualizarData(divElement, true);
							atualizarStatus(divElement);
							
							element.attr('data-alcancada', alcancada);
							element.html("Alcançada");
							element.addClass('active').removeClass('btn-warning').addClass('btn-success');
						}
					}
				});
			});

	$('.deletar').click(
		function() {
			var element = $(this.parent().parent().parent());
			var ulElement = $(this.parent().parent().parent()
					.parent());
			var id = element.data('id');

			$.ajax({
				url : '/meta/' + id,
				type : 'DELETE',
				success : function(result) {
					element.fadeOut(300,function() {
						
						var divElement = element.parent().parent();
						
						if (element.hasClass('yellow')){
							var naoAlcancadas = Number(divElement.attr('data-naoAlcancada')) - 1;
							divElement.attr('data-naoAlcancada', naoAlcancadas)
						}else{
							var alcancadas = Number(divElement.attr('data-alcancada')) - 1;
							divElement.attr('data-alcancada', alcancadas)
						}
						
						atualizarStatus(divElement);
						
						element.remove();
						
						if (ulElement.html().trim() == "") {
							ulElement.html('<h3 style="color:white;font-family:\'Short Stack\', cursive;margin-left:40px">Nenhuma meta para está semana</h3>')
						}
					});
				}
			});
		})
	}