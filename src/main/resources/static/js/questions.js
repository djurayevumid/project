let searchFieldIgnored = "#searchIgnored";
let searchFieldTracked = "#searchTracked";
let openIgnoredTagBtn = `#openIgnoredTagBtn`;
let openTrackedTagBtn = `#openTrackedTagBtn`;
let ignoredTagForm = `#ignoredTagForm`;
let trackedTagForm = `#trackedTagForm`;
let map = new Map;
let addIgnoredTagBtn = '#addIgnoredTagButton';
let addTrackedTagBtn = '#addTrackedTagButton';
let ignoredTagUrl = `/ignored`;
let trackedTagUrl = `/tracked`;
let ignoredTagAddedField = '#ignoredTagButtons';
let trackedTagAddedField = '#trackedTagButtons';
let ignoredTagErrorField = `#ignoredTagsError`;
let trackedTagErrorField = `#trackedTagsError`;
let lettersErrIgnored = "#lettersErrorIgnored";
let lettersErrTracked = "#lettersErrorTracked";

$(async function () {
    await openTagForm(openIgnoredTagBtn, ignoredTagForm);
    await openTagForm(openTrackedTagBtn, trackedTagForm);
    await searchTag(searchFieldIgnored, lettersErrIgnored);
    await searchTag(searchFieldTracked, lettersErrTracked);
    readySearch(searchFieldIgnored, ignoredTagForm, lettersErrIgnored);
    readySearch(searchFieldTracked, trackedTagForm, lettersErrTracked);
    await addTag(addIgnoredTagBtn, searchFieldIgnored, ignoredTagUrl, ignoredTagAddedField,
        ignoredTagErrorField);
    await addTag(addTrackedTagBtn, searchFieldTracked, trackedTagUrl, trackedTagAddedField,
        trackedTagErrorField);
    await getTags(ignoredTagAddedField, ignoredTagUrl);
    await getTags(trackedTagAddedField, trackedTagUrl);
})
async function searchTag(searchField, letterErr){
    new autoComplete({
        selector:  'input[name="' + searchField.substring(1, searchField.length)+ '"]',
        minChars: 1,
        source: function (term, suggest) {
            if (term !== undefined) {
                term = term.toLowerCase();
            }
            $.get({
                url: "/api/user/tag/letters?letters=" + term,
                contentType: 'application/json',
                beforeSend: function (request) {
                    let token = $.cookie("jwt_token");
                    if (token != null) {
                        request.setRequestHeader("Authorization", "Bearer " + token);
                    }
                },
                success: function (result) {
                    result.forEach(function (r){
                        map.set(r.name, r.id);
                    })
                    suggest(result);
                },
                error: function (error) {
                    $(".autocomplete-suggestions")[0].style.display = "none";
                    if (error.status === 400) {
                        $(letterErr)[0].insertAdjacentHTML('afterbegin',
                            "<span style='color: red'>" +
                            "<b>Некоторые введённые символы недопустимы.</b><br>" +
                            "</span>");
                    }
                    if (error.status === 403) {
                        window.location.replace("/login");
                    }
                }
            })
        },
        onSelect(event, term, item) {
            let tags = $(searchField);
            tags.val(term);
        },
        renderItem: function (item, search) {
            search = search.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&");
            const re = new RegExp("(" + search.split(" ").join("|") + ")", "gi");
            const descr = item.description !== null ? item.description : "";
            return '<div class="autocomplete-suggestion col-xs-6" data-val="' + item.name + '">' + '<span>' + item.name.replace(re, "<b>$1</b>") + '</span>' +
                "<br>" + '<div class="description">' + descr + '</div>' + "</div>"
        }
    });
}

function readySearch(searchField,searchForm, lettersErr){
    $(document).ready(function () {
        $(searchField).keydown(function () {
            let errorLettersField = $(lettersErr +" span");
            if (errorLettersField.length >= 1) {
                errorLettersField[0].remove();
            }
        });
        $(searchForm).keydown(function (event) {
            if (event.keyCode === 13 && $(".autocomplete-suggestions").is(":visible")) {
                event.preventDefault();
                return false;
            }
        });
    });
}

function addTagBtn(tagName, tagSelector, tagId){
    let btn = `
                            <button type="button" class="btn btn-outline-dark btn-sm fs-sm" id="`+tagId+`" onClick = "getDel(this)">
                                 <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-x-circle-fill" viewBox="0 0 16 16">
                                <path d="M16 8A8 8 0 1 1 0 8a8 8 0 0 1 16 0zM5.354 4.646a.5.5 0 1 0-.708.708L7.293 8l-2.647 2.646a.5.5 0 0 0 .708.708L8 8.707l2.646 2.647a.5.5 0 0 0 .708-.708L8.707 8l2.647-2.646a.5.5 0 0 0-.708-.708L8 7.293 5.354 4.646z"/>
                            </svg>
                                <i class="bi bi-x-circle-fill"></i>` + tagName + `
                            </button>
    `;

    $(tagSelector).append(btn);
}

async function addTag(addBtn, searchField, tagUrl, addedTagField, errorField) {
    document.querySelector(addBtn).addEventListener('click', () => {
        let tagName = document.querySelector(searchField).value.trim();
        let id;
        if (map.size > 0){
            id = map.get(tagName);
        } else {
            id = undefined;
        }
        if(id!== undefined) {
            $.post({
                url: `/api/user/tag/` + id + tagUrl,
                contentType: 'application/json',
                beforeSend: function (request) {
                    let token = $.cookie("jwt_token");
                    if (token != null) {
                        request.setRequestHeader("Authorization", "Bearer " + token);
                    }
                },
                success: function (result) {
                    if (tagUrl==trackedTagUrl){
                        let img = $('#trackedImage');
                        img.remove();
                    }
                    getTags(addedTagField, tagUrl);
                    refreshPage();
                },
                error: function (error) {
                    if (error.status === 403) {
                        window.location.replace("/login");
                    }
                    if (error.status === 400) {
                        let div = `<div class='poof' ><span style='color: red'>` +
                            `<b>`+error.responseText+`</b><br>`  +
                            `</span></div>`;
                        $(errorField).append(div);
                        setTimeout(function () {
                            $('.poof')[0].remove();
                        }, 3000)
                    }
                    console.log(error);
                }
            });
        } else {
            let div = `<div class='poof' ><span style='color: red'>` +
                `<b>Такого тега не существует</b><br>`  +
                `</span></div>`;
            $(errorField).append(div);
            setTimeout(function () {
                $('.poof')[0].remove();
            }, 2000)
        }
    });
}

function refreshPage() {
    switch (sortedBy) {
        case "new":
            document.getElementById("newestButton").click();
            break;
        case "noAnswer":
            document.getElementById("unansweredButton").click();
            break;
        case "allTime":
            document.getElementById("reputationButton").click();
            break;
    }
}

function getDel(obj){
    let id = obj.id
    let addedTagField = '#' + obj.parentNode.id;
    let tagUrl;
    if (addedTagField == ignoredTagAddedField ){
        tagUrl = ignoredTagUrl;
    } else {
        tagUrl= trackedTagUrl;
    }
    (async () => {
        await deleteTag(addedTagField, tagUrl, id);
    })();
}

async function deleteTag(addedTagField, tagUrl, tagId) {
    $.ajax({
        url: `/api/user/tag/` + tagId + tagUrl,
        type: "DELETE",
        contentType: 'application/json',
        beforeSend: function (request) {
            let token = $.cookie("jwt_token");
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function (result) {
            getTags(addedTagField, tagUrl);
            refreshPage();
        },
        error: function (error) {
            if (error.status === 403) {
                window.location.replace("/login");
            } else {
                let div = `<div class='poof' ><span style='color: red'>` +
                    `<b>`+error.responseText+`</b><br>`  +
                    `</span></div>`;
                $(addedTagField).append(div);
                setTimeout(function () {
                    $('.poof')[0].remove();
                }, 2000)
            }
        }
    })
}

async function openTagForm(openBtn, tagForm) {
    let button = $(openBtn);
    let form = $(tagForm);
    let img = $('#trackedImage');
    button.on('click', () => {
        if (form.attr("data-hidden") === "true") {
            form.attr('data-hidden', 'false');
            form.show();
            button.text('Hide');
            if (openBtn == openTrackedTagBtn){
                img.hide();
            }
        } else {
            form.attr('data-hidden', 'true');
            form.hide();
            button.text('Open');
            if (openBtn == openTrackedTagBtn){
                img.show();
            }
        }
    })
}

async function getTags(addedTagField, tagUrl) {
    $.get({
        url: `/api/user/tag` + tagUrl,
        beforeSend: function (request) {
            let token = $.cookie("jwt_token");
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function (result) {
            $(addedTagField)[0].innerHTML = "";
            result.forEach(tag =>{
                addTagBtn(tag.name, addedTagField, tag.id);
            })
        },
        error: function (error) {
            if (error.status === 403) {
                window.location.replace("/login");
            }
        }
    })
}

async function getTagsIgnored(addedTagField, tagUrl) {
    $.get({
        url: `/api/user/tag/ignored` + tagUrl,
        beforeSend: function (request) {
            let token = $.cookie("jwt_token");
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function (result) {
            $(addedTagField)[0].innerHTML = "";
            result.forTagsIgnored(tag =>{
                addTagBtn(tag.name, addedTagField, tag.id);
            })
        },
        error: function (error) {
            if (error.status === 403) {
                window.location.replace("/login/user/tag/ignored/");
            }
        }
    })
}

async function getTagsTracking(addedTagField, tagUrl) {
    $.get({
        url: `/api/user/tag/tracking` + tagUrl,
        beforeSend: function (request) {
            let token = $.cookie("jwt_token");
            if (token != null) {
                request.setRequestHeader("Authorization", "Bearer " + token);
            }
        },
        success: function (result) {
            $(addedTagField)[0].innerHTML = "";
            result.forTagsTracking(tag =>{
                addTagBtn(tag.name, addedTagField, tag.id);
            })
        },
        error: function (error) {
            if (error.status === 403) {
                window.location.replace("/login/user/tag/tracking/");
            }
        }
    })
}