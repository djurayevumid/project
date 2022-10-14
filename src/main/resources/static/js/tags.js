
$(document).ready(function () {
    sendUrlAndNumberOfRows('/api/user/tag/popular', 15);
});

async function getFilterTags(value) {
    valueOfCurrentPage = 1;
    valueFilter = value;
    popularTags();
}

async function displayList(wrapper, page) {
    const items = await getItemsForPage()
    wrapper.innerHTML = "";
    page--;
    let tag = "";
    for (let note in items) {
        let tr = document.createElement('tr');
        tr.classList.add('tags');

        tag +=
            `<div class="card">` +
            `<div class="card-body">` +
            `<h1>` +
            `<a href="/questions" class="btn btn-sm btn-outline-secondary">` + items[note]['name'] + `</a>` +
            `</h1>` +
            `<p class="card-text">` + `Используйте этот тэг для поиска вопросов по ` + items[note]['name'] + `</p>` +
            `<small>` +
            `<p class = "text-start">` +
            `<a href="#" class="card-link">` + items[note]['questionCount'] + ` за все время` + `</a>` +
            `</p>` +
            `<p class="text-end">` +
            `<a href="#" class="card-link">` + items[note]['questionCountOneDay'] + ` сегодня` + `</a>` +
            `<a href="#" class="card-link right">` + items[note]['questionCountOneWeek'] + ` за неделю` + `</a>` +
            `</p>` +
            `</small>` +
            `</div>` +
            `</div>`;

    }
    wrapper.innerHTML = tag;
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

async function popularTags() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/tag/popular', 15);
    })
}

async function nameTags() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/tag/name', 15);
    })

}

async function newTags() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/tag/new', 15);
    })
}

async function ignoredTags() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/tag/ignored', 15);
    })
}

async function trackingTags() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/tag/tracking', 15);
    })
}


