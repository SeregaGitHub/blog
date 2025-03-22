function toggleForm() {
        const form = document.getElementById('postForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

function togglePostUpdateForm() {
        const form = document.getElementById('postUpdateForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

function toggleCommentForm() {
        const form = document.getElementById('commentForm');
        form.style.display = form.style.display === 'none' ? 'block' : 'none';
    }

function updateComment(id) {
        document.getElementById(id).style.display = 'none';
        document.getElementById('updateComment' + id).style.display = 'block';
}

let from = 0;
let size = 10;
//let currentPage = 0;

//function setRowCount() {
//
//        this.to =
//    }

setRowCount10 = () => {
    size = document.querySelector(".pag-value-10").value;
    from = 0;
//    currentPage = 0;
    console.log(document.querySelector(".pag-value-10").value);
    console.log(size);

    const xhr = new XMLHttpRequest(); // создаем объект запроса
    xhr.open('GET', 'http://localhost:8080/blog/feed?from=' + from + '&size=' + size); // настраиваем запрос (метод и URL)
    xhr.send(); // отправляем запрос
}

setRowCount20 = () => {
    size = document.querySelector(".pag-value-20").value;
    from = 0;
//    currentPage = 0;
    console.log(document.querySelector(".pag-value-20").value);
    console.log(size);

    const xhr = new XMLHttpRequest(); // создаем объект запроса
    xhr.open('GET', 'http://localhost:8080/blog/feed?from=' + from + '&size=' + size); // настраиваем запрос (метод и URL)
    xhr.send(); // отправляем запрос
}

setRowCount50 = () => {
    size = document.querySelector(".pag-value-50").value;
    from = 0;
//    currentPage = 0;
    console.log(document.querySelector(".pag-value-50").value);
    console.log(size);

    const xhr = new XMLHttpRequest(); // создаем объект запроса
    xhr.open('GET', 'http://localhost:8080/blog/feed?from=' + from + '&size=' + size); // настраиваем запрос (метод и URL)
    xhr.send(); // отправляем запрос
}
//next = () => {
//
//}