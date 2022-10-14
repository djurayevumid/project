class Sidebar extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
    <div class="menu sticky-top p-4" id="sideBarContent" style="background-color: #F8F9FA; max-width: 300px; top: 10%;">
        <a href="/main" class="link-dark">
            <span><h3><small style="font-weight: bold">Главная</small></h3></span>
        </a>
        <ul class="list-unstyled pt-3">
            <li class="mb-1">
                <p>ПУБЛИЧНЫЕ</p>
                <div class="collapse show">
                    <ul class="list-unstyled fw-normal pb-1 small">
                        <li class="sideBarElement">
                        <svg aria-hidden="true" class="svg-icon iconGlobe" style="margin-left: 10px" width="18" height="18" viewBox="0 0 18 18">
                        <path style="fill: black;" d="M9 1C4.64 1 1 4.64 1 9c0 4.36 3.64 8 8 8 4.36 0 8-3.64 8-8 0-4.36-3.64-8-8-8ZM8 15.32a6.46 6.46 0 0 1-4.3-2.74 6.46 6.46 0 0 1-.93-5.01L7 11.68v.8c0 .88.12 1.32 1 1.32v1.52Zm5.72-2c-.2-.66-1-1.32-1.72-1.32h-1v-2c0-.44-.56-1-1-1H6V7h1c.44 0 1-.56 1-1V5h2c.88 0 1.4-.72 1.4-1.6v-.33a6.45 6.45 0 0 1 3.83 4.51 6.45 6.45 0 0 1-1.51 5.73v.01Z">
                        </path></svg>
                        <a href="/questions" class="link-dark"><h3><small style="margin-left: 5px">Вопросы</small></h3></a>
                        </li>
                        <li class="sideBarElement"><a href="/tags" class="link-dark"><h3><small style="margin-left: 33px">Метки</small></h3></a></li>
                        <li class="sideBarElement"><a href="/users" class="link-dark"><h3><small style="margin-left: 33px">Участники</small></h3></a></li>
                        <li class="sideBarElement"><a href="/unanswered" class="link-dark"><h3><small style="margin-left: 33px">Неотвеченные</small></h3></a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
        `
    }
}

$(document).ready(function(){
    const currentLocation = location.href;
    const menuItem = document.querySelectorAll('.sideBarElement a'); console.log(menuItem)
    const menuLength = menuItem.length;
    for (let i = 0; i < menuLength; i++){
        if (menuItem[i].href === currentLocation){
            $(menuItem[i]).parent().addClass('highlighted');
        }
    }
});


customElements.define('jm-sidebar', Sidebar)
