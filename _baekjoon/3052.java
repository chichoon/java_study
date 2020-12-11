import java.io.*;

public class Main {
    public static void main(String args[]) throws IOException {
    	int[] o = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        int cnt = 0;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        
        for(int i = 0; i < 10; i++){
        	o[i] = Integer.parseInt(bf.readLine()) % 42;
        }
        bf.close();

        for(int i = 0; i < 42; i++) {
        	for(int j = 0; j < 10; j++) {
        		if(o[j] == i) {
        			cnt++;
        			break;
        		}
        	}
        }
    	System.out.println(cnt);
    }
}