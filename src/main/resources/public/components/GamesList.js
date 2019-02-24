class GamesList extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            games: []
        };
    }
    componentDidMount() {
        this.refreshGames();
    }

    refreshGames() {
        axios.get("/api/games")
            .then((response) => {
                const games = response.data;
                this.setState({games: games});
            });
    }

    addGame() {
        axios.post("/api/games").then(response => {
            this.refreshGames();
        });

    }

    render() {
        return (
            <div>
                <Title text={this.props.title}></Title>
                <div>Displaying {this.state.games.length} games</div>
                <p><button onClick={() => this.addGame()}>Add Game</button></p>
                <ul>
                {this.state.games.map(game => (
                    <li><a href={"/games/" + game.id}>Game {game.id}</a> is {game.state}</li>
                ))}
                </ul>
            </div>
        )
    }
}