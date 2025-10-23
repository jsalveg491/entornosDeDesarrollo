package es.marquescomares.daw.ed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class manumyx extends JPanel implements ActionListener {
    private static final int WIDTH = 1200;
    private static final int HEIGHT = 800;
    private static final int NUM_PARTICLES = 100;
    private static final double MAX_DISTANCE = 150; // Distancia máxima para conectar partículas
    private static final double PARTICLE_SPEED = 0.3; // Velocidad reducida para movimiento más fluido
    
    private List<Particle> particles;
    private Timer timer;
    private Random random;
    
    public manumyx() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        
        particles = new ArrayList<>();
        random = new Random();
        
        // Crear partículas
        for (int i = 0; i < NUM_PARTICLES; i++) {
            particles.add(new Particle(
                random.nextDouble() * WIDTH,
                random.nextDouble() * HEIGHT,
                (random.nextDouble() - 0.5) * PARTICLE_SPEED,
                (random.nextDouble() - 0.5) * PARTICLE_SPEED
            ));
        }
        
        // Timer para animar
        timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activar antialiasing para mejor calidad
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        // Dibujar conexiones entre partículas cercanas
        for (int i = 0; i < particles.size(); i++) {
            for (int j = i + 1; j < particles.size(); j++) {
                Particle p1 = particles.get(i);
                Particle p2 = particles.get(j);
                
                double distance = p1.distanceTo(p2);
                
                if (distance < MAX_DISTANCE) {
                    // Calcular opacidad basada en la distancia
                    float opacity = (float) (1 - distance / MAX_DISTANCE);
                    g2d.setColor(new Color(100, 150, 255, (int) (opacity * 100)));
                    g2d.drawLine((int) p1.x, (int) p1.y, (int) p2.x, (int) p2.y);
                }
            }
        }
        
        // Dibujar partículas
        g2d.setColor(new Color(200, 220, 255));
        for (Particle p : particles) {
            g2d.fillOval((int) p.x - 2, (int) p.y - 2, 4, 4);
        }
        
        // Dibujar el texto "manumyx" con aura azul
        drawGlowingText(g2d, "manumyx", WIDTH / 2, HEIGHT / 2);
    }
    
    private void drawGlowingText(Graphics2D g2d, String text, int centerX, int centerY) {
        Font font = new Font("Arial", Font.BOLD, 80);
        g2d.setFont(font);
        
        // Obtener métricas del texto para centrarlo
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int x = centerX - textWidth / 2;
        int y = centerY + textHeight / 4;
        
        // Dibujar múltiples capas de sombra azul para el efecto de aura/glow
        for (int i = 20; i > 0; i--) {
            float alpha = (float) (0.1 * (20 - i) / 20.0);
            g2d.setColor(new Color(50, 150, 255, (int) (alpha * 255)));
            
            // Dibujar el texto con desplazamiento para crear el aura
            for (int dx = -i; dx <= i; dx++) {
                for (int dy = -i; dy <= i; dy++) {
                    if (dx * dx + dy * dy <= i * i) {
                        g2d.drawString(text, x + dx, y + dy);
                    }
                }
            }
        }
        
        // Dibujar el texto principal en blanco
        g2d.setColor(Color.WHITE);
        g2d.drawString(text, x, y);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Actualizar posición de las partículas
        for (Particle p : particles) {
            p.update(WIDTH, HEIGHT);
        }
        repaint();
    }
    
    // Clase interna para representar una partícula
    private static class Particle {
        double x, y;
        double vx, vy;
        
        public Particle(double x, double y, double vx, double vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
        }
        
        public void update(int width, int height) {
            x += vx;
            y += vy;
            
            // Rebotar en los bordes
            if (x <= 0 || x >= width) {
                vx = -vx;
                x = Math.max(0, Math.min(width, x));
            }
            if (y <= 0 || y >= height) {
                vy = -vy;
                y = Math.max(0, Math.min(height, y));
            }
        }
        
        public double distanceTo(Particle other) {
            double dx = x - other.x;
            double dy = y - other.y;
            return Math.sqrt(dx * dx + dy * dy);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("manumyx - Particle System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new manumyx());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
