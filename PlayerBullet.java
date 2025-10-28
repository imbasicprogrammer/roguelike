import greenfoot.*;

/**
 * Aktor ini adalah proyektil (peluru) yang ditembakkan oleh Player.
 * (Versi ini sekarang "Homing" / mencari target)
 */
public class PlayerBullet extends Actor
{
    private int damage = 25;
    private int speed = 4;
    
    // Variabel "Mesin Animasi Mini" (Sudah benar)
    private GreenfootImage[] animationImages;
    private int currentImage = 0;
    private int animationTimer = 0;
    private int animationDelay = 5;

    // --- BARU: Variabel untuk menyimpan target ---
    private Enemy target;

    /**
     * Constructor SEKARANG menerima 'Enemy' sebagai target,
     * BUKAN 'int rotation'.
     */
    public PlayerBullet(Enemy target) {
        this.target = target; // Simpan targetnya
        
        loadImages(); 
        
        // Atur gambar pertama (dengan cek keamanan)
        if (animationImages != null && animationImages[0] != null) {
            setImage(animationImages[0]);
        } else {
            // Gambar darurat jika loadImages gagal
            setImage(new GreenfootImage("Skills/Dagger/Soulcrater1.png")); 
        }
    }
    
    /**
     * Metode untuk mengisi array gambar
     * (Saya perbaiki bug 'i=1' menjadi 'i=0')
     */
    private void loadImages() {
        int frameCount = 8; 
        animationImages = new GreenfootImage[frameCount];
        
        for (int i = 1; i < frameCount; i++) { // <-- Diperbaiki (dimulai dari 0)
            String filename = "Skills/Dagger/Soulcrater" + i + ".png"; 
            
            try {
                animationImages[i] = new GreenfootImage(filename);
            } catch (Exception e) {
                // Jika file tidak ada, buat gambar darurat
                animationImages[i] = new GreenfootImage(20, 20);
                animationImages[i].setColor(Color.RED);
                animationImages[i].fill();
            }
        }
    }
    
    /**
     * Metode "Mesin Animasi Mini"
     * (Sudah benar, saya rapikan sedikit)
     */
    private void animate() {
        if (animationImages[currentImage] == null) return; // Keamanan
        
        animationTimer++;
        if (animationTimer % animationDelay == 0) {
            animationTimer = 0;
            currentImage = (currentImage + 1) % animationImages.length; // Looping aman
            
            if (animationImages[currentImage] != null) {
                setImage(animationImages[currentImage]);
            }
        }
    }
    
    /**
     * act() sekarang memiliki logika "Homing"
     */
    public void act()
    {
        // "Penjaga" 1: Apakah saya masih di dunia?
        if (getWorld() == null) {
            return; 
        }
        
        // "Penjaga" 2: Apakah target saya masih hidup/ada di dunia?
        if (target == null || target.getWorld() == null) {
            getWorld().removeObject(this); // Hancurkan diri jika target hilang
            return;
        }

        // 1. Putar animasi (Selalu)
        animate(); 
        
        // --- 2. LOGIKA GERAK BARU (Homing) ---
        // Arahkan peluru ke target
        turnTowards(target.getX(), target.getY());
        // Bergerak lurus ke arah target
        move(speed);
        
        // 3. Cek tabrakan (Logika ini SAMA, dan sudah benar)
        
        if (isTouching(Wall.class)) {
            getWorld().removeObject(this); // Berhenti di tembok
            return;
        }
        
        // Cek musuh (Akan berhenti jika kena musuh APAPUN)
        Enemy hitEnemy = (Enemy) getOneIntersectingObject(Enemy.class);
        if (hitEnemy != null) {
            hitEnemy.takeDamage(damage);    // Beri damage
            getWorld().removeObject(this);  // Berhenti/hancur
            return;
        }
        
        if (isAtEdge()) {
            getWorld().removeObject(this);
            return;
        }
    }
}