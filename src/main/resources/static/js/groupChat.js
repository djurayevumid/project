'use strict';
console.log(window.location.pathname.substring(10))
let tokenChat = $.cookie("jwt_token");

const URL_CURRENT_USER = "/api/auth/currentUser"

let senderId
let senderNickname
let senderImage
let chatId = 1

const setCurrentUser = (currentUser) => {
    senderId = currentUser.id
    senderNickname = currentUser.nickname
    senderImage = currentUser.linkImage
}

$.ajax({
    url: URL_CURRENT_USER,
    type: "GET",
    async: false,
    beforeSend: function (request) {
        if (tokenChat != null) {
            request.setRequestHeader("Authorization", "Bearer " + tokenChat);
        }
    },
    success: function (result) {
        setCurrentUser(result)
    }
})

const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const messageArea = document.querySelector('#messageArea');
const connectingElement = document.querySelector('.connecting');

let stompClient = null

function connect(event) {

    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, onConnected, onError);
}


function onConnected() {

    // Subscribe to the Public Topic
    stompClient.subscribe('/topic/public', onMessageReceived);
}

function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    const messageContent = messageInput.value.trim();
    console.log(messageContent)
    if (messageContent && stompClient) {
        const chatMessage = {
            message: messageContent,
            senderId: senderId,
            senderNickname: senderNickname,
            senderImage: senderImage,
            chatId:  chatId
        };

        stompClient.send("/app/groupchat", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
    }
    event.preventDefault();

}


function onMessageReceived(payload) {
    const messageRequestDto = JSON.parse(payload.body);
    console.log(messageRequestDto)

    const messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');

    const avatarElement = document.createElement("i");
    avatarElement.innerHTML = `<img src="${messageRequestDto.senderImage}"
                             alt="" width="42" height="42" class="bar-sm">`;

    messageElement.appendChild(avatarElement);

    const usernameElement = document.createElement('span');
    const usernameText = document.createTextNode(messageRequestDto.senderNickname);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);
    const br = document.createElement("br");
    usernameElement.after(br)

    const textElement = document.createElement('a');
    const messageText = document.createTextNode(messageRequestDto.message);
    textElement.appendChild(messageText);


    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight
}


document.addEventListener('DOMContentLoaded', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
