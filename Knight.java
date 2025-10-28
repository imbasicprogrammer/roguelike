import greenfoot.*;
import java.util.*;

/**
 * Write a description of class Villager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Knight extends player 
{
    // instance variables - replace the example below with your own
    private int attackDamage = 40; // Knight punya damage besar
    private int attackRange = 50;  // Jarak tebasan pedang
    /**
     * Constructor for objects of class Villager
     */
    public Knight()
    {
        super();
        this.speed = 2; // Knight standar
        this.maxHealth = 500; // Knight lebih tebal
        this.health = this.maxHealth;
        this.maxAttackCooldown = 40; // Serangan pedang lebih lambat
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
     @Override
    protected void loadImages() {
        String folderPath = "Knight/";
        
        // --- (Ini adalah SEMUA kode 'load' dari constructor Anda sebelumnya) ---
        
        int idleFrameCount = 12;
        idleImages = new GreenfootImage[idleFrameCount];
        for (int i = 0; i < idleFrameCount; i++) {
            String filename = "Knight/Idle/Idle" + i + ".png";
            idleImages[i] = new GreenfootImage(filename);
        }
        
        int walkRightFrameCount = 5; 
        walkRightImages = new GreenfootImage[walkRightFrameCount];
        for (int i = 0; i < walkRightFrameCount; i++) {
            String filename = folderPath + "Walk/" + "walk_right" + i + ".png"; 
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
            String filename = folderPath + "Attack/" + "Attack" + i + ".png"; 
            attackRightImages[i] = new GreenfootImage(filename);
        }
        
        int attackLeftFrameCount = 5; 
        attackLeftImages = new GreenfootImage[attackLeftFrameCount];
        for (int i = 0; i < attackLeftFrameCount; i++) {
            String filename = folderPath + "Attack/" + "Attack" + i + ".png";
            attackLeftImages[i] = new GreenfootImage(filename);
        }
        
        int attackUpFrameCount = 5;
        attackUpImages = new GreenfootImage[attackUpFrameCount];
        for (int i = 0; i < attackUpFrameCount; i++) {
            String filename = folderPath + "Attack/" + "Attack" + i + ".png";
            attackUpImages[i] = new GreenfootImage(filename);
        }

        int attackDownFrameCount = 5;
        attackDownImages = new GreenfootImage[attackDownFrameCount];
        for (int i = 0; i < attackDownFrameCount; i++) {
            String filename = folderPath + "Attack/" + "Attack" + i + ".png";
            attackDownImages[i] = new GreenfootImage(filename);
        }
        
        int hurtFrameCount = 3; // Contoh
        hurtImages = new GreenfootImage[hurtFrameCount];
        for (int i = 0; i < hurtFrameCount; i++) {
            String filename = "Knight/Hurt/Hurt" + i + ".png"; // Sesuaikan path
            hurtImages[i] = new GreenfootImage(filename);
        }

        int deathFrameCount = 6; // Contoh
        deathImages = new GreenfootImage[deathFrameCount];
        for (int i = 0; i < deathFrameCount; i++) {
            String filename = "Knight/Death/Death" + i + ".png"; // Sesuaikan path
            deathImages[i] = new GreenfootImage(filename);
        }
    }

    @Override
    protected void performAttack(String state) {
        // Dapatkan semua musuh dalam jangkauan
        List<Enemy> targets = getObjectsInRange(attackRange, Enemy.class);
        // (Ganti 'Goblin.class' ke 'Enemy.class' jika Bos sudah buat)
        
        if (targets.isEmpty()) {
            return; // Serangan kosong
        }

        // Cek satu per satu musuh, apakah mereka di arah yang benar
        for (Enemy target : targets) {
            int dx = target.getX() - getX();
            int dy = target.getY() - getY();

            // Cek apakah arahnya cocok dengan serangan
            if (state.equals("AttackRight") && dx > 0) {
                target.takeDamage(attackDamage);
            }
            else if (state.equals("AttackLeft") && dx < 0) {
                target.takeDamage(attackDamage);
            }
            else if (state.equals("AttackUp") && dy < 0) {
                target.takeDamage(attackDamage);
            }
            else if (state.equals("AttackDown") && dy > 0) {
                target.takeDamage(attackDamage);
            }
        }
    }
}
