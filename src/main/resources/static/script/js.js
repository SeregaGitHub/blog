'use strict';

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
        const form = document.getElementById('form' + id);

        form.addEventListener('keydown', (event) => {
                            if (event.ctrlKey && (event.keyCode == 13 || event.keyCode == 10)) {
                                form.submit();
                            }
                        });


        /*document.getElementById(id).style.display = 'none';
        let updateComment = document.getElementById('updateComment' + id);

        updateComment.style.display = 'block';
        console.log(updateComment);

        updateComment.addEventListener('keydown', (event) =>{
            if (event.ctrlKey && (event.keyCode == 13 || event.keyCode == 10)) {
                console.log("Work !!!");
            }
        });

        let display = document.querySelector('#postComment' + id);
        console.log(display.value);



        let form = document.getElementById('form' + id);
        console.log(form);

        display.addEventListener('keydown', (event) =>{
                    if (event.ctrlKey && (event.keyCode == 13 || event.keyCode == 10)) {
                        console.log("display - work !!!");
                        form.submit();
                        console.log("form - work !!!");
                    }
                });*/
}
