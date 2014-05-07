import java.util.*;
import java.io.*;

public class BiGramFrequency{
	
	String bigram, inputFilePath,outputFilePath;
	String delims = "( ),;.'\" “” ?";
	countBigram[] result;
	int totalToken=0;
	BufferedReader inputFile, outputFile;
	File file;
	static BufferedReader inBuf = new BufferedReader(new InputStreamReader(System.in));
	static BufferedReader outBuf = new BufferedReader(new InputStreamReader(System.in));
	Hashtable<Object, Object> table;
	Enumeration<Object> key,data;
	
	
	//Main
	 	
	public static void main(String[] args){
		
		BiGramFrequency BiGram = new BiGramFrequency();
	 	BiGram.generateBigram();
	 	//System.out.println("Bigrams generated");
	 	BiGram.calculateFrequency();
	 	//System.out.println("Frequency Generated");
	 	BiGram.GenerateOutput();
	 	System.out.println("Output Generated");
	 }
	
	 
 	//C:\Users\Rasel\workspace\NLP1\src\a.txt
 		
 	// takes input of the file, tokenize the words of two and counts the bigrams
	void generateBigram(){
		
		table = new Hashtable<Object, Object>();
		String line = "";
		StringTokenizer token;
		String token1 = "";
		String token2 = "";
			
		try{
			
			System.out.println("Enter Input File Path:");
			inputFilePath = inBuf.readLine();
			
			
			inputFile = new BufferedReader(new FileReader(inputFilePath));
			line = inputFile.readLine();
			while(line != null){
				token = new StringTokenizer(line,delims );
				if (token.hasMoreTokens()){
					token1 = token.nextToken();
				}
				while(token.hasMoreTokens()){
					token2 = token.nextToken();				
					bigram = token1 + " " + token2;
					Object item = table.get(bigram);
					if (item != null)
						((int[])item)[0]++;
					else{
						int[] count = {1};
						table.put(bigram,count);
					}
					token1 = token2;
					totalToken++;
				}
				totalToken++;			
				line = inputFile.readLine();
			}
		}
		catch (Exception e){ 
			System.out.println("PLEASE CHECK FILE PATH" + e.getMessage());
			
		}
	}
	
	//countBigrams class
	class countBigram{
		int count;
		String bigram;
		
		countBigram(int count, String bigram){
			this.count = count;
		 	this.bigram = bigram;
		}
	}
	
	// transfers the hashtable values to an array 	 	
	void calculateFrequency(){
		key = table.keys();
		data= table.elements();
		int index = 0;
		result = new countBigram[table.size()];
						
		while(key.hasMoreElements()){
			data.hasMoreElements();
			result[index++] = new countBigram(((int[])data.nextElement())[0],(String)key.nextElement());
		}
	} 
	
	// sorts the result with the frequency and prints in the file			
	void GenerateOutput(){
		Comparator<Object> compare = new Comparator<Object>(){
			public int compare(Object obj1, Object obj2){
				int i1 = ((countBigram)obj1).count;
				int i2 = ((countBigram)obj2).count;
				if(i1 == i2)
					return 0;
				else if (i1>i2)
					return -1;
				else
					return 1;
			}	
		};
					
		Arrays.sort(result,compare);
				
		try{
			System.out.println("Enter Output File path:");
			outputFilePath = outBuf.readLine();
			file = new File(outputFilePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write("Total words  : " + totalToken);
			bw.newLine();
			bw.write("Total bigrams: " + table.size());
			bw.newLine();
			if(result.length > 50)
				for(int i=0; i < 50; i++){
					bw.write(result[i].bigram +"\t \t"+ result[i].count );
					bw.newLine();
				}
			else
				for(int i=0; i < result.length; i++){
					bw.write(result[i].bigram +"\t \t"+ result[i].count );
					bw.newLine();
				}
			bw.close();
		
		}
		catch(Exception e){
			System.out.println("Something went wrong"+e.getMessage());			
		}
	} 
		
}