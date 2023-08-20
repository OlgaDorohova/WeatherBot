
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;


import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;




public class Weather {
	
	public static String getWeather(String message, long chatId, Model model) throws IOException{
		
		URL url  = new URL("https://api.openweathermap.org/data/2.5/weather?q="+
		message+"&units=metric&lang=ru&appid=286c0568aa7a808f18c29d3a8df7c062");
		
		Scanner in = new Scanner((InputStream)url.getContent());
		
		String result = "";
		while(in.hasNext()) {
			result += in.nextLine();
		}
		
		
		
		JSONObject object = new JSONObject(result);
		model.setName(object.getString("name"));
		
		JSONObject main = object.getJSONObject("main");
		model.setTemp(main.getDouble("temp"));
		model.setHimidity(main.getDouble("humidity"));
		
		JSONArray getArray = object.getJSONArray("weather");
		for(int i = 0; i < getArray.length(); i++) {
			
			JSONObject obj = getArray.getJSONObject(i);
			model.setIcon((String)obj.get("icon"));
			model.setMain((String)obj.get("description"));
			
			
		}
		
		
		
		return 
				"Город: " + model.getName() + "\n" +
				"Температура: " + model.getTemp() + " C" + "\n" +
				"Влажность: " + model.getHimidity() + " %" + "\n" +
				"Облачность: " + model.getMain() + "\n"	+ 
				"http://openweathermap.org/img/wn/" + model.getIcon()  + ".png"
				;
				//;
				
				
		
			
		}
				


	

	
}
