// ?gameId=1
const urlParams = new URLSearchParams(window.location.search);
const gameId = urlParams.get('gameId');
console.log("Game Id: " + gameId);

function loadGames() {

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
}

loadGames();

//var button = document.getElementById("button");
//button.addEventListener("click", createGame);
//onclick = function () {
//    createGame();
//};


function createGame() {
    axios.post('http://localhost:8080/api/games')
        .then(function (response) {
            console.log(response.data);
        })
        .catch(function (error) {
            console.log(error);
        });
    window.location.reload();
}