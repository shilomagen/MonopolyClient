package com.monopoly.scenes;

import com.monopoly.client.ws.DuplicateGameName_Exception;
import com.monopoly.client.ws.GameDoesNotExists_Exception;
import com.monopoly.client.ws.InvalidParameters_Exception;
import com.monopoly.utility.EventTaker;
import com.monopoly.ws.MonopolyWSClient;
import java.io.IOException;
import java.util.Timer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneManager extends Application {

    private static final String USER_LANDING_SCENE_PATH = "LandingScene.fxml";
    private static final String USER_CREATING_SCENE_PATH = "UserCreatingScene.fxml";
    private static final String CREATE_GAME_SCENE_PATH = "CreateGame.fxml";
    private static final String JOIN_GAME_SCENE_PATH = "JoinGame.fxml";
    private static final String WAIT_FOR_GAME_SCENE_PATH = "WaitForGame.fxml";

    private Stage primaryStage;

    private Pane landingSceneLayout;
    private Scene landingScene;
    private LandingSceneController landingSceneController;

    private Pane createGameLayout;
    private Scene createGameScene;
    private CreateGameController createGameController;

    private Pane joinGameLayout;
    private Scene joinGameScene;
    private JoinGameController joinGameController;

    private Pane waitForGameLayout;
    private Scene waitForGameScene;

    private MonopolyWSClient monopolyWS;

//	private UserCreatingSceneController userCreatingSceneController;
//	private Scene userCreatingScene;
//	private Pane userCreatingSceneLayout;
    private Scene mainBoardScene;
    private MainBoardController mainBoardController;
//	private BooleanProperty finishedInit = new SimpleBooleanProperty(this, "Finish Init");
//	private GameEngine gameEngine;

    @Override
    public void start(Stage primaryStage) throws IOException, DuplicateGameName_Exception, InvalidParameters_Exception, GameDoesNotExists_Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Start Screen");
        this.loadLandingScreen();
        this.loadCreateGameScene();
        this.loadJoinGameScene();
        this.loadWaitForGameScene();     
        String namegame="ss22";
        this.monopolyWS = new MonopolyWSClient();
        this.monopolyWS.createWSClient("localhost", 8080);
        this.monopolyWS.getBoardScheme();
        this.monopolyWS.getBoardXML();
        this.monopolyWS.createNewGame(namegame, 2, 3);
        this.monopolyWS.addPlayerToGame(namegame, "adsad");
        this.monopolyWS.getPlayersDetails(namegame);
        

//		this.loadUserCreatingScreen();
//		this.gameEngine = new GameEngine();
        mainBoardController = new MainBoardController(this);
        mainBoardController.setPlayersManager(this.monopolyWS.getPlayerManager());
        mainBoardScene = mainBoardController.getMainBoardScene();
        this.mainBoardController.addPlayersToMainBoard();
        this.primaryStage.setScene(this.getMainBoardScene());

//		mainBoardController.setPlayersManager(userCreatingSceneController.getPlayersManager());
//		mainBoardController.setGameEngine(this.gameEngine);
//		mainBoardController.registerButtonEvents();
//		this.gameEngine.setMainBoardController(this.mainBoardController);
//		this.gameEngine.setPlayersManager(userCreatingSceneController.getPlayersManager());
//		this.gameEngine.setCellModel(this.mainBoardController.getCellModel());
//		this.gameEngine.setSurpriseDeck(this.mainBoardController.getSurpriseDeck());
//		this.gameEngine.setWarrantDeck(this.mainBoardController.getWarrantDeck());
//        primaryStage.setScene(landingScene);
//shilo
        primaryStage.show();
        Timer timer = new Timer();
        timer.schedule(new EventTaker(this.monopolyWS), 0, 2000);
//		userCreatingSceneController.getFinishedInit().addListener((source, oldValue, newValue) -> {
//			if (newValue) {
//				landingSceneController.activateStartGame(true);
//			}
//		});
//		this.landingSceneController.getStartGame().addListener((source, oldValue, newValue) -> {
//			if (newValue){
//				this.gameEngine.startObserv();	
//				this.gameEngine.addEventToEngine(EventTypes.PLAY_TURN);
//			}
//		});
    }

    public Stage getPrimaryStage() {
        return this.primaryStage;
    }

    public void loadLandingScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource(USER_LANDING_SCENE_PATH));
        landingSceneLayout = loader.load();
        landingSceneController = (LandingSceneController) loader.getController();
        landingSceneController.setSceneManager(this);
        this.landingScene = new Scene(landingSceneLayout);
    }

    public void loadCreateGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource(CREATE_GAME_SCENE_PATH));
        this.createGameLayout = loader.load();
        this.createGameController = (CreateGameController) loader.getController();
        this.createGameController.setManager(this);
        this.createGameScene = new Scene(createGameLayout);
    }

    public void loadJoinGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource(JOIN_GAME_SCENE_PATH));
        this.joinGameLayout = loader.load();
        this.joinGameController = (JoinGameController) loader.getController();
        this.joinGameController.setManager(this);
        this.joinGameScene = new Scene(joinGameLayout);
    }
//	public void loadUserCreatingScreen() throws IOException {
//		FXMLLoader loader = new FXMLLoader();
//		loader.setLocation(SceneManager.class.getResource(USER_CREATING_SCENE_PATH));
//		userCreatingSceneLayout = loader.load();
//		userCreatingSceneController = (UserCreatingSceneController) loader.getController();
//		userCreatingSceneController.setManager(this);
//		this.userCreatingScene = new Scene(userCreatingSceneLayout);
//	}
//	public void showUserCreatingScreen() {
//		this.primaryStage.setScene(userCreatingScene);
//	}

    public void showLandingScreen() {
        this.primaryStage.setScene(landingScene);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public Scene getStartScene() {
        return this.landingScene;
    }

//	public Scene getUserCreationScene() {
//		return this.userCreatingScene;
//	}
//
    public Scene getMainBoardScene() {
        return this.mainBoardScene;
    }

    public MainBoardController getMainBoardController() {
        return this.mainBoardController;
    }

    public Scene getCreateGameScene() {
        return this.createGameScene;
    }

    public Scene getJoinGameScene() {
        return this.joinGameScene;
    }

    public MonopolyWSClient getMonopolyWS() {
        return this.monopolyWS;
    }

    private void loadWaitForGameScene() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(SceneManager.class.getResource(WAIT_FOR_GAME_SCENE_PATH));
        this.waitForGameLayout = loader.load();
        this.waitForGameScene = new Scene(waitForGameLayout);
    }

    public void setWaitScene() {
        this.primaryStage.setScene(this.waitForGameScene);
    }

}
