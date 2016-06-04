package com.monopoly.client.engine;

import java.util.LinkedList;

import com.monopoly.data.Assets;
import com.monopoly.data.BoardData;
import com.monopoly.data.CellData;
import com.monopoly.data.City;
import com.monopoly.data.CountryGame;
import com.monopoly.data.Transportation;
import com.monopoly.data.Utility;
import com.monopoly.scenes.MainBoard;
import com.monopoly.utility.CountryColors;
import com.monopoly.utility.PositionHelper;

public class GameBoard {

    private BoardData boardData;
    private Assets gameAssets;
    private MainBoard mainBoard;

    public GameBoard(MainBoard mainBoard) {
        this.mainBoard = mainBoard;
    }

    public void loadTheBoard() {
        boardData = InitiateGame.getBoardData();
        gameAssets = InitiateGame.getAssets();
        LinkedList<CountryGame> theCountries = gameAssets.getTheCountries();
        LinkedList<Transportation> transportation = gameAssets.getTransportation();
        LinkedList<Utility> utilities = gameAssets.getUtility();
        this.LoadDataOnCells(theCountries, transportation, utilities);

    }

    private void LoadDataOnCells(LinkedList<CountryGame> theCountries, LinkedList<Transportation> transportation,
            LinkedList<Utility> utilities) {
        int placeOnBoard = 0;
        int countryCounter = 0, cityCounter = 0;
        int transPlace = 0;
        int utilityPlace = 0;
        int suprisePlace = 0;
        int warrantPlace = 0;
        LinkedList<CellData> cellList = boardData.getTheBoard();
        PositionHelper newHelper = new PositionHelper();

        mainBoard.createBoardLogo();
        for (CellData dataCell : cellList) {
            String cellType = dataCell.getType();
            switch (cellType) {
                case "Start Square":
                    mainBoard.createStartPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    placeOnBoard++;
                    break;
                case "CITY":
                    if (cityCounter >= theCountries.get(countryCounter).getCitiesNum()) {
                        countryCounter++;
                        cityCounter = 0;
                    }   City cityToData = theCountries.get(countryCounter).getCities().get(cityCounter);
                    mainBoard.createCityPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow(), cityToData, CountryColors.countryArr[countryCounter]);
                    cityCounter++;
                    placeOnBoard++;
                    break;
                case "SURPRISE":
                    mainBoard.createSurprisePane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    suprisePlace++;
                    placeOnBoard++;
                    break;
                case "WARRANT":
                    mainBoard.createWarrantPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    warrantPlace++;
                    placeOnBoard++;
                    break;
                case "TRANSPORTATION":
                    mainBoard.createTransportationPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow(), transportation.get(transPlace));
                    placeOnBoard++;
                    transPlace++;
                    break;
                case "GotoJail":
                    mainBoard.createGoToJailPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    placeOnBoard++;
                    break;
                case "Parking":
                    mainBoard.createFreeParkingPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    placeOnBoard++;
                    break;
                case "UTILITY":
                    mainBoard.createUtilityPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow(), utilities.get(utilityPlace));
                    placeOnBoard++;
                    utilityPlace++;
                    break;
                case "Jail":
                    mainBoard.createJailPane(PositionHelper.arr.get(placeOnBoard).getCol(),
                            PositionHelper.arr.get(placeOnBoard).getRow());
                    placeOnBoard++;
                    break;
                default:
                    break;
            }
        }

    }

}
