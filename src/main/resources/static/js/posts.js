const comentarioBtn = document.getElementById('comentario');
const comentarioCard = document.getElementById('comentario-card');
const cancelarBtn = document.getElementById('cancelar');
const enviarBtn = document.getElementById('enviar');
const conteudoTxt = document.getElementById('conteudo');
const ComentarioImgInput = document.getElementById('imgComentario');
const divImgPreview = document.getElementById('comentarioImgPreview');
const ImgPreview = document.querySelector('#divImgPreview img');

const resetCommentForm = () => {
     conteudoTxt.value = '';
     ComentarioImgInput.value = '';
     divImgPreview.classList.add('d-none');
     ImgPreview.src = '';
   };

   ComentarioImgInput.addEventListener('change', () => {
     const file = ComentarioImgInput.files[0];
     if (!file) {
       divImgPreview.classList.add('d-none');
       ImgPreview.src = '';
       return;
     }

     const reader = new FileReader();
     reader.onload = (event) => {
       ImgPreview.src = event.target.result;
       divImgPreview.classList.remove('d-none');
     };
     reader.readAsDataURL(file);
   });

   comentarioBtn.addEventListener('click', (event) => {
     event.preventDefault();
     comentarioCard.classList.toggle('d-none');
     resetCommentForm();
     conteudoTxt.focus();
   });

   cancelarBtn.addEventListener('click', () => {
     comentarioCard.classList.add('d-none');
     resetCommentForm();
   });

   enviarBtn.addEventListener('click', () => {
     const hasText = conteudoTxt.value.trim();
     const hasImage = ComentarioImgInput.files.length > 0;

     if (!hasText && !hasImage) {
       conteudoTxt.focus();
       return;
     }

     const imageName = hasImage ? ComentarioImgInput.files[0].name : '';
     const message = hasText ? `Comentário enviado: ${conteudoTxt.value.trim()}` : 'Comentário enviado com foto';
     alert(imageName ? `${message} (${imageName})` : message);
     resetCommentForm();
     comentarioCard.classList.add('d-none');
   });