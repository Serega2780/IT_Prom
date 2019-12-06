$(document).ready(function () {

    $('#Employees').on('shown.bs.tab', function (e) {
        employeesList();
    });

    function employeesList() {
        $('#empBody').empty();
        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "/rest/emp/emps",

        }).done(function (data) {
            $.each(data, function (i, item) {
                var hrefEdit = "<a href=\"#\" class=\"btn btn-info item-edit\" data=\"" +
                    +item.id + "\" data-toggle=\"modal\" data-target=\"#myModal\" role=\"button\" >Edit</a>";

                var hrefDelete = "<a href=\"#\" class=\"btn btn-info item-delete\" data=\"" +
                    +item.id + "\" role=\"button\">Delete</a>";

                $('#empBody').append("<tr>")
                    .append("<td>" + item.id + "</td>")
                    .append("<td>" + item.name + "</td>")
                    .append("<td>" + (item.profession != null ? item.profession.name : "") + "</td>")
                    .append("<td>" + (item.department != null ? item.department.name : "") + "</td>")
                    .append("<td>" + item.description + "</td>")
                    .append("<td>" + hrefEdit + "</td>")
                    .append("<td>" + hrefDelete + "</td>")
                    .append("</tr>");
            })
        });
    }

//Show Add modal form

    $('#empAdd').click(function () {
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').find('.modal-title').text('Add employee');
        document.getElementById('modalPut').style.visibility = "hidden";
        document.getElementById('modalPost').style.visibility = "visible";
        document.getElementById('parDep').style.display = 'none';
        document.getElementById('pro').style.display = '';
        document.getElementById('dep').style.display = '';

        $('#deptList')
            .find('option')
            .remove()
            .end()
        ;

        $('#profList')
            .find('option')
            .remove()
            .end()
        ;

        $('#deptList').append('<option value=""> --</option>');

        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "/rest/dept/depts",

        }).done(function (data) {
            $.each(data, function (i, item) {
                $('#deptList').append('<option value="' + item.name + '\">' + item.name + '</option>');
            })
        });

        $('#profList').append('<option value=""> --</option>');

        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "/rest/prof/profs",

        }).done(function (data) {
            $.each(data, function (i, item) {
                $('#profList').append('<option value="' + item.name + '\">' + item.name + '</option>');
            })
        });

    })

    //Post

    $('#modalPost').click(function () {
        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var description = $('input[name=description]').val();
        var department = $('#deptList option:selected').val();
        var profession = $('#profList option:selected').val();
        var ent;

        if (department.length == 0 && profession.length == 0) {
            ent = {
                "id": id,
                "name": name,
                "profession": null,
                "department": null,
                "description": description
            };
            postPut("employee", "POST", "/rest/emp/insert", ent);
            $('#myModal').modal('hide');
            employeesList();
        } else if (department.length != 0 && profession.length != 0) {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/dept" + '?' + $.param({"name": department})

            }).done(function (d) {
                $.ajax({
                    dataType: "json",
                    type: 'GET',
                    url: "/rest/prof/prof" + '?' + $.param({"name": profession})

                }).done(function (p) {
                    //Preparing a JSON object
                    ent = {
                        "id": id,
                        "name": name,
                        "profession": p,
                        "department": d,
                        "description": description
                    };
                    postPut("employee", "POST", "/rest/emp/insert", ent);
                    $('#myModal').modal('hide');
                    employeesList();
                })

            });
        } else if (department.length != 0 && profession.length == 0) {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/dept" + '?' + $.param({"name": department})

            }).done(function (d) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "profession": null,
                    "department": d,
                    "description": description
                };
                postPut("employee", "POST", "/rest/emp/insert", ent);
                $('#myModal').modal('hide');
                employeesList();
            })
        } else {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/prof/prof" + '?' + $.param({"name": profession})

            }).done(function (p) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "profession": p,
                    "department": null,
                    "description": description
                };
                postPut("employee", "POST", "/rest/emp/insert", ent);
                $('#myModal').modal('hide');
                employeesList();
            })
        }


    })


    //PUT

    $('#modalPut').click(function () {
        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var description = $('input[name=description]').val();
        var department = $('#deptList option:selected').val();
        var profession = $('#profList option:selected').val();
        var ent;

        if (department.length == 0 && profession.length == 0) {
            ent = {
                "id": id,
                "name": name,
                "profession": null,
                "department": null,
                "description": description
            };
            postPut("employee", "PUT", "/rest/emp/update", ent);
            $('#myModal').modal('hide');
            employeesList();
        } else if (department.length != 0 && profession.length != 0) {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/dept" + '?' + $.param({"name": department})

            }).done(function (d) {
                $.ajax({
                    dataType: "json",
                    type: 'GET',
                    url: "/rest/prof/prof" + '?' + $.param({"name": profession})

                }).done(function (p) {
                    //Preparing a JSON object
                    ent = {
                        "id": id,
                        "name": name,
                        "profession": p,
                        "department": d,
                        "description": description
                    };
                    postPut("employee", "PUT", "/rest/emp/update", ent);
                    $('#myModal').modal('hide');
                    employeesList();
                })

            });
        } else if (department.length != 0 && profession.length == 0) {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/dept" + '?' + $.param({"name": department})

            }).done(function (d) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "profession": null,
                    "department": d,
                    "description": description
                };
                postPut("employee", "PUT", "/rest/emp/update", ent);
                $('#myModal').modal('hide');
                employeesList();
            })
        } else {
            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/prof/prof" + '?' + $.param({"name": profession})

            }).done(function (p) {
                //Preparing a JSON object
                ent = {
                    "id": id,
                    "name": name,
                    "profession": p,
                    "department": null,
                    "description": description
                };
                postPut("employee", "PUT", "/rest/emp/update", ent);
                $('#myModal').modal('hide');
                employeesList();
            })
        }


    })


    //Delete

    //press Delete button (open Modal window)
    $('#empBody').on('click', '.item-delete', function () {
        var id = $(this).attr('data');
        console.error(id);
        $.ajax({
            type: 'ajax',
            method: 'DELETE',
            url: "/rest/emp/delete" + '?' + $.param({"id": id}),
            async: false
        });
        employeesList();
        $('#myModal').modal('hide');
        //  location.reload();
    })


    //Edit
    $('#empBody').on('click', '.item-edit', function () {
        var id = $(this).attr('data');
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').find('.modal-title').text('Edit employee');
        document.getElementById('modalPut').style.visibility = "visible";
        document.getElementById('modalPost').style.visibility = "hidden";
        document.getElementById('parDep').style.display = 'none';
        document.getElementById('pro').style.display = '';
        document.getElementById('dep').style.display = '';

        $('#deptList')
            .find('option')
            .remove()
            .end()
        ;

        $('#profList')
            .find('option')
            .remove()
            .end()
        ;

        $.ajax({
            type: 'ajax',
            method: 'GET',
            url: "/rest/emp/edit",
            data: {id: id},
            async: false,
            dataType: 'json'
        }).done(function (data) {
            $('input[name=id]').val(data.id);
            $('input[name=name]').val(data.name);
            $('input[name=description]').val(data.description);
            $('#deptList').append('<option style="font-weight: bold;" selected="selected" value="' + (data.department != null ? data.department.name : "") +
                '\">' + (data.department != null ? data.department.name : "") + '</option>');

            $('#deptList').append('<option value=""> --</option>');

            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/dept/depts",

            }).done(function (data) {
                $.each(data, function (i, item) {
                    $('#deptList').append('<option value="' + item.name + '\">' + item.name + '</option>');
                })
            });

        }).done(function (data) {
            $('input[name=id]').val(data.id);
            $('input[name=name]').val(data.name);
            $('input[name=description]').val(data.description);
            $('#profList').append('<option style="font-weight: bold;" selected="selected" value="' + (data.profession != null ? data.profession.name : "") +
                '\">' + (data.profession != null ? data.profession.name : "") + '</option>');

            $('#profList').append('<option value=""> --</option>');

            $.ajax({
                dataType: "json",
                type: 'GET',
                url: "/rest/prof/profs",

            }).done(function (data) {
                $.each(data, function (i, item) {
                    $('#profList').append('<option value="' + item.name + '\">' + item.name + '</option>');
                })
            });
        ;
    });

})


})//$(document).ready(function () {
