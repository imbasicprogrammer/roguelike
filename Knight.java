import greenfoot.*;
import java.util.*;

public class Knight extends player 
{
    private int attackDamage = 40; // Damage melee normal
    private int attackRange = 50;  

    // --- VARIABEL UNTUK STARFALL SMITE ---
    private int xpForSkill = 50; // Butuh 50 XP
    private boolean skillReady = false; 
    private int skillCooldownTimer = 0;  
    private int maxSkillCooldown = 600; // Cooldown 10 detik
    // --- Variabel Durasi & Buff DIHAPUS ---

    public Knight()
    {
        super();
        this.speed = 2; 
        this.maxHealth = 150; 
        this.health = this.maxHealth;
        this.maxAttackCooldown = 40; 
    }

    @Override
    protected void loadImages() {
        // ... (Kode loadImages Bos - PASTIKAN GAMBAR HURT & DEATH ADA) ...
        String folderPath = "player/";
        double scaleFactor = 1.5; 
        int idleFrameCount = 12;
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
            String filename = "player/idle/idle" + i + ".png";
            // 1. Muat gambar ASLI
            GreenfootImage originalImage = new GreenfootImage(filename);

            // 2. Tentukan skala baru (misal, 150% atau 1.5x)
            // Ganti angka ini (1.0 = 100%, 2.0 = 200%, 0.5 = 50%)
            int newWidth = (int)(originalImage.getWidth() * scaleFactor);
            int newHeight = (int)(originalImage.getHeight() * scaleFactor);

            // 3. Ubah skala gambar
            originalImage.scale(newWidth, newHeight); 

            // 4. Simpan gambar yang SUDAH diubah ukurannya ke array
            idleImages[i] = originalImage;
            
        }
        
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_right" + i + ".png"; 
            GreenfootImage originalWalk = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newWalkWidth = (int)(originalWalk.getWidth() * scaleFactor);
            int newWalkHeight = (int)(originalWalk.getHeight() * scaleFactor);
            originalWalk.scale(newWalkWidth, newWalkHeight);
            walkRightImages[i] = originalWalk;
            
        }
        
        int walkLeftFrameCount = 5; 
        walkLeftImages = new GreenfootImage[walkLeftFrameCount];
        for (int i = 0; i < walkLeftFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_left" + i + ".png";
            GreenfootImage originalWalk = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newWalkWidth = (int)(originalWalk.getWidth() * scaleFactor);
            int newWalkHeight = (int)(originalWalk.getHeight() * scaleFactor);
            originalWalk.scale(newWalkWidth, newWalkHeight);
            walkLeftImages[i] = originalWalk;
        }
        
        int walkUpFrameCount = 5;
        walkUpImages = new GreenfootImage[walkUpFrameCount];
        for (int i = 0; i < walkUpFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_up" + i + ".png";
            GreenfootImage originalWalk = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newWalkWidth = (int)(originalWalk.getWidth() * scaleFactor);
            int newWalkHeight = (int)(originalWalk.getHeight() * scaleFactor);
            originalWalk.scale(newWalkWidth, newWalkHeight);
            walkUpImages[i] = originalWalk;
        }

        int walkDownFrameCount = 5;
        walkDownImages = new GreenfootImage[walkDownFrameCount];
        for (int i = 0; i < walkDownFrameCount; i++) {
            String filename = folderPath + "walk/" + "walk_down" + i + ".png";
            GreenfootImage originalWalk = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newWalkWidth = (int)(originalWalk.getWidth() * scaleFactor);
            int newWalkHeight = (int)(originalWalk.getHeight() * scaleFactor);
            originalWalk.scale(newWalkWidth, newWalkHeight);
            walkDownImages[i] = originalWalk;
        }
        
        int attackRightFrameCount = 5; 
        attackRightImages = new GreenfootImage[attackRightFrameCount];
        for (int i = 0; i < attackRightFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_right" + i + ".png"; 
            GreenfootImage originalAttack = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newAttackWidth = (int)(originalAttack.getWidth() * scaleFactor);
            int newAttackHeight = (int)(originalAttack.getHeight() * scaleFactor);
            originalAttack.scale(newAttackWidth, newAttackHeight);
            attackRightImages[i] = originalAttack;
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_left" + i + ".png"; 
            GreenfootImage originalAttack = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newAttackWidth = (int)(originalAttack.getWidth() * scaleFactor);
            int newAttackHeight = (int)(originalAttack.getHeight() * scaleFactor);
            originalAttack.scale(newAttackWidth, newAttackHeight);
            attackLeftImages[i] = originalAttack;
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_up" + i + ".png";
            GreenfootImage originalAttack = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newAttackWidth = (int)(originalAttack.getWidth() * scaleFactor);
            int newAttackHeight = (int)(originalAttack.getHeight() * scaleFactor);
            originalAttack.scale(newAttackWidth, newAttackHeight);
            attackUpImages[i] = originalAttack;
        }

        int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            String filename = folderPath + "walk_attack/" + "walk_attack_down" + i + ".png";
            GreenfootImage originalAttack = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newAttackWidth = (int)(originalAttack.getWidth() * scaleFactor);
            int newAttackHeight = (int)(originalAttack.getHeight() * scaleFactor);
            originalAttack.scale(newAttackWidth, newAttackHeight);
            attackDownImages[i] = originalAttack;
        }
        
        int hurtFrameCount = 3; // Contoh
        hurtImages = new GreenfootImage[hurtFrameCount];
        for (int i = 0; i < hurtFrameCount; i++) {
            String filename = "player/Hurt/hurt" + i + ".png"; // Sesuaikan path
            GreenfootImage originalHurt = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newHurtWidth = (int)(originalHurt.getWidth() * scaleFactor);
            int newHurtHeight = (int)(originalHurt.getHeight() * scaleFactor);
            originalHurt.scale(newHurtWidth, newHurtHeight);
            hurtImages[i] = originalHurt;
        }

        int deathFrameCount = 6; // Contoh
        deathImages = new GreenfootImage[deathFrameCount];
        for (int i = 0; i < deathFrameCount; i++) {
            String filename = "player/Death/death" + i + ".png"; // Sesuaikan path
            GreenfootImage originalDeath = new GreenfootImage(filename);
            // Gunakan scaleFactor yang sama
            int newDeathWidth = (int)(originalDeath.getWidth() * scaleFactor);
            int newDeathHeight = (int)(originalDeath.getHeight() * scaleFactor);
            originalDeath.scale(newDeathWidth, newDeathHeight);
            deathImages[i] = originalDeath;
        }
    }

    /**
     * Otak Knight, sekarang dengan aktivasi Starfall Smite.
     */
    @Override
    protected void determineState() {
        
        if (!skillReady && xp >= xpForSkill && skillCooldownTimer == 0) {
        skillReady = true;

        // --- PANGGIL NOTIFIKASI ---
        if (getWorld() instanceof Dungeons) {
            // Tampilkan "Skill Ready" selama 3 detik
            ((Dungeons)getWorld()).showNotification("Starfall Smite Ready!", 3);
        }
        // --- AKHIR PANGGILAN ---
        }
        // 1. Update Cooldown Skill
        if (skillCooldownTimer > 0) {
            skillCooldownTimer--;
        }

        // 2. Cek Kesiapan Skill (berdasarkan XP)
        if (!skillReady && xp >= xpForSkill && skillCooldownTimer == 0) {
            skillReady = true;
            
        }

        // 3. Cooldown Serangan Biasa
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // 4. Kunci Input jika Animasi Berjalan
        if (isAttacking || isHurt) {
            return; 
        }
        
        // 5. Input Gerakan (Panah)
        String newState = "idle";
        int dx = 0, dy = 0;
        if (Greenfoot.isKeyDown("right")) { newState = "walkRight"; dx = speed; }
        else if (Greenfoot.isKeyDown("left")) { newState = "walkLeft"; dx = -speed; }
        else if (Greenfoot.isKeyDown("up")) { newState = "walkUp"; dy = -speed; }
        else if (Greenfoot.isKeyDown("down")) { newState = "walkDown"; dy = speed; }
        moveWithCollision(dx, dy);
        
        // 6. Input Serangan BIASA (WASD)
        if (attackCooldown == 0) 
        {
            String attackState = ""; 
            if (Greenfoot.isKeyDown("d")) attackState = "AttackRight";
            else if (Greenfoot.isKeyDown("a")) attackState = "AttackLeft";
            else if (Greenfoot.isKeyDown("w")) attackState = "AttackUp";
            else if (Greenfoot.isKeyDown("s")) attackState = "AttackDown";

            if (!attackState.isEmpty()) {
                newState = attackState;
                isAttacking = true;
                attackCooldown = maxAttackCooldown; 
                performAttack(attackState); // Serangan melee biasa
            }
        }
        
        // --- 7. LOGIKA BARU: Aktivasi Skill STARFALL SMITE ('E') ---
        if (Greenfoot.isKeyDown("e") && skillReady)
        {
        skillReady = false;
        skillCooldownTimer = maxSkillCooldown;
        getWorld().addObject(new StarfallSmiteEffect(), getX(), getY());

        // --- PANGGIL NOTIFIKASI ---
       /* if (getWorld() instanceof Dungeons) {
            // Tampilkan "Skill Activated" selama 1 detik
             ((Dungeons)getWorld()).showNotification("Starfall Smite!", 1);
        }*/
        // --- AKHIR PANGGILAN ---

        // (Opsional: animasi casting)
        isAttacking = true;
        newState = "AttackDown"; // Contoh
        }
        
        // 8. Reset Animasi
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
     * Logika serangan melee NORMAL (tanpa buff).
     */
    @Override
    protected void performAttack(String state) {
        List<Enemy> targets = getObjectsInRange(attackRange, Enemy.class);
        if (targets.isEmpty()) return;

        // Damage normal, tidak ada boost
        int currentDamage = attackDamage; 

        for (Enemy target : targets) {
            int dx = target.getX() - getX();
            int dy = target.getY() - getY();

            if (state.equals("AttackRight") && dx > 0) target.takeDamage(currentDamage);
            else if (state.equals("AttackLeft") && dx < 0) target.takeDamage(currentDamage);
            else if (state.equals("AttackUp") && dy < 0) target.takeDamage(currentDamage);
            else if (state.equals("AttackDown") && dy > 0) target.takeDamage(currentDamage);
        }
    }
}