$.post({
    url: '/api/user' + window.location.pathname + '/view',
    contentType: 'application/json',
    beforeSend: function (request) {
        let token = $.cookie("jwt_token");
        if (token != null) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        }
    },
    error: function (error) {
        if (error.status === 403) {
            window.location.replace("/login");
        }
    }
});