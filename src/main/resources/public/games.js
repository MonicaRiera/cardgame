// ?gameId=1
const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');
console.log("Game Id: " + gameId);

const games = fetch("http://localhost:8080/api/games"); // + gameId to call a specific game page

// it returns a promise, it is an object that will contain the data we want to obtain
games
    .then(x => x.json()) //converts the response to json
    .then(function (games) {
    // this function will be called when the data comes
        console.log(games);
        for (let game of games) {
            console.log(`Game ${game.id} -> ${game.state}`)
        }
    // at this point, games contains the data that the end-point sends (the list of games)
        const container = document.getElementById("container");
        for (let game of games) {
            let gameInfo = document.createElement("p");
            let link = document.createElement("a");
            let linkText = document.createTextNode(`Go to this game`);
            link.appendChild(linkText);
            gameInfo.textContent = `Game ID: ${game.id} is ${game.state}`;
            link.title = `Go to this game`;
            link.href = "http://localhost:8080/game.html?gameId=" + game.id;
            container.appendChild(gameInfo);
            container.appendChild(link);
        }
});


/**let button = document.getElementById("button");
button.onclick = function () {
    displayCards();
};


function displayCards() {
    let text = document.getElementById("text");
    text.textContent = "Let's play...";

    let container = document.getElementById("container");

    let subtitle = document.createElement("h3");
    subtitle.textContent = "...the card game!!";
    container.appendChild(subtitle);

    let cards = [
        {magic: 1, strength: 8, intelligence: 1},
        {magic: 2, strength: 7, intelligence: 1},
        {magic: 3, strength: 3, intelligence: 4}];
    for (let i = 0; i < cards.length; i++) {
        let paragraph = document.createElement("p");
        paragraph.textContent = "Card " + (i + 1) + " - I: " + cards[i].intelligence + " M: " + cards[i].magic + " S: " + cards[i].strength;
        container.appendChild(paragraph);
    }

}*/
