import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;


public class GetSearchResult {
	
	public static void main(String[] args) throws Exception {
		
		String[] srchQueries = new String[1000];
		Collection<String> setSR = new HashSet<String>();
		FileReader read=new FileReader("...input.txt");     
		BufferedReader reader=new BufferedReader(read); 
        	String line;
		int j=0; 
        
		while ((line=reader.readLine())!=null) {
        		srchQueries[j]=line;
            		j++;
        	}
        
		String key="API key";
		
		for(int k=0;k<100;k++){  // Custom search API (free edition) provides 100 search queries per day
			if(srchQueries[k]!=null){
				String query=srchQueries[k].trim();
				URL url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+"&cx=013036536707430787589:_pqjad5hr1a&q="+ query +"&alt=json");
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.setRequestProperty("Accept", "application/json");
				BufferedReader buffRead = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				String result;
			
				while ((result = buffRead.readLine()) != null) {
					if(result.contains("\"link\": \"")){                
						String link=result.substring(result.indexOf("\"link\": \"")+("\"link\": \"").length(), result.indexOf("\","));
                				setSR.add(link);
                			} 
				}
				connection.disconnect();
        
				Iterator iterator_1 = setSR.iterator();
				PrintStream out = new PrintStream(new FileOutputStream("...SearchResults.txt"));
				Iterator iterator_2 = setSR.iterator();
				while(iterator_2.hasNext()){
					out.println(iterator_2.next());
				}
			}
		}
	}
}
