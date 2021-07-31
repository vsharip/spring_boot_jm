
// Пример стартового JavaScript для отключения отправки форм при наличии недопустимых полей
(function() {
    'use strict';
    window.addEventListener('load', function() {
        // Получите все формы, к которым мы хотим применить пользовательские стили проверки Bootstrap
        var forms = document.getElementsByClassName('formCreateUser');
        // Зацикливайтесь на них и предотвращайте подчинение
        var validation = Array.prototype.filter.call(forms, function(form) {
            form.addEventListener('submit', function(event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();

$(function () {
    $('#modalUpdate').modal('show');
});
$(function () {
    $('#modalDelete').modal('show');
});







