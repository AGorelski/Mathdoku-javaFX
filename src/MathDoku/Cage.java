package MathDoku;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Collections;

class Cage {
    private Label label;

    ArrayList<Cell> cells = new ArrayList<>();

    public Cage(Label cageLabel, String[] cellsIds, Color colour) {
        this.label = cageLabel;
        for(int i = 0; i < cellsIds.length; i++) {
            if(i == 0) {
                cells.add(new Cell(cageLabel,Integer.parseInt(cellsIds[i]), colour, this));
            } else {
                cells.add(new Cell(Integer.parseInt(cellsIds[i]), colour, this));
            }
        }
    }

    public Label getCageLabel() {
        return label;
    }

    public void setColour(String s){
        if (s.equals("Mistakes")){
            for (Cell cell : cells) {
                cell.setColor("Mistakes");
            }
        }else if (s.equals("Casual")){
            for (Cell cell : cells) {
                cell.setColor("Casual");
            }
        }
    }

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public boolean isValid(){
        String targetNum = label.getText().substring(0,label.getText().length() - 1);
        String operator = label.getText().substring(label.getText().length() - 1);

        if (operator.equals("+")){
            int sum = 0;
            for(Cell cell : cells) {
                sum += cell.getValue();
            }
            if(sum != Integer.parseInt(targetNum)) {
                return false;
            }
        }else if (operator.equals("*")){
            int sum = 0;
            for (Cell cell : cells) {
                sum *= cell.getValue();
            }
            if (sum != Integer.parseInt(targetNum)){
                return false;
            }
        }else if (operator.equals("÷")){
            ArrayList<Integer> cellValues = new ArrayList<>();
            for(int i=0; i< cells.size(); i++) {
                cellValues.add(cells.get(i).getValue());
            }
            Integer maxValue = Collections.max(cellValues);
            cellValues.remove(maxValue);
            int restValuesProduct = 1;
            for(int i=0; i < cellValues.size(); i++) {
                restValuesProduct *= cellValues.get(i);
            }
            if(maxValue / restValuesProduct != Integer.parseInt(targetNum)) {
                return false;
            }
        }else if (operator.equals("-")){
            ArrayList<Integer> cellValues = new ArrayList<>();
            for (int i = 0; i < cells.size(); i++) {
                cellValues.add(cells.get(i).getValue());
            }
            Integer maxValue = Collections.max(cellValues);
            cellValues.remove(maxValue);
            int restValuesSum = 1;
            for (int i = 0; i < cellValues.size(); i++) {
                restValuesSum += cellValues.get(i);
            }
            if (maxValue - restValuesSum != Integer.parseInt(targetNum)){
                return false;
            }
        }
        return true;
    }
}
