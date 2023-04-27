package MathDoku;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Grid {

    private ArrayList<Cage> cages = new ArrayList<>();
    private Cell[][] cells;

    private LinkedHashSet<Integer> changedCells = new LinkedHashSet<>();
    private LinkedHashSet<Cell> removedCells = new LinkedHashSet<>();

    private HashSet<Integer> wrongCol;
    private HashSet<Integer> wrongRow;
    private ArrayList<Cage> wrongCage;

    private int size;
    private boolean win = false;

    public Grid() {
        this.size = 0;
    }

    public int getSize() {
        return size;
    }

    public Cell[][] getCells() {
        return cells;
    }

    public void addChangedCells(int cellID){
        changedCells.add(cellID);
        if (changedCells.size() == size * size){
            checkForWin();
        }
    }

    public void removeChangedCells(int cellID){
        changedCells.remove(cellID);
    }

    public LinkedHashSet<Integer> getChangedCells() {
        return changedCells;
    }

    public LinkedHashSet<Cell> getRemovedCells() {
        return removedCells;
    }

    public void changeColour(String s){
        if (s.equals("Win") || s.equals("Casual")){
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    cells[i][j].setColor(s);
                }
            }
        }
        if (s.equals("Mistakes")){
            for (Cage cage : wrongCage) {
                cage.setColour(s);
            }
            for (Integer j : wrongRow) {
                for (int i = 0; i < size; i++) {
                    cells[i][j].setColor(s);
                }
            }
            for (Integer j : wrongCol) {
                for (int i = 0; i < size; i++) {
                    cells[j][i].setColor(s);
                }
            }
        }
    }

    public void  checkForWin(){
        win = false;
        changeColour("Casual");

        wrongCage = new ArrayList<>();
        wrongCol = new HashSet<>();
        wrongRow = new HashSet<>();

        int[] allowedNum = new int[size];

        for (int i = 0; i < size; i++) {
            Arrays.fill(allowedNum,0);
            for(int j=0; j<size; j++) {
                try {
                    allowedNum[cells[i][j].getValue()-1] += 1;
                } catch(ArrayIndexOutOfBoundsException e) {
                    wrongCol.add(i);
                }
            }
            for(int j=0; j<size; j++) {
                if(allowedNum[j] != 1) {
                    wrongCol.add(i);
                }
            }
        }
        for(int i = 0; i < size; i++) {
            Arrays.fill(allowedNum, 0);
            for(int j = 0; j < size; j++) {
                try {
                    allowedNum[cells[j][i].getValue()-1] += 1;
                } catch(ArrayIndexOutOfBoundsException e) {
                    wrongRow.add(i);
                }
            }
            for(int j = 0; j < size; j++) {
                if(allowedNum[j] != 1) {
                    wrongRow.add(i);
                }
            }
        }

        for (Cage cage : cages) {
            if (!cage.isValid()){
                wrongCage.add(cage);
            }
        }

        if (wrongCol.size() == 0 && wrongRow.size() == 0 && wrongCage.size() == 0){
            win = true;
            changeColour("Win");
        }
    }
    public void clear(){
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                cells[j][i].setValue("0");
                cells[j][i].setField("");
            }
        }
    }

    public boolean checkRow(int row){
        int[] numInRow = new int[size];
        Arrays.fill(numInRow,0);

        int num = 0;
        while(num < size && cells[row][num].getValue() != 0) {
            numInRow[cells[row][num].getValue()-1] += 1;
            num++;
        }
        if(num == size) {
            for(int i = 0; i < size; i++) {
                if(numInRow[i] != 1) {
                    return false;
                }
            }
        } else {
            for(int i = 0; i < size; i++) {
                if(numInRow[i] == 2) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkCol(int column) {
        int[] numInCol = new int[size];
        Arrays.fill(numInCol, 0);

        int num = 0;
        while(num < size && cells[num][column].getValue() != 0) {
            numInCol[cells[num][column].getValue()-1] += 1;
            num++;
        }
        if(num == size) {
            for(num = 0; num < size; num++) {
                if(numInCol[num] != 1) {
                    return false;
                }
            }
        } else {
            for(num = 0; num < size; num++) {
                if(numInCol[num] == 2) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkCage(Cage cage) {
        boolean emptyCell = false;

        for(Cell cell : cage.getCells()) {
            if(cell.getValue() == 0) {
                emptyCell = true;
            }
        }
        if(emptyCell) {
            return true;
        }

        return cage.isValid();
    }

    public void solve() throws Exception{
        boolean success;

        int num = 0;
        for (int i = 0; i < size; i++) {
            num %= size;
            for (; num < size; num++) {
                success = false;
                for (int k = (cells[i][num].getValue() + 1); k <= size; k++) {
                    cells[i][num].setValue(String.valueOf(k));
                    cells[i][num].setField(String.valueOf(k));
                    if (checkRow(i) && checkCol(num) && checkCage(cells[i][num].getCage())) {
                        success = true;
                        break;
                    }
                }
                if (!success) {
                    if (i == 0 && num == 0) {
                        throw new Exception("I couldn't solve it");
                    }
                    cells[i][num].setValue("0");
                    cells[i][num].setField("0");
                    num -= 2;
                    if (num < -1) {
                        i -= 2;
                        num = size - 1;
                        break;
                    }
                }
            }
        }
    }

    public void generateGrid(int size) {
        this.size = size;
        cells = new Cell[size][size];
    }

    public void undo() throws CloneNotSupportedException{
        if(changedCells.size() != 0) {
            int lastId = (int) (changedCells.toArray()[changedCells.size() - 1]) - 1;
            removedCells.add((Cell) cells[lastId / size][lastId % size].clone());
            cells[lastId / size][lastId % size].setValue("0");
            cells[lastId / size][lastId % size].setField("");
        }
    }

    public void redo(){
        if(removedCells.size() != 0) {
            Cell removedCell = (Cell) (removedCells.toArray()[removedCells.size()-1]);
            int lastId = removedCell.getCellID() - 1;
            cells[lastId / size][lastId % size].setValue(String.valueOf(removedCell.getValue()));
            cells[lastId / size][lastId % size].setField(removedCell.getText());
            removedCells.remove(removedCell);
        }
    }

    public boolean ValidInput(String loadedText) {
        loadedText = loadedText.replaceAll("\n", " ");
        String[] stringContents = loadedText.split( " ");
        int size = 0;
        Integer[] sizes = new Integer[] {4,9,16,25,36,49,64};
        HashSet<Integer> cellsIdsToConfirm = new HashSet<Integer>();

        for(int i = 0; i < stringContents.length; i++) {
            if(i % 2 == 0) {
                if(!stringContents[i].matches("^[1-9][0-9]*[x÷+\\-]?")) {
                    return false;
                }
            } else {
                String[] cellsIds = stringContents[i].split(",");
                size += cellsIds.length;
                for(int j=0; j < cellsIds.length; j++) {
                    cellsIdsToConfirm.add(Integer.parseInt(cellsIds[j]));
                    if(!cellsIds[j].matches("^[1-9][0-9]*$")) {
                        return false;
                    }
                }
            }
        }
        if(!Arrays.asList(sizes).contains(size)) {
            return false;
        }
        if(cellsIdsToConfirm.size() != size) {
            return false;
        }
        for(int x : cellsIdsToConfirm) {
            if(x < 1 || x > size) {
                return false;
            }
        }
        return true;
    }

    public void readFromString(String textInput) {
        double colour1 = 0;
        double colour2 = 0;
        double colour3 = 0;

        cages.clear();
        changedCells.clear();
        removedCells.clear();

        Label cageLabel = new Label();
        textInput = textInput.replaceAll("\n", " ");
        String[] stringContents = textInput.split(" ");

        for (int i = 0; i < stringContents.length; i++) {
            if (i % 2 == 0) {
                cageLabel = new Label(stringContents[i]);
                colour1 = (colour1 + 0.10) % 1;
                colour3 = (colour3 + 0.5) % 1;
                colour2 = (colour2 + 0.2) % 1;
            } else {
                String[] cellID = stringContents[i].split(",");
                size += cellID.length;
                cages.add(new Cage(cageLabel, cellID, new Color(colour1, colour2, colour3, 1)));
            }
        }
        size = (int) Math.sqrt(size);
        cells = new Cell[size][size];

        for (int i = 0; i < cages.size(); i++) {
            ArrayList<Cell> currentCage = cages.get(i).getCells();
            for (int j = 0; j < currentCage.size(); j++) {
                int cellId = currentCage.get(j).getCellID();
                cells[(cellId - 1) / size][(cellId - 1) % size] = currentCage.get(j);
            }
        }
    }
}
