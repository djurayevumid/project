let sortedBy

$(document).ready(function () {
    sendUrlAndNumberOfRows('/api/user/question/allTime', 4)
    sortedBy = "allTime"
})

async function displayList(wrapper, page) {
    const items = await getItemsForPage()
    wrapper.innerHTML = "";
    page--;
    const months = ['янв','фев','мар','апр','мая','июн','июл','авг','сен','окт','ноя','дек']
    let question = '';

    for (let index in items) {
        let getT = items[index]['listTagDto'];
        const persistDate = new Date(items[index]['persistDateTime']);
        let dataTime = `${persistDate.getDate()} ${months[`${persistDate.getMonth()}`]} ${persistDate.getFullYear() % 100}г. в ${persistDate.getHours()}:${persistDate.getMinutes()}`;
        question += '<div class="row mb-4 pb-2 border-bottom">' +
            '   <div class="col-sm-auto me-3 text-lg-center p-0">' +
            '       <p class="m-0 text-secondary">' + items[index]['viewCount'] + '</p>' +
            '       <p class="m-0 text-secondary fs-sm">votes</p>' +
            '       <p class="m-0 text-secondary">' + items[index]['countAnswer'] + '</p>' +
            '       <p class="m-0 text-secondary fs-sm mb-3">answer</p>' +
            '       <p class="m-0 text-secondary fs-sm">' + items[index]['countAnswer'] + ' views</p>' +
            '   </div>' +
            '   <div class="col-sm">' +
            '       <a href="/question/' + items[index]['id'] + '"><h3 class="fs-5 text-primary">' +
            '           ' + items[index]['title'] + '' +
            '       </h3></a>' +
            '       <p class="fs-m">' +
            '           ' + items[index]['description'] +
            '       </p>' +
            '       <div class="row p-0">' +
            '           <div class="col-sm">';

        getT.forEach((tag) => {
            question += `<a href="/tags"><button class="btn btn-primary btn-sm fs-sm">` + tag['name'] + `</button></a>`;
        });

        question += `    </div>
                            </div>
                            <div class="text-end">
                            <p class="text-secondary fs-sm m-0 mb-1">создан ${dataTime}</p>
                                <div class="d-inline-flex">
                                    <a href="/user"><img src="https://lh3.googleusercontent.com/a-/AOh14GgBh42YYQEzixOX5FbvcDyKCzjJUqJ7YGfJDbk5-w=k-s192" alt="картинка пользователя" width="32" height="32" class="rounded-2"></a>
                                    <div class="text-start ms-1">
                                        <a href="/user"><p class="text-primary fs-sm m-0">` + items[index]['authorName'] + `</p></a>
                                    </div>
                                    <span class="text-reputation fs-sm ml-1" title="reputation score">` + items[index]['authorReputation'] + `</span>
                                </div>
                          </div>
                    </div>
                </div>`;
    }
    wrapper.innerHTML = question;
}

async function getItemsForPage() {
    let itemsForPage = await fetch (url + keyOfCurrentPage + valueOfCurrentPage + keyCountOfItems + valueOfCountItems, {
        method: "GET",
        contentType: "application/json",
        headers: {
            "Authorization": token
        }
    })
    let itemsOnPage = await itemsForPage.json()
    $("#countAllQuestions").text(`Total questions: ${itemsOnPage.totalResultCount}`);
    return itemsOnPage["items"]
}

async function paginationButton(page) {
    let a = document.createElement('a');
    a.innerText = page;
    a.classList.add('a-pagination--item');

    if (valueOfCurrentPage === page)
        a.classList.add('a-is-selected');

    a.addEventListener('click', async function (e) {
        e.preventDefault();
        valueOfCurrentPage = page;
        await displayList(table_element, valueOfCurrentPage);
        let current_btn = document.querySelector('.pagenumbers a.a-is-selected');
        current_btn.classList.remove('a-is-selected');
        a.classList.add('a-is-selected');
    });
    return a;
}

document.getElementById('newestButton').addEventListener('click', async function(e) {
    e.preventDefault();
    await sendUrlAndNumberOfRows('/api/user/question/new', 4);
    sortedBy = "new"
})
document.getElementById('unansweredButton').addEventListener('click', async function(e) {
    e.preventDefault();
    await sendUrlAndNumberOfRows('/api/user/question/noAnswer', 4);
    sortedBy = "noAnswer"
})
document.getElementById('reputationButton').addEventListener('click', async function(e) {
    e.preventDefault();
    await sendUrlAndNumberOfRows('/api/user/question/allTime', 4);
    sortedBy = "allTime"
})



