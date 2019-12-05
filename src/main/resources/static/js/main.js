$(document).ready(function () {
    userList();

    //press Delete button (open Modal window)
    $('#userBody').on('click', '.item-delete', function () {
        var id = $(this).attr('data');
        console.error(id);
        $.ajax({
            type: 'ajax',
            method: 'DELETE',
            url: "/admin/delete" + '?' + $.param({"id": id}),
            async: false,

        });
        location.reload();
    })

    //press Edit button (open Modal window)
    $('#userBody').on('click', '.item-edit', function () {
        var id = $(this).attr('data');
        $('#myModal').modal('show');
        $('#myModal').find('.modal-title').text('Edit user');
        $.ajax({
            type: 'ajax',
            method: 'GET',
            url: "/admin/edit",
            data: {id: id},
            async: false,
            dataType: 'json',
            success: function (data) {
                $('input[name=id]').val(data.id);
                $('input[name=name]').val(data.name);
                $('input[name=password]').val(data.password);
                $('input[name=email]').val(data.email);
                $('#countryList').append('<option style="font-weight: bold;" selected="selected" value="' + data.country + '\">' + data.country + '</option>');
                data.grantedAuthorities.forEach(function (item) {
                    $('#roles').append('<option style="font-weight: bold;" selected="selected" value="' + item.authority + '\">' + item.authority + '</option>');

                });
                $('#roles').append('<option value=""> --</option>');
            },
            error: function () {

            }
        });

        getCountries($('#countryList'));

        getRoles($('#roles'));


    });

    function getRoleByName(authorities, rls) {
        var i;
        for (i = 0; i < rls.length; i++) {
            $.ajax({
                method: 'GET',
                url: "/admin/role",
                async: false,
                data: {role: rls[i]},
                dataType: 'json',
            }).done(function (role) {
                authorities.push(role);
            }).fail(function (jqXHR, textStatus, errorThrown) {

            });

        }
    }

    //Get countries
    function getCountries(select) {
        $.ajax({
            type: 'ajax',
            method: 'GET',
            url: "/admin/countries",
            async: false,
            dataType: 'json',
            success: function (countries) {
                countries.forEach(function (item) {
                    select.append('<option value="' + item + '\">' + item + '</option>');

                });

            },
            error: function () {

            }
        });
    }

    //Get roles
    function getRoles(rolesList) {
        $.ajax({
            method: 'GET',
            url: "/admin/roles",
            async: false,
            dataType: 'json',
        }).done(function (roles) {

            $.each(roles, function (i, role) {
                rolesList.append('<option value="' + role.role + '\">' + role.role + '</option>');
            })


        }).fail(function (jqXHR, textStatus, errorThrown) {

        });
    }


//Save Edit User
    $('#modalSave').click(function () {


        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var password = $('input[name=password]').val();
        var email = $('input[name=email]').val();
        var country = $('#countryList option:selected').val();

        // Get roles from server by roleName

        var authorities = new Array();
        getRoleByName(authorities, $('#roles').val().toString().split(","));

        //Preparing a JSON object
        var userHead = {
            "id": id,
            "name": name,
            "password": password,
            "email": email,
            "country": country,
            "grantedAuthorities": authorities
        };
        var user = JSON.stringify(userHead);

        $.ajax({
            method: 'PUT',
            url: "/admin/insert",
            dataType: 'json',
            contentType: 'application/json',
            async: false,
            data: user,
        }).done(function (roles) {

        }).fail(function () {

        }).always(function () {
            $('#modalSave').modal('hide');
            location.reload();
        })
    })

    $('#mainList').on('shown.bs.tab', function (e) {
        location.reload();

    });
    //Create a new User
    $('#newUserAdd').on('shown.bs.tab', function (e) {
        getCountries($('#countryListForNewUser'));
        getRoles($('#rolesForNewUser'));

    });

    $('#userCreateButton').click(function () {
        var name = $('input[id=login]').val();
        var password = $('input[id=password]').val();
        var email = $('input[id=email]').val();
        var country = $('#countryListForNewUser option:selected').val();
        var authorities = new Array();
        getRoleByName(authorities, $('#rolesForNewUser').val().toString().split(","));
        var userHead = {
            "id": 0,
            "name": name,
            "password": password,
            "email": email,
            "country": country,
            "grantedAuthorities": authorities
        };
        var user = JSON.stringify(userHead);

        $.ajax({
            method: 'PUT',
            url: "/admin/insert",
            dataType: 'json',
            contentType: 'application/json',
            async: false,
            data: user,
        }).done(function () {

        }).fail(function () {

        }).always(function () {

        })

    })

//Clear countries/roles list
    $('#modalClose').click(function () {
        $('#countryList')
            .find('option')
            .remove()
            .end()
        ;

        $('#roles')
            .find('option')
            .remove()
            .end()
        ;

    })

    function userList() {
        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "/admin/users",

        }).done(function (data, textStatus, jqXHR) {
            $.each(data, function (i, item) {

                var roles = "";
                var hrefEdit = "<a href=\"#\" class=\"btn btn-info item-edit\" data=\"" +
                    +item.id + "\" data-toggle=\"modal\" data-target=\"#myModal0\" role=\"button\" >Edit</a>";

                var hrefDelete = "<a href=\"#\" class=\"btn btn-info item-delete\" data=\"" +
                    +item.id + "\" role=\"button\">Delete</a>";

                $.each(item.grantedAuthorities, function (k, v) {
                    roles += v.role + '\n';

                })
                $('#userBody').append("<tr>")
                    .append("<td>" + item.id + "</td>")
                    .append("<td>" + roles + "</td>")
                    .append("<td>" + item.name + "</td>")
                    .append("<td>" + item.password + "</td>")
                    .append("<td>" + item.email + "</td>")
                    .append("<td>" + item.country + "</td>")

                    .append("<td>" + hrefEdit + "</td>")
                    .append("<td>" + hrefDelete + "</td>")
                    .append("</tr>");

            })
        }).fail(function (jqXHR, textStatus, errorThrown) {

            console.error('Booh! Wrong credentials, try again!');
        });
    }


    $('#profBody').on('click', '.item-edit', function () {
        var id = $(this).attr('data');
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').modal('show');
        $('#myModal').find('.modal-title').text('Edit profession');
        document.getElementById('modalPost').style.visibility="hidden";
        document.getElementById('modalPut').style.visibility="visible";

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
            url: "/rest/profs",

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
        document.getElementById('modalPut').style.visibility="hidden";
        document.getElementById('modalPost').style.visibility="visible";

    })
    $('#empAdd').click(function () {
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').find('.modal-title').text('Add employee');
        document.getElementById('modalPut').style.visibility="hidden";
        document.getElementById('modalPost').style.visibility="visible";
    })
    $('#depAdd').click(function () {
        $('input[name=id]').val("");
        $('input[name=name]').val("");
        $('input[name=description]').val("");
        $('#myModal').find('.modal-title').text('Add department');
        document.getElementById('modalPut').style.visibility="hidden";
        document.getElementById('modalPost').style.visibility="visible";
    })


    $('#modalPost').click(function () {


        var id = $('input[name=id]').val();
        var name = $('input[name=name]').val();
        var description = $('input[name=description]').val();

        //Preparing a JSON object
        var headHead = {
            "id": id,
            "name": name,
            "description": description

        };

        var p="profession"
        if(document.querySelector('.modal-title').textContent.indexOf(p) !== -1) {

            var prof = JSON.stringify(headHead);

            $.ajax({
                method: 'POST',
                url: "/rest/prof/insert",
                dataType: 'json',
                contentType: 'application/json',
                async: false,
                data: prof,
            }).done(function (roles) {

            }).fail(function () {

            }).always(function () {
                $('#myModal').modal('hide');
                professionsList();
             //   location.reload();
            })
        }
    })


    //press Delete button (open Modal window)
    $('#profBody').on('click', '.item-delete', function () {
        var id = $(this).attr('data');
        console.error(id);
        $.ajax({
            type: 'ajax',
            method: 'DELETE',
            url: "/rest/prof/delete" + '?' + $.param({"id": id}),
            async: false,

        });
        professionsList();
        $('#myModal').modal('hide');
      //  location.reload();
    })


})