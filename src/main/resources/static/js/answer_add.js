$('#submit-button').on('click',function() {
    let text = document.getElementById('description').value;
    let message = "<h5>"+text+"</h5>";
    let id = window.location.pathname.substring(10);
    if(text &&  text.trim().length > 0){
        $.ajax({
            url: "/api/user/question/" + id + "/answer/add",
            type: "POST",
            contentType:'application/text',
            data:message,
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

$(document).ready(function (){
    let container = document.getElementById('tagsContainer');
    $.ajax({
        url:"/api/user"+window.location.pathname,
        method:"GET",
        async: false,
        contentType: "application/json",
        beforeSend: function (request) {
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success:function (response){
            let tagsList = response['listTagDto'];
            let tags ='';
            tagsList.forEach((tag)=>{
                tags+=`<a href="/tags"><button class="btn btn-primary btn-sm fs-sm">` + tag['name'] + `</button></a> &nbsp`;
            });
            container.innerHTML = tags;
        }

    })
});
