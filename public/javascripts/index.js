var semanaEmVisualizacao = 0;

$(document).on('ready', function () {
    atualizarMetas(semanaEmVisualizacao);

    $('#form-nova-meta').on('submit', function (e) {
        e.preventDefault();

        $.ajax({
            url: $(this).attr('action'),
            type: $(this).attr('method'),
            data: new FormData(this),
            mimeType: "multipart/form-data",
            contentType: false,
            cache: false,
            processData: false,
            success: function (result) {
                atualizarMetas(semanaEmVisualizacao);
                $('#form-nova-meta')[0].reset();
                $('#modal-nova-meta').modal('hide');
            }
        });
    });
});

function atualizarMetas(semanaSelecionada) {
	semanaEmVisualizacao = semanaSelecionada;

    $.ajax({
        url: '/metas/semana/' + semanaSelecionada,
        type: 'GET',
        success: function (result) {
            $('#lista-de-metas').empty();
            var data = JSON.parse(result);

            semanaSelecionada += 1;

            if (semanaSelecionada > 1){
            	$('.semana-meta').html('Para ' + semanaSelecionada + ' semanas');
                $('.dropdown-semana').html(semanaSelecionada + " Semanas");
            }else{
            	$('.semana-meta').html('Para ' + semanaSelecionada + ' semana');
                $('.dropdown-semana').html(semanaSelecionada + ' Semana');
            }
            
            if (data.length > 0) {

                var alcancada = 0;
                var naoAlcancada = 0;

                for (var i = 0; i < data.length; i++) {
                    var metaTemplate = $('#meta-template').clone();
                    var metaColor;
                    var botaoStatus;

                    if (data[i].alcancada) {
                        metaColor = "green";
                        botaoStatus = $($('#btn-alcancada-template').html());
                        botaoStatus.attr('data-id', data[i].id).attr('data-alcancada', "1");
                        alcancada++;
                    } else {
                        metaColor = 'yellow';
                        botaoStatus = $($('#btn-nao-alcancada-template').html());
                        botaoStatus.attr('data-id', data[i].id).attr('data-alcancada', "0");
                        naoAlcancada++;
                    }

                    metaTemplate.find('.meta-descricao').html(data[i].descricao);
                    metaTemplate.find('li').attr('data-id', data[i].id).addClass(metaColor);
                    metaTemplate.find('.texto-prioridade').html('Prioridade: ' + data[i].prioridade)

                    metaTemplate.find('.btn-status').html(botaoStatus);
                    $('#lista-de-metas').append(metaTemplate.html());
                }

                addEvents();

                $(".popconfirm").popConfirm({
                    title: 'Confirmação',
                    content: 'Você deseja desistir dessa meta?',
                    yesBtn: 'Sim',
                    noBtn: 'Não'
                });

                var element = $('#metas-da-semana');
                element.attr('data-naoAlcancada', naoAlcancada).attr('data-alcancada', alcancada);
                atualizarStatus(element);
            } else {
            	$('#metas-da-semana .status').empty();
                $('#lista-de-metas').append($('#nenhuma-meta-template').clone().html());
            }
        }
    });
}

function atualizarStatus(element) {
    var naoAlcancadas = Number(element.attr('data-naoAlcancada'));
    var alcancadas = Number(element.attr('data-alcancada'));

    element.find('.status').html('Total de metas: ' + String(naoAlcancadas + alcancadas) + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Metas para alcançar: ' + naoAlcancadas);
}

function atualizarData(element, alcancou) {
    var incremento = (alcancou ? -1 : 1)

    var naoAlcancadas = Number(element.attr('data-naoAlcancada')) + incremento;
    var alcancadas = Number(element.attr('data-alcancada')) - incremento;

    element.attr('data-naoAlcancada', naoAlcancadas).attr('data-alcancada', alcancadas);
}

function addEvents() {
    $('.alcancou').click(
        function () {
            var element = $(this);
            var id = element.data('id');
            var alcancada = (element.attr('data-alcancada') == "0" ? 1 : 0);

            $.ajax({
                url: '/meta/' + id + '/' + alcancada,
                type: 'PUT',
                success: function (result) {
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
        function () {
            var element = $(this.parent().parent());
            var ulElement = $(this.parent().parent().parent());
            var id = element.data('id');

            $.ajax({
                url: '/meta/' + id,
                type: 'DELETE',
                success: function (result) {
                    element.fadeOut(300, function () {

                        var divElement = element.parent().parent();

                        if (element.hasClass('yellow')) {
                            var naoAlcancadas = Number(divElement.attr('data-naoAlcancada')) - 1;
                            divElement.attr('data-naoAlcancada', naoAlcancadas)
                        } else {
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