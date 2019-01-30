// ?gameId=1
const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');
console.log("Game Id: " + gameId);

//const game = fetch("http://localhost:8080/api/games/" + gameId);
const gamePromise = axios.get("http://localhost:8080/api/games/" + gameId);

gamePromise
    //.then(x => x.json()) //converts the response to json
    .then(function (response) {
        const game = response.data;
        // this function will be called when the data comes
        // at this point, games contains the data that the end-point sends (the list of games)
        const container = document.getElementById("container");
        console.log(game);
        let gameTitle = document.createElement("h1");
        gameTitle.textContent = `Game ID: ${game.id}`;
        container.appendChild(gameTitle);
        let gameState = document.createElement("h2");
        gameState.textContent = game.state;
        container.appendChild(gameState);
        let gamePlayers = document.createElement("p");
        gamePlayers.textContent = game.toString();
        container.appendChild(gamePlayers);

        let link = document.createElement("a");
        let linkText = document.createTextNode(`Go back to games`);
        link.appendChild(linkText);
        link.href = "http://localhost:8080/games.html";
        container.appendChild(link);

    });