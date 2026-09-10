document.addEventListener("DOMContentLoaded", function () {

    // ==============================
    // BOTÕES "COMENTAR"
    // ==============================

    const comentarioBtns = document.querySelectorAll("#comentario");
    const comentarioCards = document.querySelectorAll("#comentario-card");

    comentarioBtns.forEach(function (btn, index) {

        btn.addEventListener("click", function (event) {

            event.preventDefault();

            const card = comentarioCards[index];

            if (card) {
                card.classList.toggle("d-none");
            }

        });

    });


    // ==============================
    // BOTÕES "CANCELAR"
    // ==============================

    const cancelarBtns = document.querySelectorAll("#cancelar");

    cancelarBtns.forEach(function (btn, index) {

        btn.addEventListener("click", function () {

            const card = comentarioCards[index];

            if (card) {
                card.classList.add("d-none");
            }

        });

    });


    // ==============================
    // PREVIEW DA IMAGEM
    // ==============================

    const imagemInputs = document.querySelectorAll("#imgComentario");
    const previews = document.querySelectorAll("#comentarioImgPreview");

    imagemInputs.forEach(function (input, index) {

        input.addEventListener("change", function () {

            const file = this.files[0];

            const preview = previews[index];

            if (!preview) {
                return;
            }

            const img = preview.querySelector("img");

            if (!file) {

                preview.classList.add("d-none");

                if (img) {
                    img.src = "";
                }

                return;
            }

            const reader = new FileReader();

            reader.onload = function (event) {

                if (img) {
                    img.src = event.target.result;
                }

                preview.classList.remove("d-none");

            };

            reader.readAsDataURL(file);

        });

    });


});