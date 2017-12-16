$(document).ready(function () {
    loadTodo();
    populateUserCount();
    populateTodoCount();
});

function populateUserCount() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8085/users/count"
    }).done(function (response) {
        $("#userCount").html(response);
    }).fail(function () {
        alert("Something is wrong")
    });
}

function populateTodoCount() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8085/todo/count"
    }).done(function (response) {
        $("#todoCount").html(response);
    }).fail(function () {
        alert("Something is wrong")
    });
}

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
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    if (type === 'display') {
                        data = '<a href="javascript:void(0)" onclick="updateTodo(' + data + ')">' + data + '</a>';
                    }
                    return data
                }
            },
            {"data": "title"},
            {"data": "createdBy"},
            {"data": "assignedTo"},
            {"data": "deadLineDate"}
        ]
    })
}

function updateTodo(todoId) {

}