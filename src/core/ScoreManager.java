package core;

import global.Score;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public abstract class ScoreManager {

	private static ArrayList<Score> score = new ArrayList<>(); 
	
	private static final int maxScoreCount = 3;
	
	//private static final String scoreSplit = ";";
	
	static private Preferences prefs = init();
			
	static private Preferences init() {
		Preferences p = Preferences.userRoot().node(ScoreManager.class.getClass().getName());
		try {
			try {
				for(String k: p.keys()) {
					String[] u = p.get(k, null).split(";", 2);
					System.out.println("Loading score: "+u[0]+" - "+(u[1]));
					score.add(new Score(u[0], Integer.parseInt(u[1])));
				}
			} catch (NumberFormatException e)  {
				System.err.println("Couldn't read saved score, clearing...");
				p.clear();
			} catch (ArrayIndexOutOfBoundsException e) {
				System.err.println("Couldn't read saved score, clearing...");
				p.clear();
			}
		} catch (BackingStoreException e) {
			System.err.println("Couldn't load score!");
		}
		
		return p;
	}
	
	
	static public int testScore(int score) {
		if(ScoreManager.score.size() == 0) {
			return 1;
		}
		else for(int i = 0; i < maxScoreCount; ++i) {
			if(ScoreManager.score.size() <= i) {
				return i+1;
			}
			else if(ScoreManager.score.get(i).getScore() < score) {
				return i+1;
			}
		}
		return -1;
	}
	
	static public boolean clearScore() {
		score.clear();
		try {
			/*for(String k: prefs.keys()) {
				prefs.remove(k);
			}*/
			prefs.clear();
		} catch (BackingStoreException e) {
			System.err.println("Couldn't clear score!");
			return false;
		}
		return true;
	}
	
	static private void saveScore() {
		try {
			prefs.clear();
		} catch (BackingStoreException e) {
			System.err.println("Couldn't clear preferences!");
		}
		int i = 0;
		for(Score s: score) {
			++i;
			prefs.put(String.valueOf(i), s.getPlayerName().replaceAll(";", " ")+";"+s.getScore());
		}
	}

	
	static public int setScore(String name, int score) {
		int place = -1;
		Score add = new Score(name, score);
		if(ScoreManager.score.size() == 0) {
			ScoreManager.score.add(add);
			place = 1;
		}
		else for(int i = 0; i < maxScoreCount; ++i) {
			if(ScoreManager.score.size() <= i) {
				ScoreManager.score.add(add);
				if(place == -1) {
					place = i+1;
				}
				break;
			}
			else if(ScoreManager.score.get(i).getScore() < score) {
				Score tmp = ScoreManager.score.get(i);
				System.out.println(ScoreManager.score.get(i).getScore() +"<" + score);
				ScoreManager.score.set(i, add);
				add = tmp;
				if(place == -1) {
					place = i+1;
				}
			}
		}
		
		if(place != -1) {
			saveScore();
		}
		
		return place;
	}
	
	static public void print() {
		System.out.println(getFormatted());
	}
	static public String getFormatted() {
		StringBuilder sb = new StringBuilder();
		if(score.size() > 0) {
			int i = 1;
			for(Score s: score) {
				sb.append((i++) +": "+s.getPlayerName()+" - "+s.getScore()+"\n");
			}
		}
		else sb.append("No Scores!");
		return sb.toString();
	}
	
}
