
//============================================================== Adicionar likes ========================================================================
//Informando que quando clicamos em qualquer botão que tenha esse id, uma função acontece. O * que informa que pode ser qualquer promocao 
$(document).on("click", "button[id*='likes-btn-']", function () {
	//Atribuindo o valor do id a uma variavel para mostrar no console
    var id = $(this).attr("id").split("-")[2];

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        method: "POST",
        url: "/posts/like/" + id,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
		//lembrando que o response recebe uma resposta do servidor e pode ser usado para qualquer coisa, nesse caso é para likes
        success: function(response){
            $("#likes-count-" + id).text(response);
        },
        error: function(xhr){
            alert("Erro: " + xhr.status);
        }
    });
});