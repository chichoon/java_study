
import java.io.*;

class Main {
    public static void main(String args[]) throws IOException {
    	int o = 1;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        
        for(int i = 0; i < 3; i++){
        	o *= Integer.parseInt(bf.readLine());
        }
        bf.close();
        String a = Integer.toString(o);
        
        for(int i = 0; i < 10; i++) {
            int cnt = 0;
        	for(int j = 0; j < a.length(); j++) {
        		o = Integer.parseInt(a.substring(j, j+1));
        		cnt = (o == i) ? cnt + 1 : cnt;
        	}
        	System.out.println(cnt);
        }
    }
}
