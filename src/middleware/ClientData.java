package middleware;

public class ClientData {
    public String username;
    public Integer score;
    public void gainPoints(int row){
        score += (row+1)*100;
    }
}


