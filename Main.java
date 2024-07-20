import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame f = new JFrame();
        Game game = new Game();
        
        f.setBounds(10, 10, 700, 600);
        f.setTitle("Brick Breaker");      
        f.setResizable(false);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(game);
        f.setVisible(true);
    }
}
