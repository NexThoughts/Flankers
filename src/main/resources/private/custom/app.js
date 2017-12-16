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
            {
                "data": "id",
                "render": function (data, type, row, meta) {
                    if (type === 'display') {
                        data = '<a href="javascript:void(0)" onclick="updateTodo()">' + data + '</a>';
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