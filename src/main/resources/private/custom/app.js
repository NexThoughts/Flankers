$(document).ready(function () {
    loadTodo();
});

function loadTodo() {
    console.log("///////////////////////");
    $("#dt-ajax-array").DataTable({
        ajax: {
            url: "http://localhost:8085/todo",
            dataSrc: '',
            method: 'GET',
            'contentType': 'application/json'
        },
        columns: [
            {"data": "id"},
            {"data": "title"},
            {"data": "createdBy"},
            {"data": "assignedTo"},
            {"data": "deadLineDate"}
        ]
    })
}