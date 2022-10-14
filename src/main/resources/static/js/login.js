
$(document).on('submit', '#loginForm', function(){

    const login_form = $(this);
    const form_data = JSON.stringify(login_form.serializeObject());

$.ajax({
    url: "/api/auth/token",
    type: "POST",
    contentType: 'application/json',
    data: form_data,
    success: function (result) {

        //Get token in response body.
        $.cookie("jwt_token", null, { path: '/' });
        $.cookie("jwt_token", result.token, { path: '/' });

        window.location.replace("/main");
    },
    error: function () {
        if($("#loginForm div").length > 1){
            $("#loginForm div")[0].remove();
        }
        $("#loginForm h1")[0].insertAdjacentHTML('afterend',"<div><span style='color: red'><b>Username or password were incorrect!</b></span></div>");
    }
})
    return false;
});

$.fn.serializeObject = function(){

    const o = {};
    const a = this.serializeArray();
    $.each(a, function() {
        if (o[this.name] !== undefined) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};
