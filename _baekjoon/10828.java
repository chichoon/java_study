import java.io.*;
import java.util.StringTokenizer;

class Main {
    public static void main(String args[]) throws IOException {
    	int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        
        int N = Integer.parseInt(bf.readLine());
        StringTokenizer st = new StringTokenizer(bf.readLine(), " ");
        bf.close();
        
        for(int i = 0; i < N; i++){
        	int a = Integer.parseInt(st.nextToken());
            min = (a < min) ? a : min;
            max = (a > max) ? a : max;
        }
        System.out.println(min + " " + max);
    }
}
