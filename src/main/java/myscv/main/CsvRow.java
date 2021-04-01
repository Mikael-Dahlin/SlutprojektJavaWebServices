package myscv.main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/*
 * Class that holds all the row data.
 */
public class CsvRow {

	// Private class variables.
	private int RowNumber = 0;
	private LocalDate OrderDate = null;
	private String Region = "";
	private String Rep1 = "";
	private String Rep2 = "";
	private String Item = "";
	private double Units = 0.0;
	private double UnitCost = 0.0;
	private double Total = 0.0;

	/*
	 * Constructor that takes in the row as an ArrayList of strings.
	 */
	public CsvRow(ArrayList<String> rowValues) {
		// Formatter for reading the date.
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");

		// Parsing the variables to their correct object types.
		RowNumber = Integer.parseInt(rowValues.get(0));
		OrderDate = LocalDate.parse(rowValues.get(1), formatter);
		Region = rowValues.get(2);
		Rep1 = rowValues.get(3);
		Rep2 = rowValues.get(4);
		Item = rowValues.get(5);
		Units = Double.parseDouble(rowValues.get(6));
		UnitCost = Double.parseDouble(rowValues.get(7));
		Total =  Double.parseDouble(rowValues.get(8));
	}
	
	/*
	 * Check if Units * UnitCost is the same as Total, with 2 decimals.
	 */
	public boolean checkPrice() {
		return (Math.round(Units * UnitCost * 100)/100 == Math.round(Total * 100)/100);
	}

	/*
	 * Getters.
	 */
	public int getRowNumber() {
		return RowNumber;
	}

	public LocalDate getOrderDate() {
		return OrderDate;
	}

	public String getRegion() {
		return Region;
	}

	public String getRep1() {
		return Rep1;
	}

	public String getRep2() {
		return Rep2;
	}

	public String getItem() {
		return Item;
	}

	public double getUnits() {
		return Units;
	}

	public double getUnitCost() {
		return UnitCost;
	}

	public double getTotal() {
		return Total;
	}
	
	/*
	 * Custom get method for getting the data with an index.
	 */
	public Object get(int i) {
		Object obj = "";
		
		switch(i) {
			case 0:
				obj = getRowNumber();
				break;
			case 1:
				obj = getOrderDate();
				break;
			case 2:
				obj = getRegion();
				break;
			case 3:
				obj = getRep1();
				break;
			case 4:
				obj = getRep2();
				break;
			case 5:
				obj = getItem();
				break;
			case 6:
				obj = getUnits();
				break;
			case 7:
				obj = getUnitCost();
				break;
			case 8:
				obj = getTotal();
				break;
		}
		
		return obj;
	}	
}
