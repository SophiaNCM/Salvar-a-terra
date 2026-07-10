const commentButton = document.getElementById('comment');
const commentCard = document.getElementById('commentCard');
const cancelComment = document.getElementById('cancelComment');
const sendComment = document.getElementById('sendComment');
const commentText = document.getElementById('commentText');
const readMoreLink = document.getElementById('readMore');
const postExcerpt = document.querySelector('.post-excerpt');
const postFull = document.querySelector('.post-full');

commentButton.addEventListener('click', (event) => {
    event.preventDefault();
    commentCard.classList.toggle('d-none');
    commentText.focus();
});

cancelComment.addEventListener('click', () => {
    commentCard.classList.add('d-none');
    commentText.value = '';
});

sendComment.addEventListener('click', () => {
    if (!commentText.value.trim()) {
      commentText.focus();
      return;
  }
 alert('Comentário enviado: ' + commentText.value.trim());
 commentText.value = '';
 commentCard.classList.add('d-none');
});

