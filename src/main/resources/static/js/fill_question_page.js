let token = $.cookie("jwt_token");
$.ajax({
    url: "/api/user" + window.location.pathname,
    type: "GET",
    async: false,
    beforeSend: function (request) {
        if (token != null) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        }
    },
    success: function (result) {
        const persistDate = new Date(result.persistDateTime);
        const months = ['янв','фев','мар','апр','мая','июн','июл','авг','сен','окт','ноя','дек'];
        const questionComments = result.listCommentsDto;
        let resDes ="" ;
        if (result.description.includes("http://") || result.description.includes("https://")
            || result.description.includes("ftp://")){
            let words = result.description.split(" ");
            for (let i = 0; i<words.length; i++){
                if(words[i].includes("http://") || words[i].includes("https://") || words[i].includes("ftp://")){
                    if(words[i].charAt(words[i].length - 1)==="/"){
                    words[i] = words[i].substring(0,words[i].length-1);}
                    words[i] = words[i].replaceAll(">", "");
                    words[i] = words[i].replaceAll("<", "");
                    resDes = resDes + " <a href=" + words[i] + ">" + " " +  words[i] + " " + "</a> ";
                }else{
                    resDes = resDes + words[i];
                }
            }
        }
        $("#questionTitle").text(`${result.title}` );
        $("#question-created-date").replaceWith(`<span style="color: gray;">Вопрос задан</span> ${persistDate.getDate()} ${months[`${persistDate.getMonth()}`]} ${persistDate.getFullYear()%100}г. в ${persistDate.getHours()}:${persistDate.getMinutes()}`);
        $("#views-counter").replaceWith(`<span style="color: gray;">Просмотрен</span> ${result.viewCount} раз`);
        $("#questionDescription").html(`${resDes}`);
        $("#relativeTime").text(` ${persistDate.getDate()} ${months[`${persistDate.getMonth()}`]} ${persistDate.getFullYear()%100}г. в ${persistDate.getHours()}:${persistDate.getMinutes()}`);
        $("#userImageLink").attr("src",`${result.authorImage}`);
        $("#userName").text(`${result.authorName}`);
        $("#questionValuable").text(`${result.countValuable}`);
        questionComments.forEach( com => {
            const dateAdded = new Date(com.dateAdded);
            const formattedDateAdded = `${dateAdded.getDate()} ${months[dateAdded.getMonth()]} ${dateAdded.getFullYear()%100}г. в ${dateAdded.getHours()}:${dateAdded.getMinutes()}`;
            $("#questionCommentsContainer")[0].insertAdjacentHTML('beforeend',
                `<li class="list-group-item">${com.comment}
                    <span> - ${com.fullName}</span>
                    <span style="color: gray;"> ${formattedDateAdded}</span>
                   </li>`)
        });
        $.get(
            {
                url: `/api/user/question/${result.id}/answer`,
                contentType: 'application/json',
                beforeSend: function (request) {
                        request.setRequestHeader("Authorization", "Bearer " + token);
                },
                success: function (answersList) {
                    $("#countAnswers").text(`${answersList.length} ответов`);
                    answersList.forEach( answer => {
                            const dateAccept = new Date(answer.dateAccept);
                            const persistDate = new Date(answer.persistDate);
                            const comments = answer.comments;
                            $("#answersContainer")[0].insertAdjacentHTML('beforeend',
                                `<div class="col col-lg-1">
                                    <a id="answerLinkUp${answer.id}" onclick="answerUpVote(${answer.id})"
                                       title="Ответ полезен.">
                                        <svg aria-hidden="true" class="svg-icon iconArrowUpLg" width="36" height="36"
                                             viewBox="0 0 36 36">
                                            <path id="answerPathUp${answer.id}" d="M2 26h32L18 10 2 26Z"></path>
                                        </svg>
                                    </a>
                                    <h3 id="answerValuable${answer.id}" style="text-indent: 10px;">${answer.countValuable}</h3>
                                    <a id="answerLinkDown${answer.id}" onclick="answerDownVote(${answer.id})"
                                       title="Ответ не является полезным.">
                                        <svg aria-hidden="true" class="svg-icon iconArrowDownLg" width="36" height="36"
                                             viewBox="0 0 36 36">
                                            <path id="answerPathDown${answer.id}" d="M2 10h32L18 26 2 10Z"></path>
                                        </svg>
                                    </a>
                                </div>
                            <div class="col col-lg-11">
                                <div id="" class="answercell post-layout--right">
                                    <div class="s-prose js-post-body" itemprop="text">
                                       ${answer.body}
                                    </div>
                                </div>
                                <div class="more-info d-flex justify-content-between align-items-center"
                                     style="flex-basis: 75%;">
                                    <div class="share-edit-follow" style="align-self: baseline;">
                                        <a href="/*" title="Короткая постоянная ссылка на этот вопрос"><span
                                                style="color: gray;">Поделиться</span></a> <a href="/*"
                                                                                              title="Отредактируйте сообщение, чтобы улучшить его"><span
                                            style="color: gray;">Править</span></a> <a href="/*"
                                                                                       title="Нажмите отслеживать, чтобы получать уведомления"><span
                                            style="color: gray;">Отслеживать</span></a>
                                    </div>
                                    <div class="card text-gray bg-light border-0 mb-3">
                                        <div class="text font-weight-light"
                                             style="margin-right:5px; margin-left:5px;">
                                            <p>ответ дан<span class="relativetime"> ${persistDate.getDate()} ${months[`${persistDate.getMonth()}`]} ${persistDate.getFullYear() % 100}г. в ${persistDate.getHours()}:${persistDate.getMinutes()}</span></p>
                                        </div>
                                        <div class="d-flex">
                                            <div class="user-avatar" style="margin-left:5px; margin-bottom:3px;">
                                                <a href="/user">
                                                    <div class="gravatar-wrapper-32"><img
                                                            src="${answer.image}"
                                                            alt="" width="32" height="32" class="bar-sm"></div>
                                                </a>
                                            </div>
                                            <div class="user-name d-flex" style="margin-left:3px;">
                                                <a class="text-info" href="/user">${answer.nickName}</a><span
                                                    class="d-none">${answer.nickName}</span>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="">
                                    <ul id="answerCommentsContainer${answer.id}" class="list-group list-group-flush">
                                    </ul>
                                </div>
                                <div class="add comment" style="margin-top:10px;">
                                    <a href="/*"
                                       title="Используйте комментарии для запроса дополнительной информации или предложения улучшений. Избегайте публикации ответа на вопросы в комментариях"><span
                                            style="color: gray;">Добавить комментарий</span></a>
                                    <div id="" class="d-flex flex-row add-comment-section mt-4 mb-4" style="display: none !important;">
                                        <input type="text" class="form-control mr-3" placeholder="Add comment">
                                        <button id="commentAnswerBtn${answer.id}" class="btn btn-primary" type="button">Comment</button>
                                    </div>
                                </div>
                                <hr>
                                <br>
                            </div>`)
                        comments.forEach( com => {
                            const dateAdded = new Date(com.dateAdded);
                            const formattedDateAdded = `${dateAdded.getDate()} ${months[dateAdded.getMonth()]} ${dateAdded.getFullYear()%100}г. в ${dateAdded.getHours()}:${dateAdded.getMinutes()}`;
                            $(`#answerCommentsContainer${answer.id}`)[0].insertAdjacentHTML('beforeend',
                                `<li class="list-group-item">${com.comment}
                                    <span> - ${com.fullName}</span>
                                    <span style="color: gray;"> ${formattedDateAdded}</span>
                                   </li>`)
                        })
                        }
                    )

                },
                error: function (error) {
                    alert(error.error());
                }
            }
        )
    },
    error: function (error) {
        if (error.status === 403) {
            window.location.replace("/login");
        }
    }
})

function questionUpVote() {
$.post({
    url: `/api/user` + window.location.pathname +`/upVote`,
    contentType: 'application/json',
    beforeSend: function (request) {
        if (token != null) {
            request.setRequestHeader("Authorization", "Bearer " + token);
        }
    },
    success: function () {
        const questionVal = $("#questionValuable");
        let text = Number(questionVal.text());
        questionVal.text(text + 1);
    },
    error: function (error) {
        if(error.responseText.substring("Vote for this question already exists")) {
            $("#iconArrowUpLg").attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
            $("#iconArrowUpLg").parents().parents().attr("onclick", "");
            $("#iconArrowDownLg").attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
            $("#iconArrowDownLg").parents().parents().attr("onclick", "");
        }
    }
    })
}

function questionDownVote() {
    $.post({
        url: `/api/user` + window.location.pathname +`/downVote`,
        contentType: 'application/json',
        beforeSend: function (request) {
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function () {
            const questionVal = $(`#questionValuable`);
            let text = Number(questionVal.text());
            questionVal.text(text - 1);
        },
        error: function (error) {
            if(error.responseText.substring("Vote for this question already exists")) {
                $("#iconArrowUpLg").attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $("#iconArrowUpLg").parents().parents().attr("onclick", "");
                $("#iconArrowDownLg").attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $("#iconArrowDownLg").parents().parents().attr("onclick", "");
            }
        }
    })
}

function questionBookmark() {
    $.post({
        url: `/api/user` + window.location.pathname +`/bookmark`,
        contentType: 'application/json',
        beforeSend: function (request) {
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function () {
            $("#iconBookmark").attr("fill", "hsl(0,0%,50%)");
        },
        error: function (error) {
            if(error.responseText.substring("Этот вопрос уже находится в Закладках")) {
                $("#iconBookmark").attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)")
                    .parents().parents().attr("onclick", "");
            }
        }
    })
}

function answerUpVote(answerId) {
    $.post({
        url: `/api/user` + window.location.pathname +`/answer/${answerId}/upVote`,
        contentType: 'application/json',
        beforeSend: function (request) {
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function () {
            const answerVal = $(`#answerValuable${answerId}`);
            let text = Number(answerVal.text());
            answerVal.text(text + 1);
        },
        error: function (error) {
            if(error.responseText.substring("User is already voted")) {
                $(`#answerPathUp${answerId}`).attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $(`#answerLinkUp${answerId}`).attr("onclick", "");
                $(`#answerPathDown${answerId}`).attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $(`#answerLinkDown${answerId}`).attr("onclick", "");
            }
        }
    })
}

function answerDownVote(answerId) {
    $.post({
        url: `/api/user` + window.location.pathname +`/answer/${answerId}/downVote`,
        contentType: 'application/json',
        beforeSend: function (request) {
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function () {
            const answerVal = $(`#answerValuable${answerId}`);
            let text = Number(answerVal.text());
            answerVal.text(text - 1);
        },
        error: function (error) {
            if(error.responseText.substring("User is already voted")) {
                $(`#answerPathUp${answerId}`).attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $(`#answerLinkUp${answerId}`).attr("onclick", "");
                $(`#answerPathDown${answerId}`).attr("stroke", "hsl(0,0%,50%)").attr("fill", "hsl(0,0%,85%)");
                $(`#answerLinkDown${answerId}`).attr("onclick", "");
            }
        }
    })
}
