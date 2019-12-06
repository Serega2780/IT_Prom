$(document).ready(function () {

        $('#deptBody').on('click', '.item-edit', function () {
            var id = $(this).attr('data');
            $('input[name=id]').val("");
            $('input[name=name]').val("");
            $('input[name=description]').val("");
            $('#myModal').modal('show');
            $('#myModal').find('.modal-title').text('Edit department');
            document.getElementById('modalPost').style.visibility = "hidden";
            document.getElementById('modalPut').style.visibility = "visible";
            document.getElementById('parDep').style.display = '';
            document.getElementById('pro').style.display = 'none';
            document.getElementById('dep').style.display = 'none';

            $('#deptParList')
                .find('option')
                .remove()
                .end()
            ;

            $.ajax({
                type: 'ajax',
                method: 'GET',
                url: "/rest/dept/edit",
                data: {id: id},
                async: false,
                dataType: 'json',
                success: function (data) {
                    $('input[name=id]').val(data.id);
                    $('input[name=name]').val(data.name);
                    $('input[name=description]').val(data.description);
                    $('#deptParList').append('<option style="font-weight: bold;" selected="selected" value="' + (data.parentDept != null ? data.parentDept.name : "") +
                        '\">' + (data.parentDept != null ? data.parentDept.name : "") + '</option>');
                    $('#deptParList').append('<option value=""> --</option>');
                    $.ajax({
                        dataType: "json",
                        type: 'GET',
                        url: "/rest/dept/depts",

                    }).done(function (data) {
                        $.each(data, function (i, item) {
                            $('#deptParList').append('<option value="' + item.name + '\">' + item.name + '</option>');
                        })
                    });

                }
            });

        });


        $('#Departments').on('shown.bs.tab', function (e) {
            departmentsList();
        });


        function departmentsList() {
            $('#deptBody').empty();
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/depts",

            }).done(function (data) {
                $.each(data, function (i, item) {
                    var hrefEdit = "<a href=\"#\" class=\"btn btn-info item-edit\" data=\"" +
                        +item.id + "\" data-toggle=\"modal\" data-target=\"#myModal\" role=\"button\" >Edit</a>";

                    var hrefDelete = "<a href=\"#\" class=\"btn btn-info item-delete\" data=\"" +
                        +item.id + "\" role=\"button\">Delete</a>";

                    $('#deptBody').append("<tr>")
                        .append("<td>" + item.id + "</td>")
                        .append("<td>" + item.name + "</td>")
                        .append("<td>" + (item.parentDept != null ? item.parentDept.name : "") + "</td>")
                        .append("<td>" + item.description + "</td>")
                        .append("<td>" + hrefEdit + "</td>")
                        .append("<td>" + hrefDelete + "</td>")
                        .append("</tr>");
                })
            });
        }


        $('#depAdd').click(function () {
            $('input[name=id]').val("");
            $('input[name=name]').val("");
            $('input[name=description]').val("");
            $('#myModal').find('.modal-title').text('Add department');
            document.getElementById('modalPut').style.visibility = "hidden";
            document.getElementById('modalPost').style.visibility = "visible";
            document.getElementById('parDep').style.display = '';
            document.getElementById('pro').style.display = 'none';
            document.getElementById('dep').style.display = 'none';

            $('#deptParList')
                .find('option')
                .remove()
                .end()
            ;
            $('#deptParList').append('<option value=""> --</option>');
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/depts",

            }).done(function (data) {
                $.each(data, function (i, item) {
                    $('#deptParList').append('<option value="' + item.name + '\">' + item.name + '</option>');
                })
            });

        })

        $('#modalPost').click(function () {
            var id = $('input[name=id]').val();
            var name = $('input[name=name]').val();
            var description = $('input[name=description]').val();
            var parentDept = $('#deptParList option:selected').val();
            var ent;

            if (parentDept.length == 0) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "parentDept": null,
                    "description": description
                };
                postPut("department", "POST", "/rest/dept/insert", ent);
                $('#myModal').modal('hide');
                departmentsList();

            } else {

                $.ajax({
                    dataType: "json",
                    type: 'GET',
                    url: "/rest/dept/dept" + '?' + $.param({"name": parentDept})

                }).done(function (data) {

                    //Preparing a JSON object
                    ent = {
                        "id": id,
                        "name": name,
                        "parentDept": data,
                        "description": description
                    };
                    postPut("department", "POST", "/rest/dept/insert", ent);
                    $('#myModal').modal('hide');
                    departmentsList();
                });
            }

        })

        $('#modalPut').click(function () {
            var id = $('input[name=id]').val();
            var name = $('input[name=name]').val();
            var description = $('input[name=description]').val();
            var parentDept = $('#deptParList option:selected').val();
            var ent;

            if (parentDept.length == 0) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "parentDept": null,
                    "description": description
                };
                postPut("department", "PUT", "/rest/dept/update", ent);
                $('#myModal').modal('hide');
                departmentsList();

            } else {
                $.ajax({
                    dataType: "json",
                    type: 'GET',
                    url: "/rest/dept/dept" + '?' + $.param({"name": parentDept})

                }).done(function (data) {

                    //Preparing a JSON object
                    ent = {
                        "id": id,
                        "name": name,
                        "parentDept": data,
                        "description": description
                    };

                    postPut("department", "PUT", "/rest/dept/update", ent);
                    $('#myModal').modal('hide');
                    departmentsList();
                })
            }
            ;
        })

        //press Delete button (open Modal window)
        $('#deptBody').on('click', '.item-delete', function () {
            var id = $(this).attr('data');
            console.error(id);
            $.ajax({
                type: 'ajax',
                method: 'DELETE',
                url: "/rest/dept/delete" + '?' + $.param({"id": id}),
                async: false
            });
            departmentsList();
            $('#myModal').modal('hide');
            //  location.reload();
        })
    }
);