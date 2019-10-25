package rusafe;

import java.net.*;
import java.io.*;
import java.io.PrintWriter;
import java.util.regex.*;
import java.util.ArrayList;

public class GetURL {
	private ArrayList<String> places;

	public GetURL() {

	}
	
	public static String getData() {
		System.out.println("get data called");
		ArrayList<String> places = new ArrayList<String>();
		String base = "https://local.nixle.com/rutgers-police-department/";
		for (int i = 1; i <= 10; i++) {
			places.addAll(printPages(base + "?page=" + i));
		}

		return getTxt(places);
	}


	private static ArrayList<String> printPages(String url) {
		String x = new String(JavaUrlConnectionReader.getUrlContents(url));
		// System.out.println(x);
		Pattern p = Pattern.compile("Crime Alert.*?/alert/\\d*/");
		Pattern p2 = Pattern.compile("/alert/\\d*/");
		Matcher m = p.matcher(x);
		ArrayList<String> sites = new ArrayList<String>();

		while (m.find()) {
			Matcher m2 = p2.matcher(m.group());
			m2.find();
			sites.add(m2.group());
		}
		String base = "https://local.nixle.com";
		for (int i = 0; i < sites.size(); i++) {
			sites.set(i, base + sites.get(i));
			// System.out.println(sites.get(i));
		}
		String text = new String();
		for (String s : sites) {
			text += JavaUrlConnectionReader.getUrlContents(s);
		}
		return (getPlaces(text));
	}

	private static ArrayList<String> getPlaces(String text) {
		Pattern p = Pattern.compile("<br\\s/>.*?\\n");
		Matcher m = p.matcher(text);
		String br = "";
		while (m.find()) {
			br += m.group();
		}
		ArrayList<String> places = new ArrayList<String>();
		Pattern p2 = Pattern.compile(
				"(([A-Z]\\w*)+(\\s(Street)|(Streets)|(Avenue)|(Road)|(Court)|(Drive)|(Place)|(Circle)|(Square)|(Row)|(Way)|(Boulevard)|(Lane)|(Cres)|(Pkwy)|(Parkway)|(Crescent))*\\sand\\s)*([A-Z]\\w*\\s)+((Street)|(Streets)|(Avenue)|(Road)|(Court)|(Drive)|(Place)|(Circle)|(Square)|(Row)|(Way)|(Boulevard)|(Lane)|(Cres)|(Pkwy)|(Parkway)|(Crescent))");
		Matcher m2 = p2.matcher(br);
		while (m2.find()) {
			places.add(m2.group());
		}
		return (places);
	}

	private static void writeTxt(ArrayList<String> list) {
		try {
			FileWriter txt = new FileWriter("places.txt");
			for (String s : list) {
				if (s.contains(" and")) {
					s = s.substring(s.indexOf(" and") + 5);
				}
				txt.append(s + "\n");
			}
			txt.close();
		} catch (IOException e) {
			System.out.println("IOException");
		}
	}

	private static String getTxt(ArrayList<String> list)
    {
            String txt = "";
            for(String s : list)
            {
                if(s.contains(" and"))
                {
                    s = s.substring(s.indexOf(" and")+5);
                }
                txt += s+",";
            }
            return txt;
    }
}