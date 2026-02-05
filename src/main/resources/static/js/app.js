let stompClient = null;

function connect() {

    console.log("Connecting to WebSocket...");
    console.log("Player Name:", playerName);
    console.log("Moderator Name:", moderatorName);
    console.log("Are they equal?", playerName === moderatorName);

    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {

        console.log("WebSocket connected successfully");

        stompClient.subscribe(`/topic/room/${roomId}`, msg => {
            console.log("Received room update:", msg.body);
            const room = JSON.parse(msg.body);
            renderRoom(room);
        });

        stompClient.send(`/app/room/${roomId}/join`,
            {},
            JSON.stringify({ player: playerName })
        );

        showModeratorControls();
        renderDeck();
    });
}

function initializePage() {
    console.log("Initializing page...");
    showModeratorControls();
    renderDeck();
    renderInitialPlayers();
}

function showModeratorControls() {
    console.log("Checking moderator controls...");
    console.log("playerName === moderatorName:", playerName === moderatorName);

    if (playerName === moderatorName) {
        console.log("Showing moderator controls");
        document.getElementById("moderatorControls").style.display = "block";
        document.getElementById("storyControls").style.display = "block";
    } else {
        console.log("Not showing moderator controls - player is not moderator");
    }
}

function renderInitialPlayers() {
    const list = document.getElementById("players");
    list.innerHTML = "";

    const li = document.createElement("li");
    li.className = "list-group-item text-muted";
    li.innerText = "Connecting...";
    list.appendChild(li);
}

function renderDeck() {

    const deckDiv = document.getElementById("deck");
    deckDiv.innerHTML = "";

    let values = [];

    if (deckType === "FIBONACCI") {
        values = ["1", "2", "3", "5", "8", "13", "21", "34", "?"];
    }

    if (deckType === "TSHIRT") {
        values = ["XS", "S", "M", "L", "XL", "XXL", "Tent", "?"];
    }

    values.forEach(v => {

        const btn = document.createElement("button");
        btn.className = "btn btn-primary";
        btn.innerText = v;

        btn.onclick = () => sendVote(v);

        deckDiv.appendChild(btn);
    });
}

function renderRoom(room) {

    renderStory(room);
    renderStorySelect(room);
    renderPlayers(room);
}

function renderPlayers(room) {

    const list = document.getElementById("players");
    list.innerHTML = "";

    if (!room.players || room.players.length === 0) {
        const li = document.createElement("li");
        li.className = "list-group-item text-muted";
        li.innerText = "No players yet";
        list.appendChild(li);
        return;
    }

    room.players.forEach(p => {

        let icon = room.revealed
            ? (p.vote ?? "-")
            : (p.vote
                ? `<i class="fa-solid fa-square-check"></i>`
                : `<i class="fa-regular fa-square"></i>`);

        const li = document.createElement("li");
        li.className = "list-group-item d-flex justify-content-between";

        li.innerHTML = `
            <strong>${p.name}</strong>
            <span>${icon}</span>
        `;

        list.appendChild(li);
    });
}

function renderStory(room) {

    const box = document.getElementById("activeStoryBox");

    if (!room.activeStory) {
        box.innerHTML = "No story selected yet";
        return;
    }

    const url = room.activeStory.url || "";
    const urlHtml = url
        ? `<a href="${url}" target="_blank">${url}</a>`
        : "";

    box.innerHTML = `
        <strong>${room.activeStory.title}</strong><br>
        ${urlHtml}
    `;
}

function renderStorySelect(room) {

    const select = document.getElementById("storySelect");
    if (!select) return;

    select.innerHTML = "";

    if (!room.stories || room.stories.length === 0) {
        const opt = document.createElement("option");
        opt.value = "";
        opt.text = "No stories added";
        select.appendChild(opt);
        return;
    }

    room.stories.forEach((s, i) => {

        const opt = document.createElement("option");
        opt.value = i;
        opt.text = s.completed ? "✓ " + s.title : s.title;

        if (room.activeStory && room.activeStory.title === s.title) {
            opt.selected = true;
        }

        select.appendChild(opt);
    });
}

function sendVote(vote) {
    stompClient.send(`/app/room/${roomId}/vote`,
        {},
        JSON.stringify({ player: playerName, vote })
    );
}

function sendReveal() {
    stompClient.send(`/app/room/${roomId}/reveal`,
        {},
        JSON.stringify({ player: playerName })
    );
}

function sendReset() {
    stompClient.send(`/app/room/${roomId}/reset`,
        {},
        JSON.stringify({ player: playerName })
    );
}

function addStory() {

    const titleInput = document.getElementById("storyTitle");
    const urlInput = document.getElementById("storyUrl");

    const title = titleInput.value.trim();
    const url = urlInput.value.trim();

    if (!title) {
        alert("Please enter a story title");
        return;
    }

    stompClient.send(`/app/room/${roomId}/story/add`,
        {},
        JSON.stringify({
            player: playerName,
            title: title,
            url: url
        })
    );

    titleInput.value = "";
    urlInput.value = "";
}

function selectStory() {

    const selectElement = document.getElementById("storySelect");
    const selectedIndex = selectElement.value;

    if (selectedIndex === "" || selectedIndex < 0) {
        return;
    }

    stompClient.send(`/app/room/${roomId}/story/select`,
        {},
        JSON.stringify({
            player: playerName,
            index: parseInt(selectedIndex)
        })
    );
}

function markComplete() {

    const selectElement = document.getElementById("storySelect");
    const selectedIndex = selectElement.value;

    if (selectedIndex === "" || selectedIndex < 0) {
        alert("Please select a story first");
        return;
    }

    stompClient.send(`/app/room/${roomId}/story/complete`,
        {},
        JSON.stringify({
            player: playerName,
            index: parseInt(selectedIndex)
        })
    );
}

function removeStory() {

    const selectElement = document.getElementById("storySelect");
    const selectedIndex = selectElement.value;

    if (selectedIndex === "" || selectedIndex < 0) {
        alert("Please select a story first");
        return;
    }

    if (!confirm("Are you sure you want to remove this story?")) {
        return;
    }

    stompClient.send(`/app/room/${roomId}/story/remove`,
        {},
        JSON.stringify({
            player: playerName,
            index: parseInt(selectedIndex)
        })
    );
}

function copyRoomLink() {
    const link = window.location.href.split("&name=")[0];

    navigator.clipboard.writeText(link).then(() => {
        const btn = document.getElementById("copyLinkBtn");
        const originalText = btn.innerText;
        btn.innerText = "Copied!";
        btn.classList.remove("btn-outline-primary");
        btn.classList.add("btn-success");

        setTimeout(() => {
            btn.innerText = originalText;
            btn.classList.remove("btn-success");
            btn.classList.add("btn-outline-primary");
        }, 2000);
    }).catch(err => {
        alert("Failed to copy link: " + err);
    });
}

initializePage();
connect();