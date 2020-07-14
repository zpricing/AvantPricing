package cl.zpricing.avant.test.external;

import java.io.IOException;
import java.util.Map;
import java.util.StringTokenizer;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import junit.framework.TestCase;

public class OmdbTest extends TestCase {
	
	public void testRestService() throws JsonParseException, JsonMappingException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		String response = restTemplate.getForObject("http://www.omdbapi.com/?t=shrek 2", String.class);
		
		System.out.println(response);
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> movieData = mapper.readValue(response, Map.class);
		
		if (movieData.get("Response").equalsIgnoreCase("True")) {
    		StringTokenizer tokenizer = new StringTokenizer(movieData.get("Genre"), ", ");
        	
        	while(tokenizer.hasMoreElements()) {
        		System.out.println("->" + tokenizer.nextToken() + "<-");
        	}
    	}
    	else {
    		System.out.println("error: " + movieData.get("Error"));
    	}
	}
}
