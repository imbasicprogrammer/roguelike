import greenfoot.*;
import java.util.List;

/**
 * Ranger.java (Versi Auto-Aim + Homing Bullet)
 * - Bergerak dengan Tombol Panah (dari 'player')
 * - Menyerang dengan 'Q' (logika baru)
 * - Serangan otomatis mencari musuh terdekat (Auto-Aim)
 * - Menembakkan PlayerBullet yang 'Homing' (mencari target)
 */
public class Ranger extends player {

    public Ranger() {
        super();
        this.speed = 3; // Ranger lebih cepat
        this.maxAttackCooldown = 20; // Menembak lebih cepat
    }

    /**
     * IMPLEMENTASI WAJIB 1:
     * Mengisi semua array gambar.
     * * PERINGATAN: Bos harus mengganti path "player/"
     * di bawah ini agar sesuai dengan folder gambar Ranger Bos!
     */
    @Override
    protected void loadImages() {
        // GANTI "player/" DENGAN FOLDER GAMBAR RANGER ANDA (misal "Ranger/")
        String folderPath = "player/"; 
        
        // --- CONTOH PERBAIKAN ---
        // String folderPath = "Ranger/";
        // String idleFilename = "Ranger/Idle/idle" + i + ".png";
        // --- AKHIR CONTOH ---

        // --- Load Idle ---
        int idleFrameCount = 12;
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
            // GANTI PATH INI
            String filename = "player/idle/idle" + i + ".png";
            idleImages[i] = new GreenfootImage(filename);
        }
        
        // --- Load Walk (4 Arah) ---
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk/" + "walk_right" + i + ".png"; 
            walkRightImages[i] = new GreenfootImage(filename);
        }
        
        int walkLeftFrameCount = 5; 
        walkLeftImages = new GreenfootImage[walkLeftFrameCount];
        for (int i = 0; i < walkLeftFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk/" + "walk_left" + i + ".png"; 
            walkLeftImages[i] = new GreenfootImage(filename);
        }
        
        int walkUpFrameCount = 5;
        walkUpImages = new GreenfootImage[walkUpFrameCount];
        for (int i = 0; i < walkUpFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk/" + "walk_up" + i + ".png";
            walkUpImages[i] = new GreenfootImage(filename);
        }

        int walkDownFrameCount = 5;
        walkDownImages = new GreenfootImage[walkDownFrameCount];
        for (int i = 0; i < walkDownFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk/" + "walk_down" + i + ".png";
            walkDownImages[i] = new GreenfootImage(filename);
        }
        
        // --- Load Attack (4 Arah) ---
        int attackRightFrameCount = 5; 
        attackRightImages = new GreenfootImage[attackRightFrameCount];
        for (int i = 0; i < attackRightFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk_attack/" + "walk_attack_right" + i + ".png"; 
            attackRightImages[i] = new GreenfootImage(filename);
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk_attack/" + "walk_attack_left" + i + ".png"; 
            attackLeftImages[i] = new GreenfootImage(filename);
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk_attack/" + "walk_attack_up" + i + ".png";
            attackUpImages[i] = new GreenfootImage(filename);
        }

        int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            // GANTI PATH INI
            String filename = folderPath + "walk_attack/" + "walk_attack_down" + i + ".png";
            attackDownImages[i] = new GreenfootImage(filename);
        }
        
        // --- Load Hurt & Death ---
        int hurtFrameCount = 3; // Contoh
        hurtImages = new GreenfootImage[hurtFrameCount];
        for (int i = 0; i < hurtFrameCount; i++) {
            // GANTI PATH INI
            String filename = "player/Hurt/hurt" + i + ".png";
            hurtImages[i] = new GreenfootImage(filename);
        }

        int deathFrameCount = 6; // Contoh
        deathImages = new GreenfootImage[deathFrameCount];
        for (int i = 0; i < deathFrameCount; i++) {
            // GANTI PATH INI
            String filename = "player/Death/death" + i + ".png";
            deathImages[i] = new GreenfootImage(filename);
        }
    }
    
    /**
     * @Override
     * KITA MENIMPA (OVERRIDE) SELURUH OTAK PLAYER
     * Karena logika serangan kita sangat berbeda.
     */
    @Override
    protected void determineState() {
        // 1. Logika Cooldown (SAMA SEPERTI INDUK)
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // 2. Kunci Input Gerak/Serang (SAMA SEPERTI INDUK)
        if (isAttacking || isHurt) {
            return; 
        }
        
        // 3. Logika Input Gerakan (SAMA SEPERTI INDUK - Tombol Panah)
        String newState = "idle";
        int dx = 0;
        int dy = 0;
        
        if (Greenfoot.isKeyDown("right")) {
            newState = "walkRight"; 
            dx = speed;
        }
        else if (Greenfoot.isKeyDown("left")) {
            newState = "walkLeft"; 
            dx = -speed;
        }
        else if (Greenfoot.isKeyDown("up")) {
            newState = "walkUp"; 
            dy = -speed;
        }
        else if (Greenfoot.isKeyDown("down")) {
            newState = "walkDown"; 
            dy = speed;
        }
        
        moveWithCollision(dx, dy);
        
        // --- 4. LOGIKA SERANGAN BARU (Auto-Aim + 'Q') ---
        if (Greenfoot.isKeyDown("q") && attackCooldown == 0) 
        {
            // A. Temukan musuh terdekat
            Enemy target = findNearestEnemy();

            if (target != null) { // Hanya serang jika ada target
                
                // B. Hitung rotasi ke target (HANYA UNTUK ANIMASI PLAYER)
                int rotation = (int) Math.toDegrees(Math.atan2(target.getY() - getY(), target.getX() - getX()));
                
                // C. Tentukan animasi berdasarkan rotasi (agar Player menghadap musuh)
                String attackState = getAttackAnimationState(rotation);
                
                // D. Jalankan serangan
                newState = attackState;
                isAttacking = true;
                attackCooldown = maxAttackCooldown;
                
                // --- INI PERUBAHANNYA ---
                // E. Tembakkan peluru (Mengirim 'target', BUKAN 'rotation')
                // PlayerBullet sekarang akan 'homing' ke target ini.
                getWorld().addObject(new PlayerBullet(target), getX(), getY());
            }
            // Jika tidak ada target, tombol 'q' tidak melakukan apa-apa
        }
        
        // --- 5. Reset Animasi (SAMA SEPERTI INDUK) ---
        if ( !currentState.equals(newState) ) {
            currentState = newState;
            currentImage = 0;
            animationTimer = 0; 
        }
        if (isAttacking) {
            currentImage = 0;
            animationTimer = 0;
        }
    }
    
    /**
     * METODE PEMBANTU BARU
     * Mencari musuh yang paling dekat dengan Ranger.
     */
    private Enemy findNearestEnemy() {
        // PERBAIKAN PENTING: Cek jika kita ada di dunia
        if (getWorld() == null) {
            return null;
        }
        List<Enemy> enemies = getWorld().getObjects(Enemy.class);
        if (enemies.isEmpty()) {
            return null; // Tidak ada musuh
        }
        
        Enemy nearest = enemies.get(0);
        // Hitung jarak menggunakan rumus pythagoras (hypot)
        double minDistance = Math.hypot(nearest.getX() - getX(), nearest.getY() - getY());
        
        // Loop untuk membandingkan jarak
        for (Enemy e : enemies) {
            double distance = Math.hypot(e.getX() - getX(), e.getY() - getY());
            if (distance < minDistance) {
                minDistance = distance;
                nearest = e;
            }
        }
        return nearest;
    }
    
    /**
     * METODE PEMBANTU BARU
     * Menentukan animasi mana ('AttackRight', 'Up', 'Left', 'Down')
     * yang harus dimainkan berdasarkan rotasi ke musuh.
     */
    private String getAttackAnimationState(int rotation) {
        // Normalisasi rotasi ke 0-360 (jika -180 s/d 180)
        if (rotation < 0) rotation += 360; 
        
        if (rotation > 315 || rotation <= 45) { // 315-360 atau 0-45
            return "AttackRight";
        } else if (rotation > 45 && rotation <= 135) { // 45-135
            return "AttackDown";
        } else if (rotation > 135 && rotation <= 225) { // 135-225
            return "AttackLeft";
        } else { // 225-315
            return "AttackUp";
        }
    }
    
    /**
     * METHOD WAJIB (dari 'player')
     * Kita harus punya ini untuk memuaskan kelas induk,
     * tapi kita tidak akan pernah menggunakannya karena kita 
     * meng-override 'determineState()'.
     */
    @Override
    protected void performAttack(String state) {
        // Biarkan kosong
    }
}