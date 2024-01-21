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





// $().ready(function() {
//     $("#registerForm").validate({
//         rules:{
//             name:{
//                 required: true,
//                 minlength: 10,
//                 maxlength: 15,
//             },
//         },
//         highlight: function (element) {
//             $(element).closest('.form-group').addClass('has-error');
//         },
//         messages:{
//             email:{
//                 required: "This field is required",
//                 minlength: "Name must be at least 2 characters",
//                 maxlength: "Maximum number of characters - 10",
//             },
//         },
//         submitHandler: function(form) {
//             $.ajax({
//                 url: 'http://localhost:8080/process_login',
//                 type: 'POST',
//                 data: new FormData(this),
//                 processData: false,
//                 contentType: false,
//
//                 success: function(data) {
//                     $("#exampleModal").html(data);
//                 }
//             });
//             return false; // required to block normal submit since you used ajax
//         }
//     });
// });