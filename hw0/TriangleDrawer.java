public class TriangleDrawer {
   public static void drawTriangle(int N) {
       for (int row = 0; row < N; row++) {
           for (int col = 0; col <= row; col++) {
               System.out.print("*");
           }
           System.out.println();
       }
   }
   
   public static void main(String[] args) {
       if (args.length < 1) {
            System.out.println("Please enter command line arguments.");
            System.out.println("e.g. java TriangleDrawer 5");
        }
       for (int i = 0; i < args.length; i++) {
            try {
                int N = Integer.parseInt(args[i]);
                drawTriangle(N);
            } catch (NumberFormatException e) {
                System.out.printf("%s is not a valid number.\n", args[i]);
            }
        }
   }
}