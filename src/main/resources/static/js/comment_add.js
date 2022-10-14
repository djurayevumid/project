$( "#comDiv" ).on( "click", "a", function( event ) {
    event.preventDefault();
    $(event.target).parent().hide();
    $(event.target).parent().parent().find("div").show();
});

$( "#questDiv" ).on('click',"button",function( event ) {
    let text = $(event.target).siblings("input").val();
    // let message = "<h5>"+text+"</h5>";
    let id = window.location.pathname.substring(10);
    if(text &&  text.trim().length > 0){
        $.ajax({
            url: "/api/user/question/" + id + "/comment",
            type: "POST",
            contentType:'application/text',
            data:text,
            async: false,
            beforeSend: function (request) {
                if (token != null) {
                    request.setRequestHeader("Authorization", "Bearer " + token);
                }
            },
            success: function () {
                window.location.reload();
            }
        })
    }
    else {
        return false;
    }
});

$( "#answersContainer" ).on('click',"button",function( event ) {
    let text = $(event.target).siblings("input").val();
    let questionId = window.location.pathname.substring(10);
    let answerId = $(event.target).attr("id").substring(16);
    if(text &&  text.trim().length > 0){
        $.ajax({
            url: "/api/user/question/" + questionId + "/answer/" + answerId + "/comment",
            type: "POST",
            contentType:'application/text',
            data:text,
            async: false,
            beforeSend: function (request) {
                if (token != null) {
                    request.setRequestHeader("Authorization", "Bearer " + token);
                }
            },
            success: function () {
                window.location.reload();
            }
        })
    }
    else {
        return false;
    }
});