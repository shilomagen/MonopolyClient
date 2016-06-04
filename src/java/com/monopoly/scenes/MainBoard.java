package com.monopoly.scenes;


import com.monopoly.client.engine.InitiateGame;
import com.monopoly.client.player.Player;
import com.monopoly.client.ws.PlayerStatus;
import com.monopoly.data.City;
import com.monopoly.data.Transportation;
import com.monopoly.data.Utility;
import com.monopoly.utility.BoardConsts;
import com.monopoly.utility.PositionHelper;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class MainBoard {
	private AnchorPane root;
	private AnchorPane gridAnchor;
	private GridPane boardPane;
	private AnchorPane playersAnchor;
	private Label screenConsole;
	private Button rollButton;
	private Pane dicePane;
	private VBox playerVBox;

	public MainBoard() {
		root = new AnchorPane();
		root.setPrefHeight(768);
		root.setPrefWidth(1024);

		gridAnchor = new AnchorPane();
		gridAnchor.getStyleClass().add("grid-anchor");
		gridAnchor.setLayoutX(167.0);
		gridAnchor.setLayoutY(124);
		gridAnchor.setPrefHeight(768);
		gridAnchor.setPrefWidth(835);
		AnchorPane.setRightAnchor(gridAnchor, 189.0);
		AnchorPane.setTopAnchor(gridAnchor, 0.0);
		AnchorPane.setBottomAnchor(gridAnchor, 0.0);
		AnchorPane.setLeftAnchor(gridAnchor, 0.0);

		boardPane = new GridPane();
		boardPane.setLayoutX(85);
		boardPane.setLayoutY(86);
		boardPane.setPrefHeight(768);
		boardPane.setPrefWidth(835);
		boardPane.getStyleClass().add("general-grid");
		AnchorPane.setBottomAnchor(boardPane, 0.0);
		AnchorPane.setLeftAnchor(boardPane, 0.0);
		AnchorPane.setRightAnchor(boardPane, 0.0);
		AnchorPane.setTopAnchor(boardPane, 0.0);

		gridAnchor.getChildren().add(boardPane);
		this.createBasicGridPane();

		playersAnchor = new AnchorPane();
		playersAnchor.setLayoutX(835);
		playersAnchor.setPrefHeight(768);
		playersAnchor.setPrefWidth(189);
		this.createPlayerPane();
		root.getChildren().add(gridAnchor);

	}

	public void createBasicGridPane() {

		// Create columns
		ColumnConstraints colEdge, colRegular;
		colEdge = new ColumnConstraints();
		colRegular = new ColumnConstraints();

		colEdge.setHgrow(Priority.SOMETIMES);
		colEdge.setMaxWidth(113.85);
		colEdge.setMinWidth(10.0);
		colEdge.setPrefWidth(113.85);
		colEdge.setHalignment(HPos.CENTER);

		colRegular.setHgrow(Priority.SOMETIMES);
		colRegular.setMaxWidth(75.9);
		colRegular.setMinWidth(10.0);
		colRegular.setPrefWidth(75.9);
		colRegular.setHalignment(HPos.CENTER);

		// Create Rows
		RowConstraints rowEdge, rowRegular;
		rowEdge = new RowConstraints();
		rowRegular = new RowConstraints();

		rowEdge.setMaxHeight(104.7);
		rowEdge.setMinHeight(10.0);
		rowEdge.setPrefHeight(104.7);
		rowEdge.setVgrow(Priority.SOMETIMES);
		rowEdge.setValignment(VPos.CENTER);

		rowRegular.setMaxHeight(69.8);
		rowRegular.setMinHeight(10.0);
		rowRegular.setPrefHeight(69.8);
		rowRegular.setVgrow(Priority.SOMETIMES);
		rowRegular.setValignment(VPos.CENTER);

		this.boardPane.getColumnConstraints().add(colEdge);
		this.boardPane.getRowConstraints().add(rowEdge);
		for (int i = 0; i < 8; i++) {
			this.boardPane.getColumnConstraints().add(colRegular);
			this.boardPane.getRowConstraints().add(rowRegular);
		}
		this.boardPane.getColumnConstraints().add(colEdge);
		this.boardPane.getRowConstraints().add(rowEdge);

	}

	public void createJailPane(int col, int row) {
		StackPane jailPane = new StackPane();
		jailPane.setPrefHeight(200);
		jailPane.setPrefWidth(200);
		ImageView jailImage = new ImageView();
		jailImage.setFitHeight(91);
		jailImage.setFitWidth(91);
		jailImage.setPickOnBounds(true);
		jailImage.setPreserveRatio(true);
		jailImage.setImage(new Image(BoardConsts.IMAGE_URL + "/jail-free-pass.png"));
		jailImage.setRotate(270);
		jailPane.getChildren().add(jailImage);
		StackPane.setAlignment(jailImage, Pos.CENTER);
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//						GameEngine.addEventToEngine(EventTypes.ON_JAIL_FREE_PASS);
//					}
//				}
//			}
//		});
		jailPane.getChildren().add(playerBox);
		this.boardPane.add(jailPane, col, row);
	}

	public void createFreeParkingPane(int col, int row) {
		StackPane freeParkingPane = new StackPane();
		freeParkingPane.setPrefHeight(200);
		freeParkingPane.setPrefWidth(200);
		ImageView freeParkingImage = new ImageView();
		freeParkingImage.setFitHeight(91);
		freeParkingImage.setFitWidth(91);
		freeParkingImage.setPickOnBounds(true);
		freeParkingImage.setPreserveRatio(true);
		freeParkingImage.setImage(new Image(BoardConsts.IMAGE_URL + "/free-parking.png"));
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_FREE_PARKING);
//
//					}
//				}
//			}
//		});
		freeParkingPane.getChildren().addAll(freeParkingImage, playerBox);
		StackPane.setAlignment(freeParkingImage, Pos.CENTER);

		this.boardPane.add(freeParkingPane, col, row);

	}

	public void createGoToJailPane(int col, int row) {
		StackPane goToJailPane = new StackPane();
		goToJailPane.setPrefHeight(200);
		goToJailPane.setPrefWidth(200);
		ImageView goToJailImage = new ImageView();
		goToJailImage.setFitHeight(91);
		goToJailImage.setFitWidth(91);
		goToJailImage.setPickOnBounds(true);
		goToJailImage.setPreserveRatio(true);
		goToJailImage.setImage(new Image(BoardConsts.IMAGE_URL + "/gotojail.png"));
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_GO_TO_JAIL);
//
//					}
//				}
//			}
//		});
		goToJailPane.getChildren().addAll(goToJailImage, playerBox);
		StackPane.setAlignment(goToJailImage, Pos.CENTER);
		this.boardPane.add(goToJailPane, col, row);

	}

	public void createStartPane(int col, int row) {
		StackPane startPane = new StackPane();
		startPane.setPrefHeight(200);
		startPane.setPrefWidth(200);
		ImageView startImage = new ImageView();
		startImage.setFitHeight(91);
		startImage.setFitWidth(91);
		startImage.setPickOnBounds(true);
		startImage.setPreserveRatio(true);
		startImage.setImage(new Image(BoardConsts.IMAGE_URL + "/start.png"));
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//						if (GameEngine.isEventHandlerOn)
//							GameEngine.addEventToEngine(EventTypes.ON_START);
//					}
//				}
//			}
//		});
		startPane.getChildren().addAll(startImage, playerBox);
		StackPane.setAlignment(startImage, Pos.CENTER);
		this.boardPane.add(startPane, col, row);

	}

	public void assignPaneOnGridByCoordinates(int gridCol, int gridRow, Node paneToAssign) {
		this.boardPane.add(paneToAssign, gridCol, gridRow);
	}

	public void createCityPane(int col, int row, City city, String colorCountry) {
		SplitPane cityPane = new SplitPane();
		cityPane.getStyleClass().add("city-cell");
		Pane infoPane = new StackPane();
		Label countryLabel = new Label(city.getCountry());
		countryLabel.setId(this.generateCountryLabelID(col, row, city.getCountry()));
		Label cityLabel = new Label(city.getName());
		cityLabel.setId(this.generateCityLabelID(col, row, city.getName()));
		Label priceLabel = new Label(city.getCost() + "$");
		cityLabel.getStyleClass().add("city-label");
		countryLabel.getStyleClass().add("country-label");
		Tooltip infoTip = new Tooltip();
		infoTip.setText("House Cost: " + city.getHouseCost() + "\n" + "Stay Cost: " + city.getStayCost()
				+ "\nStay Cost 1" + city.getStayCost1() + "\nStay Cost 2" + city.getStayCost2() + "\nStay Cost 3"
				+ city.getStayCost3());

		infoTip.setId(this.generateToolTipID(col, row, city.getName()));
		countryLabel.setTooltip(infoTip);
		cityLabel.setTooltip(infoTip);
		priceLabel.getStyleClass().add("price-label");
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_CITY);
//
//					}
//				}
//			}
//		});
		infoPane.getChildren().add(playerBox);
		infoPane.getChildren().add(countryLabel);
		infoPane.getChildren().add(cityLabel);
		infoPane.getChildren().add(priceLabel);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.HORIZONTAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_LEFT_RIGHT);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_LEFT_RIGHT);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_UP_LEFT);
			cityLabel.getStyleClass().add("left");
			countryLabel.getStyleClass().add("left");
			priceLabel.getStyleClass().add("left");
			cityPane.getItems().add(infoPane);
			cityPane.getItems().add(new FlowPane());
			cityPane.getItems().get(1).setStyle("-fx-background-color:" + colorCountry + ";");
			((FlowPane) cityPane.getItems().get(1)).setId(generateHoseBoxID(col, row));
			((FlowPane) cityPane.getItems().get(1)).setVgap(3);
			((FlowPane) cityPane.getItems().get(1)).setHgap(1);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.HORIZONTAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_LEFT_RIGHT);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_LEFT_RIGHT);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_DOWN_RIGHT);
			cityLabel.getStyleClass().add("right");
			countryLabel.getStyleClass().add("right");
			priceLabel.getStyleClass().add("right");
			cityPane.getItems().add(new FlowPane());
			cityPane.getItems().get(0).setStyle("-fx-background-color:" + colorCountry + ";");
			((FlowPane) cityPane.getItems().get(0)).setId(generateHoseBoxID(col, row));
			((FlowPane) cityPane.getItems().get(0)).setVgap(3);
			((FlowPane) cityPane.getItems().get(0)).setHgap(1);
			cityPane.getItems().add(infoPane);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.VERTICAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_UP_DOWN);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_UP_DOWN);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_UP_LEFT);
			cityLabel.getStyleClass().add("top");
			countryLabel.getStyleClass().add("top");
			priceLabel.getStyleClass().add("top");
			cityPane.getItems().add(infoPane);
			cityPane.getItems().add(new FlowPane());
			cityPane.getItems().get(1).setStyle("-fx-background-color:" + colorCountry + ";");
			((FlowPane) cityPane.getItems().get(1)).setId(generateHoseBoxID(col, row));
			((FlowPane) cityPane.getItems().get(1)).setVgap(1);
			((FlowPane) cityPane.getItems().get(1)).setHgap(3);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			cityPane.setOrientation(Orientation.VERTICAL);
			cityPane.prefHeight(BoardConsts.SPLIT_PREF_HEIGHT_UP_DOWN);
			cityPane.prefWidth(BoardConsts.SPLIT_PREF_WIDTH_UP_DOWN);
			cityPane.setDividerPosition(0, BoardConsts.SPLIT_DIVIDOR_DOWN_RIGHT);
			cityLabel.getStyleClass().add("bottom");
			countryLabel.getStyleClass().add("bottom");
			priceLabel.getStyleClass().add("bottom");
			cityPane.getItems().add(new FlowPane());
			cityPane.getItems().add(infoPane);
			this.assignPaneOnGridByCoordinates(col, row, cityPane);
			cityPane.getItems().get(0).setStyle("-fx-background-color:" + colorCountry + ";");
			((FlowPane) cityPane.getItems().get(0)).setId(generateHoseBoxID(col, row));
			((FlowPane) cityPane.getItems().get(0)).setVgap(1);
			((FlowPane) cityPane.getItems().get(0)).setHgap(3);
		}
	}

	private String generateCityLabelID(int col, int row, String name) {
		return name + col + row;

	}

	private String generateCountryLabelID(int col, int row, String country) {
		return col + row + country;
	}

	private String generateToolTipID(int col, int row, String name) {
		return col + row + "ToolTip";
	}

	public void createBoardLogo() {
		StackPane centralPane = new StackPane();
		centralPane.setPrefSize(200, 150);

		ImageView centralImage = new ImageView();
		centralImage.setFitHeight(559);
		centralImage.setFitWidth(608);
		centralImage.setPickOnBounds(true);
		centralImage.setPreserveRatio(true);
		centralImage.setImage(new Image(BoardConsts.IMAGE_URL + "/main-pic.png"));

		Pane surpriseDeck = new Pane();
		surpriseDeck.setPrefSize(200, 200);
		ImageView surpriseImage = new ImageView(BoardConsts.IMAGE_URL + "/surprise-deck.png");
		surpriseImage.setFitHeight(223);
		surpriseImage.setFitWidth(189);
		surpriseImage.setRotate(8.0);
		surpriseImage.setLayoutX(38.0);
		surpriseImage.setLayoutY(21.0);
		surpriseImage.setPickOnBounds(true);
		surpriseDeck.getChildren().add(surpriseImage);

		Pane warrantDeck = new Pane();
		warrantDeck.setPrefSize(200, 200);
		ImageView warrantImage = new ImageView(BoardConsts.IMAGE_URL + "/warrant-deck.png");
		warrantImage.setFitHeight(223);
		warrantImage.setFitWidth(189);
		warrantImage.setRotate(6.3);
		warrantImage.setPickOnBounds(true);
		warrantImage.setLayoutX(380.0);
		warrantImage.setLayoutY(307.0);
		warrantDeck.getChildren().add(warrantImage);

		this.dicePane = new Pane();
		dicePane.setId("dicePane");
		dicePane.setPrefSize(204, 84);
		dicePane.setMaxHeight(84);
		StackPane.setAlignment(dicePane, Pos.BOTTOM_LEFT);

		ImageView leftDie = new ImageView();
		leftDie.setFitHeight(55);
		leftDie.setFitWidth(101);
		leftDie.setLayoutX(13);
		leftDie.setLayoutY(15);
		leftDie.setPreserveRatio(true);
		leftDie.setPickOnBounds(true);
		leftDie.setImage(new Image("file:src/com/monopoly/assets/dice/dice-1.png"));

		ImageView rightDie = new ImageView();
		rightDie.setFitHeight(55);
		rightDie.setFitWidth(101);
		rightDie.setLayoutX(133);
		rightDie.setLayoutY(15);
		rightDie.setPreserveRatio(true);
		rightDie.setPickOnBounds(true);
		rightDie.setImage(new Image("file:src/com/monopoly/assets/dice/dice-1.png"));

		this.rollButton = new Button("Roll");
		rollButton.setLayoutX(80);
		rollButton.setLayoutY(30);
		rollButton.setMnemonicParsing(false);
		rollButton.setOnAction((event) -> {

		});

		dicePane.getChildren().addAll(leftDie, rightDie, rollButton);

		this.screenConsole = new Label("");
		screenConsole.setId("screen-console");
		screenConsole.setAlignment(Pos.CENTER);
		screenConsole.setPrefSize(331, 47);
		screenConsole.setWrapText(true);

		StackPane.setAlignment(screenConsole, Pos.TOP_RIGHT);

		centralPane.getChildren().addAll(centralImage, surpriseDeck, warrantDeck, dicePane, screenConsole);

		this.boardPane.add(centralPane, 1, 1, 8, 8);

	}

	private void createCardDecks() {
		Pane surprisePane = new Pane();
		surprisePane.setPrefSize(200, 200);
		ImageView surpriseImage = new ImageView(BoardConsts.IMAGE_URL + "/surprise-deck.png");
		surpriseImage.setFitHeight(223);
		surpriseImage.setFitWidth(189);
		surpriseImage.setLayoutX(38);
		surpriseImage.setLayoutY(21);
		surpriseImage.setPickOnBounds(true);
		surpriseImage.setRotate(6.3);
		surprisePane.getChildren().add(surpriseImage);
		this.boardPane.add(surprisePane, 1, 1, 4, 4);

		Pane warrantPane = new Pane();
		warrantPane.setPrefSize(200, 200);
		ImageView warrantImage = new ImageView(BoardConsts.IMAGE_URL + "/warrant-deck.png");
		warrantImage.setFitHeight(223);
		warrantImage.setFitWidth(189);
		warrantImage.setLayoutX(76);
		warrantImage.setLayoutY(29);
		warrantImage.setPickOnBounds(true);
		warrantImage.setRotate(8.0);
		warrantPane.getChildren().add(warrantImage);
		this.boardPane.add(warrantPane, 5, 5, 4, 4);

	}

	public void createTransportationPane(int col, int row, Transportation trans) {
		StackPane transPane = new StackPane();
		transPane.getStyleClass().add("trans-util-cell");
		ImageView transImage = new ImageView(BoardConsts.IMAGE_URL + "/transportation.png");
		transImage.getStyleClass().add("trans-util-image");
		transImage.setPickOnBounds(true);

		Label transLabel = new Label(trans.getName());
		transLabel.getStyleClass().add("trans-util-label");
		Label transPrice = new Label(trans.getCost() + "$");
		transPrice.getStyleClass().add("trans-util-price");
		Tooltip infoTip = new Tooltip();
		infoTip.setText("Stay Cost: " + trans.getStayCost() + "\nStay Cost With All Transportation:" + InitiateGame.getAssets().getTransportationStayCost());
		transLabel.setTooltip(infoTip);
		transPrice.setTooltip(infoTip);
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_TRANSPORTATION);
//
//					}
//				}
//			}
//		});
		transPane.getChildren().add(playerBox);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("left");
			transImage.getStyleClass().add("left");
			transLabel.getStyleClass().add("left");
			transPrice.getStyleClass().add("left");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transPrice, Pos.CENTER_LEFT);
			transImage.setFitHeight(57);
			transImage.setFitWidth(60);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("right");
			transImage.getStyleClass().add("right");
			transLabel.getStyleClass().add("right");
			transPrice.getStyleClass().add("right");
			StackPane.setAlignment(transImage, Pos.CENTER_LEFT);
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transPrice, Pos.CENTER_RIGHT);
			transImage.setFitHeight(57);
			transImage.setFitWidth(60);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("top");
			transImage.getStyleClass().add("top");
			transLabel.getStyleClass().add("top");
			transPrice.getStyleClass().add("top");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transImage, Pos.BOTTOM_CENTER);
			StackPane.setAlignment(transPrice, Pos.TOP_CENTER);
			transImage.setFitHeight(50);
			transImage.setFitWidth(50);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			transPane.getStyleClass().add("bottom");
			transImage.getStyleClass().add("bottom");
			transLabel.getStyleClass().add("bottom");
			transPrice.getStyleClass().add("bottom");
			StackPane.setAlignment(transLabel, Pos.CENTER);
			StackPane.setAlignment(transImage, Pos.TOP_CENTER);
			StackPane.setAlignment(transPrice, Pos.BOTTOM_CENTER);
			transImage.setFitHeight(50);
			transImage.setFitWidth(50);
			transPane.getChildren().addAll(transImage, transLabel, transPrice);
			this.assignPaneOnGridByCoordinates(col, row, transPane);
		}
	}

	public void createUtilityPane(int col, int row, Utility util) {
		StackPane utilityPane = new StackPane();
		utilityPane.getStyleClass().add("trans-util-cell");
		ImageView utilityImage = new ImageView(BoardConsts.IMAGE_URL + "/utilities.png");
		utilityImage.getStyleClass().add("trans-util-image");
		utilityImage.setPickOnBounds(true);

		Label utilityLabel = new Label(util.getName());
		utilityLabel.getStyleClass().add("trans-util-label");
		Label utilityPrice = new Label(util.getCost() + "$");
		Tooltip infoTip = new Tooltip();
		infoTip.setText("Stay Cost: " + util.getStayCost() + "\nStay Cost With All Transportation:" + InitiateGame.getAssets().getUtilityStayCost());
		utilityLabel.setTooltip(infoTip);
		utilityPrice.setTooltip(infoTip);
		utilityPrice.getStyleClass().add("trans-util-price");
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//						GameEngine.addEventToEngine(EventTypes.ON_UTILITY);
//					}
//				}
//			}
//		});
		utilityPane.getChildren().add(playerBox);
		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("left");
			utilityImage.getStyleClass().add("left");
			utilityLabel.getStyleClass().add("left");
			utilityPrice.getStyleClass().add("left");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityPrice, Pos.CENTER_LEFT);
			utilityImage.setFitHeight(57);
			utilityImage.setFitWidth(60);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("right");
			utilityImage.getStyleClass().add("right");
			utilityLabel.getStyleClass().add("right");
			utilityPrice.getStyleClass().add("right");
			StackPane.setAlignment(utilityImage, Pos.CENTER_LEFT);
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityPrice, Pos.CENTER_RIGHT);
			utilityImage.setFitHeight(57);
			utilityImage.setFitWidth(60);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("top");
			utilityImage.getStyleClass().add("top");
			utilityLabel.getStyleClass().add("top");
			utilityPrice.getStyleClass().add("top");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityImage, Pos.BOTTOM_CENTER);
			StackPane.setAlignment(utilityPrice, Pos.TOP_CENTER);
			utilityImage.setFitHeight(50);
			utilityImage.setFitWidth(50);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			utilityPane.getStyleClass().add("bottom");
			utilityImage.getStyleClass().add("bottom");
			utilityLabel.getStyleClass().add("bottom");
			utilityPrice.getStyleClass().add("bottom");
			StackPane.setAlignment(utilityLabel, Pos.CENTER);
			StackPane.setAlignment(utilityImage, Pos.TOP_CENTER);
			StackPane.setAlignment(utilityPrice, Pos.BOTTOM_CENTER);
			utilityImage.setFitHeight(50);
			utilityImage.setFitWidth(50);
			utilityPane.getChildren().addAll(utilityImage, utilityLabel, utilityPrice);
			this.assignPaneOnGridByCoordinates(col, row, utilityPane);
		}
	}

	public void createWarrantPane(int col, int row) {
		StackPane warrantPane = new StackPane();
		warrantPane.getStyleClass().add("warrant-pane");
		ImageView warrantImage = new ImageView(BoardConsts.IMAGE_URL + "/warrant.png");
		warrantImage.getStyleClass().add("warrant-image");
		warrantImage.setPickOnBounds(true);
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_WARRANT);
//
//					}
//				}
//			}
//		});
		StackPane.setAlignment(warrantImage, Pos.CENTER);
		warrantPane.getChildren().addAll(warrantImage, playerBox);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(50);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("left");
			warrantImage.setRotate(90);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(45);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("right");
			warrantImage.setRotate(270);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(45);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("top");
			warrantImage.setRotate(180);
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			warrantImage.setFitWidth(50);
			warrantImage.setFitHeight(60);
			warrantImage.getStyleClass().add("bottom");
			this.assignPaneOnGridByCoordinates(col, row, warrantPane);
		}
	}

	public void createSurprisePane(int col, int row) {
		StackPane surprisePane = new StackPane();
		surprisePane.getStyleClass().add("warrant-pane");
		ImageView surpriseImage = new ImageView(BoardConsts.IMAGE_URL + "/surprise.png");
		surpriseImage.getStyleClass().add("surprise-image");
		surpriseImage.setPickOnBounds(true);
		FlowPane playerBox = new FlowPane();
		playerBox.setId(this.generatePlayerBoxID(col, row));
		playerBox.setVgap(3);
		playerBox.setHgap(3);
//		playerBox.getChildren().addListener(new ListChangeListener() {
//			@Override
//			public void onChanged(ListChangeListener.Change change) {
//				while (change.next()) {
//					if (change.wasAdded()) {
//
//						GameEngine.addEventToEngine(EventTypes.ON_SUPRISE);
//
//					}
//				}
//			}
//		});
		StackPane.setAlignment(surpriseImage, Pos.CENTER);
		surprisePane.getChildren().addAll(surpriseImage, playerBox);

		if (col == 0) { // LEFT SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(50);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("left");
			surpriseImage.setRotate(90);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);

		} else if (col == 9) { // RIGHT SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(45);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("right");
			surpriseImage.setRotate(270);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		} else if (row == 0) {// UPPER SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(45);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("top");
			surpriseImage.setRotate(180);
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		} else if (row == 9) { // LOWER SIDE OF MONOPOLY BOARD
			surpriseImage.setFitWidth(50);
			surpriseImage.setFitHeight(60);
			surpriseImage.getStyleClass().add("bottom");
			this.assignPaneOnGridByCoordinates(col, row, surprisePane);
		}
	}

	public AnchorPane getRoot() {
		return this.root;
	}

	public void createPlayerPane() {
		this.playerVBox = new VBox();
		playerVBox.setPrefSize(189.0, 768.0);
		AnchorPane.setBottomAnchor(playerVBox, 0.0);
		AnchorPane.setLeftAnchor(playerVBox, 0.0);
		AnchorPane.setRightAnchor(playerVBox, 0.0);
		AnchorPane.setTopAnchor(playerVBox, 0.0);
		this.playersAnchor.getChildren().add(playerVBox);
	}

	public void createPlayerProfile(VBox playersPane, Player player) {
		Pane playerProfile = new Pane();
		playerProfile.setPrefSize(200.0, 128.0);
		playerProfile.setId(player.getPlayerDetails().getName() + "-player");

		ImageView playerAvatar = new ImageView();
		playerAvatar.setImage(player.getPlayerImage());
		playerAvatar.setFitHeight(70.0);
		playerAvatar.setFitWidth(69.0);
		playerAvatar.setLayoutY(7.0);
		playerAvatar.setLayoutX(60);
		playerAvatar.setPickOnBounds(true);
		playerAvatar.setPreserveRatio(true);

		ImageView playerIcon = new ImageView();
		playerIcon.setImage(player.getPlayerIcon());
		playerIcon.setFitHeight(40.0);
		playerIcon.setFitWidth(39.0);
		playerIcon.setLayoutY(10.0);
		playerIcon.setLayoutX(10);
		playerIcon.setPickOnBounds(true);
		playerIcon.setPreserveRatio(true);

		Label playerName = new Label(player.getPlayerDetails().getName());
		playerName.setAlignment(Pos.CENTER);
		playerName.setLayoutX(1.0);
		playerName.setLayoutY(85.0);
		playerName.setPrefSize(187.0, 17.0);

		Label playerMoney = new Label(player.getPlayerDetails().getMoney()+ "$");
		playerMoney.setAlignment(Pos.CENTER);
		playerMoney.setLayoutX(1.0);
		playerMoney.setLayoutY(105.0);
		playerMoney.setPrefSize(187.0, 17.0);

		ImageView playerStatus;
		if (player.getPlayerDetails().getStatus()==PlayerStatus.RETIRED) {
			playerStatus = new ImageView(BoardConsts.IMAGE_URL + "/player-off.png");
		} else {
			playerStatus = new ImageView(BoardConsts.IMAGE_URL + "/player-on.png");
		}
		playerStatus.setFitHeight(17.0);
		playerStatus.setFitWidth(15.0);
		playerStatus.setLayoutX(169.0);
		playerStatus.setLayoutY(4.0);
		playerStatus.setId(player.getPlayerDetails().getName() + "-status");
		playerStatus.setPickOnBounds(true);
		playerStatus.setPreserveRatio(true);

		playerProfile.getChildren().addAll(playerAvatar, playerIcon, playerName, playerMoney, playerStatus);
		playersPane.getChildren().add(playerProfile);

	}

	public AnchorPane getPlayersAnchorPane() {
		return this.playersAnchor;
	}

	public AnchorPane getGridAnchor() {
		return this.gridAnchor;
	}

	public Node getCellByPosition(int location) {
		int col = PositionHelper.arr.get(location).getCol();
		int row = PositionHelper.arr.get(location).getRow();
		return this.getNodeByRowColumnIndex(row, col);

	}

	public Node getNodeByRowColumnIndex(final int row, final int column) {
		Node result = null;
		ObservableList<Node> childrens = this.boardPane.getChildren();
		for (Node node : childrens) {
			if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
				result = node;
				break;
			}
		}
		return result;
	}

	public FlowPane getPlayerBox(int location) {
		Node node = this.getCellByPosition(location);
		int col = PositionHelper.arr.get(location).getCol();
		int row = PositionHelper.arr.get(location).getRow();
		Parent tempParent = (Parent) node;
		if (tempParent.getClass().getSimpleName().equals("SplitPane")) {
			SplitPane pane = (SplitPane) tempParent;
			for (Node citytemp : pane.getItems()) {
				if (citytemp.getClass().getSimpleName().equals("StackPane")) {
					for (Node theNode : ((StackPane) citytemp).getChildren()) {
						if (theNode.getId() != null && theNode.getId().equals(this.generatePlayerBoxID(col, row)))
							return (FlowPane) theNode;
					}
				}
			}

		} else {
			Pane cellPane = (Pane) tempParent;
			for (Node runNode : cellPane.getChildrenUnmodifiable()) {
				if (runNode.getId() != null && runNode.getId().equals(this.generatePlayerBoxID(col, row))) {
					return (FlowPane) runNode;
				}
			}
		}

		return null;
	}

	public FlowPane getHouseBox(int location) {
		Node node = this.getCellByPosition(location);
		int col = PositionHelper.arr.get(location).getCol();
		int row = PositionHelper.arr.get(location).getRow();
		Parent tempParent = (Parent) node;
		if (tempParent.getClass().getSimpleName().equals("SplitPane")) {
			SplitPane pane = (SplitPane) tempParent;
			for (Node citytemp : pane.getItems()) {
				if (citytemp.getId() != null && citytemp.getId().equals(this.generateHoseBoxID(col, row)))
					return (FlowPane) citytemp;
			}
		}
		return null;
	}

	public String generateHoseBoxID(int col, int row) {
		return "houseBox-" + new Integer(col).toString() + new Integer(row).toString(); // eden
	}

	public String generatePlayerBoxID(int col, int row) {
		return "playerbox-" + new Integer(col).toString() + new Integer(row).toString();
	}

	public Label getScreenConsole() {
		return this.screenConsole;
	}

	public Button getRollButton() {
		return this.rollButton;
	}

	public Pane getDicePane() {
		return this.dicePane;
	}

	public VBox getPlayersVBox() {
		return this.playerVBox;
	}
}
