// function loadContent(url) {
//     $.ajax({
//         type: "GET",
//         url: url,
//         success: function(data) {
//             $("#content").html(data);
//         },
//         error: function(error) {
//             alert("Error loading content: " + error);
//         }
//     });
// }

function reindexTable() {
    $('#usersTable tbody tr').each(function (index){
        $(this).find('td:first').text(index + 1);
    })
}

function deleteRow(row) {
    console.log("Deleting row");
    $(row).closest('tr').remove();
    reindexTable();
}

$(document).ready(function() {
    $('.delete-link').on('click', function(event) {
        event.preventDefault();
        if (confirm('Are you sure you want to delete this user?')) {
            var row = $(this).closest('tr');
            var userId = row.find('td:first').text();
            $.ajax({
                url: '/admin/users/delete/' + userId,
                type: 'GET',
                success: function(result) {
                    deleteRow(row);
                },
                error: function(err) {
                    alert('Error deleting user.');
                }
            });
        }
    });
});

function hideAllSections() {
    $('#theTasks').hide().html('');
    $('#theUsers').hide().html('');
    $('#theHome').hide().html('');
    $('#theDep').hide().html('');
}

function loadingHome(url) {
    $('#loaderUsers').show();
    hideAllSections();

    $.ajax({
        type: "GET",
        url: url,

        success: function (data) {
            $('#theHome').show().html(data);
        },
        error: function (err){
            alert("Error loading content" + err);
        },
        complete: function () {
            $('#loaderUsers').hide();
        }
    });

    return false;
}

function loadingUsers(url) {
    $('#loaderUsers').show();
    hideAllSections();

    $.ajax({
        type: "GET",
        url: url,

        success: function (data) {
            $('#theUsers').show().html(data);
        },
        error: function (err){
            alert("Error loading content" + err);
        },
        complete: function () {
            $('#loaderUsers').hide();
        }
    });

    return false;
}

function loadingDep(url) {
    $('#loaderUsers').show();
    hideAllSections();

    $.ajax({
        type: "GET",
        url: url,

        success: function (data) {
            $('#theDep').show().html(data);
        },
        error: function (err){
            alert("Error loading content" + err);
        },
        complete: function () {
            $('#loaderUsers').hide();
        }
    });

    return false;
}

function loadingTasks(url) {
    $('#loaderUsers').show();
    hideAllSections();

    $.ajax({
        type: "GET",
        url: url,

        success: function (data) {
            $('#theTasks').show().html(data);
        },
        error: function (err){
            alert("Error loading content" + err);
        },
        complete: function () {
            $('#loaderUsers').hide();
        }
    });

    return false;
}