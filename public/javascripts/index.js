$(document).on('ready', function(){
	addEvents();
	$(".popconfirm").popConfirm({
		title: 'Confirmação',
		content: 'Você realmente deseja excluir?',
		yesBtn: 'Sim',
		noBtn: 'Não'
	});
});


function addEvents(){
	$('.deletar').click(function(){
		var element = $(this.parent().parent().parent());
		var ulElement = $(this.parent().parent().parent().parent());
		var id = element.data('id');
		
		$.ajax({
		    url: '/meta/'+id,
		    type: 'DELETE',
		    success: function(result) {
		    	element.fadeOut(300, function(){
		    		element.remove();
		    		
		    		if (ulElement.html().trim() == ""){
		    			ulElement.html('<h3 style="color:white;font-family:\'Short Stack\', cursive">Nenhuma meta para está semana</h3>')
			    	}
		    	});
		    }
		});
	})
}