// $('#exampleModal').on('show.bs.modal', function () {
//     $.get("/modals/modal1", function (data) {
//         $('#exampleModal1').find('.modal-body').html(data);
//     })
// });

$(document).ready(function (){
    $('.table .eBtn').on('click', function (event){

        $('.myForm #emailModal').modal();

    });
});

