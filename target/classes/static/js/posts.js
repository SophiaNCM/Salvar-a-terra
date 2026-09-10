document.addEventListener("DOMContentLoaded", function () {

    // ==========================================
    // BOTÃO "COMENTAR"
    // ==========================================

    const comentarioBotoes =
        document.querySelectorAll(".comentario-btn");

    comentarioBotoes.forEach(function (botao) {

        botao.addEventListener("click", function () {

            const postId = this.getAttribute("data-post-id");

            const comentarioCard =
                document.getElementById("comentario-card-" + postId);

            if (comentarioCard) {
                comentarioCard.classList.toggle("d-none");
            }

        });

    });


    // ==========================================
    // BOTÃO "CANCELAR"
    // ==========================================

    const cancelarBotoes =
        document.querySelectorAll(".cancelar-comentario");

    cancelarBotoes.forEach(function (botao) {

        botao.addEventListener("click", function () {

            const postId = this.getAttribute("data-post-id");

            const comentarioCard =
                document.getElementById("comentario-card-" + postId);

            if (comentarioCard) {
                comentarioCard.classList.add("d-none");
            }

        });

    });


    // ==========================================
    // PREVISUALIZAÇÃO DA IMAGEM DO COMENTÁRIO
    // ==========================================

    const formularios =
        document.querySelectorAll(".comentario-form");

    formularios.forEach(function (form) {

        const imagemInput =
            form.querySelector(".img-comentario");

        const previewDiv =
            form.querySelector(".comentario-img-preview");

        const previewImg =
            form.querySelector(".comentario-img-preview img");


        if (!imagemInput || !previewDiv || !previewImg) {
            return;
        }


        imagemInput.addEventListener("change", function () {

            const file = this.files[0];


            // Nenhuma imagem selecionada
            if (!file) {

                previewDiv.classList.add("d-none");
                previewImg.src = "";

                return;
            }


            // Lê a imagem
            const reader = new FileReader();


            reader.onload = function (event) {

                previewImg.src = event.target.result;

                previewDiv.classList.remove("d-none");

            };


            reader.readAsDataURL(file);

        });

    });

});