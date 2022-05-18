import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


interface Commons
{
    int WIDTH  = 700;
    int HEIGHT = 500;

    int DELAY = 20;
}


class Ball
{
    int x, y, speedX, speedY, size;
}


class Paddle
{
    int x, y, speedY, width, height, score;
}


class GamePanel extends JPanel implements KeyListener, ActionListener
{

    private Ball ball = new Ball();
    private Paddle paddleA = new Paddle();
    private Paddle paddleB = new Paddle();


    private boolean isPlay = false;
    private Timer timer;
    

    
    public GamePanel()
    {

        addKeyListener( this );
        setFocusable( true );
        setFocusTraversalKeysEnabled( true );
        
        isPlay = true;

        timer = new Timer( Commons.DELAY, this );
        
        // Ball's value
        ball.x = Commons.WIDTH  / 2;
        ball.y = Commons.HEIGHT / 2;
        ball.speedX = -5;
        ball.speedY = -5;
        ball.size = 20;

        // Paddle A's value
        paddleA.x = 40;
        paddleA.y = Commons.HEIGHT / 2;
        paddleA.speedY = 20;
        paddleA.width  = 20;
        paddleA.height = 120;
        paddleA.score = 0;


        // Paddle B's value
        paddleB.x = Commons.WIDTH - 80;
        paddleB.y = Commons.HEIGHT / 2;
        paddleB.speedY = 20;
        paddleB.width  = 20;
        paddleB.height = 120;
        paddleB.score = 0;

        timer.start();
    }

    public void paintComponent( Graphics g )
    {
        super.paintComponent( g );
        draw( g );
    }

    public void draw( Graphics g )
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.setRenderingHint(
                             RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON );


        g2d.setRenderingHint(
                             RenderingHints.KEY_TEXT_ANTIALIASING,
                             RenderingHints.VALUE_TEXT_ANTIALIAS_ON );


        
        // Background
        g.setColor( Color.GREEN );
        g.fillRect( 0, 0, Commons.WIDTH, Commons.HEIGHT );

        // Ball
        g.setColor( Color.WHITE );
        g.fillRect( ball.x, ball.y, ball.size, ball.size );

        // Paddle A
        g.setColor( Color.WHITE );
        g.fillRect( paddleA.x, paddleA.y, paddleA.width, paddleA.height );

        // Paddle B
        g.setColor( Color.WHITE );
        g.fillRect( paddleB.x, paddleB.y, paddleB.width, paddleB.height );


        // Score
        String s1, s2;

        s1 = String.format( "%02d", paddleA.score );
        s2 = String.format( "%02d", paddleB.score );

        g.setColor( Color.WHITE );
        g.setFont( new Font( "MS Gothic", Font.BOLD, 20 ) );
        g.drawString( s1, 10, 20 );
        g.drawString( s2, Commons.WIDTH - 50, 20 );
        g.drawString( "USE ARROW KEY TO MOVE", 10, Commons.HEIGHT - 50 );


        // Press ESC to end the game
        if ( !isPlay ) {
            g.setFont( new Font( "MS Gothic", Font.BOLD, 50 ) );
            g.drawString( "GAME END", Commons.WIDTH / 2 - 120, Commons.HEIGHT / 2 );
        }
    }

    
    @Override public void actionPerformed( ActionEvent e )
    {

        if ( isPlay ) {
            ball.x += ball.speedX;
            ball.y += ball.speedY;


            paddleB.y += ball.speedY;

            if ( paddleB.y < 0 ) paddleB.y = 0;
            if ( paddleB.y > Commons.HEIGHT - 160 ) paddleB.y = Commons.HEIGHT - 160;


            if ( ball.y < 0 ) {
                ball.speedY *= -1;
            }

            if ( ball.y > Commons.HEIGHT - 60 ) {
                ball.speedY *= -1;
            }

            if ( ball.x < 0 ) {
                // System.out.println( "A:" + paddleA.score + "\nB:" + paddleB.score );
                ball.x = Commons.WIDTH  / 2;
                ball.y = Commons.HEIGHT / 2;

                ball.speedX -= 1;
                ball.speedY -= 1;
                
                paddleB.score++;
            }

            if ( ball.x > Commons.WIDTH ) {
                // System.out.println( "A:" + paddleA.score + "\nB:" + paddleB.score );
                ball.x = Commons.WIDTH  / 2;
                ball.y = Commons.HEIGHT / 2;

                ball.speedX -= 1;
                ball.speedY -= 1;
                
                paddleA.score++;                
            }


            Rectangle ballRect = new Rectangle( ball.x, ball.y, ball.size, ball.size );
            Rectangle paddleARect = new Rectangle( paddleA.x, paddleA.y, paddleA.width, paddleA.height );
            Rectangle paddleBRect = new Rectangle( paddleB.x, paddleB.y, paddleB.width, paddleB.height );

            if ( ballRect.intersects( paddleARect ) ) {
                ball.speedX *= -1;
            }

            if ( ballRect.intersects( paddleBRect ) ) {
                ball.speedX *= -1;
            }

        }

        if ( !isPlay ) {
            timer.stop();
        }
        
        repaint();        
    }

    @Override public void keyPressed( KeyEvent e )
    {
        switch( e.getKeyCode() ) {
        case KeyEvent.VK_UP:
            if ( paddleA.y < 0 )
                paddleA.y = 0;
            else
                paddleA.y -= paddleA.speedY;
            break;
        case KeyEvent.VK_DOWN:
            if ( paddleA.y > Commons.HEIGHT - 180 )
                paddleA.y = Commons.HEIGHT - 180;
            else
                paddleA.y += paddleA.speedY;
            break;
        case KeyEvent.VK_ESCAPE:
            isPlay = false;
            break;
        }
    }

    @Override public void keyTyped( KeyEvent e )
    {
    }

    @Override public void keyReleased( KeyEvent e )
    {
    }

}

class GameFrame extends JFrame
{
    public GameFrame()
    {
        add( new GamePanel() );
        setTitle( "Pong in Java" );
        setLocation( 100, 10 );
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        setResizable( false );
        pack();
        setSize( Commons.WIDTH, Commons.HEIGHT );
        setVisible( true );    
    }
}


class Main
{
    public static void main( String[] args )
    {
        new GameFrame();
    }
}
