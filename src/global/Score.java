package global;

public class Score {
    private String playerName;
    private int score;

    public Score(String playerName, int score) {
        setPlayerName(playerName);
        setScore(score);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    private void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getScore() {
        return this.score;
    }

    private void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString(){
        return playerName + ";" + score;
    }
}
