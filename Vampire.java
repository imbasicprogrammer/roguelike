import greenfoot.*;
import java.util.List;

/**
 * Musuh Goblin. Ini adalah ANAK dari kelas Enemy.
 * Tugasnya:
 * 1. Menyediakan gambar (loadImages)
 * 2. Menyediakan AI (determineState)
 */
public class Vampire extends Enemy // (GANTI JADI 'extends Enemy' SETELAH 'Enemy.java' DIBUAT)
{
    /**
     * Constructor Goblin
     */
    public Vampire() {
        super(); // Wajib panggil constructor Induk (Character)
        
        // Atur stats spesifik untuk Goblin
        this.speed = 1;             // Lebih lambat dari Player
        this.animationDelay = 15;   // Animasinya lebih lambat
        this.maxHealth = 50;        // HP lebih sedikit
        this.health = this.maxHealth; // Set HP
    }

    /**
     * IMPLEMENTASI METHOD WAJIB 1:
     * Mengisi semua array 'protected' dari induk
     * dengan gambar-gambar milik Goblin.
     */
    @Override
    protected void loadImages() {
        // GANTI INI DENGAN KODE LOAD GAMBAR GOBLIN
        // Contoh (Harus disesuaikan dengan nama file Anda!):
        
        // idleImages = new GreenfootImage[4];
        // for(int i=0; i < idleImages.length; i++) {
        //     idleImages[i] = new GreenfootImage("goblin/idle/idle" + i + ".png");
        // }
        
        // walkRightImages = new GreenfootImage[4];
        // for(int i=0; i < walkRightImages.length; i++) {
        //     walkRightImages[i] = new GreenfootImage("goblin/walk/walk_right" + i + ".png");
        // }
        String folderPath = "Enemy/Vampire/";
        
        // --- (Ini adalah SEMUA kode 'load' dari constructor Anda sebelumnya) ---
        
        int idleFrameCount = 15;
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
            String filename = "Enemy/Vampire/Idle/Idle" + i + ".png";
            idleImages[i] = new GreenfootImage(filename);
        }
        
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkright" + i + ".png"; 
            walkRightImages[i] = new GreenfootImage(filename);
        }
        
        int walkLeftFrameCount = 5; 
        walkLeftImages = new GreenfootImage[walkLeftFrameCount];
        for (int i = 0; i < walkLeftFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkleft" + i + ".png"; 
            walkLeftImages[i] = new GreenfootImage(filename);
        }
        
        int walkUpFrameCount = 5;
        walkUpImages = new GreenfootImage[walkUpFrameCount];
        for (int i = 0; i < walkUpFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkup" + i + ".png";
            walkUpImages[i] = new GreenfootImage(filename);
        }

        int walkDownFrameCount = 5;
        walkDownImages = new GreenfootImage[walkDownFrameCount];
        for (int i = 0; i < walkDownFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walkdown" + i + ".png";
            walkDownImages[i] = new GreenfootImage(filename);
        }
        
        int attackRightFrameCount = 5; 
        attackRightImages = new GreenfootImage[attackRightFrameCount];
        for (int i = 0; i < attackRightFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackright" + i + ".png"; 
            attackRightImages[i] = new GreenfootImage(filename);
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackleft" + i + ".png"; 
            attackLeftImages[i] = new GreenfootImage(filename);
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            String filename = folderPath + "Attack/" + "attackup" + i + ".png";
            attackUpImages[i] = new GreenfootImage(filename);
        }

       int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            String filename = folderPath + "Attack/" + "VampireAttack" + i + ".png";
            attackDownImages[i] = new GreenfootImage(filename);
        } 
        // (Pastikan Bos setidaknya mengisi 'idleImages' agar tidak error)
        // Jika Bos belum punya gambar, isi 'idleImages' saja untuk tes:
        if (idleImages == null) {
            idleImages = new GreenfootImage[1];
            idleImages[0] = new GreenfootImage(32, 32); // Gambar kotak 32x32
            idleImages[0].setColor(Color.RED);
            idleImages[0].fill();
        }
    }

    /**
     * IMPLEMENTASI METHOD WAJIB 2:
     * Ini adalah "Otak" atau AI (Artificial Intelligence) si Goblin.
     * Dia akan otomatis mengejar dan menyerang Player.
     */
    @Override
    protected void determineState() {
        // 1. Jika sedang menyerang, jangan lakukan apa-apa (tunggu animasi selesai)
        if (isAttacking) {
            return;
        }

        // 2. Temukan Player
        List<player> players = getWorld().getObjects(player.class);
        if (players.isEmpty()) { // Jika Player tidak ada
            currentState = "idle";
            return;
        }
        player player = players.get(0); // Ambil player pertama yang ditemukan
        
        // 3. Inisialisasi
        String newState = "idle";
        int dx = 0;
        int dy = 0;
        
        // 4. LOGIKA AI: Cek Jarak (Attack atau Kejar)
        
        // Cek apakah Player dalam jangkauan serang (misal 40 piksel)
        if (getObjectsInRange(40, player.class).contains(player)) 
        {
            // JIKA DEKAT: SERANG!
            isAttacking = true;
            player.takeDamage(10); // Langsung beri damage ke player
            
            // Tentukan animasi serangan mana yang akan dimainkan
            int deltaX = player.getX() - getX();
            int deltaY = player.getY() - getY();
            
            if (Math.abs(deltaX) > Math.abs(deltaY)) { // Lebih dominan horizontal
                newState = (deltaX > 0) ? "AttackRight" : "AttackLeft";
            } else { // Lebih dominan vertikal
                newState = (deltaY > 0) ? "AttackDown" : "AttackUp";
            }
        }
        else
        {
            // JIKA JAUH: KEJAR!
            int deltaX = player.getX() - getX();
            int deltaY = player.getY() - getY();

            // Tentukan arah gerakan & animasi (prioritaskan arah terjauh)
            if (Math.abs(deltaX) > Math.abs(deltaY)) { // Bergerak horizontal
                if (deltaX > 5) { // Beri jarak 5px agar tidak "gemetar"
                    dx = speed;
                    newState = "walkRight";
                } else if (deltaX < -5) {
                    dx = -speed;
                    newState = "walkLeft";
                }
            } else { // Bergerak vertikal
                if (deltaY > 5) {
                    dy = speed;
                    newState = "walkDown";
                } else if (deltaY < -5) {
                    dy = -speed;
                    newState = "walkUp";
                }
            }
            
            // Panggil metode gerak anti-tembok
            moveWithCollision(dx, dy);
        }

        // 5. Reset animasi (jika status berubah)
        if ( !currentState.equals(newState) ) {
            currentState = newState;
            currentImage = 0;
            animationTimer = 0; 
        }
        
        // 6. Reset khusus saat attack dimulai
        if (isAttacking) {
            currentImage = 0;
            animationTimer = 0;
        }
    }
    
    /**
     * IMPLEMENTASI METHOD WAJIB (dari Character):
     * * Inilah yang terjadi saat HP musuh <= 0.
     * Ini adalah logika yang SAMA untuk SEMUA musuh.
     */
    @Override
    protected void onDeath()
    {
        // 1. Beri tahu Dungeon (World) bahwa 1 musuh telah kalah
        // Ini PENTING untuk Manajer Gelombang (Wave Manager)
        if (getWorld() instanceof Dungeons) {
            ((Dungeons)getWorld()).enemyDefeated();
        }
        
        // 2. Hilangkan diri dari dunia
        getWorld().removeObject(this);
    }
}