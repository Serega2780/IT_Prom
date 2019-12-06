
////////////////////////////////////////////////////////////////////

global = {

    postPut: function (entity, method, url, ent) {
        return postPut(entity, method, url, ent);
    }
};

function postPut(entity, method, url, ent) {

    if (document.querySelector('.modal-title').textContent.indexOf(entity) !== -1) {
        $.ajax({
            method: method,
            url: url,
            dataType: 'json',
            contentType: 'application/json',
            async: false,
            data: JSON.stringify(ent),
        }).done(function (roles) {

        })
    }
}


