package edu.calvin.csw61.finalproject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
	String fileName, output;
	
	public ReadFile(String file){
		this.fileName = file;
		output = "";
	}
	public void readAndPrint(){
		read();
		System.out.println(getContents());
	}
	
	//read in the file and set it to the output string
	public void read() {
		String line = null;
		fileName = "src/edu/calvin/csw61/finalproject/text_files/" + fileName;
		
		try{
			FileReader fr = new FileReader(fileName); //FileReader reads text files in the default encoding.
			BufferedReader br = new BufferedReader(fr); //Always wrap FileReader in BufferedReader.
			while((line = br.readLine()) != null) { //read in each line
				output += line + "\n";			//set the description to the read lines, and add a newline after ever line
			}
			br.close(); //close the file
		}
		catch(FileNotFoundException ex) {
			System.out.println("Unable to open file.");
		
		}
		catch(IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public String getContents() {
		return output;
	}
}
