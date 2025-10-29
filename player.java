import greenfoot.*;

/**
 * KELAS INDUK BARU untuk SEMUA karakter yang bisa dimainkan.
 * (Ini adalah file 'player.java' Anda yang sudah di-refactor).
 * Kelas ini 'abstract' karena dia tidak tahu cara menyerang.
 */
public abstract class player extends Character
{
    protected int attackCooldown = 0;
    protected int maxAttackCooldown = 30; // 0.5 detik, bisa di-override anak

    protected int xp = 0; 

    public void gainXp(int amount) {
        xp += amount;
        // (Nanti bisa tambahkan cek naik level di sini jika perlu)
        if (getWorld() instanceof Dungeons) {
        // Tampilkan "+XP" selama 2 detik
        ((Dungeons)getWorld()).showNotification("+" + amount + " XP", 2);
        }
    }
    
    public int getXp() {
        return xp;
    }
    
    /**
     * METHOD ABSTRAK BARU:
     * Ini adalah "kontrak". Setiap anak (Knight, Ranger)
     * WAJIB mengisi metode ini dengan logika serangan mereka.
     * @param state Arah serangan (misal "AttackRight")
     */
    protected abstract void performAttack(String state);

    /**
     * IMPLEMENTASI WAJIB (dari Character):
     * Ini adalah logika kematian untuk SEMUA player.
     */
    @Override
    protected void onDeath() {
        // Animasi kematian sudah selesai diputar.
        getWorld().showText("GAME OVER", 400, 300);
        Greenfoot.stop();
    }
    
    /**
     * IMPLEMENTASI WAJIB (dari Character):
     * Ini adalah "Otak" bersama untuk semua Player.
     * Mengelola input gerak, cooldown, dan memanggil 'performAttack()'.
     */
    @Override
    protected void determineState() {
        // 1. Logika Cooldown
        if (attackCooldown > 0) {
            attackCooldown--;
        }

        // 2. Kunci Input Gerak/Serang
        if (isAttacking || isHurt) {
            return; 
        }
        
        // 3. Logika Input Gerakan (Tombol Panah)
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
        
        // 4. Logika Input Serangan (Tombol WASD)
        if (attackCooldown == 0) 
        {
            String attackState = ""; // Arah serangan
            
            if (Greenfoot.isKeyDown("d")) {
                attackState = "AttackRight";
            }
            else if (Greenfoot.isKeyDown("a")) {
                attackState = "AttackLeft";
            }
            else if (Greenfoot.isKeyDown("w")) {
                attackState = "AttackUp";
            }
            else if (Greenfoot.isKeyDown("s")) {
                attackState = "AttackDown";
            }

            // Jika tombol serangan ditekan:
            if (!attackState.isEmpty()) {
                newState = attackState;     // Set animasi
                isAttacking = true;         // Kunci input
                attackCooldown = maxAttackCooldown; // Reset cooldown
                
                // PANGGIL METODE ABSTRAK!
                // Java akan otomatis memanggil 'performAttack'
                // milik Ranger atau Knight.
                performAttack(attackState);
            }
        }
        
        // 5. Reset Animasi
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
}