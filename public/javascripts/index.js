$(document).on('ready', function() {
	addEvents();
	$(".popconfirm").popConfirm({
		title : 'Confirmação',
		content : 'Você deseja desistir dessa meta?',
		yesBtn : 'Sim',
		noBtn : 'Não'
	});
});

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
						if (alcancada == 0) {
							element.parent().parent().removeClass('green')
									.addClass('yellow');
							element.attr('data-alcancada', alcancada);
							element.html("Não alcançada");
							element.remove('active').removeClass('btn-success').addClass('btn-warning');
						} else {
							element.parent().parent().removeClass('yellow')
									.addClass('green');
							element.attr('data-alcancada', alcancada);
							element.html("Alcançada");
							element.addClass('active').removeClass('btn-warning').addClass('btn-success');
						}
						var a = 'ok';
					}
				});
			});

	$('.deletar')
			.click(
					function() {
						var element = $(this.parent().parent().parent());
						var ulElement = $(this.parent().parent().parent()
								.parent());
						var id = element.data('id');

						$
								.ajax({
									url : '/meta/' + id,
									type : 'DELETE',
									success : function(result) {
										element
												.fadeOut(
														300,
														function() {
															element.remove();

															if (ulElement
																	.html()
																	.trim() == "") {
																ulElement
																		.html('<h3 style="color:white;font-family:\'Short Stack\', cursive;margin-left:40px">Nenhuma meta para está semana</h3>')
															}
														});
									}
								});
					})
}