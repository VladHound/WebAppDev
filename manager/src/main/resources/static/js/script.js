'use strict'

const forms = document.querySelectorAll('form');

// const message = {
//     loading: 'Загрузка',
//     success: 'Спасибо! Загружено!',
//     failure: 'Что-то пошло не так...'
// };

// forms.forEach(item => {
//     postData(item);
// })
//
// function postData(form) {
//     form.addEventListener('submit', (e) => {
//         e.preventDefault();
//
//         // const statusMessage = document.createElement('div');
//         // statusMessage.classList.add('status');
//         // statusMessage.textContent = message.loading;
//         // form.append(statusMessage);
//
//         const request = new XMLHttpRequest();
//         request.open('POST', 'http://localhost:8080/postdata')
//
//         // request.setRequestHeader('Content-type', 'multipart/form-data');
//         const formData = new FormData(form);
//
//         request.send(formData);
//
//         request.addEventListener('load', ()=> {
//             if (request.status === 200) {
//                 console.log(request.response);
//                 // statusMessage.textContent = message.success;
//             } else {
//                 // statusMessage.textContent = message.failure;
//             }
//         })
//     })
// }

$('#registerForm')
    .submit(function (e) {
        $.ajax({
            url: 'http://localhost:8080/registration',
            type: 'POST',
            data: new FormData(this),
            processData: false,
            contentType: false

        });
        e.preventDefault();

        $('#registerForm').modal('hide');

        $.ajax({
            url: 'http://localhost:8080/session',
            type: 'POST',
            data: new FormData(this),
            processData: false,
            contentType: false

        });
    });

// $(document).ready(function () {
//     $("#form").on('submit', function (){
//         $.ajax({
//             url: 'http://localhost:8080/postdata',
//             type: 'post',
//             dataType: 'html',
//             data: $(this).serialize(),
//             success: function(data){
//                 $('#msg').html(data);
//             }
//         });
//     });
// });