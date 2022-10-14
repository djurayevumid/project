$(async () => {
    await displayTagList();
})

async function displayTagList() {
    await fetch('/api/user/tag/related', {
        headers: {
            "Authorization": "Bearer " + $.cookie("jwt_token")
        }
    }).then(res => res.json()).then(tags => {
        tags.forEach(tag => {
            let tr = document.getElementById("tagsbar").innerHTML +=
                "<div class=\"mb-2\"> \n" +
                "         <a href=\/question/tag/" + tag.id + "\> <div class=\"d-inline-flex my-auto\"> \n" +
                "            <button class=\"btn btn-primary btn-sm fs-sm\">" + tag.title + "</button>\n" +
                "            <div class=\"ms-1 fs-sm text-secondary my-auto\">" + "х " + tag.countQuestion + "</div> \n" +
                "            </div>\n" +
                "            </div>"
        })
    })
}

