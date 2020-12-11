import java.io.*;

public class Main {
    public static void main(String args[]) throws IOException {
    	int max = Integer.MIN_VALUE;
    	int o = 0;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        
        for(int i = 0; i < 9; i++){
        	int a = Integer.parseInt(bf.readLine());
            max = (a > max) ? a : max;
            o = (a == max) ? i + 1 : o;
        }
        bf.close();
        System.out.println(max);
        System.out.println(o);
    }
}