async function getFilterUsers(value) {
    valueFilter = value;
    valueOfCurrentPage = 1;
    await getUsersByReputation()
}

$(document).ready(function () {
    sendUrlAndNumberOfRows('/api/user/reputation', 36);
})

async function displayList(wrapper, page) {
    const items = await getItemsForPage()
    wrapper.innerHTML = "";
    page--;
    for (let note in items) {
        let tr = document.createElement('tr');
        tr.classList.add('user-info');

        tr.innerHTML =
            '<div class="user-gravatar48">' +
            '        <a href="/*"><div class="gravatar-wrapper-48"><img src="https://i.stack.imgur.com/Xu7hp.jpg?s=96&g=1" alt="" width="48" height="48" class="bar-sm"></div></a>' +
            '</div>' +
            '<div class="user-details">' +
            '    <a href="/*">' + items[note]['nickname'] +'</a>' +
            '    <span class="user-location">' + items[note]['city'] + '</span>' +
            '    <div class="-flair">' +
            '        <span class="reputation-score" title="reputation: ' + items[note]['reputation'] + ' total reputation: ' + items[note]['reputationTotal'] + '" dir="ltr">' + items[note]['reputation'] + '</span>' +
            '     </div>' +
            '</div>' +
            '<div class="user-tags"></div>';

        wrapper.appendChild(tr)
    }
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

async function getUsersByReputation() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/reputation', 36);
    })
}

async function getUsersByPersistDate() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/new', 36);
    })
}

async function getUsersByVote() {
    $(document).ready(function () {
        sendUrlAndNumberOfRows('/api/user/vote', 36);
    })
}









