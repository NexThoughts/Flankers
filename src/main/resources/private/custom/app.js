$(document).ready(function () {
    loadTodo();
    loadUsers();
    loadComments();
    populateTagCount();
    populateUserCount();
    populateTodoCount();
    populateCommentCount();
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

function populateCommentCount() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8085/comments/count"
    }).done(function (response) {
        $("#commentCount").html(response);
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

function populateTagCount() {
    $.ajax({
        type: 'GET',
        url: "http://localhost:8085/tags/count"
    }).done(function (response) {
        $("#tagCount").html(response);
    }).fail(function () {
        alert("Something is wrong")
    });
}

function loadTodo() {
    $("#todos").DataTable({
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

function loadUsers() {
    $("#users").DataTable({
        ajax: {
            url: "http://localhost:8085/users",
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

function loadComments() {
    $("#comments").DataTable({
        ajax: {
            url: "http://localhost:8085/comments",
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
            {"data": "message"},
            {"data": "createdBy"},
            {"data": "todoId"}
        ]
    })
}