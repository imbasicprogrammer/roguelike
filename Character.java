import greenfoot.*;

/**
 * Kelas template (Induk) untuk semua karakter yang bisa beranimasi.
 * (Versi Upgrade dengan Animasi Hurt & Death)
 */
public abstract class Character extends Actor
{
    // 1. Variabel 'mesin' (protected agar bisa diakses anak)
    protected GreenfootImage[] idleImages;
    protected GreenfootImage[] walkRightImages;
    protected GreenfootImage[] walkLeftImages;
    protected GreenfootImage[] walkUpImages;   
    protected GreenfootImage[] walkDownImages;
    protected GreenfootImage[] attackRightImages;
    protected GreenfootImage[] attackLeftImages;
    protected GreenfootImage[] attackUpImages;   
    protected GreenfootImage[] attackDownImages;
    // --- BARU: Array untuk Hurt & Death ---
    protected GreenfootImage[] hurtImages;
    protected GreenfootImage[] deathImages;
    
    protected int currentImage = 0;
    protected boolean isAttacking = false;
    // --- BARU: Status Hurt & Death ---
    protected boolean isHurt = false;
    protected boolean isDead = false;
    
    protected int animationTimer = 0;
    
    // Bisa di-override oleh anak jika perlu kecepatan beda
    protected int animationDelay = 10; 
    protected int speed = 1;
    
    // --- Logika HP (Sudah ada) ---
    protected int maxHealth = 100;
    protected int health;
    
    // Status utama, di-manage oleh anak
    protected String currentState = "idle";
    
    /**
     * Constructor Induk
     */
    public Character() {
        loadImages(); // Memanggil method yang di-implementasi oleh anak
        
        this.health = this.maxHealth;
        
        if (idleImages != null && idleImages.length > 0) {
            setImage(idleImages[0]);
        }
    }

    /**
     * 'act()' final yang di-upgrade.
     * Urutan prioritas:
     * 1. Jika 'isDead', HANYA mainkan animasi kematian.
     * 2. Jika 'isHurt', HANYA mainkan animasi terluka.
     * 3. Jika normal, baru jalankan AI/Input.
     */
    public final void act()
    {
        // 1. Prioritas tertinggi: Jika mati, mainkan animasi & berhenti
        if (isDead) {
            animate(); 
            return;
        }
        
        // 2. Prioritas kedua: Jika terluka, mainkan animasi & kunci input
        if (isHurt) {
            animate();
        } 
        // 3. Jika aman, jalankan logika normal
        else {
            determineState(); // Dijalankan oleh anak (cek input / AI)
            animate();        // Dijalankan oleh induk (mesin animasi)
        }
    }
    
    // --- METHOD WAJIB UNTUK ANAK ---
    
    protected abstract void loadImages();
    
    protected abstract void determineState();

    /**
     * METHOD ABSTRAK (Sudah ada)
     * *PENTING:* Metode ini sekarang akan dipanggil SECARA OTOMATIS
     * *SETELAH* animasi 'deathImages' selesai diputar.
     */
    protected abstract void onDeath();
    
    
    // --- LOGIKA HP & DAMAGE (MODIFIKASI) ---
    
    /**
     * takeDamage() yang di-upgrade.
     * Sekarang memicu animasi 'isHurt' atau 'isDead'.
     */
    public void takeDamage(int amount) {
        if (isDead) return; // Tidak bisa dilukai jika sudah mati
        
        health -= amount;
        
        if (health <= 0) {
            // --- MATI ---
            health = 0;
            isDead = true;       // Kunci karakter ke status 'mati'
            isHurt = false;      // Mati > Terluka
            isAttacking = false; // Mati > Menyerang
            
            currentState = "death"; // Set status untuk animasi
            currentImage = 0;
            animationTimer = 0;
            
            // JANGAN panggil onDeath() di sini.
            // Kita panggil 'onDeath()' NANTI setelah animasi selesai.
        } 
        else {
            // --- TERLUKA ---
            isHurt = true;           // Kunci karakter ke status 'terluka'
            isAttacking = false;     // Terluka membatalkan serangan
            
            currentState = "hurt";   // Set status untuk animasi
            currentImage = 0;
            animationTimer = 0;
        }
    }
    
    public void heal(int amount) {
        if (isDead) return; // Tidak bisa heal jika sudah mati
        
        health += amount;
        if (health > maxHealth) {
            health = maxHealth;
        }
    }
    
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }

    
    // --- MESIN ANIMASI (MODIFIKASI) ---
    
    /**
     * Method 'animate()' yang di-upgrade.
     * Sekarang mengenali 'hurt' dan 'death'.
     */
    protected void animate() {
        animationTimer++;
        
        if (animationTimer % animationDelay == 0)
        {
            GreenfootImage[] currentAnimation;
            
            // 1. Tentukan array mana yang mau dipakai
            switch (currentState) {
                // Jalan 4 Arah
                case "walkRight":       currentAnimation = walkRightImages;     break;
                case "walkLeft":        currentAnimation = walkLeftImages;      break;
                case "walkUp":          currentAnimation = walkUpImages;        break;
                case "walkDown":        currentAnimation = walkDownImages;      break;
                // Serang 4 Arah
                case "AttackRight":     currentAnimation = attackRightImages;   break;
                case "AttackLeft":      currentAnimation = attackLeftImages;    break;
                case "AttackUp":        currentAnimation = attackUpImages;      break;
                case "AttackDown":      currentAnimation = attackDownImages;    break;
                
                // --- BARU: Status Hurt & Death ---
                case "hurt":            currentAnimation = hurtImages;          break;
                case "death":           currentAnimation = deathImages;         break;
                
                // Default
                case "idle":
                default:
                    currentAnimation = idleImages;
                    break;
            }
            
            // Jika array-nya tidak di-load (null), default ke idle
            if (currentAnimation == null) {
                currentAnimation = idleImages;
                if (currentAnimation == null) return; // Tidak ada gambar sama sekali
            }

            // --- Logika "One-Shot" vs "Looping" (MODIFIKASI) ---
            
            if (currentImage >= currentAnimation.length - 1) 
            {
                // JIKA animasi ini adalah ATTACK...
                if (isAttacking) {
                    setImage(currentAnimation[currentImage]);
                    isAttacking = false;    // Buka kunci
                    currentState = "idle";  // Kembali ke idle
                    currentImage = 0;       
                }
                // JIKA animasi ini adalah HURT...
                else if (isHurt) {
                    setImage(currentAnimation[currentImage]);
                    isHurt = false;         // Buka kunci
                    currentState = "idle";  // Kembali ke idle
                    currentImage = 0;
                }
                // JIKA animasi ini adalah DEATH...
                else if (isDead) {
                    setImage(currentAnimation[currentImage]); // Tampilkan frame terakhir
                    
                    // --- PENTING ---
                    // Animasi mati selesai, SEKARANG panggil 'onDeath()'
                    // (yang akan diisi oleh Player/Enemy)
                    onDeath(); 
                }
                // JIKA ini animasi looping biasa (walk/idle)
                else {
                    currentImage = 0; // Loop kembali ke 0
                    setImage(currentAnimation[currentImage]);
                }
            }
            // JIKA ini bukan frame terakhir
            else {
                // Lanjutkan animasi seperti biasa
                currentImage++;
                setImage(currentAnimation[currentImage]);
            }
        }
    }
    
    /**
     * Metode ini menangani gerakan dan tabrakan dengan Tembok.
     * (Tidak perlu diubah)
     */
    protected void moveWithCollision(int dx, int dy) {
        // Cek tabrakan di sumbu X dulu
        if (getOneObjectAtOffset(dx, 0, Wall.class) == null) {
            setLocation(getX() + dx, getY());
        }
    
        // Cek tabrakan di sumbu Y secara terpisah
        if (getOneObjectAtOffset(0, dy, Wall.class) == null) {
            setLocation(getX(), getY() + dy);
        }
    }
}