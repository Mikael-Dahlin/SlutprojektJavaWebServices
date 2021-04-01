package myscv.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import org.springframework.core.io.ClassPathResource;

/*
 * Class that handles the logic, reading the CSV-file and print out JSON.
 */
public class readCSV {

	private static String COMMA_DELIMITER = ",";

	private static ArrayList<CsvRow> wholeSheet = new ArrayList<>();
	private static ArrayList<CsvRow> sortedSheet = new ArrayList<>();
	private static ArrayList<CsvRow> errorsSheet = new ArrayList<>();
	private static ArrayList<String> sheetHeaders = new ArrayList<String>();

	/*
	 * Method for printing the base sheet.
	 */
	public static String getWholeSheet() {
		// Loads the CSV-file if it is the first method called.
		loadCSV();
		return printJSON(wholeSheet, sheetHeaders);
	}

	/*
	 * Method that reads the CSV-file.
	 */
	public static void loadCSV() {
		// Skips if the file has been read already.
		if (wholeSheet.isEmpty()) {
			
			var csvFile = new ClassPathResource("sample.csv");
		
			try (Scanner scanner = new Scanner(csvFile.getFile());) {
				int i = 0;
				while (scanner.hasNextLine()) {
					getRows(scanner.nextLine(), i);
					i++;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Method that handles each row of the file.
	 */
	private static void getRows(String row, int i) {
		// Scans the row and puts the values into an ArrayList based on the delimiter.
		ArrayList<String> rowValues = new ArrayList<String>();
		try (Scanner rowScanner = new Scanner(row)) {
			rowScanner.useDelimiter(COMMA_DELIMITER);

			rowValues.add(String.valueOf(i));
			while (rowScanner.hasNext()) {
				String currentCell = rowScanner.next();				
				rowValues.add(currentCell);
			} 
		}
		
		// First row is turned into a headers array.
		if (i == 0) {
			sheetHeaders.addAll(rowValues);
			sheetHeaders.set(0, "RowNumber");
			for (String header : sheetHeaders) {
				sheetHeaders.set(sheetHeaders.indexOf(header), header.toLowerCase());
			}
		} else {
			// All other rows gets put as an Object in the wholeSheet ArrayList.
			wholeSheet.add(new CsvRow(rowValues));
		}
	}

	/*
	 * Method for printing selected columns.
	 */
	public static String getColumns(String[] headers, String sorted) {
		// Loads the CSV-file if it is the first method called.
		loadCSV();
		
		// Get selected headers from the headers parameter.
		ArrayList<String> selectedHeaders = new ArrayList<String>();
		for(String header: headers) {
			selectedHeaders.add(header.toLowerCase());
		}
		
		// Checks which list to print, base sheet or a sorted sheet.
		if (sorted.equals("sorted")) {
			return printJSON(sortedSheet, selectedHeaders);	
		} else {
			return printJSON(wholeSheet, selectedHeaders);			
		}
	}

	/*
	 * Method for finding the rows with price errors.
	 */
	public static String getErrorsSheet() {
		// Loads the CSV-file if it is the first method called.
		loadCSV();
		if (errorsSheet.isEmpty()) {
			for(CsvRow row: wholeSheet) {
				if (!row.checkPrice()) {
					errorsSheet.add(row);
				}
			}			
		}
		return printJSON(errorsSheet, sheetHeaders);
	}
	
	/*
	 * Method for sorting the sheet.
	 */
	public static String sortedCSV(String header, String selected) {
		// Loads the CSV-file if it is the first method called.
		loadCSV();
		
		if(sortedSheet.isEmpty()) {			
			sortedSheet.addAll(wholeSheet);
		}
		
		// Switch case to catch the different object types and sort accordingly.
		switch (header.toLowerCase()) {
			case "rownumber":
				sortedSheet.sort((CsvRow o1, CsvRow o2) -> 
								((Integer) o1.get(sheetHeaders.indexOf(header.toLowerCase())))
								.compareTo((Integer) o2.get(sheetHeaders.indexOf(header.toLowerCase()))));
				break;
			case "orderdate":
				sortedSheet.sort((CsvRow o1, CsvRow o2) -> 
								((LocalDate) o1.get(sheetHeaders.indexOf(header.toLowerCase())))
								.compareTo((LocalDate) o2.get(sheetHeaders.indexOf(header.toLowerCase()))));
				break;
			case "units":
			case "unitcost":
			case "total":
				sortedSheet.sort((CsvRow o1, CsvRow o2) -> 
								((Double) o1.get(sheetHeaders.indexOf(header.toLowerCase())))
								.compareTo((Double) o2.get(sheetHeaders.indexOf(header.toLowerCase()))));
				break;
			default:
				sortedSheet.sort((CsvRow o1, CsvRow o2) -> 
								((String) o1.get(sheetHeaders.indexOf(header.toLowerCase())))
								.compareTo((String) o2.get(sheetHeaders.indexOf(header.toLowerCase()))));
				break;
		}
		
		// Prints the whole sheet or just selected column.
		if (selected.equals("all")) {
			return printJSON(sortedSheet, sheetHeaders);
		} else {
			return getColumns(new String[] {selected} , "sorted");
		}
	}

	/*
	 * Method for printing out JSON, takes a sheet to print and which headers should be included.
	 */
	public static String printJSON(ArrayList<CsvRow> sheet, ArrayList<String> headers) {
		String json = "{\"rows\": [";
		
		for (int i = 0; i < sheet.size(); i++) {
			json += "{";
			for (int j = 0; j < headers.size(); j++) {
				 json += "\"" + headers.get(j) + "\": \"" + sheet.get(i).get(sheetHeaders.indexOf(headers.get(j))) + "\"";
				 if (j < headers.size()-1) {
					 json += ",";
				 }
			}
			json += "}";
			if (i < sheet.size()-1) {
				json += ",";
			}
			
		}
		return json +=  "]}";
	}
}
