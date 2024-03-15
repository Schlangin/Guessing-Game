public class LeaderBoardEntry implements Comparable<LeaderBoardEntry>{
    String name;
    int score;
    LeaderBoardEntry(String name, int score){
        this.name = name;
        this.score = score;
    }
    public String getName(){
        return name;
    }
    public int getScore(){
        return score;
    }

    @Override
    public int compareTo(LeaderBoardEntry other){
        return Integer.compare(this.score,other.score);
    }
    @Override
    public String toString(){
        return name + " - " + score;
    }
}
