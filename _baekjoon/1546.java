import java.io.*;
import java.util.StringTokenizer;

class Main {
    public static void main(String args[]) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        int max = Integer.MIN_VALUE;
        float cnt = 0;
        int N = Integer.parseInt(bf.readLine());
        StringTokenizer st = new StringTokenizer(bf.readLine(), " ");
        bf.close();
        
        for(int i = 0; i < N; i++){
        	int a = Integer.parseInt(st.nextToken());
        	cnt += a;
        	max = (a > max) ? a : max;
        }
    	System.out.println((((cnt / N) / max) * 100));
    }
}