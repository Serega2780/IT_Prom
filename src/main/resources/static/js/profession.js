$(document).ready(function () {

    $('#profBody').on('click', '.item-edit', function () {
        var id = $(this).attr('data');
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').modal('show');
        $('#myModal').find('.modal-title').text('Edit profession');
        document.getElementById('modalPost').style.visibility = "hidden";
        document.getElementById('modalPut').style.visibility = "visible";
        document.getElementById('parDep').style.display = 'none';
        document.getElementById('pro').style.display = 'none';
        document.getElementById('dep').style.display = 'none';

        $.ajax({
            type: 'ajax',
            method: 'GET',
            url: "/rest/prof/edit",
            data: {id: id},
            async: false,
            dataType: 'json',
            success: function (data) {
                $('input[name=id]').val(data.id);
                $('input[name=name]').val(data.name);
                $('input[name=description]').val(data.description);
            },
            error: function () {
            }
        });

    });

    $('#Professions').on('shown.bs.tab', function (e) {
        //alert("Professions!!!");
        professionsList();
    });

    function professionsList() {
        $('#profBody').empty();

        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "/rest/prof/profs"

        }).done(function (data) {
            $.each(data, function (i, item) {

                var hrefEdit = "<a href=\"#\" class=\"btn btn-info item-edit\" data=\"" +
                    +item.id + "\" data-toggle=\"modal\" data-target=\"#myModal\" role=\"button\" >Edit</a>";

                var hrefDelete = "<a href=\"#\" class=\"btn btn-info item-delete\" data=\"" +
                    +item.id + "\" role=\"button\">Delete</a>";

                $('#profBody').append("<tr>")
                    .append("<td>" + item.id + "</td>")
                    .append("<td>" + item.name + "</td>")
                    .append("<td>" + item.description + "</td>")
                    .append("<td>" + hrefEdit + "</td>")
                    .append("<td>" + hrefDelete + "</td>")
                    .append("</tr>");
            })
        });
    }


    $('#profAdd').click(function () {
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').find('.modal-title').text('Add profession');
        document.getElementById('modalPut').style.visibility = "hidden";
        document.getElementById('modalPost').style.visibility = "visible";
        document.getElementById('parDep').style.display = 'none';
        document.getElementById('pro').style.display = 'none';
        document.getElementById('dep').style.display = 'none';
        
    })

    $('#modalPost').click(function () {

        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var description = $('input[name=description]').val();

        //Preparing a JSON object
        var ent = {
            "id": id,
            "name": name,
            "description": description
        };

        global.postPut("profession", "POST", "/rest/prof/insert", ent);
        $('#myModal').modal('hide');
        professionsList();
    })

    $('#modalPut').click(function () {

        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var description = $('input[name=description]').val();

        //Preparing a JSON object
        var ent = {
            "id": id,
            "name": name,
            "description": description
        };

        global.postPut("profession", "PUT", "/rest/prof/update", ent);
        $('#myModal').modal('hide');
        professionsList();
    })


    //press Delete button (open Modal window)
    $('#profBody').on('click', '.item-delete', function () {
        var id = $(this).attr('data');
        console.error(id);
        $.ajax({
            type: 'ajax',
            method: 'DELETE',
            url: "/rest/prof/delete" + '?' + $.param({"id": id}),
            async: false
        });
        professionsList();
        $('#myModal').modal('hide');
        //  location.reload();
    })


});