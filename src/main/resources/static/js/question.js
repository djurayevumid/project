new autoComplete({
    selector: 'input[name="tags"]',
    minChars: 1,
    source: function (term, suggest) {
        term = term.split(/\s*[ ,;]\s*/).filter(item => item !== "").pop();
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
                suggest(result);
            },
            error: function (error) {
                $(".autocomplete-suggestions")[0].style.display = "none";
                if (error.status === 400 && error.responseText.substring("The valid characters are defined in RFC 7230 and RFC 3986")) {
                    $("#lettersError")[0].insertAdjacentHTML('afterbegin',
                        "<span style='color: red'>" +
                        "<b>Некоторые введённые символы недопустимы. Пожалуйста, заполните поле согласно примеру</b><br>" +
                        "<span style='color: black'>android, java; kotlin RXJava</span>" +
                        "</span>");
                }
                if (error.status === 403) {
                    window.location.replace("/login");
                }
            }
        })
    },
    onSelect(event, term, item) {
        let tags = $("#tags");
        let modifiedText = tags.val().replace(/[ ,;]*[^ ,;]*$/ig, "");
        if (modifiedText === "") {
            tags.val(term);
        } else {
            tags.val(modifiedText + ", " + term);
        }
    },
    renderItem: function (item, search) {
        search = search.replace(/[-\/\\^$*+?.()|[\]{}]/g, "\\$&");
        const re = new RegExp("(" + search.split(" ").join("|") + ")", "gi");
        const descr = item.description !== null ? item.description : "";
        return '<div class="autocomplete-suggestion col-md-6" data-val="' + item.name + '">' + '<span>' + item.name.replace(re, "<b>$1</b>") + '</span>' +
            "<br>" + '<div class="description">' + descr + '</div>' + "</div>"
    }
});

$(document).on('submit', '#askQuestionForm', function () {

    const ask_form = $(this);
    const questionDtoArray = ask_form.serializeToQuestionCreateDto();
    const form_data = JSON.stringify(questionDtoArray);

    if (validate(questionDtoArray)) {
        $.ajax({
            url: "/api/user/question",
            type: "POST",
            contentType: 'application/json',
            data: form_data,
            beforeSend: function (request) {
                let token = $.cookie("jwt_token");
                if (token != null) {
                    request.setRequestHeader("Authorization", "Bearer " + token);
                }
            },
            success: function (result) {
                let id = result.id;
                let persistDateTime = result.persistDateTime;
                let listTagDto = result.listTagDto;
                window.location.replace("/question/" + id);
            },
            error: function (error) {
                if (error.status === 403) window.location.replace("/login");
                else alert(error.responseText);
            }
        })
    }
    return false;
});

function validate(formData) {
    let isValidationsFails = false;
    let errorTitleFields = $("#titleError span");
    if (errorTitleFields.length >= 1) {
        errorTitleFields[0].remove();
    }
    let errorDescriptionFields = $("#descriptionError span");
    if (errorDescriptionFields.length >= 1) {
        errorDescriptionFields[0].remove();
    }
    let errorTagFields = $("#tagsError span");
    if (errorTagFields.length >= 1) {
        errorTagFields[0].remove();
    }
    if (formData.title === "") {
        $("#titleError")[0].insertAdjacentHTML('afterbegin',
            "<span style='color: red'>" +
            "<b>Title cannot be empty!</b>" +
            "</span>");
        isValidationsFails = true;
    }
    if (formData.description === "") {
        $("#descriptionError")[0].insertAdjacentHTML('afterbegin',
            "<span style='color: red'>" +
            "<b>Description cannot be empty!</b>" +
            "</span>");
        isValidationsFails = true;
    }
    if (formData.tags.length === 0) {
        $("#tagsError")[0].insertAdjacentHTML('afterbegin',
            "<span style='color: red'>" +
            "<b>Tags cannot be empty!</b>" +
            "</span>");
        return false;
    }
    if (formData.tags.length > 5) {
        $("#tagsError")[0].insertAdjacentHTML('afterbegin',
            "<span style='color: red'>" +
            "<b>Tag's amount cannot be more than 5!</b>" +
            "</span>");
        return false;
    }
    let invalidCharacters = formData.tags[0].name.match(/[^\w ;',.!@#$%^&*()_+|\\/~`абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНУФХЦЧШЩЪЫЬЭЮЯ№":?=-]/g);
    if (invalidCharacters !== null) {
        $("#tagsError")[0].insertAdjacentHTML('afterbegin',
            "<span style='color: red'>" +
            '<b>Недопустимый символ: ' + invalidCharacters[0] +"</b>" +
            "</span>");
        return false;
    }
    return !isValidationsFails;
}

$.fn.serializeToQuestionCreateDto = function () {
    const questionCreateDto = {};
    const array = this.serializeArray();
    $.each(array, function () {
        if (questionCreateDto[this.name] !== undefined) {
            if (!questionCreateDto[this.name].push) {
                questionCreateDto[this.name] = [questionCreateDto[this.name]];
            }
            questionCreateDto[this.name].push(this.value || '');
        } else {
            if (this.name === "tags") {
                questionCreateDto[this.name] = this.value.split(/\s*[ ,;]\s*/).filter(item => item !== "").map((v) => ({"name": v}));
            } else {
                questionCreateDto[this.name] = this.value;
            }
        }
    });
    return questionCreateDto;
};

$(document).ready(function () {
    $("#tags").keydown(function (event) {
        let errorLettersField = $("#lettersError span");
        if (errorLettersField.length >= 1) {
            errorLettersField[0].remove();
        }
    });
    $("#askQuestionForm").keydown(function (event) {
        if (event.keyCode === 13 && $(".autocomplete-suggestions").is(":visible")) {
            event.preventDefault();
            return false;
        }
    });
});


let descriptionField = $("#description")[0];
let displayField = $("#displayField");
let patternBold = new RegExp(/[*]([^*]+)[*]/g);
let patternItalic = new RegExp(/[$]([^$]+)[$]/g);
let patternCode = new RegExp(/[']([^']+)[']/g);
let patternBlockQuote = new RegExp(/[~]([^~]+)[~]/g);
descriptionField.addEventListener("keyup", updateDisplayField, false);

function makeTransformation(sign) {
    if (descriptionField.selectionStart === descriptionField.selectionEnd) {
        let signText;
        let text;
        if (sign === "$") signText = "курсивом";
        if (sign === "*") signText = "жирным шрифтом";
        if (sign === "\'") signText = "кодом";
        signText = `${sign}текст, выделенный ${signText}${sign}`;
        if (sign === "~") signText = `${sign}Цитата${sign}`;
        descriptionField.setRangeText(signText);
        updateDisplayField();
        return;
    }
    let text = descriptionField.value;
    let startSymbPos = descriptionField.selectionStart;
    let endSymbPos = descriptionField.selectionEnd;
    if (text.charAt(startSymbPos - 1) === sign && text.charAt(endSymbPos) === sign) {
        descriptionField.value = spliceString(text, startSymbPos - 1, 1, "");
        descriptionField.value = spliceString(descriptionField.value, endSymbPos - 1, 1, "");
        updateDisplayField();
        descriptionField.selectionStart = startSymbPos - 1;
        descriptionField.selectionEnd = endSymbPos - 1;
        descriptionField.focus();
    } else {
        let selected = descriptionField.value.slice(startSymbPos, endSymbPos);
        descriptionField.setRangeText(`${sign}${selected}${sign}`);
        descriptionField.selectionStart = descriptionField.selectionStart + 1;
        descriptionField.selectionEnd = descriptionField.selectionEnd - 1;
        descriptionField.focus();
        updateDisplayField();
    }
}

function spliceString(str, start, count, stringToInsert) {
    return str.slice(0, start) + stringToInsert + str.slice(start + count);
}

function updateDisplayField() {
    displayField.html(descriptionField.value
        .replace(patternBold, '<b>$1</b>')
        .replace(patternItalic, '<i>$1</i>')
        .replace(patternCode, '<span class="code">$1</span>')
        .replace(patternBlockQuote, '<br></p><span class="block">$1</span>'));
}
