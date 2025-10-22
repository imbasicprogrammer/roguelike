import greenfoot.*;

/**
 * Kelas Player adalah ANAK dari kelas Character.
 * Dia mewarisi semua logika 'animate()'.
 * Tugasnya hanya menyediakan gambar dan logika input.
 */
public class player extends Character
{
    /**
     * Constructor Player:
     * Mengatur stats spesifik untuk player.
     * super() akan memanggil constructor Character, yang akan
     * memanggil 'loadImages()' di bawah.
     */
    public player() {
        super(); // Wajib panggil constructor Induk
        
        // Atur stats spesifik untuk Player
        this.speed = 1;
        this.animationDelay = 10;
    }

    /**
     * IMPLEMENTASI METHOD WAJIB 1:
     * Mengisi semua array 'protected' dari induk
     * dengan gambar-gambar milik Player.
     */
    @Override
    protected void loadImages() {
        String folderPath = "player/";
        
        // --- (Ini adalah SEMUA kode 'load' dari constructor Anda sebelumnya) ---
        
        int idleFrameCount = 12;
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
            String filename = "player/idle/idle" + i + ".png";
            idleImages[i] = new GreenfootImage(filename);
        }
        
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_right" + i + ".png"; 
            walkRightImages[i] = new GreenfootImage(filename);
        }
        
        int walkLeftFrameCount = 5; 
        walkLeftImages = new GreenfootImage[walkLeftFrameCount];
        for (int i = 0; i < walkLeftFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_left" + i + ".png"; 
            walkLeftImages[i] = new GreenfootImage(filename);
        }
        
        int walkUpFrameCount = 5;
        walkUpImages = new GreenfootImage[walkUpFrameCount];
        for (int i = 0; i < walkUpFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_up" + i + ".png";
            walkUpImages[i] = new GreenfootImage(filename);
        }

        int walkDownFrameCount = 5;
        walkDownImages = new GreenfootImage[walkDownFrameCount];
        for (int i = 0; i < walkDownFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_down" + i + ".png";
            walkDownImages[i] = new GreenfootImage(filename);
        }
        
        int attackRightFrameCount = 5; 
        attackRightImages = new GreenfootImage[attackRightFrameCount];
        for (int i = 0; i < attackRightFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_right" + i + ".png"; 
            attackRightImages[i] = new GreenfootImage(filename);
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_left" + i + ".png"; 
            attackLeftImages[i] = new GreenfootImage(filename);
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_up" + i + ".png";
            attackUpImages[i] = new GreenfootImage(filename);
        }

        int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_down" + i + ".png";
            attackDownImages[i] = new GreenfootImage(filename);
        }
    }

    /**
     * IMPLEMENTASI METHOD WAJIB 2:
     * Ini adalah method 'checkInput()' Anda sebelumnya.
     * Tugasnya hanya mengatur 'currentState', 'isAttacking', dan 'setLocation'.
     * Dia tidak perlu tahu cara menganimasikannya.
     */
    @Override
    protected void determineState() {
        // --- (Ini adalah SEMUA kode 'checkInput()' Anda sebelumnya) ---
        
        if (isAttacking) {
            return; // Mengunci input saat sedang menyerang
        }
        
        String newState = "idle"; // Asumsi awal: diam
        
        if (Greenfoot.isKeyDown("right")) {
            newState = "walkRight"; 
            setLocation(getX() + speed, getY()); // Bergerak ke kanan
        }
        else if (Greenfoot.isKeyDown("left")) {
            newState = "walkLeft"; 
            setLocation(getX() - speed, getY()); // Bergerak ke kiri
        }
        else if (Greenfoot.isKeyDown("up")) {
            newState = "walkUp"; 
            setLocation(getX(), getY() - speed); // Bergerak ke atas
        }
        else if (Greenfoot.isKeyDown("down")) {
            newState = "walkDown"; 
            setLocation(getX(), getY() + speed); // Bergerak ke bawah
        }
        
        // Cek Attack (Tombol WASD)
        if (Greenfoot.isKeyDown("d") && !isAttacking) {
            newState = "AttackRight"; 
            isAttacking = true;
        }
        else if (Greenfoot.isKeyDown("a")&& !isAttacking) {
            newState = "AttackLeft"; 
            isAttacking = true;
        }
        else if (Greenfoot.isKeyDown("w")&& !isAttacking) {
            newState = "AttackUp";
            isAttacking = true;
        }
        else if (Greenfoot.isKeyDown("s")&& !isAttacking) {
            newState = "AttackDown"; 
            isAttacking = true;
        }
        
        // Reset animasi jika status berubah
        if ( !currentState.equals(newState) ) {
            currentState = newState;
            currentImage = 0;
            animationTimer = 0; 
        }
        
        // Reset khusus saat attack dimulai
        if (isAttacking) {
            currentImage = 0;
            animationTimer = 0;
        }
    }
}